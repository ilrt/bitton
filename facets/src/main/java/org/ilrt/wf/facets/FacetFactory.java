package org.ilrt.wf.facets;

import java.util.List;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public interface FacetFactory {

    Facet create(FacetConstraint constraint) throws FacetException;

    void calculateCount(List<Facet> facetList);
}
