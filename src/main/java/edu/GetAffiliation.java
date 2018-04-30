
package edu;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        response.addHeader("Access-Control-Allow-Origin", "*");
        //--
        List<Object[]> list = findAllAffiliates();
        //
        ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode json = objectMapper.valueToTree(list);
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
