<!-- top facet list -->
<div id="main-content">
    <p>${facet}</p>
    <ul>
        <#list results as result>
            <li><a href="./${result['uri']}">${result['label']} (${result['count']})</a></li>
        </#list>
    </ul>
</div>