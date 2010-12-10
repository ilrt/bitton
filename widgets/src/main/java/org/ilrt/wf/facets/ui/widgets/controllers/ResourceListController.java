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
public class ResourceListController extends AbstractController {

    ResourceListController()
    {
        log = LoggerFactory.getLogger(ResourceListController.class);
    }
    
    final String prefix = "PREFIX rdf: <" + RDF.getURI() + ">\n" +
        "PREFIX rdfs: <" + RDFS.getURI() + ">\n" +
        "PREFIX dc: <http://purl.org/dc/terms/>\n" +
        "PREFIX aiiso: <http://purl.org/vocab/aiiso/schema#>\n" +
        "PREFIX closed: <http://vocab.bris.ac.uk/rr/closed#>\n";

   final String publicationsForContributor = prefix +
           "SELECT DISTINCT ?res ?label ?date ?citation\n"
           + "{\n"
           + "  GRAPH ?g "
           + "  { "
           + "    ?res dc:contributor <%s>;\n"
           + "    rdfs:label ?label;\n"
           + "    dc:date ?date;\n"
           + "    dc:bibliographicCitation ?citation.\n"
           + "  }\n"
           + "}";

    // ---------- public methods that are mapped to URLs

    @RequestMapping(value = "/publication", method = RequestMethod.GET)
    public ModelAndView mainView(HttpServletRequest request) throws FacetViewServiceException 
    {
        String person = "http://resrev.ilrt.bris.ac.uk/research-revealed-hub/people/11652#person";

        // do a fresh query the service
        ModelAndView mav = createModelAndView(request);

        ArrayList<HashMap> resultsList = new ArrayList();

        ResultSet results = querySelect(publicationsForContributor,new Object[]{person});
        while (results.hasNext())
        {
            QuerySolution qs = results.next();
            String citation = qs.getLiteral("citation").getString();
            String date = qs.getLiteral("date").getString();
            String label = qs.getLiteral("label").getString();

            HashMap<String,String> resultList = new HashMap();
            resultList.put("label",makeStringSafe(label));
            resultList.put("citation",makeStringSafe(citation));
            resultList.put("date",date);
            resultsList.add(resultList);
        }

        mav.addObject("results", resultsList);
        return mav;
    }

    private String makeStringSafe(String unSafeString)
    {
        String safeString = "";
        safeString = unSafeString.replace("\"", "'");
        return safeString;
    }
}