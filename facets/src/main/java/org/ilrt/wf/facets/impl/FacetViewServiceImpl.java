package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Resource;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetEnvironment;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetFactory;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.FacetView;
import org.ilrt.wf.facets.FacetViewService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class FacetViewServiceImpl implements FacetViewService {

    public FacetViewServiceImpl(FacetFactory facetFactory,
                                List<Map<String, String>> configurationList,
                                Map<String, String> prefixes) {
        this.facetFactory = facetFactory;
        this.configurationList = configurationList;
        this.prefixes = prefixes;
    }

    @Override
    public FacetView generate(HttpServletRequest request) throws FacetException {

        // the view that will be returned
        FacetViewImpl facetView = new FacetViewImpl();

        // ---------- facet creation

        // holds facets to be displayed for the request
        List<Facet> facets = new ArrayList<Facet>();

        // iterate through facet configurations
        for (Map<String, String> configuration : configurationList) {

            // the facet is affected by its configuration and possibly request parameters
            FacetEnvironment environment = environment(configuration, request);

            // get the facet via the factory and add to the list
            facetView.getFacets().add(facetFactory.create(environment));
        }

        // get all of the current states
        List<FacetState> states = currentStates(facets);

        // get the counts
        facetFactory.calculateCount(states);

        // add the facets to the view
        facetView.setFacets(facets);

        // ---------- results list

        // TODO handle index and off set from parameter values
        List<Resource> results = facetFactory.results(states, 0, 10);
        facetView.setResults(results);

        // ---------- add the total count

        int total = facetFactory.totalResults(states);
        facetView.setTotal(total);

        return facetView;
    }

    /**
     * Query Service wants the current facet states from all of the facets - we decompose the
     * list of facets to get the states.
     *
     * @param facetList list of facets
     * @return a list of facet states from the facets
     */
    private List<FacetState> currentStates(List<Facet> facetList) {

        List<FacetState> states = new ArrayList<FacetState>();

        for (Facet facet : facetList) {
            states.add(facet.getState());
        }

        return states;
    }

    @SuppressWarnings(value = "unchecked")
    private FacetEnvironment environment(Map<String, String> configuration,
                                         HttpServletRequest request) {
        return new FacetEnvironmentImpl(configuration, request.getParameterMap(), prefixes);
    }

    final FacetFactory facetFactory;
    final List<Map<String, String>> configurationList;
    final Map<String, String> prefixes;
}
