<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<!-- main content -->
<div id="content" class="${viewcontext}">

    <div class="col1of3">
        <#include "includes/facet.ftl"/>
    </div><!-- /col -->

    <div class="col2-3of3">
        <#include "includes/textsearch-facet.ftl"/>
        <#include "includes/resultsList.ftl"/>
    </div><!-- /col -->

</div><!-- /content -->

    <#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
