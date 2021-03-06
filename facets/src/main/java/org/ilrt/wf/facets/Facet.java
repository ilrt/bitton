package org.ilrt.wf.facets;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 * @author Damian Steer (d.steer.@bristol.ac.uk)
 */
public interface Facet {

    String getName();

    FacetState getState();

    String getParam();

    String getType();

    // possible parameter values in the facet configuration
    String FACET_TYPE = "facetType";
    String FACET_TITLE = "facetTitle";
    String FACET_ORDER = "order";
    String LINK_PROPERTY = "linkProperty";
    String LINK_INVERT = "linkInvert";
    String BROADER_PROPERTY = "widerProperty";
    String FACET_BASE = "facetBase";
    String CONSTRAINT_TYPE = "constraintType";
    String REQUIRE_LABEL = "requireLabel";
    String PARAM_NAME = "paramName";
    String REQUIRE_COUNTS = "requireCounts";
    String PREFIX = "prefix";
    String START_YEAR = "startYear";
    String END_YEAR = "endYear";
    String ENUM_LIST = "enumList";
    String NUMERIC_RANGE = "numericRange";
    String NUMERIC_RANGE_TYPE = "numericRangeType";
    String NUMERIC_RANGE_FORMAT = "numericRangeFormat";

    // facet types
    String ALPHA_NUMERIC_FACET_TYPE = "AlphaNumeric";
    String HIERARCHICAL_FACET_TYPE = "Hierarchical";
    String SIMPLE_NUMBER_RANGE_FACET_TYPE = "SimpleNumberRange";
    String DATE_TIME_FACET_TYPE = "DateTime";
    String FLAT_FACET_TYPE = "Flat";
    String TEXT_SEARCH_FACET = "TextSearch";
    String ENUM_FLAT_FACET = "EnumFlat";
}
