package org.ilrt.wf.facets.impl;

import org.ilrt.wf.facets.FacetEnvironment;

import java.util.Map;

public class FacetEnvironmentImpl implements FacetEnvironment {

    public FacetEnvironmentImpl(Map<String, String> config, Map<String, String[]> parameters,
                                Map<String, String> prefixes) {
        this.config = config;
        this.parameters = parameters;
        this.prefixes = prefixes;
    }

    @Override
    public Map<String, String> getConfig() {
        return config;
    }

    @Override
    public Map<String, String[]> getParameters() {
        return parameters;
    }

    @Override
    public Map<String, String> getPrefix() {
        return prefixes;
    }

    private Map<String, String> config;
    private Map<String, String[]> parameters;
    private Map<String, String> prefixes;
}
