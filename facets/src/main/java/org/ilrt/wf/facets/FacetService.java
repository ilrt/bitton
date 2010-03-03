package org.ilrt.wf.facets;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 * @author Damian Steer (d.steer.@bristol.ac.uk)
 */
public interface FacetService {

    FacetView generate(HttpServletRequest request) throws FacetException;

}
