<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<div id="content" class="grant">
	<#if view??><div id="topinfo"><a class="back" href="javascript:history.go(-1)">Back to search results</a></div></#if>

	<div class="col1-3of4">

	<div class="inner">

        <h1>${resource[dc + 'title']?first}</h1>

        <p><strong>Grant Number:</strong> ${resource[proj + 'grantNumber']?first}</p>
        
        <#if resource['proj:startDate']??>
          <p><strong>Start Date: </strong> ${resource[proj + 'startDate']?first}</p>
        </#if>
        
        <#if resource['proj:endDate']??>
          <p><strong>End Date: </strong> ${resource[proj + 'endDate']?first}</p>
        </#if>
        
        <p><strong>Value: </strong>&pound;${resource[proj + 'value']?first}</p>

        <p><strong>Abstract:</strong></p>
        
        <#if resource[dc + 'abstract']??>
          <p>${resource[dc + 'abstract']?first}</p>
        </#if>
        
        <p><strong>Hosted by:</strong></p>

        <#if resource[proj + 'hostedBy']??>
            <ul>
                <#list resource[proj + 'hostedBy'] as host>
                    <@displayHost host=host/>
                </#list>
            </ul>
        </#if>



</div><!-- /inner -->
</div><!-- /col -->

	<div class="col4of4 sidebar">
		<div class="section">

        <#if resource[proj + 'hasPrincipalInvestigator']??>
        <h2>Principal investigators</h2>
            <ul class="objects">
                <#list resource[proj + 'hasPrincipalInvestigator'] as pi>
                    <#if pi[rdfs + 'label']??>
                      <li class="person"><@displayPerson person=pi/></li>
                    </#if>
                </#list>
            </ul>
        </#if>

        <#if resource[proj + 'hasCoInvestigator']??>
	<h2>Co-investigators</h2>
            <ul class="objects">
                <#list resource[proj + 'hasCoInvestigator'] as pi>
                  <#if pi[rdfs + 'label']??>
                      <li class="person"><@displayPerson person=pi/></li>
                  </#if>
                </#list>
            </ul>
        </#if>

	</div><!-- /section -->
		<div class="help"><a href="page?name=datasources">Where does this data come from?</a></div>

	</div><!-- /col4of4 -->

</div><!-- /content -->
<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
