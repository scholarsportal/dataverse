package edu.harvard.iq.dataverse;

import com.amazonaws.services.cloudformation.model.CreateChangeSetRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import edu.harvard.iq.dataverse.authorization.AuthenticationServiceBean;
import edu.harvard.iq.dataverse.authorization.Permission;
import edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser;
import edu.harvard.iq.dataverse.authorization.users.User;
import edu.harvard.iq.dataverse.branding.BrandingUtil;
import edu.harvard.iq.dataverse.dataaccess.DataAccess;
import edu.harvard.iq.dataverse.dataaccess.ImageThumbConverter;
import edu.harvard.iq.dataverse.dataaccess.StorageIO;
import edu.harvard.iq.dataverse.dataset.DatasetUtil;
import edu.harvard.iq.dataverse.datavariable.DataVariable;
import edu.harvard.iq.dataverse.engine.command.Command;
import edu.harvard.iq.dataverse.engine.command.CommandContext;
import edu.harvard.iq.dataverse.engine.command.DataverseRequest;
import edu.harvard.iq.dataverse.engine.command.exception.CommandException;
import edu.harvard.iq.dataverse.engine.command.impl.DestroyDatasetCommand;
import edu.harvard.iq.dataverse.engine.command.impl.FinalizeDatasetPublicationCommand;
import edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetVersionCommand;
import edu.harvard.iq.dataverse.export.ExportService;
import edu.harvard.iq.dataverse.globus.AccessToken;
import edu.harvard.iq.dataverse.globus.GlobusServiceBean;
import edu.harvard.iq.dataverse.globus.Task;
import edu.harvard.iq.dataverse.globus.Tasklist;
import edu.harvard.iq.dataverse.harvest.server.OAIRecordServiceBean;
import edu.harvard.iq.dataverse.ingest.IngestServiceBean;
import edu.harvard.iq.dataverse.search.IndexServiceBean;
import edu.harvard.iq.dataverse.settings.SettingsServiceBean;
import edu.harvard.iq.dataverse.util.BundleUtil;
import edu.harvard.iq.dataverse.util.FileUtil;
import edu.harvard.iq.dataverse.util.JsfHelper;
import edu.harvard.iq.dataverse.util.SystemConfig;
import edu.harvard.iq.dataverse.workflows.WorkflowComment;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.*;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.ocpsoft.common.util.Strings;

import org.apache.commons.lang.RandomStringUtils;
import org.ocpsoft.common.util.Strings;

import static edu.harvard.iq.dataverse.util.JsfHelper.JH;

/**
 *
 * @author skraffmiller
 */


@Stateless
@Named
public class DatasetServiceBean implements java.io.Serializable {

    private static final Logger logger = Logger.getLogger(DatasetServiceBean.class.getCanonicalName());
    @EJB
    IndexServiceBean indexService;

    @EJB
    DOIEZIdServiceBean doiEZIdServiceBean;

    @EJB
    SettingsServiceBean settingsService;

    @EJB
    DatasetVersionServiceBean versionService;

    @EJB
    DvObjectServiceBean dvObjectService;

    @EJB
    AuthenticationServiceBean authentication;

    @EJB
    DataFileServiceBean fileService;

    @EJB
    PermissionServiceBean permissionService;

    @EJB
    OAIRecordServiceBean recordService;

    @EJB
    EjbDataverseEngine commandEngine;

    @EJB
    SystemConfig systemConfig;

    @EJB
    protected GlobusServiceBean globusServiceBean;


    @EJB
    DataverseServiceBean dataverseService;

    @Inject
    DataverseRequestServiceBean dvRequestService;

    @EJB
    DataFileServiceBean datafileService;

    @Inject
    DataverseSession session;

    @EJB
    protected SettingsServiceBean settingsSvc;

    private static final SimpleDateFormat logFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss");

    @PersistenceContext(unitName = "VDCNet-ejbPU")
    protected EntityManager em;

    public Dataset find(Object pk) {
        return em.find(Dataset.class, pk);
    }

    public List<Dataset> findByOwnerId(Long ownerId) {
        return findByOwnerId(ownerId, false);
    }

    public List<Dataset> findPublishedByOwnerId(Long ownerId) {
        return findByOwnerId(ownerId, true);
    }

    private List<Dataset> findByOwnerId(Long ownerId, boolean onlyPublished) {
        List<Dataset> retList = new ArrayList<>();
        TypedQuery<Dataset> query = em.createNamedQuery("Dataset.findByOwnerId", Dataset.class);
        query.setParameter("ownerId", ownerId);
        if (!onlyPublished) {
            return query.getResultList();
        } else {
            for (Dataset ds : query.getResultList()) {
                if (ds.isReleased() && !ds.isDeaccessioned()) {
                    retList.add(ds);
                }
            }
            return retList;
        }
    }

