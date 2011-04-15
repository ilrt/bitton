package org.ilrt.wf.facets.sparql;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.algebra.Op;
import com.hp.hpl.jena.sparql.algebra.OpAsQuery;
import com.hp.hpl.jena.sparql.algebra.op.OpBGP;
import com.hp.hpl.jena.sparql.algebra.op.OpGraph;
import com.hp.hpl.jena.sparql.algebra.op.OpJoin;
import com.hp.hpl.jena.sparql.algebra.op.OpProject;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.expr.E_Aggregator;
import com.hp.hpl.jena.sparql.expr.ExprVar;
import com.hp.hpl.jena.sparql.expr.aggregate.AggCountVarDistinct;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.constraints.Constraint;
import org.ilrt.wf.facets.impl.FacetStateCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pldms
 */
public class SPARQLQueryServiceGroupOpt extends SPARQLQueryService {
    
    private final static Logger log = LoggerFactory.getLogger(SPARQLQueryServiceGroupOpt.class);

    public SPARQLQueryServiceGroupOpt(QEFactory qef) {
        super(qef);
    }
    
    @Override
    public Map<FacetState, Integer> getCounts(List<? extends FacetState> currentFacetStates) {
        long startTime = 0;
        if (log.isDebugEnabled()) startTime = System.currentTimeMillis();
        // Inefficient first pass
        Map<FacetState, Integer> counts = new HashMap<FacetState, Integer>();
        VarGen vgen = new VarGen();
        for (FacetState state: currentFacetStates) {
            // This is the point of this class:
            // Spot cases where we can get _ALL_ counts for the facet in one go
            if (state instanceof FacetStateCollector) getFastStateCounts((FacetStateCollector) state, currentFacetStates, counts, vgen);
            else getStateCounts(state, currentFacetStates, counts, vgen);
        }
        if (log.isDebugEnabled()) log.debug("getCounts took: {} ms",
                System.currentTimeMillis() - startTime);
        return counts;
    }
    
    private void getFastStateCounts(FacetStateCollector state, List<? extends FacetState> currentFacetStates, 
            Map<FacetState, Integer> counts, VarGen vgen) {
        
        Property prop = state.getProperty();
        Var val = Var.alloc("val");
        Var count = Var.alloc("count"); 
        // Match current state
        Op matcher = constraintsToOp(statesToConstraints(currentFacetStates), vgen);
        
        Op getter = new OpGraph(vgen.genVar(), tripleToBGP(SUBJECT, prop.asNode(), val, state.getInvert()));
        
        Op op;
        
        // Kind of a hack: text matching really, really, really needs to be first
        // This enables that by spotting a BGP (which we only use for TM)
        if ((matcher instanceof OpJoin) && ((OpJoin) matcher).getLeft() instanceof OpBGP) {
            Op bgp = ((OpJoin) matcher).getLeft();
            Op right = ((OpJoin) matcher).getRight();
            op = OpJoin.create(bgp,
                    OpJoin.create(getter, right)
                    );
        } else {
            op = OpJoin.create(
                    new OpGraph(vgen.genVar(), tripleToBGP(SUBJECT, prop.asNode(), val, state.getInvert())),
                    matcher
                );
        }
        
        op = new OpProject(op, Collections.singletonList(val));
        Query q = OpAsQuery.asQuery(op);
        E_Aggregator counter = q.allocAggregate(new AggCountVarDistinct(new ExprVar(SUBJECT)));
        q.addResultVar(count, counter);
        q.addGroupBy(val);
                
        QueryExecution qe = qef.get(q);
        ResultSet intResults = qe.execSelect();
        
        // Curses: which refinement does this correspond to?
        
        while (intResults.hasNext()) {
            QuerySolution row = intResults.next();
            RDFNode value = row.get("val");
            int number = row.getLiteral("count").getInt();
            
            for (FacetState st: state.getRefinements()) {
                for (Constraint c: st.getConstraints()) {
                    if (c.getProperty().equals(prop) && c.matches(value)) counts.put(st, number);
                }
            }
        }
    }
}
