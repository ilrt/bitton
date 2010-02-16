package org.ilrt.wf.facets;

import java.util.List;

/**
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class FacetView {

    public FacetView(int total, List<Facet> facets, SearchFilter searchFilter) {
        this.total = total;
        this.facets = facets;
        this.searchFilter = searchFilter;
    }

    public int getTotal() {
        return total;
    }

    public List<Facet> getFacets() {
        return facets;
    }

    public SearchFilter getSearchFilter() {
        return searchFilter;
    }

    final int total;
    final List<Facet> facets;
    final SearchFilter searchFilter;
}
