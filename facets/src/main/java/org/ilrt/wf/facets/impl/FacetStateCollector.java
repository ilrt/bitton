package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import java.util.List;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.constraints.Constraint;

/**
 * This is a 'marker' class. It indicates that a certain optimisation is possible,
 * namely that we don't have to count each refinement but may do it in one go.
 * 
 * @author pldms
 */
public class FacetStateCollector extends FacetStateImpl {
    private Property property; // property to collect
    private boolean invert; // invert?

    FacetStateCollector(String name, FacetState parent, String paramValue,
                          List<? extends Constraint> constraint) {
        super(name, parent, paramValue, constraint);
    }
    
    public void setProperty(Property property) { this.property = property; }
    public Property getProperty() { return property; }
    
    public void setInvert(boolean invert) { this.invert = invert; }
    public boolean getInvert() { return invert; }
}
