package org.ilrt.wf.facets.freemarker;

import com.hp.hpl.jena.rdf.model.Resource;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class JenaObjectWrapper extends DefaultObjectWrapper {

    public JenaObjectWrapper() {
        super();
    }

    public TemplateModel wrap(Object obj) throws TemplateModelException {

        if (obj instanceof Resource) {
            System.out.println("It's a resource");
            return new ResourceHashModel((Resource) obj);
        } else {
            return super.wrap(obj);
        }

    }

}
