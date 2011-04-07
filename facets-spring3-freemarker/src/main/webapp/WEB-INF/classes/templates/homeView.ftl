<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<!-- main content -->
<div id="content">

	<div class="col1of2">
		<div class="panel" id="mastercategories">
			<h1>Explore</h1>

			<div class="panelcontent">

				<div class="category" id="homepubs">
					<h2><a href="pubs">Research Outputs</a> <span class="count">${outputTotal}</span></h2>
					<ul id="home-publist">
						<#list outputSummary as output>
							<#if output['type']??>
								<li><a href="pubs?pubtype=U${output['type']?url('utf-8')}%23${output['label']}">${output['label']}</a> <span class="count">${output['scount']}</span></li>
							</#if>
						</#list>
					</ul>
				</div><!-- /category -->

				<div class="category" id="homepeople">
				<h2><a href="people">People</a> <span class="count">${peopleCount}</span></h2>
				<form action="item" method="get">
					<input id="people-complete" type="text" placeholder="Start typing a name" >
					<input id="people-complete-target" type="hidden" name="res">
					<input type="submit" value="go">
				</form>
				</div><!-- /category -->

				<div class="category" id="homegrants">
				<h2><a href="grants">Grants &amp; funding bodies</a> <span class="count">${grantTotal}</span></h2>
				<ul>
					<#list grantSummary as grant>
						<#if grant['s']??>
							<li><a href="pubs?funder=U${grant['s']?url('utf-8')}%23${grant['label']}">${grant['label']}</a> <span class="count">${grant['gcount']}</span></li>
						</#if>
					</#list>
				</ul>
				</div><!-- /category -->

				<div class="category" id="homedepts">
				<h2><a href="organisations">Departments &amp; groups</a> <span class="count">${deptCount}</span></h2>
				<form action="item" method="get">
					<input id="dept-complete" type="text" placeholder="Start typing a dept" >
					<input id="dept-complete-target" type="hidden" name="res">
					<input type="submit" value="go">
				</form>
				</div><!-- /category -->

			</div><!-- /panelcontent -->
		</div><!-- /panel -->
	</div><!-- /col-->

	<div class="col2of2 sidebar">
		<div class="section">
			<h2>What is Research Revealed?</h2>

			<p>Research Revealed provides an integrated view of research-related <a href="page?name=datasources">data sources</a> for the University of Bristol.  It allows you to:</p>

			<ul>
				<li>View research outputs, grants and impacts for indviduals and departments within the University</li>
				<li>Explore and filter research data across the whole univeristy according to specific criteria</li>
			</ul>

			<p>Research Revealed is funded by <link>JISC</link> and developed bu the <link>Web Futures team at ILRT</link>.  For more information about the project, read the <a href="http://researchrevealed.ilrt.bris.ac.uk/">project blog</a>.</p>

		</div><!-- /section -->
		<div class="section">
			<h2>Latest impacts</h2>
				<ul class="objects">
                    <#list latestImpacts as item>
    					<li class="impact"><a href="item?res=U${item['uri']?url('utf-8')}">${item['title']}</a> ${item['date']?datetime("yyyy-MM-dd")?string("dd MMM yyyy")}</li>
                    </#list>
				</ul>

		</div><!-- /section -->
	</div><!-- /col1of2 -->




</div><!-- /content -->

<#include "includes/address-footer.ftl"/>
<#include "includes/footer.ftl"/>
