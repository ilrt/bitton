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
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.algebra.Op;
import com.hp.hpl.jena.sparql.algebra.OpAsQuery;
import com.hp.hpl.jena.sparql.algebra.op.OpBGP;
import com.hp.hpl.jena.sparql.algebra.op.OpFilter;
import com.hp.hpl.jena.sparql.algebra.op.OpGraph;
import com.hp.hpl.jena.sparql.algebra.op.OpJoin;
import com.hp.hpl.jena.sparql.algebra.op.OpNull;
import com.hp.hpl.jena.sparql.algebra.op.OpProject;
import com.hp.hpl.jena.sparql.core.BasicPattern;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.engine.binding.Binding;
import com.hp.hpl.jena.sparql.engine.binding.BindingMap;
import com.hp.hpl.jena.sparql.expr.E_LessThan;
import com.hp.hpl.jena.sparql.expr.E_LessThanOrEqual;
import com.hp.hpl.jena.sparql.expr.E_LogicalAnd;
import com.hp.hpl.jena.sparql.expr.E_Regex;
import com.hp.hpl.jena.sparql.expr.ExprVar;
import com.hp.hpl.jena.sparql.expr.nodevalue.NodeValueNode;
import java.net.URL;
import java.util.Collection;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pldms
 */
public class SPARQLQueryService implements FacetQueryService {

    private final static Logger log = LoggerFactory.getLogger(SPARQLQueryService.class);

    private final static Var SUBJECT = Var.alloc("s");

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
    public Map<FacetState, Integer> getCounts(List<? extends FacetState> currentFacetStates) {
        // Inefficient first pass
        Map<FacetState, Integer> counts = new HashMap<FacetState, Integer>();
        for (FacetState state: currentFacetStates) {
            getStateCounts(state, currentFacetStates, counts);
        }
        return counts;
    }

    @Override
    public int getCount(List<? extends FacetState> currentFacentStates) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Resource> getResults(List<? extends FacetState> currentFacentStates, int offset, int number) {
        int limit = offset + number;
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected void getStateCounts(FacetState state,
            List<? extends FacetState> currentFacetStates, Map<FacetState, Integer> counts) {
        // Get contrast state
        List<FacetState> otherStates = new LinkedList<FacetState>(currentFacetStates);
        otherStates.remove(state);
        for (FacetState futureState: state.getRefinements()) {
            counts.put(futureState, getCount(futureState, otherStates));
        }
    }

    protected int getCount(FacetState ffs, List<FacetState> otherStates) {
        return getCount(statesToConstraints(otherStates, ffs));
    }

    protected int getCount(Collection<Constraint> constraints) {
        Op op = constraintsToOp(constraints);

        // Over named graphs, just get subject
        op = new OpGraph(Var.alloc("g"), op);
        op = new OpProject(op, Collections.singletonList(SUBJECT));

        Query q = OpAsQuery.asQuery(op);
        QueryExecution qe = qef.get(q);

        int count = 0;
        ResultSet r = qe.execSelect();
        while (r.hasNext()) { count++; r.next(); }
        return count;
    }

    protected Op constraintsToOp(Collection<Constraint> cons) {
        Op op = null;
        for (Constraint con: cons) {
            Op newOp = constraintToOp(con);
            if (newOp instanceof OpNull) continue;
            else if (op == null) op = newOp;
            else op = OpJoin.create(op, newOp);
        }
        return op;
    }

    protected Op constraintToOp(Constraint constraint) {
        if (constraint instanceof UnConstraint) return OpNull.create();
        else if (constraint instanceof ValueConstraint) {
            return tripleToBGP(SUBJECT,
                    constraint.getProperty().asNode(),
                    ((ValueConstraint) constraint).getValue().asNode()
                    );
        } else if (constraint instanceof RangeConstraint) {
            RangeConstraint rc = (RangeConstraint) constraint;
            Var val = genVar(); // TODO generate
            // create filters
            return OpFilter.filter(
                    new E_LogicalAnd(
                        new E_LessThanOrEqual(
                            NodeValueNode.makeNode(rc.getFrom().asNode()),
                            new ExprVar(val) ),
                        new E_LessThan(
                            new ExprVar(val),
                            NodeValueNode.makeNode(rc.getTo().asNode()) )
                            ),
                    tripleToBGP(SUBJECT, constraint.getProperty().asNode(), val)
                    );
        } else if (constraint instanceof RegexpConstraint) {
            RegexpConstraint rc = (RegexpConstraint) constraint;
            Var val = genVar(); // TODO generate
            return OpFilter.filter(
                    new E_Regex(
                      new ExprVar(val),
                      rc.getRegexp(),
                      "i"
                      ),
                    tripleToBGP(SUBJECT, constraint.getProperty().asNode(), val)
                    );
        } else throw new RuntimeException("Unknown constraint type");
    }

    private Op tripleToBGP(Node s, Node p, Node o) {
        BasicPattern bgp = new BasicPattern();
        bgp.add(Triple.create(s, p, o));
        return new OpBGP(bgp);
    }

    private Collection<Constraint> statesToConstraints(Collection<FacetState> states, FacetState... moreStates) {
        Collection<Constraint> cs = new LinkedList<Constraint>();
        for (FacetState s: states) cs.addAll(s.getConstraints());
        for (FacetState s: moreStates) cs.addAll(s.getConstraints());
        return cs;
    }

    private int varCount = 0;
    private Var genVar() { varCount++; return Var.alloc("v" + varCount); }

}
