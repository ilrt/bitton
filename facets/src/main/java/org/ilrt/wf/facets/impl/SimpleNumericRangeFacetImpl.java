package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.datatypes.TypeMapper;
import com.hp.hpl.jena.datatypes.xsd.impl.XSDDateType;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetEnvironment;
import org.ilrt.wf.facets.FacetFactory;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.constraints.Constraint;
import org.ilrt.wf.facets.constraints.RangeConstraint;
import org.ilrt.wf.facets.constraints.ValueConstraint;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleNumericRangeFacetImpl extends AbstractFacetFactoryImpl implements FacetFactory {

    public SimpleNumericRangeFacetImpl() {
        mapper = TypeMapper.getInstance();
    }

    @Override
    public Facet create(FacetEnvironment environment) {

        DecimalFormat df = null;

        if (environment.getConfig().get(Facet.NUMERIC_RANGE_FORMAT) != null) {
            df = new DecimalFormat(environment.getConfig().get(Facet.NUMERIC_RANGE_FORMAT));
        }

        // the facet state to be passed to the facet
        FacetState facetState;

        // each state is constrained to a type, e.g. foaf:Person
        ValueConstraint typeConstraint = createTypeConstraint(environment.getConfig()
                .get(Facet.CONSTRAINT_TYPE));

        // property used in each state
        Property p = ResourceFactory.createProperty(environment.getConfig()
                .get(Facet.LINK_PROPERTY));

        // create a pseudo parent
        FacetStateImpl root = new FacetStateImpl();
        root.setRoot(true);

        String typeUri = environment.getConfig().get(Facet.NUMERIC_RANGE_TYPE);

        // this range facet has been selected via the request object
        if (environment.getParameters().containsKey(environment.getConfig()
                .get(Facet.PARAM_NAME))) {

            // get the label from the parameter value
            String[] values = environment.getParameters()
                    .get(environment.getConfig().get(Facet.PARAM_NAME));
            String value = values[0];

            String[] parts = value.split(":");

            // create a state to represent the currently selected state
            facetState = new FacetStateImpl(label(parts, df, typeUri), root, value,
                    Arrays.asList(typeConstraint, rangeConstraint(p, parts, typeUri)));

        } else { // we want them all

            root.getConstraints().addAll(Arrays.asList(typeConstraint));
            root.setRefinements(refinements(environment.getConfig().get(Facet.NUMERIC_RANGE),
                    typeConstraint, p, root, typeUri, df));
            facetState = root;
        }

        // create the facet
        return new FacetImpl(getFacetTitle(environment), facetState, getParameterName(environment), Facet.SIMPLE_NUMBER_RANGE_FACET_TYPE);
    }


    protected List<FacetState> refinements(String rangeConfig, Constraint typeConstraint,
                                           Property linkProperty, FacetState rootState,
                                           String typeUri, DecimalFormat df) {

        // list to hold refinements
        List<FacetState> refinementsList = new ArrayList<FacetState>();

        String[] ranges = rangeConfig.split(",");

        for (String range : ranges) {

            String[] parts = range.split(":");

            refinementsList.add(new FacetStateImpl(label(parts, df, typeUri), rootState, range,
                    Arrays.asList(typeConstraint, rangeConstraint(linkProperty, parts, typeUri))));
        }

        return refinementsList;
    }

    protected String label(String[] parts, DecimalFormat df, String typeUri) {

        if (df != null) {

            return df.format(Integer.parseInt(parts[0])) + " - "
                    + df.format(Integer.parseInt(parts[1]));
        } else {

            if (mapper.getSafeTypeByName(typeUri) instanceof XSDDateType) {
                return parts[0].substring(0, 4);
            } else {
                return parts[0] + " - " + parts[1];
            }

        }
    }

    protected Constraint rangeConstraint(Property property, String[] parts, String typeUri) {

        Literal lower = ResourceFactory.createTypedLiteral(parts[0], mapper.getTypeByName(typeUri));
        Literal upper = ResourceFactory.createTypedLiteral(parts[1], mapper.getTypeByName(typeUri));
        return new RangeConstraint(property, lower, upper);
    }

    TypeMapper mapper;
}