    public List<Long> findIdsByOwnerId(Long ownerId) {
        return findIdsByOwnerId(ownerId, false);
    }

    private List<Long> findIdsByOwnerId(Long ownerId, boolean onlyPublished) {
        List<Long> retList = new ArrayList<>();
        if (!onlyPublished) {
            return em.createNamedQuery("Dataset.findIdByOwnerId")
                    .setParameter("ownerId", ownerId)
                    .getResultList();
        } else {
            List<Dataset> results = em.createNamedQuery("Dataset.findByOwnerId")
                    .setParameter("ownerId", ownerId).getResultList();
            for (Dataset ds : results) {
                if (ds.isReleased() && !ds.isDeaccessioned()) {
                    retList.add(ds.getId());
                }
            }
            return retList;
        }
    }

    public List<Dataset> filterByPidQuery(String filterQuery) {
        // finds only exact matches
        Dataset ds = findByGlobalId(filterQuery);
        List<Dataset> ret = new ArrayList<>();
        if (ds != null) ret.add(ds);

        
        /*
        List<Dataset> ret = em.createNamedQuery("Dataset.filterByPid", Dataset.class)
            .setParameter("affiliation", "%" + filterQuery.toLowerCase() + "%").getResultList();
        //logger.info("created native query: select o from Dataverse o where o.alias LIKE '" + filterQuery + "%' order by o.alias");
        logger.info("created named query");
        */
        if (ret != null) {
            logger.info("results list: " + ret.size() + " results.");
        }
        return ret;
    }

    public List<Dataset> findAll() {
        return em.createQuery("select object(o) from Dataset as o order by o.id", Dataset.class).getResultList();
    }


    public List<Long> findAllLocalDatasetIds() {
        return em.createQuery("SELECT o.id FROM Dataset o WHERE o.harvestedFrom IS null ORDER BY o.id", Long.class).getResultList();
    }

    public List<Long> findAllUnindexed() {
        return em.createQuery("SELECT o.id FROM Dataset o WHERE o.indexTime IS null ORDER BY o.id DESC", Long.class).getResultList();
    }

    /**
     * For docs, see the equivalent method on the DataverseServiceBean.
     *
     * @param numPartitions
     * @param partitionId
     * @param skipIndexed
     * @return a list of datasets
     * @see DataverseServiceBean#findAllOrSubset(long, long, boolean)
     */
    public List<Long> findAllOrSubset(long numPartitions, long partitionId, boolean skipIndexed) {
        if (numPartitions < 1) {
            long saneNumPartitions = 1;
            numPartitions = saneNumPartitions;
        }
        String skipClause = skipIndexed ? "AND o.indexTime is null " : "";
        TypedQuery<Long> typedQuery = em.createQuery("SELECT o.id FROM Dataset o WHERE MOD( o.id, :numPartitions) = :partitionId " +
                skipClause +
                "ORDER BY o.id", Long.class);
        typedQuery.setParameter("numPartitions", numPartitions);
        typedQuery.setParameter("partitionId", partitionId);
        return typedQuery.getResultList();
    }

    /**
     * Merges the passed dataset to the persistence context.
     *
     * @param ds the dataset whose new state we want to persist.
     * @return The managed entity representing {@code ds}.
     */
    public Dataset merge(Dataset ds) {
        return em.merge(ds);
    }

    public Dataset findByGlobalId(String globalId) {
        Dataset retVal = (Dataset) dvObjectService.findByGlobalId(globalId, "Dataset");
        if (retVal != null) {
            return retVal;
        } else {
            //try to find with alternative PID
            return (Dataset) dvObjectService.findByGlobalId(globalId, "Dataset", true);
        }
    }

    /**
     * Instantiate dataset, and its components (DatasetVersions and FileMetadatas)
     * this method is used for object validation; if there are any invalid values
     * in the dataset components, a ConstraintViolationException will be thrown,
     * which can be further parsed to detect the specific offending values.
     *
     * @param id the id of the dataset
     * @throws javax.validation.ConstraintViolationException
     */

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void instantiateDatasetInNewTransaction(Long id, boolean includeVariables) {
        Dataset dataset = find(id);
        for (DatasetVersion version : dataset.getVersions()) {
            for (FileMetadata fileMetadata : version.getFileMetadatas()) {
                // todo: make this optional!
                if (includeVariables) {
                    if (fileMetadata.getDataFile().isTabularData()) {
                        DataTable dataTable = fileMetadata.getDataFile().getDataTable();
                        for (DataVariable dataVariable : dataTable.getDataVariables()) {

                        }
                    }
                }
            }
        }
    }

