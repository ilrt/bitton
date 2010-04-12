package org.ilrt.wf.facets.freemarker;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FacetStateUrlMethod implements TemplateMethodModelEx {
    @Override
    public Object exec(List list) throws TemplateModelException {

        if (list.size() != 1) {
            throw new TemplateModelException("Expected 1 argument, actually received " + list.size());
        }

        if (!(list.get(0) instanceof HttpServletRequest)) {
            throw new TemplateModelException("Unexpected argument type, expected a HttpServletRequest");
        }

        return new SimpleScalar("Hello");
    }
}
