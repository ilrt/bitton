package org.ilrt.wf.facets;

import com.hp.hpl.jena.rdf.model.Resource;

import java.util.List;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public interface FacetFactory {

    Facet create(FacetEnvironment environment) throws FacetException;

    List<Resource> results(List<FacetState> states, int offset, int number);

    int totalResults(List<FacetState> states);

    void calculateCount(List<FacetState> states);
}
