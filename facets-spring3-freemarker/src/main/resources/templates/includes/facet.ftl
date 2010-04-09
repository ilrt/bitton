<#-- @ftlvariable name="facetView" type="org.ilrt.wf.facets.FacetView" -->
<div id="facetContainer">
    <#list facetView.facets as facet>
        <div class="facet">
            <h3>${facet.name}</h3>
        </div>
    </#list>
</div>