package org.ilrt.wf.facets.impl;

import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetConstraint;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetFactory;
import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.impl.FacetFactoryImpl;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

@RunWith(JMock.class)
public class FacetFactoryImplTest {


    /**
     * Tests to see that the factory will throw an exception if it doesn't recognize the
     * facet type specified in a configuration file.
     *
     * @throws org.ilrt.wf.facets.FacetException   expected exception for this test
     */
    @Test(expected = FacetException.class)
    public void unrecognizedFacetType() throws FacetException {

        final FacetQueryService mockQueryService = context.mock(FacetQueryService.class);

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

        FacetFactory facetFactory = new FacetFactoryImpl(mockQueryService);
        facetFactory.create(mockConstraint);
    }


    @Test
    public void expectedAlphaNumericArray() {

        final FacetQueryService mockQueryService = context.mock(FacetQueryService.class);
        FacetFactory facetFactory = new FacetFactoryImpl(mockQueryService);


       // AssertEquals("Unexpected array size", 26, facetFactory.);
    }

    // mock context
    private final Mockery context = new JUnit4Mockery();
}
