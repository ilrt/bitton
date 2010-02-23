package org.ilrt.wf.facets;

import org.ilrt.wf.facets.impl.FacetServiceImpl;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
@RunWith(JMock.class)
public class FacetServiceImplTest {

    Mockery context = new JUnit4Mockery();

    @Test
    public void generate() {

        final FacetQueryService facetQueryService = context.mock(FacetQueryService.class);

        context.checking(new Expectations() {{
            oneOf(facetQueryService).getRefinements(null);
        }});


        FacetService facetService = new FacetServiceImpl(facetQueryService);
        facetService.generate(null);


    }

}
