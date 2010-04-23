<#include "facet-macros.ftl"/>
<#-- @ftlvariable name="facetView" type="org.ilrt.wf.facets.FacetView" -->
<div id="facetContainer">
    <#list facetView.facets as facet>
        <@diplayFacet facet=facet/>
    </#list>
</div>