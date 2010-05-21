package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Resource;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetEnvironment;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetFactory;
import org.ilrt.wf.facets.FacetFactoryService;
import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.QNameUtility;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class FacetFactoryServiceImpl implements FacetFactoryService {

    // ---------- public constructors

    public FacetFactoryServiceImpl(FacetQueryService facetQueryService, Map<String, String> prefixMap) {

        this.facetQueryService = facetQueryService;

        QNameUtility qNameUtility = new QNameUtility(prefixMap);
        hierarchicalFacetImpl = new HierarchicalFacetImpl(facetQueryService, qNameUtility);
        alphaNumericFacetImpl = new AlphaNumericFacetImpl();
        simpleNumericRangeFacetImpl = new SimpleNumericRangeFacetImpl();
    }

    // ---------- public methods implementing the interface

    @Override
    public Facet create(FacetEnvironment environment) throws FacetException {

        String facetType = environment.getConfig().get(Facet.FACET_TYPE);

        if (facetType.equals(Facet.ALPHA_NUMERIC_FACET_TYPE)) {
            return alphaNumericFacetImpl.create(environment);
        } else if (facetType.equals(Facet.HIERARCHICAL_FACET_TYPE)) {
            return hierarchicalFacetImpl.create(environment);
        } else if (facetType.equals(Facet.SIMPLE_NUMBER_RANGE_FACET_TYPE)) {
            return simpleNumericRangeFacetImpl.create(environment);
        } else {
            throw new FacetException("Unrecognized facet type: " + facetType);
        }
    }

    @Override
    public List<Resource> results(List<FacetState> states, int offset, int number) {
        return facetQueryService.getResults(states, offset, number);
    }

    @Override
    public int totalResults(List<FacetState> states) {
        return facetQueryService.getCount(states);
    }

    @Override
    public void calculateCount(List<FacetState> states) {

        // request counts

        Map<FacetState, Integer> results = facetQueryService.getCounts(states);

        // update the states with the counts

        Set<FacetState> countStates = results.keySet();

        for (FacetState facetState : countStates) {
            ((FacetStateImpl) facetState).setCount(results.get(facetState));
        }
    }

    private final FacetQueryService facetQueryService;
    private final FacetFactory hierarchicalFacetImpl;
    private final FacetFactory alphaNumericFacetImpl;
    private final FacetFactory simpleNumericRangeFacetImpl;
}
