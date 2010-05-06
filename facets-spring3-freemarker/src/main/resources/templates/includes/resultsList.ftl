<div id="results">
<#list facetView.results as result>
    <p>${result}</p>
    <p class="result">${result['rdfs:label']}</p>
</#list>
</div>