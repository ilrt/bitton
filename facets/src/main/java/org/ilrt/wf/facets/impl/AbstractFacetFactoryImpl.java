package org.ilrt.wf.facets.impl;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.ilrt.wf.facets.Facet;
import org.ilrt.wf.facets.FacetEnvironment;
import org.ilrt.wf.facets.FacetException;
import org.ilrt.wf.facets.FacetFactory;
import org.ilrt.wf.facets.FacetState.Order;
import org.ilrt.wf.facets.constraints.UnionConstraint;


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
    
    protected Set<Order> getOrder(FacetEnvironment environment) {
        String orderCond = environment.getConfig().get(Facet.FACET_ORDER);
        if (orderCond == null || orderCond.isEmpty()) return Collections.EMPTY_SET;
        String[] orders = orderCond.split("\\W+");
        EnumSet<Order> result = EnumSet.noneOf(Order.class);
        for (String order: orders) result.add(Order.valueOf(order));
        return result;
    }
    
    protected UnionConstraint createTypeConstraint(String type) {
        List <RDFNode> nodes = new ArrayList();
        if (type == null)
        {
            return new UnionConstraint(RDF.type, null, false);
        }
        else
        {
            for (String s : type.split(","))
            {
                nodes.add(ResourceFactory.createProperty(s));
            }
        }
        
        return new UnionConstraint(RDF.type, nodes, false);
    }
}
