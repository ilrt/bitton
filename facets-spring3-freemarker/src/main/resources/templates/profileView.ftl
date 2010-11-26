<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>
<#include "includes/header-title.ftl"/>
<#include "includes/header-navigation.ftl"/>

<!-- main content -->
<div id="main-content">

    <div id="content">

        <h2>Profile for ${resource[rdfs+'label']?first}</h2>

        <h3>My Details</h3>
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
     </div>

    <#if resource[relationship + 'collaboratesWith']??>
       <div id="collaborators">
           <h2>Collaborators</h2>
           <#list resource[relationship + 'collaboratesWith'] as collaborator>
             <p><a href="${collaborator}">${collaborator[rdfs+'label']?first}</a></p>
           </#list>
       <div>
    </#if>
<#include "includes/address-footer.ftl"/>

</div>

<#include "includes/footer.ftl"/>
