<#assign foaf = "http://xmlns.com/foaf/0.1/">
<#assign rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#">
<#assign rdfs = "http://www.w3.org/2000/01/rdf-schema#">
<#assign dc = "http://purl.org/dc/terms/">
<#assign elements = "http://purl.org/dc/elements/1.1/">
<#assign closed = "http://vocab.bris.ac.uk/rr/closed#">
<#assign resrev = "http://vocab.bris.ac.uk/resrev#">
<#assign aiiso = "http://purl.org/vocab/aiiso/schema#">
<#assign bibo = "http://purl.org/ontology/bibo/">
<#assign relationship = "http://purl.org/vocab/relationship/">
<#assign proj = "http://vocab.ouls.ox.ac.uk/projectfunding#">

<#assign alphabet = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'] />

<#-- display a default label for a resource -->
<#-- The following needs to be on a single line as it gets passed to a javascript function -->
<#macro label resource><#if resource[rdfs + 'label']??>${resource[rdfs + 'label']?first?html}<#elseif resource[foaf + 'name']??>${resource[foaf + 'name']?first?html}<#elseif resource[dc + 'title']??>${resource[dc + 'title']?first?html}<#else>Untitled resource</#if></#macro>

<#macro drillForResult result>${contextPath}${servletPath}/item?res=${result?url('UTF-8')}<#list RequestParameters?keys as key>&amp;${key}=${RequestParameters[key]}</#list></#macro>

<#macro moreResults facetView>
    <#assign neighbour=3/>
    <#assign fullList=10/>

    <#if RequestParameters.number?exists>
        <#assign numberofResults=RequestParameters.number?number/>
    <#else>
         <#assign numberofResults=facetView.pageSize/>
    </#if>

    <p>
        <#if facetView.totalPages < 2>
            <!-- don't show anything -->
        <#elseif facetView.totalPages < fullList>
            <#list 1..facetView.totalPages as i>
                <@displayPageLink page=i currentPage=facetView.currentPage number=numberofResults/>&nbsp;
            </#list>
        <#else>
            <#if facetView.currentPage < fullList>
                <!-- fewer then 10 pages -->
                <#list 1..fullList as i>
                    <@displayPageLink page=i currentPage=facetView.currentPage number=numberofResults/>
                </#list>
                <#if fullList < facetView.totalPages>
                ... ${facetView.totalPages}
                </#if>
            <#else>
                <#if facetView.totalPages <= facetView.currentPage+neighbour>
                    <!-- more then [fullList] pages but close to end of list -->
                    <@displayPageLink page=1 currentPage=facetView.currentPage number=numberofResults/> ...
                    <#list facetView.currentPage-neighbour..facetView.totalPages as i>
                        <@displayPageLink page=i currentPage=facetView.currentPage number=numberofResults/>
                    </#list>
                <#else>
                    <!-- lost in the middle of the list -->
                    <@displayPageLink page=1 currentPage=facetView.currentPage number=numberofResults/> ...
                    <#list facetView.currentPage-neighbour..facetView.currentPage+neighbour as i>
                        <@displayPageLink page=i currentPage=facetView.currentPage number=numberofResults/>
                    </#list>
                    ... ${facetView.totalPages}
                </#if>
            </#if>
            
        </#if>
    </p>
</#macro>

<#macro displayPageLink page currentPage number>
    <#if page == currentPage>
        ${page}
    <#else>
        <#assign url=facetStateUrl(Request)/>
        <#if url?index_of("?") != -1>
            <a href="${url}&offset=${(page-1)*number}&number=${number}">${page}</a>
        <#else>
            <a href="${url}?offset=${(page-1)*number}&number=${number}">${page}</a>
        </#if>
    </#if>&nbsp;
</#macro>

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
    <a href="item?res=${person?html?replace("#","%23")}">
        <#if person[rdfs + 'label']??>${person[rdfs + 'label']?first}</#if></a></#macro>

<#macro displayOrg org>
    <a href="item?res=${org?html?replace("#","%23")}">
        <#if org[rdfs + 'label']??>${org[rdfs + 'label']?first}</#if></a></#macro>

<#macro linkToPageFor item>
    <a href="<@drillForResult result=item/>"><@label resource=item/></a>
</#macro>

<#macro displayPublication pub>
    <#if pub[dc + 'contributor']??>
      <span class='contributor'><#list pub[dc + 'contributor'] as contributor><@label resource=contributor/><#if contributor_has_next>, </#if></#list>.</span>
    </#if>

    <a class='title' href='<@drillForResult result=pub/>'><@label resource=pub/></a>.

    <span class='otherdetails'>
    <#if pub[dc + 'date']??>
        (${pub[dc + 'date']?first})
    </#if>
    <#if pub[elements + 'publisher']??>
        ${pub[elements + 'publisher']?first}
    </#if>
    <#if pub[bibo + 'isbn']??>
        ${pub[bibo + 'isbn']?first}
    </#if>
    <#if pub[bibo + 'volume']??>
        Vol. ${pub[bibo + 'volume']?first}
    </#if>
    <#if pub[dc + 'isPartOf']??>
        Part of ${pub[dc + 'isPartOf']?first['label']}
    </#if>
    <#if pub[bibo + 'pageStart']?? && pub[bibo + 'pageEnd']??>
       Pages
        ${pub[bibo + 'pageStart']?first} - ${pub[bibo + 'pageEnd']?first}
    <#elseif pub[bibo + 'pageStart']??>
        Page ${pub[bibo + 'pageStart']?first}
    <#elseif pub[bibo + 'pageEnd']??>
        Page ${pub[bibo + 'pageEnd']?first}
    </#if>
    </span>
</#macro>

<#macro generateGraphHTML graphCount>

	<script type="text/javascript">
		<#-- create global object for each collection -->
		if (typeof graphData == "undefined") graphData = new Object();
		graphData[${graphCount}] = new Object();
	</script>

    <div class="combiGraphAndList" id="combiGraphAndList_${graphCount}">
        <div class="fig">
			<script type="text/javascript+protovis">
				/* The root panel. */
				graphData[${graphCount}].vis = new pv.Panel();
				initGraph(${graphCount});
				initEvents(${graphCount});
				showResults(${graphCount});
				graphData[${graphCount}].vis.render();
			</script>
        </div><!-- END #fig -->

        <div class="slider-container">
            <div class="slider-outer">
                <div class="slider-inner">
                    <div class="valueLeft">
                        <input type="text" class="startYear" />
                    </div>
                    <div class="slider-range"></div>
                </div>
            </div>
            <div class="valueRight">
                <input type="text" class="endYear" />
            </div>
        </div><!-- END class="slider-container" -->

        <div class="clearing">&nbsp;</div>

        <div class="results">
          <div class="body"></div><!-- END class="body" -->

          <div class="controls">
            <span class="prev">Previous</span>
            <span class="resultstotal"></span>
            <span class="next">Next</span>
          </div>

          <select class="ordering"></select>
        </div><!-- END class="results" -->

    </div><!-- END class="combiGraphAndList" -->
</#macro>

<#macro debug resource>
    <table class="debug">
        <#list resource?keys as key>
            <#list resource[key] as value>
                <tr><td>${key}</td><td>${value}</td></tr>
            </#list>
        </#list>
    </table>
</#macro>