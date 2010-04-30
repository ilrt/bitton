<div>

<#-- display the URI of a resource -->
<p>${resource}</p>

<#-- display a literal property -->
<p>${resource['http://www.w3.org/2000/01/rdf-schema#label']}</p>

<#-- display a literal property using a prefix -->
<p>${resource['rdfs:label']}</p>

<#-- follow the graph to another resource and display a literal (String) -->
<p>${resource['http://example.org/schema#hasPerson']['http://xmlns.com/foaf/0.1/name']}</p>

<#-- follow the graph to another resource and display a literal (Number) -->
<p>${resource['http://example.org/schema#hasPerson']['http://example.org/schema#hasAgeInYears']}</p>

<#-- follow the graph to another resource and assign the literal (Date) to another variable -->
<#assign aDate = resource['http://example.org/schema#hasPerson']['http://example.org/schema#lastSeen']>

<#-- display the full date time -->
<p>${aDate?string.full}</p>

<#-- display the short date -->
<p>${aDate?string.short}</p>

<#-- display the date with specific format -->
<p>${aDate?string("yyyy-MM-dd HH:mm:ss zzzz")}</p>

</div>