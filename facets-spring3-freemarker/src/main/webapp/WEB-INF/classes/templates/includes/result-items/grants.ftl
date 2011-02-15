<li class="grant">

    <#if result[proj + 'value']??>
        <strong>&pound;${result[proj + 'value']?first}</strong>
    </#if>

    <@linkToPageFor item=result/>

    <#if result[proj + 'hasPrincipalInvestigator']??>
        <span class="contributor"><#list result[proj + 'hasPrincipalInvestigator'] as pi><#if pi[rdfs + 'label']??>${pi[rdfs + 'label']?first}</#if></#list>.</span>
    </#if>

    <span class="otherdetails">
    <#if result[proj + 'hostedBy']??>
        <@label resource=result[proj + 'hostedBy']?first/>
    </#if>
    <#if result[proj + 'funds']??>
        <@linkToPageFor item=result[proj + 'funds']?first/>
    </#if>
    </span>
</li>