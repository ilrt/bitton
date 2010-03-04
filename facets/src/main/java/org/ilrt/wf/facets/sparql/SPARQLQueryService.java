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
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.algebra.Algebra;
import com.hp.hpl.jena.sparql.algebra.Op;
import com.hp.hpl.jena.sparql.algebra.op.OpNull;
import com.hp.hpl.jena.sparql.algebra.op.OpTriple;
import com.hp.hpl.jena.sparql.core.BasicPattern;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.engine.binding.Binding;
import com.hp.hpl.jena.sparql.engine.binding.BindingMap;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.constraints.Constraint;
import org.ilrt.wf.facets.constraints.RangeConstraint;
import org.ilrt.wf.facets.constraints.RegexpConstraint;
import org.ilrt.wf.facets.constraints.UnConstraint;
import org.ilrt.wf.facets.constraints.ValueConstraint;

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
        URL refinementsQ = this.getClass().getResource("/sparql/refinements.rq");
        Query query = QueryFactory.read(refinementsQ.toExternalForm());

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

        QueryExecution qe = qef.get( QueryRebinder.rebind(query, binding) );

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
    public Map<FacetState, Integer> getCounts(List<FacetState> currentFacetStates) {
        // Inefficient first pass
        Map<FacetState, Integer> counts = new HashMap<FacetState, Integer>();
        for (FacetState state: currentFacetStates) {
            getStateCounts(state, currentFacetStates, counts);
        }
        return counts;
    }

    protected void getStateCounts(FacetState state,
            List<FacetState> currentFacetStates, Map<FacetState, Integer> counts) {
        // Get contrast state
        List<FacetState> otherStates = new LinkedList<FacetState>(currentFacetStates);
        otherStates.remove(state);
        for (FacetState futureState: state.getRefinements()) {
            counts.put(futureState, getCount(futureState, otherStates));
        }
    }

    protected int getCount(FacetState ffs, List<FacetState> otherStates) {
        Var subject = Var.alloc("subject");

        return -1;
    }

    protected Op stateToOp(FacetState state) {
        for (Constraint constraint: state.getConstraints()) {
            Op op = constraintToOp(state.getLinkProperty(), constraint);
        }
        return null;
    }

    protected Op constraintToOp(Property prop, Constraint constraint) {
        if (constraint instanceof UnConstraint) return OpNull.create();
        if (constraint instanceof ValueConstraint)
            return new OpTriple(
                    Triple.create(Node.ANY, prop.asNode(),
                    ((ValueConstraint) constraint).getValue().asNode()));
        if (constraint instanceof RangeConstraint) {
            // create triple
            // create filter
        }
        if (constraint instanceof RegexpConstraint) {
            // create triple
            // create filter
        }
        throw new RuntimeException("Unknown constraint type");
    }
}
