package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetEnvironment;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetFactory;
import org.ilrt.wf.facets.FacetQueryService;
import org.ilrt.wf.facets.FacetQueryService.Tree;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.QNameUtility;
import org.ilrt.wf.facets.constraints.Constraint;
import org.ilrt.wf.facets.constraints.RegexpConstraint;
import org.ilrt.wf.facets.constraints.ValueConstraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public class FacetFactoryImpl implements FacetFactory {

    // ---------- public constructors

    public FacetFactoryImpl(FacetQueryService facetQueryService, Map<String, String> prefixMap) {
        this.facetQueryService = facetQueryService;
        this.qNameUtility = new QNameUtility(prefixMap);
    }

    // ---------- public methods implementing the interface

    @Override
    public Facet create(FacetEnvironment environment) throws FacetException {

        String facetType = environment.getConfig().get(Facet.FACET_TYPE);

        if (facetType.equals(Facet.ALPHA_NUMERIC_FACET_TYPE)) {
            return createAlphaNumericFacet(environment);
        } else if (facetType.equals(Facet.HIERARCHICAL_FACET_TYPE)) {
            return createHierarchicalFacet(environment);
        } else {
            throw new FacetException("Unrecognized facet type: " + facetType);
        }
    }

    // ---------- high level methods relating to the construction of different facet types

    private Facet createHierarchicalFacet(FacetEnvironment environment) {

        // constraints used in the facet states of this facet
        List<? extends Constraint> constraints = createHierarchicalConstraintList(environment);

        // base resource representing the currently selected uri in the hierarchy
        Resource baseResource = createBaseResource(environment);

        // broader property
        Property broaderProperty = createBroaderProperty(environment);

        // TODO - hard coded true = isBroader -> should be derived from configuration?
        // create the current state - will include refinements
        FacetStateImpl currentFacetState = currentHierarchicalState(constraints, baseResource,
                broaderProperty, true);

        findParents(currentFacetState, ResourceFactory.createResource(environment.getConfig()
                .get(Facet.FACET_BASE)), baseResource, broaderProperty, true);

        // create the facet
        return new FacetImpl(getFacetTitle(environment), currentFacetState,
                getParameterName(environment));
    }

    private Facet createAlphaNumericFacet(FacetEnvironment environment) {

        // the facet state to be passed to the facet
        FacetState facetState;

        // each state is constrained to a type, e.g. foaf:Person
        ValueConstraint typeConstraint = createValueConstraint(environment.getConfig()
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
        return new FacetImpl(getFacetTitle(environment), facetState, getParameterName(environment));
    }

    // ---------- methods relating to all facets

    @Override
    public void calculateCount(List<FacetState> states) {

        // request counts

        Map<FacetState, Integer> results = facetQueryService.getCounts(states);

        // update the states with the counts

        Set<FacetState> countStates = results.keySet();

        for (FacetState facetState : countStates) {
            ((FacetStateImpl) facetState).setCount(results.get(facetState));
        }

    }

    @Override
    public List<Resource> results(List<FacetState> states, int offset, int number) {
        return facetQueryService.getResults(states, offset, number);
    }

    @Override
    public int totalResults(List<FacetState> states) {
        return facetQueryService.getCount(states);
    }

    protected ValueConstraint createValueConstraint(String type) {
        return new ValueConstraint(RDF.type, ResourceFactory.createProperty(type));
    }

    protected String getFacetTitle(FacetEnvironment environment) {
        return environment.getConfig().get(Facet.FACET_TITLE);
    }

    protected String getParameterName(FacetEnvironment environment) {
        return environment.getConfig().get(Facet.PARAM_NAME);
    }

    // ---------- methods relating to hierarchical facets

    protected FacetStateImpl currentHierarchicalState(List<? extends Constraint> constraints,
                                                      Resource resource, Property property,
                                                      boolean isBroader) {

        FacetStateImpl currentFacetState = new FacetStateImpl();
        currentFacetState.setParamValue(qNameUtility.getQName(resource.getURI()));
        currentFacetState.getConstraints().addAll(constraints);

        List<Resource> resources = facetQueryService.getRefinements(resource,
                property, isBroader);

        currentFacetState.setRefinements(hierarchicalRefinements(resources, constraints,
                currentFacetState));

        return currentFacetState;
    }

    protected List<FacetState> hierarchicalRefinements(List<Resource> resources,
                                                       List<? extends Constraint> constraints,
                                                       FacetState parentState) {

        List<FacetState> refinementList = new ArrayList<FacetState>();

        for (Resource resource : resources) {

            String uri = resource.getURI();
            String label = getLabel(resource);

            refinementList.add(new FacetStateImpl(label, parentState,
                    qNameUtility.getQName(uri), constraints));
        }

        return refinementList;
    }

    protected void findParents(FacetStateImpl currentState,
                               Resource base, Resource current, Property property, boolean isBroader) {

        Tree<Resource> results = facetQueryService.getHierarchy(base, property, isBroader);

        List<Resource> familyHistory = findChildren(results, current.getURI());

        FacetStateImpl parentState = null;

        ListIterator<Resource> iter = familyHistory.listIterator();

        while (iter.hasNext()) {

            // get the next resource in the path
            Resource resource = iter.next();

            // get the URIs and label
            String uri = resource.getURI();
            String label = getLabel(resource);

            // end of the line - so is actually the current state
            if (!iter.hasNext()) {

                currentState.setName(label);
                //currentState.setParamValue(qNameUtility.getQName(uri));

                // does the current state have a parent? if not, it's the root
                if (parentState != null) {
                    currentState.setParent(parentState);
                } else {
                    currentState.setRoot(true);
                }

            } else { // we are dealing with the parent of the current state

                FacetStateImpl aState = new FacetStateImpl();
                aState.setName(label);
                aState.setParamValue(qNameUtility.getQName(uri));

                if (parentState != null) {
                    aState.setParent(parentState);
                } else {
                    aState.setRoot(true);
                }

                parentState = aState;

            }
        }

    }

    protected List<Resource> findChildren(Tree<Resource> hierarchy, String currentUri) {
        List<Resource> familyHistory = new ArrayList<Resource>();
        findChildren(familyHistory, hierarchy, currentUri, 0, true);
        return familyHistory;
    }


    protected boolean findChildren(List<Resource> path, Tree<Resource> hierarchy, String currentUri,
                                   int level, boolean follow) {

        if (follow) {

            // anything on the path greater than the current level
            // is a failed search and can be removed
            if (path.size() > level) {
                for (int i = level; i <= path.size(); i++) {
                    path.remove(level);
                }
            }

            // add the current node to the path
            path.add(level, hierarchy.getValue());

            // have we reached our goal?
            if (hierarchy.getValue().getURI().equals(currentUri)) {
                return false;
            }

            // recursively follow the children ...
            for (Tree<Resource> tree : hierarchy.getChildren()) {
                level++;
                follow = findChildren(path, tree, currentUri, level, follow);
                level--;
            }
        }

        return follow;
    }


    protected boolean hasUriParameterValue(FacetEnvironment environment) {

        return environment.getParameters().containsKey(environment.getConfig()
                .get(Facet.PARAM_NAME));
    }

    protected String getUriFromParameterValue(FacetEnvironment environment) {

        String[] paramStrings = environment.getParameters()
                .get(environment.getConfig().get(Facet.PARAM_NAME));
        String uri = paramStrings[0];

        return qNameUtility.expandQName(uri);
    }

    protected List<? extends Constraint> createHierarchicalConstraintList(FacetEnvironment environment) {

        ValueConstraint typeConstraint = createValueConstraint(environment.getConfig()
                .get(Facet.CONSTRAINT_TYPE));
        ValueConstraint linkConstraint = createValueConstraint(environment.getConfig()
                .get(Facet.LINK_PROPERTY));
        return Arrays.asList(typeConstraint, linkConstraint);
    }

    protected Resource createBaseResource(FacetEnvironment environment) {
        if (hasUriParameterValue(environment)) {
            String uri = getUriFromParameterValue(environment);
            return ResourceFactory.createResource(uri);
        } else {
            return ResourceFactory.createResource(environment.getConfig()
                    .get(Facet.FACET_BASE));
        }
    }

    protected Property createBroaderProperty(FacetEnvironment environment) {
        return ResourceFactory.createProperty(environment.getConfig().get(Facet.BROADER_PROPERTY));
    }

    // ---------- methods relating to alpha numeric facets

    /**
     * @return an array of alpha-numeric characters.
     */
//    protected char[] alphaNumericArray() {
//        return "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
//    }
    protected char[] alphaNumericArray() {
        return "ABCDEFG".toCharArray();
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

    protected String getLabel(Resource resource) {

        if (resource.hasProperty(RDFS.label)) {
            return resource.getProperty(RDFS.label).getLiteral().getLexicalForm();
        }

        if (resource.getURI() != null) {
            return resource.getURI();
        }

        return resource.toString();
    }


    private final FacetQueryService facetQueryService;
    private final QNameUtility qNameUtility;
}
