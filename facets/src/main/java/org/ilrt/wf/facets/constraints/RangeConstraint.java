/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.constraints;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;

/**
 *
 * @author pldms
 */
public class RangeConstraint extends AbstractConstraint {
    private final RDFNode from, to;
    
    public RangeConstraint(Property property, RDFNode from, RDFNode to) {
        super(property);
        this.from = from; this.to = to;
    }

    public RDFNode getFrom() { return from; }

    public RDFNode getTo() { return to; }

    @Override
    public boolean matches(RDFNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
