package org.ilrt.wf.facets.impl;

import org.ilrt.wf.facets.FacetEnvironment;

import java.util.Map;

public class FacetEnvironmentImpl implements FacetEnvironment {

    public FacetEnvironmentImpl(Map<String, String> config, Map parameters) {
        this.config = config;
        this.parameters = parameters;
    }

    @Override
    public Map<String, String> getConfig() {
        return config;
    }

    @Override
    public Map getParameters() {
        return parameters;
    }

    private Map<String, String> config;
    private Map parameters;
}
