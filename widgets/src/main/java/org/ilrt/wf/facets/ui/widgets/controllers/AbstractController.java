/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.ui.widgets.controllers;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;
import org.slf4j.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author cmcpb
 */
public abstract class AbstractController {
    public static String CONTEXT_PATH_KEY = "contextPath";
    public static String SERVLET_PATH_KEY = "servletPath";
    private static String viewName = "";
    protected Logger log;
    
    /**
     * @return the viewName
     */
    public static String getViewName() {
        return viewName;
    }

    /**
     * @param aViewName the viewName to set
     */
    public static void setViewName(String aViewName) {
        viewName = aViewName;
    }

    private String endpoint = null;

    /**
     * @return the endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * @param endpoint the endpoint to set
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    protected ModelAndView createModelAndView(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject(CONTEXT_PATH_KEY, request.getContextPath());
        mav.addObject(SERVLET_PATH_KEY, request.getServletPath());
        return mav;
    }

    protected ResultSet querySelect(String query, Object[] args) {
        long startTime = System.currentTimeMillis();
        query = String.format(query, args);
        log.info("Query is: {}", query);
        Query q = QueryFactory.create(query, Syntax.syntaxARQ);
        QueryExecution qe = QueryExecutionFactory.sparqlService(endpoint, q);

        // This isn't supported form http engines. Damn
        // qe.setInitialBinding(initialBinding);
        log.info("Query took {}ms", System.currentTimeMillis() - startTime);
        return qe.execSelect();
    }
}
