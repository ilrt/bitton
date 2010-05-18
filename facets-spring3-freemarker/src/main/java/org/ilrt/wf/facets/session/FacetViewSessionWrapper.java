package org.ilrt.wf.facets.session;

import com.hp.hpl.jena.rdf.model.Resource;
import org.ilrt.wf.facets.FacetView;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class FacetViewSessionWrapper {

    public FacetViewSessionWrapper(FacetView facetView) {
        this.facetView = facetView;
        populateCache();
    }

    public Resource get(String key) {
        return lookUp.get(key);
    }

    public FacetView getFacetView() {
        return facetView;
    }

    private void populateCache() {
        for (Resource r : facetView.getResults()) {
            lookUp.put(r.getURI(), r);
        }
    }

    private FacetView facetView;
    private Map<String, Resource> lookUp = new HashMap<String, Resource>();
}
