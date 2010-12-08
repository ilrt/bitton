<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>
<#include "includes/header-title.ftl"/>
<#include "includes/header-navigation.ftl"/>

<!-- main content -->
<div id="main-content">

    <div id="content">

        <h2>${resource[dc + 'title']?first}</h2>

        <p><strong>Grant Number:</strong> ${resource['proj:grantNumber']?first}</p>
        
        <#if resource['proj:startDate']??>
          <p><strong>Start Date: </strong> ${resource['proj:startDate']?first}</p>
        </#if>
        
        <#if resource['proj:endDate']??>
          <p><strong>End Date: </strong> ${resource['proj:endDate']?first}</p>
        </#if>
        
        <p><strong>Value: </strong> ${resource['proj:value']?first}</p>

        <p><strong>Abstract:</strong></p>
        
        <#if resource[dc + 'abstract']??>
          <p>${resource[dc + 'abstract']?first}</p>
        </#if>
        
        <p><strong>Principal Investigators:</strong></p>

        <#if resource['proj:hasPrincipalInvestigator']??>
            <ul>
                <#list resource['proj:hasPrincipalInvestigator'] as pi>
                    <li>
                        <@displayPerson person=pi/>
                    </li>
                </#list>
            </ul>
        </#if>

        <p><strong>Co-Investigators:</strong></p>

        <#if resource['proj:hasCoInvestigator']??>
            <ul>
                <#list resource['proj:hasCoInvestigator'] as pi>
                    <li>
                        <@displayPerson person=pi/>
                    </li>
                </#list>
            </ul>
        </#if>

        <p><strong>Hosted by:</strong></p>

        <#if resource['proj:hostedBy']??>
            <ul>
                <#list resource['proj:hostedBy'] as host>
                    <@displayHost host=host/>
                </#list>
                <#list resource['proj:hostedBy'] as host>
                    <@displayHost host=host/>
                </#list>
            </ul>
        </#if>

        <#if view??><p><em><a href="javascript:history.go(-1)">Return to results</a></em></p></#if>


     </div>

<#include "includes/address-footer.ftl"/>

</div>

<#include "includes/footer.ftl"/>
