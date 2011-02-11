<#-- @ftlvariable name="facetView" type="org.ilrt.wf.facets.FacetView" -->
    <#list facetView.facets as facet>
    <#if facet?? && facet.state??>
        <#if facet.type = "TextSearch">
                    <form id="textsearchfacet" action="" method="get">
                        ${facetStateInput(Request, facet.param, facet.paramValue)}
                        <input type="text" name="${facet.param}" id="${facet.param}" value="<#if facet.state.paramValue??>${facet.state.paramValue}</#if>" placeholder="Search within these results" />
                        <input type="submit" value="Go"/>
			<!--
			#DEVNOTE:
			Please make the 'clear' link below clear the text search:
			-->
			<#if facet.state.paramValue??><ul class="bubbles"><li>${facet.state.paramValue} (<a href="#">clear</a>)</li></ul></#if>
                    </form>
        </#if>
    </#if>
    </#list>
