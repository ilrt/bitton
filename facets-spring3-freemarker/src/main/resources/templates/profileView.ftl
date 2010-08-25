<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>
<#include "includes/header-title.ftl"/>
<#include "includes/header-navigation.ftl"/>

<#assign foaf = "http://xmlns.com/foaf/0.1/">
<#assign rdfs = "http://www.w3.org/2000/01/rdf-schema#">

<!-- main content -->
<div id="main-content">

    <div id="content">

        <h2>Profile for ${resource[rdfs+'label']?first}</h2>

        <h3>My Details</h3>
        <p><strong>Alternative name: </strong> ${resource[foaf+'name']?first}</p>
        <p><strong>UoB username: </strong> ${resource[foaf+'nick']?first}</p>
        <p><strong>Email: </strong> <a href="${resource[foaf+'mbox']?first}">${resource[foaf+'mbox']?first?substring(7)}</a></p>
        <p>See also: <a href="${resource[foaf+'homepage']?first}">merb user page</a></p>

        <h3>My Research</h3>

        <p>...</p>

        <h3>My Departments</h3>

        <p>...</p>
<#--
<#list resource?keys as prop>
    ${prop} = ${resource[prop]?first}<br/>
</#list>
-->
     </div>


<#include "includes/address-footer.ftl"/>

</div>

<#include "includes/footer.ftl"/>