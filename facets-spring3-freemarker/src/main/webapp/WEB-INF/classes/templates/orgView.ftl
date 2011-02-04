<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<!-- main content -->
<div id="main-content">

    <div id="content">

        <h2>${resource[foaf + 'name']?first}</h2>

        <#if resource[foaf + 'homepage']??>
            <p><strong>Homepage:</strong> <a href="${resource[foaf + 'homepage']?first}">${resource[foaf + 'homepage']?first}</a></p>
        </#if>

        <#if resource[aiiso + 'part_of']??>
          <p><strong>Part of: </strong><@displayOrg org=resource[aiiso + 'part_of']?first/></p>
        </#if>

        <#if  resource[foaf + 'member']??>
            <div class="collapsible collapsed">
                <p class="heading"><strong>Members</strong></p>

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

        
        <#if resource['<-http://vocab.bris.ac.uk/resrev#department']??>
          <div class="pubs">
            <ul>
            <#list resource['<-http://vocab.bris.ac.uk/resrev#department'] as pub>
              <li><@linkToPageFor item=pub/></li>
            </#list>
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

</div>

<#include "includes/footer.ftl"/>
