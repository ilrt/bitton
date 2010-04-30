package org.ilrt.wf.facets.web.freemarker;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDFS;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateModelIterator;
import org.ilrt.wf.facets.freemarker.ResourceHashModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test the FreeMarker template class used to wrap a resource in a hash.
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class ResourceHashModelTest {

    @Before
    public void setUp() {

        Model model = ModelFactory.createDefaultModel();
        Resource resource = model.createResource(uri);
        resource.addLiteral(RDFS.label, label);

        resourceHashModel = new ResourceHashModel(resource);
    }

    @Test
    public void testSize() throws TemplateModelException {

        assertEquals("Unexpected size", 1, resourceHashModel.size());
    }

    @Test
    public void testKeys() throws TemplateModelException {

        List<TemplateModel> keysList = new ArrayList<TemplateModel>();

        TemplateCollectionModel collection = resourceHashModel.keys();

        TemplateModelIterator i = collection.iterator();

        while (i.hasNext()) {
            keysList.add(i.next());
        }

        // check the number of keys
        assertEquals("Unexpected size", 1, keysList.size());

        // check the first key value
        assertEquals("Unexpected key", RDFS.label.getURI(),
                ((SimpleScalar) keysList.get(0)).getAsString());
    }

    @Test
    public void testValues() throws TemplateModelException {

        List<TemplateModel> valueList = new ArrayList<TemplateModel>();

        TemplateCollectionModel collection = resourceHashModel.values();

        TemplateModelIterator i = collection.iterator();

        while (i.hasNext()) {
            valueList.add(i.next());
        }

        // check the number of keys
        assertEquals("Unexpected size", 1, valueList.size());

        // check the first key value
        assertEquals("Unexpected key", label,
                ((SimpleScalar) valueList.get(0)).getAsString());
    }

    @Test
    public void testGet() throws TemplateModelException {

        // test a literal
        assertEquals("Unexpected label", label, ((SimpleScalar) resourceHashModel
                .get(RDFS.label.getURI())).getAsString());
    }

    @Test
    public void testEmpty() throws TemplateModelException {

        // check the number of keys
        assertFalse("The resource is not empty", resourceHashModel.isEmpty());
    }

    @Test
    public void testAsString() throws TemplateModelException {

        // check that we get the uri
        assertEquals("Unexpected string value", uri, resourceHashModel.getAsString());
    }

    private String uri = "http://example.org/1/";
    private String label = "This is a label";


    private ResourceHashModel resourceHashModel;
}
