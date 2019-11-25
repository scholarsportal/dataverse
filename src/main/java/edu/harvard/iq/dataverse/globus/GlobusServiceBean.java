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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

        String redirectURL = "https://" + origRequest.getServerName() + "/globus.xhtml";
        if (code != null ) {
            URL url = null;
            try {
                redirectURL = URLEncoder.encode(redirectURL, "UTF-8");
                String scope = URLEncoder.encode("urn:globus:auth:scope:auth.globus.org:view_identities+openid+email+profile", "UTF-8");

                url = new URL("https://auth.globus.org/v2/oauth2/token?code=" + code + "&redirect_uri=" + redirectURL
                        + "&grant_type=authorization_code&" + scope);
                logger.info(url.toString());

                InputStream result = makeRequest(url, "Basic",
                        "NThjMGYxNDQtN2QzMy00ZTYzLTk3MmUtMjljNjY5YzJjNGJiOktzSUVDMDZtTUxlRHNKTDBsTmRibXBIbjZvaWpQNGkwWVVuRmQyVDZRSnc9", "POST");
               /* if (result != null) {
                    StringBuilder sb = readResultJson(result);
                    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
                    accessTokenUser = gson.fromJson(sb.toString(), AccessToken.class);
                    logger.info(accessTokenUser.getAccessToken());
                } else {
                    logger.severe("Bad respond from token rquest");
                    return;
                }*/
                AccessToken accessTokenUser = parseJson(result, AccessToken.class);

                getUserInfo(accessTokenUser);
            } catch (Exception ex) {
                logger.severe(ex.getMessage());
                return;
            }

        }

    }

    UserInfo getUserInfo(AccessToken accessTokenUser) throws MalformedURLException {
        UserInfo usr = null;
        URL url = new URL("https://auth.globus.org/v2/oauth2/userinfo");
        InputStream result = makeRequest(url, "Bearer" , accessTokenUser.getAccessToken() , "GET");
        if (result != null) {
            StringBuilder sb = readResultJson(result);
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            usr = gson.fromJson(sb.toString(), UserInfo.class);
            logger.info(usr.getEmail());
        } else {
            logger.severe("Bad respond from token request");
        }

        return usr;
    }

    private InputStream makeRequest(URL url, String authType, String authCode, String method) {
        InputStream result = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //Basic NThjMGYxNDQtN2QzMy00ZTYzLTk3MmUtMjljNjY5YzJjNGJiOktzSUVDMDZtTUxlRHNKTDBsTmRibXBIbjZvaWpQNGkwWVVuRmQyVDZRSnc9
            logger.info(authType + " " + authCode);
            connection.setRequestProperty("Authorization", authType + " " + authCode);
            //connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestMethod(method);

            int status = connection.getResponseCode();
            if (status == 200) {
                result = connection.getInputStream();
            } else {
                logger.severe("Status request is " + status );
            }

            logger.info("status: " + status);

        } catch (MalformedURLException ex) {
            logger.severe(ex.getMessage());
        } catch (IOException ex) {
            logger.info("IO");
            logger.severe(ex.getMessage());
            logger.info(ex.getCause().toString());
            logger.info(ex.getStackTrace().toString());
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
            logger.info(ex.getCause().toString());
        }

        return result;

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

    private <T> T parseJson(InputStream result, Class<T> jsonParserClass) {
        if (result != null) {
            StringBuilder sb = readResultJson(result);
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            T jsonClass = gson.fromJson(sb.toString(), jsonParserClass);
            return jsonClass;
        } else {
            logger.severe("Bad respond from token rquest");
            return null;
        }
    }

}
