<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

    <div id="content">

        <h1>${resource[foaf + 'name']?first}</h1>

        <#if resource[foaf + 'homepage']??>
            <p><strong>Homepage:</strong> <a href="${resource[foaf + 'homepage']?first}">${resource[foaf + 'homepage']?first}</a></p>
        </#if>

        <#if resource[aiiso + 'part_of']??>
          <p><strong>Part of: </strong><@displayOrg org=resource[aiiso + 'part_of']?first/></p>
        </#if>
               
        <div id="tabs">
            <ul>
                <li><a href="#tabs-pub">Research Outputs</a></li>
                <li><a href="#tabs-grants">Grants</a></li>
                <li><a href="#tabs-members">Members</a></li>
            </ul>

            <div id="tabs-pub">
                <#if recentoutputs??>
                    <script type="text/javascript">
                        // global vars
                        var maxPageSize = 5;
                        var currentPage = 0;

                        var results = new Array();

                        <#list recentoutputs as item>
                            <#if item[dc + 'date']??>
                                var o = new Object();
                                o.year = ${item[dc + 'date']?first?date?string("yyyy")};
                                o.label = "${item[rdfs + 'label']?first?html}";
                                if (o.label == "") o.label = "[Title missing]
                                o.citation = "<#if item[dc + 'contributor']??><span class='contributor'><#list item[dc + 'contributor'] as contributor><@label resource=contributor/><#if contributor_has_next>, </#if></#list>.</span> </#if><a class='title' href='<@drillForResult result=item/>'><@label resource=item/></a>. <span class='otherdetails'><#if item[dc + 'date']??>(${item[dc + 'date']?first?date?string("yyyy")}) </#if><#if item[elements + 'publisher']??>${item[elements + 'publisher']?first}</#if><#if item[bibo + 'isbn']??> ${item[bibo + 'isbn']?first}</#if><#if item[bibo + 'volume']??> Vol. ${item[bibo + 'volume']?first}</#if><#if item[dc + 'isPartOf']??> Part of ${item[dc + 'isPartOf']?first['label']}</#if><#if item[bibo + 'pageStart']?? && item[bibo + 'pageEnd']??> Pages ${item[bibo + 'pageStart']?first} - ${item[bibo + 'pageEnd']?first}<#elseif item[bibo + 'pageStart']??> Page ${item[bibo + 'pageStart']?first}<#elseif item[bibo + 'pageEnd']??> Page ${item[bibo + 'pageEnd']?first}</#if></span>";
                                results[results.length] = o;
                            </#if>
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

            <#-- Grants -->
            <div id="tabs-grants">
                <#if recentgrants??>
                    <div class="recent-grants">
                        <h2>Recent grants</h2>
                        <#list recentgrants as grant>
                            <p><@linkToPageFor item=grant/></p>
                        </#list>
                    </div>
                </#if>
            </div>

            <#-- collaborators -->
            <div id="tabs-members">
                <#if  resource[foaf + 'member']??>
                    <div class="collapsible collapsed">
                        <h2>Members</h2>

                        <ul class="content collapsed">
                            <#if resource[foaf + 'member']??>
                                <#list resource[foaf + 'member'] as member>
                                    <li>
                                         <@displayPerson person=member/>
                                    </li>
                                </#list>
                            </#if>
                        </ul>
                    </div>
                </#if>
            </div><!-- END id="tabs-members" -->

        </div> <!-- END  id="tabs" -->


        <#if view??><p><em><a href="javascript:history.go(-1)">Return to results</a></em></p></#if>

     </div>

<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
