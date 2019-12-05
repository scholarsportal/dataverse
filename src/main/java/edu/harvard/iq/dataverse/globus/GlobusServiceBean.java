package edu.harvard.iq.dataverse.globus;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import edu.harvard.iq.dataverse.Dataset;
import edu.harvard.iq.dataverse.DatasetServiceBean;
import edu.harvard.iq.dataverse.FeaturedDataverseServiceBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;
import com.google.gson.Gson;
import org.primefaces.PrimeFaces;


@Stateless
@Named("GlobusServiceBean")
public class GlobusServiceBean implements java.io.Serializable{

    @PersistenceContext(unitName = "VDCNet-ejbPU")
    private EntityManager em;

    @EJB
    protected DatasetServiceBean datasetSvc;

    private static final Logger logger = Logger.getLogger(FeaturedDataverseServiceBean.class.getCanonicalName());

    private String code;
    private String datasetId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    public void onLoad() {
        logger.info("Start Globus " + code);
        logger.info("DatasetId " + datasetId);

        String directory = getDirectory(datasetId);
        if (directory == null) {
            logger.severe("Cannot find directory");
            return;
        }
        HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        logger.info(origRequest.getScheme());
        logger.info(origRequest.getServerName());

        if (code != null ) {
            try {
                AccessToken accessTokenUser = getAccessToken(origRequest);
                if (accessTokenUser == null) {
                    logger.severe("Cannot get access user token for code " + code);
                    return;
                }
                UserInfo usr = getUserInfo(accessTokenUser);
                if (usr == null) {
                    logger.severe("Cannot get user info for " + accessTokenUser.getAccessToken());
                    return;
                }
                logger.info(accessTokenUser.getAccessToken());
                logger.info(usr.getEmail());
                AccessToken clientTokenUser = getClientToken();
                if (clientTokenUser == null) {
                    logger.severe("Cannot get client token ");
                    return;
                }
                logger.info(clientTokenUser.getAccessToken());

                int status = createDirectory(clientTokenUser, directory);
                if (status == 202) {
                    int perStatus = givePermission("identity", usr.getSub(), "rw", clientTokenUser, directory);
                    if (perStatus != 201) {
                        logger.severe("Cannot get permissions ");
                        return;
                    }
                } else if (status == 502) { //directory already exists
                    int perStatus =  givePermission("identity", usr.getSub(), "rw", clientTokenUser, directory);
                    if (perStatus == 409) {
                        logger.info("permissions already exist");
                    } else if (perStatus != 201) {
                        logger.severe("Cannot get permissions ");
                        return;
                    }
                } else {
                    logger.severe ("Cannot create directory, status code " + status);
                    return;
                }

                goGlobus(directory);

            } catch (MalformedURLException ex) {
                logger.severe(ex.getMessage());
                logger.severe(ex.getCause().toString());
            } catch (UnsupportedEncodingException ex) {
                logger.severe(ex.getMessage());
                logger.severe(ex.getCause().toString());
            } catch (IOException ex) {
                logger.severe(ex.getMessage());
                logger.severe(ex.getCause().toString());
            }
        }

    }

    private void goGlobus(String directory) {

        String httpString = "window.location.replace('" + "https://app.globus.org/file-manager?destination_id=5102894b-f28f-47f9-bc9a-d8e1b4e9e62c&destination_path=" + directory + "'" +")";
        PrimeFaces.current().executeScript(httpString);
    }

    String  checkPermisions(String idnt, AccessToken clientTokenUser, String directory) throws MalformedURLException {
        URL url = new URL("https://transfer.api.globusonline.org/v0.10/endpoint/5102894b-f28f-47f9-bc9a-d8e1b4e9e62c/access_list");
        MakeRequestResponse result = makeRequest(url, "Bearer",
                clientTokenUser.getOtherTokens().get(0).getAccessToken(),"GET",  null);

        if (result.status == 200) {
            AccessList al = parseJson(result.jsonResponse, AccessList.class, false);
            for (int i = 0; i< al.getDATA().size(); i++) {
                Permissions pr = al.getDATA().get(i);
                if (pr.getPath().equals(directory + "/") || pr.getPath().equals(directory )) {
                    return pr.getId();
                } else {
                    continue;
                }
            }
        }

        return null;
    }

