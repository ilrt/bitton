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
<#assign annot = "http://www.w3.org/2000/10/annotation-ns#">

<#assign alphabet = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'] />

<#-- display a default label for a resource -->
<#-- The following needs to be on a single line as it gets passed to a javascript function -->
<#macro label resource><#if resource[rdfs + 'label']??>${resource[rdfs + 'label']?first?html}<#elseif resource[foaf + 'name']??>${resource[foaf + 'name']?first?html}<#elseif resource[dc + 'title']??>${resource[dc + 'title']?first?html}<#else>Untitled resource</#if></#macro>

<#macro drillForResult result>${contextPath}${servletPath}/item?res=${result?url('UTF-8')}<#list RequestParameters?keys as key>&amp;${key}=${RequestParameters[key]}</#list></#macro>

<#macro moreResults facetView>
    <#assign neighbour=3/>
    <#assign fullList=10/>
    <#assign url=facetStateUrl(Request)/>

    <#if RequestParameters.number?exists>
        <#assign numberofResults=RequestParameters.number?number/>
    <#else>
         <#assign numberofResults=facetView.pageSize/>
    </#if>

    <div class="controls">
        <a class="prev button <#if 1 < facetView.currentPage>active" href="${url}<#if url?index_of("?") != -1>&<#else>?</#if>offset=${(facetView.currentPage-2)*numberofResults}&number=${numberofResults}</#if>">&lt; prev</a>

        <#if facetView.totalPages < 2>
            <!-- don't show anything -->
        <#elseif facetView.totalPages < fullList>
            <#list 1..facetView.totalPages as i>
                <@displayPageLink page=i currentPage=facetView.currentPage number=numberofResults url=url/><#if i_has_next>&nbsp;|&nbsp;</#if></#list>
        <#else>
            <#if facetView.currentPage < fullList>
                <!-- fewer then 10 pages -->
                <#list 1..fullList as i>
                    <@displayPageLink page=i currentPage=facetView.currentPage number=numberofResults url=url/><#if i_has_next>&nbsp;|&nbsp;</#if></#list>
                <#if fullList < facetView.totalPages>
                &nbsp;...&nbsp;${facetView.totalPages}
                </#if>
            <#else>
                <#if facetView.totalPages <= facetView.currentPage+neighbour>
                    <!-- more then [fullList] pages but close to end of list -->
                    <@displayPageLink page=1 currentPage=facetView.currentPage number=numberofResults url=url/>&nbsp;...&nbsp;
                    <#list facetView.currentPage-neighbour..facetView.totalPages as i>
                        <@displayPageLink page=i currentPage=facetView.currentPage number=numberofResults url=url/><#if i_has_next>&nbsp;|&nbsp;</#if></#list>
                <#else>
                    <!-- lost in the middle of the list -->
                    <@displayPageLink page=1 currentPage=facetView.currentPage number=numberofResults url=url/>&nbsp;...&nbsp;
                    <#list facetView.currentPage-neighbour..facetView.currentPage+neighbour as i>
                        <@displayPageLink page=i currentPage=facetView.currentPage number=numberofResults url=url/><#if i_has_next>&nbsp;|&nbsp;</#if></#list>
                    &nbsp;...&nbsp;${facetView.totalPages}
                </#if>
            </#if>
            
        </#if>

    <a class="prev button <#if facetView.currentPage != facetView.totalPages>active" href="${url}<#if url?index_of("?") != -1>&<#else>?</#if>offset=${(facetView.currentPage)*numberofResults}&number=${numberofResults}</#if>">next &gt;</a>
    </div><!-- END .controls -->
</#macro>

<#macro displayPageLink page currentPage number url>
    <#if page == currentPage>
        ${page}
    <#else>
        <a href="${url}<#if url?index_of("?") != -1>&<#else>?</#if>offset=${(page-1)*number}&number=${number}">${page}</a>
    </#if>
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

<#macro displayImpact impact>
    <a href="item?res=${impact?html?replace("#","%23")}">
        <#if impact[dc + 'title']??>${impact[dc + 'title']?first}</#if>
	</a>
</#macro>

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

        <div class="listingcontrols">
            <div class="row">
                <div class="label">Narrow by date</div>
                <div class="slider-container col2">
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
                </div><!-- END .slider-container -->
          </div><!-- END .row -->

            <div class="row">
                <div class="label">Keyword search</div>
                <div class="filters col2">
                <input type='text' class='resultfilter'/>
                </div><!-- END .filters -->
            </div><!-- END .row -->

        </div><!-- END .listingcontrols -->

        <div class="resultsheader">
            <h2 class="resultstotal"></h2><!-- END .resultstotal -->
            <div class="sorting"><span>Sort by</span> <select class="ordering"></select></div>
        </div>

        <div class="results">

          <div class="body"></div><!-- END class="body" -->

          <div class="controls">
            <span class="prev button">&lt; prev</span>
            <span class="pages"></span>
            <span class="next button">next &gt;</span>
          </div><!-- END .controls -->

        </div><!-- END .results -->

    </div><!-- END .combiGraphAndList -->
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
