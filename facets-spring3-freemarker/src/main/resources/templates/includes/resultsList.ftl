<#include "macro.ftl">
<div id="results">

    <h2>Results</h2>

    <#list facetView.results as result>
        <p><a href="<@drillForResult result=result/>"><@label resource=result/></a></p>
    </#list>

    <#if facetView.results?size < facetView.total>
        <p class="more-results"><a href="<@moreResults/>">More results ...</a></p>
    </#if>

</div>