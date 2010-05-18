<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>
<#include "includes/header-title.ftl"/>
<#include "includes/header-navigation.ftl"/>

<!-- main content -->
<div id="main-content">

    <div id="content">

        <h2>${resource['http://purl.org/dc/terms/title']}</h2>

        <p><strong>Grant Number:</strong> ${resource['http://vocab.ouls.ox.ac.uk/projectfunding/projectfunding#grantNumber']}</p>

        <p><strong>Start Date: </strong> ${resource['http://vocab.ouls.ox.ac.uk/projectfunding/projectfunding#startDate']}</p>

        <p><strong>End Date: </strong> ${resource['http://vocab.ouls.ox.ac.uk/projectfunding/projectfunding#endDate']}</p>

        <p><strong>Value: </strong> ${resource['http://vocab.ouls.ox.ac.uk/projectfunding/projectfunding#value']}</p>

        <p><strong>Abstract:</strong></p>

        <p>${resource['http://purl.org/dc/terms/abstract']}</p>

        <p><em><a href="javascript:history.go(-1)">Return to results</a></em></p>

     </div>

<#include "includes/address-footer.ftl"/>

</div>

<#include "includes/footer.ftl"/>