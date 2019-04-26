/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.harvard.iq.dataverse.authorization.providers.builtin;

import edu.harvard.iq.dataverse.DataverseLocaleBean;
import edu.harvard.iq.dataverse.authorization.AuthenticatedUserDisplayInfo;
import edu.harvard.iq.dataverse.authorization.groups.impl.ipaddress.IpGroup;
import edu.harvard.iq.dataverse.authorization.groups.impl.ipaddress.IpGroupsServiceBean;
import edu.harvard.iq.dataverse.authorization.groups.impl.ipaddress.ip.IpAddress;
import edu.harvard.iq.dataverse.util.BundleUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author manikons
 */
@Stateless
@Named("affiliationServiceBean")
public class AffiliationServiceBean implements Serializable {

    private static final Logger logger = Logger.getLogger(AffiliationServiceBean.class.getCanonicalName());
    
    @EJB
    IpGroupsServiceBean ipGroupsService;
    
    @Inject
    DataverseLocaleBean bean;
    
    public void convertAffiliation(AuthenticatedUserDisplayInfo userDisplayInfo, ResourceBundle fromBundle, ResourceBundle toBundle) {
        Enumeration<String> enumeration = fromBundle.getKeys();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String engValue = fromBundle.getString(key);
            if (userDisplayInfo.getAffiliation().equalsIgnoreCase(engValue)) {
                String value = toBundle.getString(key);
                if (StringUtils.isNotBlank(value)) {
                    userDisplayInfo.setAffiliation(value);
                    break;
                } else {
                    logger.log(Level.SEVERE, "Unable to find affiliation key for {0}", userDisplayInfo.getAffiliation());
                }
            }
        }
    }

    public String getAlias(String userAffiliation) {
        if ("OTHER".equalsIgnoreCase(userAffiliation.toUpperCase())) {
            return "";
        }
        ResourceBundle bundle = BundleUtil.getResourceBundle("affiliation", "en");
        Enumeration<String> enumeration = bundle.getKeys();
        while (enumeration.hasMoreElements()) {
            String next = enumeration.nextElement();
            String value = bundle.getString(next);
            if (value.equalsIgnoreCase(userAffiliation)) {
                return next.substring(next.indexOf(".")+1);
            }
        }        
        logger.log(Level.SEVERE, "Unable to find alias for {0}", userAffiliation);
        return "";
    }
    
    public List<String> getValues(ResourceBundle bundle) {
        List<String> values = new ArrayList<String>();
        Enumeration<String> enumeration = bundle.getKeys();
        while (enumeration.hasMoreElements()) {
            String next = enumeration.nextElement();
            String value = bundle.getString(next);
            values.add(value);
        }
        return values;
    }
    
    public String getAffiliationFromIPAddress() {
        ResourceBundle bundle = BundleUtil.getResourceBundle("affiliation", "en");
        HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String remoteAddressFromRequest = httpServletRequest.getRemoteAddr();
        IpAddress sourceAddress = null;
        if (remoteAddressFromRequest != null) {
            sourceAddress = IpAddress.valueOf(remoteAddressFromRequest);
            Set<IpGroup> ipgroups = ipGroupsService.findAllIncludingIp(sourceAddress);
            Iterator<IpGroup> iterator = ipgroups.iterator();
            List<String> values = getValues(bundle);
            while (iterator.hasNext()) {
                IpGroup next = iterator.next();
                if (values.contains(next.getDisplayName())) {
                    return next.getDisplayName();
                }
            }
        }
        logger.log(Level.WARNING, "IPAddress not found. {0}", Optional.ofNullable(sourceAddress.toString()));
        return StringUtils.EMPTY;
    }
    
    public String getLocalizedAffiliation(String affiliation) {
        String localeCode = bean.getLocaleCode(); 
        logger.log(Level.INFO, "getLocalizedAffiliation() Locale. {0}", localeCode);
        if (!localeCode.equalsIgnoreCase("en")) {
            ResourceBundle bundle = BundleUtil.getResourceBundle("affiliation", localeCode);
            ResourceBundle enBundle = BundleUtil.getResourceBundle("affiliation", "en");
            Enumeration<String> enumeration = enBundle.getKeys();
            while (enumeration.hasMoreElements()) {
                String next = enumeration.nextElement();
                String value = enBundle.getString(next);
                if (affiliation.equalsIgnoreCase(value)) {
                    return bundle.getString(next);
                }
            }
        }
        return affiliation;
    }
}