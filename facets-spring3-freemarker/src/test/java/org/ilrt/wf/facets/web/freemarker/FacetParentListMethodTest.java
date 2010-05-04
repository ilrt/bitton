package org.ilrt.wf.facets.web.freemarker;

import freemarker.ext.beans.StringModel;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateModelException;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.freemarker.FacetParentListMethod;
import org.ilrt.wf.facets.impl.FacetImpl;
import org.ilrt.wf.facets.impl.FacetStateImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test the FreeMarker method used to determine any parents for a facet state.
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class FacetParentListMethodTest {

    @Before
    public void setUp() {
        args = new ArrayList<Object>();
        method = new FacetParentListMethod();
    }

    @Test(expected = TemplateModelException.class)
    public void invalidNoArguments() throws TemplateModelException {

        // execute with no arguments
        method.exec(args);
    }

    @Test(expected = TemplateModelException.class)
    public void invalidIncorrectArgumentSize() throws TemplateModelException {

        // execute with the incorrect number of arguments
        args.add(new SimpleScalar("arg0"));
        args.add(new SimpleScalar("arg1"));
        method.exec(args);
    }

    @Test(expected = ClassCastException.class)
    public void invalidIncorrectArgument() throws TemplateModelException {

        // execute with incorrect argument type
        args.add(new Object()); // incorrect
        method.exec(args);
    }

    @Test
    public void testNoParents() throws TemplateModelException {

        FacetStateImpl rootState = new FacetStateImpl();
        rootState.setRoot(true);

        // freemarker will wrap the Facet class in a StringModel
        Facet facet = new FacetImpl(null, rootState, null);
        StringModel m = new StringModel(facet, new DefaultObjectWrapper());
        args.add(m);

        List results = (List) method.exec(args);

        assertEquals(0, results.size());
    }


    @Test
    public void testParents() throws TemplateModelException {

        final String grandParentLabel = "Grandparent";
        final String parentLabel = "Parent";

        FacetStateImpl grandParentState = new FacetStateImpl();
        grandParentState.setRoot(true);
        grandParentState.setName(grandParentLabel);

        FacetStateImpl parentState = new FacetStateImpl();
        parentState.setRoot(false);
        parentState.setName(parentLabel);
        parentState.setParent(grandParentState);

        FacetStateImpl childState = new FacetStateImpl();
        childState.setRoot(false);
        childState.setParent(parentState);

        // freemarker will wrap the Facet class in a StringModel
        Facet facet = new FacetImpl(null, childState, null);
        StringModel m = new StringModel(facet, new DefaultObjectWrapper());
        args.add(m);

        List results = (List) method.exec(args);

        assertEquals("Unexpected number of parents", 2, results.size());
        assertEquals("Unexpected parent", grandParentLabel, ((FacetState) results.get(0)).getName());
        assertEquals("Unexpected parent", parentLabel, ((FacetState) results.get(1)).getName());
    }


    private List<Object> args;
    private FacetParentListMethod method;
}
