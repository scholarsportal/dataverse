package edu.harvard.iq.dataverse.globus;

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

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;

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

        String redirectURL = "https://utl-192-123.library.utoronto.ca/globus.xhtml";
        if (code != null ) {
            try {
            redirectURL = URLEncoder.encode(redirectURL,"UTF-8");
            URL url = new URL("https://auth.globus.org/v2/oauth2/token?code=" + code + "&redirect_uri=" + redirectURL
                    + "&grant_type=authorization_code&scope=openid+email+profile+urn:globus:auth:scope:transfer.api.globus.org:all");
            logger.info(url.toString());

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Authorization", "Basic NThjMGYxNDQtN2QzMy00ZTYzLTk3MmUtMjljNjY5YzJjNGJiOktzSUVDMDZtTUxlRHNKTDBsTmRibXBIbjZvaWpQNGkwWVVuRmQyVDZRSnc9");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded" );
                connection.setRequestMethod("POST");

                int status = connection.getResponseCode();

                logger.info("status: " + status);

                try {

                    //System.out.println("****** Content of the URL ********");
                    BufferedReader br = new BufferedReader( new InputStreamReader(connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = br.readLine()) != null){
                        sb.append(line+"\n");
                    }
                    br.close();

                    Gson gson = new Gson();
                    logger.info(sb.toString());
                    AccessToken accessToken = gson.fromJson(sb.toString(), AccessToken.class);
                    logger.info(accessToken.getAccessToken());

                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        }
            logger.info("Success");
        }
}
