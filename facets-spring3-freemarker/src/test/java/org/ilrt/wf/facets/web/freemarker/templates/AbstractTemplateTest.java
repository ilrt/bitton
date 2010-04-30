package org.ilrt.wf.facets.web.freemarker.templates;

import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import org.ilrt.wf.facets.web.spring.controllers.AbstractController;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

/**
 * Abstract class with helper methods for running tests with FreeMarker test_templates.
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public abstract class AbstractTemplateTest {

    /**
     * Create the test configuration for the FreeMarker engine.
     *
     * @param wrapper class used for determining wrapper objects.
     * @return a FreeMarker engine configuration.
     * @throws IOException if the template directory cannot be found.
     */
    Configuration createTestConfiguration(ObjectWrapper wrapper) throws IOException {
        return createTestConfiguration(wrapper, TEMPLATES_PATH);
    }

    Configuration createTestConfiguration(ObjectWrapper wrapper, String templatePath)
            throws IOException {

        Configuration configuration = new Configuration();
        configuration.setDirectoryForTemplateLoading(new File(getClass()
                .getResource(templatePath).getFile()));
        configuration.setObjectWrapper(wrapper);
        return configuration;
    }

    /**
     * Creates a mock HTTP request and wraps it in a FreeMarker hash model.
     *
     * @param wrapper class used for determining wrapper objects.
     * @return a FreeMarker hash model that wraps a mock http request.
     */
    HttpRequestHashModel createHttpRequestHashModel(ObjectWrapper wrapper) {

        // mock the http request
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.setRequestURI(baseUri);
        return new HttpRequestHashModel(httpServletRequest,
                wrapper);
    }

    /**
     * @return a Spring ModelAndView object passed to the template.
     */
    ModelAndView createModelAndView() {
        ModelAndView mav = new ModelAndView(TEST_VIEW_NAME);
        mav.addObject(AbstractController.CONTEXT_PATH_KEY, TEST_CONTEXT_PATH);
        return mav;
    }

    private final String TEMPLATES_PATH = "/templates/";
    private final String TEST_CONTEXT_PATH = "/resrev";
    private final String TEST_VIEW_NAME = "mainView";
    private final String baseUri = "/list.do";
}
