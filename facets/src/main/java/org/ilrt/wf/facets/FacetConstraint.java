package org.ilrt.wf.facets;

import java.util.Map;

/**
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 * @author Damian Steer (d.steer.@bristol.ac.uk)
 */
public class FacetConstraint {
    public FacetConstraint(Map<String, String> config, Map parameters) {
        this.config = config;
        this.parameters = parameters;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

    private Map<String, String> config;
    private Map parameters;
}
