/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.sparql;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import java.util.Arrays;
import java.util.Collections;
import org.ilrt.wf.facets.FacetQueryService.Tree;
import org.ilrt.wf.facets.constraints.Constraint;
import org.ilrt.wf.facets.constraints.ValueConstraint;

/**
 *
 * @author pldms
 */
public class Scratch {

    public static void main(String... args) {
        SPARQLQueryService service = new SPARQLQueryService(
                new RemoteQEFactory("http://localhost:2020/sparql")
                );

        Property broader = ResourceFactory.createProperty("http://purl.org/vocab/aiiso/schema#part_of");
        Property hosted = ResourceFactory.createProperty("http://vocab.ilrt.bris.ac.uk/rr/closed#hostedBy");
        Resource univ = ResourceFactory.createResource("http://resrev.ilrt.bris.ac.uk/research-revealed-merb/organisation_units/UNIV#org");
        Resource grant = ResourceFactory.createResource("http://vocab.ouls.ox.ac.uk/projectfunding/projectfunding#Grant");
        Resource mech = ResourceFactory.createResource("http://resrev.ilrt.bris.ac.uk/research-revealed-merb/organisation_units/MECH#org");
        Tree<Resource> h = service.getHierarchy(univ, broader, true);
        System.err.println(h);
        h.getValue().getModel().write(System.err, "TTL");

        Constraint type = new ValueConstraint(RDF.type, grant);
        System.err.println("Number of grants: " + service.getCount(Collections.singleton(type)));
        Constraint inMech = new ValueConstraint(hosted, mech);
        System.err.println("Number of things hostedBy Mech: " + service.getCount(Collections.singleton(inMech)));
        System.err.println("Number of Grants hostedBy Mech: " +
                service.getCount(Arrays.asList(inMech, type)));
    }

}
