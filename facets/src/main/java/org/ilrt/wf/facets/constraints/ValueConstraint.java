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
public class ValueConstraint implements Constraint {
    private final RDFNode value;

    public ValueConstraint(RDFNode value) {
        this.value = value;
    }

    @Override
    public boolean matches(RDFNode node) {
        return value.equals(node);
    }
}
