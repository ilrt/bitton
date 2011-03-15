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
			<li class="first current"><a href="#tabs-pub">Research Outputs <span class="count"><#if outputlist??>${outputlist.size}<#else>0</#if></span></a></li>
			<li><a href="#tabs-grants">Grants <span class="count"><#if grantlist??>${grantlist.size}<#else>0</#if></span></a></li>
			<li><a href="#tabs-members">Members <span class="count"><#if resource[foaf + 'member']??>${resource[foaf + 'member']?size}<#else>0</#if></span></a></li>
		</ul>

		<div class="tabbedcontent">
			<div class="inner" id="tabs-pub">

				<#if outputlist??  && 0 < outputlist.size>

					<#if graphCount??><#assign graphCount=graphCount+1/><#else><#assign graphCount=0/></#if>
					<@generateGraphHTML graphCount=graphCount/>

					<script>
						<#-- vars -->
						graphData[${graphCount}].maxPageSize = 5;
						graphData[${graphCount}].currentPage = 0;

						<!-- set specific sort options for this dataset -->
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
						graphData[${graphCount}].results = new Array();
						obj.title = "Date (desc)";
						obj.fun = sortResultsOnDateThenLabelDesc;
						sortOptions['sortResultsOnDateThenLabelDesc'] = obj;
						graphData[${graphCount}].sortOptions = sortOptions;
					</script>

					<#-- Generate listing HTML output -->
					<ul style="display:none" id="data_${graphCount}">
						<#list outputlist.collection as item>
							<#if item[dc + 'date']??>
								<li class='pub' date="${item[dc + 'date']?first?date?string("yyyy")}">
									<a class='title' href='<@drillForResult result=item/>'><@label resource=item/></a>.
									<span class='otherdetails'><#if item[dc + 'contributor']??><span class='contributor'><#list item[dc + 'contributor'] as contributor><@label resource=contributor/><#if contributor_has_next>, </#if></#list>.</span> </#if> <#if item[dc + 'date']??>(${item[dc + 'date']?first?date?string("yyyy")}) </#if><#if item[elements + 'publisher']??>${item[elements + 'publisher']?first}</#if><#if item[bibo + 'isbn']??> ${item[bibo + 'isbn']?first}</#if><#if item[bibo + 'volume']??> Vol. ${item[bibo + 'volume']?first}</#if><#if item[dc + 'isPartOf']??> Part of ${item[dc + 'isPartOf']?first['label']}</#if><#if item[bibo + 'pageStart']?? && item[bibo + 'pageEnd']??> Pages ${item[bibo + 'pageStart']?first} - ${item[bibo + 'pageEnd']?first}<#elseif item[bibo + 'pageStart']??> Page ${item[bibo + 'pageStart']?first}<#elseif item[bibo + 'pageEnd']??> Page ${item[bibo + 'pageEnd']?first}</#if></span>
								</li>
							</#if>
						</#list>
					</ul>

				<#else>
						<h2>No research outputs available</h2>
				</#if>

			</div><!-- END id="tabs-pub" -->

			<#-- Grants -->
			<div class="inner" id="tabs-grants">
				<#if grantlist?? && 0 < grantlist.size>

					<#if graphCount??><#assign graphCount=graphCount+1/><#else><#assign graphCount=0/></#if>
					<@generateGraphHTML graphCount=graphCount/>

					<script>
						<#-- vars -->
						graphData[${graphCount}].maxPageSize = 5;
						graphData[${graphCount}].currentPage = 0;

						<!-- set specific sort options for this dataset -->
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
					</script>

					<#-- Generate listing HTML output -->
					<ul style="display:none" id="data_${graphCount}">
						<#list grantlist.collection as item>
							<li class='grant' date="${item[proj + 'startDate']?first?date?string("yyyy")}">
								<a class='title' href='<@drillForResult result=item/>'><@label resource=item/></a>.
								<#if item[proj + 'value']??><span class='amount'>&pound;${item[proj + 'value']?first}</span> </#if>
								<span class='otherdetails'><#if item[proj + 'hasPrincipalInvestigator']??><#list item[proj + 'hasPrincipalInvestigator'] as pi><#if pi[rdfs + 'label']??>${pi[rdfs + 'label']?first}</#if></#list></#if><#if item[proj + 'startDate']??> (${item[proj + 'startDate']?first?date?string('yyyy')})</#if><#if item[proj + 'hostedBy']??> <@label resource=item[proj + 'hostedBy']?first/></#if></span>
							</li>
						</#list>
					</ul>

				<#else>
									<h2>No grants available</h2>
				</#if>
			</div> <!-- END id="tabs-grants" -->

			<#-- Members -->
			<div class="inner" id="tabs-members">
				<#if  resource[foaf + 'member']??>
					<div class="collapsible collapsed">
						<h2>Members</h2>

						<ul class="content collapsed">
							<#if resource[foaf + 'member']??>
								<#list resource[foaf + 'member'] as member>
									<li><@displayPerson person=member/></li>
								</#list>
							</#if>
						</ul>
					</div>
				<#else>
					<h2>No members</h2>
				</#if>
			</div><!-- END id="tabs-members" -->
		</div><!-- /tabbedcontent -->
	</div> <!-- END  id="tabs" -->

	<#if view??><p><em><a href="javascript:history.go(-1)">Return to results</a></em></p></#if>

</div>

<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
