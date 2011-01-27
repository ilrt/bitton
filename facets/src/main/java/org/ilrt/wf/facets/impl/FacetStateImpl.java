package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.constraints.Constraint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class FacetStateImpl implements FacetState {

    public FacetStateImpl() {
    }

    public FacetStateImpl(String name, FacetState parent, String paramValue,
                          List<? extends Constraint> constraint) {

        this.name = name;
        this.parent = parent;
        this.paramValue = paramValue;

        if (constraint != null) {
            this.constraint.addAll(constraint);
        }
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
    public Collection<Constraint> getConstraints() {
        return constraint;
    }
    
    @Override
    public boolean isCountable()
    {
        return isCountable;
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

    public void setCountable(boolean b)
    {
        this.isCountable = b;
    }

    private String name = "Untitled";
    private int count = 0;
    private boolean isRoot = false;
    private boolean isCountable = true;
    private List<FacetState> refinements = new ArrayList<FacetState>();
    private FacetState parent = null;
    private String paramValue = null;
    private LinkedList<Constraint> constraint = new LinkedList<Constraint>();
}
