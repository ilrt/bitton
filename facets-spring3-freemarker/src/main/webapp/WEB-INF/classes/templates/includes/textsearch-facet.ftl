<#-- @ftlvariable name="facetView" type="org.ilrt.wf.facets.FacetView" -->
    <#list facetView.facets as facet>
    <#if facet?? && facet.state??>
        <#if facet.type = "TextSearch">
                    <form id="textsearchfacet" action="${facetStateUrl(Request)}" method="get">
                        <input type="text" name="${facet.param}" id="${facet.param}" value="<#if facet.state.paramValue??>${facet.state.paramValue}</#if>" placeholder="Search within these results" />
                        <input type="submit" value="Go"/>
                    </form>
        </#if>
    </#if>
    </#list>
