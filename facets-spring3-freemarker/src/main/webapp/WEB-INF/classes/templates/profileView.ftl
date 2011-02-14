<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<!-- main content -->
<div id="content">

        <#if resource[rdfs+'label']??>
            <h1>Profile for ${resource[rdfs+'label']?first}</h1>
        <#elseif resource[foaf+'name']??>
            <h1>Profile for ${resource[foaf+'name']?first}</h1>
        </#if>

        <h2>Details</h2>
        <p><strong>Name: </strong> ${resource[foaf+'name']?first}</p>
        <#if profileview??>
            <p><strong>UoB username: </strong> ${resource[foaf+'nick']?first}</p>
        </#if>

        <#if resource[foaf+'mbox']??>
            <p><strong>Email: </strong> <a href="${resource[foaf+'mbox']?first}">${resource[foaf+'mbox']?first?substring(7)}</a></p>
        </#if>

        <#if resource[foaf+'homepage']??>
            <p>See also: <a href="${resource[foaf+'homepage']?first}">merb user page</a></p>
        </#if>

        <h2>Additional Info</h2>
        <ul>
            <#if resource['<-' + proj + 'hasPrincipalInvestigator']??>
                <li>Is named as PI on <b>${resource['<-' + proj + 'hasPrincipalInvestigator']?size}</b> grant<#if resource['<-' + proj + 'hasPrincipalInvestigator']?size != 1>s</#if></li>
            </#if>
            <#if resource['<-' + proj + 'hasCoInvestigator']??>
                <li>Is co-investigator of <b>${resource['<-' + proj + 'hasCoInvestigator']?size}</b> grant<#if resource['<-' + proj + 'hasCoInvestigator']?size != 1>s</#if></li>
            </#if>
            <#if resource[proj + 'contributesTo']??>
                <li>Contributes to <b>${resource[proj + 'contributesTo']?size}</b> grant<#if resource[proj + 'contributesTo']?size != 1>s</#if></li>
            </#if>
            <#if resource['<-' + dc + 'contributor']??>
                <li>Has contributed to <b>${resource['<-' + dc + 'contributor']?size}</b> publication<#if resource['<-' + dc + 'contributor']?size != 1>s</#if></li>
            </#if>
        </ul>

        <#if resource['<-' + foaf + 'member']??>
           <div class="departments">
               <h2>My Departments</h2>
               <ul>
                   <#list resource['<-' + foaf + 'member'] as member>
                        <li><@displayOrg org=member/></li>
                   </#list>
                </ul>
           <div>
        </#if>

        <#if profileview??>
            <h2>My Research</h2>

        </#if>

<#--
<#list resource?keys as prop>
    ${prop} = ${resource[prop]?first}<br/>
</#list>
-->

    <#if resource[relationship + 'collaboratesWith']??>
       <div id="collaborators">
           <h2>Collaborators</h2>
           <#list resource[relationship + 'collaboratesWith'] as collaborator>
             <p><a href="${collaborator}">${collaborator[rdfs+'label']?first}</a></p>
           </#list>
       <div>
    </#if>

<!--
        <table class="debug">
            <#list resource?keys as key>
                <#list resource[key] as value>
                    <tr><td>${key}</td><td>${value}</td></tr>
                </#list>
            </#list>
        </table>
-->

</div><!-- /content -->
<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
