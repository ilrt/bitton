<li>
    <#assign found=false/>
    <#list result[rdf + 'type'] as type>
        <#if type = foaf + "Person">
            <#assign found=true/>
            <#include "people.ftl">
        <#elseif type = dc + "Publication">
            <#assign found=true/>
            <#include "pubs.ftl">
        <#elseif type = proj + "Grant">
            <#assign found=true/>
            <#include "grants.ftl">
        </#if>
    </#list>

    <#if !found>
       <@linkToPageFor item=result/> ${result}
        <table class="debug">
            <#list result?keys as key>
                <#list result[key] as value>
                    <tr><td>${key}</td><td>${value}</td></tr>
                </#list>
            </#list>
        </table>

    </#if>
</li>