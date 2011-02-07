<#include "macro.ftl">
<div id="results" class="panel">

    <h1>${facetView.total} results</h1>

    <ul class="results">

    <#list facetView.results as result>
    <li>
        <span><a href="<@drillForResult result=result/>"><@label resource=result/></a></span>
        <#if view??>
          <#include "result-items/" + view + ".ftl">
        </#if>
    </li>
    </#list>

    </ul>

    <#if facetView.results?size < facetView.total>
        <@moreResults facetView=facetView/>
    </#if>

</div><!-- /panel -->
