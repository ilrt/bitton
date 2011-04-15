package org.ilrt.wf.facets.web.freemarker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Run the suite of tests relating to facets and FreeMarker.
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        FacetParentListMethodTest.class,
        FacetStateUrlMethodTest.class,
        JenaObjectWrapperTest.class,
        ResourceHashModelTest.class
})
public class FacetFreeMarkerSuiteTest {
    // nothing to see here, move along!
}
