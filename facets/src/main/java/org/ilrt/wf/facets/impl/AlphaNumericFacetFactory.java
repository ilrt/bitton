package org.ilrt.wf.facets.impl;

import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetConstraint;
import org.ilrt.wf.facets.FacetFactory;

public class AlphaNumericFacetFactory implements FacetFactory {

    private AlphaNumericFacetFactory() {
    }

    public static AlphaNumericFacetFactory getInstance() {
        if (instance == null) {
            instance = new AlphaNumericFacetFactory();
        }

        return instance;
    }

    private static AlphaNumericFacetFactory instance = null;

    @Override
    public Facet create(FacetConstraint constraint) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
