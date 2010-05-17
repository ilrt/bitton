<#-- display a default label for a resource -->
<#macro label resource>
    <#if resource['http://xmlns.com/foaf/0.1/name']??>
        ${resource['http://xmlns.com/foaf/0.1/name']}
    <#elseif resource['http://purl.org/dc/terms/title']??>
        ${resource['http://purl.org/dc/terms/title']}
    <#else>
        Untitled resource
    </#if>
</#macro>

<#macro moreResults>${contextPath}${servletPath}/?<#if RequestParameters.number?exists>number=${RequestParameters.number?number + 10}<#else>number=20</#if><#list RequestParameters?keys as key><#if key != 'number'>&amp;${key}=${RequestParameters[key]}</#if></#list></#macro>