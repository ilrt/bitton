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
public interface Constraint {
    public Property getProperty();
    public boolean matches(RDFNode node);
}
