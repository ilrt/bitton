package org.ilrt.wf.facets.freemarker;

import freemarker.ext.beans.StringModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetState;

import java.util.ArrayList;
import java.util.List;

public class FacetParentListMethod implements TemplateMethodModelEx {

    @Override
    public Object exec(List args) throws TemplateModelException {

        checkArguments(args);

        Facet facet = (Facet) ((StringModel) args.get(0)).getAdaptedObject(Facet.class);

        List<FacetState> results = null;

        FacetState facetState = facet.getState();

        if (!facetState.isRoot()) {
            results = findParents(facetState);
        }

        return results == null ? new ArrayList<FacetState>() : results;
    }

    private void checkArguments(List args) throws TemplateModelException {

        if (args.size() != 1) {
            throw new TemplateModelException("Expected 1 argument, actually received "
                    + args.size() + ". Expected org.ilrt.wf.facets.Facet");
        }

        Object o = ((StringModel) args.get(0)).getAdaptedObject(Facet.class);

        if (!(o instanceof Facet)) {
            throw new TemplateModelException("Expected an instance of org.ilrt.wf.facets.Facet, "
                    + " but actually received " + o);

        }

    }

    private List<FacetState> findParents(FacetState state) {

        List<FacetState> pList = new ArrayList<FacetState>();

        while (state.getParent() != null) {
            state = state.getParent();
            pList.add(0, state);
        }
        return pList;
    }
}
