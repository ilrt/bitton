/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.sparql;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.algebra.Algebra;
import com.hp.hpl.jena.sparql.algebra.Op;
import com.hp.hpl.jena.sparql.algebra.OpVisitor;
import com.hp.hpl.jena.sparql.algebra.TransformCopy;
import com.hp.hpl.jena.sparql.algebra.op.OpBGP;
import com.hp.hpl.jena.sparql.algebra.op.OpTriple;
import com.hp.hpl.jena.sparql.core.BasicPattern;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.engine.binding.Binding;
import com.hp.hpl.jena.sparql.syntax.ElementTriplesBlock;
import com.hp.hpl.jena.sparql.syntax.ElementVisitorBase;
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
        Query query = QueryFactory.read("/sparql/refinements.rq");

        Op op = Algebra.compile(query);



        Property rel = null;
        String toBind = null;
        if (currentFacetState.getBroaderProperty() != FacetState.NONE) {
            rel = currentFacetState.getBroaderProperty();
            query.addProjectVars(Collections.singleton("s"));
            toBind = "o";
        } else {
            rel = currentFacetState.getNarrowerProperty();
            query.addProjectVars(Collections.singleton("o"));
            toBind = "s";
        }

        QueryExecution qe = qef.get(query);

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

    public static class Rebinder extends TransformCopy {
        private final Binding bindings;

        public Rebinder(Binding bindings) {
            this.bindings = bindings;
        }

        @Override
        public Op transform(OpBGP op) {
            BasicPattern pattern = op.getPattern();
            BasicPattern newPattern = new BasicPattern();
            for (Triple t: pattern) {
               Node s = bind(t.getSubject());
                Node p = bind(t.getPredicate());
                Node o = bind(t.getObject());
                newPattern.add(Triple.create(s, p, o));
            }
            return new OpBGP(newPattern);
        }

        private Node bind(Node node) {
            if (node instanceof Var && 
                    bindings.contains((Var) node))
                return bindings.get((Var) node);
            else return node;
        }

    }
}
