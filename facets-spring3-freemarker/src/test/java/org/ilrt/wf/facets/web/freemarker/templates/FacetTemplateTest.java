package org.ilrt.wf.facets.web.freemarker.templates;

import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.freemarker.FacetParentListMethod;
import org.ilrt.wf.facets.freemarker.FacetStateUrlMethod;
import org.ilrt.wf.facets.freemarker.JenaObjectWrapper;
import org.ilrt.wf.facets.impl.FacetImpl;
import org.ilrt.wf.facets.impl.FacetStateImpl;
import org.ilrt.wf.facets.impl.FacetViewImpl;
import org.junit.Test;
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

/**
 * A test to check the FreeMarker template for rendering facet states. We don't check all facets, just a
 * few key test_templates.
 * <p/>
 * The facets are displayed in nested lists - we create mock data that represent different possible states
 * and use XPath to check we have the expected results.
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class FacetTemplateTest extends AbstractTemplateTest {

    @Test
    public void test() throws IOException, TemplateException, XPathExpressionException {

        // wrapper used by FreeMarker
        ObjectWrapper wrapper = new JenaObjectWrapper();

        // configure to find test_templates
        Configuration configuration = createTestConfiguration(wrapper);

        // mock the http request
        HttpRequestHashModel requestHashModel = createHttpRequestHashModel(wrapper);

        // create a model and view
        ModelAndView mav = createModelAndView();

        // should be added by the framework?
        mav.addObject("Request", requestHashModel);

        // mock view object
        FacetViewImpl facetView = new FacetViewImpl();


        // ---------- departments facet

        FacetState facetOneRootState = createTestState(true, null, null, 0, null);

        FacetState facetOneRefinementOne = createTestState(false, "History and Archeology", "depts:history",
                10, facetOneRootState);
        facetOneRootState.getRefinements().add(facetOneRefinementOne);

        FacetState facetOneRefinementTwo = createTestState(false, "Geography", "depts:geography",
                10, facetOneRootState);
        facetOneRootState.getRefinements().add(facetOneRefinementTwo);

        FacetImpl facetOne = new FacetImpl("Departments", facetOneRootState, "depts");


        // ----------- subjects facet

        FacetState facetTwoParentState = createTestState(true, null, null, 0, null);

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

        Facet facetTwo = new FacetImpl(subjects_title, currentState, "subjects");


        // ------------ alpha numeric

        FacetState facetThreeParentState = createTestState(true, null, null, 0, null);

        FacetState facetThreeCurrentState = createTestState(false, "Z*", "z*", 0,
                facetThreeParentState);

        Facet facetThree = new FacetImpl(staff_title, facetThreeCurrentState, "staff");


        // ------------- a completely selected hierarchical facet

        FacetState facetFourRootState = createTestState(true, null, null, 0, null);

        FacetState facetFourGreatGrandparentState = createTestState(false, europe_title, "loc:europe",
                0, facetFourRootState);

        FacetState facetFourGrandparentState = createTestState(false, "United Kingdom", "loc:uk",
                0, facetFourGreatGrandparentState);

        FacetState facetFourParentState = createTestState(false, "England", "loc:england",
                0, facetFourGrandparentState);

        FacetState facetFourCurrentState = createTestState(false, bristol_label, "loc:bristol",
                0, facetFourParentState);

        Facet facetFour = new FacetImpl(location_title, facetFourCurrentState, "location");


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
        assertEquals("Unexpected title", subjects_title, engine.evaluate("/div/div[2]/h3/text()",
                new InputSource(new StringReader(output))));

        // check the selected subject
        assertTrue(engine.evaluate("/div/div[2]/ul/li/ul/li/text()",
                new InputSource(new StringReader(output))).startsWith("British History"));

        // check the number of refinements for subjects
        assertEquals("Unexpected number of refinements", "3",
                engine.evaluate("count(/div/div[2]/ul/li/ul/li/ul/li)",
                        new InputSource(new StringReader(output))));

        // check title of the third div - staff
        assertEquals("Unexpected title", staff_title, engine.evaluate("/div/div[3]/h3/text()",
                new InputSource(new StringReader(output))));

        // check the selected letter for staff
        assertTrue(engine.evaluate("/div/div[3]/ul/li/text()",
                new InputSource(new StringReader(output))).startsWith("Z*"));

        // check title of the fourth div - location
        assertEquals("Unexpected title", location_title, engine.evaluate("/div/div[4]/h3/text()",
                new InputSource(new StringReader(output))));


        // check the top selected location
        assertTrue(engine.evaluate("/div/div[4]/ul/li/text()",
                new InputSource(new StringReader(output))).startsWith(europe_title));


        // check the latest selected location
        assertTrue(engine.evaluate("/div/div[4]/ul/li/ul/li/ul/li/ul/li/text()",
                new InputSource(new StringReader(output))).startsWith(bristol_label));

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

    private final String TEMPLATE_NAME = "includes/facet.ftl";

    private final String departments_title = "Departments";
    private final String subjects_title = "Subject Areas";
    private final String staff_title = "Staff";
    private final String location_title = "Location";
    private final String europe_title = "Europe";
    private final String bristol_label = "Bristol";

}
