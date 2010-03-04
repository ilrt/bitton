package org.ilrt.wf.facets.impl;

import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetConstraint;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.FacetState;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JMock.class)
public class FacetFactoryImplTest {


    @Before
    public void setUp() {
        final FacetQueryService mockQueryService = context.mock(FacetQueryService.class);
        facetFactory = new FacetFactoryImpl(mockQueryService);
    }

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


    @Test
    public void expectedAlphaNumericArray() {

        assertEquals("Unexpected array size", MAX_ALPHANUMERIC_ITEMS,
                facetFactory.alphaNumericArray().length);
    }

    @Test
    public void alphaNumericLabel() {

        assertEquals("Unexpected label", label, facetFactory.alphaNumericLabel(c));
    }

    @Test
    public void alphaNumericConstraint() {

        assertEquals("Unexpected constraint", constraint,
                facetFactory.alphaNumericConstraint(c).getRegexp());
    }

    @Test
    public void alphaNumericRefinements() {

        final String linkProperty = "http://http://purl.org/dc/elements/1.1/title";

        List<FacetState> states = facetFactory.alphaNumericRefinements(linkProperty);

        // check we have the expected number of elements

        assertEquals("Unexpected array size", MAX_ALPHANUMERIC_ITEMS,
                states.size());

        // check one of the items ...

        FacetStateImpl state = (FacetStateImpl)states.get(10);

        assertEquals("Unexpected label", label, state.getName());
        assertNotNull("Expected constraint", state.getConstraints());
        assertEquals("Unexpected link property", linkProperty, state.getLinkProperty().getURI());
    }


    // mock context
    private final Mockery context = new JUnit4Mockery();

    // common variables used in a number of tests
    private final char c = 'A';
    private final String label = "A*";
    private final String constraint = "^A";
    private final int MAX_ALPHANUMERIC_ITEMS = 36;

    private FacetFactoryImpl facetFactory;
}
