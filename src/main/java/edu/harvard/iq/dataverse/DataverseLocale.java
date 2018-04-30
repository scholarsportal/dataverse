package edu.harvard.iq.dataverse;


import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

 
@Named
@SessionScoped
public class DataverseLocale implements Serializable {
    
    private static final long serialVersionUID = -4294031399458044273L;
    
    private static final Logger logger = Logger.getLogger(DataverseLocale.class.getCanonicalName());
    
    private Locale locale;
    
    FacesContext context = FacesContext.getCurrentInstance();    
    
    {
        if (context== null){
          locale= new Locale ("");
        } else if(context.getViewRoot().getLocale().getLanguage()== "en_US" || context.getViewRoot().getLocale().getLanguage()== "en"){
        	 locale= new Locale ("");
        }else{
         	locale= context.getViewRoot().getLocale();
        }
    }
    
    
    public DataverseLocale(){    	 
         
    }   
    
    
    public Locale getLocale(){
        return locale;
    }
     
    public boolean isLocaleFr(){
        return locale.getLanguage().equals("fr");
    }
    
    public void setLocaleFr(){
        locale = Locale.FRANCE;
    }
    
    public void setLocaleEn(){
        locale = Locale.ENGLISH;
    }     
    
    public void changePageToFr(){
    	logger.warning("================== change page to fr ====================== "   );
        locale = Locale.FRANCE;
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        try{
            String url = ((HttpServletRequest)FacesContext.getCurrentInstance()
                    .getExternalContext().getRequest()).getHeader("referer");           
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        }catch(IOException ioe){
            logger.log(Level.SEVERE, "rediect error", ioe);
        }
    }
    
    public void changePageToEn(){
    	logger.warning("================== change page to en ====================== "   );
        locale = new Locale ("");
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        try{
        	
            String url = ((HttpServletRequest)FacesContext.getCurrentInstance()
                    .getExternalContext().getRequest()).getHeader("referer");
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
            
        }catch(IOException ioe){
            logger.log(Level.SEVERE, "rediect error", ioe);
        }
    }
}