package org.ilrt.wf.facets.web.freemarker;

import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateModelException;
import org.ilrt.wf.facets.freemarker.FacetStateUrlMethod;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test the FreeMarker method used to create URLs within a facet.
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class FacetStateUrlMethodTest {

    @Before
    public void setUp() {
        args = new ArrayList<Object>();
        method = new FacetStateUrlMethod();
        request = new MockHttpServletRequest();
        requestHashModel = new HttpRequestHashModel(request,
                new DefaultObjectWrapper());
    }

    @Test(expected = TemplateModelException.class)
    public void invalidNoArguments() throws TemplateModelException {

        // execute with no arguments
        method.exec(args);
    }

    @Test(expected = TemplateModelException.class)
    public void invalidIncorrectArgumentSize() throws TemplateModelException {

        // execute with the incorrect number of arguments
        args.add(requestHashModel);
        args.add("arg1");
        method.exec(args);
    }

    @Test(expected = TemplateModelException.class)
    public void invalidIncorrectArgumentSize2() throws TemplateModelException {

        // execute with the incorrect number of arguments
        args.add(requestHashModel);
        args.add(new SimpleScalar("arg1"));
        args.add(new SimpleScalar("arg2"));
        args.add(new SimpleScalar("arg3"));  // one too many
        method.exec(args);
    }


    @Test(expected = TemplateModelException.class)
    public void invalidIncorrectArgument() throws TemplateModelException {

        // execute with incorrect argument type
        args.add(new Object()); // incorrect
        method.exec(args);
    }

    @Test(expected = TemplateModelException.class)
    public void invalidIncorrectArgument2() throws TemplateModelException {

        // execute with incorrect argument type
        args.add(requestHashModel);
        args.add(new SimpleScalar("arg1"));
        args.add(new Object()); // incorrect
        method.exec(args);
    }

    @Test
    public void testNoParameterValues() throws TemplateModelException {

        request.setRequestURI(baseUri);

        args.add(requestHashModel);

        SimpleScalar scalar = (SimpleScalar) method.exec(args);

        assertEquals("Unexpected url", baseUri, scalar.getAsString());
    }

    @Test
    public void testNoFacetParameters() throws TemplateModelException, UnsupportedEncodingException {

        final String expectedUrl = baseUri + "?" + key + "=" + URLEncoder.encode(historyValue, "UTF-8");

        request.setRequestURI(baseUri);

        args.add(requestHashModel);
        args.add(new SimpleScalar(key));
        args.add(new SimpleScalar(historyValue));

        SimpleScalar scalar = (SimpleScalar) method.exec(args);

        assertEquals("Unexpected url", expectedUrl, scalar.getAsString());
    }

    @Test
    public void testNoFacetParametersWithFooParameter() throws TemplateModelException,
            UnsupportedEncodingException {

        final String expectedUrl = baseUri + "?" + fooKey + "=" + barValue + "&amp;"
                + key + "=" + URLEncoder.encode(historyValue, "UTF-8");

        request.setRequestURI(baseUri);
        request.addParameter(fooKey, barValue);

        args.add(requestHashModel);
        args.add(new SimpleScalar(key));
        args.add(new SimpleScalar(historyValue));

        SimpleScalar scalar = (SimpleScalar) method.exec(args);

        assertEquals("Unexpected url", expectedUrl, scalar.getAsString());
    }

    @Test
    public void testReplacingExistingFacetParameter() throws TemplateModelException, UnsupportedEncodingException {

        final String replacementValue = "subjects:british_history";

        final String expectedUrl = baseUri + "?" + key + "=" + URLEncoder.encode(replacementValue, "UTF-8");

        // set the request with an initial replacementValue
        request.setRequestURI(baseUri);
        request.addParameter(key, historyValue);
        args.add(requestHashModel);

        // key/value that should replace the value above in the new URL
        args.add(new SimpleScalar(key));
        args.add(new SimpleScalar(replacementValue));

        SimpleScalar scalar = (SimpleScalar) method.exec(args);

        assertEquals("Unexpected url", expectedUrl, scalar.getAsString());
    }

    @Test
    public void testReplacingExistingFacetParameterAlsoHasFooParameter()
            throws TemplateModelException, UnsupportedEncodingException {

        final String replacementValue = "subjects:british_history";

        final String expectedUrl = baseUri + "?" + fooKey + "=" + barValue + "&amp;"
                + key + "=" + URLEncoder.encode(replacementValue, "UTF-8");

        // set the request with an initial replacementValue
        request.setRequestURI(baseUri);
        request.addParameter(key, historyValue);
        request.addParameter(fooKey, barValue);
        args.add(requestHashModel);

        // key/value that should replace the value above in the new URL
        args.add(new SimpleScalar(key));
        args.add(new SimpleScalar(replacementValue));

        SimpleScalar scalar = (SimpleScalar) method.exec(args);

        assertEquals("Unexpected url", expectedUrl, scalar.getAsString());
    }


    private final String baseUri = "/list.do";
    private final String key = "subjects";
    private final String historyValue = "subjects:History";

    private final String fooKey = "foo";
    private final String barValue = "bar";

    private List<Object> args;
    private FacetStateUrlMethod method;
    private MockHttpServletRequest request;
    private HttpRequestHashModel requestHashModel;
}
