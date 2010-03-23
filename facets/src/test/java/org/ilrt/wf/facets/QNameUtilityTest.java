package org.ilrt.wf.facets;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class QNameUtilityTest {

    @Before
    public void setUp() {

        // add foaf prefix
        Map<String, String> prefixes = new HashMap<String, String>();
        prefixes.put("foaf", "http://xmlns.com/foaf/0.1/");

        qnameUtility = new QNameUtility(prefixes);
    }

    @Test
    public void testBuiltInPrefix() {

        final String uri = "http://www.w3.org/2000/01/rdf-schema#Resource";
        final String qname = "rdfs:Resource";

        assertEquals(qname, qnameUtility.getQName(uri));
        assertEquals(uri, qnameUtility.expandQName(qname));
    }

    @Test
    public void testAddedPrefix() {

        final String uri = "http://xmlns.com/foaf/0.1/Person";
        final String qname = "foaf:Person";

        assertEquals(qname, qnameUtility.getQName(uri));
        assertEquals(uri, qnameUtility.expandQName(qname));
    }

    @Test
    public void testUnknownPrefix() {

        final String uri = "http://example.org/vocab/#Something";

        // should just send the uri back if there is no prefix
        assertEquals(uri, qnameUtility.getQName(uri));
        assertEquals(uri, qnameUtility.expandQName(uri));
    }

    private QNameUtility qnameUtility;
}
