<#include "macro.ftl">
<div id="results" class="panel">

    <h1>${facetView.total} results</h1>

    <ul class="results">
	    <!--
	    #DEVNOTE: Some sample markup for pub search results:
	    
<li class="pub"><span class="contributor">Armstrong, VD, Barnes, SB, Sutherland, RJ.</span> <a class="title" href="#">Collaborative research methodology for investigating teaching and learning: the use of interactive whiteboard technology</a>. <span class="otherdetails"><span class="container-publication">Modern Teaching Methods</span> <span class="vol">32.8</span> <span class="date">(2005)</span> <span class="pages" title="page reference">: 457-469<span>.</span> <a href="#" class="impactcount"><strong>5</strong> impacts</a></li>
	    <li class="pub"><span class="contributor">Brinkley, Alan.</span> <a class="title" href="#">The Unfinished Nation</a>. <span class="otherdetails">New York: Knopf, 1993.</span></li>
-->
    <#list facetView.results as result>
    <li>
        <#if view??>
          <#include "result-items/" + view + ".ftl">
        </#if>
    </li>
    </#list>

    </ul>

    <#if facetView.results?size < facetView.total>
        <@moreResults facetView=facetView/>
    </#if>

</div><!-- /panel -->
