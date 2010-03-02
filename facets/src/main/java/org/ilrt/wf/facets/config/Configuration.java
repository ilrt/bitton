package org.ilrt.wf.facets.config;

import java.util.Map;
import java.util.Set;

/**
 * Holds the configuration details of the facets. A wrapper that manages the contents of a Map.
 *
 * Sits over Map<String, Map<String, String>>
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public interface Configuration {

    Set<String> configKeys();

    Map<String, String> getConfiguration(String key);

}