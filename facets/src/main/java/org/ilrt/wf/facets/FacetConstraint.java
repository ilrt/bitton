package org.ilrt.wf.facets;

import java.util.Map;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public interface FacetConstraint {
    
    Map<String, String> getConfig();

    Map getParameters();
}
