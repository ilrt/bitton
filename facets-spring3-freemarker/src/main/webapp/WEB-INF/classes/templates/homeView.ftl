<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<!-- main content -->
<div id="main-content">

    <div>
        <h1>Explore</h1>
        <ul>
            <#list facetView?keys as prop>
                <li><a href="./${prop}">${facetView[prop]}</a></li>
            </#list>
        </ul>
    </div>

    <#include "includes/address-footer.ftl"/>

</div>

<#include "includes/footer.ftl"/>
