package org.ilrt.wf.facets.freemarker;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Resource;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class JenaObjectWrapper extends DefaultObjectWrapper {

    public JenaObjectWrapper() {
        super();
    }

    @Override
    public TemplateModel wrap(Object obj) throws TemplateModelException {

        if (obj instanceof Resource) {
            return new ResourceHashModel((Resource) obj);
        } else if (obj instanceof Literal) {
            return super.wrap(((Literal) obj).getValue());
        } else {
            return super.wrap(obj);
        }

    }

}
