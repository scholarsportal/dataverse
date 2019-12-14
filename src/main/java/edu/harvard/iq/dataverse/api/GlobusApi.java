package edu.harvard.iq.dataverse.api;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import edu.harvard.iq.dataverse.*;
import edu.harvard.iq.dataverse.authorization.Permission;
import edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser;
import edu.harvard.iq.dataverse.authorization.users.User;
import edu.harvard.iq.dataverse.dataaccess.DataAccess;

import edu.harvard.iq.dataverse.dataaccess.StorageIO;
import edu.harvard.iq.dataverse.engine.command.Command;
import edu.harvard.iq.dataverse.engine.command.DataverseRequest;
import edu.harvard.iq.dataverse.engine.command.exception.CommandException;
import edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetVersionCommand;
import edu.harvard.iq.dataverse.globus.GlobusServiceBean;
import edu.harvard.iq.dataverse.settings.SettingsServiceBean;
import edu.harvard.iq.dataverse.util.FileUtil;



import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@Path("globus")
public class GlobusApi extends AbstractApiBean {
    private static final Logger logger = Logger.getLogger(Access.class.getCanonicalName());

    @EJB
    DatasetServiceBean datasetService;

    @EJB
    GlobusServiceBean globusServiceBean;

    @EJB
    EjbDataverseEngine commandEngine;

    @EJB
    PermissionServiceBean permissionService;

    @Inject
    DataverseRequestServiceBean dvRequestService;


