package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetEnvironment;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetFactory;
import org.ilrt.wf.facets.constraints.ValueConstraint;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public abstract class AbstractFacetFactoryImpl implements FacetFactory {

    @Override
    public abstract Facet create(FacetEnvironment environment) throws FacetException;

    protected String getLabel(Resource resource) {

        if (resource.hasProperty(RDFS.label)) {
            return resource.getProperty(RDFS.label).getLiteral().getLexicalForm();
        }

        if (resource.getURI() != null) {
            return resource.getURI();
        }

        return resource.toString();
    }

    protected String getParameterName(FacetEnvironment environment) {
        return environment.getConfig().get(Facet.PARAM_NAME);
    }

    protected String getFacetTitle(FacetEnvironment environment) {
        return environment.getConfig().get(Facet.FACET_TITLE);
    }

    protected ValueConstraint createTypeConstraint(String type) {
        return new ValueConstraint(RDF.type, ResourceFactory.createProperty(type));
    }


}
