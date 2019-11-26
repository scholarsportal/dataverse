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
                UserInfo usr = getUserInfo(accessTokenUser);
                logger.info(accessTokenUser.getAccessToken());
                logger.info(usr.getEmail());
                AccessToken clientTokenUser = getClientToken();
                logger.info(clientTokenUser.getAccessToken());
                Identity idnt = getIdentity(usr);
                logger.info("Identity email " + idnt.getId());
                if (!createDirectory(clientTokenUser)) {
                    return;
                }
            } catch (MalformedURLException | UnsupportedEncodingException ex) {
                logger.severe(ex.getMessage());
                logger.severe(ex.getCause().toString());
            }
        }

    }

    private boolean createDirectory(AccessToken clientTokenUser) throws MalformedURLException {
        URL url = new URL("https://transfer.api.globusonline.org/v0.10/operation/endpoint/5102894b-f28f-47f9-bc9a-d8e1b4e9e62c/mkdir");
        MkDir mkDir = new MkDir();
        mkDir.setDataType("mkdir");
        mkDir.setPath("/~/testvictoria3");
        Gson gson = new GsonBuilder().create();

        StringBuilder result = makeRequest(url, "Bearer",
                clientTokenUser.getOtherTokens().get(0).getAccessToken(),"POST",gson.toJson(mkDir));
        logger.info(result.toString());
        MkDirResponse response = parseJson(result, MkDirResponse.class);
        logger.info(response.getCode());
        if (response.getCode().equals("ExternalError.MkdirFailed.Exists")) {
            logger.warning("Cannot create directory " + mkDir.getPath() + ", it already exists");
            return true;
        } else if (response.getCode().equals("ExternalError.MkdirFailed.PermissionDenied")) {
            logger.severe("Cannot create directory " + mkDir.getPath() + ", permission denied");
            return false;
        } else if (response.getCode().equals("DirectoryCreated")) {
            logger.info("Directory " + mkDir.getPath() + " created");
        }

        return true;
    }

    private Identity getIdentity(UserInfo usr) throws MalformedURLException {
        URL url = new URL("https://auth.globus.org/v2/api/identities?usernames=" + usr.getEmail());
        StringBuilder result = makeRequest(url, "Basic",
                "ODA0ODBhNzEtODA5ZC00ZTJhLWExNmQtY2JkMzA1NTk0ZDdhOmQvM3NFd1BVUGY0V20ra2hkSkF3NTZMWFJPaFZSTVhnRmR3TU5qM2Q3TjA9","GET", null);
        Identities ids = parseJson(result, Identities.class);
        logger.info(result.toString());
        if (ids.getIdentities().size() > 0) {
            return ids.getIdentities().get(0);
        } else {
            logger.severe("Cannot find identity for user " + usr.getEmail());
            return null;
        }
    }

    private AccessToken getClientToken() throws MalformedURLException {
        URL url = new URL("https://auth.globus.org/v2/oauth2/token?scope=openid+email+profile+urn:globus:auth:scope:transfer.api.globus.org:all&grant_type=client_credentials");
        StringBuilder result = makeRequest(url, "Basic",
                "ODA0ODBhNzEtODA5ZC00ZTJhLWExNmQtY2JkMzA1NTk0ZDdhOmQvM3NFd1BVUGY0V20ra2hkSkF3NTZMWFJPaFZSTVhnRmR3TU5qM2Q3TjA9","POST", null);
        AccessToken clientTokenUser = parseJson(result, AccessToken.class);
        return clientTokenUser;
    }

    private AccessToken getAccessToken(HttpServletRequest origRequest ) throws UnsupportedEncodingException, MalformedURLException {
            String redirectURL = "https://" + origRequest.getServerName() + "/globus.xhtml";
            redirectURL = URLEncoder.encode(redirectURL, "UTF-8");
            //String scope = URLEncoder.encode("urn:globus:auth:scope:auth.globus.org:view_identities+openid+email+profile", "UTF-8");
            //scope = "scope=" + scope;
            URL url = new URL("https://auth.globus.org/v2/oauth2/token?code=" + code + "&redirect_uri=" + redirectURL
                    + "&grant_type=authorization_code");
            logger.info(url.toString());

            StringBuilder result = makeRequest(url, "Basic",
                    //"NThjMGYxNDQtN2QzMy00ZTYzLTk3MmUtMjljNjY5YzJjNGJiOktzSUVDMDZtTUxlRHNKTDBsTmRibXBIbjZvaWpQNGkwWVVuRmQyVDZRSnc9", "POST");
                    "ODA0ODBhNzEtODA5ZC00ZTJhLWExNmQtY2JkMzA1NTk0ZDdhOmQvM3NFd1BVUGY0V20ra2hkSkF3NTZMWFJPaFZSTVhnRmR3TU5qM2Q3TjA9","POST", null);
            AccessToken accessTokenUser = parseJson(result, AccessToken.class);
            return accessTokenUser;

    }

    private UserInfo getUserInfo(AccessToken accessTokenUser) throws MalformedURLException {

        URL url = new URL("https://auth.globus.org/v2/oauth2/userinfo");
        StringBuilder result = makeRequest(url, "Bearer" , accessTokenUser.getAccessToken() , "GET", null);
        UserInfo usr = parseJson(result, UserInfo.class);

        return usr;
    }

    private StringBuilder  makeRequest(URL url, String authType, String authCode, String method, String jsonString) {
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


            int status = connection.getResponseCode();
            if (status >= 200 && status < 300) {
                InputStream result = connection.getInputStream();
                str = readResultJson(result);
            } else {
                InputStream result = connection.getInputStream();
                logger.severe("Status request is " + status );
                logger.info(readResultJson(result).toString());
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