    public int givePermission(String principalType, String principal, String perm, AccessToken clientTokenUser, String directory) throws MalformedURLException {
        URL url = new URL("https://transfer.api.globusonline.org/v0.10/endpoint/5102894b-f28f-47f9-bc9a-d8e1b4e9e62c/access");

        Permissions permissions = new Permissions();
        permissions.setDATA_TYPE("access");
        permissions.setPrincipalType(principalType);
        permissions.setPrincipal(principal);
        permissions.setPath(directory + "/");
        permissions.setPermissions(perm);

        Gson gson = new GsonBuilder().create();


        MakeRequestResponse result = makeRequest(url, "Bearer",
                clientTokenUser.getOtherTokens().get(0).getAccessToken(),"POST",  gson.toJson(permissions));

        if (result.status == 400) {
            logger.severe("Path " + permissions.getPath() + " is not valid");
        } else if (result.status == 409) {
            logger.warning("ACL already exists or Endpoint ACL already has the maximum number of access rules");
        }

        return result.status;
    }

    private int createDirectory(AccessToken clientTokenUser, String directory) throws MalformedURLException {
        URL url = new URL("https://transfer.api.globusonline.org/v0.10/operation/endpoint/5102894b-f28f-47f9-bc9a-d8e1b4e9e62c/mkdir");

        MkDir mkDir = new MkDir();
        mkDir.setDataType("mkdir");
        mkDir.setPath(directory);
        Gson gson = new GsonBuilder().create();

        MakeRequestResponse result = makeRequest(url, "Bearer",
                clientTokenUser.getOtherTokens().get(0).getAccessToken(),"POST",  gson.toJson(mkDir));
        logger.info(result.toString());

        if (result.status == 502) {
            logger.warning("Cannot create directory " + mkDir.getPath() + ", it already exists");
        } else if (result.status == 403) {
            logger.severe("Cannot create directory " + mkDir.getPath() + ", permission denied");
        } else if  (result.status == 202) {
            logger.info("Directory created " + mkDir.getPath());
        }

        return result.status;

    }

    private int getTaskList(AccessToken accessTokenUser) throws MalformedURLException, ParseException {
        URL url = new URL("https://transfer.api.globusonline.org/v0.10/task_list");

        //accessTokenUser.getOtherTokens().get(0).getAccessToken()
        MakeRequestResponse result = makeRequest(url, "Bearer",
                "Ag67eJqr2Yr289kdXWb7JzEyBzOeqPQeQNkrdbWq04BngoMoYyC2CoVQbW4pVBQ4XKvqqjQadx9x90f1o42kwIrXwy",
                "GET",  null);
        logger.info("==TEST ==" + result.toString());



        //2019-12-01 18:34:37+00:00
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String timeWhenAsyncStarted = sdf.format(new Date());
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(sdf.parse(timeWhenAsyncStarted));

        Calendar cal2 = Calendar.getInstance();

        Tasklist tasklist = null;

        if (result.status == 200) {
            tasklist = parseJson(result.jsonResponse, Tasklist.class, false);
            for (int i = 0; i< tasklist.getDATA().size(); i++) {
                Task task = tasklist.getDATA().get(i);
                Date tastTime = sdf.parse(task.getRequest_time().toString());
                cal2.setTime(tastTime);

                logger.info(" timeWhenAsyncStarted = " + timeWhenAsyncStarted + "task.getRequest_time().toString()  " + task.getRequest_time().toString());

                if ( task.getStatus().equals("SUCCEEDED") && task.getType().equals("TRANSFER" ) &&
                        task.getDestination_endpoint_display_name().equals("Dataverse GCS test collection") && cal1.before(cal2))  {

                    logger.info(" timeWhenAsyncStarted is before tastTime  =  TASK owner id " + task.getTask_id());
                    // get /task/<task_id>/successful_transfers
                    // verify datasetid in "destination_path": "/~/test_godata_copy/file1.txt",
                    // go to aws and get files and write to database tables
                }
                else
                {
                    logger.info(" timeWhenAsyncStarted is after tastTime =  TASK owner id " + task.getTask_id());

                }
            }
        }
        return result.status;

    }

