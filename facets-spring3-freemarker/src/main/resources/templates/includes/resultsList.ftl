<div id="results">
<#list facetView.results as result>
    <#--
    <p>${result}</p>
    <p class="result">${result['http://xmlns.com/foaf/0.1/name']}</p>
    <p>${result!'Not a valid URI'}</p>
    -->
    <p>${result}</p>
    <p class="result">${result['http://xmlns.com/foaf/0.1/name']}</p>
</#list>
</div>