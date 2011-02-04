package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Resource;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetView;

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

    public FacetViewImpl(int total, List<Facet> facets, List<Resource> results) {
        this.total = total;
        this.facets = facets;
        this.results = results;
    }

    @Override
    public int getTotal() {
        return total;
    }
    
    @Override
    public int getTotalPages() {
        return totalPages;
    }
    
    @Override
    public List<Facet> getFacets() {
        return facets;
    }

    @Override
    public List<Resource> getResults() {
        return results;
    }

    @Override
    public int getCurrentPage()
    {
        return currentPage;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }

    public void setFacets(List<Facet> facets) {
        this.facets = facets;
    }

    public void setResults(List<Resource> results) {
        this.results = results;
    }
    
    public void calculateCurrentPage(int offset, int number)
    {
        if (offset == 0 || number == 0) currentPage = 1;
        else
        {
            currentPage = (int)Math.floor(offset / number)+1;
        }
        totalPages = (int)Math.ceil((double)total / (double)number);
    }
    
    private int currentPage = 1;
    private int total;
    private int totalPages = 0;
    private List<Facet> facets = new ArrayList<Facet>();
    private List<Resource> results = new ArrayList<Resource>();
}
