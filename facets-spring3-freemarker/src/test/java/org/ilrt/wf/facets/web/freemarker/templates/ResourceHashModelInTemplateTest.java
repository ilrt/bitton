package org.ilrt.wf.facets.web.freemarker.templates;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.RDFS;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.ilrt.wf.facets.freemarker.JenaObjectWrapper;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import static org.junit.Assert.assertTrue;

/**
 * Test for the objects that wrap Jena objects so they can be rendered in a FreeMarker template.
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class ResourceHashModelInTemplateTest {

    @Test
    public void test() throws IOException, TemplateException {

        // wrapper used by FreeMarker
        ObjectWrapper wrapper = new JenaObjectWrapper();

        // configure to find templates
        Configuration configuration = new Configuration();
        configuration.setDirectoryForTemplateLoading(new File(getClass()
                .getResource(TEMPLATES_PATH).getFile()));
        configuration.setObjectWrapper(wrapper);

        // create a model and view
        ModelAndView mav = new ModelAndView();

        Model model = ModelFactory.createDefaultModel();

        // resources
        Resource resource = model.createResource("http://example.org/1/");
        Resource person = model.createResource("http://example.org/person/1");

        // properties
        Property hasPersonProperty = model.createProperty(hasPersonUri);
        Property foafNameProperty = model.createProperty(foafName);

        // statements
        Statement stmtOne = model.createStatement(resource, RDFS.label, "Example Label");
        Statement stmtTwo = model.createStatement(resource, hasPersonProperty, person);
        Statement stmtThree = model.createStatement(person, foafNameProperty, "Fred Smith");

        model.add(stmtOne);
        model.add(stmtTwo);
        model.add(stmtThree);

        System.out.println(model.toString());

        mav.addObject("resource", resource);

        // ---------- run the template

        Template template = configuration.getTemplate(TEMPLATE_NAME);
        StringWriter writer = new StringWriter();
        template.process(mav.getModel(), writer);

        String output = writer.getBuffer().toString();

        writer.flush();

        System.out.println(output);

        assertTrue(true);
    }

    private final String TEMPLATES_PATH = "/templates/";
    private final String TEMPLATE_NAME = "resourceTest.ftl";

    private String hasPersonUri = "http://example.org/schema#hasPerson";
    private String foafName = "http://xmlns.com/foaf/0.1/name";
}