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
<#macro label resource>
    <#if resource[rdfs + 'label']??>
    ${resource[rdfs + 'label']?first}<#elseif resource[foaf + 'name']??>
    ${resource[foaf + 'name']?first}<#elseif resource[dc + 'title']??>
    ${resource[dc + 'title']?first}<#else>
    Untitled resource</#if></#macro>

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
    <a href="${person}">
        <#if person[rdfs + 'label']??>${person[rdfs + 'label']?first}</#if>
        <#if !person[rdfs + 'label']??>${person}</#if>
    </a>
</#macro>

<#macro displayOrg org>
    <a href="${org}">
        <#if org[rdfs + 'label']??>${org[rdfs + 'label']?first}</#if>
        <#if !org[rdfs + 'label']??>${org}</#if>
    </a>
</#macro>

<#macro linkToPageFor item>
    <a href="<@drillForResult result=item/>"><@label resource=item/></a>
</#macro>