package org.ilrt.wf.facets.web.freemarker;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.ilrt.wf.facets.impl.FacetImpl;
import org.ilrt.wf.facets.impl.FacetStateImpl;
import org.ilrt.wf.facets.impl.FacetViewImpl;
import org.ilrt.wf.facets.web.spring.controllers.AbstractController;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class FreeMarkerTest {

    @Test
    public void test() throws IOException, TemplateException {

        Configuration configuration = new Configuration();
        configuration.setDirectoryForTemplateLoading(new File(getClass().getResource(TEMPLATES_PATH).getFile()));
        configuration.setObjectWrapper(new DefaultObjectWrapper());

        ModelAndView mav = new ModelAndView(TEST_VIEW_NAME);
        mav.addObject(AbstractController.CONTEXT_PATH_KEY, TEST_CONTEXT_PATH);

        FacetViewImpl facetView = new FacetViewImpl();


        //FacetStateImpl facetOneRootState = new FacetStateImpl();
        //facetOneRootState.setRoot(true);

        FacetImpl facetOne = new FacetImpl("Departments", null, "depts");
        FacetImpl facetTwo = new FacetImpl("Subject Areas", null, "subject");
        FacetImpl facetThree = new FacetImpl("Staff Names", null, "names");

        facetView.getFacets().add(facetOne);
        facetView.getFacets().add(facetTwo);
        facetView.getFacets().add(facetThree);


        mav.addObject("facetView", facetView);

        Template template = configuration.getTemplate(TEMPLATE_NAME);

        Writer writer = new OutputStreamWriter(System.out);

        template.process(mav.getModel(), writer);
        writer.flush();

        assertTrue(true);
    }

    private final String TEMPLATES_PATH = "/templates/";
    private final String TEMPLATE_NAME = "includes/facet.ftl";
    private final String TEST_VIEW_NAME = "mainView";
    private final String TEST_CONTEXT_PATH = "/resrev";

}
