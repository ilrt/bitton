package org.ilrt.wf.facets;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class FacetViewServiceException extends Exception {

    public FacetViewServiceException(Exception ex) {
        super(ex);
    }

    public FacetViewServiceException(String message) {
        super(message);
    }
}
