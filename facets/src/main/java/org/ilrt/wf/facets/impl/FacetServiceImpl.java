package org.ilrt.wf.facets.impl;

import org.ilrt.wf.facets.FacetConstraint;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetFactory;
import org.ilrt.wf.facets.FacetService;
import org.ilrt.wf.facets.FacetView;
import org.ilrt.wf.facets.config.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class FacetServiceImpl implements FacetService {

    public FacetServiceImpl(FacetFactory facetFactory, Configuration configuration) {
        this.facetFactory = facetFactory;
        this.configuration = configuration;
    }

    @Override
    public FacetView generate(HttpServletRequest request) throws FacetException {

        // the view that will be returned
        FacetView facetView = new FacetViewImpl();

        // go through configurations
        for (String key : configuration.configKeys()) {

            FacetConstraint constraint = new FacetConstraintImpl(configuration.getConfiguration(key),
                    request.getParameterMap());
            facetView.getFacets().add(facetFactory.create(constraint));

        }

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

        public void setParameters(Map parameters) {
            this.parameters = parameters;
        }

        private Map<String, String> config;
        private Map parameters;
    }
}
