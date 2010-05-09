package org.ilrt.wf.facets.web.spring.controllers;

import org.ilrt.wf.facets.FacetView;
import org.ilrt.wf.facets.FacetViewService;
import org.ilrt.wf.facets.FacetViewServiceException;
import org.ilrt.wf.facets.freemarker.FacetParentListMethod;
import org.ilrt.wf.facets.freemarker.FacetStateUrlMethod;
import org.ilrt.wf.facets.freemarker.FacetViewFreeMarkerWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
@Controller
public class ResRevController extends AbstractController {

    @Autowired
    public ResRevController(final FacetViewService facetViewService) {
        this.facetViewService = facetViewService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView mainView(HttpServletRequest request) throws FacetViewServiceException {

        FacetView facetView = facetViewService.generate(request);

        ModelAndView mav = new ModelAndView(VIEW_NAME);
        mav.addObject(CONTEXT_PATH_KEY, request.getContextPath());
        mav.addObject(FACET_VIEW_KEY, new FacetViewFreeMarkerWrapper(facetView));
        //mav.addObject(FACET_VIEW_KEY, facetView);
        mav.addObject("facetStateUrl", new FacetStateUrlMethod());
        mav.addObject("facetParentList", new FacetParentListMethod());
        return mav;
    }

    private FacetViewService facetViewService;

    public static String VIEW_NAME = "mainView";
}