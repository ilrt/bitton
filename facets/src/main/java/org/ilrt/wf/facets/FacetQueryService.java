package org.ilrt.wf.facets;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import java.util.List;
import java.util.Map;

/**
 *
 * Warning! This interface is likely to change as Damian and Mike pull their respective hair out!
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 * @author Damian Steer (d.steer.@bristol.ac.uk)
 */
public interface FacetQueryService {

    /**
     * We want to find all the possible refinements for a facet state. For example, in a SKOS
     * hierarchy that represents countries, if the the current facet state represented the
     * United Kingdom, we would expect refinements for England, Northern Ireland, Scotland and
     * Wales.
     *
     * @param currentFacetState     the current state of a facet.
     * @return                      the possible further refinements to a facet,
     */
    Map<FacetState, List<RDFNode>> getRefinements(FacetState currentFacetState);

    /**
     * Return the number of matches for future facet states - for example, there my be
     * 8 conferences being held in Bristol. By separating the count from the refinement
     * method call, it does allow the option to configure applications not to
     * produce counts since they can be an expensive operation.
     *
     * @param currentFacetStates    the current state of all facets
     * @return                      the future states and their associated count.
     */
    Map<FacetState, Integer> getCounts(List<? extends FacetState> currentFacetStates);

    /**
     * Get the number of resources which fall under the given states.
     *
     * @param currentFacentStates
     * @return The total number of resources which match the current state.
     */
    int getCount(List<? extends FacetState> currentFacentStates);

    /**
     * Get the resources that match the current states.
     *
     * @param currentFacentStates
     * @param offset Start at this point in whole result list
     * @param number Get at most this many results
     * @return
     */
    List<Resource> getResults(List<? extends FacetState> currentFacetStates, int offset, int number);
}
