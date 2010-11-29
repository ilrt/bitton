package org.ilrt.wf.facets.ui.widgets.controllers;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import java.util.ArrayList;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ilrt.wf.facets.FacetViewService;
import org.ilrt.wf.facets.FacetViewServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.ilrt.wf.facets.FacetQueryService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 */
@Controller
public class TopFacetController {
    public static String CONTEXT_PATH_KEY = "contextPath";
    public static String SERVLET_PATH_KEY = "servletPath";

    private FacetViewService facetViewService;
    private FacetQueryService facetQueryService;

    private final String FOAF = "http://xmlns.com/foaf/0.1/";
    private final Property userNameProp = ResourceFactory.createProperty(FOAF, "nick");

    public static String TOP_FACET_VIEW_NAME = "topFacetView";
    public static String DEFAULT_VIEW = "defaultView";


    private final String HOME_PATH = "/";
    private final String VIEW_PATH = "/*";
    private final String ITEM_PATH = "/item";
    private final String ABOUT_PATH = "/about/";
    private final String CONTACT_PATH = "/contact/";
    private final String PROFILE_PATH = "/profile";

    Logger log = LoggerFactory.getLogger(TopFacetController.class);
    
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

    private String endpoint = null;
    private Dataset ds = null;

    @Autowired
    public TopFacetController(String ep)
    {
        endpoint = ep;
    };

    // ---------- public methods that are mapped to URLs

    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public ModelAndView mainView(HttpServletRequest request) throws FacetViewServiceException {

        // get the session object
        HttpSession session = request.getSession(true);

        // do a fresh query the service
        ModelAndView mav = createModelAndView(TOP_FACET_VIEW_NAME, request);

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

    ModelAndView createModelAndView(String name, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(name);
        mav.addObject(CONTEXT_PATH_KEY, request.getContextPath());
        mav.addObject(SERVLET_PATH_KEY, request.getServletPath());
        return mav;
    }

    private ResultSet querySelect(String query, Object[] args) {
        long startTime = System.currentTimeMillis();
        query = String.format(query, args);
        log.info("Query is: {}", query);
        Query q = QueryFactory.create(query, Syntax.syntaxARQ);
        QueryExecution qe = QueryExecutionFactory.sparqlService(endpoint, q);
        
        // This isn't supported form http engines. Damn
        // qe.setInitialBinding(initialBinding);
        log.info("Query took {}ms", System.currentTimeMillis() - startTime);
        return qe.execSelect();
    }
}