<!--
#DEVNOTE:
Please use the following markup:

<li class="grant"><a class="title" href="#">Visualising China</a> <span class="amount">&pound;56,000</span> <span class="otherdetails">Dr Jasper Tregold (2010) Institute for Learning and Research Technology | Funded by <span class="fundingbody">JISC</span></span></li>

-->
<li class="grant">
    <#if result[proj + 'value']??>
        <strong>&pound;${result[proj + 'value']?first}</strong>
    </#if>
<#if result[proj + 'value']??>
    <strong>&pound;${result[proj + 'value']?first}</strong>
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
</li>
