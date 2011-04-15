/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.constraints;

import com.hp.hpl.jena.rdf.model.RDFNode;
import java.util.ArrayList;
import java.util.List;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pldms
 */
public class ConstraintTest {

    public ConstraintTest() {
    }

    @Test
    public void TestUnConstraint() {
        Constraint c = new UnConstraint();
        assertTrue(c.matches(RDF.type)); // should match anything
    }

    @Test
    public void TestValueConstraint() {
        Constraint c = new ValueConstraint(null, RDF.type);
        assertTrue(c.matches(RDF.type));
        assertFalse(c.matches(RDF.nil));

        c = new ValueConstraint(null, ResourceFactory.createTypedLiteral(1));
        assertFalse(c.matches(RDF.nil));
        assertTrue(c.matches(ResourceFactory.createTypedLiteral(1)));
    }

    @Test
    public void TestRangeConstraint() {
        Constraint c = new RangeConstraint(null,
                ResourceFactory.createTypedLiteral(1),
                ResourceFactory.createTypedLiteral(3)
                );
        assertTrue(c.matches(ResourceFactory.createTypedLiteral(2)));
        assertTrue(c.matches(ResourceFactory.createTypedLiteral(1)));
        assertFalse(c.matches(ResourceFactory.createTypedLiteral(3))); // open
        // Check that we aren't falling back to lexical range
        assertFalse(c.matches(ResourceFactory.createPlainLiteral("2")));
        assertFalse(c.matches(RDF.nil)); // should never match
    }

    @Test
    public void TestRegexpConstraint() {
        Constraint c = new RegexpConstraint(null, "^Z");
        assertTrue(c.matches(ResourceFactory.createPlainLiteral("Zeb")));
        assertFalse(c.matches(ResourceFactory.createPlainLiteral("Bez")));
        assertTrue(c.matches(ResourceFactory.createPlainLiteral("zeb")));
        assertTrue(c.matches(ResourceFactory.createResource("zttp://nope/")));

        c = new RegexpConstraint(null, "Z");
        assertTrue(c.matches(ResourceFactory.createPlainLiteral("Bez")));
        assertFalse(c.matches(ResourceFactory.createPlainLiteral("flsdkjffs")));
    }

    @Test
    public void UnionConstraint() {
        List <RDFNode> restrictions = new ArrayList();
        restrictions.add(ResourceFactory.createTypedLiteral(1));
        Constraint c = new UnionConstraint(null, restrictions);
        
        assertTrue(c.matches(ResourceFactory.createTypedLiteral(1)));
        assertFalse(c.matches(ResourceFactory.createTypedLiteral(2)));
        assertFalse(c.matches(ResourceFactory.createTypedLiteral(3)));
        
        restrictions.add(ResourceFactory.createTypedLiteral(3));
        c = new UnionConstraint(null, restrictions);
        
        assertTrue(c.matches(ResourceFactory.createTypedLiteral(1)));
        assertFalse(c.matches(ResourceFactory.createTypedLiteral(2)));
        assertTrue(c.matches(ResourceFactory.createTypedLiteral(3)));
    }
}