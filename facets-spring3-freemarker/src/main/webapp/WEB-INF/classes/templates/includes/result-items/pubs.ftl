<#if result[dc + 'contributor']??>
  <#list result[dc + 'contributor'] as contributor>
    <@displayPerson person=contributor/> ,
  </#list>
</#if>

<#if result[dc + 'date']??>
    (${result[dc + 'date']?first})
</#if>