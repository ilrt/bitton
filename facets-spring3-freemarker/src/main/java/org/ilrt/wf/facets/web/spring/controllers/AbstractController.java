package org.ilrt.wf.facets.web.spring.controllers;

import org.springframework.web.servlet.ModelAndView;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public abstract class AbstractController {

    ModelAndView createModelAndView() {
        ModelAndView mav = new ModelAndView();

        return mav;

    }



    public static String CONTEXT_PATH_KEY = "contextPath";
    public static String FACET_VIEW_KEY = "facetView";
}
