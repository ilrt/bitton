package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Resource;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetEnvironment;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetFactory;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.FacetView;
import org.ilrt.wf.facets.FacetViewService;
import org.ilrt.wf.facets.FacetViewServiceException;

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
    public FacetView generate(HttpServletRequest request) throws FacetViewServiceException {

        if (configurationList.size() == 0) {
            throw new FacetViewServiceException("There is no registered configuration");
        }

        // calculate the offset and number needed in returning results
        int offset = calculateIntegerFromParam(request.getParameter(OFFSET_PARAM), DEFAULT_OFFSET);
        int number = calculateIntegerFromParam(request.getParameter(NUMBER_PARAM), DEFAULT_NUMBER);


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
            try {
                Facet facet = facetFactory.create(environment);
                facets.add(facet);
            } catch (FacetException ex) {
                throw new FacetViewServiceException(ex);
            }
        }

        // get all of the current states

        List<FacetState> states = currentStates(facets);

        // get the counts

        facetFactory.calculateCount(states);

        // add the facets to the view
        facetView.setFacets(facets);

        // ---------- results list

        // TODO handle index and off set from parameter values
        List<Resource> results = facetFactory.results(states, offset, number);
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


    /**
     * @param value        string value received from http request
     * @param defaultValue default value to return if the string doesn't parse
     * @return an integer representation of the string
     */
    int calculateIntegerFromParam(String value, int defaultValue) {

        if (value == null) {
            return defaultValue;
        }

        try {

            int intValue = Integer.parseInt(value);
            return intValue <= 0 ? defaultValue : intValue;

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return defaultValue;
        }
    }


    final FacetFactory facetFactory;
    final List<Map<String, String>> configurationList;
    final Map<String, String> prefixes;

    final int DEFAULT_OFFSET = 0;
    final int DEFAULT_NUMBER = 10;

    final String NUMBER_PARAM = "number";
    final String OFFSET_PARAM = "offset";

    //final private Logger log = Logger.getLogger(FacetViewServiceImpl.class);
}
