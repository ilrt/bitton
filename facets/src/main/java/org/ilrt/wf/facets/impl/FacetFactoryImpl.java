package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetConstraint;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetFactory;
import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.constraints.Constraint;
import org.ilrt.wf.facets.constraints.RegexpConstraint;
import org.ilrt.wf.facets.constraints.ValueConstraint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FacetFactoryImpl implements FacetFactory {

    public FacetFactoryImpl(FacetQueryService facetQueryService) {
        this.facetQueryService = facetQueryService;
    }

    @Override
    public Facet create(FacetConstraint constraint) throws FacetException {

        String facetType = constraint.getConfig().get(Facet.FACET_TYPE);

        if (facetType.equals(Facet.ALPHA_NUMERIC_FACET_TYPE)) {
            return createAlphaNumericFacet(constraint);
        } else {
            throw new FacetException("Unrecognized facet type: " + facetType);
        }
    }

    @Override
    public void calculateCount(List<Facet> facetList) {

        // find all the states
        List<FacetState> states = new ArrayList<FacetState>();

        for (Facet facet : facetList) {
            states.add(facet.getState());
        }

        // request counts

        Map<FacetState, Integer> results = facetQueryService.getCounts(states);

        // update the states with the counts

        Set<FacetState> countStates = results.keySet();

        for (FacetState facetState : countStates) {
            ((FacetStateImpl)facetState).setCount(results.get(facetState));
        }

    }


    private Facet createAlphaNumericFacet(FacetConstraint constraint) {

        // the facet state to be passed to the facet
        FacetState facetState;

        // each state is constrained to a type, e.g. foaf:Person
        ValueConstraint typeConstraint = new ValueConstraint(RDF.type,
                ResourceFactory.createProperty(constraint.getConfig().get(Facet.CONSTRAINT_TYPE)));

        // property used in each state
        Property p = ResourceFactory.createProperty(constraint.getConfig().get(Facet.LINK_PROPERTY));

        // create a pseudo parent
        FacetStateImpl root = new FacetStateImpl();
        root.setRoot(true);

        // this alpha-numeric facet has been selected via the request object
        if (constraint.getParameters().containsKey(constraint.getConfig().get(Facet.PARAM_NAME))) {

            // get the label from the parameter value
            String[] values = (String[]) constraint.getParameters()
                    .get(constraint.getConfig().get(Facet.PARAM_NAME));
            String value = values[0];

            // create a state to represent the currently selected state
            facetState = new FacetStateImpl(value, root, value,
                    facetStateConstraint(typeConstraint, alphaNumericConstraint(p, value)));

        } else { // we want them all

            root.setRefinements(alphaNumericRefinements(typeConstraint, p, root));
            facetState = root;
        }

        // create the facet
        return new FacetImpl(constraint.getConfig().get(Facet.FACET_TITLE), facetState,
                constraint.getConfig().get(Facet.PARAM_NAME));
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
                    rootState, alphaNumericLabel(c), facetStateConstraint(typeConstraint,
                            alphaNumericConstraint(linkProperty, c))));
        }

        return refinementsList;
    }

    /**
     * Convenience method to create a Set of constraint
     *
     * @param a first constraint.
     * @param b second constraint.
     * @return a set of constraints
     */
    private Set<Constraint> facetStateConstraint(Constraint a, Constraint b) {

        Set<Constraint> constraints = new HashSet<Constraint>();
        constraints.add(a);
        constraints.add(b);

        return constraints;
    }

    private final FacetQueryService facetQueryService;
}
