/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.sparql;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.sparql.algebra.Algebra;
import com.hp.hpl.jena.util.FileManager;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ilrt.wf.facets.FacetState;
import org.ilrt.wf.facets.constraints.Constraint;
import org.ilrt.wf.facets.constraints.RangeConstraint;
import org.ilrt.wf.facets.constraints.RegexpConstraint;
import org.ilrt.wf.facets.constraints.UnConstraint;
import org.ilrt.wf.facets.constraints.ValueConstraint;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pldms
 */
public class SPARQLQueryServiceTest {
    private final Model model;
    private final String NS = "http://example.com/ns#";
    private final Property prop = ResourceFactory.createProperty(NS, "prop");
    private final Property range = ResourceFactory.createProperty(NS, "range");
    private final Property label = ResourceFactory.createProperty(NS, "label");
    private final RDFNode val = ResourceFactory.createResource(NS + "value");
    private final Property broader = ResourceFactory.createProperty(NS, "broader");
    private final Property narrower = ResourceFactory.createProperty(NS, "narrower");


    public SPARQLQueryServiceTest() {
        URL data = this.getClass().getResource("/sparql/testdata.ttl");
        this.model = FileManager.get().loadModel(data.toExternalForm());
    }

    /**
     * Test of getRefinements method, of class SPARQLQueryService.
     */
    @Test
    public void testGetBroaderRefinements() {
        FacetState fs = new MFacetState(ResourceFactory.createResource(NS + "a"),
                broader, FacetState.NONE);
        SPARQLQueryService instance = new SPARQLQueryService(new ModelQEFactory(model));
        Map<FacetState, List<RDFNode>> a = instance.getRefinements(fs);

        assertEquals("Got correct broader refinements", 3, a.get(fs).size());
    }

    @Test
    public void testGetNarrowerRefinements() {
        FacetState fs = new MFacetState(ResourceFactory.createResource(NS + "a"),
                FacetState.NONE, narrower);
        SPARQLQueryService instance = new SPARQLQueryService(new ModelQEFactory(model));
        Map<FacetState, List<RDFNode>> a = instance.getRefinements(fs);

        assertEquals("Got correct narrower refinements", 4, a.get(fs).size());
    }

    @Test
    public void checkConstraintToOp() {
        SPARQLQueryService instance = new SPARQLQueryService(null);

        Constraint constraint = new UnConstraint();
        assertEquals(Algebra.parse("(null)"),
                instance.constraintToOp(constraint));

        constraint = new ValueConstraint(prop, val);
        assertEquals(Algebra.parse("(bgp (triple ?s <http://example.com/ns#prop> <http://example.com/ns#value>))"),
                instance.constraintToOp(constraint));

        constraint = new RangeConstraint(prop, ResourceFactory.createPlainLiteral("a"), ResourceFactory.createPlainLiteral("z"));
        assertEquals(Algebra.parse("(filter (&& (<= \"a\" ?v1) (< ?v1 \"z\") ) (bgp (triple ?s <http://example.com/ns#prop> ?v1)))"),
                instance.constraintToOp(constraint));

        constraint = new RegexpConstraint(prop, "^a");
        assertEquals(Algebra.parse("(filter (regex ?v2 \"^a\" \"i\") (bgp (triple ?s <http://example.com/ns#prop> ?v2)))"),
                instance.constraintToOp(constraint));
    }

    @Test
    public void checkCount() {
        SPARQLQueryService instance = new SPARQLQueryService(new ModelQEFactory(model));

        Collection<Constraint> cos = new LinkedList<Constraint>();
        Collections.addAll(cos,
                new ValueConstraint(prop, val),
                makeRangeCon(0, 5)
                );

        assertEquals(3, instance.getCount(cos));

        cos = new LinkedList<Constraint>();
        Collections.addAll(cos,
                new ValueConstraint(prop, val),
                new RegexpConstraint(label, "^A")
                );

        assertEquals(4, instance.getCount(cos));

        cos = new LinkedList<Constraint>();
        Collections.addAll(cos,
                makeRangeCon(6, 9),
                new ValueConstraint(prop, val),
                new RegexpConstraint(label, "^g")
                );

        assertEquals(1, instance.getCount(cos));
    }

    @Test
    public void checkFullCounts() {
        SPARQLQueryService instance = new SPARQLQueryService(new ModelQEFactory(model));

        MFacetState a = new MFacetState(new UnConstraint());
        FacetState a1 =
                a.addRefinement( new MFacetState(new RegexpConstraint(label, "^a")) );
        FacetState a2 =
                a.addRefinement( new MFacetState(new RegexpConstraint(label, "^b")) );
        FacetState a3 =
                a.addRefinement( new MFacetState(new RegexpConstraint(label, "^g")) );
        FacetState a4 =
                a.addRefinement( new MFacetState(new RegexpConstraint(label, "^x")) );

        Map<FacetState, Integer> counts = instance.getCounts(Collections.singletonList(a));

        assertEquals((Object) 8, counts.get(a1));
        assertEquals((Object) 4, counts.get(a2));
        assertEquals((Object) 2, counts.get(a3));
        assertEquals((Object) 0, counts.get(a4));

        MFacetState b = new MFacetState(new ValueConstraint(prop, val));

        counts = instance.getCounts(Arrays.asList(a, b));

        assertEquals((Object) 4, counts.get(a1));
        assertEquals((Object) 2, counts.get(a2));
        assertEquals((Object) 1, counts.get(a3));
        assertEquals((Object) 0, counts.get(a4));

        MFacetState c = new MFacetState(makeRangeCon(5, 9));
        FacetState c1 =
                c.addRefinement( new MFacetState(makeRangeCon(5,7)) );
        FacetState c2 =
                c.addRefinement( new MFacetState(makeRangeCon(7,9)) );

        counts = instance.getCounts(Arrays.asList(a, c));

        assertEquals((Object) 2, counts.get(a1));
        assertEquals((Object) 4, counts.get(a2));
        assertEquals((Object) 2, counts.get(a3));

        assertEquals((Object) 4, counts.get(c1));
        assertEquals((Object) 4, counts.get(c2));
    }

    private RangeConstraint makeRangeCon(int start, int end) {
        return new RangeConstraint(range,
                ResourceFactory.createTypedLiteral(start),
                ResourceFactory.createTypedLiteral(end)
                );
    }

    static class MFacetState implements FacetState {
        private final Property broader;
        private final Property narrower;
        private final RDFNode value;
        private final List<Constraint> constraints = new LinkedList<Constraint>();
        private final List<FacetState> refinements = new LinkedList<FacetState>();

        public MFacetState(RDFNode value, Property broader, Property narrower) {
            if (broader == null ||
                    narrower == null) throw new IllegalArgumentException("No nulls");
            this.value = value;
            this.broader = broader;
            this.narrower = narrower;
        }

        public MFacetState(Constraint... cons) {
            broader = narrower = null;
            value = null;
            Collections.addAll(constraints, cons);
        }

        @Override
        public String getName() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getCount() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isRoot() {
            return value == null;
        }

        @Override
        public List<FacetState> getRefinements() {
            return refinements;
        }

        @Override
        public FacetState getParent() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getParamValue() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Property getBroaderProperty() {
            return broader;
        }

        @Override
        public Property getNarrowerProperty() {
            return narrower;
        }

        @Override
        public RDFNode getValue() {
            return value;
        }

        @Override
        public Collection<Constraint> getConstraints() {
            return constraints;
        }

        public FacetState addRefinement(FacetState state) {
            refinements.add(state);
            return state;
        }
    }

}