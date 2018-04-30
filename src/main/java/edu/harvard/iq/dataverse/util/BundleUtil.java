package edu.harvard.iq.dataverse.util;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import edu.harvard.iq.dataverse.DataverseLocale;
import javax.faces.context.FacesContext;
import java.util.logging.Logger;


public class BundleUtil {

    private static final Logger logger = Logger.getLogger(BundleUtil.class.getCanonicalName());
    private static final String defaultBundleFile = "Bundle";
    private static ResourceBundle bundle; 
    private static Locale bundle_locale;

    public static String getStringFromBundle(String key) {
        return getStringFromBundle(key, null);
    }
   
    public static String getStringFromBundle(String key, List<String> arguments) {
    	FacesContext context = FacesContext.getCurrentInstance();    

    	if (context== null){
    		bundle_locale= new Locale ("");
    	} else if(context.getViewRoot().getLocale().getLanguage()== "en_US" || context.getViewRoot().getLocale().getLanguage()== "en"){
    		bundle_locale= new Locale ("");
    	}else{
    		bundle_locale= context.getViewRoot().getLocale();
    	}

    	bundle = ResourceBundle.getBundle(defaultBundleFile, bundle_locale); 
    	return getStringFromBundle(key, arguments, bundle);
    }
    
    
    public static String getStringFromBundle(String key, List<String> arguments, ResourceBundle bundle) {
    	
    	if (key == null || key.isEmpty()) {
    		return null;
    	}
    	String stringFromBundle = null;
    	try {
    		stringFromBundle = bundle.getString(key);
    	} catch (MissingResourceException ex) {
    		logger.warning("Could not find key \"" + key + "\" in bundle file.");
    		return null;
    	}
    	
    	if (arguments != null) {
    		Object[] argArray = new String[arguments.size()];
    		argArray = arguments.toArray(argArray);
    		return MessageFormat.format(stringFromBundle, argArray);
    	} else {
    		return stringFromBundle;
    	}
    }  

}
