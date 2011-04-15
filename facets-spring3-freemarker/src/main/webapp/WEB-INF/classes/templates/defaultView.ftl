<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>
<!-- main content -->
<div id="main-content">

    <div id="content">

        <h2>Default View</h2>

        <table>
            <#list resource?keys as key>
                <#list resource[key] as value>
                    <tr><td>${key}</td><td>${value}</td></tr>
                </#list>
            </#list>
        </table>

     </div>


<#include "includes/address-footer.ftl"/>

</div>
<#include "includes/footer.ftl"/>
