package org.ilrt.wf.facets.web.spring.controller;

import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.FacetViewService;
import org.ilrt.wf.facets.FacetViewServiceException;
import org.ilrt.wf.facets.impl.FacetViewImpl;
import org.ilrt.wf.facets.web.spring.controllers.ResRevController;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
@RunWith(JMock.class)
public class ResRevControllerTest {

    @Before
    public void setUp() {

        controller = new ResRevController(facetViewService, facetQueryService);
    }


    @Test
    public void test() throws FacetViewServiceException {

        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath(contextPath);

        context.checking(new Expectations() {{
            oneOf(facetViewService).generate(request);
            will(returnValue(new FacetViewImpl()));
            oneOf(facetViewService).getViewType(request);
            will(returnValue(new String()));
        }});

        ModelAndView mav = controller.mainView(request);

        assertEquals("Unexpected view name", ResRevController.MAIN_VIEW_NAME, mav.getViewName());
        assertEquals("Unexpected context path", contextPath,
                mav.getModel().get(ResRevController.CONTEXT_PATH_KEY));

        assertNotNull("The view data should not be null",
                mav.getModel().get(ResRevController.FACET_VIEW_KEY));
    }


    private ResRevController controller;

    private String contextPath = "resrev";

    private final Mockery context = new JUnit4Mockery();
    private final FacetViewService facetViewService = context.mock(FacetViewService.class);
    private final FacetQueryService facetQueryService = context.mock(FacetQueryService.class);
}
