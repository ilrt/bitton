<#include "facet-macros.ftl"/>
<#-- @ftlvariable name="facetView" type="org.ilrt.wf.facets.FacetView" -->
<div id="facetcontainer">
    <#list facetView.facets as facet>
        <@displayFacet facet=facet/>
    </#list>
</div>
