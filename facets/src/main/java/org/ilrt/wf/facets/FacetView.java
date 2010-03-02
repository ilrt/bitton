package org.ilrt.wf.facets;

import java.util.List;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 * @author Damian Steer (d.steer.@bristol.ac.uk)
 */
public interface FacetView {

    int getTotal();

    List<Facet> getFacets();

    SearchFilter getSearchFilter();
}
