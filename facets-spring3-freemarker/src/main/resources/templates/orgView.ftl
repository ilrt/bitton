<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>
<#include "includes/header-title.ftl"/>
<#include "includes/header-navigation.ftl"/>

<!-- main content -->
<div id="main-content">

    <div id="content">

        <h2>${resource['http://xmlns.com/foaf/0.1/name']?first}</h2>

        <#if resource['http://xmlns.com/foaf/0.1/homepage']??>
            <p><strong>Homepage:</strong> <a href="${resource['http://xmlns.com/foaf/0.1/homepage']?first}">${resource['http://xmlns.com/foaf/0.1/homepage']?first}</a></p>
        </#if>

        <#if resource['http://purl.org/vocab/aiiso/schema#part_of']??>
          <p><strong>Part of: </strong><a href="${resource['http://purl.org/vocab/aiiso/schema#part_of']?first}">${resource['http://purl.org/vocab/aiiso/schema#part_of']?first}</a></p>
        </#if>

        <#if resource['http://vocab.ilrt.bris.ac.uk/rr/closed#member']?? && resource['http://xmlns.com/foaf/0.1/member']??>
            <div class="collapsible collapsed">
                <p class="heading"><strong>Members</strong></p>

                <ul class="content collapsed">
                    <#if resource['http://vocab.ilrt.bris.ac.uk/rr/closed#member']??>
                        <#list resource['http://vocab.ilrt.bris.ac.uk/rr/closed#member'] as member>
                            <li><a href="${member}">${member}</a></li>
                        </#list>
                    </#if>
                    <#if resource['http://xmlns.com/foaf/0.1/member']??>
                        <#list resource['http://xmlns.com/foaf/0.1/member'] as member>
                            <li><a href="${member}">${member}</a></li>
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
