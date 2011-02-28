/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.vocabulary.RDFS;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetEnvironment;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.QNameUtility;
import com.hp.hpl.jena.rdf.model.RDFNode;
import org.ilrt.wf.facets.constraints.UnionConstraint;
import org.ilrt.wf.facets.constraints.ValueConstraint;

/**
 *
 * @author pldms
 */
public class EnumFlatFacetImpl extends FlatFacetImpl {

    public EnumFlatFacetImpl(FacetQueryService facetQueryService, QNameUtility qNameUtility) {
        super(facetQueryService, qNameUtility);
    }

    @Override
    public Facet create(FacetEnvironment environment) throws FacetException {
        FacetStateImpl state;
        String type = environment.getConfig().get(Facet.CONSTRAINT_TYPE);
        String property = environment.getConfig().get(Facet.LINK_PROPERTY);
        String invertVal = environment.getConfig().get(Facet.LINK_INVERT);
        String requireCountS = environment.getConfig().get(Facet.REQUIRE_COUNTS);
        boolean invert = (invertVal != null && invertVal.equalsIgnoreCase("true"));
        boolean requireCount = (requireCountS != null && requireCountS.equalsIgnoreCase("true"));
        Property prop = ResourceFactory.createProperty(property);
        String param = environment.getConfig().get(Facet.PARAM_NAME);
        String enumList = environment.getConfig().get(Facet.ENUM_LIST);
        UnionConstraint typeConstraint = createTypeConstraint(type);

        String[] enumArray = enumList.split(",");
        String[] currentVals = environment.getParameters().get(param);

        if ( currentVals == null || currentVals.length == 0 ) { // The root state
            state = new FacetStateCollector("Base", null, null, Collections.singletonList(typeConstraint));
            state.setSortRefinements(getOrder(environment));
            
            ((FacetStateCollector) state).setProperty(prop);
            ((FacetStateCollector) state).setInvert(invert);
            state.setRoot(true);
            List<FacetState> refinements = new ArrayList(enumArray.length);

            for (String s : enumArray)
            {
                try
                {
                    RDFNode val = fromEnumParamVal(s);
                    ValueConstraint valConstraint = new ValueConstraint(prop, val, invert);
                    FacetStateImpl refine = new FacetStateImpl(
                            getLabel(val),
                            state,
                            toParamVal(val),
                            Arrays.asList(typeConstraint, valConstraint));
                    refine.setCountable(requireCount);
                    refinements.add(refine);
                }
                catch (StringIndexOutOfBoundsException sioobe) {}
            }

            state.setRefinements(refinements);
            state.setCountable(false);
        }
        else
        {
            FacetStateImpl bState = new FacetStateImpl("Base", null, null, Collections.singletonList(typeConstraint));
            bState.setRoot(true);
            RDFNode val = fromParamVal(currentVals[0]);
            ValueConstraint valConstraint = new ValueConstraint(prop, val,invert);
            state = new FacetStateImpl(
                    getLabel(val),
                    bState,
                    toParamVal(val),
                    Arrays.asList(typeConstraint, valConstraint)
                    );
        }

        return new FacetImpl(getFacetTitle(environment), state, getParameterName(environment), Facet.ENUM_FLAT_FACET);
    }

    protected RDFNode fromEnumParamVal(String s) {
        String uri = s.substring(0,s.indexOf("|"));
        String label = s.substring(s.indexOf("|")+1);

        // create a resource using these properties
        Resource r = ResourceFactory.createResource(qNameUtility.expandQName(uri));
        Model m = ModelFactory.createDefaultModel();
        r = r.inModel(m);
        r.addProperty(RDFS.label, label);
        return r;
    }
}
