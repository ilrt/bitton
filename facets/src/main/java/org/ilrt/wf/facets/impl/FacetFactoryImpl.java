package org.ilrt.wf.facets.impl;

import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetConstraint;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetFactory;
import org.ilrt.wf.facets.FacetQueryService;

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

    private FacetQueryService facetQueryService;
}
