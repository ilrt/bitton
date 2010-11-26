package org.ilrt.wf.facets.ui.widgets.controllers;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import org.apache.log4j.Logger;
import org.ilrt.wf.facets.FacetViewService;
import org.ilrt.wf.facets.FacetViewServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.ilrt.wf.facets.FacetQueryService;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
@Controller
public class TopFacetController {

//    @Autowired
//    public TopFacetController() {};

    // ---------- public methods that are mapped to URLs

    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public ModelAndView mainView(HttpServletRequest request) throws FacetViewServiceException {

        // get the session object
        HttpSession session = request.getSession(true);

        // do a fresh query the service
        ModelAndView mav = createModelAndView(TOP_FACET_VIEW_NAME, request);

        mav.addObject("Test", "AABBCC");
        
        return mav;
    }

    ModelAndView createModelAndView(String name, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(name);
        mav.addObject(CONTEXT_PATH_KEY, request.getContextPath());
        mav.addObject(SERVLET_PATH_KEY, request.getServletPath());
        return mav;
    }

    public static String CONTEXT_PATH_KEY = "contextPath";
    public static String SERVLET_PATH_KEY = "servletPath";

    private FacetViewService facetViewService;
    private FacetQueryService facetQueryService;

    private final String FOAF = "http://xmlns.com/foaf/0.1/";
    private final Property userNameProp = ResourceFactory.createProperty(FOAF, "nick");

    public static String TOP_FACET_VIEW_NAME = "topFacetView";
    public static String DEFAULT_VIEW = "defaultView";


    private final String HOME_PATH = "/";
    private final String VIEW_PATH = "/*";
    private final String ITEM_PATH = "/item";
    private final String ABOUT_PATH = "/about/";
    private final String CONTACT_PATH = "/contact/";
    private final String PROFILE_PATH = "/profile";

    private Logger log = Logger.getLogger(TopFacetController.class);
}