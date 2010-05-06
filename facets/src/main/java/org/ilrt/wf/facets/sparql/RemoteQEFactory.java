/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.sparql;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pldms
 */
public class RemoteQEFactory implements QEFactory {
    private final static Logger log = LoggerFactory.getLogger(RemoteQEFactory.class);
    private final String endpoint;

    public RemoteQEFactory(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public QueryExecution get(Query query) {
        log.debug("Query issued: {}", query);
        return QueryExecutionFactory.sparqlService(endpoint, query);
    }

}
