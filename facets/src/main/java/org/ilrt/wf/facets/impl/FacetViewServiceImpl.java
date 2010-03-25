package org.ilrt.wf.facets.impl;

import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetEnvironment;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetFactory;
import org.ilrt.wf.facets.FacetView;
import org.ilrt.wf.facets.FacetViewService;
import org.ilrt.wf.facets.config.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class FacetViewServiceImpl implements FacetViewService {

    public FacetViewServiceImpl(FacetFactory facetFactory, Configuration configuration, Map<String, String> prefixes) {
        this.facetFactory = facetFactory;
        this.configuration = configuration;
        this.prefixes = prefixes;
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
            FacetEnvironment environment =
                    new FacetEnvironmentImpl(configuration.getConfiguration(key),
                            request.getParameterMap(), prefixes);

            // get the facet via the factory and add to the list
            facetView.getFacets().add(facetFactory.create(environment));
        }

        // get the counts
        facetFactory.calculateCount(facets);

        // add the facets to the view
        facetView.getFacets().addAll(facets);

        return facetView;
    }

    final FacetFactory facetFactory;
    final Configuration configuration;
    final Map<String, String> prefixes;


}
