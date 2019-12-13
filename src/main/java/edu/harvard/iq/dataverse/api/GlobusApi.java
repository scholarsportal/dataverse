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
import edu.harvard.iq.dataverse.util.FileUtil;



import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @POST
    @Path("{datasetId}")
    public Response globus(@PathParam("datasetId") String datasetId, @QueryParam("token") String userTransferToken)  {

        Dataset dataset = null;
        try {
            dataset = findDatasetOrDie(datasetId);

        } catch (WrappedResponse ex) {
            return ex.getResponse();
        }
        User apiTokenUser = checkAuth(dataset);

        if (apiTokenUser == null) {
            return unauthorized("Access denied" );
        }

        /*

        1.  execute task_list API call using client credentials
        2.  parse the json output
        3.  filter the tasks with "globusUserId": "d8b3928b-b218-40cf-a9d4-2fb69a9be3cf", and the request_time "request_time": "2019-11-13 20:14:42+00:00",
        4.  check the status for that task , "status": "SUCCEEDED",
        5.  keep looping until the task is Succeeded


        1.  To get list of files: use either
            1.  https://transfer.api.globusonline.org/v0.10/operation/endpoint/ddb59aef-6d04-11e5-ba46-22000b92c6ec/ls?path=/share/godata/
        or
            1.  /task/<task_id>/successful_transfers
        2.


         */

        logger.info("======Start Tasklist " );

        /*

        AccessToken clientTokenUser = null;
        String output = globusServiceBean.getClientTokenLocal();
        logger.info(clientTokenUser.getAccessToken());

        if (clientTokenUser == null) {
            logger.severe("Cannot get client token " );
            return;
        }
        */


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        logger.log(Level.INFO, "====S3 storage driver used for DCM (dataset id={0})", datasetId);
        try {
            StorageIO<Dataset> datasetSIO = DataAccess.getStorageIO(dataset);

            String task_id = null;
            int i = 0;
            //int duration = 25;

            String timeWhenAsyncStarted = sdf.format(new Date(System.currentTimeMillis() + (5 * 60 * 60 * 1000)));  // added 5 hrs to match output from globus api

            String endDateTime = sdf.format(new Date(System.currentTimeMillis() + (4 * 60 * 60 * 1000))); // the tasklist will be monitored for 4 hrs
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(sdf.parse(endDateTime));

            do {
                try {
                    if (userTransferToken != null) {
                        task_id = globusServiceBean.getTaskList(userTransferToken, dataset.getIdentifierForFileStorage(), timeWhenAsyncStarted);
                        //Thread.sleep(10000);
                        String currentDateTime = sdf.format(new Date(System.currentTimeMillis()));
                        Calendar cal2 = Calendar.getInstance();
                        cal2.setTime(sdf.parse(currentDateTime));
                        i++;

                        /*
                        if (i > duration) {
                            logger.info("======Loop Tasklist " + i);
                            logger.info("======first condition ==== ");

                            break;
                        } else
                        */
                        if (cal2.after(cal1)) {
                            logger.info("======Time exceeded " + endDateTime + " ====== " + currentDateTime);
                            logger.info("======second condition ==== ");
                            break;
                        } else if (task_id != null) {
                            break;
                        }
                    }
                    else
                    {
                        //logger.info(" Get User Transfer Token is NULL ");
                    }
                } catch (Exception ex) {
                    //logger.info("======third condition ==== "  );
                    ex.printStackTrace();
                    logger.info(ex.getMessage());
                }

            } while (task_id == null);


            logger.info("======End Tasklist " + task_id );


            //if(task_id != null)
            {
                String directory = dataset.getAuthorityForFileStorage() + "/" + dataset.getIdentifierForFileStorage();

                System.out.println("======= directory ==== " + directory);
                Map<String, Integer> checksumMapOld = new HashMap<>();

                Iterator<FileMetadata> fmIt = dataset.getLatestVersion().getFileMetadatas().iterator();

                while (fmIt.hasNext()) {
                    FileMetadata fm = fmIt.next();
                    if (fm.getDataFile() != null && fm.getDataFile().getId() != null) {
                        String chksum = fm.getDataFile().getChecksumValue();
                        if (chksum != null) {
                            checksumMapOld.put(chksum, 1);

                        }
                    }
                }

                for (S3ObjectSummary s3ObjectSummary : datasetSIO.listAuxObjects("")) {

                    String s3ObjectKey = s3ObjectSummary.getKey();
                    System.out.println("======= s3ObjectKey ==== " + s3ObjectKey);
                    String t = s3ObjectKey.replace(directory, "");
                    //String t = destinationKey.replace(datasetSIO.getDvObject().getAuthority()+"/"+datasetSIO.getDvObject().getIdentifier()+"/","");
                    //
                    System.out.println("======= t ==== " + t);
                    if (t.indexOf(".") > 0) {
                        long totalSize = s3ObjectSummary.getSize();
                        String filePath = s3ObjectKey;
                        String checksumVal = s3ObjectSummary.getETag();

                        if ((checksumMapOld.get(checksumVal) != null)) {
                            System.out.println("======= filename ==== " + filePath + " == file already exists ");
                        } else if ( ! filePath.contains("cached"))
                        {
                            System.out.println("======= filename ==== " + filePath + " == new file - add to database ");


                            DataFile dataFile = new DataFile(DataFileServiceBean.MIME_TYPE_GLOBUS_FILE);
                            dataFile.setChecksumType(DataFile.ChecksumType.SHA1);
                            dataFile.setChecksumValue(checksumVal);


                            dataFile.setFilesize(totalSize);
                            dataFile.setModificationTime(new Timestamp(new Date().getTime()));
                            dataFile.setCreateDate(new Timestamp(new Date().getTime()));
                            dataFile.setPermissionModificationTime(new Timestamp(new Date().getTime()));
                            dataFile.setOwner(dataset);
                            dataset.getFiles().add(dataFile);
                            dataFile.setIngestDone();

                            // set metadata and add to latest version
                            // Set early so we can generate the storage id with the info
                            FileMetadata fmd = new FileMetadata();

                            String fileName = filePath.split("/")[filePath.split("/").length - 1];
                            fmd.setLabel(fileName);
                            fmd.setDirectoryLabel(filePath.replace(directory, "").replace(File.separator + fileName, ""));

                            fmd.setDataFile(dataFile);
                            dataFile.getFileMetadatas().add(fmd);
                            if (dataset.getLatestVersion().getFileMetadatas() == null)
                                dataset.getLatestVersion().setFileMetadatas(new ArrayList<>());

                            dataset.getLatestVersion().getFileMetadatas().add(fmd);
                            fmd.setDatasetVersion(dataset.getLatestVersion());

                            FileUtil.generateS3PackageStorageIdentifier(dataFile);
                        }
                    }
                }

             /*   DatasetLock dcmLock = dataset.getLockFor(DatasetLock.Reason.GlobusUpload);
                if (dcmLock == null) {
                    logger.info("Dataset not locked for DCM upload");
                } else {
                    removeDatasetLocks(dataset, DatasetLock.Reason.GlobusUpload);
                    dataset.removeLock(dcmLock);
                }

                logger.info(" ======= Remove Dataset Lock ");*/


                // update version using the command engine to enforce user permissions and constraints
                if (dataset.getVersions().size() == 1 && dataset.getLatestVersion().getVersionState() == DatasetVersion.VersionState.DRAFT) {
                    try {
                        Command<Dataset> cmd;
                        cmd = new UpdateDatasetVersionCommand(dataset, new DataverseRequest(apiTokenUser, (HttpServletRequest) null));
                        commandEngine.submit(cmd);
                    } catch (CommandException ex) {
                        logger.log(Level.WARNING, "CommandException updating DatasetVersion from batch job: " + ex.getMessage());
                    }
                } else {
                    String constraintError = "ConstraintException updating DatasetVersion form batch job: dataset must be a "
                            + "single version in draft mode.";
                    logger.log(Level.SEVERE, constraintError);
                }

                //Thread.sleep(10000);
                logger.info(" ======= DONE GLOBUS ASYNC CALL ");
                return ok("Nothing to update");
            }

        }  catch (Exception e) {
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
