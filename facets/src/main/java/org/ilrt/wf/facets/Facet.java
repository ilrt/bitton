package org.ilrt.wf.facets;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 * @author Damian Steer (d.steer.@bristol.ac.uk)
 */
public interface Facet {

    String getName();

    FacetState getState();

    String getParam();

    // possible parameter values in the facet configuration
    String FACET_TYPE = "facetType";
    String FACET_TITLE = "facetTitle";
    String LINK_PROPERTY = "linkProperty";
    String BROADER_PROPERTY = "widerProperty";
    String FACET_BASE = "facetBase";
    String CONSTRAINT_TYPE = "constraintType";
    String PARAM_NAME = "paramName";
    String PREFIX = "prefix";
    String START_YEAR = "startYear";
    String END_YEAR = "endYear";

    // facet types
    String ALPHA_NUMERIC_FACET_TYPE = "AlphaNumeric";
    String HIERARCHICAL_FACET_TYPE = "Hierarchical";
    String DATE_TIME_FACET_TYPE = "DateTime";
    String FLAT_FACET_TYPE = "Flat";
    String TEXT_SEARCH_FACET = "TextSearch";
}
