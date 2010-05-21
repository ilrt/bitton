package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDFS;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetQueryService.Tree;
import org.ilrt.wf.facets.FacetState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public abstract class AbstractFacetTest {

    Map<FacetState, Integer> getCounts(List<? extends FacetState> states) {

        Map<FacetState, Integer> results = new HashMap<FacetState, Integer>();

        // go through root states to get refinements
        for (FacetState facetState : states) {
            for (FacetState refinementState : facetState.getRefinements()) {
                // set everything with a count of 5
                results.put(refinementState, 5);
            }
        }

        return results;
    }

    List<FacetState> currentStates(List<Facet> facetList) {

        List<FacetState> states = new ArrayList<FacetState>();

        for (Facet facet : facetList) {
            states.add(facet.getState());
        }

        return states;
    }

    Map<String, String> getPrefixMap() {

        Map<String, String> map = new HashMap<String, String>();
        map.put(foafPrefix, foafUri);
        map.put(exPrefix, exUri);
        return map;
    }

    Tree<Resource> createTestTree() {

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

        Tree<Resource> tree_A = new Tree<Resource>(resource_A);
        Tree<Resource> tree_B1 = new Tree<Resource>(resource_B1);
        Tree<Resource> tree_B2 = new Tree<Resource>(resource_B2);
        Tree<Resource> tree_C1 = new Tree<Resource>(resource_C1);
        Tree<Resource> tree_C2 = new Tree<Resource>(resource_C2);
        Tree<Resource> tree_C3 = new Tree<Resource>(resource_C3);
        Tree<Resource> tree_C4 = new Tree<Resource>(resource_C4);
        Tree<Resource> tree_D1 = new Tree<Resource>(resource_D1);
        Tree<Resource> tree_D2 = new Tree<Resource>(resource_D2);

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

    final String URI_A = "http://example.org/A";
    final String URI_B1 = "http://example.org/B1";
    final String URI_B2 = "http://example.org/B2";
    final String URI_C1 = "http://example.org/C1";
    final String URI_C2 = "http://example.org/C2";
    final String URI_C3 = "http://example.org/C3";
    final String URI_C4 = "http://example.org/C4";
    final String URI_D1 = "http://example.org/D1";
    final String URI_D2 = "http://example.org/D2";

    final String label_A = "Node A";
    final String label_B1 = "Node B1";
    final String label_B2 = "Node B2";
    final String label_C1 = "Node C1";
    final String label_C2 = "Node C2";
    final String label_C3 = "Node C3";
    final String label_C4 = "Node C4";
    final String label_D1 = "Node D1";
    final String label_D2 = "Node D2";

    private final String foafPrefix = "foaf";
    private final String foafUri = "http://xmlns.com/foaf/0.1/Person";

    private final String exPrefix = "ex";
    private final String exUri = "http://example.org/";
}
