package org.ilrt.wf.facets.freemarker;

import com.hp.hpl.jena.rdf.model.Resource;
import freemarker.ext.beans.StringModel;
import freemarker.template.SimpleNumber;
import freemarker.template.TemplateModel;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class FacetViewFreeMarkerWrapper {

    public FacetViewFreeMarkerWrapper(FacetView view) {

        this.view = view;

        results = new ArrayList<TemplateModel>();

        for (Resource resource : view.getResults()) {
            results.add(new ResourceHashModel(resource));
        }

        facets = new ArrayList<StringModel>();

        for (Facet facet : view.getFacets()) {
            facets.add(new StringModel(facet, new JenaObjectWrapper()));
        }
    }


    public SimpleNumber getTotal() {
        return new SimpleNumber(view.getTotal());
    }

    public List<StringModel> getFacets() {
        return facets;
    }

    public List<TemplateModel> getResults() {
        return results;
    }

    List<TemplateModel> results;

    List<StringModel> facets;

    FacetView view;
}
