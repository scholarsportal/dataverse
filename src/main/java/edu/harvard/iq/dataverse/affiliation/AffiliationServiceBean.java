/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.harvard.iq.dataverse.affiliation;

import edu.harvard.iq.dataverse.util.BundleUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
        
@Stateless
@Named("affiliationServiceBean")
public class AffiliationServiceBean implements Serializable  {
    
    @PersistenceContext(unitName = "VDCNet-ejbPU")
    protected EntityManager em;
    
    public List<Object[]> getAffiliationsByLocale(String locale) {
        String affProp = "affiliation";
        if (locale != null && !locale.equalsIgnoreCase("en")) {
            affProp = affProp + "_" + locale;
        }

        List<Object[]> affiliationList = new ArrayList<Object[]>();        
        List<Object[]> affiliates = findAll();
        for (Object[] affiliate : affiliates) {
            Object[] item = new Object[4];
            item[0] = affiliate[0];          
            item[1] = affiliate[1];
            String alias = BundleUtil.getStringFromPropertyFile("affiliation." + affiliate[1], affProp);
            if (alias != null) {
                item[2] = alias;
            } else {
                item[2] = affiliate[2];
            }           
            item[3] = affiliate[3];
            affiliationList.add(item);
        }
        return affiliationList;
    }    
    
    private List findAll() {
        String jpql = "Select affiliation.affiliation_id, home, title, array_to_string(array_agg(pattern), ',')\n"
                + "FROM affiliation\n"
                + "inner join affiliation_pattern on affiliation_pattern.affiliation_id = affiliation.affiliation_id\n"
                + "GROUP BY affiliation.affiliation_id";
        Query query = em.createNativeQuery(jpql);
        return query.getResultList();
    }     
}