    private Identity getIdentity(UserInfo usr) throws MalformedURLException {
        URL url = new URL("https://auth.globus.org/v2/api/identities?usernames=" + usr.getEmail());

        MakeRequestResponse result = makeRequest(url, "Basic",
                "ODA0ODBhNzEtODA5ZC00ZTJhLWExNmQtY2JkMzA1NTk0ZDdhOmQvM3NFd1BVUGY0V20ra2hkSkF3NTZMWFJPaFZSTVhnRmR3TU5qM2Q3TjA9","GET", null);
        Identities ids = null;
        Identity id = null;
        if (result.status == 200) {
            ids = parseJson(result.jsonResponse, Identities.class, true);
            if (ids.getIdentities().size() > 0) {
                id = ids.getIdentities().get(0);
            }
        }
        return id;
    }

    public AccessToken getClientToken() throws MalformedURLException {
        URL url = new URL("https://auth.globus.org/v2/oauth2/token?scope=openid+email+profile+urn:globus:auth:scope:transfer.api.globus.org:all&grant_type=client_credentials");

        MakeRequestResponse result = makeRequest(url, "Basic",
                "ODA0ODBhNzEtODA5ZC00ZTJhLWExNmQtY2JkMzA1NTk0ZDdhOmQvM3NFd1BVUGY0V20ra2hkSkF3NTZMWFJPaFZSTVhnRmR3TU5qM2Q3TjA9","POST",   null);
        AccessToken clientTokenUser = null;
        if (result.status == 200) {
            clientTokenUser = parseJson(result.jsonResponse, AccessToken.class, true);
        }
        return clientTokenUser;
    }

    public AccessToken getAccessToken(HttpServletRequest origRequest ) throws UnsupportedEncodingException, MalformedURLException {
        String redirectURL = "https://" + origRequest.getServerName() + "/globus.xhtml";
        redirectURL = URLEncoder.encode(redirectURL, "UTF-8");

        URL url = new URL("https://auth.globus.org/v2/oauth2/token?code=" + code + "&redirect_uri=" + redirectURL
                    + "&grant_type=authorization_code");
        logger.info(url.toString());

         MakeRequestResponse result = makeRequest(url, "Basic",
                    //"NThjMGYxNDQtN2QzMy00ZTYzLTk3MmUtMjljNjY5YzJjNGJiOktzSUVDMDZtTUxlRHNKTDBsTmRibXBIbjZvaWpQNGkwWVVuRmQyVDZRSnc9", "POST");
                    "ODA0ODBhNzEtODA5ZC00ZTJhLWExNmQtY2JkMzA1NTk0ZDdhOmQvM3NFd1BVUGY0V20ra2hkSkF3NTZMWFJPaFZSTVhnRmR3TU5qM2Q3TjA9","POST",   null);
        AccessToken accessTokenUser = null;

        if (result.status == 200) {
            logger.info("Access Token: \n" + result.toString());
            accessTokenUser = parseJson(result.jsonResponse, AccessToken.class, true);
            logger.info(accessTokenUser.getAccessToken());
        }

        return accessTokenUser;

    }

    public UserInfo getUserInfo(AccessToken accessTokenUser) throws MalformedURLException {

        URL url = new URL("https://auth.globus.org/v2/oauth2/userinfo");
        MakeRequestResponse result = makeRequest(url, "Bearer" , accessTokenUser.getAccessToken() , "GET",  null);
        UserInfo usr = null;
        if (result.status == 200) {
            usr = parseJson(result.jsonResponse, UserInfo.class, true);
        }

        return usr;
    }

