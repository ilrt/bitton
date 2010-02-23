package org.ilrt.wf.facets;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import java.util.List;

/**
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 * @author Damian Steer (d.steer.@bristol.ac.uk)
 */
public interface FacetState {

    String getName();

    int getCount();

    boolean isRoot();

    List<FacetState> getRefinements();

    FacetState getParent();

    String getParamValue();

    Property getLinkProperty();

    public final static Property NONE =
            ResourceFactory.createProperty("urn:x-ilrt:none");

    /**
     * @return broader relation for facet, or NONE.
     */
    Property getBroaderProperty();

    /**
     * @return narrower relation for facet, or NONE.
     */
    Property getNarrowerProperty();

    /**
     * @return Current value of this state. May be NONE for 'top' state (?)
     */
    RDFNode getValue();
}
