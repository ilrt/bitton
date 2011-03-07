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
        <ul>
            <li><a href="#tabs-pub">My Research Outputs</a></li>
            <li><a href="#tabs-collab">My Collaborators</a></li>
        </ul>

        <div id="tabs-pub">
            <#if publist??>
                <script type="text/javascript">
                    // global vars
                    var maxPageSize = 5;
                    var currentPage = 0;

                    var results = new Array();


                    <#list publist as item>
                        var o = new Object();
                        o.year = ${item[dc + 'date']?first?date?string("yyyy")};
                        o.label = "<@label resource=item/>";
                        o.citation = "<#if item[dc + 'contributor']??><span class='contributor'><#list item[dc + 'contributor'] as contributor><@label resource=contributor/><#if contributor_has_next>, </#if></#list>.</span> </#if><a class='title' href='<@drillForResult result=item/>'><@label resource=item/></a>. <span class='otherdetails'><#if item[dc + 'date']??>(${item[dc + 'date']?first?date?string("yyyy")}) </#if><#if item[elements + 'publisher']??>${item[elements + 'publisher']?first}</#if><#if item[bibo + 'isbn']??> ${item[bibo + 'isbn']?first}</#if><#if item[bibo + 'volume']??> Vol. ${item[bibo + 'volume']?first}</#if><#if item[dc + 'isPartOf']??> Part of ${item[dc + 'isPartOf']?first['label']}</#if><#if item[bibo + 'pageStart']?? && item[bibo + 'pageEnd']??> Pages ${item[bibo + 'pageStart']?first} - ${item[bibo + 'pageEnd']?first}<#elseif item[bibo + 'pageStart']??> Page ${item[bibo + 'pageStart']?first}<#elseif item[bibo + 'pageEnd']??> Page ${item[bibo + 'pageEnd']?first}</#if></span>";
                        results[results.length] = o;
                    </#list>
                </script>
                <@generateGraphHTML graphCount=graphCount/>
            </#if>
        </div><!-- END id="tabs-pub" -->

        <#-- collaborators -->
        <div id="tabs-collab">
            <#if resource[relationship + 'collaboratesWith']??>
               <div id="collaborators">
                   <h2>Collaborators</h2>
                   <#list resource[relationship + 'collaboratesWith'] as collaborator>
                     <p><a href="${collaborator}">${collaborator[rdfs+'label']?first}</a></p>
                   </#list>
               </div>
            </#if>
        </div><!-- END id="tabs-collab" -->

    </div> <!-- END  id="tabs" -->

</div><!-- /content -->
<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
