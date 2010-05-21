package org.ilrt.wf.facets.web.spring.controllers;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import org.apache.log4j.Logger;
import org.ilrt.wf.facets.FacetView;
import org.ilrt.wf.facets.FacetViewService;
import org.ilrt.wf.facets.FacetViewServiceException;
import org.ilrt.wf.facets.freemarker.FacetViewFreeMarkerWrapper;
import org.ilrt.wf.facets.freemarker.ResourceHashModel;
import org.ilrt.wf.facets.session.FacetViewSessionWrapper;
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

    // ---------- public methods that are mapped to URLs

    @RequestMapping(value = DEFAULT_PATH, method = RequestMethod.GET)
    public ModelAndView mainView(HttpServletRequest request) throws FacetViewServiceException {

        // get the session object
        HttpSession session = request.getSession(true);

        if (request.getParameter(DRILL_PARAMETER) != null) {
            return displayResource(session, request);
        }

        log.debug("Displaying results");

        // do a fresh query the service
        ModelAndView mav = createModelAndView(MAIN_VIEW_NAME, request);
        FacetView facetView = facetViewService.generate(request);
        session.setAttribute(FACETVIEW_SESSION, new FacetViewSessionWrapper(facetView));
        mav.addObject(FACET_VIEW_KEY, new FacetViewFreeMarkerWrapper(facetView));

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


    // ---------- private methods

    private FacetViewSessionWrapper createWrapper(HttpSession session, HttpServletRequest request)
            throws FacetViewServiceException {

        FacetView facetView = facetViewService.generate(request);
        FacetViewSessionWrapper wrapper = new FacetViewSessionWrapper(facetView);
        session.setAttribute(FACETVIEW_SESSION, wrapper);
        return new FacetViewSessionWrapper(facetView);
    }

    private ModelAndView displayResourceOrFail(FacetViewSessionWrapper wrapper, String uri,
                                               HttpServletRequest request) {

        Resource resource = wrapper.get(uri);

        if (resource == null) {
            throw new NotFoundException("Unable to find the requested resource");
        } else {
            ModelAndView mav = createModelAndView(resolveViewForResource(resource), request);
            mav.addObject("resource", new ResourceHashModel(resource));
            return mav;
        }
    }


    private ModelAndView displayResource(HttpSession session, HttpServletRequest request)
            throws FacetViewServiceException {

        String uri = request.getParameter(DRILL_PARAMETER);

        log.debug("Requesting to view resource: " + uri);

        FacetViewSessionWrapper wrapper =
                (FacetViewSessionWrapper) session.getAttribute(FACETVIEW_SESSION);

        if (wrapper != null) {

            log.debug("We have a FacetViewSessionWrapper instance on the session");

            Resource resource = wrapper.get(uri);

            if (resource != null) {

                log.debug("Found resource");

                ModelAndView mav = createModelAndView(resolveViewForResource(resource), request);
                mav.addObject("resource", new ResourceHashModel(resource));
                return mav;
            } else {

                log.debug("Cannot find the resource on the current session. Refreshing");

                FacetViewSessionWrapper freshWrapper = createWrapper(session, request);
                return displayResourceOrFail(freshWrapper, uri, request);
            }

        } else {

            log.debug("We do not have a FacetViewSessionWrapper instance on the session. Refreshing");

            FacetViewSessionWrapper freshWrapper = createWrapper(session, request);
            return displayResourceOrFail(freshWrapper, uri, request);
        }
    }

    private String resolveViewForResource(Resource resource) {

        if (resource.hasProperty(RDF.type)) {

            String type = resource.getProperty(RDF.type).getResource().getURI();

            if (type.equals("http://vocab.ouls.ox.ac.uk/projectfunding/projectfunding#Grant")) {
                return GRANT_VIEW_NAME;
            }

        }

        return DEFAULT_VIEW;
    }

    private FacetViewService facetViewService;

    public static String MAIN_VIEW_NAME = "mainView";
    public static String ABOUT_VIEW_NAME = "aboutView";
    public static String CONTACT_VIEW_NAME = "contactView";
    public static String GRANT_VIEW_NAME = "grantView";
    public static String DEFAULT_VIEW = "defaultView";

    private final String FACETVIEW_SESSION = "facetView";
    private final String DRILL_PARAMETER = "drill";

    private final String DEFAULT_PATH = "/";
    private final String ABOUT_PATH = "/about/";
    private final String CONTACT_PATH = "/contact/";

    private Logger log = Logger.getLogger(ResRevController.class);
}