    @POST
    @Path("{datasetId}")
    public Response globus(@PathParam("datasetId") String datasetId, @QueryParam("token") String userTransferToken) {

        logger.info("Started " + datasetId);
        Dataset dataset = null;
        try {
            dataset = findDatasetOrDie(datasetId);

        } catch (WrappedResponse ex) {
            return ex.getResponse();
        }
        User apiTokenUser = checkAuth(dataset);

        if (apiTokenUser == null) {
            return unauthorized("Access denied");
        }

        try {
            logger.info("======Start Tasklist "  );

            /*
            String lockInfoMessage = "Globus upload in progress";
            DatasetLock lock = datasetService.addDatasetLock(dataset.getId(), DatasetLock.Reason.GlobusUpload, apiTokenUser != null ? ((AuthenticatedUser)apiTokenUser).getId() : null, lockInfoMessage);
            if (lock != null) {
                dataset.addLock(lock);
            } else {
                logger.log(Level.WARNING, "Failed to lock the dataset (dataset id={0})", dataset.getId());
            }
*/

            List<FileMetadata> fileMetadatas = new ArrayList<>();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            StorageIO<Dataset> datasetSIO = DataAccess.getStorageIO(dataset);


            String task_id = null;

            String timeWhenAsyncStarted = sdf.format(new Date(System.currentTimeMillis() + (5 * 60 * 60 * 1000)));  // added 5 hrs to match output from globus api

            String endDateTime = sdf.format(new Date(System.currentTimeMillis() + (4 * 60 * 60 * 1000))); // the tasklist will be monitored for 4 hrs
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(sdf.parse(endDateTime));


            do {
                try {
                    String basicGlobusToken = settingsSvc.getValueForKey(SettingsServiceBean.Key.BasicGlobusToken, "");

                    task_id = globusServiceBean.getTaskList(basicGlobusToken, dataset.getIdentifierForFileStorage(), timeWhenAsyncStarted);
                    //Thread.sleep(10000);
                    String currentDateTime = sdf.format(new Date(System.currentTimeMillis()));
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(sdf.parse(currentDateTime));

                    if (cal2.after(cal1)) {
                        logger.info("======Time exceeded " + endDateTime + " ====== " + currentDateTime);
                        logger.info("======second condition ==== ");
                        break;
                    } else if (task_id != null) {
                        break;
                    }

                } catch (Exception ex) {
                    //logger.info("======third condition ==== "  );
                    ex.printStackTrace();
                    logger.info(ex.getMessage());
                    return error(Response.Status.INTERNAL_SERVER_ERROR, "Failed to do task_list" );
                }

            } while (task_id == null);


            logger.info("======End Tasklist " + task_id );


            DatasetVersion workingVersion = dataset.getEditVersion();

            if (workingVersion.getCreateTime() != null) {
                workingVersion.setCreateTime(new Timestamp(new Date().getTime()));
            }


            String directory = dataset.getAuthorityForFileStorage() + "/" + dataset.getIdentifierForFileStorage();

            System.out.println("======= directory ==== " + directory);
            Map<String, Integer> checksumMapOld = new HashMap<>();

            Iterator<FileMetadata> fmIt = workingVersion.getFileMetadatas().iterator();

            while (fmIt.hasNext()) {
                FileMetadata fm = fmIt.next();
                if (fm.getDataFile() != null && fm.getDataFile().getId() != null) {
                    logger.log(Level.INFO, "====fm.getDataFile().getDisplayName() ======", fm.getDataFile().getDisplayName());
                    String chksum = fm.getDataFile().getChecksumValue();
                    if (chksum != null) {
                        checksumMapOld.put(chksum, 1);
                    }
                }
            }

            //todo: step 1
            //fileutil.java
            // private static DataFile createSingleDataFile(DatasetVersion version, File tempFile, String fileName, String contentType,
            // DataFile.ChecksumType checksumType, boolean addToDataset) {


            List<DataFile> dFileList = new ArrayList<>();
            for (S3ObjectSummary s3ObjectSummary : datasetSIO.listAuxObjects("")) {

                String s3ObjectKey = s3ObjectSummary.getKey();

                String t = s3ObjectKey.replace(directory, "");

                if (t.indexOf(".") > 0) {
                    long totalSize = s3ObjectSummary.getSize();
                    String filePath = s3ObjectKey;
                    String checksumVal = s3ObjectSummary.getETag();

                    if ((checksumMapOld.get(checksumVal) != null)) {
                        logger.info("======= filename ==== " + filePath + " == file already exists ");
                    } else if (!filePath.contains("cached")) {

                        logger.info("======= filename ==== " + filePath + " == new file   ");
                        try {
                            // Note: A single uploaded file may produce multiple datafiles -
                            // for example, multiple files can be extracted from an uncompressed
                            // zip file.
                            //dFileList = FileUtil.createDataFilesGlobus(workingVersion, uFile.getInputstream(), uFile.getFileName(), uFile.getContentType(), systemConfig);


                            logger.info(" == in single createDataFiles  1");

                            DataFile datafile = new DataFile(DataFileServiceBean.MIME_TYPE_GLOBUS_FILE);  //MIME_TYPE_GLOBUS
                            datafile.setModificationTime(new Timestamp(new Date().getTime()));
                            datafile.setCreateDate(new Timestamp(new Date().getTime()));
                            datafile.setPermissionModificationTime(new Timestamp(new Date().getTime()));


                            FileMetadata fmd = new FileMetadata();

                            String fileName = filePath.split("/")[filePath.split("/").length - 1];
                            fmd.setLabel(fileName);
                            fmd.setDirectoryLabel(filePath.replace(directory, "").replace(File.separator + fileName, ""));

                            fmd.setDataFile(datafile);

                            datafile.getFileMetadatas().add(fmd);

                            FileUtil.generateS3PackageStorageIdentifier(datafile);
                            logger.info("======= filename ==== " + filePath + " == added to datafile, filemetadata   ");

                            try {
                                // We persist "SHA1" rather than "SHA-1".
                                datafile.setChecksumType(DataFile.ChecksumType.SHA1);
                                datafile.setChecksumValue(checksumVal);
                            } catch (Exception cksumEx) {
                                logger.info("======Could not calculate  checksumType signature for the new file ");
                            }

                            datafile.setFilesize(totalSize);

                            dFileList.add(datafile);

                        } catch (Exception ioex) {
                            logger.info("======Failed to process and/or save the file " + ioex.getMessage());
                            return error(Response.Status.INTERNAL_SERVER_ERROR, "Failed to do task_list" );

                        }
                    }
                }
            }

/*
            DatasetLock dcmLock = dataset.getLockFor(DatasetLock.Reason.GlobusUpload);
            if (dcmLock == null) {
                logger.info("Dataset not locked for DCM upload");
            } else {
                datasetService.removeDatasetLocks(dataset, DatasetLock.Reason.GlobusUpload);
                dataset.removeLock(dcmLock);
            }


            logger.info(" ======= Remove Dataset Lock ");

*/

            // Try to save the NEW files permanently:
            //List<DataFile> filesAdded = ingestService.saveAndAddGlobusFilesToDataset(workingVersion, dFileList);


            List<DataFile> filesAdded = new ArrayList<>();

            logger.info(" == saveAndAddFilesToDataset function started  dFileList.size() == " + dFileList.size());

            if (dFileList != null && dFileList.size() > 0) {

                // Dataset dataset = version.getDataset();

                for (DataFile dataFile : dFileList) {

                    if (dataFile.getOwner() == null) {
                        logger.info(" == set owner for data file == ");
                        dataFile.setOwner(dataset);

                        workingVersion.getFileMetadatas().add(dataFile.getFileMetadata());
                        dataFile.getFileMetadata().setDatasetVersion(workingVersion);
                        dataset.getFiles().add(dataFile);

                    }

                    filesAdded.add(dataFile);

                }

                logger.info(" ===== Done! Finished saving new files in permanent storage and adding them to the dataset.");
            }


            // reset the working list of fileMetadatas, as to only include the ones
            // that have been added to the version successfully:
            fileMetadatas.clear();
            for (DataFile addedFile : filesAdded) {
                logger.info(" ==== check datafile id===== " + addedFile.getId());
                fileMetadatas.add(addedFile.getFileMetadata());
            }
            filesAdded = null;


            logger.info(" 2==== dataset.getVersions().size()  === " + dataset.getVersions().size());
            logger.info(" 2==== dataset.getLatestVersion().getVersionState() ===== " + dataset.getLatestVersion().getVersionState());
            logger.info(" 2==== workingVersion.getId()  ===== " + workingVersion.getId());
            logger.info(" 2==== workingVersion.isDraft() ===== " + workingVersion.isDraft());

            if (workingVersion.isDraft()) {

                logger.info(" ==== inside draft version ");

                Timestamp updateTime = new Timestamp(new Date().getTime());

                workingVersion.setLastUpdateTime(updateTime);
                dataset.setModificationTime(updateTime);

                StringBuilder saveError = new StringBuilder();

                for (FileMetadata fileMetadata : fileMetadatas) {
                    logger.info(" ==== inside filemetadatas size " + fileMetadatas.size());

                    if (fileMetadata.getDataFile().getCreateDate() == null) {
                        logger.info("==== inside filemetadata loop in draft version ");
                        fileMetadata.getDataFile().setCreateDate(updateTime);
                        fileMetadata.getDataFile().setCreator((AuthenticatedUser) apiTokenUser);
                    }
                    fileMetadata.getDataFile().setModificationTime(updateTime);
                    try {
                        // DataFile savedDatafile = datafileService.save(fileMetadata.getDataFile());
                        //fileMetadata = datafileService.mergeFileMetadata(fileMetadata);
                        logger.fine("===Successfully saved DataFile " + fileMetadata.getLabel() + " in the database.");
                    } catch (EJBException ex) {
                        saveError.append(ex).append(" ");
                        saveError.append(ex.getMessage()).append(" ");
                        Throwable cause = ex;
                        while (cause.getCause() != null) {
                            cause = cause.getCause();
                            saveError.append(cause).append(" ");
                            saveError.append(cause.getMessage()).append(" ");
                        }
                    }
                }

                String saveErrorString = saveError.toString();
                if (saveErrorString != null && !saveErrorString.isEmpty()) {
                    logger.log(Level.INFO, "===Couldn''t save dataset: {0}", saveErrorString);
                    //populateDatasetUpdateFailureMessage();

                }

            } else {
                logger.info(" ==== inside released version ");

                for (int i = 0; i < workingVersion.getFileMetadatas().size(); i++) {
                    for (FileMetadata fileMetadata : fileMetadatas) {
                        if (fileMetadata.getDataFile().getStorageIdentifier() != null) {

                            if (fileMetadata.getDataFile().getStorageIdentifier().equals(workingVersion.getFileMetadatas().get(i).getDataFile().getStorageIdentifier())) {
                                logger.info(" ===== inside filemetadata loop in released version ");
                                workingVersion.getFileMetadatas().set(i, fileMetadata);
                            }
                        }
                    }
                }


            }


            try {
                Command<Dataset> cmd;
                logger.info(" ======= UpdateDatasetVersionCommand START in globus function ");
                cmd = new UpdateDatasetVersionCommand(dataset,new DataverseRequest(apiTokenUser, (HttpServletRequest) null));
                ((UpdateDatasetVersionCommand) cmd).setValidateLenient(true);
                //new DataverseRequest(authenticatedUser, (HttpServletRequest) null)
                //dvRequestService.getDataverseRequest()
                logger.info(" ======= UpdateDatasetVersionCommand END in globus function ");
                commandEngine.submit(cmd);
                logger.info(" ======= commandEngine.submit END in globus function ");
            } catch (CommandException ex) {
                logger.log(Level.WARNING, "======CommandException updating DatasetVersion from batch job: " + ex.getMessage());
                return error(Response.Status.INTERNAL_SERVER_ERROR, "Failed to do task_list" );
            }

            //workingVersion = dataset.getEditVersion();
            //logger.info("========working version id: "+workingVersion.getId());


            //Thread.sleep(10000);
            logger.info(" ======= DONE GLOBUS ASYNC CALL ");

            return ok("Finished task_list");
        }  catch(Exception e) {
            String message = e.getMessage();

            logger.info(" ======= DONE GLOBUS ASYNC CALL Exception ============== " + message);
            e.printStackTrace();
            return error(Response.Status.INTERNAL_SERVER_ERROR, "Failed to do task_list" );
            //return error(Response.Status.INTERNAL_SERVER_ERROR, "Uploaded files have passed checksum validation but something went wrong while attempting to move the files into Dataverse. Message was '" + message + "'.");
        }


    }

    private User checkAuth(Dataset dataset) {

        User apiTokenUser = null;

        try {
            apiTokenUser = findUserOrDie();
        } catch (WrappedResponse wr) {
            apiTokenUser = null;
            logger.log(Level.FINE, "Message from findUserOrDie(): {0}", wr.getMessage());
        }

        if (apiTokenUser != null) {
            // used in an API context
            if (!permissionService.requestOn(createDataverseRequest(apiTokenUser), dataset.getOwner()).has(Permission.EditDataset)) {
                apiTokenUser = null;
            }
        }

        return apiTokenUser;

    }
}

