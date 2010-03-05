package org.ilrt.wf.facets.impl;

import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetState;


public class FacetImpl implements Facet {

    public FacetImpl(String name, FacetState state, String param) {
        this.name = name;
        this.state = state;
        this.param = param;
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

    private String name;
    private FacetState state;
    private String param;
}
