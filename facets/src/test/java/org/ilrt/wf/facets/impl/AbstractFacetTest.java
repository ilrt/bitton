package org.ilrt.wf.facets.impl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public abstract class AbstractFacetTest {

    Map<String, String> getPrefixMap() {

        Map<String, String> map = new HashMap<String, String>();
        map.put(foafPrefix, foafUri);
        map.put(exPrefix, exUri);
        return map;
    }

    private final String foafPrefix = "foaf";
    private final String foafUri = "http://xmlns.com/foaf/0.1/Person";

    private final String exPrefix = "ex";
    private final String exUri = "http://example.org/";
}
