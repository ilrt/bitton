/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.sparql;

import com.hp.hpl.jena.query.DataSource;
import com.hp.hpl.jena.query.DatasetFactory;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.rdf.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pldms
 */
public class ModelQEFactory implements QEFactory {

    private final static Logger log = LoggerFactory.getLogger(ModelQEFactory.class);

    private final DataSource d;

    public ModelQEFactory(Model m) {
        this.d = DatasetFactory.create();
        d.addNamedModel("urn:x-test:foo", m);
    }

    @Override
    public QueryExecution get(Query query) {
        log.info("Query:\n{}", query);
        return QueryExecutionFactory.create(query, d);
    }

}
