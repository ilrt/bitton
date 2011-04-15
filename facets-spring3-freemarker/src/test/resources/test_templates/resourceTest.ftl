<div>

<#-- display the URI of a resource -->
<p>${resource}</p>

<#-- display a literal property -->
<#list resource['http://www.w3.org/2000/01/rdf-schema#label'] as item>
    <p>${item}</p>
</#list>

<#-- display a literal property using a prefix -->
<#list resource['rdfs:label'] as item>
    <p>${item}</p>
</#list>

<#-- follow the graph to another resource and display a literal (String) -->
<#list resource['http://example.org/schema#hasPerson'] as person>
    <#list person['http://xmlns.com/foaf/0.1/name'] as name><p>${name}</p></#list>
</#list>


<#-- follow the graph to another resource and display a literal (Number) -->
<#list resource['http://example.org/schema#hasPerson'] as person>
    <#list person['http://example.org/schema#hasAgeInYears'] as age><p>${age}</p></#list>
</#list>

<#-- follow the graph to another resource and assign the literal (Date) to another variable -->
<#list resource['http://example.org/schema#hasPerson'] as person>
    <#list person['http://example.org/schema#lastSeen'] as aDate>

        <#-- display the full date time -->
        <p>${aDate?string.full}</p>

        <#-- display the short date -->
        <p>${aDate?string.short}</p>

        <#-- display the date with specific format -->
        <p>${aDate?string("yyyy-MM-dd HH:mm:ss zzzz")}</p>

    </#list>
</#list>


</div>