/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.sparql;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;

/**
 *
 * @author pldms
 */
public class RemoteQEFactory implements QEFactory {
    private final String endpoint;

    public RemoteQEFactory(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public QueryExecution get(Query query) {
        return QueryExecutionFactory.sparqlService(endpoint, query);
    }

}
