<#-- display a default label for a resource -->
<#macro label resource>
    <#if resource['http://www.w3.org/2000/01/rdf-schema#label']??>
        ${resource['http://www.w3.org/2000/01/rdf-schema#label']?first}
    <#elseif resource['http://xmlns.com/foaf/0.1/name']??>
        ${resource['http://xmlns.com/foaf/0.1/name']?first}
    <#elseif resource['http://purl.org/dc/terms/title']??>
        ${resource['http://purl.org/dc/terms/title']?first}
    <#else>
        Untitled resource
    </#if>
</#macro>

<#macro drillForResult result>${contextPath}${servletPath}/?drill=${result?url('UTF-8')}<#list RequestParameters?keys as key>&amp;${key}=${RequestParameters[key]}</#list></#macro>

<#macro moreResults>${contextPath}${servletPath}/?<#if RequestParameters.number?exists>number=${RequestParameters.number?number + 10}<#else>number=20</#if><#list RequestParameters?keys as key><#if key != 'number'>&amp;${key}=${RequestParameters[key]}</#if></#list></#macro>

<#-- check that we are not dealing with a bnode -> http://invalid.org -->
<#macro displayHost host>
    <#if host != 'http://invalid.org'><li><a href="${host}">${host}</a></li></#if>
</#macro>