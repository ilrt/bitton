package org.ilrt.wf.facets.web.freemarker.templates;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDFS;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.ilrt.wf.facets.freemarker.JenaObjectWrapper;
import org.ilrt.wf.facets.impl.FacetViewImpl;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ResultsListTemplateTest extends AbstractTemplateTest {

    @Test
    public void test() throws IOException, TemplateException, XPathExpressionException {

        // ---------- Set up FreeMarker

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

        // ---------- Set up data

        List<Resource> results = new ArrayList<Resource>();

        Model model = ModelFactory.createDefaultModel();

        Resource resourceOne = model.createResource(uriOne);
        resourceOne.addLiteral(RDFS.label, labelOne);
        results.add(resourceOne);

        Resource resourceTwo = model.createResource(uriTwo);
        resourceTwo.addLiteral(RDFS.label, labelTwo);
        results.add(resourceTwo);

        Resource resourceThree = model.createResource(uriThree);
        resourceThree.addLiteral(RDFS.label, labelThree);
        results.add(resourceThree);

        Resource resourceFour = model.createResource(uriFour);
        resourceFour.addLiteral(RDFS.label, labelFour);
        results.add(resourceFour);

        Resource resourceFive = model.createResource(uriFive);
        resourceFive.addLiteral(RDFS.label, labelFive);
        results.add(resourceFive);

        FacetViewImpl facetView = new FacetViewImpl();
        facetView.setResults(results);

        mav.addObject("facetView", facetView);
        mav.addObject("servletPath", "");
        mav.addObject("RequestParameters", new HashMap());

        // ---------- run the template

        Template template = configuration.getTemplate(TEMPLATE_NAME);
        StringWriter writer = new StringWriter();
        template.process(mav.getModel(), writer);

        String output = writer.getBuffer().toString();

        //System.out.println(output);

        writer.flush();

        System.out.println(output);

        // ---------- run the tests

        XPath engine = XPathFactory.newInstance().newXPath();

        // check we have the expected label
        assertEquals("Unexpected label", labelOne, engine.evaluate("/div/div[1]/p/a/text()",
                new InputSource(new StringReader(output))).trim());

        // check we have the expected label
        assertEquals("Unexpected label", labelTwo, engine.evaluate("/div/div[2]/p/a/text()",
                new InputSource(new StringReader(output))).trim());

        // check we have the expected label
        assertEquals("Unexpected label", labelThree, engine.evaluate("/div/div[3]/p/a/text()",
                new InputSource(new StringReader(output))).trim());

        // check we have the expected label
        assertEquals("Unexpected label", labelFour, engine.evaluate("/div/div[4]/p/a/text()",
                new InputSource(new StringReader(output))).trim());

        // check we have the expected label
        assertEquals("Unexpected label", labelFive, engine.evaluate("/div/div[5]/p/a/text()",
                new InputSource(new StringReader(output))).trim());
    }

    private final String uriOne = "http://example/item/1/";
    private final String uriTwo = "http://example/item/2/";
    private final String uriThree = "http://example/item/3/";
    private final String uriFour = "http://example/item/4/";
    private final String uriFive = "http://example/item/5/";

    private final String labelOne = "Label One";
    private final String labelTwo = "Label Two";
    private final String labelThree = "Label Three";
    private final String labelFour = "Label Four";
    private final String labelFive = "Label Five";

    private final String TEMPLATE_NAME = "includes/resultsList.ftl";
}
