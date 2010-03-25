package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDFS;
import org.ilrt.wf.facets.FacetQueryService.Tree;
import org.junit.Before;
import org.junit.Test;

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
 */
public class HierarchicalParentTest {


    @Before
    public void setUp() {

        node_A = createTestTree();


        // ---------- check node A

        assertFalse("Node A is not a leaf", node_A.isLeaf());
        assertEquals("Unexpected URI for Node A", URI_A, ((Resource) node_A.getValue()).getURI());
        assertEquals("Unexpected label for Node A", label_A, ((Resource) node_A.getValue())
                .getProperty(RDFS.label).getLiteral().getLexicalForm());

        List<Tree<RDFNode>> node_A_children = node_A.getChildren();

        assertEquals("There should be two children - B1 and B2", 2, node_A_children.size());


        // ---------- check node B1

        Tree<RDFNode> node_B1 = node_A_children.get(0);

        assertFalse("Node B1 is not a leaf", node_B1.isLeaf());
        assertEquals("Unexpected URI for Node B1", URI_B1, ((Resource) node_B1.getValue()).getURI());
        assertEquals("Unexpected label for Node B1", label_B1, ((Resource) node_B1.getValue())
                .getProperty(RDFS.label).getLiteral().getLexicalForm());

        List<Tree<RDFNode>> node_B1_children = node_B1.getChildren();

        assertEquals("There should be two children - C1 and C2", 2, node_B1_children.size());


        // ---------- check node B2

        Tree<RDFNode> node_B2 = node_A_children.get(1);

        assertFalse("Node B2 is not a leaf", node_B2.isLeaf());
        assertEquals("Unexpected URI for Node B2", URI_B2, ((Resource) node_B2.getValue()).getURI());
        assertEquals("Unexpected label for Node B2", label_B2, ((Resource) node_B2.getValue())
                .getProperty(RDFS.label).getLiteral().getLexicalForm());

        List<Tree<RDFNode>> node_B2_children = node_B2.getChildren();

        assertEquals("There should be two children - C3 and C4", 2, node_B2_children.size());


        // ---------- check node C1

        Tree<RDFNode> node_C1 = node_B1_children.get(0);

        assertFalse("Node C1 is not a leaf", node_C1.isLeaf());
        assertEquals("Unexpected URI for Node C1", URI_C1, ((Resource) node_C1.getValue()).getURI());
        assertEquals("Unexpected label for Node C1", label_C1, ((Resource) node_C1.getValue())
                .getProperty(RDFS.label).getLiteral().getLexicalForm());

        List<Tree<RDFNode>> node_C1_children = node_C1.getChildren();

        assertEquals("There should be one child - D1", 1, node_C1_children.size());


        // ---------- check node C2

        Tree<RDFNode> node_C2 = node_B1_children.get(1);

        assertTrue("Node C2 is a leaf", node_C2.isLeaf());
        assertEquals("Unexpected URI for Node C2", URI_C2, ((Resource) node_C2.getValue()).getURI());
        assertEquals("Unexpected label for Node C2", label_C2, ((Resource) node_C2.getValue())
                .getProperty(RDFS.label).getLiteral().getLexicalForm());

        List<Tree<RDFNode>> node_C2_children = node_C2.getChildren();

        assertEquals("There should be no children", 0, node_C2_children.size());


        // ---------- check node C3

        Tree<RDFNode> node_C3 = node_B2_children.get(0);

        assertTrue("Node C3 is a leaf", node_C3.isLeaf());
        assertEquals("Unexpected URI for Node C3", URI_C3, ((Resource) node_C3.getValue()).getURI());
        assertEquals("Unexpected label for Node C3", label_C3, ((Resource) node_C3.getValue())
                .getProperty(RDFS.label).getLiteral().getLexicalForm());

        List<Tree<RDFNode>> node_C3_children = node_C3.getChildren();

        assertEquals("There should be no children", 0, node_C3_children.size());


        // ---------- check node C4

        Tree<RDFNode> node_C4 = node_B2_children.get(1);

        assertFalse("Node C4 is not a leaf", node_C4.isLeaf());
        assertEquals("Unexpected URI for Node C4", URI_C4, ((Resource) node_C4.getValue()).getURI());
        assertEquals("Unexpected label for Node C4", label_C4, ((Resource) node_C4.getValue())
                .getProperty(RDFS.label).getLiteral().getLexicalForm());

        List<Tree<RDFNode>> node_C4_children = node_C4.getChildren();

        assertEquals("There should be one child - D2", 1, node_C4_children.size());


        // ---------- check node D1

        Tree<RDFNode> node_D1 = node_C1_children.get(0);

        assertTrue("Node D1 is a leaf", node_D1.isLeaf());
        assertEquals("Unexpected URI for Node D1", URI_D1, ((Resource) node_D1.getValue()).getURI());
        assertEquals("Unexpected label for Node D1", label_D1, ((Resource) node_D1.getValue())
                .getProperty(RDFS.label).getLiteral().getLexicalForm());

        List<Tree<RDFNode>> node_D1_children = node_D1.getChildren();

        assertEquals("There should be no children", 0, node_D1_children.size());


        // ---------- check node D2

        Tree<RDFNode> node_D2 = node_C4_children.get(0);

        assertTrue("Node D2 is a leaf", node_D2.isLeaf());
        assertEquals("Unexpected URI for Node D2", URI_D2, ((Resource) node_D2.getValue()).getURI());
        assertEquals("Unexpected label for Node D2", label_D2, ((Resource) node_D2.getValue())
                .getProperty(RDFS.label).getLiteral().getLexicalForm());

        List<Tree<RDFNode>> node_D2_children = node_D2.getChildren();

        assertEquals("There should be no children", 0, node_D2_children.size());


    }

