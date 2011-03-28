<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<!-- main content -->
<div id="content">
    <div class="col1-2of3">
        <#if resource[rdfs+'label']??>
            <h1>Profile for ${resource[rdfs+'label']?first}</h1>
        <#elseif resource[foaf+'name']??>
            <h1>Profile for ${resource[foaf+'name']?first}</h1>
        </#if>

        <#if resource['<-' + foaf + 'member']??>
            <#if resource['<-' + foaf + 'member']?size = 1>
               <div class="departments">
                   <dl>
                       <dt>Department</dt>
                       <#list resource['<-' + foaf + 'member'] as member>
                            <#if member[rdfs + 'label']?? && member[rdfs + 'label']?first != invalidUrl>
                                <dd><@displayOrg org=member/></dd>
                            </#if>
                       </#list>
                   </dl>
               </div>
            <#else>
               <div class="departments">
                   <dl>
                       <dt>My Departments</dt>
                       <dd>
                           <#list resource['<-' + foaf + 'member'] as member>
                                <#if member[rdfs + 'label']?? && member[rdfs + 'label']?first != invalidUrl>
                                    <dd><@displayOrg org=member/></dd>,
                                </#if>
                           </#list>
                        </dd>
                    </dl>
               </div>
           </#if>
        </#if>


        <div id="tabs">
            <ul class="tabs">
                <li class="first current"><a href="#tabs-pub">Research Outputs <span class="count"><#if outputlist??>${outputlist.size}<#else>0</#if></span></a></li>
                <li><a href="#tabs-grants">Grants <span class="count"><#if grantlist??>${grantlist.size}<#else>0</#if></span></a></li>
                <li><a href="#tabs-impacts">Impacts <span class="count"><#if impactlist??>${impactlist.size}<#else>0</#if></span></a></li>
            </ul>

            <div class="tabbedcontent">
                <div class="inner" id="tabs-pub">
                    <#if outputlist??  && 0 < outputlist.size>

                        <#if graphCount??><#assign graphCount=graphCount+1/><#else><#assign graphCount=0/></#if>
                        <@generateGraphHTML graphCount=graphCount/>

                        <script type="text/javascript">
                            graphData[${graphCount}].type="publications";
                        </script>

                        <#-- Generate listing HTML output -->
                        <ul style="display:none" id="data_${graphCount}">
                            <#list outputlist.collection as item>
                                <#if !item[dc + 'date']??>
                                    <li class='pub' date="${item[dc + 'date']?first?date?string("yyyy")}">
                                        <a class='title' href='<@drillForResult result=item/>'><@label resource=item/></a>.
                                        <span class='otherdetails'><#if item[dc + 'contributor']??><span class='contributor'><#list item[dc + 'contributor'] as contributor><@label resource=contributor/><#if contributor_has_next>, </#if></#list>.</span> </#if> <#if item[dc + 'date']??>(${item[dc + 'date']?first?date?string("yyyy")}) </#if><#if item[elements + 'publisher']??>${item[elements + 'publisher']?first}</#if><#if item[bibo + 'isbn']??> ${item[bibo + 'isbn']?first}</#if><#if item[bibo + 'volume']??> Vol. ${item[bibo + 'volume']?first}</#if><#if item[dc + 'isPartOf']??> Part of ${item[dc + 'isPartOf']?first['label']}</#if><#if item[bibo + 'pageStart']?? && item[bibo + 'pageEnd']??> Pages ${item[bibo + 'pageStart']?first} - ${item[bibo + 'pageEnd']?first}<#elseif item[bibo + 'pageStart']??> Page ${item[bibo + 'pageStart']?first}<#elseif item[bibo + 'pageEnd']??> Page ${item[bibo + 'pageEnd']?first}</#if></span>
                                        <#if item['<-'+resrev+'associatedPublication']??>
                                            <a href="" class="show_impacts">show ${item['<-'+resrev+'associatedPublication']?size} impact<#if item['<-'+resrev+'associatedPublication']?size != 1>s</#if></a>
                                            <div class="impacts hide">
                                                <ul class="results">
                                                    <#list item['<-'+resrev+'associatedPublication'] as impact>
                                                        <li class="impact"><@displayImpact impact=impact/> <span class="otherdetails"></span></li>
                                                    </#list>
                                                </ul>
                                            </div>
                                        </#if>
                                    </li>
                                </#if>
                            </#list>
                        </ul>

                    <#else>
                        <h2>No research outputs available</h2>
                        <p>&nbsp;</p>
                    </#if>
                </div><!-- END #tabs-pub -->

                <#-- Grants -->
                <div class="inner" id="tabs-grants">
                    <#if grantlist?? && 0 < grantlist.size>
                        <#if graphCount??><#assign graphCount=graphCount+1/><#else><#assign graphCount=0/></#if>
                        <@generateGraphHTML graphCount=graphCount/>

                        <script type="text/javascript">
                            graphData[${graphCount}].type="grants";
                        </script>

                        <#-- Generate listing HTML output -->
                        <ul style="display:none" id="data_${graphCount}">
                            <#list grantlist.collection as item>
                                <li class='grant' date="${item[proj + 'startDate']?first?date?string("yyyy")}">
                                    <a class='title' href='<@drillForResult result=item/>'><@label resource=item/></a>.
                                    <#if item[proj + 'value']??><span class='amount'>&pound;${item[proj + 'value']?first}</span> </#if>
                                    <span class='otherdetails'><#if item[proj + 'hasPrincipalInvestigator']??><#list item[proj + 'hasPrincipalInvestigator'] as pi><#if pi[rdfs + 'label']??>${pi[rdfs + 'label']?first}</#if></#list></#if><#if item[proj + 'startDate']??> (${item[proj + 'startDate']?first?date?string('yyyy')})</#if><#if item[proj + 'hostedBy']??> <@label resource=item[proj + 'hostedBy']?first/></#if></span>
                                    <#if item['<-'+resrev+'associatedGrant']??>
                                        <a href="" class="show_impacts">show ${item['<-'+resrev+'associatedGrant']?size} impact<#if item['<-'+resrev+'associatedGrant']?size != 1>s</#if></a>
                                        <div class="impacts hide">
                                            <ul class="results">
                                                <#list item['<-'+resrev+'associatedGrant'] as impact>
                                                    <li class="impact"><@displayImpact impact=impact/> <span class="otherdetails"></span></li>
                                                </#list>
                                            </ul>
                                        </div>
                                    </#if>
                                </li>grants
                            </#list>
                        </ul>
                    <#else>
                        <h2>No grants available</h2>
                        <p>&nbsp;</p>
                    </#if>
                </div> <!-- END #tabs-grants -->

                <#-- Impacts -->
                <div class="inner" id="tabs-impacts">

                    <#if impactlist?? && 0 < impactlist.size>

                        <#if graphCount??><#assign graphCount=graphCount+1/><#else><#assign graphCount=0/></#if>
                        <@generateGraphHTML graphCount=graphCount/>

                        <script type="text/javascript">
                            graphData[${graphCount}].type="grants";
                        </script>

                        <#-- Generate listing HTML output -->
                        <ul style="display:none" id="data_${graphCount}">
                            <#list impactlist.collection as item>
                                <#if item[annot + 'created']??>
                                    <li class='pub' date="${item[annot + 'created']?first?date?string("yyyy")}">
                                        <a class='title' href='<@drillForResult result=item/>'><@label resource=item/></a>.
                                        <span class='otherdetails'>
                                            <#if item[resrev + 'associatedResearcherName']??>
                                            <span class='contributor'>
                                                <#list item[resrev + 'associatedResearcherName'] as contributor>
                                                    ${contributor}<#if contributor_has_next>, </#if>
                                                </#list>.
                                            </span>
                                            </#if>
                                            <#if item[annot + 'created']??>
                                                (${item[annot + 'created']?first?date?string("dd-mm-yyyy")}) </#if>
                                            <#if item[dc + 'description']??>${item[dc + 'description']?first}</#if>
                                        </span>
                                    </li>
                                </#if>
                            </#list>
                        </ul>


                    <#else>
                        <h2>No impacts available</h2>
                        <p>&nbsp;</p>
                    </#if>
                </div> <!-- END #tabs-impacts -->

            </div> <!-- END .tabbedcontent -->
        </div> <!-- END  #tabs -->

    </div> <!-- END .col1-2of3 -->

    <div class="col3of3 sidebar">
        <div class="section">
            <h2>Details</h2>
            <dl><dt>Name:</dt><dd>${resource[foaf+'name']?first}</dd></dl>
            <#if profileview??>
                <dl><dt>UoB username:</dt><dd>${resource[foaf+'nick']?first}</dd></dl>
            </#if>

            <#if resource[foaf+'mbox']??>
                <dl><dt>Email:</dt><dd><a href="${resource[foaf+'mbox']?first}">${resource[foaf+'mbox']?first?substring(7)}</a></dd></dl>
            </#if>

            <#if resource[foaf+'homepage']??>
                <dl><dt>See also:</dt><dd><a href="${resource[foaf+'homepage']?first}">merb user page</a></dd></dl>
            </#if>
        </div><!-- END .section -->

        <#-- Members -->
        <div class="section">
            <#if resource[relationship + 'collaboratesWith']??>

                <h2>${resource[relationship + 'collaboratesWith']?size} collaborators</h2>

                <#assign count=0/>
                <ul class="objects">
                    <#list resource[relationship + 'collaboratesWith'] as member>
                        <li class="person"><@displayPerson person=member/></li>
                    </#list>
                </ul>
            <#else>
                <h2>No collaborators</h2>
            </#if>
        </div><!-- END .section -->
    </div><!-- END .col3of3 -->

</div><!-- /content -->
<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
