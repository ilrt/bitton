/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.sparql;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.rdf.model.Model;

/**
 *
 * @author pldms
 */
public class ModelQEFactory implements QEFactory {
    private final Model m;

    public ModelQEFactory(Model m) {
        this.m = m;
    }

    @Override
    public QueryExecution get(Query query) {
        return QueryExecutionFactory.create(query, m);
    }

}
