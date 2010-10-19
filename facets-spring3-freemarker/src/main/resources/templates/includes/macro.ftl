<#assign foaf = "http://xmlns.com/foaf/0.1/">
<#assign rdfs = "http://www.w3.org/2000/01/rdf-schema#">
<#assign dc = "http://purl.org/dc/terms/">
<#assign closed = "http://vocab.ilrt.bris.ac.uk/rr/closed#">
<#assign aiiso = "http://purl.org/vocab/aiiso/schema#">

<#-- display a default label for a resource -->
<#macro label resource>
    <#if resource[rdfs + 'label']??>
        ${resource[rdfs + 'label']?first}
    <#elseif resource[foaf + 'name']??>
        ${resource[foaf + 'name']?first}
    <#elseif resource[dc + 'title']??>
        ${resource[dc + 'title']?first}
    <#else>
        Untitled resource
    </#if>
</#macro>

<#macro drillForResult result>${contextPath}${servletPath}/item?res=${result?url('UTF-8')}<#list RequestParameters?keys as key>&amp;${key}=${RequestParameters[key]}</#list></#macro>

<#macro moreResults>${contextPath}${servletPath}/${view}/?<#if RequestParameters.number?exists>number=${RequestParameters.number?number + 10}<#else>number=20</#if><#list RequestParameters?keys as key><#if key != 'number'>&amp;${key}=${RequestParameters[key]}</#if></#list></#macro>

<#-- check that we are not dealing with a bnode -> http://invalid.org -->
<#macro displayHost host>
    <#if host != 'http://invalid.org'>
        <li>
            <a href="${host}">
                <#if host[rdfs + 'label']??>${host[rdfs + 'label']?first}</#if>
                <#if !host[rdfs + 'label']??>${host}</#if>
            </a>
        </li>
    </#if>
</#macro>

<#macro displayPerson person>
    <a href="${person}">
        <#if person[rdfs + 'label']??>${person[rdfs + 'label']?first}</#if>
        <#if !person[rdfs + 'label']??>${person}</#if>
    </a>
</#macro>