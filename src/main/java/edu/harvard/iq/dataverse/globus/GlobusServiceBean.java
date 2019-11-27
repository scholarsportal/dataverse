package edu.harvard.iq.dataverse.globus;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import edu.harvard.iq.dataverse.FeaturedDataverseServiceBean;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Logger;
import com.google.gson.Gson;
import org.apache.poi.ss.formula.functions.T;

@ViewScoped
@Named("GlobusServiceBean")
public class GlobusServiceBean implements java.io.Serializable{

    @PersistenceContext(unitName = "VDCNet-ejbPU")
    private EntityManager em;

    private static final Logger logger = Logger.getLogger(FeaturedDataverseServiceBean.class.getCanonicalName());

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void onLoad() {
        logger.info("Start Globus " + code);
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
                int status = createDirectory(clientTokenUser);
                if (status == 202) {
                    int perStatus = givePermission(idnt, clientTokenUser);
                    if (perStatus != 200) {
                        logger.severe("Cannot get permissions " );
                        return;
                    }
                } else if (status != 502) {
                    logger.severe ("Cannot create directory, status code " + status);
                    return;
                }

            } catch (MalformedURLException | UnsupportedEncodingException ex) {
                logger.severe(ex.getMessage());
                logger.severe(ex.getCause().toString());
            }
        }

    }

    private int givePermission(Identity idnt, AccessToken clientTokenUser) throws MalformedURLException {
        URL url = new URL("https://transfer.api.globusonline.org/v0.10/endpoint/5102894b-f28f-47f9-bc9a-d8e1b4e9e62c/access");
        Integer status = new Integer(0);
        Permissions permissions = new Permissions();
        permissions.setDATA_TYPE("access");
        permissions.setPrincipalType("identity");
        permissions.setPrincipal(idnt.getId());
        permissions.setPath("/~/testvictoria3");
        permissions.setPermissions("rw");

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();


        StringBuilder result = makeRequest(url, "Bearer",
                clientTokenUser.getOtherTokens().get(0).getAccessToken(),"POST", status, gson.toJson(permissions));

        if (status.intValue() == 400) {
            logger.severe("Path " + permissions.getPath() + " is not valid");
        } else if (status.intValue() == 409) {
            PermissionsResponse pr = parseJson(result, PermissionsResponse.class);
            if (pr.getCode().equals("LimitExceeded")) {
                logger.severe("Endpoint ACL already has the maximum number of access rules");
            } else if (pr.getCode().equals("Exists")) {
                logger.warning("ACL already exists" );
                return 200;
            }

        }

        return status;
    }

    private int createDirectory(AccessToken clientTokenUser) throws MalformedURLException {
        URL url = new URL("https://transfer.api.globusonline.org/v0.10/operation/endpoint/5102894b-f28f-47f9-bc9a-d8e1b4e9e62c/mkdir");
        Integer status = new Integer(0);
        MkDir mkDir = new MkDir();
        mkDir.setDataType("mkdir");
        mkDir.setPath("/~/testvictoria3");
        Gson gson = new GsonBuilder().create();

        StringBuilder result = makeRequest(url, "Bearer",
                clientTokenUser.getOtherTokens().get(0).getAccessToken(),"POST", status, gson.toJson(mkDir));
        logger.info(result.toString());

        if (status.intValue() == 502) {
            logger.warning("Cannot create directory " + mkDir.getPath() + ", it already exists");
        } else if (status.intValue() == 403) {
            logger.severe("Cannot create directory " + mkDir.getPath() + ", permission denied");
        } else if  (status.intValue() == 202) {
            logger.info("Directory created " + mkDir.getPath());
        }

        return status.intValue();

    }

    private Identity getIdentity(UserInfo usr) throws MalformedURLException {
        URL url = new URL("https://auth.globus.org/v2/api/identities?usernames=" + usr.getEmail());
        Integer status = new Integer(0);
        StringBuilder result = makeRequest(url, "Basic",
                "ODA0ODBhNzEtODA5ZC00ZTJhLWExNmQtY2JkMzA1NTk0ZDdhOmQvM3NFd1BVUGY0V20ra2hkSkF3NTZMWFJPaFZSTVhnRmR3TU5qM2Q3TjA9","GET", status,null);
        Identities ids = null;
        Identity id = null;
        if (status.intValue() == 200) {
            ids = parseJson(result, Identities.class);
            if (ids.getIdentities().size() > 0) {
                id = ids.getIdentities().get(0);
            }
        }
        return id;
    }

    private AccessToken getClientToken() throws MalformedURLException {
        URL url = new URL("https://auth.globus.org/v2/oauth2/token?scope=openid+email+profile+urn:globus:auth:scope:transfer.api.globus.org:all&grant_type=client_credentials");
        Integer status = new Integer(0);
        StringBuilder result = makeRequest(url, "Basic",
                "ODA0ODBhNzEtODA5ZC00ZTJhLWExNmQtY2JkMzA1NTk0ZDdhOmQvM3NFd1BVUGY0V20ra2hkSkF3NTZMWFJPaFZSTVhnRmR3TU5qM2Q3TjA9","POST",  status, null);
        AccessToken clientTokenUser = null;
        if (status.intValue() == 200) {
            clientTokenUser = parseJson(result, AccessToken.class);
        }
        return null;
    }

    private AccessToken getAccessToken(HttpServletRequest origRequest ) throws UnsupportedEncodingException, MalformedURLException {
        String redirectURL = "https://" + origRequest.getServerName() + "/globus.xhtml";
        redirectURL = URLEncoder.encode(redirectURL, "UTF-8");

        URL url = new URL("https://auth.globus.org/v2/oauth2/token?code=" + code + "&redirect_uri=" + redirectURL
                    + "&grant_type=authorization_code");
        logger.info(url.toString());
        Integer status = new Integer(0);

        StringBuilder result = makeRequest(url, "Basic",
                    //"NThjMGYxNDQtN2QzMy00ZTYzLTk3MmUtMjljNjY5YzJjNGJiOktzSUVDMDZtTUxlRHNKTDBsTmRibXBIbjZvaWpQNGkwWVVuRmQyVDZRSnc9", "POST");
                    "ODA0ODBhNzEtODA5ZC00ZTJhLWExNmQtY2JkMzA1NTk0ZDdhOmQvM3NFd1BVUGY0V20ra2hkSkF3NTZMWFJPaFZSTVhnRmR3TU5qM2Q3TjA9","POST",  status, null);
        AccessToken accessTokenUser = null;
        logger.info(status.toString());
        if (status.intValue() == 200) {
            logger.info("Access Token: \n" + result.toString());
            accessTokenUser = parseJson(result, AccessToken.class);
            logger.info(accessTokenUser.getAccessToken());
        }

        return accessTokenUser;

    }

    private UserInfo getUserInfo(AccessToken accessTokenUser) throws MalformedURLException {

        URL url = new URL("https://auth.globus.org/v2/oauth2/userinfo");
        Integer status = new Integer(0);
        StringBuilder result = makeRequest(url, "Bearer" , accessTokenUser.getAccessToken() , "GET", status, null);
        UserInfo usr = null;
        if (status == 200) {
            usr = parseJson(result, UserInfo.class);
        }

        return usr;
    }

    private StringBuilder  makeRequest(URL url, String authType, String authCode, String method, Integer status, String jsonString) {
        StringBuilder str = null;
        HttpURLConnection connection = null;
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

            InputStream result = connection.getInputStream();
            if (result != null) {
                str = readResultJson(result);
            } else {
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

        return str;

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

    private <T> T parseJson(StringBuilder sb, Class<T> jsonParserClass) {
        if (sb != null) {
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            T jsonClass = gson.fromJson(sb.toString(), jsonParserClass);
            return jsonClass;
        } else {
            logger.severe("Bad respond from token rquest");
            return null;
        }
    }

}
