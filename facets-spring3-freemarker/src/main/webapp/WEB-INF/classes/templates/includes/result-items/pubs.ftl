<#if result[dc + 'contributor']??>
  <#list result[dc + 'contributor'] as contributor>
    <@displayPerson person=contributor/> ,
  </#list>
</#if>

(${result[dc + 'date']?first})