    @Test
    public void willPutTestsHere() {

        assertFalse(node_A.isLeaf());
    }

    private Tree<RDFNode> createTestTree() {

        Model model = ModelFactory.createDefaultModel();


        // create the resources

        Resource resource_A = model.createResource(URI_A);
        resource_A.addLiteral(RDFS.label, label_A);

        Resource resource_B1 = model.createResource(URI_B1);
        resource_B1.addLiteral(RDFS.label, label_B1);

        Resource resource_B2 = model.createResource(URI_B2);
        resource_B2.addLiteral(RDFS.label, label_B2);

        Resource resource_C1 = model.createResource(URI_C1);
        resource_C1.addLiteral(RDFS.label, label_C1);

        Resource resource_C2 = model.createResource(URI_C2);
        resource_C2.addLiteral(RDFS.label, label_C2);

        Resource resource_C3 = model.createResource(URI_C3);
        resource_C3.addLiteral(RDFS.label, label_C3);

        Resource resource_C4 = model.createResource(URI_C4);
        resource_C4.addLiteral(RDFS.label, label_C4);

        Resource resource_D1 = model.createResource(URI_D1);
        resource_D1.addLiteral(RDFS.label, label_D1);

        Resource resource_D2 = model.createResource(URI_D2);
        resource_D2.addLiteral(RDFS.label, label_D2);

        Tree<RDFNode> tree_A = new Tree<RDFNode>(resource_A);
        Tree<RDFNode> tree_B1 = new Tree<RDFNode>(resource_B1);
        Tree<RDFNode> tree_B2 = new Tree<RDFNode>(resource_B2);
        Tree<RDFNode> tree_C1 = new Tree<RDFNode>(resource_C1);
        Tree<RDFNode> tree_C2 = new Tree<RDFNode>(resource_C2);
        Tree<RDFNode> tree_C3 = new Tree<RDFNode>(resource_C3);
        Tree<RDFNode> tree_C4 = new Tree<RDFNode>(resource_C4);
        Tree<RDFNode> tree_D1 = new Tree<RDFNode>(resource_D1);
        Tree<RDFNode> tree_D2 = new Tree<RDFNode>(resource_D2);

        tree_A.addChild(tree_B1);
        tree_A.addChild(tree_B2);

        tree_B1.addChild(tree_C1);
        tree_B1.addChild(tree_C2);

        tree_B2.addChild(tree_C3);
        tree_B2.addChild(tree_C4);

        tree_C1.addChild(tree_D1);

        tree_C4.addChild(tree_D2);

        return tree_A;
    }

    final private String URI_A = "http://example.org/A";
    final private String URI_B1 = "http://example.org/B1";
    final private String URI_B2 = "http://example.org/B2";
    final private String URI_C1 = "http://example.org/C1";
    final private String URI_C2 = "http://example.org/C2";
    final private String URI_C3 = "http://example.org/C3";
    final private String URI_C4 = "http://example.org/C4";
    final private String URI_D1 = "http://example.org/D1";
    final private String URI_D2 = "http://example.org/D2";

    final private String label_A = "Node A";
    final private String label_B1 = "Node B1";
    final private String label_B2 = "Node B2";
    final private String label_C1 = "Node C1";
    final private String label_C2 = "Node C2";
    final private String label_C3 = "Node C3";
    final private String label_C4 = "Node C4";
    final private String label_D1 = "Node D1";
    final private String label_D2 = "Node D2";

    private Tree<RDFNode> node_A;
}
