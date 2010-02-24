/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.sparql;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import java.util.List;
import java.util.Map;
import org.ilrt.wf.facets.FacetState;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pldms
 */
public class SPARQLQueryServiceTest {

    public SPARQLQueryServiceTest() {
    }

    /**
     * Test of getRefinements method, of class SPARQLQueryService.
     */
    @Ignore
    @Test
    public void testGetRefinements() {
        System.out.println("getRefinements");
        FacetState currentFacetState = null;
        SPARQLQueryService instance = null;
        Map expResult = null;
        Map result = instance.getRefinements(currentFacetState);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCounts method, of class SPARQLQueryService.
     */
    @Test
    public void testGetCounts() {
    }

    static class MFacetState implements FacetState {

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
            throw new UnsupportedOperationException("Not supported yet.");
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
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Property getBroaderProperty() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Property getNarrowerProperty() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public RDFNode getValue() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

}