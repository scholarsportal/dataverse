/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.harvard.iq.dataverse.authorization.groups.impl.affiliation;

import edu.harvard.iq.dataverse.DataverseSession;
import edu.harvard.iq.dataverse.authorization.AuthenticatedUserDisplayInfo;
import edu.harvard.iq.dataverse.authorization.groups.impl.ipaddress.IpGroup;
import edu.harvard.iq.dataverse.authorization.groups.impl.ipaddress.IpGroupsServiceBean;
import edu.harvard.iq.dataverse.authorization.groups.impl.ipaddress.ip.IpAddress;
import edu.harvard.iq.dataverse.util.BundleUtil;
import org.apache.commons.lang.StringUtils;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author manikons
 */
@Stateless
@Named("affiliationServiceBean")
public class AffiliationServiceBean implements Serializable {

    private static final Logger logger = Logger.getLogger(AffiliationServiceBean.class.getCanonicalName());

    @EJB
    IpGroupsServiceBean ipGroupsService;

    @Inject
    DataverseSession session;

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
        ResourceBundle bundle = BundleUtil.getResourceBundle("affiliation", new Locale("en"));
        Enumeration<String> enumeration = bundle.getKeys();
        while (enumeration.hasMoreElements()) {
            String next = enumeration.nextElement();
            String value = bundle.getString(next);
            if (value.equalsIgnoreCase(userAffiliation)) {
                String alias = next.substring(next.indexOf(".") + 1);
                String newKey = next.replace(".","_")+"_federationcegeps";
                try {
                    String isitCegep = bundle.getString(newKey);
                    if (isitCegep.equalsIgnoreCase("true")) {
                        return "federationcegeps";
                    } else {
                        return alias;
                    }
                }catch (Exception e)
                {

                }
                return alias;
                /*
                if(alias.equalsIgnoreCase("jonquiere") ||
                        alias.equalsIgnoreCase("montmorency") ||
                        alias.equalsIgnoreCase("levis") ||
                        alias.equalsIgnoreCase("cdc") ||
                        alias.equalsIgnoreCase("maisonneuve") ||
                        alias.equalsIgnoreCase("federationcegeps") ||
                        alias.equalsIgnoreCase("cegepthetford")
                )
                {
                    return "federationcegeps";
                }
                else
                {
                    return alias;
                }
                */
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
        ResourceBundle bundle = BundleUtil.getResourceBundle("affiliation", new Locale("en"));
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest httpServletRequest = (HttpServletRequest) externalContext.getRequest();
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
        //logger.log(Level.WARNING, "IPAddress not found. {0}");
        return bundle.getString("affiliation.other");
    }

    public String getLocalizedAffiliation(String affiliation) {
        String localeCode = session.getLocaleCode();
        //logger.log(Level.INFO, "getLocalizedAffiliation() Locale. {0}", localeCode);
        if (!localeCode.equalsIgnoreCase("en")) {
            ResourceBundle bundle = BundleUtil.getResourceBundle("affiliation", new Locale(localeCode));
            ResourceBundle enBundle = BundleUtil.getResourceBundle("affiliation", new Locale("en"));
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
