package org.ilrt.wf.facets.impl;

import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetState;


public class FacetImpl implements Facet {

    public FacetImpl(String name, FacetState state, String param, String type) {
        this.name = name;
        this.state = state;
        this.param = param;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public FacetState getState() {
        return state;
    }

    @Override
    public String getParam() {
        return param;
    }

    @Override
    public String getType() {
        return type;
    }

    private String type;
    private String name;
    private FacetState state;
    private String param;
}
