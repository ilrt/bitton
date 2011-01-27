/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.datatypes.TypeMapper;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDFS;
import java.util.Arrays;
import java.util.Collections;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetEnvironment;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.QNameUtility;
import org.ilrt.wf.facets.constraints.ValueConstraint;

/**
 *
 * @author pldms
 */
public class TextSearchFacetImpl extends AbstractFacetFactoryImpl {
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
        String property = environment.getConfig().get(Facet.LINK_PROPERTY);
        boolean requireLabel = true;
        Property prop = ResourceFactory.createProperty(property);
        String param = environment.getConfig().get(Facet.PARAM_NAME);
        ValueConstraint typeConstraint = createTypeConstraint(type);

        String[] currentVals = environment.getParameters().get(environment.getConfig().get(Facet.PARAM_NAME));

        state = new FacetStateCollector("Base", null, null, Collections.singletonList(typeConstraint));

            // do nothing
            state = new FacetStateImpl();
            state.setRoot(true);

            state.getConstraints().addAll(Arrays.asList(typeConstraint));
//            state.setRefinements(refinements(environment.getConfig().get(Facet.NUMERIC_RANGE), typeConstraint, p, root, typeUri, df));



        return new FacetImpl(getFacetTitle(environment), state, getParameterName(environment), Facet.TEXT_SEARCH_FACET);
    }

    private String toParamVal(RDFNode node) {
        if (node.isLiteral()){
            return "L" + ((Literal) node).getLexicalForm() + " " +
                    qNameUtility.getQName(((Literal) node).getDatatypeURI());
        }
        else if (node.isURIResource())
            return "U" + qNameUtility.getQName(((Resource) node).getURI()) + '#' + getLabel(node);
        else return "B" + ((Resource) node).getId().getLabelString();
    }

    private final TypeMapper TM = TypeMapper.getInstance();

    private RDFNode fromParamVal(String val) {
        if (val.startsWith("U"))
        {
            String param = val.substring(1);
            String uri = param.substring(0,param.lastIndexOf("#")); // obtain uri from parameter
            String label = param.substring(param.lastIndexOf("#")+1); // obtain label from parameter

            // create a resource using these properties
            Resource r = ResourceFactory.createResource(qNameUtility.expandQName(uri));
            Model m = ModelFactory.createDefaultModel();
            r = r.inModel(m);
            r.addProperty(RDFS.label, label);
            return r;
        }
        // Erm, what should we do here? Fail?
        else if (val.startsWith("B")) return ResourceFactory.createResource();
        else {
            int index = val.lastIndexOf(" ");
            String lex = val.substring(1, index);
            String dt = qNameUtility.expandQName(val.substring(index + 1));
            return (dt.isEmpty()) ? 
                ResourceFactory.createPlainLiteral(lex) :
                ResourceFactory.createTypedLiteral(lex, TM.getSafeTypeByName(dt));
        }
    }

    private String getLabel(RDFNode node) {
        if (node.isLiteral()) return ((Literal) node).getLexicalForm();
        else if (node.isAnon()) return ((Resource) node).getId().getLabelString();
        else {
            Resource r = (Resource) node;
            String label = (r.hasProperty(RDFS.label)) ?
                r.getProperty(RDFS.label).getLiteral().getLexicalForm() :
                qNameUtility.getQName(r.getURI());
            return label;
        }
    }
}
