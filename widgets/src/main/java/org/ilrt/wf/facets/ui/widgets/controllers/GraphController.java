package org.ilrt.wf.facets.ui.widgets.controllers;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import java.util.ArrayList;
import java.util.HashMap;

import org.ilrt.wf.facets.FacetViewServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
@Controller
public class GraphController extends AbstractController {
    String publicationType = "http://purl.org/dc/terms/Publication";
    GraphController()
    {
        log = LoggerFactory.getLogger(GraphController.class);
    }
    
    final String prefix = "PREFIX rdf: <" + RDF.getURI() + ">\n" +
        "PREFIX rdfs: <" + RDFS.getURI() + ">\n" +
        "PREFIX dc: <http://purl.org/dc/terms/>\n" +
        "PREFIX aiiso: <http://purl.org/vocab/aiiso/schema#>\n" +
        "PREFIX closed: <http://vocab.bris.ac.uk/rr/closed#>\n";

   final String publishingYears = prefix +
           "SELECT DISTINCT ?year "
           + "{ GRAPH ?g "
           + "  {"
           + "    ?a a <http://purl.org/ontology/bibo/Article>;"
           + "         <http://purl.org/dc/terms/date> ?year. "
           + "  }"
           + "}"
           + "ORDER BY ?year";

    final String pubsByYearAndTypeCount = prefix +
            "SELECT (count(?a) AS ?pubscount) ?type"+
            "{"+
            "  GRAPH ?g "+
            "    {"+
            "       ?a a <"+publicationType+">;"+
            "            rdf:type ?type;"+
            "            dc:date \"%s\"^^<http://www.w3.org/2001/XMLSchema-datatypes#gYear>. "+
            "    }"+
            "  }"+
            "GROUP BY ?type";


    // ---------- public methods that are mapped to URLs

    @RequestMapping(value = "/graph", method = RequestMethod.GET)
    public ModelAndView mainView(HttpServletRequest request) throws FacetViewServiceException {

        // do a fresh query the service
        ModelAndView mav = createModelAndView(request);

        ArrayList<HashMap> resultsList = new ArrayList();
        ArrayList<String> types = new ArrayList();

        ResultSet results = querySelect(publishingYears,new Object[]{});
        while (results.hasNext())
        {
            QuerySolution qs = results.next();
            String year = qs.get("year").asLiteral().getString();
            System.out.println("Got " + year);
            ResultSet resultForGrouping = querySelect(pubsByYearAndTypeCount,new Object[]{year});
            while (resultForGrouping.hasNext())
            {
                QuerySolution sol = resultForGrouping.next();
                String typeOfPublication = "UNKNOWN";
                if (sol.contains("type"))
                {
                    typeOfPublication = sol.getResource("type").getURI();
                }
                String numberOfPublication = sol.getLiteral("pubscount").getString();
                HashMap<String,String> resultList = new HashMap();
                resultList.put("count", numberOfPublication);
                resultList.put("type", typeOfPublication);
                resultList.put("year", year);
                resultsList.add(resultList);
                if (!types.contains(typeOfPublication)) types.add(typeOfPublication);
            }
        }

        mav.addObject("facet", "Published Work");
        mav.addObject("results", resultsList);
        mav.addObject("types", types);
        return mav;
    }
}