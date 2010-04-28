package org.ilrt.wf.facets.freemarker;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.shared.PrefixMapping;
import freemarker.template.SimpleCollection;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

import java.util.ArrayList;

/**
 * Class to help display a Jena Resource in a freemarker template. For example, for the
 * following triple:
 *
 * &lt;http://example.org/1/&gt; &lt;http://www.w3.org/2000/01/rdf-schema#label&gt; "Example Label" .
 *
 * The following freemarker syntax:
 *
 * &lt;p&gt;${resource}&lt;p&gt;
 * &lt;p&gt;${resource['http://www.w3.org/2000/01/rdf-schema#label']}&lt;p&gt;
 * &lt;p&gt;${resource['rdfs#label']}&lt;p&gt;
 *
 * Would produce:
 *
 * &lt;p&gt;http://example.org/1/&lt;p&gt;
 * &lt;p&gt;Example Label&lt;p&gt;
 * &lt;p&gt;Example Label&lt;p&gt;
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class ResourceHashModel implements TemplateHashModelEx, TemplateScalarModel {

    // ---------- constructors

    public ResourceHashModel(Resource resource) {
        this.resource = resource;
        this.prefixMapping = PrefixMapping.Factory.create();
        prefixMapping.setNsPrefixes(PrefixMapping.Standard);
    }

    // ---------- TemplateModel interface methods

    @Override
    public int size() throws TemplateModelException {
        StmtIterator iterator = resource.listProperties();
        return iterator.toList().size();
    }

    @Override
    public TemplateCollectionModel keys() throws TemplateModelException {

        ArrayList<String> list = new ArrayList<String>();

        StmtIterator iterator = resource.listProperties();

        while (iterator.hasNext()) {
            list.add(iterator.next().getPredicate().getURI());
        }

        return new SimpleCollection(list);
    }

    @Override
    public TemplateCollectionModel values() throws TemplateModelException {

        ArrayList<String> list = new ArrayList<String>();

        StmtIterator iterator = resource.listProperties();

        while (iterator.hasNext()) {

            RDFNode node = iterator.next().getObject();

            if (node.isLiteral()) {
                list.add(((Literal) node).getLexicalForm());
            } else if (node.isResource()) {
                list.add(((Resource) node).getURI());
            }

        }

        return new SimpleCollection(list);
    }

    // ---------- TemplateHashModel interface methods

    @Override
    public TemplateModel get(String s) throws TemplateModelException {

        //System.out.println(s);

        if (s.equals("uri")) {
            return new SimpleScalar(resource.getURI());
        }

        String uri = prefixMapping.expandPrefix(s);

        Property property = ResourceFactory.createProperty(uri);

        Statement stmt = resource.getProperty(property);

        RDFNode node = stmt.getObject();

        if (node.isLiteral()) {
            return new SimpleScalar(((Literal) node).getLexicalForm());
        } else if (node.isResource()) {
            return new SimpleScalar(((Resource) node).getURI());
        }

        return null;
    }

    @Override
    public boolean isEmpty() throws TemplateModelException {

        StmtIterator iterator = resource.listProperties();
        return iterator.toList().size() == 0;
    }

    // ---------- TemplateScalarModel interface methods

    @Override
    public String getAsString() throws TemplateModelException {
        return resource.getURI();
    }

    private Resource resource;
    private PrefixMapping prefixMapping;
}
