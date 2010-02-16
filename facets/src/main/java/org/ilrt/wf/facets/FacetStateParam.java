package org.ilrt.wf.facets;

/**
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class FacetStateParam {

    public FacetStateParam(String paramName, String paramValue) {
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    public String getParamName() {
        return paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    final private String paramName;

    final private String paramValue;
}
