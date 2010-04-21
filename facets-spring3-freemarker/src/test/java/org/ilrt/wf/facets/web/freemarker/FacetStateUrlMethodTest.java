package org.ilrt.wf.facets.web.freemarker;

import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateModelException;
import org.ilrt.wf.facets.freemarker.FacetStateUrlMethod;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FacetStateUrlMethodTest {

    @Before
    public void setUp() {
        args = new ArrayList<Object>();
        facetStateUrlMethod = new FacetStateUrlMethod();
        httpServletRequest = new MockHttpServletRequest();
        httpRequestHashModel = new HttpRequestHashModel(httpServletRequest,
                new DefaultObjectWrapper());
    }

    @Test(expected = TemplateModelException.class)
    public void invalidNoArguments() throws TemplateModelException {

        // execute with no arguments
        facetStateUrlMethod.exec(args);
    }

    @Test(expected = TemplateModelException.class)
    public void invalidIncorrectArgumentSize() throws TemplateModelException {

        // execute with the incorrect number of arguments
        args.add(httpRequestHashModel);
        args.add("arg1");
        facetStateUrlMethod.exec(args);
    }

    @Test(expected = TemplateModelException.class)
    public void invalidIncorrectArgumentSize2() throws TemplateModelException {

        // execute with the incorrect number of arguments
        args.add(httpRequestHashModel);
        args.add(new SimpleScalar("arg1"));
        args.add(new SimpleScalar("arg2"));
        args.add(new SimpleScalar("arg3"));  // one too many
        facetStateUrlMethod.exec(args);
    }


    @Test(expected = TemplateModelException.class)
    public void invalidIncorrectArgument() throws TemplateModelException {

        // execute with incorrect argument type
        args.add(new Object()); // incorrect
        facetStateUrlMethod.exec(args);
    }

    @Test(expected = TemplateModelException.class)
    public void invalidIncorrectArgument2() throws TemplateModelException {

        // execute with incorrect argument type
        args.add(httpRequestHashModel);
        args.add(new SimpleScalar("arg1"));
        args.add(new Object()); // incorrect
        facetStateUrlMethod.exec(args);
    }

    @Test
    public void testNoParameterValues() throws TemplateModelException {

        httpServletRequest.setRequestURI(baseUri);

        args.add(httpRequestHashModel);

        SimpleScalar scalar = (SimpleScalar) facetStateUrlMethod.exec(args);

        assertEquals("Unexpected url", baseUri, scalar.getAsString());
    }

    @Test
    public void testNoFacetParameters() throws TemplateModelException {

        final String expectedUrl = baseUri + "?" + key + "=" + historyValue;

        httpServletRequest.setRequestURI(baseUri);

        args.add(httpRequestHashModel);
        args.add(new SimpleScalar(key));
        args.add(new SimpleScalar(historyValue));

        SimpleScalar scalar = (SimpleScalar) facetStateUrlMethod.exec(args);

        assertEquals("Unexpected url", expectedUrl, scalar.getAsString());
    }

    @Test
    public void testNoFacetParametersWithFooParameter() throws TemplateModelException {

        final String expectedUrl = baseUri + "?" + fooKey + "=" + barValue + "&amp;"
                + key + "=" + historyValue;

        httpServletRequest.setRequestURI(baseUri);
        httpServletRequest.addParameter(fooKey, barValue);

        args.add(httpRequestHashModel);
        args.add(new SimpleScalar(key));
        args.add(new SimpleScalar(historyValue));

        SimpleScalar scalar = (SimpleScalar) facetStateUrlMethod.exec(args);

        assertEquals("Unexpected url", expectedUrl, scalar.getAsString());
    }

    @Test
    public void testReplacingExistingFacetParameter() throws TemplateModelException {

        final String replacementValue = "subjects:british_history";

        final String expectedUrl = baseUri + "?" + key + "=" + replacementValue;

        // set the request with an initial replacementValue
        httpServletRequest.setRequestURI(baseUri);
        httpServletRequest.addParameter(key, historyValue);
        args.add(httpRequestHashModel);

        // key/value that should replace the value above in the new URL
        args.add(new SimpleScalar(key));
        args.add(new SimpleScalar(replacementValue));

        SimpleScalar scalar = (SimpleScalar) facetStateUrlMethod.exec(args);

        assertEquals("Unexpected url", expectedUrl, scalar.getAsString());
    }

    @Test
    public void testReplacingExistingFacetParameterAlsoHasFooParameter()
            throws TemplateModelException {

        final String replacementValue = "subjects:british_history";

        final String expectedUrl = baseUri + "?" + fooKey + "=" + barValue + "&amp;"
                + key + "=" + replacementValue;

        // set the request with an initial replacementValue
        httpServletRequest.setRequestURI(baseUri);
        httpServletRequest.addParameter(key, historyValue);
        httpServletRequest.addParameter(fooKey, barValue);
        args.add(httpRequestHashModel);

        // key/value that should replace the value above in the new URL
        args.add(new SimpleScalar(key));
        args.add(new SimpleScalar(replacementValue));

        SimpleScalar scalar = (SimpleScalar) facetStateUrlMethod.exec(args);

        assertEquals("Unexpected url", expectedUrl, scalar.getAsString());
    }


    final String baseUri = "/list.do";
    final String key = "subjects";
    final String historyValue = "subjects:History";

    final String fooKey = "foo";
    final String barValue = "bar";

    List<Object> args;
    FacetStateUrlMethod facetStateUrlMethod;
    MockHttpServletRequest httpServletRequest;
    HttpRequestHashModel httpRequestHashModel;
}
