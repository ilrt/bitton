<#-- @ftlvariable name="facetView" type="org.ilrt.wf.facets.FacetView" -->
    <#list facetView.facets as facet>
    <#if facet?? && facet.state??>
        <#if facet.type = "TextSearch">
                    <form id="textsearchfacet" action="" method="get">
                        ${facetStateInput(Request, facet.param, facet.paramValue)}
                        <input type="text" name="${facet.param}" id="${facet.param}" value="<#if facet.state.paramValue??>${facet.state.paramValue}</#if>" placeholder="Search within these results" />
                        <input type="submit" value="Go"/>
			<a class="help" href="page?name=search">Advanced search tips</a>
			<!--
                                                    #DEVNOTE:
                                                    Possibly offer some help on lucene searching
                                                    Help: E.g. nature, nature AND eden, nature OR eden, nature +eden
                                                    See: http://lucene.apache.org/java/2_4_0/queryparsersyntax.html#AND
			-->
			<#if facet.state.paramValue??><ul><li class="selected"><a title="Clear this search term" href="${facetStateUrl(Request, facet.param, '')}">${facet.state.paramValue}</a></li></ul></#if>
                    </form>
        </#if>
    </#if>
    </#list>
