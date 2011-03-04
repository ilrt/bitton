<li class="pub">

<#if result[dc + 'contributor']??>
  <span class="contributor"><#list result[dc + 'contributor'] as contributor><@label resource=contributor/><#if contributor_has_next>, </#if></#list>.</span>
</#if>

<a class="title" href="<@drillForResult result=result/>"><@label resource=result/></a>.

<span class="otherdetails">
<#if result[dc + 'date']??>
    (${result[dc + 'date']?first?date?string("yyyy")})
</#if>
<#if result[elements + 'publisher']??>
    ${result[elements + 'publisher']?first}
</#if>
<#if result[bibo + 'isbn']??>
    ${result[bibo + 'isbn']?first}
</#if>
<#if result[bibo + 'volume']??>
    Vol. ${result[bibo + 'volume']?first}
</#if>
<#if result[dc + 'isPartOf']??>
    Part of ${result[dc + 'isPartOf']?first['label']}
</#if>
<#if result[bibo + 'pageStart']?? && result[bibo + 'pageEnd']??>
   Pages
    ${result[bibo + 'pageStart']?first} - ${result[bibo + 'pageEnd']?first}
<#elseif result[bibo + 'pageStart']??>
    Page ${result[bibo + 'pageStart']?first}
<#elseif result[bibo + 'pageEnd']??>
    Page ${result[bibo + 'pageEnd']?first}
</#if>
</span>
</li>
