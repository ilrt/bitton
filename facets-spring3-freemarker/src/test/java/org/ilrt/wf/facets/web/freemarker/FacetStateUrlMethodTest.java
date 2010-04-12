package org.ilrt.wf.facets.web.freemarker;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateModelException;
import org.ilrt.wf.facets.freemarker.FacetStateUrlMethod;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FacetStateUrlMethodTest {

    @Test(expected = TemplateModelException.class)
    public void invalidNoArguments() throws TemplateModelException {
        FacetStateUrlMethod facetStateUrlMethod = new FacetStateUrlMethod();
        facetStateUrlMethod.exec(new ArrayList());
    }

    @Test(expected = TemplateModelException.class)
    public void invalidIncorrectArgument() throws TemplateModelException {

        List<Object> args = new ArrayList<Object>();
        args.add(new Object());

        FacetStateUrlMethod facetStateUrlMethod = new FacetStateUrlMethod();
        facetStateUrlMethod.exec(args);
    }

    @Test
    public void test() throws TemplateModelException {

        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();

        List<MockHttpServletRequest> args = new ArrayList<MockHttpServletRequest>();
        args.add(httpServletRequest);

        FacetStateUrlMethod facetStateUrlMethod = new FacetStateUrlMethod();
        SimpleScalar scalar = (SimpleScalar) facetStateUrlMethod.exec(args);

        assertEquals("Hello", scalar.getAsString());

    }

}
