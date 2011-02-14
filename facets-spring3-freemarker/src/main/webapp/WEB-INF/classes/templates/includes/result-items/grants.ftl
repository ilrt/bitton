<#if result[proj + 'value']??>
    <b>£${result[proj + 'value']?first}</b>
</#if>

<@linkToPageFor item=result/>

<#if result[proj + 'hasPrincipalInvestigator']??>
    <#list result[proj + 'hasPrincipalInvestigator'] as pi>
        <#if pi[rdfs + 'label']??>
            <@linkToPageFor item=pi/>,
        </#if>
    </#list>
</#if>
<#if result[proj + 'hostedBy']??>
    <@linkToPageFor item=result[proj + 'hostedBy']?first/>,
</#if>
<#if result[proj + 'funds']??>
    <@linkToPageFor item=result[proj + 'funds']?first/>
</#if>