package org.ilrt.wf.facets.ui.widgets.controllers;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import java.util.ArrayList;
import java.util.HashMap;

import org.ilrt.wf.facets.FacetViewService;
import org.ilrt.wf.facets.FacetViewServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import org.ilrt.wf.facets.FacetQueryService;
import org.slf4j.LoggerFactory;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
@Controller
public class TopFacetController extends AbstractController {

    TopFacetController()
    {
        log = LoggerFactory.getLogger(TopFacetController.class);
    }
    
    final String prefix = "PREFIX rdf: <" + RDF.getURI() + ">\n" +
        "PREFIX rdfs: <" + RDFS.getURI() + ">\n" +
        "PREFIX dc: <http://purl.org/dc/terms/>\n" +
        "PREFIX aiiso: <http://purl.org/vocab/aiiso/schema#>\n" +
        "PREFIX closed: <http://vocab.bris.ac.uk/rr/closed#>\n";

   final String topOrgFacets = prefix +
           "SELECT DISTINCT ?org ?name  "
           + "WHERE { GRAPH ?g1 { "
           + "?org aiiso:part_of <http://resrev.ilrt.bris.ac.uk/research-revealed-hub/organisation_units/ACAD#org>; rdfs:label ?name. "
           + "} }";

    final String facetCount = prefix +
            "SELECT (count(?members) AS ?membercount) WHERE { GRAPH ?g1 { <%s> closed:member ?members. } }";


    // ---------- public methods that are mapped to URLs

    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public ModelAndView mainView(HttpServletRequest request) throws FacetViewServiceException {

        // do a fresh query the service
        ModelAndView mav = createModelAndView(request);

        ArrayList<HashMap> resultsList = new ArrayList();

        ResultSet results = querySelect(topOrgFacets,new Object[]{});
        while (results.hasNext())
        {

            QuerySolution qs = results.next();
            String resource = qs.get("org").asResource().getURI();
            String label = qs.get("name").asLiteral().getString();
            System.out.println("Got " + resource);
            ResultSet resultForGrouping = querySelect(facetCount,new Object[]{resource});
            if (resultForGrouping.hasNext())
            {
                QuerySolution sol = resultForGrouping.next();
                String members = sol.getLiteral("membercount").getString();
                System.out.println("Members for <"+label+">" + members);
                HashMap<String,String> resultList = new HashMap();
                resultList.put("label",label);
                resultList.put("count",members);
                resultList.put("uri",resource);
                resultsList.add(resultList);
            }
        }

        mav.addObject("facet", "Organisations");
        mav.addObject("results", resultsList);
        return mav;
    }
}