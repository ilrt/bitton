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
		<ul class="tabs">
			<li class="first"><a href="#tabs-pub">Research Outputs <span class="count"><#if recentoutputs??>${recentoutputs.size}<#else>0</#if></span></a></li>
			<li><a href="#tabs-grants">Grants <span class="count"><#if recentgrants??>${recentgrants.size}<#else>0</#if></span></a></li>
			<li><a href="#tabs-members">Members <span class="count"><#if resource[foaf + 'member']??>${resource[foaf + 'member']?size}<#else>0</#if></span></a></li>
		</ul>

		<div class="tabbedcontent">
			<div class="inner" id="tabs-pub">

				<#if recentoutputs??  && 0 < recentoutputs.size>
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

					<#list recentoutputs.collection as item>
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

				<!-- DEVNOTE: Example content to show impact styles:
				-->
				<h2>Example content, to show impact styles:</h2>
				<ul class="results">
					<li class="grant"><a class="title" href="/resrev/item?res=http%3A%2F%2Fresrev.ilrt.bris.ac.uk%2Fthings%2Fgrants%2Fepsrc%2FEP%2FG003599%2F1%23grant">CRack Arrest and Self-Healing in COMPosite Structures (CRASHCOMPS)</a>.  <span class="amount">&pound;594,946</span> <span class="otherdetails"> Ian Bond (2009) The University of Bristol</span> <a href="#" class="show impacts" id="show1">show 3 impacts</a>
					<div class="impacts" id="impacts1">
						<ul class="results">
							<li class="impact"><a class="title" href="#">The name of an impact - we really need some sample data</a> <span class="otherdetails">I'm assuming that the impact title will link off to a separate page with full impact details</span></li>
							<li class="impact"><a class="title" href="#">The name of an impact - we really need some sample data</a> <span class="otherdetails">I'm assuming that the impact title will link off to a separate page with full impact details</span></li>
							<li class="impact"><a class="title" href="#">The name of an impact - we really need some sample data</a> <span class="otherdetails">I'm assuming that the impact title will link off to a separate page with full impact details</span></li>
						</ul>
					</div></li>
					<li class="grant"><a class="title" href="/resrev/item?res=http%3A%2F%2Fresrev.ilrt.bris.ac.uk%2Fthings%2Fgrants%2Fepsrc%2FEP%2FG003599%2F1%23grant">CRack Arrest and Self-Healing in COMPosite Structures (CRASHCOMPS)</a>.  <span class="amount">&pound;594,946</span> <span class="otherdetails"> Ian Bond (2009) The University of Bristol </span> </li>
					<li class="grant"><a class="title" href="/resrev/item?res=http%3A%2F%2Fresrev.ilrt.bris.ac.uk%2Fthings%2Fgrants%2Fepsrc%2FEP%2FG003599%2F1%23grant">CRack Arrest and Self-Healing in COMPosite Structures (CRASHCOMPS)</a>.  <span class="amount">&pound;594,946</span> <span class="otherdetails"> Ian Bond (2009) The University of Bristol </span> </li>
				</ul>
				<script>
					$(document).ready(function() {

						$('#impacts1').hide();

						$('#show1').click(function() {
							$('#show1').toggleClass('open').blur();

							/* Switch the wording: */
							$stem = $('#show1').text().substr(5);
							if($('#show1').hasClass('open')) {
								$('#show1').text('hide ' + $stem);
								} else {
								$('#show1').text('show ' + $stem);
							}

							$('#impacts1').toggle();
							return false;
						});
					});

				</script>
				<!-- /Example content -->


			</div><!-- END id="tabs-pub" -->

			<#-- Grants -->
			<div class="inner" id="tabs-grants">
				<#if recentgrants?? && 0 < recentgrants.size>
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

					<#list recentgrants.collection as item>
					<#if item[proj + 'startDate']??>
					var o = new Object();
					o.year = ${item[proj + 'startDate']?first?date?string("yyyy")};
					o.label = "<@label resource=item/>";
					o.citation = "<a class='title' href='<@drillForResult result=item/>'><@label resource=item/></a>. <#if item[proj + 'value']??><span class='amount'>&pound;${item[proj + 'value']?first}</span></#if> <span class='otherdetails'><#if item[proj + 'hasPrincipalInvestigator']??><#list item[proj + 'hasPrincipalInvestigator'] as pi><#if pi[rdfs + 'label']??>${pi[rdfs + 'label']?first}</#if></#list></#if><#if item[proj + 'startDate']??> (${item[proj + 'startDate']?first?date?string('yyyy')})</#if><#if item[proj + 'hostedBy']??> <@label resource=item[proj + 'hostedBy']?first/></#if></span>";
					graphData[${graphCount}].results[graphData[${graphCount}].results.length] = o;
					</#if>
					</#list>
				</script>
				<@generateGraphHTML graphCount=graphCount/>
				<#else>
				<h2>No grants available</h2>
				</#if>
			</div>

			<#-- collaborators -->
			<div class="inner" id="tabs-members">
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
		</div><!-- /tabbedcontent -->
	</div> <!-- END  id="tabs" -->

	<#if view??><p><em><a href="javascript:history.go(-1)">Return to results</a></em></p></#if>

</div>

<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