    public MakeRequestResponse  makeRequest(URL url, String authType, String authCode, String method, String jsonString) {
        String str = null;
        HttpURLConnection connection = null;
        int status = 0;
        try {
            connection = (HttpURLConnection) url.openConnection();
            //Basic NThjMGYxNDQtN2QzMy00ZTYzLTk3MmUtMjljNjY5YzJjNGJiOktzSUVDMDZtTUxlRHNKTDBsTmRibXBIbjZvaWpQNGkwWVVuRmQyVDZRSnc9
            logger.info(authType + " " + authCode);
            connection.setRequestProperty("Authorization", authType + " " + authCode);
            //connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestMethod(method);
            if (jsonString != null) {
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                logger.info(jsonString);
                connection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
                wr.write(jsonString);
                wr.flush();
            }

            status = connection.getResponseCode();
            logger.info("Status now " + status);
            InputStream result = connection.getInputStream();
            if (result != null) {
                logger.info("Result is not null");
                str = readResultJson(result).toString();
                logger.info("str is ");
                logger.info(result.toString());
            } else {
                logger.info("Result is null");
                str = null;
            }

            logger.info("status: " + status);
        } catch (IOException ex) {
            logger.info("IO");
            logger.severe(ex.getMessage());
            logger.info(ex.getCause().toString());
            logger.info(ex.getStackTrace().toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        MakeRequestResponse r = new MakeRequestResponse(str, status);
        return r;

    }

    private StringBuilder readResultJson(InputStream in) {
        StringBuilder sb = null;
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            logger.info(sb.toString());
        } catch (IOException e) {
            sb = null;
            logger.severe(e.getMessage());
        }
        return sb;
    }

    private <T> T parseJson(String sb, Class<T> jsonParserClass, boolean namingPolicy) {
        if (sb != null) {
            Gson gson = null;
            if (namingPolicy) {
                gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

            } else {
                gson = new GsonBuilder().create();
            }
            T jsonClass = gson.fromJson(sb, jsonParserClass);
            return jsonClass;
        } else {
            logger.severe("Bad respond from token rquest");
            return null;
        }
    }

    String getDirectory(String datasetId) {
        Dataset dataset = null;
        String directory = null;
        try {
            dataset = datasetSvc.find(Long.parseLong(datasetId));
            if (dataset == null) {
                logger.severe("Dataset not found " + datasetId);
                return null;
            }
            String storeId = dataset.getStorageIdentifier();
            storeId.substring(storeId.indexOf("//") + 1);
            directory = storeId.substring(storeId.indexOf("//") + 1);
            logger.info(storeId);
            logger.info(directory);
            logger.info("Storage identifier:" + dataset.getIdentifierForFileStorage());
            return directory;

        } catch (NumberFormatException nfe) {
            logger.severe(nfe.getMessage());

            return null;
        }

    }

    class MakeRequestResponse {
        public String jsonResponse;
        public int status;
        MakeRequestResponse(String jsonResponse, int status) {
            this.jsonResponse = jsonResponse;
            this.status = status;
        }

    }

    private int findDirectory(String directory, AccessToken clientTokenUser) throws MalformedURLException {
        URL url = new URL(" https://transfer.api.globusonline.org/v0.10/endpoint/5102894b-f28f-47f9-bc9a-d8e1b4e9e62c/ls?path=" + directory + "/");

        MakeRequestResponse result = makeRequest(url, "Bearer",
                clientTokenUser.getOtherTokens().get(0).getAccessToken(),"GET", null);

        return result.status;
    }

    public boolean giveGlobusPublicPermissions(String datasetId) throws UnsupportedEncodingException, MalformedURLException {

        AccessToken clientTokenUser = getClientToken();
        if (clientTokenUser == null) {
            logger.severe("Cannot get client token ");
            return false ;
        }

        String directory = getDirectory(datasetId);
        logger.info(directory);

        int status = findDirectory(directory, clientTokenUser);

        if (status == 404 || status == 200) {

            int perStatus = givePermission("all_authenticated_users", "", "r", clientTokenUser, directory);

            if (perStatus == 409) {
                logger.info("Permissions already exist or limit was reached");
            } else if (perStatus == 400) {
                logger.info("No directory in Globus");
            } else if (perStatus != 201) {
                return false;
            }
        } else {
            logger.severe("Cannot find directory in globus, status " + status );
            return false;
        }

        return true;
    }


}
