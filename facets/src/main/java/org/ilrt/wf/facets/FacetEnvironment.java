package org.ilrt.wf.facets;

import java.util.Map;

/**
 * A wrapper class that provides access to information that is used
 * when constructing the facets.
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public interface FacetEnvironment {
    
    Map<String, String> getConfig();

    Map<String, String[]> getParameters();
}
