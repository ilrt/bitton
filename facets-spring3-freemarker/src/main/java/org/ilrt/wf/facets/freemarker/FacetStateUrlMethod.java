package org.ilrt.wf.facets.freemarker;

import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Freemarker class/method to create URLs for rendering new facet states.
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class FacetStateUrlMethod implements TemplateMethodModelEx {
    @Override
    @SuppressWarnings("unchecked")
    public Object exec(List args) throws TemplateModelException {

        // check the arguments received
        checkArguments(args);

        Map params;
        String key = null, value = null;

        // get the existing request parameters
        HttpRequestHashModel httpRequestHashModel = (HttpRequestHashModel) args.get(0);

        HttpServletRequest request = httpRequestHashModel.getRequest();


        // get the new parameter we want to add
        if (args.size() == 3) {
            key = ((SimpleScalar) args.get(1)).getAsString();
            if (args.get(2) != null) {
                value = ((SimpleScalar) args.get(2)).getAsString();
            }
        }

        // get the base URI for future requests
        final String baseUri = request.getRequestURI();

        // return the base URI if there are no existing parameters or a new
        // key/value pair for a new parameter
        if ((request.getParameterMap() == null || request.getParameterMap().size() == 0)
                && key == null && value == null) {
            return new SimpleScalar(baseUri);
        }

        // we need a mutable map to play with
        if (request.getParameterMap() != null) {
            params = new HashMap(request.getParameterMap());
        } else {
            params = new HashMap();
        }

        // we need to check whether or not the parameter already exists. if
        // it doesn't exist we are looking at a new state. if it exists then we
        // need to replace the value with the new one

        if (params.get(key) == null) {  // ok, we don't have a value
            if (key != null && value != null) { // only add if they key and value exist
                params.put(key, new String[]{value});
            }
        } else {
            // if there are problems with key/value, remove
            if (value == null || key == null || key.equals("")) {
                params.remove(key);
            } else {
                params.put(key, new String[]{value});  // replace the value
            }
        }

        // return the new url
        return new SimpleScalar(url(baseUri, params));
    }

    private void checkArguments(List args) throws TemplateModelException {

        if (args.size() != 1 && args.size() != 3) {
            throw new TemplateModelException("Expected 1 or 3 arguments, actually received "
                    + args.size() + ". Expected HttpRequestHashModel or HttpServletRequest, "
                    + "String, String");
        }

        if (args.size() == 1) {
            if (!(args.get(0) instanceof HttpRequestHashModel)) {
                throw new TemplateModelException("Unexpected argument type, expected a " +
                        "HttpRequestHashModel; received " + args.get(0));
            }
        }

        if (args.size() == 3) {
            if (!((args.get(0) instanceof HttpRequestHashModel) && (args.get(1) instanceof SimpleScalar)
                    && (args.get(2) instanceof SimpleScalar || args.get(2) == null))) {
                throw new TemplateModelException("Unexpected argument type, expected a " +
                        "HttpRequestHashModel, SimpleScalar and SimpleScalar or null; received "
                        + args.get(0)
                        + ", " + args.get(1)
                        + ", " + args.get(2));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private String url(String baseUri, Map params) {

        StringBuilder builder = new StringBuilder(baseUri).append("?");

        // order the keys so the order of parameter key/values remains uniform
        Set keysSet = params.keySet();
        List<String> keysList = new ArrayList<String>();
        keysList.addAll(keysSet);
        Collections.sort(keysList);

        Iterator<String> iter = keysList.iterator();

        while (iter.hasNext()) {

            String key = iter.next();
            String[] values = (String[]) params.get(key);

            for (int i = 0; i < values.length; i++) {
                if (i > 0) {
                    builder.append("&amp;");
                }
                builder.append(key).append("=").append(encodeValue(values[i]));
            }

            if (iter.hasNext()) {
                builder.append("&amp;");
            }
        }


        return builder.toString();
    }

    private String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return value;
        }
    }
}
