package org.ilrt.wf.facets.web.freemarker;

import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.freemarker.FacetParentListMethod;
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
import static org.junit.Assert.assertTrue;

public class FacetTemplateTest {

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

        // should be added by the framework?
        mav.addObject("Request", requestHashModel);


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

        FacetStateImpl facetTwoParentState = new FacetStateImpl();
        facetTwoParentState.setRoot(true);

        FacetState parentState = createTestState(false, "History", "subjects:history", 0,
                facetTwoParentState);

        FacetState currentState = createTestState(false, "British History",
                "subjects:british_history", 0, parentState);

        FacetState refinementOne = createTestState(false, "Medieval",
                "subjects:british_history_medieval", 3, currentState);
        FacetState refinementTwo = createTestState(false, "Early Modern",
                "subjects:british_history_early_modern", 3, currentState);
        FacetState refinementThree = createTestState(false, "Modern",
                "subjects:british_history_modern", 4, currentState);

        currentState.getRefinements().add(refinementOne);
        currentState.getRefinements().add(refinementTwo);
        currentState.getRefinements().add(refinementThree);

        Facet facetTwo = new FacetImpl("Subject Areas", currentState, "subjects");


        // ------------ alpha numeric

        FacetStateImpl facetThreeParentState = new FacetStateImpl();
        facetThreeParentState.setRoot(true);

        FacetState facetThreeCurrentState = createTestState(false, "Z*", "z*", 0,
                facetThreeParentState);

        Facet facetThree = new FacetImpl("Staff", facetThreeCurrentState, "staff");


        // ------------- a completely selected hierarchical facet

        FacetStateImpl facetFourRootState = new FacetStateImpl();
        facetFourRootState.setRoot(true);

        FacetState facetFourGreatGrandparentState = createTestState(false, "Europe", "loc:europe",
                0, facetFourRootState);

        FacetState facetFourGrandparentState = createTestState(false, "United Kingdom", "loc:uk",
                0, facetFourGreatGrandparentState);

        FacetState facetFourParentState = createTestState(false, "England", "loc:england",
                0, facetFourGrandparentState);

        FacetState facetFourCurrentState = createTestState(false, "Bristol", "loc:bristol",
                0, facetFourParentState);

        Facet facetFour = new FacetImpl("Location", facetFourCurrentState, "location");


        // ---------- add to view

        facetView.getFacets().add(facetOne);
        facetView.getFacets().add(facetTwo);
        facetView.getFacets().add(facetThree);
        facetView.getFacets().add(facetFour);

        // update mav
        mav.addObject("facetView", facetView);
        mav.addObject("facetStateUrl", new FacetStateUrlMethod());
        mav.addObject("facetParentList", new FacetParentListMethod());


        // ---------- run the template

        Template template = configuration.getTemplate(TEMPLATE_NAME);
        StringWriter writer = new StringWriter();
        template.process(mav.getModel(), writer);

        String output = writer.getBuffer().toString();

        writer.flush();

        //System.out.println(output);

        XPath engine = XPathFactory.newInstance().newXPath();

        // check the first div is the container via its id
        assertEquals("Unexpected container", "facetContainer", engine.evaluate("/div/@id",
                new InputSource(new StringReader(output))));

        // there that there are four div tags within the container
        assertEquals("Unexpected number of child divs", "4", engine.evaluate("count(/div/div)",
                new InputSource(new StringReader(output))));

        // check that the four div tags within the container hold facets by checking the class
        assertEquals("Unexpected class for divs", "true", engine.evaluate("/div/div/@class='facet'",
                new InputSource(new StringReader(output))));

        // check title of the first div - departments
        assertEquals("Unexpected title", departments_title, engine.evaluate("/div/div[1]/h3/text()",
                new InputSource(new StringReader(output))));

        // check the number of refinements for departments
        assertEquals("Unexpected number of refinements", "2",
                engine.evaluate("count(/div/div[1]/ul/li)",
                        new InputSource(new StringReader(output))));

        // check title of the second div - subjects
        assertEquals("Unexpected title", "Subject Areas", engine.evaluate("/div/div[2]/h3/text()",
                new InputSource(new StringReader(output))));

        // check the selected subject
        assertTrue(engine.evaluate("/div/div[2]/ul/li/ul/li/text()",
                new InputSource(new StringReader(output))).startsWith("British History"));

        // check the number of refinements for subjects
        assertEquals("Unexpected number of refinements", "3",
                engine.evaluate("count(/div/div[2]/ul/li/ul/li/ul/li)",
                        new InputSource(new StringReader(output))));

        // check title of the third div - staff
        assertEquals("Unexpected title", "Staff", engine.evaluate("/div/div[3]/h3/text()",
                new InputSource(new StringReader(output))));

        // check the selected letter for staff
        assertTrue(engine.evaluate("/div/div[3]/ul/li/text()",
                new InputSource(new StringReader(output))).startsWith("Z*"));

        // check title of the fourth div - location
        assertEquals("Unexpected title", "Location", engine.evaluate("/div/div[4]/h3/text()",
                new InputSource(new StringReader(output))));


        // check the top selected location
        assertTrue(engine.evaluate("/div/div[4]/ul/li/text()",
                new InputSource(new StringReader(output))).startsWith("Europe"));


        // check the latest selected location
        assertTrue(engine.evaluate("/div/div[4]/ul/li/ul/li/ul/li/ul/li/text()",
                new InputSource(new StringReader(output))).startsWith("Bristol"));

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

    private final String baseUri = "/list.do";

    private final String departments_title = "Departments";

}
