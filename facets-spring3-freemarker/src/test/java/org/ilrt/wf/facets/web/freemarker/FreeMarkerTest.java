package org.ilrt.wf.facets.web.freemarker;

import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.freemarker.FacetStateUrlMethod;
import org.ilrt.wf.facets.impl.FacetImpl;
import org.ilrt.wf.facets.impl.FacetStateImpl;
import org.ilrt.wf.facets.impl.FacetViewImpl;
import org.ilrt.wf.facets.web.spring.controllers.AbstractController;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class FreeMarkerTest {

    @Test
    public void test() throws IOException, TemplateException, XPathExpressionException {

        // wrapper used by freemarker
        ObjectWrapper wrapper = new DefaultObjectWrapper();

        // configure to find templates
        Configuration configuration = new Configuration();
        configuration.setDirectoryForTemplateLoading(new File(getClass()
                .getResource(TEMPLATES_PATH).getFile()));
        configuration.setObjectWrapper(wrapper);

        // mock the http request
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.setRequestURI(baseUri);
        HttpRequestHashModel requestHashModel = new HttpRequestHashModel(httpServletRequest,
                wrapper);

        // create a model and view
        ModelAndView mav = new ModelAndView(TEST_VIEW_NAME);
        mav.addObject(AbstractController.CONTEXT_PATH_KEY, TEST_CONTEXT_PATH);
        mav.addObject("Request", requestHashModel); // should be added by the framework?


        // mock view object
        FacetViewImpl facetView = new FacetViewImpl();


        // ---------- departments facet

        FacetStateImpl facetOneRootState = new FacetStateImpl();
        facetOneRootState.setRoot(true);

        FacetState facetOneRefinementOne = createTestState(false, "History and Archeology", "depts:history",
                10, facetOneRootState);
        facetOneRootState.getRefinements().add(facetOneRefinementOne);

        FacetState facetOneRefinementTwo = createTestState(false, "Geography", "depts:geography",
                10, facetOneRootState);
        facetOneRootState.getRefinements().add(facetOneRefinementTwo);

        FacetImpl facetOne = new FacetImpl("Departments", facetOneRootState, "depts");


        // ----------- subjects facet

        FacetStateImpl facetTwoRootState = new FacetStateImpl();
        facetTwoRootState.setRoot(true);

        FacetState facetTwoRefinementOne = createTestState(false, "History", "subjects:history",
                10, facetTwoRootState);
        facetTwoRootState.getRefinements().add(facetTwoRefinementOne);

        FacetState facetTwoRefinementTwo = createTestState(false, "Human Geography", "subjects:geography",
                10, facetTwoRootState);
        facetTwoRootState.getRefinements().add(facetTwoRefinementTwo);

        FacetImpl facetTwo = new FacetImpl("Subject Areas", facetTwoRootState, "subjects");


        // ---------- add to view

        facetView.getFacets().add(facetOne);
        facetView.getFacets().add(facetTwo);


        // update mav
        mav.addObject("facetView", facetView);
        mav.addObject("facetStateUrl", new FacetStateUrlMethod());


        // ---------- run the template

        Template template = configuration.getTemplate(TEMPLATE_NAME);
        StringWriter writer = new StringWriter();
        template.process(mav.getModel(), writer);

        String output = writer.getBuffer().toString();

        writer.flush();

        XPath engine = XPathFactory.newInstance().newXPath();


        // check the container id
        assertEquals("Unexpected container", "facetContainer", engine.evaluate("/div/@id",
                new InputSource(new StringReader(output))));

        // there should be 2 div tags for the facets
        assertEquals("Unexpected number of child divs", "2", engine.evaluate("count(/div/div)",
                new InputSource(new StringReader(output))));

        // check title
        assertEquals("Unexpected title", departments_title, engine.evaluate("//div/h3/text()",
                new InputSource(new StringReader(output))));

        // check number of expected nodes
        assertEquals("Unexpected title", "2", engine.evaluate("count(//div/ul)",
                new InputSource(new StringReader(output))));

    }

    private FacetState createTestState(boolean isRoot, String name, String paramValue,
                                       int count, FacetState parent) {
        FacetStateImpl state = new FacetStateImpl();
        state.setRoot(isRoot);
        state.setName(name);
        state.setParamValue(paramValue);
        state.setCount(count);
        state.setParent(parent);
        return state;
    }


    private final String TEMPLATES_PATH = "/templates/";
    private final String TEMPLATE_NAME = "includes/facet.ftl";
    private final String TEST_VIEW_NAME = "mainView";
    private final String TEST_CONTEXT_PATH = "/resrev";

    final String baseUri = "/list.do";

    final String departments_title = "Departments";

}
