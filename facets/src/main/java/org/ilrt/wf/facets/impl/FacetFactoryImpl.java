package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.ResourceFactory;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetConstraint;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetFactory;
import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.constraints.RegexpConstraint;

import java.util.ArrayList;
import java.util.List;

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
     * @param c the character we want to represent in a constraint
     * @return the constraint that represents the character
     */
    protected RegexpConstraint alphaNumericConstraint(char c) {
        return new RegexpConstraint("^" + c);
    }

    protected List<FacetState> alphaNumericRefinements(String linkProperty) {

        List<FacetState> refinements = new ArrayList<FacetState>();

        for (char c : alphaNumericArray()) {
            FacetStateImpl facet = new FacetStateImpl();
            facet.setName(alphaNumericLabel(c));
            facet.setConstraint(alphaNumericConstraint(c));
            facet.setLinkProperty(ResourceFactory.createProperty(linkProperty));
            refinements.add(facet);
        }

        return refinements;
    }

    private final FacetQueryService facetQueryService;
}
