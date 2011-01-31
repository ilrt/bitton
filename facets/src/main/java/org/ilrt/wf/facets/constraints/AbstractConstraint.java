/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.constraints;

import com.hp.hpl.jena.rdf.model.Property;

/**
 *
 * @author pldms
 */
public abstract class AbstractConstraint implements Constraint {
    protected final Property property;
    protected final boolean inverted;

    public AbstractConstraint(Property property) {
        this(property, false);
    }

    public AbstractConstraint(Property property, boolean inverted) {
        this.property = property;
        this.inverted = inverted;
    }

    @Override
    public Property getProperty() { return property; }

    @Override
    public boolean isPropertyInverted() { return inverted; }

    @Override
    public String toString() { return "[" + property + "]"; }
}
