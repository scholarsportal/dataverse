package edu.harvard.iq.dataverse.globus;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import edu.harvard.iq.dataverse.Dataset;
import edu.harvard.iq.dataverse.DatasetServiceBean;
import edu.harvard.iq.dataverse.FeaturedDataverseServiceBean;

import javax.ejb.EJB;
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

import java.util.logging.Logger;
import com.google.gson.Gson;
import org.primefaces.PrimeFaces;


@ViewScoped
@Named("GlobusServiceBean")
public class GlobusServiceBean implements java.io.Serializable{

    @PersistenceContext(unitName = "VDCNet-ejbPU")
    private EntityManager em;

    @EJB
    protected DatasetServiceBean datasetSvc;

    private static final Logger logger = Logger.getLogger(FeaturedDataverseServiceBean.class.getCanonicalName());

    private String code;
    private String datasetId;
    private String directory;

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

        getDirectory();
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
                    logger.severe("Cannot get client token " );
                    return;
                }
                logger.info(clientTokenUser.getAccessToken());
                Identity idnt = getIdentity(usr);
                if (idnt == null) {
                    logger.severe("Cannot get client token " );
                    return;
                }
                logger.info("Identity email " + idnt.getId());

                logger.info("Start Tasklist " + idnt.getId());
                getTaskList(clientTokenUser);
                logger.info("End Tasklist " + idnt.getId());

                int status = createDirectory(clientTokenUser);
                if (status == 202) {
                    int perStatus = givePermission(idnt, clientTokenUser);
                    if (perStatus != 201) {
                        logger.severe("Cannot get permissions ");
                        return;
                    }
                } else if (status == 502) {
                    if (checkPermisions(idnt, clientTokenUser)) {
                        int perStatus = givePermission(idnt, clientTokenUser);
                        if (perStatus != 201) {
                            logger.severe("Cannot get permissions ");
                            return;
                        } else {
                            logger.info("permissions already exist");
                        }
                    }
                } else {
                    logger.severe ("Cannot create directory, status code " + status);
                    return;
                }

                goGlobus();

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

    private void goGlobus() throws IOException {

        String httpString = "window.location.replace('" + "https://app.globus.org/file-manager?origin_id=5102894b-f28f-47f9-bc9a-d8e1b4e9e62c&origin_path=" + directory + "'" +")";
        PrimeFaces.current().executeScript(httpString);
    }

    private boolean checkPermisions(Identity idnt, AccessToken clientTokenUser) throws MalformedURLException {
        URL url = new URL("https://transfer.api.globusonline.org/v0.10/endpoint/5102894b-f28f-47f9-bc9a-d8e1b4e9e62c/access_list");
        MakeRequestResponse result = makeRequest(url, "Bearer",
                clientTokenUser.getOtherTokens().get(0).getAccessToken(),"GET",  null);

        if (result.status == 200) {
            AccessList al = parseJson(result.jsonResponse, AccessList.class, false);
            for (int i = 0; i< al.getDATA().size(); i++) {
                Permissions pr = al.getDATA().get(i);
                if ((pr.getPath().equals(directory + "/") || pr.getPath().equals(directory )) &&
                     (pr.getPermissions().equals("rw") || pr.getPermissions().equals("wr")) &&
                         (pr.getPrincipal().equals(idnt.getId()))) {
                    logger.info("Permissions already exist");
                    return false;
                } else {
                    continue;
                }
            }
        }

        return true;
    }

    private int givePermission(Identity idnt, AccessToken clientTokenUser) throws MalformedURLException {
        URL url = new URL("https://transfer.api.globusonline.org/v0.10/endpoint/5102894b-f28f-47f9-bc9a-d8e1b4e9e62c/access");

        Permissions permissions = new Permissions();
        permissions.setDATA_TYPE("access");
        permissions.setPrincipalType("identity");
        permissions.setPrincipal(idnt.getId());
        permissions.setPath(directory + "/");
        permissions.setPermissions("rw");

        Gson gson = new GsonBuilder().create();


        MakeRequestResponse result = makeRequest(url, "Bearer",
                clientTokenUser.getOtherTokens().get(0).getAccessToken(),"POST",  gson.toJson(permissions));

        if (result.status == 400) {
            logger.severe("Path " + permissions.getPath() + " is not valid");
        } else if (result.status == 409) {
            PermissionsResponse pr = parseJson(result.jsonResponse, PermissionsResponse.class, false);
            if (pr.getCode().equals("LimitExceeded")) {
                logger.severe("Endpoint ACL already has the maximum number of access rules");
            } else if (pr.getCode().equals("Exists")) {
                logger.warning("ACL already exists" );
                return 201;
            }

        }

        return result.status;
    }

    private int createDirectory(AccessToken clientTokenUser) throws MalformedURLException {
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

    private int getTaskList(AccessToken clientTokenUser) throws MalformedURLException {
        URL url = new URL("https://transfer.api.globusonline.org/v0.10/task_list");


        MakeRequestResponse result = makeRequest(url, "Bearer",
                clientTokenUser.getOtherTokens().get(0).getAccessToken(),"GET",  null);
        logger.info("==TEST ==" + result.toString());


        Tasklist tasklist = null;
        Task task = null;
        if (result.status == 200) {
            tasklist = parseJson(result.jsonResponse, Tasklist.class, true);
            if (tasklist.getTasklist().size() > 0) {
                task = tasklist.getTasklist().get(0);
                logger.info("==TEST2 ==" +task.toString());
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

    private AccessToken getClientToken() throws MalformedURLException {
        URL url = new URL("https://auth.globus.org/v2/oauth2/token?scope=openid+email+profile+urn:globus:auth:scope:transfer.api.globus.org:all&grant_type=client_credentials");

        MakeRequestResponse result = makeRequest(url, "Basic",
                "ODA0ODBhNzEtODA5ZC00ZTJhLWExNmQtY2JkMzA1NTk0ZDdhOmQvM3NFd1BVUGY0V20ra2hkSkF3NTZMWFJPaFZSTVhnRmR3TU5qM2Q3TjA9","POST",   null);
        AccessToken clientTokenUser = null;
        if (result.status == 200) {
            clientTokenUser = parseJson(result.jsonResponse, AccessToken.class, true);
        }
        return clientTokenUser;
    }

    private AccessToken getAccessToken(HttpServletRequest origRequest ) throws UnsupportedEncodingException, MalformedURLException {
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

    private UserInfo getUserInfo(AccessToken accessTokenUser) throws MalformedURLException {

        URL url = new URL("https://auth.globus.org/v2/oauth2/userinfo");
        MakeRequestResponse result = makeRequest(url, "Bearer" , accessTokenUser.getAccessToken() , "GET",  null);
        UserInfo usr = null;
        if (result.status == 200) {
            usr = parseJson(result.jsonResponse, UserInfo.class, true);
        }

        return usr;
    }

    private MakeRequestResponse  makeRequest(URL url, String authType, String authCode, String method, String jsonString) {
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

    void getDirectory() {
        Dataset dataset = null;
        try {
            dataset = datasetSvc.find(Long.parseLong(datasetId));
            if (dataset == null) {
                logger.severe("Dataset not found " + datasetId);
                directory = null;
                return;
            }
            directory = "/" + dataset.getAuthorityForFileStorage() + "/" + dataset.getIdentifierForFileStorage();
            logger.info(dataset.getAuthorityForFileStorage() + "/" + dataset.getIdentifierForFileStorage());

        } catch (NumberFormatException nfe) {
            logger.severe(nfe.getMessage());
            directory = null;
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


}
