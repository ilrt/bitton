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
                <#if resource['<-http://vocab.bris.ac.uk/resrev#associatedResearcher']??>
                        <li>Is involved in <b>${resource['<-http://vocab.bris.ac.uk/resrev#associatedResearcher']?size}</b> impacts</li>
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
			<li class="first current"><a href="#tabs-pub">Research Outputs <span class="count"><#if outputlist??>${outputlist.size}<#else>0</#if></span></a></li>
			<li><a href="#tabs-grants">Grants <span class="count"><#if grantlist??>${grantlist.size}<#else>0</#if></span></a></li>
			<li><a href="#tabs-collab">Collaborators <span class="count"><#if resource[relationship + 'collaboratesWith']??>${resource[relationship + 'collaboratesWith']?size}<#else>0</#if></span></a></li>
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
						</#list>
					</ul>

				<#else>
					<h2>No research outputs available</h2>
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
							</li>grants
						</#list>
					</ul>
				<#else>
					<h2>No grants available</h2>
				</#if>
			</div> <!-- END #tabs-grants -->

			<#-- collaborators -->
			<div class="inner" id="tabs-collab">
				<#if resource[relationship + 'collaboratesWith']??>
				   <div id="collaborators">
					   <h2>Collaborators</h2>
					   <#list resource[relationship + 'collaboratesWith'] as collaborator>
						 <p><@displayPerson person=collaborator/></p>
					   </#list>
				   </div>
				<#else>
					<h2>No collaborators</h2>
				</#if>
			</div> <!-- END #tabs-collab -->
		</div> <!-- END .tabbedcontent -->
	</div> <!-- END  #tabs -->

</div><!-- /content -->
<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
