package org.ilrt.wf.facets;

/**
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 * @author Damian Steer (d.steer.@bristol.ac.uk)
 */
public interface Facet {

    String getName();

    Facet getState();

    String getParam();
}
