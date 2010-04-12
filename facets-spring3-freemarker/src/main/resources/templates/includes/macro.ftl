<#-- display a facet -->
<#macro diplayFacet facet>
    <#if facet?? && facet.state??>
        <div class="facet">
            <h3 class="facet-title">${facet.name}</h3>
            <ul class="facet-list">
                <#list facet.getState().getRefinements() as refinement>
                    <@displayRefinementWithCount refinement=refinement/>
                </#list>
            </ul>
        </div>
    </#if>
</#macro>

<#-- display refinements if they have a count -->
<#macro displayRefinement refinement>
    <@displayRefinementListItem name=refinement.name count=refinement.count/>
</#macro>

<#-- display refinements if they have a count -->
<#macro displayRefinementWithCount refinement>
    <#if (refinement.count > 0)>
        <@displayRefinementListItem name=refinement.name count=refinement.count/>
    </#if>
</#macro>

<#-- display refinement details -->
<#macro displayRefinementListItem name count>
    <li class="facet-list-item"><a href="">${name}</a> (${count})</li>
</#macro>
