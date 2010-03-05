package org.ilrt.wf.facets.impl;

import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetConstraint;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetFactory;
import org.ilrt.wf.facets.FacetViewService;
import org.ilrt.wf.facets.FacetView;
import org.ilrt.wf.facets.config.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class FacetViewServiceImpl implements FacetViewService {

    public FacetViewServiceImpl(FacetFactory facetFactory, Configuration configuration) {
        this.facetFactory = facetFactory;
        this.configuration = configuration;
    }

    @Override
    public FacetView generate(HttpServletRequest request) throws FacetException {

        // the view that will be returned
        FacetView facetView = new FacetViewImpl();

        // holds facets to be displayed for the request
        List<Facet> facets = new ArrayList<Facet>();

        // iterate through facet configurations
        for (String key : configuration.configKeys()) {

            // the facet is affected by its configuration and possibly request parameters
            FacetConstraint constraint =
                    new FacetConstraintImpl(configuration.getConfiguration(key),
                            request.getParameterMap());

            // get the facet via the factory and add to the list
            facetView.getFacets().add(facetFactory.create(constraint));

            // get the counts
            facetFactory.calculateCount(facets);
        }

        // add the facets to the view
        facetView.getFacets().addAll(facets);

        return facetView;
    }

    final FacetFactory facetFactory;
    final Configuration configuration;


    private class FacetConstraintImpl implements FacetConstraint {
        public FacetConstraintImpl(Map<String, String> config, Map parameters) {
            this.config = config;
            this.parameters = parameters;
        }

        @Override
        public Map<String, String> getConfig() {
            return config;
        }

        @Override
        public Map getParameters() {
            return parameters;
        }

        private Map<String, String> config;
        private Map parameters;
    }
}
