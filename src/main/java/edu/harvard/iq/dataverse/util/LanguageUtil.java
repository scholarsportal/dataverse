package edu.harvard.iq.dataverse.util;

import java.util.Locale;
import edu.harvard.iq.dataverse.DataverseLocale;

public class LanguageUtil {
    
	DataverseLocale language;
    Locale locale;	
	         
    public LanguageUtil()
    {    	
    	language = new DataverseLocale();      	
		locale = language.getLocale();
	}
     
    public String  getLanguage() {			
		return locale.getLanguage() ;
	}
 
}