package org.ilrt.wf.facets.vocab;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class FACET_CONFIG {


    private static final Model model = ModelFactory.createDefaultModel();

    public static final String NS = "http://schemas.ilrt.org/facets/config#";

    public static String getURI() {
        return NS;
    }

    // resources

    



    // properties
}
