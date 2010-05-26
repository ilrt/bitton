/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.sparql;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.sparql.algebra.Op;
import com.hp.hpl.jena.sparql.algebra.op.OpJoin;
import com.hp.hpl.jena.sparql.core.Var;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.constraints.Constraint;

/**
 *
 * @author pldms
 */
public class SPARQLOneShotQueryService extends SPARQLQueryService {

    public SPARQLOneShotQueryService(QEFactory qef) {
        super(qef);
    }

    /**
     * Implementation using one query
     * @param currentFacetStates
     * @return
     */
    @Override
    public Map<FacetState, Integer> getCounts(List<? extends FacetState> currentFacetStates) {
        Map<FacetState, Integer> counts = new HashMap<FacetState, Integer>();
        Collection<Constraint> allConstraints = statesToConstraints(currentFacetStates);

        // This limits SUBJECT to those things matching current state
        Op op = constraintsToOp(allConstraints);

        // Now we want to grab the values to count for refinements
        // TODO: This assumes the same property is used for parent and refinements
        Map<Property, Var> propToVar = new HashMap<Property, Var>();
        VarMaker vf = new VarMaker();
        for (Constraint c: allConstraints) {
            if (!propToVar.containsKey(c.getProperty())) {
                Var val = vf.make();
                op = OpJoin.create(op,
                        tripleToBGP(SUBJECT, c.getProperty().asNode(), val));
                propToVar.put(c.getProperty(), val);
            }
        }

        return counts;
    }

    public static class VarMaker {
        private int num = 0;

        public Var make() { num++; return Var.alloc("vm" + num); }
        
    }
}
