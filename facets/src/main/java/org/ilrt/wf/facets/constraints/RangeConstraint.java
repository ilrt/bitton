/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.constraints;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
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
    
    @Override
    public int hashCode() {
        return fromV.hashCode() ^ ~toV.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RangeConstraint other = (RangeConstraint) obj;
        
        if (this.isPropertyInverted() != other.isPropertyInverted()) return false;
        
        if (this.fromV != other.fromV && (this.fromV == null || !this.fromV.equals(other.fromV))) {
            return false;
        }
        if (this.toV != other.toV && (this.toV == null || !this.toV.equals(other.toV))) {
            return false;
        }
        return (this.inverted == other.inverted) && this.property.equals(other.property);
    }
}
