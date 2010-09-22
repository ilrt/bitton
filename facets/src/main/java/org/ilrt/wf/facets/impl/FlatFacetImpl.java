/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.impl;

import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetEnvironment;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.constraints.ValueConstraint;

/**
 *
 * @author pldms
 */
public class FlatFacetImpl extends AbstractFacetFactoryImpl {

    @Override
    public Facet create(FacetEnvironment environment) throws FacetException {
        FacetState state;
        ValueConstraint typeConstraint = createTypeConstraint(environment.getConfig()
                .get(Facet.CONSTRAINT_TYPE));

        String[] currentVals = environment.getParameters().get(environment.getConfig().get(Facet.PARAM_NAME));

        // TODO -- GET THIS RIGHT
        if (currentVals == null) // The BASE state
            state = new FacetStateImpl();
        else {
            state = new FacetStateImpl();
        }

        return new FacetImpl(getFacetTitle(environment), state, getParameterName(environment));
    }

    

}
