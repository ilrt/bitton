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
                        o.label = "${item[rdfs + 'label']?first?html}";
                        if (o.label == "") o.label = "[Title missing]";
                        o.citation = "<#if item[dc + 'contributor']??><span class='contributor'><#list item[dc + 'contributor'] as contributor><@label resource=contributor/><#if contributor_has_next>, </#if></#list>.</span> </#if><a class='title' href='<@drillForResult result=item/>'><@label resource=item/></a>. <span class='otherdetails'><#if item[dc + 'date']??>(${item[dc + 'date']?first?date?string("yyyy")}) </#if><#if item[elements + 'publisher']??>${item[elements + 'publisher']?first}</#if><#if item[bibo + 'isbn']??> ${item[bibo + 'isbn']?first}</#if><#if item[bibo + 'volume']??> Vol. ${item[bibo + 'volume']?first}</#if><#if item[dc + 'isPartOf']??> Part of ${item[dc + 'isPartOf']?first['label']}</#if><#if item[bibo + 'pageStart']?? && item[bibo + 'pageEnd']??> Pages ${item[bibo + 'pageStart']?first} - ${item[bibo + 'pageEnd']?first}<#elseif item[bibo + 'pageStart']??> Page ${item[bibo + 'pageStart']?first}<#elseif item[bibo + 'pageEnd']??> Page ${item[bibo + 'pageEnd']?first}</#if></span>";
                        results[results.length] = o;
                    </#list>
                </script>
                <div class="combiGraphAndList">
                    <div class="fig">
                        <script type="text/javascript+protovis">
                            /* Sizing and scales. */
                            var w = $('.combiGraphAndList .fig').width(),
                            h = $('.combiGraphAndList .fig').height(),
                            x = pv.Scale.ordinal(labels).splitBanded(0, w),
                            y = pv.Scale.linear(data).range(0, h);

                            /* The root panel. */
                            var vis = new pv.Panel()
                                .width(w)
                                .height(h)
                                .bottom(5)
                                .left(0)
                                .right(0)
                                .top(5);

                            /* X-axis ticks. */
                            vis.add(pv.Rule)
                                .data(labels)
                                .bottom(-10)
                                .height(15)
                                .left(function(d) x(d))
                                .strokeStyle("#000");

                            /* The bars. */
                            var bar = vis.add(pv.Bar)
                                .data(data)
                                .height(function(d) y(d))
                                .width(function() vis.width()/labels.length)
                                .left(function(d) x(this.index))
                                .fillStyle("#AAA")
                                .bottom(0);

                            $('.combiGraphAndList .fig').resize(function() {
                              var w = $('.combiGraphAndList .fig').width();
                              var h = $('.combiGraphAndList .fig').height();
                              vis.width(w).height(h);
                              x.domain(labels).splitBanded(0, w);
                              y.range(0, h);
                              vis.render();
                            });

                            $('.combiGraphAndList .fig').resize();
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
                      <div class="body"
                           xmlns:dc="http://purl.org/dc/elements/1.1/"
                           xmlns:dcterms="http://purl.org/dc/terms/"
                           about="http://www.example.com/books/wikinomics">
                           <!--  
                             <p>
                               <span class='title' property='dc:title'>[title]</span>
                               <span class='year' property='dc:date'>([year])</span>
                               <span class='citation' property='dcterms:bibliographicCitation'>[citation]</span>
                             </p>
                           -->
                      </div><!-- END class="body" -->

                      <div class="controls">
                        <span class="prev">Previous</span>
                        <span class="resultstotal"></span>
                        <span class="next">Next</span>
                      </div>

                      <select class="ordering"></select>
                    </div><!-- END class="results" -->

                </div><!-- END class="combiGraphAndList" -->
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
