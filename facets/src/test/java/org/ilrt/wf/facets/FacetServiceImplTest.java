package org.ilrt.wf.facets;

import org.ilrt.wf.facets.config.Configuration;
import org.ilrt.wf.facets.impl.FacetViewServiceImpl;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
@RunWith(JMock.class)
public class FacetServiceImplTest {

    @Before
    public void setUp() {

        // add the core request components
        request = new MockHttpServletRequest();
        request.setContextPath(contextPath);
        request.setRequestURI(requestURI);
        request.setMethod(httpMethod);

        // sanity check - probably overkill
        assertEquals("Unexpected requestURI", requestURI, request.getRequestURI());
        assertEquals("Unexpected HTTP method", httpMethod, request.getMethod());
    }


    @Test
    public void generate() {

        // mock the Configuration.class

        final Configuration configuration = context.mock(Configuration.class);

        context.checking(new Expectations() {{
            oneOf(configuration).configKeys();
        }});


        // mock the FacetFactory.class

        final FacetFactory facetFactory = context.mock(FacetFactory.class);

        // test the service

        FacetViewService facetViewService = new FacetViewServiceImpl(facetFactory, configuration);
//        FacetView facetView = facetViewService.generate(request);


//        assertNotNull("The facet view should never be null", facetView);
    }

    // standard http request components used in the tests
    final private String contextPath = "/facets/";
    final private String requestURI = contextPath + "list.do";
    final private String httpMethod = "GET";

    // request used in each test
    MockHttpServletRequest request = null;

    // mock context
    Mockery context = new JUnit4Mockery();
}
