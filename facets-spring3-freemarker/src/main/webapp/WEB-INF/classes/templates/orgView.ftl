<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

    <div id="content">

        <h1>${resource[foaf + 'name']?first}</h1>

        <#if resource[foaf + 'homepage']??>
            <p><strong>Homepage:</strong> <a href="${resource[foaf + 'homepage']?first}">${resource[foaf + 'homepage']?first}</a></p>
        </#if>

        <#if resource[aiiso + 'part_of']??>
          <p><strong>Part of: </strong><@displayOrg org=resource[aiiso + 'part_of']?first/></p>
        </#if>
        
        <#if recentoutputs??>
            <div class="recent-outputs">
                <h2>Recent outputs</h2>
                <#list recentoutputs as output>
                    <p><@linkToPageFor item=output/></p>
                </#list>
            </div>
        </#if>
        
        <#if recentgrants??>
            <div class="recent-grants">
                <h2>Recent grants</h2>
                <#list recentgrants as grant>
                    <p><@linkToPageFor item=grant/></p>
                </#list>
            </div>
        </#if>

        <#if  resource[foaf + 'member']??>
            <div class="collapsible collapsed">
                <h2>Members</h2>

                <ul class="content collapsed">
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

        <table class="debug">
            <#list resource?keys as key>
                <#list resource[key] as value>
                    <tr><td>${key}</td><td>${value}</td></tr>
                </#list>
            </#list>
        </table>


        <#if view??><p><em><a href="javascript:history.go(-1)">Return to results</a></em></p></#if>

     </div>

<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
