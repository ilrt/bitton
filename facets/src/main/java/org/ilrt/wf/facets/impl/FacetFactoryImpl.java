package org.ilrt.wf.facets.impl;

import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetConstraint;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetFactory;
import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.constraints.RegexpConstraint;

import java.util.Map;

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

    /**
     * @return an array of alpha-numeric characters.
     */
    protected char[] alphaNumericArray() {
        return "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    }

    /**
     * @param c     the character we want to represent in a label
     * @return      the label representation of a character
     */
    protected String alphaNumericLabel(char c) {
        return c + "*";
    }

    /**
     * @param c     the character we want to represent in a constraint
     * @return      the constraint that represents the character
     */
    protected RegexpConstraint alphaNumericConstraint(char c) {
        return new RegexpConstraint("^" + c);
    }

    private FacetQueryService facetQueryService;
}
