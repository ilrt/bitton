<#-- @ftlvariable name="results" type="java.util.List" -->

<p>Hello</p>

<p>${results?size}</p>

<#list results as result>
${result.label}
</#list>