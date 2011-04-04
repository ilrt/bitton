<#include "../includes/macro.ftl"/>
<#include "../includes/header.ftl"/>

<!-- main content -->
<div id="static-page">

    <div id="content">
    <div class="col1-2of3">

        <h1>Searching using the Research Revealed browser</h1>

        <p>The search engine used in the Research Revealed system is based on <a href="http://lucene.apache.org/">Lucene</a>. Lucene uses a slightly different syntax to other search engines (such as Google).</p>

        <h2>Boolean Operators</h2>
        
        <div class="code">"jakarta apache" AND "Apache Lucene"</div> 

        <div class="code">"jakarta apache" OR jakarta</div> 

        <div class="code">(jakarta OR apache) AND website</div>

        <h2>Wildcard Searches</h2>
        <p>To perform a single character wildcard search use the "?" symbol.<br/>
        <p>To perform a multiple character wildcard search use the "*" symbol.</p>
        <p>The single character wildcard search looks for terms that match that with the single character replaced. For example, to search for "text" or "test" you can use the search:</p>
        <div class="code">te?t</div>

        <p>Multiple character wildcard searches looks for 0 or more characters. For example, to search for test, tests or tester, you can use the search: </p>
        <div class="code">test*</div>

        <p>More examples can be found on the <a href="http://lucene.apache.org/java/2_4_0/queryparsersyntax.html">Lucene syntax page</a></p>
</div><!-- /col -->
     </div><!-- /content -->


<#include "../includes/address-footer.ftl"/>

</div>

<#include "../includes/footer.ftl"/>
