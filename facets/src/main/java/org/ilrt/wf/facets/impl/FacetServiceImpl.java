package org.ilrt.wf.facets.impl;

import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.FacetService;
import org.ilrt.wf.facets.FacetView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class FacetServiceImpl implements FacetService {

    public FacetServiceImpl(FacetQueryService facetQueryService) {
        this.facetQueryService = facetQueryService;
    }

    @Override
    public FacetView generate(HttpServletRequest request) {
        facetQueryService.getRefinements(null);
        return null;
    }


    final FacetQueryService facetQueryService;
}
