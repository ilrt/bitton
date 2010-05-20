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
    private final Property property;

    public AbstractConstraint(Property property) {
        this.property = property;
    }

    @Override
    public Property getProperty() { return property; }

    @Override
    public String toString() { return "[" + property + "]"; }
}
