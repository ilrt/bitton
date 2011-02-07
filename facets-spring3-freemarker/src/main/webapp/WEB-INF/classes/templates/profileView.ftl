<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<!-- main content -->
<div id="content">


        <h1>Profile for ${resource[rdfs+'label']?first}</h1>

        <h2>My Details</h2>
        <p><strong>Name: </strong> ${resource[foaf+'name']?first}</p>
        <#if profileview??>
            <p><strong>UoB username: </strong> ${resource[foaf+'nick']?first}</p>
        </#if>
        <p><strong>Email: </strong> <a href="${resource[foaf+'mbox']?first}">${resource[foaf+'mbox']?first?substring(7)}</a></p>
        <p>See also: <a href="${resource[foaf+'homepage']?first}">merb user page</a></p>

        <#if profileview??>
            <h3>My Research</h3>

            <p>...</p>

            <h3>My Departments</h3>

            <p>...</p>
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

</div><!-- /content -->
<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
