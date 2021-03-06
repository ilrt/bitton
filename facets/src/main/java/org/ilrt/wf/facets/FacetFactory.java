package org.ilrt.wf.facets;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public interface FacetFactory {

    Facet create(FacetEnvironment environment) throws FacetException;

}
