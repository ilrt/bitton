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

    private Facet createAlphaNumericFacet(FacetConstraint constraint) {

        if (constraint.getParameters().containsKey(constraint.getConfig().get(Facet.PARAM_NAME))) {


        } else {


        }


        return null;
    }

    // ---------- methods relating to alpha numeric facets

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

    protected List<FacetState> alphaNumericRefinements(String typeProperty, String linkProperty) {

        // each state is constrained to a type, e.g. foaf:Person
        ValueConstraint valueConstraint = new ValueConstraint(RDF.type,
                ResourceFactory.createProperty(typeProperty));

        // property used in each state
        Property p = ResourceFactory.createProperty(linkProperty);

        // list to hold refinements
        List<FacetState> refinements = new ArrayList<FacetState>();

        // go through the list
        for (char c : alphaNumericArray()) {

            // constraints used in this facet state - type and character
            Set<Constraint> constraints = new HashSet<Constraint>();
            constraints.add(valueConstraint);
            constraints.add(alphaNumericConstraint(p, c));

            FacetStateImpl facet = new FacetStateImpl();
            facet.setName(alphaNumericLabel(c));
            facet.setConstraints(constraints);
            facet.setLinkProperty(ResourceFactory.createProperty(linkProperty));
            refinements.add(facet);
        }

        return refinements;
    }

    private final FacetQueryService facetQueryService;
}
