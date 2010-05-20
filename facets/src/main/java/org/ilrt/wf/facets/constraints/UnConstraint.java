/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.constraints;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;

/**
 * A completely unconstrained constraint
 *
 * @author pldms
 */
public class UnConstraint implements Constraint {

    @Override
    public boolean matches(RDFNode node) {
        return true;
    }

    // TODO ought to be an empty property
    @Override
    public Property getProperty() {
        throw new UnsupportedOperationException("Don't bother");
    }

    @Override
    public String toString() { return "[ Unconstrained ]"; }
}
