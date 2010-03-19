package org.ilrt.wf.facets.impl;

import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetEnvironment;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetQueryService;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

@RunWith(JMock.class)
public class FacetFactoryImplTest {

    @Before
    public void setUp() {
        FacetQueryService mockQueryService = context.mock(FacetQueryService.class);
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

        final FacetEnvironment mockEnvironment = context.mock(FacetEnvironment.class);

        context.checking(new Expectations() {{
            oneOf(mockEnvironment).getConfig();
            will(returnValue(mockConfig));
        }});

        // test the service

        facetFactory.create(mockEnvironment);
    }


    // ----------- private variables

    // mock context
    private final Mockery context = new JUnit4Mockery();

    private FacetFactoryImpl facetFactory;
}
