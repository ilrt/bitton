/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.sparql;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.util.FileManager;
import java.net.URL;
import java.util.List;
import java.util.Map;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.constraints.Constraint;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pldms
 */
public class SPARQLQueryServiceTest {
    private final Model model;
    private final String NS = "http://example.com/ns#";
    private final Property link = ResourceFactory.createProperty(NS, "link");
    private final Property broader = ResourceFactory.createProperty(NS, "broader");
    private final Property narrower = ResourceFactory.createProperty(NS, "narrower");


    public SPARQLQueryServiceTest() {
        URL data = this.getClass().getResource("/sparql/testdata.ttl");
        this.model = FileManager.get().loadModel(data.toExternalForm());
    }

    /**
     * Test of getRefinements method, of class SPARQLQueryService.
     */
    @Test
    public void testGetBroaderRefinements() {
        FacetState fs = new MFacetState(ResourceFactory.createResource(NS + "a"),
                link, broader, FacetState.NONE);
        SPARQLQueryService instance = new SPARQLQueryService(new ModelQEFactory(model));
        Map<FacetState, List<RDFNode>> a = instance.getRefinements(fs);

        assertEquals("Got correct broader refinements", 3, a.get(fs).size());
    }

    @Test
    public void testGetNarrowerRefinements() {
        FacetState fs = new MFacetState(ResourceFactory.createResource(NS + "a"),
                link, FacetState.NONE, narrower);
        SPARQLQueryService instance = new SPARQLQueryService(new ModelQEFactory(model));
        Map<FacetState, List<RDFNode>> a = instance.getRefinements(fs);

        assertEquals("Got correct narrower refinements", 4, a.get(fs).size());
    }

    /**
     * Test of getCounts method, of class SPARQLQueryService.
     */
    @Ignore
    @Test
    public void testGetCounts() {
    }

    static class MFacetState implements FacetState {
        private final Property link;
        private final Property broader;
        private final Property narrower;
        private final RDFNode value;

        public MFacetState(RDFNode value, Property link, Property broader, Property narrower) {
            if (link == null ||
                    broader == null ||
                    narrower == null) throw new IllegalArgumentException("No nulls");
            this.value = value;
            this.link = link;
            this.broader = broader;
            this.narrower = narrower;
        }

        @Override
        public String getName() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getCount() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isRoot() {
            return value == null;
        }

        @Override
        public List<FacetState> getRefinements() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public FacetState getParent() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getParamValue() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Property getLinkProperty() {
            return link;
        }

        @Override
        public Property getBroaderProperty() {
            return broader;
        }

        @Override
        public Property getNarrowerProperty() {
            return narrower;
        }

        @Override
        public RDFNode getValue() {
            return value;
        }

        @Override
        public Constraint getConstraint() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

}