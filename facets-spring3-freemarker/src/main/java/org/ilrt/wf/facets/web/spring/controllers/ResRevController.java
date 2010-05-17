package org.ilrt.wf.facets.web.spring.controllers;

import com.hp.hpl.jena.rdf.model.Resource;
import org.apache.log4j.Logger;
import org.ilrt.wf.facets.FacetView;
import org.ilrt.wf.facets.FacetViewService;
import org.ilrt.wf.facets.FacetViewServiceException;
import org.ilrt.wf.facets.freemarker.FacetViewFreeMarkerWrapper;
import org.ilrt.wf.facets.freemarker.ResourceHashModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
@Controller
public class ResRevController extends AbstractController {

    @Autowired
    public ResRevController(final FacetViewService facetViewService) {
        this.facetViewService = facetViewService;
    }

    @RequestMapping(value = DEFAULT_PATH, method = RequestMethod.GET)
    public ModelAndView mainView(HttpServletRequest request) throws FacetViewServiceException {

        HttpSession session = request.getSession(true);

        if (request.getParameter(DRILL_PARAMETER) != null) {

            FacetView view = (FacetView) session.getAttribute(FACETVIEW_SESSION);

            if (view != null) {

                int drill = Integer.parseInt(request.getParameter(DRILL_PARAMETER));

                if (drill < view.getResults().size()) {

                    Resource resource = view.getResults().get(drill);

                    log.debug("Request to view " + resource.getURI());

                    ModelAndView mav = createModelAndView(GRANT_VIEW_NAME, request);
                    mav.addObject("resource", new ResourceHashModel(resource));
                    return mav;
                }
            }

        }

        ModelAndView mav = createModelAndView(MAIN_VIEW_NAME, request);
        FacetView facetView = facetViewService.generate(request);
        session.setAttribute(FACETVIEW_SESSION, facetView);
        mav.addObject(FACET_VIEW_KEY,
                new FacetViewFreeMarkerWrapper(facetViewService.generate(request)));
        return mav;
    }

    @RequestMapping(value = ABOUT_PATH, method = RequestMethod.GET)
    public ModelAndView aboutView(HttpServletRequest request) throws FacetViewServiceException {

        return createModelAndView(ABOUT_VIEW_NAME, request);
    }

    @RequestMapping(value = CONTACT_PATH, method = RequestMethod.GET)
    public ModelAndView contactView(HttpServletRequest request) throws FacetViewServiceException {

        return createModelAndView(CONTACT_VIEW_NAME, request);
    }

    private FacetViewService facetViewService;

    public static String MAIN_VIEW_NAME = "mainView";
    public static String ABOUT_VIEW_NAME = "aboutView";
    public static String CONTACT_VIEW_NAME = "contactView";
    public static String GRANT_VIEW_NAME = "grantView";

    private final String FACETVIEW_SESSION = "facetView";
    private final String DRILL_PARAMETER = "drill";

    private final String DEFAULT_PATH = "/";
    private final String ABOUT_PATH = "/about/";
    private final String CONTACT_PATH = "/contact/";

    private Logger log = Logger.getLogger(ResRevController.class);
}