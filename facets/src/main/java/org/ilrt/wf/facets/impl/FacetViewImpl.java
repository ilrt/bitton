package org.ilrt.wf.facets.impl;

import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetView;
import org.ilrt.wf.facets.SearchFilter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 * @author Damian Steer (d.steer.@bristol.ac.uk)
 */
public class FacetViewImpl implements FacetView {

    public FacetViewImpl() {
    }

    public FacetViewImpl(int total, List<Facet> facets, SearchFilter searchFilter) {
        this.total = total;
        this.facets = facets;
        this.searchFilter = searchFilter;
    }

    @Override
    public int getTotal() {
        return total;
    }

    @Override
    public List<Facet> getFacets() {
        return facets;
    }

    @Override
    public SearchFilter getSearchFilter() {
        return searchFilter;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setFacets(List<Facet> facets) {
        this.facets = facets;
    }

    public void setSearchFilter(SearchFilter searchFilter) {
        this.searchFilter = searchFilter;
    }

    private int total;
    private List<Facet> facets = new ArrayList<Facet>();
    private SearchFilter searchFilter;
}
