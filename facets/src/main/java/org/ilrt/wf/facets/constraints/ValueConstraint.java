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
        super(property);
        this.value = value;
    }

    public RDFNode getValue() { return value; }

    @Override
    public boolean matches(RDFNode node) {
        return value.equals(node);
    }
}
