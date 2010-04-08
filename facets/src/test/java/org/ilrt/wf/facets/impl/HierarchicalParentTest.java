package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDFS;
import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.FacetQueryService.Tree;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Testing a tree based on the following structure:
 * <p/>
 * <pre>
 *                              A
 *                           /    \
 *                         B1      B2
 *                       /   \   /   \
 *                      C1   C2 C3   C4
 *                     /              \
 *                    D1              D2
 * </pre>
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
@RunWith(JMock.class)
public class HierarchicalParentTest extends AbstractFacetTest {


    @Before
    public void setUp() {

        FacetQueryService mockFacetQueryService = context.mock(FacetQueryService.class);
        facetFactory = new FacetFactoryImpl(mockFacetQueryService, getPrefixMap());

        node_A = createTestTree();


        // ---------- check node A

        assertFalse("Node A is not a leaf", node_A.isLeaf());
        assertEquals("Unexpected URI for Node A", URI_A, node_A.getValue().getURI());
        assertEquals("Unexpected label for Node A", label_A, node_A.getValue()
                .getProperty(RDFS.label).getLiteral().getLexicalForm());

        List<Tree<Resource>> node_A_children = node_A.getChildren();

        assertEquals("There should be two children - B1 and B2", 2, node_A_children.size());


        // ---------- check node B1

        Tree<Resource> node_B1 = node_A_children.get(0);

        assertFalse("Node B1 is not a leaf", node_B1.isLeaf());
        assertEquals("Unexpected URI for Node B1", URI_B1, node_B1.getValue().getURI());
        assertEquals("Unexpected label for Node B1", label_B1, node_B1.getValue()
                .getProperty(RDFS.label).getLiteral().getLexicalForm());

        List<Tree<Resource>> node_B1_children = node_B1.getChildren();

        assertEquals("There should be two children - C1 and C2", 2, node_B1_children.size());


        // ---------- check node B2

        Tree<Resource> node_B2 = node_A_children.get(1);

        assertFalse("Node B2 is not a leaf", node_B2.isLeaf());
        assertEquals("Unexpected URI for Node B2", URI_B2, node_B2.getValue().getURI());
        assertEquals("Unexpected label for Node B2", label_B2, node_B2.getValue()
                .getProperty(RDFS.label).getLiteral().getLexicalForm());

        List<Tree<Resource>> node_B2_children = node_B2.getChildren();

        assertEquals("There should be two children - C3 and C4", 2, node_B2_children.size());


        // ---------- check node C1

        Tree<Resource> node_C1 = node_B1_children.get(0);

        assertFalse("Node C1 is not a leaf", node_C1.isLeaf());
        assertEquals("Unexpected URI for Node C1", URI_C1, node_C1.getValue().getURI());
        assertEquals("Unexpected label for Node C1", label_C1, node_C1.getValue()
                .getProperty(RDFS.label).getLiteral().getLexicalForm());

        List<Tree<Resource>> node_C1_children = node_C1.getChildren();

        assertEquals("There should be one child - D1", 1, node_C1_children.size());


        // ---------- check node C2

        Tree<Resource> node_C2 = node_B1_children.get(1);

        assertTrue("Node C2 is a leaf", node_C2.isLeaf());
        assertEquals("Unexpected URI for Node C2", URI_C2, node_C2.getValue().getURI());
        assertEquals("Unexpected label for Node C2", label_C2, node_C2.getValue()
                .getProperty(RDFS.label).getLiteral().getLexicalForm());

        List<Tree<Resource>> node_C2_children = node_C2.getChildren();

        assertEquals("There should be no children", 0, node_C2_children.size());


        // ---------- check node C3

        Tree<Resource> node_C3 = node_B2_children.get(0);

        assertTrue("Node C3 is a leaf", node_C3.isLeaf());
        assertEquals("Unexpected URI for Node C3", URI_C3, node_C3.getValue().getURI());
        assertEquals("Unexpected label for Node C3", label_C3, node_C3.getValue()
                .getProperty(RDFS.label).getLiteral().getLexicalForm());

        List<Tree<Resource>> node_C3_children = node_C3.getChildren();

        assertEquals("There should be no children", 0, node_C3_children.size());


        // ---------- check node C4

        Tree<Resource> node_C4 = node_B2_children.get(1);

        assertFalse("Node C4 is not a leaf", node_C4.isLeaf());
        assertEquals("Unexpected URI for Node C4", URI_C4, node_C4.getValue().getURI());
        assertEquals("Unexpected label for Node C4", label_C4, node_C4.getValue()
                .getProperty(RDFS.label).getLiteral().getLexicalForm());

        List<Tree<Resource>> node_C4_children = node_C4.getChildren();

        assertEquals("There should be one child - D2", 1, node_C4_children.size());


        // ---------- check node D1

        Tree<Resource> node_D1 = node_C1_children.get(0);

        assertTrue("Node D1 is a leaf", node_D1.isLeaf());
        assertEquals("Unexpected URI for Node D1", URI_D1, node_D1.getValue().getURI());
        assertEquals("Unexpected label for Node D1", label_D1, node_D1.getValue()
                .getProperty(RDFS.label).getLiteral().getLexicalForm());

        List<Tree<Resource>> node_D1_children = node_D1.getChildren();

        assertEquals("There should be no children", 0, node_D1_children.size());


        // ---------- check node D2

        Tree<Resource> node_D2 = node_C4_children.get(0);

        assertTrue("Node D2 is a leaf", node_D2.isLeaf());
        assertEquals("Unexpected URI for Node D2", URI_D2, node_D2.getValue().getURI());
        assertEquals("Unexpected label for Node D2", label_D2, node_D2.getValue()
                .getProperty(RDFS.label).getLiteral().getLexicalForm());

        List<Tree<Resource>> node_D2_children = node_D2.getChildren();

        assertEquals("There should be no children", 0, node_D2_children.size());


    }

