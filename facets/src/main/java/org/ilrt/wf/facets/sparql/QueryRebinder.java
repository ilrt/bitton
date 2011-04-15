package org.ilrt.wf.facets.sparql;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.sparql.algebra.Algebra;
import com.hp.hpl.jena.sparql.algebra.Op;
import com.hp.hpl.jena.sparql.algebra.OpAsQuery;
import com.hp.hpl.jena.sparql.algebra.TransformCopy;
import com.hp.hpl.jena.sparql.algebra.Transformer;
import com.hp.hpl.jena.sparql.algebra.op.OpBGP;
import com.hp.hpl.jena.sparql.algebra.op.OpGraph;
import com.hp.hpl.jena.sparql.core.BasicPattern;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.engine.binding.Binding;
import com.hp.hpl.jena.sparql.syntax.Template;
import com.hp.hpl.jena.sparql.syntax.TemplateGroup;
import com.hp.hpl.jena.sparql.util.NodeIsomorphismMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueryRebinder extends TransformCopy {

    private final Binding bindings;

    public static Query rebind(Query query, Binding binding) {
        Op q = Transformer.transform(new QueryRebinder(binding), Algebra.compile(query));
        /*Query bound = OpAsQuery.asQuery(q);
        if (bound.isConstructType()) {
            bound.setConstructTemplate(bind(bound.getConstructTemplate(), binding));
        }*/
        return OpAsQuery.asQuery(q);
    }

    public QueryRebinder(Binding bindings) {
        this.bindings = bindings;
    }

    @Override
    public Op transform(OpBGP op) {
        BasicPattern pattern = op.getPattern();
        BasicPattern newPattern = new BasicPattern();
        for (Triple t : pattern) {
            Node s = bind(t.getSubject());
            Node p = bind(t.getPredicate());
            Node o = bind(t.getObject());
            newPattern.add(Triple.create(s, p, o));
        }
        return new OpBGP(newPattern);
    }

    /*
    @Override
    public Op transform(OpGraph op, Op x) {
        return new OpGraph(bind(op.getNode()), op.getSubOp());
    }*/

    private Node bind(Node node) {
        if (node instanceof Var && bindings.contains((Var) node)) {
            return bindings.get((Var) node);
        } else {
            return node;
        }
    }

    private static Template bind(Template t, Binding b) {
        List<Triple> l = new ArrayList<Triple>();
        t.subst(l, Collections.EMPTY_MAP, b);
        TemplateGroup tg = new TemplateGroup();
        for (Triple triple: l) tg.addTriple(triple);
        return tg;
    }
}
