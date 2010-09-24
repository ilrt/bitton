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
public class ValueConstraint extends AbstractConstraint {
    private final RDFNode value;

    public ValueConstraint(Property property, RDFNode value) {
        this(property, value, false);
    }

    public ValueConstraint(Property property, RDFNode value, boolean inverted) {
        super(property, inverted);
        this.value = value;
    }

    public RDFNode getValue() { return value; }

    @Override
    public boolean matches(RDFNode node) {
        return value.equals(node);
    }

    @Override
    public String toString() { return super.toString() + " value: " + value; }
}
