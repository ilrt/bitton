<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>
<#include "includes/header-title.ftl"/>
<#include "includes/header-navigation.ftl"/>

<!-- main content -->
<div id="main-content">

    <div>
        <p>Please choose a view to search</p>
        <ul>
            <#list facetView?keys as prop>
                <li><a href="./${prop}">${facetView[prop]}</a></li>
            </#list>
        </ul>
    </div>

    <#include "includes/address-footer.ftl"/>

</div>

<#include "includes/footer.ftl"/>