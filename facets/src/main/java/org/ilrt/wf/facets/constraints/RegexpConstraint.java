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
public class RegexpConstraint extends AbstractConstraint {
    private final String regexp;

    public RegexpConstraint(Property property, String regexp) {
        super(property);
        this.regexp = regexp;
    }

    public String getRegexp() { return regexp; }

    @Override
    public boolean matches(RDFNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
