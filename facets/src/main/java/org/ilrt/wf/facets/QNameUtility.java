package org.ilrt.wf.facets;

import com.hp.hpl.jena.shared.PrefixMapping;

import java.util.Map;

/**
 *
 * Utility class that uses the Jena PrefixMapping class to create shortened forms
 * of URIs - useful to make sure that URLs used by the web application aren't
 * stupidly large.
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
public class QNameUtility {

    public QNameUtility(Map<String, String> prefixes) {
        prefixMapping = PrefixMapping.Factory.create();
        prefixMapping.setNsPrefixes(PrefixMapping.Standard);
        this.prefixMapping.setNsPrefixes(prefixes);
    }

    public String getQName(String uri) {
        return prefixMapping.shortForm(uri);
    }

    public String expandQName(String qname) {
        return prefixMapping.expandPrefix(qname);
    }

    final PrefixMapping prefixMapping;
}
