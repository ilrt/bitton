<#include "macro.ftl">

<div id="results" class="panel">

    <h1>${facetView.total} results</h1>

    <ul class="results">

    <#list facetView.results as result>
        <#if view??>
            <#include "result-items/" + view + ".ftl">
        </#if>
    </#list>

    </ul>

    <#if facetView.results?size < facetView.total>
        <@moreResults facetView=facetView/>
    </#if>

</div><!-- /panel -->
