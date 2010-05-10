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