    @Test
    public void searchForNodeA() {

        List<Resource> path = facetFactory.findChildren(node_A, URI_A);

        assertEquals("Incorrect path size", 1, path.size());
        assertEquals("Unexpected URI for element", URI_A, path.get(0).getURI());
    }

    @Test
    public void searchForNodeB1() {

        List<Resource> path = facetFactory.findChildren(node_A, URI_B1);

        assertEquals("Incorrect path size", 2, path.size());
        assertEquals("Unexpected URI for element", URI_A, path.get(0).getURI());
        assertEquals("Unexpected URI for element", URI_B1, path.get(1).getURI());
    }

    @Test
    public void searchForNodeC1() {

        List<Resource> path = facetFactory.findChildren(node_A, URI_C1);

        assertEquals("Incorrect path size", 3, path.size());
        assertEquals("Unexpected URI for element", URI_A, path.get(0).getURI());
        assertEquals("Unexpected URI for element", URI_B1, path.get(1).getURI());
        assertEquals("Unexpected URI for element", URI_C1, path.get(2).getURI());
    }

    @Test
    public void searchForNodeD1() {

        List<Resource> path = facetFactory.findChildren(node_A, URI_D1);

        assertEquals("Incorrect path size", 4, path.size());
        assertEquals("Unexpected URI for element", URI_A, path.get(0).getURI());
        assertEquals("Unexpected URI for element", URI_B1, path.get(1).getURI());
        assertEquals("Unexpected URI for element", URI_C1, path.get(2).getURI());
        assertEquals("Unexpected URI for element", URI_D1, path.get(3).getURI());
    }

    @Test
    public void searchForNodeC2() {

        List<Resource> path = facetFactory.findChildren(node_A, URI_C2);

        assertEquals("Incorrect path size", 3, path.size());
        assertEquals("Unexpected URI for element", URI_A, path.get(0).getURI());
        assertEquals("Unexpected URI for element", URI_B1, path.get(1).getURI());
        assertEquals("Unexpected URI for element", URI_C2, path.get(2).getURI());
    }

    @Test
    public void searchForNodeB2() {

        List<Resource> path = facetFactory.findChildren(node_A, URI_B2);

        assertEquals("Incorrect path size", 2, path.size());
        assertEquals("Unexpected URI for element", URI_A, path.get(0).getURI());
        assertEquals("Unexpected URI for element", URI_B2, path.get(1).getURI());
    }

    @Test
    public void searchForNodeC3() {

        List<Resource> path = facetFactory.findChildren(node_A, URI_C3);

        assertEquals("Incorrect path size", 3, path.size());
        assertEquals("Unexpected URI for element", URI_A, path.get(0).getURI());
        assertEquals("Unexpected URI for element", URI_B2, path.get(1).getURI());
        assertEquals("Unexpected URI for element", URI_C3, path.get(2).getURI());
    }

    @Test
    public void searchForNodeC4() {

        List<Resource> path = facetFactory.findChildren(node_A, URI_C4);

        assertEquals("Incorrect path size", 3, path.size());
        assertEquals("Unexpected URI for element", URI_A, path.get(0).getURI());
        assertEquals("Unexpected URI for element", URI_B2, path.get(1).getURI());
        assertEquals("Unexpected URI for element", URI_C4, path.get(2).getURI());
    }

    @Test
    public void searchForNodeD2() {

        List<Resource> path = facetFactory.findChildren(node_A, URI_D2);

        assertEquals("Incorrect path size", 4, path.size());
        assertEquals("Unexpected URI for element", URI_A, path.get(0).getURI());
        assertEquals("Unexpected URI for element", URI_B2, path.get(1).getURI());
        assertEquals("Unexpected URI for element", URI_C4, path.get(2).getURI());
        assertEquals("Unexpected URI for element", URI_D2, path.get(3).getURI());
    }

    private Tree<Resource> node_A;

    private FacetFactoryImpl facetFactory;
    private final Mockery context = new JUnit4Mockery();
}
