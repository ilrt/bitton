package org.ilrt.wf.facets;

import java.util.List;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public interface FacetFactory {

    Facet create(FacetEnvironment environment) throws FacetException;

    void calculateCount(List<Facet> facetList);

    /**
     * Created by IntelliJ IDEA.
     * User: cmmaj
     * Date: Mar 17, 2010
     * Time: 12:27:12 PM
     * To change this template use File | Settings | File Templates.
     */
    public static interface FacetCache {
    }
}
