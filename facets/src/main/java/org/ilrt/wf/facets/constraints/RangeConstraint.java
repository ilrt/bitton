/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.constraints;

import com.hp.hpl.jena.rdf.model.RDFNode;

/**
 *
 * @author pldms
 */
public class RangeConstraint implements Constraint {
    private final RDFNode from, to;
    
    public RangeConstraint(RDFNode from, RDFNode to) {
        this.from = from; this.to = to;
    }

    public RDFNode getFrom() { return from; }

    public RDFNode getTo() { return to; }

    @Override
    public boolean matches(RDFNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
