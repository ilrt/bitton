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
public class TextMatchConstraint implements Constraint {
    private final String matcher;
    
    public TextMatchConstraint(String matcher) {
        this.matcher = matcher;
    }
    
    public String getMatcher() { return matcher; }
    
    @Override
    public Property getProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isPropertyInverted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean matches(RDFNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
