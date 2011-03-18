<!--
#DEVNOTE:
Please use the following markup:

<li class="grant"><a class="title" href="#">Visualising China</a> <span class="amount">&pound;56,000</span> Institute for Learning and Research Technology | Funded by <span class="fundingbody">JISC</span></span></li>

-->
<li class="grant">

    <a class="title" href="<@drillForResult result=result/>"><@label resource=result/></a>.

    <#if result[proj + 'value']??>
        <span class="amount">&pound;${result[proj + 'value']?first}</span>
    </#if>

    <span class="otherdetails">

        <#if result[proj + 'hasPrincipalInvestigator']??>
            <#list result[proj + 'hasPrincipalInvestigator'] as pi><#if pi[rdfs + 'label']??>${pi[rdfs + 'label']?first}</#if></#list>
        </#if>
        <#if result[proj + 'startDate']??>
            (${result[proj + 'startDate']?first?date?string("yyyy")})
        </#if>

        <#if result[proj + 'hostedBy']??>
            <@label resource=result[proj + 'hostedBy']?first/>
        </#if>

    </span>

	<#if result['<-'+resrev+'associatedPublication']??>
		<a href="" class="show_impacts">show ${result['<-'+resrev+'associatedPublication']?size} impact<#if result['<-'+resrev+'associatedPublication']?size != 1>s</#if></a>
		<div class="impacts hide">
			<ul class="results">
				<#list result['<-'+resrev+'associatedPublication'] as impact>
					<li class="impact"><@displayImpact impact=impact/> <span class="otherdetails"></span></li>
				</#list>
			</ul>
		</div>
	</#if>
</li>