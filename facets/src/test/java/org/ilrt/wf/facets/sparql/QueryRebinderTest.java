/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.sparql;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.sparql.algebra.Algebra;
import com.hp.hpl.jena.sparql.algebra.Op;
import com.hp.hpl.jena.sparql.algebra.Transformer;
import com.hp.hpl.jena.sparql.algebra.op.OpBGP;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.engine.binding.Binding;
import com.hp.hpl.jena.sparql.engine.binding.BindingMap;
import java.net.URL;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pldms
 */
public class QueryRebinderTest {

    public QueryRebinderTest() {
    }

    /**
     * Test of transform method, of class QueryRebinder.
     */
    @Test
    public void testTransform() {
        URL toBindRes = this.getClass().getResource("/sparql/in.1.rq");
        URL desiredRes = this.getClass().getResource("/sparql/out.1.rq");
        Query toBind = QueryFactory.read(toBindRes.toExternalForm());
        Query desired = QueryFactory.read(desiredRes.toExternalForm());
        Binding binding = new BindingMap();
        binding.add(Var.alloc("p"), Node.createURI("urn:ex:p"));
        binding.add(Var.alloc("p1"), Node.createURI("urn:ex:p1"));
        binding.add(Var.alloc("o"), Node.createURI("urn:ex:o"));
        binding.add(Var.alloc("lit"), Node.createLiteral("lit", "en", false));

        toBind.addResultVar("s");
        toBind.setQueryResultStar(false);

        Op result = Transformer.transform(new QueryRebinder(binding), Algebra.compile(toBind));
        assertEquals(Algebra.compile(desired), result);
    }

    final static String fix(final String javaURL) {
        if (javaURL.startsWith("file:/"))
            return javaURL.replaceFirst("file:/+", "/");
        return javaURL;
    }
}