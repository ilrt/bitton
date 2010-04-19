package org.ilrt.wf.facets.freemarker;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacetStateUrlMethod implements TemplateMethodModelEx {
    @Override
    public Object exec(List args) throws TemplateModelException {

        Map params = null;

        if (args.size() != 3) {
            throw new TemplateModelException("Expected 3 arguments, actually received " + args.size());
        }

        if (!((args.get(0) instanceof HttpServletRequest) && (args.get(1) instanceof String)
                && (args.get(2) instanceof String))) {
            throw new TemplateModelException("Unexpected argument type, expected a HttpServletRequest, String and String");
        }

        // get the existing request parameters
        HttpServletRequest request = (HttpServletRequest) args.get(0);

        // we need a mutable map to play with
        if (request.getParameterMap() != null) {
            params = new HashMap(request.getParameterMap());
        } else {
            params = new HashMap();
        }

        // get the new parameter we want to add
        String key = (String) args.get(1);
        String value = (String) args.get(2);


        


        return new SimpleScalar("Hello");
    }
}
