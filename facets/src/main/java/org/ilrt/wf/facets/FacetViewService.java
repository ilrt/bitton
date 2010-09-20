package org.ilrt.wf.facets;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mike Jones (mike.a.jones@bristol.ac.uk)
 * @author Damian Steer (d.steer.@bristol.ac.uk)
 */
public interface FacetViewService {

    FacetView generate(HttpServletRequest request) throws FacetViewServiceException;
    
    String getViewType(HttpServletRequest request);

    Map<String,String> listViews();
}
