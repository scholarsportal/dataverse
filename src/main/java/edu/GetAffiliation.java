
package edu;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.harvard.iq.dataverse.util.BundleUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(description = "Get Affiliations from DB", urlPatterns = { "/GetAffiliation" })
public class GetAffiliation  extends HttpServlet{

    @PersistenceContext(unitName="VDCNet-ejbPU")
    protected EntityManager em;
                
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Object[]> affiliationList = new ArrayList<Object[]>();
        response.addHeader("Access-Control-Allow-Origin", "*");
        List<Object[]> affiliates = findAllAffiliates();
        for (Object[] affiliate : affiliates) {
            Object[] item = new Object[4];
            item[0] = affiliate[0];            
            item[1] = affiliate[1];
            String alias = BundleUtil.getStringFromPropertyFile("affiliation." + affiliate[1], "affiliation");
            if (alias != null) {
                item[2] = alias;
            } else {
                item[2] = affiliate[2];
            }           
            item[3] = affiliate[3];
            affiliationList.add(item);
        }
        
        ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode json = objectMapper.valueToTree(affiliationList);
        response.getWriter().println( json);
    }
    
    public List findAllAffiliates() {        
        String jpql = "Select affiliation.affiliation_id, home, title, array_to_string(array_agg(pattern), ',')\n" +
                "FROM affiliation\n" +
                "left join affiliation_pattern on affiliation_pattern.affiliation_id = affiliation.affiliation_id\n" +
                "GROUP BY affiliation.affiliation_id ORDER BY home" ;
        Query query = em.createNativeQuery(jpql);
        return query.getResultList();
    }
}
