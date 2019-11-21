package edu.harvard.iq.dataverse;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.primefaces.PrimeFaces;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.net.ssl.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.logging.Logger;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;

@ViewScoped
@Named("GlobusServiceBean")
public class GlobusServiceBean implements java.io.Serializable{

    @PersistenceContext(unitName = "VDCNet-ejbPU")
    private EntityManager em;

    private static final Logger logger = Logger.getLogger(FeaturedDataverseServiceBean.class.getCanonicalName());

    protected KeyManager[] keyManagers = null;
    protected TrustManager[] trustManagers = null;
    protected SSLSocketFactory socketFactory;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    protected SSLSocketFactory createSocketFactory() {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(this.keyManagers, this.trustManagers, null);
            return context.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException("Can't create SSLSocketFactory.", e);
        }
    }

    protected synchronized void initSocketFactory(boolean force) {
        if (this.socketFactory == null || force) {
            this.socketFactory = createSocketFactory();
        }
    }

    // Create a trust manager that does not validate certificate chains
    TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
    };



    public void onLoad() {
        logger.info("Start Globus " + code);
        HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String redirectURL = origRequest.getRequestURL().toString();
        if (code != null ) {
            //    try {
            //redirectURL = URLEncoder.encode(redirectURL,"UTF-8");
            //URL url = new URL("https://auth.globus.org/v2/oauth2/token?code=" + code + "&redirect_uri=" + redirectURL
            //        + "&grant_type=authorization_code");

            /*    SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

                //SSLSocketFactory tempSocketFactory = createSocketFactory();
                //connection.setSSLSocketFactory(tempSocketFactory);
                connection.setRequestProperty("Authorization", "Basic NThjMGYxNDQtN2QzMy00ZTYzLTk3MmUtMjljNjY5YzJjNGJiOktzSUVDMDZtTUxlRHNKTDBsTmRibXBIbjZvaWpQNGkwWVVuRmQyVDZRSnc9");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded" );
                connection.setRequestMethod("PUT");
               // SSLContext sc = SSLContext.getInstance("SSL");
                //sc.init(null, trustAllCerts, new java.security.SecureRandom());
                //HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                int status = connection.getResponseCode();
                // TODO: Do something with non 200 status.
                System.out.println("status: " + status);
            } catch (MalformedURLException ex) {
                logger.severe(ex.getMessage());
            } catch (IOException ex) {
                logger.severe(ex.getMessage());
            //} catch (NoSuchAlgorithmException e) {
            //    e.printStackTrace();
            //} catch (KeyManagementException e) {
            //    e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        }*/

    /*  try {
          URL url = new URL("http://localhost:8080/api/access/datafile/782/metadata?fileMetadataId=442&gbrecs=true");
          URLConnection uc = url.openConnection();
          //String userpass = "58c0f144-7d33-4e63-972e-29c669c2c4bb:KsIEC06mMLeDsJL0lNdbmpHn6oijP4i0YUnFd2T6QJw=";
          //String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
          //uc.setRequestProperty("Authorization", basicAuth);
          InputStream in = uc.getInputStream();
      } catch (Exception e) {
          logger.severe(e.getMessage());
      }*/


            try {
                logger.info("Redirect url " + redirectURL);
                OAuthClientRequest oauthRequest = OAuthClientRequest
                        .tokenLocation("https://auth.globus.org/v2/oauth2/token")
                        .setGrantType(GrantType.AUTHORIZATION_CODE)
                        .setClientId("58c0f144-7d33-4e63-972e-29c669c2c4bb")
                        .setRedirectURI(redirectURL)
                        .setClientSecret("KsIEC06mMLeDsJL0lNdbmpHn6oijP4i0YUnFd2T6QJw=")
                        //.setScope(authScope)
                        .setCode(code)
                        .buildBodyMessage();

                OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
                logger.info("Before access token");
                String accessToken = oAuthClient.accessToken(oauthRequest).getAccessToken();
                Long expiresIn = oAuthClient.accessToken(oauthRequest).getExpiresIn();

                logger.info(accessToken);

            } catch (Exception e) {
                logger.info("Exception");
                logger.severe(e.getMessage());
            }

            logger.info("Success");
        }
        //PrimeFaces.current().executeScript("window.location.search");

       // PrimeFaces.current().executeScript("location.reload(true)");
       // String httpString = "window.open('" + toolHandler.getToolUrlWithQueryParams()+  "','_blank'" +")";
       // PrimeFaces.current().executeScript(httpString);

 /*                          <script>
                //<![CDATA[
                var url = window.location.search;
        console.log(url);
        var urlParams = new URLSearchParams(window.location.search);
        if (urlParams.has('code')) {
            console.log(urlParams.get('code'));
            var request = new XMLHttpRequest();
            var new_url = 'https://auth.globus.org/v2/oauth2/token';
            new_url = new_url + "?code=" + urlParams.get('code');
            var n = window.location.href.indexOf("?");
            if (n!== -1) {
                var res = window.location.href.substring(0, n);
                new_url = new_url + "&redirect_uri=" + res;
            } else {
                new_url = new_url + "&redirect_uri=" + window.location.href;
            }
            new_url = new_url + "&grant_type=authorization_code";
            console.log(new_url);
            request.open('POST', new_url, true);
            // request.setRequestHeader("Authorization", "Basic " + "Ro6PXnGc3urxedUklR+UTSmoMlZQOWsv2lan8W6lsAE=");
            request.setRequestHeader("Authorization", "Basic NThjMGYxNDQtN2QzMy00ZTYzLTk3MmUtMjljNjY5YzJjNGJiOnRlc3Q=");
            request.setRequestHeader("Content-Type",  "application/x-www-form-urlencoded");
            request.onload = function() {
                console.log("good");
                console.log(request.status);

            }
            request.send();
        }
        //]]>*/

    }

}
