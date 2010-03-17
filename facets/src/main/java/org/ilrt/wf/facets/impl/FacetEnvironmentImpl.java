package org.ilrt.wf.facets.impl;

import org.ilrt.wf.facets.FacetEnvironment;

import java.util.Map;

public class FacetEnvironmentImpl implements FacetEnvironment {

    public FacetEnvironmentImpl(Map<String, String> config, Map<String, String[]> parameters) {
        this.config = config;
        this.parameters = parameters;
    }

    @Override
    public Map<String, String> getConfig() {
        return config;
    }

    @Override
    public Map<String, String[]> getParameters() {
        return parameters;
    }

    private Map<String, String> config;
    private Map<String, String[]> parameters;
}
