package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetEnvironment;
import org.ilrt.wf.facets.FacetFactory;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.constraints.Constraint;
import org.ilrt.wf.facets.constraints.RegexpConstraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.ilrt.wf.facets.constraints.UnionConstraint;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class AlphaNumericFacetImpl extends AbstractFacetFactoryImpl implements FacetFactory {

    // ---------- methods implementing the interface

    @Override
    public Facet create(FacetEnvironment environment) {

        // the facet state to be passed to the facet
        FacetState facetState;

        // each state is constrained to a type, e.g. foaf:Person
        UnionConstraint typeConstraint = createTypeConstraint(environment.getConfig()
                .get(Facet.CONSTRAINT_TYPE));

        // property used in each state
        Property p = ResourceFactory.createProperty(environment.getConfig()
                .get(Facet.LINK_PROPERTY));

        // create a pseudo parent
        FacetStateImpl root = new FacetStateImpl();
        root.setRoot(true);

        // this alpha-numeric facet has been selected via the request object
        if (environment.getParameters().containsKey(environment.getConfig()
                .get(Facet.PARAM_NAME))) {

            // get the label from the parameter value
            String[] values = environment.getParameters()
                    .get(environment.getConfig().get(Facet.PARAM_NAME));
            String value = values[0];

            // create a state to represent the currently selected state
            facetState = new FacetStateImpl(value, root, value,
                    Arrays.asList(typeConstraint, alphaNumericConstraint(p, value)));

        } else { // we want them all

            root.getConstraints().addAll(Arrays.asList(typeConstraint));
            root.setRefinements(alphaNumericRefinements(typeConstraint, p, root));
            facetState = root;
        }

        // create the facet
        return new FacetImpl(getFacetTitle(environment), facetState, getParameterName(environment), Facet.ALPHA_NUMERIC_FACET_TYPE);
    }


    // ---------- methods relating to alpha numeric facets

    /**
     * @return an array of alpha-numeric characters.
     */
    protected char[] alphaNumericArray() {
        return "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    }

    /**
     * @param c the character we want to represent in a label
     * @return the label representation of a character
     */
    protected String alphaNumericLabel(char c) {
        return c + "*";
    }

    /**
     * @param p the property constrained with the character.
     * @param c the character we want to represent in a constraint.
     * @return the constraint that represents the character.
     */
    protected RegexpConstraint alphaNumericConstraint(Property p, char c) {
        return new RegexpConstraint(p, "^" + c);
    }

    /**
     * @param p     the property constrained with the character.
     * @param label a label (from request) we can derive a character from.
     * @return the constraint that represents the character.
     */
    protected RegexpConstraint alphaNumericConstraint(Property p, String label) {
        return alphaNumericConstraint(p, label.charAt(0));
    }

    /**
     * @param typeConstraint constraints used in the state.
     * @param linkProperty   link property used in the state.
     * @param rootState      the root state for any refinement states.
     * @return a list of refinements for the root state.
     */
    protected List<FacetState> alphaNumericRefinements(Constraint typeConstraint,
                                                       Property linkProperty,
                                                       FacetState rootState) {

        // list to hold refinements
        List<FacetState> refinementsList = new ArrayList<FacetState>();

        // go through the list
        for (char c : alphaNumericArray()) {

            // add the potential state to the refinements
            refinementsList.add(new FacetStateImpl(alphaNumericLabel(c),
                    rootState, alphaNumericLabel(c), Arrays.asList(typeConstraint,
                            alphaNumericConstraint(linkProperty, c))));
        }

        return refinementsList;
    }
}
