package org.ilrt.wf.facets;

import com.hp.hpl.jena.rdf.model.Resource;

import java.util.List;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 * @author Damian Steer (d.steer.@bristol.ac.uk)
 */
public interface FacetView {

    int getTotal();

    int getTotalPages();
    
    int getPageSize();

    List<Facet> getFacets();

    List<Resource> getResults();
    
    int getCurrentPage();
    
}
