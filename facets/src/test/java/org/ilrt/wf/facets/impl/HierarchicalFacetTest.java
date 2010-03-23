package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetEnvironment;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.QNameUtility;
import org.ilrt.wf.facets.constraints.Constraint;
import org.ilrt.wf.facets.constraints.ValueConstraint;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
@RunWith(JMock.class)
public class HierarchicalFacetTest extends AbstractFacetTest {

    @Before
    public void setUp() {
        mockFacetQueryService = context.mock(FacetQueryService.class);
        facetFactory = new FacetFactoryImpl(mockFacetQueryService, getPrefixMap());
        qNameUtility = new QNameUtility(getPrefixMap());
    }

    @Test
    public void createHierarchicalFacetWithoutParameter() throws FacetException {

        // the mock results that the service will return
        final List<Resource> resourceList = createMockRefinementsResourceList();

        // create the expectations for the service
        context.checking(new Expectations() {{
            oneOf(mockFacetQueryService)
                    .getRefinements(ResourceFactory.createResource(testFacetBase),
                            ResourceFactory.createProperty(testBroaderProperty), true);
            will(returnValue(resourceList));
        }});

        // create a mock environment for the facet
        FacetEnvironment environment = new FacetEnvironmentImpl(createHierarchicalConfig(),
                new HashMap<String, String[]>(), new HashMap<String, String>());

        // create the facet
        Facet facet = facetFactory.create(environment);

        // test the result
        assertNotNull("The facet should not be null", facet);
        assertEquals("Unexpected name", testName, facet.getName());
        assertEquals("Unexpected parameter name", testParamName, facet.getParam());

        // test we have refinements
        assertEquals("Unexpected number of refinements", 1,
                facet.getState().getRefinements().size());

        FacetState facetState = facet.getState().getRefinements().get(0);
        assertEquals("Unexpected label", subjectLabel, facetState.getName());
        assertEquals("unexpected value", qNameUtility.getQName(subjectUri),
                facetState.getParamValue());
        assertEquals("Unexpected parent state", facet.getState(), facetState.getParent());
        assertFalse("State is not root", facetState.isRoot());
    }


    @Test
    public void hierarchicalRefinements() {

        // we need a mock parent facet
        FacetState mockParentState = new FacetStateImpl();

        // we need to create some mock constraints
        ValueConstraint mockTypeConstraint = new ValueConstraint(RDF.type,
                ResourceFactory.createProperty("http://example.org/#Thing"));
        List<? extends Constraint> mockConstraints =
                Arrays.asList(mockTypeConstraint, mockTypeConstraint);

        // we need some mock resources that would have been returned from the service
        // *IF* we had called the public interface method
        List<Resource> resources = createMockRefinementsResourceList();

        // call the internal "protected" method and test the results
        List<FacetState> refinements = facetFactory.hierarchicalRefinements(resources,
                mockConstraints, mockParentState);

        // test the list
        assertEquals("Unexpected number of refinements", 1, refinements.size());

        // test the first object in the list
        FacetState state = refinements.get(0);
        assertFalse("Should not be root", state.isRoot());
        assertEquals("Unexpected name", subjectLabel, state.getName());
        assertEquals("Unexpected parameter value", qNameUtility.getQName(subjectUri),
                state.getParamValue());
        assertEquals("Unexpected parent", mockParentState, state.getParent());
    }


    // ---------- private helper methods

    private Map<String, String> createHierarchicalConfig() {

        Map<String, String> config = new HashMap<String, String>();
        config.put(Facet.FACET_TYPE, Facet.HIERARCHICAL_FACET_TYPE);
        config.put(Facet.FACET_TITLE, testName);
        config.put(Facet.LINK_PROPERTY, testLinkProperty);
        config.put(Facet.BROADER_PROPERTY, testBroaderProperty);
        config.put(Facet.FACET_BASE, testFacetBase);
        config.put(Facet.CONSTRAINT_TYPE, testConstraintType);
        config.put(Facet.PARAM_NAME, testParamName);
        config.put(Facet.PREFIX, testPrefix);
        return config;
    }


    // ---------- private helper methods

    private List<Resource> createMockRefinementsResourceList() {

        Model model = ModelFactory.createDefaultModel();
        Resource resource = model.createResource(subjectUri);
        resource.addLiteral(RDFS.label, subjectLabel);

        List<Resource> resources = new ArrayList<Resource>();
        resources.add(resource);

        return resources;
    }


    // ---------- private variables

    private final String testName = "Subjects";
    private final String testLinkProperty = "http://xmlns.com/foaf/0.1/topic_interest";
    private final String testBroaderProperty = "http://www.w3.org/2004/02/skos/core#broader";
    private final String testFacetBase = "http://www.ilrt.bristol.ac.uk/iugo/subjects/#disciplines";
    private final String testConstraintType = "http://www.ilrt.bristol.ac.uk/iugo#MainEvent";
    private final String testParamName = "subjects";
    private final String testPrefix = "iugosubs";
    private final String subjectUri = "http://example.org/#Subject";
    private final String subjectLabel = "A Thing";

    private final Mockery context = new JUnit4Mockery();

    private FacetQueryService mockFacetQueryService;
    private FacetFactoryImpl facetFactory;
    private QNameUtility qNameUtility;
}
