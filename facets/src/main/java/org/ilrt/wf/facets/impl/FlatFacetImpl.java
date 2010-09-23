/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
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
public class FlatFacetImpl extends AbstractFacetFactoryImpl {
    private final FacetQueryService facetQueryService;
    private final QNameUtility qNameUtility;

    public FlatFacetImpl(FacetQueryService facetQueryService, QNameUtility qNameUtility) {
        this.facetQueryService = facetQueryService;
        this.qNameUtility = qNameUtility;
    }

    @Override
    public Facet create(FacetEnvironment environment) throws FacetException {
        FacetStateImpl state;
        String type = environment.getConfig().get(Facet.CONSTRAINT_TYPE);
        String property = environment.getConfig().get(Facet.LINK_PROPERTY);
        Property prop = ResourceFactory.createProperty(property);
        String param = environment.getConfig().get(Facet.PARAM_NAME);
        ValueConstraint typeConstraint = createTypeConstraint(type);

        String[] currentVals = environment.getParameters().get(environment.getConfig().get(Facet.PARAM_NAME));

        // TODO -- GET THIS RIGHT
        if (currentVals == null) { // The root state
            Collection<RDFNode> vals = facetQueryService.getValuesOfPropertyForType(
                    ResourceFactory.createResource(type),
                    prop,
                    true);

            state = new FacetStateImpl("Base", null, null, Collections.singletonList(typeConstraint));
            state.setRoot(true);
            List<FacetState> refinements = new ArrayList(vals.size());
            for (RDFNode val: vals) {
                ValueConstraint valConstraint = new ValueConstraint(prop, val);
                FacetState refine = new FacetStateImpl(
                        val.toString(),
                        state,
                        toParamVal(val),
                        Arrays.asList(typeConstraint, valConstraint));
                refinements.add(refine);
            }
            state.setRefinements(refinements);

        } else {
            FacetStateImpl bState = new FacetStateImpl("Base", null, null, Collections.singletonList(typeConstraint));
            bState.setRoot(true);
            RDFNode val = fromParamVal(currentVals[0]);
            ValueConstraint valConstraint = new ValueConstraint(prop, val);
            state = new FacetStateImpl(
                    val.toString(),
                    bState,
                    toParamVal(val),
                    Arrays.asList(typeConstraint, valConstraint)
                    );
        }

        return new FacetImpl(getFacetTitle(environment), state, getParameterName(environment));
    }

    private String toParamVal(RDFNode node) {
        if (node.isLiteral()) return "L" + ((Literal) node).getLexicalForm();
        else if (node.isURIResource())
            return "U" + qNameUtility.getQName(((Resource) node).getURI());
        else return "B" + ((Resource) node).getId().getLabelString();
    }

    private RDFNode fromParamVal(String val) {
        if (val.startsWith("U")) return ResourceFactory.createResource(qNameUtility.expandQName(val.substring(1)));
        // Erm, what should we do here? Fail?
        else if (val.startsWith("B")) return ResourceFactory.createResource();
        else return ResourceFactory.createPlainLiteral(val.substring(1));
    }
}
