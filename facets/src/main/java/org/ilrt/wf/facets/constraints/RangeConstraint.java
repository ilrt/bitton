/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.constraints;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.expr.ExprNotComparableException;
import com.hp.hpl.jena.sparql.expr.NodeValue;

/**
 *
 * @author pldms
 */
public class RangeConstraint extends AbstractConstraint {
    private final RDFNode from, to;
    private final NodeValue fromV, toV;
    
    public RangeConstraint(Property property, RDFNode from, RDFNode to) {
        super(property);
        this.from = from; this.to = to;
        fromV = NodeValue.makeNode(from.asNode());
        toV = NodeValue.makeNode(to.asNode());
    }

    public RDFNode getFrom() { return from; }

    public RDFNode getTo() { return to; }

    @Override
    public boolean matches(RDFNode node) {
        NodeValue value = NodeValue.makeNode(node.asNode());
        try {
            int lower = NodeValue.compare(value, fromV);
            int upper = NodeValue.compare(toV, value);
            return ( lower == NodeValue.CMP_EQUAL ||
                    lower == NodeValue.CMP_GREATER ) &&
                    upper == NodeValue.CMP_GREATER ;
        } catch (ExprNotComparableException e) {
            return false;
        }
    }
}
