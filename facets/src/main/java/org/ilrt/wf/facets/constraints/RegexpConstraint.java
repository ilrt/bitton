/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.constraints;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import java.util.regex.Pattern;

/**
 *
 * @author pldms
 */
public class RegexpConstraint extends AbstractConstraint {
    private final String regexp;
    private final Pattern regexpC;

    public RegexpConstraint(Property property, String regexp) {
        super(property);
        this.regexp = regexp;
        this.regexpC = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
    }

    public String getRegexp() { return regexp; }

    @Override
    public boolean matches(RDFNode node) {
        return regexpC.matcher(strval(node)).find();
    }

    private String strval(RDFNode node) {
        if (node.isLiteral()) return ((Literal) node).getLexicalForm();
        else if (node.isURIResource()) return ((Resource) node).getURI();
        else return ((Resource) node).getId().getLabelString();
    }
    
    @Override
    public int hashCode() {
        return 0xefef ^ regexp.hashCode() ^ property.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RegexpConstraint other = (RegexpConstraint) obj;
        if ((this.regexp == null) ? (other.regexp != null) : !this.regexp.equals(other.regexp)) {
            return false;
        }
        return (this.inverted == other.inverted) && this.property.equals(other.property);
    }
}
