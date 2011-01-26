/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import java.util.Collections;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetEnvironment;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.QNameUtility;
import org.ilrt.wf.facets.constraints.Constraint;
import org.ilrt.wf.facets.constraints.ValueConstraint;

/**
 *
 * @author pldms
 */
public class TextSearchFacetImpl extends AbstractFacetFactoryImpl {
    
    final static Property TEXTMATCH = ResourceFactory.createProperty("http://jena.hpl.hp.com/ARQ/property#", "textMatch");
    
    private final FacetQueryService facetQueryService;
    private final QNameUtility qNameUtility;

    public TextSearchFacetImpl(FacetQueryService facetQueryService, QNameUtility qNameUtility) {
        this.facetQueryService = facetQueryService;
        this.qNameUtility = qNameUtility;
    }

    @Override
    public Facet create(FacetEnvironment environment) throws FacetException {
        FacetStateImpl state;
        String type = environment.getConfig().get(Facet.CONSTRAINT_TYPE);
        //String property = environment.getConfig().get(Facet.LINK_PROPERTY);
        boolean requireLabel = true;
        //Property prop = ResourceFactory.createProperty(property);
        String param = environment.getConfig().get(Facet.PARAM_NAME);
        ValueConstraint typeConstraint = createTypeConstraint(type);

        String[] currentVals = environment.getParameters().get(environment.getConfig().get(Facet.PARAM_NAME));

        
        FacetStateImpl baseState = new FacetStateImpl();
        
        if (currentVals ==null || currentVals.length == 0) state = baseState;
        else {
            Constraint c = new ValueConstraint(TEXTMATCH, ResourceFactory.createPlainLiteral(currentVals[0]));
            state = new FacetStateImpl(currentVals[0], baseState, currentVals[0], Collections.singletonList(c));
        }

        return new FacetImpl(getFacetTitle(environment), state, getParameterName(environment), "search");
    }
}
  