    public String generateDatasetIdentifier(Dataset dataset, GlobalIdServiceBean idServiceBean) {
        String identifierType = settingsService.getValueForKey(SettingsServiceBean.Key.IdentifierGenerationStyle, "randomString");
        String shoulder = settingsService.getValueForKey(SettingsServiceBean.Key.Shoulder, "");

        switch (identifierType) {
            case "randomString":
                return generateIdentifierAsRandomString(dataset, idServiceBean, shoulder);
            case "sequentialNumber":
                return generateIdentifierAsSequentialNumber(dataset, idServiceBean, shoulder);
            default:
                /* Should we throw an exception instead?? -- L.A. 4.6.2 */
                return generateIdentifierAsRandomString(dataset, idServiceBean, shoulder);
        }
    }

    private String generateIdentifierAsRandomString(Dataset dataset, GlobalIdServiceBean idServiceBean, String shoulder) {
        String identifier = null;
        do {
            identifier = shoulder + RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        } while (!isIdentifierLocallyUnique(identifier, dataset));

        return identifier;
    }

    private String generateIdentifierAsSequentialNumber(Dataset dataset, GlobalIdServiceBean idServiceBean, String shoulder) {

        String identifier;
        do {
            StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("Dataset.generateIdentifierAsSequentialNumber");
            query.execute();
            Integer identifierNumeric = (Integer) query.getOutputParameterValue(1);
            // some diagnostics here maybe - is it possible to determine that it's failing 
            // because the stored procedure hasn't been created in the database?
            if (identifierNumeric == null) {
                return null;
            }
            identifier = shoulder + identifierNumeric.toString();
        } while (!isIdentifierLocallyUnique(identifier, dataset));

        return identifier;
    }

    /**
     * Check that a identifier entered by the user is unique (not currently used
     * for any other study in this Dataverse Network) also check for duplicate
     * in EZID if needed
     *
     * @param userIdentifier
     * @param dataset
     * @param persistentIdSvc
     * @return {@code true} if the identifier is unique, {@code false} otherwise.
     */
    public boolean isIdentifierUnique(String userIdentifier, Dataset dataset, GlobalIdServiceBean persistentIdSvc) {
        if (!isIdentifierLocallyUnique(userIdentifier, dataset)) return false; // duplication found in local database

        // not in local DB, look in the persistent identifier service
        try {
            return !persistentIdSvc.alreadyExists(dataset);
        } catch (Exception e) {
            //we can live with failure - means identifier not found remotely
        }

        return true;
    }

    public boolean isIdentifierLocallyUnique(Dataset dataset) {
        return isIdentifierLocallyUnique(dataset.getIdentifier(), dataset);
    }

    public boolean isIdentifierLocallyUnique(String identifier, Dataset dataset) {
        return em.createNamedQuery("Dataset.findByIdentifierAuthorityProtocol")
                .setParameter("identifier", identifier)
                .setParameter("authority", dataset.getAuthority())
                .setParameter("protocol", dataset.getProtocol())
                .getResultList().isEmpty();
    }

    public Long getMaximumExistingDatafileIdentifier(Dataset dataset) {
        //Cannot rely on the largest table id having the greatest identifier counter
        long zeroFiles = new Long(0);
        Long retVal = zeroFiles;
        Long testVal;
        List<Object> idResults;
        Long dsId = dataset.getId();
        if (dsId != null) {
            try {
                idResults = em.createNamedQuery("Dataset.findIdentifierByOwnerId")
                        .setParameter("ownerId", dsId).getResultList();
            } catch (NoResultException ex) {
                logger.log(Level.FINE, "No files found in dataset id {0}. Returning a count of zero.", dsId);
                return zeroFiles;
            }
            if (idResults != null) {
                for (Object raw : idResults) {
                    String identifier = (String) raw;
                    identifier = identifier.substring(identifier.lastIndexOf("/") + 1);
                    testVal = new Long(identifier);
                    if (testVal > retVal) {
                        retVal = testVal;
                    }
                }
            }
        }
        return retVal;
    }

    public DatasetVersion storeVersion(DatasetVersion dsv) {
        em.persist(dsv);
        return dsv;
    }


