package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.constraints.Constraint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class FacetStateImpl implements FacetState {

    public FacetStateImpl() {
    }

    public FacetStateImpl(String name, FacetState parent, String paramValue,
                          Set<Constraint> constraint) {
        this.name = name;
        this.parent = parent;
        this.paramValue = paramValue;
        this.constraint = constraint;
    }

    // ---------- getter methods defined in the interface

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isRoot() {
        return isRoot;
    }

    @Override
    public List<FacetState> getRefinements() {
        return refinements;
    }

    @Override
    public FacetState getParent() {
        return parent;
    }

    @Override
    public String getParamValue() {
        return paramValue;
    }

    @Override
    public Property getBroaderProperty() {
        return broaderProperty;
    }

    @Override
    public Property getNarrowerProperty() {
        return narrowerProperty;
    }

    @Override
    public RDFNode getValue() {
        return value;
    }

    @Override
    public Collection<Constraint> getConstraints() {
        return constraint;
    }

    // ---------- setter methods provided in the class implementation

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public void setRefinements(List<FacetState> refinements) {
        this.refinements = refinements;
    }

    public void setParent(FacetState parent) {
        this.parent = parent;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public void setBroaderProperty(Property broaderProperty) {
        this.broaderProperty = broaderProperty;
    }

    public void setNarrowerProperty(Property narrowerProperty) {
        this.narrowerProperty = narrowerProperty;
    }

    public void setValue(RDFNode value) {
        this.value = value;
    }

    public void setConstraints(Set<Constraint> constraint) {
        this.constraint = constraint;
    }

    private String name = "Untitled";
    private int count = 0;
    private boolean isRoot = false;
    private List<FacetState> refinements = new ArrayList<FacetState>();
    private FacetState parent = null;
    private String paramValue = null;
    private Property broaderProperty = null;
    private Property narrowerProperty = null;
    private RDFNode value;
    private Set<Constraint> constraint = null;
}
