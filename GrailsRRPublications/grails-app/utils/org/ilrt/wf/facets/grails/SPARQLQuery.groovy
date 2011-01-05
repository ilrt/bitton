package org.ilrt.wf.facets.grails

import com.hp.hpl.jena.query.Query
import com.hp.hpl.jena.query.QuerySolution
import com.hp.hpl.jena.query.QueryExecution
import com.hp.hpl.jena.query.QueryExecutionFactory
import com.hp.hpl.jena.query.QueryFactory
import com.hp.hpl.jena.query.ResultSet
import com.hp.hpl.jena.query.Syntax
import com.hp.hpl.jena.vocabulary.RDF
import com.hp.hpl.jena.vocabulary.RDFS
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 * @author cmcpb
 */
class SPARQLQuery {
    static Logger log = LoggerFactory.getLogger(SPARQLQuery.class)

    static String endpoint = "http://tc-p2.ilrt.bris.ac.uk:8266/data-server/sparql";

    def static querySelect(String query) {
        long startTime = System.currentTimeMillis();
        log.info("Query is:" + query);
        Query q = QueryFactory.create(query, Syntax.syntaxARQ);
        QueryExecution qe = QueryExecutionFactory.sparqlService(endpoint, q);
        log.info("Query took []ms", System.currentTimeMillis() - startTime);
        return qe.execSelect();
    }


    def static getResultSet(LinkedHashMap params) {
        String uri = RDF.getURI()

        
	String prefix = "PREFIX rdf: <${uri}>\n" +
        "PREFIX rdfs: <" + RDFS.getURI() + ">\n" +
        "PREFIX dc: <http://purl.org/dc/terms/>\n" +
        "PREFIX aiiso: <http://purl.org/vocab/aiiso/schema#>\n" +
        "PREFIX closed: <http://vocab.bris.ac.uk/rr/closed#>\n";

        String person = "http://resrev.ilrt.bris.ac.uk/research-revealed-hub/people/11652#person";
        String publicationsForContributor = "${prefix} SELECT DISTINCT ?res ?label ?date ?citation\n" +
           "{\n" +
           "  GRAPH ?g " +
           "  { " +
           "    ?res dc:contributor <${person}>;\n" +
           "    rdfs:label ?label;\n" +
           "    dc:date ?date;\n" +
           "    dc:bibliographicCitation ?citation.\n" +
           "  }\n" +
           "}";

        ResultSet resultSet = querySelect(publicationsForContributor)

        convertResultSetToList(resultSet, ["label", "date", "citation"])
    }

    def static makeStringSafe(String unSafeString)
    {
        String safeString = ""
        safeString = unSafeString.replace("\"", "'")
    }

    def static convertResultSetToList(ResultSet results, ArrayList keys)
    {
        ArrayList<HashMap> resultsList = new ArrayList()
        
        while (results.hasNext())
        {
            QuerySolution qs = results.next()
            HashMap<String,String> resultList = new HashMap()

            for (String key : keys)
            {
                String s = qs.getLiteral(key).getString()
                resultList.put(key, makeStringSafe(s))
            }
            resultsList.add(resultList);
        }

        return resultsList
    }
}

