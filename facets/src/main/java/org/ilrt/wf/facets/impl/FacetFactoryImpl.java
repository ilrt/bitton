package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetConstraint;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetFactory;
import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.constraints.Constraint;
import org.ilrt.wf.facets.constraints.RegexpConstraint;
import org.ilrt.wf.facets.constraints.ValueConstraint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FacetFactoryImpl implements FacetFactory {

    public FacetFactoryImpl(FacetQueryService facetQueryService) {
        this.facetQueryService = facetQueryService;
    }

    @Override
    public Facet create(FacetConstraint constraint) throws FacetException {

        String facetType = constraint.getConfig().get(Facet.FACET_TYPE);

        if (facetType.equals(Facet.ALPHA_NUMERIC_FACET_TYPE)) {
            return createAlphaNumericFacet(constraint);
        } else {
            throw new FacetException("Unrecognized facet type: " + facetType);
        }
    }

    @Override
    public Facet calculateCount(List<Facet> facet) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private Facet createAlphaNumericFacet(FacetConstraint constraint) {

        String typeProperty = constraint.getConfig().get(Facet.CONSTRAINT_TYPE);
        String linkProperty = constraint.getConfig().get(Facet.LINK_PROPERTY);

        // this alpha-numeric facet has been selected via the request object
        if (constraint.getParameters().containsKey(constraint.getConfig().get(Facet.PARAM_NAME))) {

//            String paramValue =


            return null;
        } else { // we want them all

            // create a pseudo parent
            FacetStateImpl root = new FacetStateImpl();
            root.setRoot(true);





            //return create(constraint.getConfig(),
            //        alphaNumericRefinements(typeProperty, linkProperty));
        }


        return null;
    }

    // ---------- methods relating to alpha numeric facets

    protected Facet create(Map<String, String> config, List<FacetState> states) {

        // get the name and parameter name
        String name = config.get(Facet.FACET_TITLE);
        String paramName = config.get(Facet.PARAM_NAME);

        // create a pseudo root state to fake a hierarchy
        FacetStateImpl root = new FacetStateImpl();
        root.setRoot(true);
        root.setRefinements(states);

        return new FacetImpl(name, root, paramName);
    }


    /**
     * @return an array of alpha-numeric characters.
     */
    protected char[] alphaNumericArray() {
        return "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    }

    /**
     * @param c the character we want to represent in a label
     * @return the label representation of a character
     */
    protected String alphaNumericLabel(char c) {
        return c + "*";
    }

    /**
     * @param p the property constrained with the character.
     * @param c the character we want to represent in a constraint.
     * @return the constraint that represents the character.
     */
    protected RegexpConstraint alphaNumericConstraint(Property p, char c) {
        return new RegexpConstraint(p, "^" + c);
    }

    protected List<FacetState> alphaNumericRefinements(String typeProperty, String linkProperty,
                                                       FacetState rootState) {

        // each state is constrained to a type, e.g. foaf:Person
        ValueConstraint valueConstraint = new ValueConstraint(RDF.type,
                ResourceFactory.createProperty(typeProperty));

        // property used in each state
        Property p = ResourceFactory.createProperty(linkProperty);

        // list to hold refinements
        List<FacetState> refinementsList = new ArrayList<FacetState>();

        // go through the list
        for (char c : alphaNumericArray()) {

            // constraints used in this facet state - type and character
            Set<Constraint> constraints = new HashSet<Constraint>();
            constraints.add(valueConstraint);
            constraints.add(alphaNumericConstraint(p, c));

            // populate the state
            FacetStateImpl facet = new FacetStateImpl();
            facet.setName(alphaNumericLabel(c));
            facet.setConstraints(constraints);
            facet.setParent(rootState);
            refinementsList.add(facet);
        }

        return refinementsList;
    }

    private final FacetQueryService facetQueryService;
}
