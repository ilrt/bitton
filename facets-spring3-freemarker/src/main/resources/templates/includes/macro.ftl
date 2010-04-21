<#-- display a facet -->
<#macro diplayFacet facet>
    <#if facet?? && facet.state??>
        <div class="facet">
            <h3 class="facet-title">${facet.name}</h3>
            <ul class="facet-list">
                <#list facet.getState().getRefinements() as refinement>
                    <@displayRefinementWithCount refinement=refinement paramKey=facet.param
                                                 paramValue=refinement.paramValue/>
                </#list>
            </ul>
        </div>
    </#if>
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
    <li class="facet-list-item"><a href="${facetStateUrl(Request, paramKey, paramValue)}">${name}</a> (${count})</li>
</#macro>
