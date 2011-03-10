<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

    <div id="content">

        <#if view??><p><a href="javascript:history.go(-1)">&laquo; Return to results</a></p></#if>

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
        
        <p><strong>Principal Investigators:</strong></p>

        <#if resource[proj + 'hasPrincipalInvestigator']??>
            <ul>
                <#list resource[proj + 'hasPrincipalInvestigator'] as pi>
                    <#if pi[rdfs + 'label']??>
                      <li><@displayPerson person=pi/></li>
                    </#if>
                </#list>
            </ul>
        </#if>

        <p><strong>Co-Investigators:</strong></p>

        <#if resource[proj + 'hasCoInvestigator']??>
            <ul>
                <#list resource[proj + 'hasCoInvestigator'] as pi>
                  <#if pi[rdfs + 'label']??>
                      <li><@displayPerson person=pi/></li>
                  </#if>
                </#list>
            </ul>
        </#if>

        <p><strong>Hosted by:</strong></p>

        <#if resource[proj + 'hostedBy']??>
            <ul>
                <#list resource[proj + 'hostedBy'] as host>
                    <@displayHost host=host/>
                </#list>
            </ul>
        </#if>



     </div>

<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
