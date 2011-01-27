package org.ilrt.wf.facets;

import org.ilrt.wf.facets.constraints.Constraint;

import java.util.Collection;
import java.util.List;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 * @author Damian Steer (d.steer.@bristol.ac.uk)
 */
public interface FacetState {

    String getName();

    int getCount();

    boolean isRoot();

    List<FacetState> getRefinements();

    FacetState getParent();

    String getParamValue();

    boolean isCountable();

    /**
     * @return How this state constrains values
     */
    Collection<Constraint> getConstraints();
}
