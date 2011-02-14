<#-- display a facet -->
<#macro displayFacet facet>
    <#if facet?? && facet.state??>
        <#if facet.type = "TextSearch">
	<#-- do nothing, displaying this elsewhere -->
        <#elseif facet.name = "Department">
            <div class="facet alldept">
                <h2 class="facet-title">${facet.name}</h2>
                    <#if facet.state.refinements?size &gt; 0>
                        <form action="" method="get">
                            ${facetStateInput(Request, facet.param, facet.paramValue)}
                            <select name="${facet.param}" id="${facet.param}" class="autocomplete">
                                <option value="">Type the name of a dept</option>
                                <@listRefinementsAsSelect refinements=facet.state.refinements paramKey=facet.param/>
                           </select>
                            <input type="submit" value="Go"/>
                        </form>
                    <#else>

		    <!--
		    #DEVNOTE: 
		    Use markup like this when facet is selected. Use classname as hook for any JS:
		    	<ul class="facet-list">
			    <li class="selected"><span>Journal article</span> <a href="#">Show all types</a></li>
			</ul>
		-->
                        <p>
                            ${facet.state.name} (<a class="selected" href="${facetStateUrl(Request, facet.param, facet.paramValue)}">clear</a>)
                        </p>
                    </#if>
            </div>

	    <#elseif facet.name= "Most Popular Departments">
            <div class="facet popdept">
                <h3 class="facet-title">Most popular:</h3>
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
                                <@listItem/>${parent.name} (<a class="selected" href="${facetStateUrl(Request, facet.param, parent.paramValue)}">clear</a>)
                                <#if parent_has_next>
                                    <ul>
                                </#if>
                            </#if>
                        </#list>
                    </#if>
                    <#-- Display the current facet -->
                    <ul class="facet-list">
                        <@listItem/>${facet.state.name} (<a class="selected" href="${facetStateUrl(Request, facet.param, facet.state.parent.paramValue)}">clear</a>)
                        <#if facet.state.refinements?size &gt; 0>
                            <@refinementList refinements=facet.state.refinements paramKey=facet.param />
                        </#if>
                        <#list parents as parent>
                            </li></ul>
                        </#list>
                </#if>
            </div>

        <#else>
            <div class="facet" id="${facet.param}-facet">
                <h2 class="facet-title">${facet.name}</h2>
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
                                <@listItem/>${parent.name} (<a class="selected" href="${facetStateUrl(Request, facet.param, parent.paramValue)}">clear</a>)
                                <#if parent_has_next>
                                    <ul>
                                </#if>
                            </#if>
                        </#list>
                    </#if>
                    <#-- Display the current facet -->
                    <ul class="facet-list">
                        <@listItem/>${facet.state.name} (<a class="selected" href="${facetStateUrl(Request, facet.param, facet.state.parent.paramValue)}">clear</a>)
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

<#macro listRefinementsAsSelect refinements paramKey>
    <@facetList/>
        <#list refinements as refinement>
            <#if refinement.countable>
                <option value="${refinement.paramValue}">${refinement.name} (${refinement.count})</option>
            <#else>
                 <option value="${refinement.paramValue}">${refinement.name}</option>
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
<@listItem/><a href="${facetStateUrl(Request, paramKey, paramValue)}">${name}</a> <span class="count">${count}</span></li>
</#macro>

<#macro displayRefinementWithoutCount name count paramKey paramValue>
    <@listItem/><a href="${facetStateUrl(Request, paramKey, paramValue)}">${name}</a></li>
</#macro>
