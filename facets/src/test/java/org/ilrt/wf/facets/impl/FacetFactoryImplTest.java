package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetConstraint;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.constraints.ValueConstraint;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JMock.class)
public class FacetFactoryImplTest {


    @Before
    public void setUp() {
        final FacetQueryService mockQueryService = context.mock(FacetQueryService.class);
        facetFactory = new FacetFactoryImpl(mockQueryService);
    }

    /**
    @Test
    public void createAlphaNumericFacetWithRefinements() throws FacetException {

        Map<String, String> config = new HashMap<String, String>();
        config.put(Facet.FACET_TYPE, Facet.ALPHA_NUMERIC_FACET_TYPE);

        Map<String, String[]> parameters = new HashMap<String, String[]>();

        FacetConstraintImpl facetConstraint = new FacetConstraintImpl(config, parameters);

        Facet facet = facetFactory.create(facetConstraint);

    }
    **/

    /**
     * Tests to see that the factory will throw an exception if it doesn't recognize the
     * facet type specified in a configuration file.
     *
     * @throws org.ilrt.wf.facets.FacetException
     *          expected exception for this test
     */
    @Test(expected = FacetException.class)
    public void unrecognizedFacetType() throws FacetException {

        // mock the configuration - it will return an unrecognized facet type

        final Map mockConfig = context.mock(Map.class);

        context.checking(new Expectations() {{
            oneOf(mockConfig).get(Facet.FACET_TYPE);
            will(returnValue("NonExistentFacetType"));
        }});

        // mock a constraint - encapsulates the configuration

        final FacetConstraint mockConstraint = context.mock(FacetConstraint.class);

        context.checking(new Expectations() {{
            oneOf(mockConstraint).getConfig();
            will(returnValue(mockConfig));
        }});

        // test the service

        facetFactory.create(mockConstraint);
    }

    /**
     * Is the alpha-numeric array is correct?
     */
    @Test
    public void expectedAlphaNumericArray() {

        assertEquals("Unexpected array size", MAX_ALPHANUMERIC_ITEMS,
                facetFactory.alphaNumericArray().length);
    }

    /**
     * Is label for a character in the correct format?
     */
    @Test
    public void alphaNumericLabel() {

        assertEquals("Unexpected label", label, facetFactory.alphaNumericLabel(c));
    }

    /**
     * Is the constraint for a character in the correct format?
     */
    @Test
    public void alphaNumericConstraint() {

        assertEquals("Unexpected constraint", constraint,
                facetFactory.alphaNumericConstraint(ResourceFactory.
                        createProperty(linkProperty), c).getRegexp());
    }

    /**
     * Is the constraint for a character in the correct format?
     */
    @Test
    public void alphaNumericConstraintFromLabel() {

        assertEquals("Unexpected constraint", constraint,
                facetFactory.alphaNumericConstraint(ResourceFactory.
                        createProperty(linkProperty), label).getRegexp());
    }

    /**
     * Test the refinements for an alpha-numeric facet are correct.
     */
    @Test
    public void alphaNumericRefinements() {

        // pseudo root state
        final FacetStateImpl rootState = new FacetStateImpl();
        rootState.setRoot(true);

        // property used in each state
        Property p = ResourceFactory.createProperty(linkProperty);

        // get the states
        List<FacetState> states =
                facetFactory.alphaNumericRefinements(new ValueConstraint(RDF.type,
                        ResourceFactory.createProperty(typeProperty)), p, rootState);

        // check we have the expected number of elements
        assertEquals("Unexpected array size", MAX_ALPHANUMERIC_ITEMS,
                states.size());

        // check one of the items ...
        FacetStateImpl state = (FacetStateImpl) states.get(10);
        assertEquals("Unexpected label", label, state.getName());
        assertEquals("Unexpected number of constraints", 2, state.getConstraints().size());
        assertFalse("The state should not assert that it is a parent", state.isRoot());
        assertTrue("The parent should assert it is the root", state.getParent().isRoot());
        assertEquals("Unexpected param value", label, state.getParamValue());
    }


    // mock context
    private final Mockery context = new JUnit4Mockery();

    // common variables used in a number of tests
    private final char c = 'A';
    private final String label = "A*";
    private final String constraint = "^A";
    private final int MAX_ALPHANUMERIC_ITEMS = 36;
    private final String linkProperty = "http://http://purl.org/dc/elements/1.1/title";
    private final String typeProperty = "http://example.org/vocab/#Thingy";

    private FacetFactoryImpl facetFactory;
}
