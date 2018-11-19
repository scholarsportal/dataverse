/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.harvard.iq.dataverse.affiliation;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author manikons
 */
public class AffiliationServlet extends HttpServlet {
    
    @Inject
    AffiliationServiceBean affiliationServiceBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        String locale = request.getParameter("locale");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Object[]> affiliationList = affiliationServiceBean.getAffiliationsByLocale(locale);
        JsonNode json = objectMapper.valueToTree(affiliationList);
        response.getWriter().println(json);
    }
}
