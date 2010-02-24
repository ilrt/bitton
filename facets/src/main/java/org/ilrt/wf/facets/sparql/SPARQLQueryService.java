/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.sparql;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.engine.binding.Binding;
import com.hp.hpl.jena.sparql.engine.binding.BindingMap;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.FacetState;

/**
 *
 * @author pldms
 */
public class SPARQLQueryService implements FacetQueryService {
    private final QEFactory qef;

    public SPARQLQueryService(QEFactory qef) {
        this.qef = qef;
    }

    @Override
    public Map<FacetState, List<RDFNode>> getRefinements(FacetState currentFacetState) {
        URL toBindRes = this.getClass().getResource("/sparql/refinements.rq");
        Query query = QueryFactory.read("/sparql/refinements.rq");

        /**
         * Rejig refinements query, binding the bits we need
         */
        Binding binding = new BindingMap();
        query.setQueryResultStar(false);
        if (currentFacetState.getBroaderProperty() != FacetState.NONE) {
            binding.add(Var.alloc("p"), currentFacetState.getBroaderProperty().asNode());
            binding.add(Var.alloc("o"), currentFacetState.getValue().asNode());
            query.addResultVar("s");
        } else {
            binding.add(Var.alloc("p"), currentFacetState.getNarrowerProperty().asNode());
            binding.add(Var.alloc("s"), currentFacetState.getValue().asNode());
            query.addResultVar("o");
        }

        QueryExecution qe = qef.get(
                QueryRebinder.rebind(query, binding)
                );

        try {
            List<RDFNode> toReturn = new LinkedList<RDFNode>();
            ResultSet results = qe.execSelect();
            while (results.hasNext()) toReturn.add(results.next().get("o"));
            return Collections.singletonMap(currentFacetState, toReturn);
        } finally {
            qe.close();
        }
    }

    @Override
    public Map<FacetState, Integer> getCounts(FacetState futureFacetState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
