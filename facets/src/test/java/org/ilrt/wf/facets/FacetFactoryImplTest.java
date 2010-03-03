package org.ilrt.wf.facets;

import org.ilrt.wf.facets.impl.FacetFactoryImpl;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNull;

@RunWith(JMock.class)
public class FacetFactoryImplTest {


    @Test
    public void createFacetState() {

        // mock the FacetQueryService.class and set the expectations

        final FacetQueryService facetQueryService = context.mock(FacetQueryService.class);
/**
        context.checking(new Expectations() {{
            oneOf(facetQueryService).getRefinements(null);
        }});
**/
        FacetFactory facetFactory = new FacetFactoryImpl(facetQueryService);

        Facet facet = facetFactory.create(null);

        assertNull(facet);
    }

    // mock context
    Mockery context = new JUnit4Mockery();
}
