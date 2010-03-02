package org.ilrt.wf.facets.impl;

import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetConstraint;
import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.FacetService;
import org.ilrt.wf.facets.FacetView;
import org.ilrt.wf.facets.config.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class FacetServiceImpl implements FacetService {

    public FacetServiceImpl(FacetQueryService facetQueryService, Configuration configuration) {
        this.facetQueryService = facetQueryService;
        this.configuration = configuration;
    }

    @Override
    public FacetView generate(HttpServletRequest request) {

        // The view that will be returned
        // TODO when the code settles down, re-factor to add all data via constructor rather than setter methods
        FacetView facetView = new FacetViewImpl();

        // go through configurations
        for (String key : configuration.configKeys()) {

            Map<String, String> config = configuration.getConfiguration(key);

            String facetType = config.get(Facet.FACET_TYPE);

            FacetConstraint constraint = new FacetConstraint(config, request.getParameterMap());

            if (facetType.equals(Facet.ALPHA_NUMERIC_FACET_TYPE)) {
                Facet facet =  AlphaNumericFacetFactory.getInstance().create(constraint);
                facetView.getFacets().add(facet);
            }

        }


//        facetQueryService.getRefinements(null);

        return facetView;
    }


    protected boolean hasParameterValue(Map<String, String> config, HttpServletRequest request) {
        return request.getParameter(config.get(Facet.PARAM_NAME)) == null;
    }

    final FacetQueryService facetQueryService;
    final Configuration configuration;
}
