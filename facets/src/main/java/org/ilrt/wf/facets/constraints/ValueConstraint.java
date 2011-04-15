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
    
    @Override
    public int hashCode() {
        return 0xf8e87da ^ value.hashCode() ^ ~property.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ValueConstraint other = (ValueConstraint) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return (this.inverted == other.inverted) && this.property.equals(other.property);
    }
}
