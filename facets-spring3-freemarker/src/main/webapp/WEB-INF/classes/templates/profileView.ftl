<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<!-- main content -->
<div id="content">

    <#if resource[rdfs+'label']??>
        <h1>Profile for ${resource[rdfs+'label']?first}</h1>
    <#elseif resource[foaf+'name']??>
        <h1>Profile for ${resource[foaf+'name']?first}</h1>
    </#if>

    <h2>Details</h2>
    <p><strong>Name: </strong> ${resource[foaf+'name']?first}</p>
    <#if profileview??>
        <p><strong>UoB username: </strong> ${resource[foaf+'nick']?first}</p>
    </#if>

    <#if resource[foaf+'mbox']??>
        <p><strong>Email: </strong> <a href="${resource[foaf+'mbox']?first}">${resource[foaf+'mbox']?first?substring(7)}</a></p>
    </#if>

    <#if resource[foaf+'homepage']??>
        <p>See also: <a href="${resource[foaf+'homepage']?first}">merb user page</a></p>
    </#if>

    <h2>Additional Info</h2>
    <ul>
        <#if resource['<-' + proj + 'hasPrincipalInvestigator']??>
            <li>Is named as PI on <b>${resource['<-' + proj + 'hasPrincipalInvestigator']?size}</b> grant<#if resource['<-' + proj + 'hasPrincipalInvestigator']?size != 1>s</#if></li>
        </#if>
        <#if resource['<-' + proj + 'hasCoInvestigator']??>
            <li>Is co-investigator of <b>${resource['<-' + proj + 'hasCoInvestigator']?size}</b> grant<#if resource['<-' + proj + 'hasCoInvestigator']?size != 1>s</#if></li>
        </#if>
        <#if resource[proj + 'contributesTo']??>
            <li>Contributes to <b>${resource[proj + 'contributesTo']?size}</b> grant<#if resource[proj + 'contributesTo']?size != 1>s</#if></li>
        </#if>
        <#if resource['<-' + dc + 'contributor']??>
            <li>Has contributed to <b>${resource['<-' + dc + 'contributor']?size}</b> publication<#if resource['<-' + dc + 'contributor']?size != 1>s</#if></li>
        </#if>
    </ul>

    <#if resource['<-' + foaf + 'member']??>
       <div class="departments">
           <h2>My Departments</h2>
           <ul>
               <#list resource['<-' + foaf + 'member'] as member>
                    <#if member[rdfs + 'label']?? && member[rdfs + 'label']?first != invalidUrl>
                        <li><@displayOrg org=member/></li>
                    </#if>
               </#list>
            </ul>
       <div>
    </#if>


    <div id="tabs">
        <ul class="tabs">
            <li class="first current"><a href="#tabs-pub">My Research Outputs <span class="count"><#if publist??>${publist.size}<#else>0</#if></span></a></li>
            <li><a href="#tabs-collab">My Collaborators <span class="count"><#if resource[relationship + 'collaboratesWith']??>${resource[relationship + 'collaboratesWith']?size}<#else>0</#if></span></a></li>
        </ul>

        <div class="tabbedcontent">
            <div class="inner" id="tabs-pub">
                <#if publist??  && 0 < publist.size>
                <script>
                        <#-- create global object for each collection -->
                        <#if graphCount??><#assign graphCount=graphCount+1/><#else><#assign graphCount=0/></#if>
                        if (typeof graphData == "undefined") graphData = new Object();

                        <#-- vars -->
                        graphData[${graphCount}] = new Object();
                        graphData[${graphCount}].maxPageSize = 5;
                        graphData[${graphCount}].currentPage = 0;

                        graphData[${graphCount}].results = new Array();

                        var sortOptions = new Array();
                        var obj = new Object();
                        obj.title = "Title (asc)";
                        obj.fun = sortResultsOnLabelAsc;
                        sortOptions['sortResultsOnLabelAsc'] = obj;
                        obj = new Object();
                        obj.title = "Title (desc)";
                        obj.fun = sortResultsOnLabelDesc;
                        sortOptions['sortResultsOnLabelDesc'] = obj;
                        obj = new Object();
                        obj.title = "Date (asc)";
                        obj.fun = sortResultsOnDateThenLabelAsc;
                        sortOptions['sortResultsOnDateThenLabelAsc'] = obj;
                        obj = new Object();
                        obj.title = "Date (desc)";
                        obj.fun = sortResultsOnDateThenLabelDesc;
                        sortOptions['sortResultsOnDateThenLabelDesc'] = obj;
                        graphData[${graphCount}].sortOptions = sortOptions;

                        <#list publist.collection as item>
                            <#if item[dc + 'date']??>
                                var o = new Object();
                                o.year = ${item[dc + 'date']?first?date?string("yyyy")};
                                o.label = "<@label resource=item/>";
                                o.citation = "<#if item[dc + 'contributor']??><span class='contributor'><#list item[dc + 'contributor'] as contributor><@label resource=contributor/><#if contributor_has_next>, </#if></#list>.</span> </#if><a class='title' href='<@drillForResult result=item/>'><@label resource=item/></a>. <span class='otherdetails'><#if item[dc + 'date']??>(${item[dc + 'date']?first?date?string("yyyy")}) </#if><#if item[elements + 'publisher']??>${item[elements + 'publisher']?first}</#if><#if item[bibo + 'isbn']??> ${item[bibo + 'isbn']?first}</#if><#if item[bibo + 'volume']??> Vol. ${item[bibo + 'volume']?first}</#if><#if item[dc + 'isPartOf']??> Part of ${item[dc + 'isPartOf']?first['label']}</#if><#if item[bibo + 'pageStart']?? && item[bibo + 'pageEnd']??> Pages ${item[bibo + 'pageStart']?first} - ${item[bibo + 'pageEnd']?first}<#elseif item[bibo + 'pageStart']??> Page ${item[bibo + 'pageStart']?first}<#elseif item[bibo + 'pageEnd']??> Page ${item[bibo + 'pageEnd']?first}</#if></span>";
                                graphData[${graphCount}].results[graphData[${graphCount}].results.length] = o;
                            </#if>
                        </#list>
                </script>
                <@generateGraphHTML graphCount=graphCount/>
                <#else>
                    <h2>No research outputs available</h2>
                </#if>
            </div><!-- END id="tabs-pub" -->

            <#-- collaborators -->
            <div class="inner" id="tabs-collab">
                <#if resource[relationship + 'collaboratesWith']??>
                   <div id="collaborators">
                       <h2>Collaborators</h2>
                       <#list resource[relationship + 'collaboratesWith'] as collaborator>
                         <p><a href="${collaborator}">${collaborator[rdfs+'label']?first}</a></p>
                       </#list>
                   </div>
                </#if>
            </div> <!-- END id="tabs-collab" -->
        </div> <!-- END class="tabbedcontent" -->
    </div> <!-- END  id="tabs" -->

</div><!-- /content -->
<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
