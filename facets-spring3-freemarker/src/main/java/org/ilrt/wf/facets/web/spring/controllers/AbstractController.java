package org.ilrt.wf.facets.web.spring.controllers;

import org.ilrt.wf.facets.freemarker.FacetParentListMethod;
import org.ilrt.wf.facets.freemarker.FacetStateUrlMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public abstract class AbstractController {

    ModelAndView createModelAndView(String name, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(name);
        mav.addObject(CONTEXT_PATH_KEY, request.getContextPath());
        mav.addObject(SERVLET_PATH_KEY, request.getServletPath());
        mav.addObject("facetStateUrl", new FacetStateUrlMethod());
        mav.addObject("facetParentList", new FacetParentListMethod());
        return mav;
    }

    public static String CONTEXT_PATH_KEY = "contextPath";
    public static String SERVLET_PATH_KEY = "servletPath";
    public static String FACET_VIEW_KEY = "facetView";
}
