<#include "macro.ftl">


    <!--
    #DEVNOTE:
    Can we show this message while the page loads?
    I.e. the user clicks a facet, or searches on a keyword, and it may take a few seconds, so show this during that time:
    -->

<div id="loading">Searching database</div>
<script type="text/ecmascript">
    $('#loading').hide();
    window.onbeforeunload = function()  {
        var to = setTimeout(function() { 
            $('#loading').show(200);
            $('#results').hide(2000);
        } , 400);
        window.onunload = function() { clearTimeout(to); $('#loading').hide(); $('#results').show(); }
    };
</script>

<div id="results" class="panel">


    <h1>${facetView.total} results</h1>




    <ul class="results">

    <#list facetView.results as result>
        <#if view??>
            <#include "result-items/" + view + ".ftl">
        </#if>
    </#list>

    </ul>

    <#if facetView.results?size < facetView.total>
        <@moreResults facetView=facetView/>
    </#if>

</div><!-- /panel -->
