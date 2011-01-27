<#-- display a facet -->
<#macro displayFacet facet>
    <#if facet?? && facet.state??>
        <#if facet.type = "TextSearch">
        <div class="facet">
            <h4 class="facet-title">${facet.name}</h4>
            <p>
                <form action="${facetStateUrl(Request)}" method="get">
                    <input type="text" name="${facet.param}" id="${facet.param}" value="<#if facet.state.paramValue??>${facet.state.paramValue}</#if>"/>
                    <input type="submit" value="Go"/>
                </form>
            </p>
        </div>
        <#else>
            <div class="facet">
                <h4 class="facet-title">${facet.name}</h4>
                <#if facet.state.root>
                    <@refinementList refinements=facet.state.refinements paramKey=facet.param/>
                <#else>
                    <#assign parents = facetParentList(facet)>
                    <#-- Display the parents -->
                    <#if parents?size &gt; 1>
                        <#list parents as parent>
                            <#if parent.root>
                                <@facetList/>
                            <#else>
                                <@listItem/>${parent.name} [<a href="${facetStateUrl(Request, facet.param, parent.paramValue)}">x</a>]
                                <#if parent_has_next>
                                    <ul>
                                </#if>
                            </#if>
                        </#list>
                    </#if>
                    <#-- Display the current facet -->
                    <ul class="facet-list">
                        <@listItem/>${facet.state.name} [<a href="${facetStateUrl(Request, facet.param, facet.state.parent.paramValue)}">x</a>]
                        <#if facet.state.refinements?size &gt; 0>
                            <@refinementList refinements=facet.state.refinements paramKey=facet.param />
                        </#if>
                        <#list parents as parent>
                            </li></ul>
                        </#list>
                </#if>
            </div>
        </#if>
    </#if>
</#macro>

<#macro facetList>
    <ul class="facet-list">
</#macro>

<#macro listItem><li class="facet-list-item"></#macro>

<#-- macro for displaying a list of refinements -->
<#macro refinementList refinements paramKey>
    <@facetList/>
        <#list refinements as refinement>
            <#if refinement.countable>
                <@displayRefinementWithCount refinement=refinement paramKey=paramKey
                    paramValue=refinement.paramValue/>
            <#else>
                <@displayRefinementWithoutCount name=refinement.name count=refinement.count paramKey=paramKey
                    paramValue=refinement.paramValue/>
            </#if>
        </#list>
    </ul>
</#macro>

<#-- display refinements if they have a count -->
<#macro displayRefinement refinement paramKey paramValue>
    <@displayRefinementListItem name=refinement.name count=refinement.count paramKey=paramKey
                                paramValue=paramValue/>
</#macro>

<#-- display refinements if they have a count -->
<#macro displayRefinementWithCount refinement paramKey paramValue>
    <#if (refinement.count > 0)>
        <@displayRefinementListItem name=refinement.name count=refinement.count paramKey=paramKey
                                    paramValue=paramValue/>
    </#if>
</#macro>

<#-- display refinement details -->
<#macro displayRefinementListItem name count paramKey paramValue>
    <@listItem/><a href="${facetStateUrl(Request, paramKey, paramValue)}">${name}</a> (${count})</li>
</#macro>

<#macro displayRefinementWithoutCount name count paramKey paramValue>
    <@listItem/><a href="${facetStateUrl(Request, paramKey, paramValue)}">${name}</a></li>
</#macro>