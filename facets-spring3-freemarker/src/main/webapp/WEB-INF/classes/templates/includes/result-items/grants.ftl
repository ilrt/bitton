<#if result[proj + 'value']??>
    <b>£${result[proj + 'value']?first}</b>
</#if>
<#if result[proj + 'hasPrincipalInvestigator']??>
    <@linkToPageFor item=result[proj + 'hasPrincipalInvestigator']?first/>,
</#if>
<#if result[proj + 'hostedBy']??>
    <@linkToPageFor item=result[proj + 'hostedBy']?first/>,
</#if>
<#if result[proj + 'funds']??>
    <@linkToPageFor item=result[proj + 'funds']?first/>
</#if>