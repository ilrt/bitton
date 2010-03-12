package org.ilrt.wf.facets;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MockFacetQueryService implements FacetQueryService{

    @Override
    public Map<FacetState, List<RDFNode>> getRefinements(FacetState currentFacetState) {
        return null;
    }

    @Override
    public Map<FacetState, Integer> getCounts(List<? extends FacetState> currentFacetStates) {

        Map<FacetState, Integer> results = new HashMap<FacetState, Integer>();

        // go through root states to get refinements
        for (FacetState facetState : currentFacetStates) {
            for (FacetState refinement : facetState.getRefinements()) {
                // set everything with a count of 5
                results.put(refinement, 5);
            }
        }

        return results;
    }

    @Override
    public int getCount(List<? extends FacetState> currentFacetStates) {
        return 0;
    }

    @Override
    public List<Resource> getResults(List<? extends FacetState> currentFacetStates, int offset, int number) {
        return null;
    }
}