    public DatasetVersionUser getDatasetVersionUser(DatasetVersion version, User user) {

        TypedQuery<DatasetVersionUser> query = em.createNamedQuery("DatasetVersionUser.findByVersionIdAndUserId", DatasetVersionUser.class);
        query.setParameter("versionId", version.getId());
        String identifier = user.getIdentifier();
        identifier = identifier.startsWith("@") ? identifier.substring(1) : identifier;
        AuthenticatedUser au = authentication.getAuthenticatedUser(identifier);
        query.setParameter("userId", au.getId());
        try {
            return query.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    public boolean checkDatasetLock(Long datasetId) {
        TypedQuery<DatasetLock> lockCounter = em.createNamedQuery("DatasetLock.getLocksByDatasetId", DatasetLock.class);
        lockCounter.setParameter("datasetId", datasetId);
        lockCounter.setMaxResults(1);
        List<DatasetLock> lock = lockCounter.getResultList();
        return lock.size() > 0;
    }

    public List<DatasetLock> getDatasetLocksByUser(AuthenticatedUser user) {

        TypedQuery<DatasetLock> query = em.createNamedQuery("DatasetLock.getLocksByAuthenticatedUserId", DatasetLock.class);
        query.setParameter("authenticatedUserId", user.getId());
        try {
            return query.getResultList();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public DatasetLock addDatasetLock(Dataset dataset, DatasetLock lock) {
        lock.setDataset(dataset);
        dataset.addLock(lock);
        lock.setStartTime(new Date());
        em.persist(lock);
        em.merge(dataset);
        return lock;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) /*?*/
    public DatasetLock addDatasetLock(Long datasetId, DatasetLock.Reason reason, Long userId, String info) {

        Dataset dataset = em.find(Dataset.class, datasetId);

        AuthenticatedUser user = null;
        if (userId != null) {
            user = em.find(AuthenticatedUser.class, userId);
        }

        // Check if the dataset is already locked for this reason:
        // (to prevent multiple, duplicate locks on the dataset!)
        DatasetLock lock = dataset.getLockFor(reason);
        if (lock != null) {
            return lock;
        }

        // Create new:
        lock = new DatasetLock(reason, user);
        lock.setDataset(dataset);
        lock.setInfo(info);
        lock.setStartTime(new Date());

        if (userId != null) {
            lock.setUser(user);
            if (user.getDatasetLocks() == null) {
                user.setDatasetLocks(new ArrayList<>());
            }
            user.getDatasetLocks().add(lock);
        }

        return addDatasetLock(dataset, lock);
    }

    /**
     * Removes all {@link DatasetLock}s for the dataset whose id is passed and reason
     * is {@code aReason}.
     *
     * @param dataset the dataset whose locks (for {@code aReason}) will be removed.
     * @param aReason The reason of the locks that will be removed.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void removeDatasetLocks(Dataset dataset, DatasetLock.Reason aReason) {
        if (dataset != null) {
            new HashSet<>(dataset.getLocks()).stream()
                    .filter(l -> l.getReason() == aReason)
                    .forEach(lock -> {
                        lock = em.merge(lock);
                        dataset.removeLock(lock);

                        AuthenticatedUser user = lock.getUser();
                        user.getDatasetLocks().remove(lock);

                        em.remove(lock);
                    });
        }
    }
    
    /*
    getTitleFromLatestVersion methods use native query to return a dataset title
    
        There are two versions:
     1) The version with datasetId param only will return the title regardless of version state
     2)The version with the param 'includeDraft' boolean  will return the most recently published title if the param is set to false
    If no Title found return empty string - protects against calling with
    include draft = false with no published version
    */

    public String getTitleFromLatestVersion(Long datasetId) {
        return getTitleFromLatestVersion(datasetId, true);
    }

    public String getTitleFromLatestVersion(Long datasetId, boolean includeDraft) {

        String whereDraft = "";
        //This clause will exclude draft versions from the select
        if (!includeDraft) {
            whereDraft = " and v.versionstate !='DRAFT' ";
        }

        try {
            return (String) em.createNativeQuery("select dfv.value  from dataset d "
                    + " join datasetversion v on d.id = v.dataset_id "
                    + " join datasetfield df on v.id = df.datasetversion_id "
                    + " join datasetfieldvalue dfv on df.id = dfv.datasetfield_id "
                    + " join datasetfieldtype dft on df.datasetfieldtype_id  = dft.id "
                    + " where dft.name = '" + DatasetFieldConstant.title + "' and  v.dataset_id =" + datasetId
                    + whereDraft
                    + " order by v.versionnumber desc, v.minorVersionNumber desc limit 1 "
                    + ";").getSingleResult();

        } catch (Exception ex) {
            logger.log(Level.INFO, "exception trying to get title from latest version: {0}", ex);
            return "";
        }

    }

    public Dataset getDatasetByHarvestInfo(Dataverse dataverse, String harvestIdentifier) {
        String queryStr = "SELECT d FROM Dataset d, DvObject o WHERE d.id = o.id AND o.owner.id = " + dataverse.getId() + " and d.harvestIdentifier = '" + harvestIdentifier + "'";
        Query query = em.createQuery(queryStr);
        List resultList = query.getResultList();
        Dataset dataset = null;
        if (resultList.size() > 1) {
            throw new EJBException("More than one dataset found in the dataverse (id= " + dataverse.getId() + "), with harvestIdentifier= " + harvestIdentifier);
        }
        if (resultList.size() == 1) {
            dataset = (Dataset) resultList.get(0);
        }
        return dataset;

    }

    public Long getDatasetVersionCardImage(Long versionId, User user) {
        if (versionId == null) {
            return null;
        }


        return null;
    }

    /**
     * Used to identify and properly display Harvested objects on the dataverse page.
     *
     * @param datasetIds
     * @return
     */
    public Map<Long, String> getArchiveDescriptionsForHarvestedDatasets(Set<Long> datasetIds) {
        if (datasetIds == null || datasetIds.size() < 1) {
            return null;
        }

        String datasetIdStr = Strings.join(datasetIds, ", ");

        String qstr = "SELECT d.id, h.archiveDescription FROM harvestingClient h, dataset d WHERE d.harvestingClient_id = h.id AND d.id IN (" + datasetIdStr + ")";
        List<Object[]> searchResults;

        try {
            searchResults = em.createNativeQuery(qstr).getResultList();
        } catch (Exception ex) {
            searchResults = null;
        }

        if (searchResults == null) {
            return null;
        }

        Map<Long, String> ret = new HashMap<>();

        for (Object[] result : searchResults) {
            Long dsId;
            if (result[0] != null) {
                try {
                    dsId = (Long) result[0];
                } catch (Exception ex) {
                    dsId = null;
                }
                if (dsId == null) {
                    continue;
                }

                ret.put(dsId, (String) result[1]);
            }
        }

        return ret;
    }


    public boolean isDatasetCardImageAvailable(DatasetVersion datasetVersion, User user) {
        if (datasetVersion == null) {
            return false;
        }

        // First, check if this dataset has a designated thumbnail image: 

        if (datasetVersion.getDataset() != null) {
            DataFile dataFile = datasetVersion.getDataset().getThumbnailFile();
            if (dataFile != null) {
                return ImageThumbConverter.isThumbnailAvailable(dataFile, 48);
            }
        }

        // If not, we'll try to use one of the files in this dataset version:
        // (the first file with an available thumbnail, really)

        List<FileMetadata> fileMetadatas = datasetVersion.getFileMetadatas();

        for (FileMetadata fileMetadata : fileMetadatas) {
            DataFile dataFile = fileMetadata.getDataFile();

            // TODO: use permissionsWrapper here - ? 
            // (we are looking up these download permissions on individual files, 
            // true, and those are unique... but the wrapper may be able to save 
            // us some queries when it determines the download permission on the
            // dataset as a whole? -- L.A. 4.2.1

            if (fileService.isThumbnailAvailable(dataFile) && permissionService.userOn(user, dataFile).has(Permission.DownloadFile)) { //, user)) {
                return true;
            }

        }

        return false;
    }


    // reExportAll *forces* a reexport on all published datasets; whether they 
    // have the "last export" time stamp set or not. 
    @Asynchronous
    public void reExportAllAsync() {
        exportAllDatasets(true);
    }

    public void reExportAll() {
        exportAllDatasets(true);
    }


    // exportAll() will try to export the yet unexported datasets (it will honor
    // and trust the "last export" time stamp).

    @Asynchronous
    public void exportAllAsync() {
        exportAllDatasets(false);
    }

    public void exportAll() {
        exportAllDatasets(false);
    }

    public void exportAllDatasets(boolean forceReExport) {
        Integer countAll = 0;
        Integer countSuccess = 0;
        Integer countError = 0;
        String logTimestamp = logFormatter.format(new Date());
        Logger exportLogger = Logger.getLogger("edu.harvard.iq.dataverse.harvest.client.DatasetServiceBean." + "ExportAll" + logTimestamp);
        String logFileName = "../logs" + File.separator + "export_" + logTimestamp + ".log";
        FileHandler fileHandler;
        boolean fileHandlerSuceeded;
        try {
            fileHandler = new FileHandler(logFileName);
            exportLogger.setUseParentHandlers(false);
            fileHandlerSuceeded = true;
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(DatasetServiceBean.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        if (fileHandlerSuceeded) {
            exportLogger.addHandler(fileHandler);
        } else {
            exportLogger = logger;
        }

        exportLogger.info("Starting an export all job");

        for (Long datasetId : findAllLocalDatasetIds()) {
            // Potentially, there's a godzillion datasets in this Dataverse. 
            // This is why we go through the list of ids here, and instantiate 
            // only one dataset at a time. 
            Dataset dataset = this.find(datasetId);
            if (dataset != null) {
                // Accurate "is published?" test - ?
                // Answer: Yes, it is! We can't trust dataset.isReleased() alone; because it is a dvobject method 
                // that returns (publicationDate != null). And "publicationDate" is essentially
                // "the first publication date"; that stays the same as versions get 
                // published and/or deaccessioned. But in combination with !isDeaccessioned() 
                // it is indeed an accurate test.
                if (dataset.isReleased() && dataset.getReleasedVersion() != null && !dataset.isDeaccessioned()) {

                    // can't trust dataset.getPublicationDate(), no. 
                    Date publicationDate = dataset.getReleasedVersion().getReleaseTime(); // we know this dataset has a non-null released version! Maybe not - SEK 8/19 (We do now! :)
                    if (forceReExport || (publicationDate != null
                            && (dataset.getLastExportTime() == null
                            || dataset.getLastExportTime().before(publicationDate)))) {
                        countAll++;
                        try {
                            recordService.exportAllFormatsInNewTransaction(dataset);
                            exportLogger.info("Success exporting dataset: " + dataset.getDisplayName() + " " + dataset.getGlobalIdString());
                            countSuccess++;
                        } catch (Exception ex) {
                            exportLogger.info("Error exporting dataset: " + dataset.getDisplayName() + " " + dataset.getGlobalIdString() + "; " + ex.getMessage());
                            countError++;
                        }
                    }
                }
            }
        }
        exportLogger.info("Datasets processed: " + countAll.toString());
        exportLogger.info("Datasets exported successfully: " + countSuccess.toString());
        exportLogger.info("Datasets failures: " + countError.toString());
        exportLogger.info("Finished export-all job.");

        if (fileHandlerSuceeded) {
            fileHandler.close();
        }

    }

    public void updateLastExportTimeStamp(Long datasetId) {
        Date now = new Date();
        em.createNativeQuery("UPDATE Dataset SET lastExportTime='" + now.toString() + "' WHERE id=" + datasetId).executeUpdate();
    }

    public Dataset setNonDatasetFileAsThumbnail(Dataset dataset, InputStream inputStream) {
        if (dataset == null) {
            logger.fine("In setNonDatasetFileAsThumbnail but dataset is null! Returning null.");
            return null;
        }
        if (inputStream == null) {
            logger.fine("In setNonDatasetFileAsThumbnail but inputStream is null! Returning null.");
            return null;
        }
        dataset = DatasetUtil.persistDatasetLogoToStorageAndCreateThumbnail(dataset, inputStream);
        dataset.setThumbnailFile(null);
        return merge(dataset);
    }

    public Dataset setDatasetFileAsThumbnail(Dataset dataset, DataFile datasetFileThumbnailToSwitchTo) {
        if (dataset == null) {
            logger.fine("In setDatasetFileAsThumbnail but dataset is null! Returning null.");
            return null;
        }
        if (datasetFileThumbnailToSwitchTo == null) {
            logger.fine("In setDatasetFileAsThumbnail but dataset is null! Returning null.");
            return null;
        }
        DatasetUtil.deleteDatasetLogo(dataset);
        dataset.setThumbnailFile(datasetFileThumbnailToSwitchTo);
        dataset.setUseGenericThumbnail(false);
        return merge(dataset);
    }

    public Dataset removeDatasetThumbnail(Dataset dataset) {
        if (dataset == null) {
            logger.fine("In removeDatasetThumbnail but dataset is null! Returning null.");
            return null;
        }
        DatasetUtil.deleteDatasetLogo(dataset);
        dataset.setThumbnailFile(null);
        dataset.setUseGenericThumbnail(true);
        return merge(dataset);
    }

    // persist assigned thumbnail in a single one-field-update query:
    // (the point is to avoid doing an em.merge() on an entire dataset object...)
    public void assignDatasetThumbnailByNativeQuery(Long datasetId, Long dataFileId) {
        try {
            em.createNativeQuery("UPDATE dataset SET thumbnailfile_id=" + dataFileId + " WHERE id=" + datasetId).executeUpdate();
        } catch (Exception ex) {
            // it's ok to just ignore... 
        }
    }

    public void assignDatasetThumbnailByNativeQuery(Dataset dataset, DataFile dataFile) {
        try {
            em.createNativeQuery("UPDATE dataset SET thumbnailfile_id=" + dataFile.getId() + " WHERE id=" + dataset.getId()).executeUpdate();
        } catch (Exception ex) {
            // it's ok to just ignore... 
        }
    }

    public WorkflowComment addWorkflowComment(WorkflowComment workflowComment) {
        em.persist(workflowComment);
        return workflowComment;
    }

    @Asynchronous
    public void callFinalizePublishCommandAsynchronously(Long datasetId, CommandContext ctxt, DataverseRequest request, boolean isPidPrePublished) throws CommandException {

        // Since we are calling the next command asynchronously anyway - sleep here 
        // for a few seconds, just in case, to make sure the database update of 
        // the dataset initiated by the PublishDatasetCommand has finished, 
        // to avoid any concurrency/optimistic lock issues. 
        try {
            Thread.sleep(15000);
        } catch (Exception ex) {
            logger.warning("Failed to sleep for 15 seconds.");
        }
        logger.fine("Running FinalizeDatasetPublicationCommand, asynchronously");
        Dataset theDataset = find(datasetId);
        commandEngine.submit(new FinalizeDatasetPublicationCommand(theDataset, request, isPidPrePublished));
    }

    /*
     Experimental asynchronous method for requesting persistent identifiers for 
     datafiles. We decided not to run this method on upload/create (so files 
     will not have persistent ids while in draft; when the draft is published, 
     we will force obtaining persistent ids for all the files in the version. 
     
     If we go back to trying to register global ids on create, care will need to 
     be taken to make sure the asynchronous changes below are not conflicting with 
     the changes from file ingest (which may be happening in parallel, also 
     asynchronously). We would also need to lock the dataset (similarly to how 
     tabular ingest logs the dataset), to prevent the user from publishing the
     version before all the identifiers get assigned - otherwise more conflicts 
     are likely. (It sounds like it would make sense to treat these two tasks -
     persistent identifiers for files and ingest - as one post-upload job, so that 
     they can be run in sequence). -- L.A. Mar. 2018
    */
    @Asynchronous
    public void obtainPersistentIdentifiersForDatafiles(Dataset dataset) {
        GlobalIdServiceBean idServiceBean = GlobalIdServiceBean.getBean(dataset.getProtocol(), commandEngine.getContext());

        //If the Id type is sequential and Dependent then write file idenitifiers outside the command
        String datasetIdentifier = dataset.getIdentifier();
        Long maxIdentifier = null;

        if (systemConfig.isDataFilePIDSequentialDependent()) {
            maxIdentifier = getMaximumExistingDatafileIdentifier(dataset);
        }

        for (DataFile datafile : dataset.getFiles()) {
            logger.info("Obtaining persistent id for datafile id=" + datafile.getId());

            if (datafile.getIdentifier() == null || datafile.getIdentifier().isEmpty()) {

                logger.info("Obtaining persistent id for datafile id=" + datafile.getId());

                if (maxIdentifier != null) {
                    maxIdentifier++;
                    datafile.setIdentifier(datasetIdentifier + "/" + maxIdentifier.toString());
                } else {
                    datafile.setIdentifier(fileService.generateDataFileIdentifier(datafile, idServiceBean));
                }

                if (datafile.getProtocol() == null) {
                    datafile.setProtocol(settingsService.getValueForKey(SettingsServiceBean.Key.Protocol, ""));
                }
                if (datafile.getAuthority() == null) {
                    datafile.setAuthority(settingsService.getValueForKey(SettingsServiceBean.Key.Authority, ""));
                }

                logger.info("identifier: " + datafile.getIdentifier());

                String doiRetString;

                try {
                    logger.log(Level.FINE, "creating identifier");
                    doiRetString = idServiceBean.createIdentifier(datafile);
                } catch (Throwable e) {
                    logger.log(Level.WARNING, "Exception while creating Identifier: " + e.getMessage(), e);
                    doiRetString = "";
                }

                // Check return value to make sure registration succeeded
                if (!idServiceBean.registerWhenPublished() && doiRetString.contains(datafile.getIdentifier())) {
                    datafile.setIdentifierRegistered(true);
                    datafile.setGlobalIdCreateTime(new Date());
                }

                DataFile merged = em.merge(datafile);
                merged = null;
            }

        }
    }

    public long findStorageSize(Dataset dataset) throws IOException {
        return findStorageSize(dataset, false);
    }

    /**
     * Returns the total byte size of the files in this dataset
     *
     * @param dataset
     * @param countCachedExtras boolean indicating if the cached disposable extras should also be counted
     * @return total size
     * @throws IOException if it can't access the objects via StorageIO
     *                     (in practice, this can only happen when called with countCachedExtras=true; when run in the
     *                     default mode, the method doesn't need to access the storage system, as the
     *                     sizes of the main files are recorded in the database)
     */
    public long findStorageSize(Dataset dataset, boolean countCachedExtras) throws IOException {
        long total = 0L;

        if (dataset.isHarvested()) {
            return 0L;
        }

        for (DataFile datafile : dataset.getFiles()) {
            total += datafile.getFilesize();

            if (!countCachedExtras) {
                if (datafile.isTabularData()) {
                    // count the size of the stored original, in addition to the main tab-delimited file:
                    Long originalFileSize = datafile.getDataTable().getOriginalFileSize();
                    if (originalFileSize != null) {
                        total += originalFileSize;
                    }
                }
            } else {
                StorageIO<DataFile> storageIO = datafile.getStorageIO();
                for (String cachedFileTag : storageIO.listAuxObjects()) {
                    total += storageIO.getAuxObjectSize(cachedFileTag);
                }
            }
        }

        // and finally,
        if (countCachedExtras) {
            // count the sizes of the files cached for the dataset itself
            // (i.e., the metadata exports):
            StorageIO<Dataset> datasetSIO = DataAccess.getStorageIO(dataset);

            for (String[] exportProvider : ExportService.getInstance(settingsService).getExportersLabels()) {
                String exportLabel = "export_" + exportProvider[1] + ".cached";
                try {
                    total += datasetSIO.getAuxObjectSize(exportLabel);
                } catch (IOException ioex) {
                    // safe to ignore; object not cached
                }
            }
        }

        return total;
    }

    /**
     * An optimized method for deleting a harvested dataset.
     *
     * @param dataset
     * @param request  DataverseRequest (for initializing the DestroyDatasetCommand)
     * @param hdLogger logger object (in practice, this will be a separate log file created for a specific harvesting job)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteHarvestedDataset(Dataset dataset, DataverseRequest request, Logger hdLogger) {
        // Purge all the SOLR documents associated with this client from the 
        // index server: 
        indexService.deleteHarvestedDocuments(dataset);

        try {
            // files from harvested datasets are removed unceremoniously, 
            // directly in the database. no need to bother calling the 
            // DeleteFileCommand on them.
            for (DataFile harvestedFile : dataset.getFiles()) {
                DataFile merged = em.merge(harvestedFile);
                em.remove(merged);
                harvestedFile = null;
            }
            dataset.setFiles(null);
            Dataset merged = em.merge(dataset);
            commandEngine.submit(new DestroyDatasetCommand(merged, request));
            hdLogger.info("Successfully destroyed the dataset");
        } catch (Exception ex) {
            hdLogger.warning("Failed to destroy the dataset");
        }
    }


    public void globusAsyncjob(Long dataset_id ) {



        try {
            logger.info("======Start Tasklist " + dataset_id);

            List<FileMetadata> fileMetadatas = new ArrayList<>();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Dataset dataset = find(dataset_id);
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
                    return  ;
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
                            return  ;

                        }
                    }
                }
            }

            DatasetLock dcmLock = dataset.getLockFor(DatasetLock.Reason.GlobusUpload);
            if (dcmLock == null) {
                logger.info("Dataset not locked for DCM upload");
            } else {
                removeDatasetLocks(dataset, DatasetLock.Reason.GlobusUpload);
                dataset.removeLock(dcmLock);
            }

            logger.info(" ======= Remove Dataset Lock ");


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
                        fileMetadata.getDataFile().setCreator((AuthenticatedUser) session.getUser());
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
                cmd = new UpdateDatasetVersionCommand(dataset, dvRequestService.getDataverseRequest());
                ((UpdateDatasetVersionCommand) cmd).setValidateLenient(true);
                //new DataverseRequest(authenticatedUser, (HttpServletRequest) null)
                //dvRequestService.getDataverseRequest()
                logger.info(" ======= UpdateDatasetVersionCommand END in globus function ");
                commandEngine.submit(cmd);
                logger.info(" ======= commandEngine.submit END in globus function ");
            } catch (CommandException ex) {
                logger.log(Level.WARNING, "======CommandException updating DatasetVersion from batch job: " + ex.getMessage());
                return  ;
            }

            //workingVersion = dataset.getEditVersion();
            //logger.info("========working version id: "+workingVersion.getId());


            //Thread.sleep(10000);
            logger.info(" ======= DONE GLOBUS ASYNC CALL ");



            return  ;
        }  catch(Exception e) {
            String message = e.getMessage();

            logger.info(" ======= DONE GLOBUS ASYNC CALL Exception ============== " + message);
            e.printStackTrace();
            //return error(Response.Status.INTERNAL_SERVER_ERROR, "Uploaded files have passed checksum validation but something went wrong while attempting to move the files into Dataverse. Message was '" + message + "'.");
        }
        return  ;

    }


}






