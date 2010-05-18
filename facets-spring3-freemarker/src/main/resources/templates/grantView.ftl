<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>
<#include "includes/header-title.ftl"/>
<#include "includes/header-navigation.ftl"/>

<!-- main content -->
<div id="main-content">

    <div id="content">

        <h2>${resource['http://purl.org/dc/terms/title']?first}</h2>

        <p><strong>Grant Number:</strong> ${resource['http://vocab.ouls.ox.ac.uk/projectfunding/projectfunding#grantNumber']?first}</p>

        <p><strong>Start Date: </strong> ${resource['http://vocab.ouls.ox.ac.uk/projectfunding/projectfunding#startDate']?first}</p>

        <p><strong>End Date: </strong> ${resource['http://vocab.ouls.ox.ac.uk/projectfunding/projectfunding#endDate']?first}</p>

        <p><strong>Value: </strong> ${resource['http://vocab.ouls.ox.ac.uk/projectfunding/projectfunding#value']?first}</p>

        <p><strong>Abstract:</strong></p>

        <p>${resource['http://purl.org/dc/terms/abstract']?first}</p>

        <p><strong>Principal Investigators</strong></p>

        <#if resource['http://vocab.ouls.ox.ac.uk/projectfunding/projectfunding#hasPrincipalInvestigator']??>
            <ul>
                <#list resource['http://vocab.ouls.ox.ac.uk/projectfunding/projectfunding#hasPrincipalInvestigator'] as pi>
                    <li><a href="${pi}">${pi}</a></li>
                </#list>
            </ul>
        </#if>

        <p><strong>Co-Investigators</strong></p>

        <#if resource['http://vocab.ouls.ox.ac.uk/projectfunding/projectfunding#hasCoInvestigator']??>
            <ul>
                <#list resource['http://vocab.ouls.ox.ac.uk/projectfunding/projectfunding#hasCoInvestigator'] as pi>
                    <li><a href="${pi}">${pi}</a></li>
                </#list>
            </ul>
        </#if>

        <p><em><a href="javascript:history.go(-1)">Return to results</a></em></p>


     </div>

<#include "includes/address-footer.ftl"/>

</div>

<#include "includes/footer.ftl"/>