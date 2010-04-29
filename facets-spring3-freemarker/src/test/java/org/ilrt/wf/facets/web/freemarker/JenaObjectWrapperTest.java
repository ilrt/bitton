package org.ilrt.wf.facets.web.freemarker;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.ilrt.wf.facets.freemarker.JenaObjectWrapper;
import org.ilrt.wf.facets.freemarker.ResourceHashModel;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test the custom object for wrapping Jena objects.
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class JenaObjectWrapperTest {

    @Test
    public void test() throws TemplateModelException {

        Resource resource = ResourceFactory.createResource();

        ObjectWrapper objectWrapper = new JenaObjectWrapper();
        TemplateModel model = objectWrapper.wrap(resource);

        assertTrue(model instanceof ResourceHashModel);
    }

}
