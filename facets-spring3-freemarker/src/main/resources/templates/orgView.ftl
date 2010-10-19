<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>
<#include "includes/header-title.ftl"/>
<#include "includes/header-navigation.ftl"/>

<!-- main content -->
<div id="main-content">

    <div id="content">

        <h2>${resource[foaf + 'name']?first}</h2>

        <#if resource[foaf + 'homepage']??>
            <p><strong>Homepage:</strong> <a href="${resource[foaf + 'homepage']?first}">${resource[foaf + 'homepage']?first}</a></p>
        </#if>

        <#if resource[aiiso + 'part_of']??>
          <p><strong>Part of: </strong><a href="${resource[aiiso + 'part_of']?first}">${resource[aiiso + 'part_of']?first}</a></p>
        </#if>

        <#if resource[closed + 'member']?? && resource[foaf + 'member']??>
            <div class="collapsible collapsed">
                <p class="heading"><strong>Members</strong></p>

                <ul class="content collapsed">
                    <#if resource[closed + 'member']??>
                        <#list resource[closed + 'member'] as member>
                            <li>
                                 <@displayPerson person=member/>
                            </li>
                        </#list>
                    </#if>
                    <#if resource[foaf + 'member']??>
                        <#list resource[foaf + 'member'] as member>
                            <li>
                                 <@displayPerson person=member/>
                            </li>
                        </#list>
                    </#if>
                </ul>
            </div>
        </#if>


        <#if view??><p><em><a href="javascript:history.go(-1)">Return to results</a></em></p></#if>


     </div>

<#include "includes/address-footer.ftl"/>

</div>

<#include "includes/footer.ftl"/>
