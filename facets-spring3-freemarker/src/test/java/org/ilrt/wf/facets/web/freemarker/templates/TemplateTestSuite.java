package org.ilrt.wf.facets.web.freemarker.templates;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Run the suite of tests relating to template generation.
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        FacetTemplateTest.class,
        ResourceHashModelInTemplateTest.class,
        ResultsListTemplateTest.class
})
public class TemplateTestSuite {
    // nothing to see here, move along!
}
