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
						<li><a href="pubs?pubtype=U${output['type']?url('utf-8')}%23${output['label']}">${output['label']}</a> <span class="count">${output['scount']}</span></li>
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
					<li><a href="pubs?funder=U${grant['s']?url('utf-8')}%23${grant['label']}">${grant['label']}</a> <span class="count">${grant['gcount']}</span></li>
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

	<div class="col2of2">
		<div class="panel">
			<h2>About Research Revealed</h2>
			<div class="panelcontent">
				<p>Research Revealed is a <a href="http://www.jisc.ac.uk">JISC</a>-funded project to demonstrate a fine-grained, access controlled, view layer application for research, built over a content integration repository layer. Nobody knows what this really means, but it enabled us to buy a lot of laptops.</p>

			</div><!-- /panelcontent -->
		</div><!-- /panel -->
	</div><!-- /col1of2 -->




</div><!-- /content -->

<#include "includes/address-footer.ftl"/>
<#include "includes/footer.ftl"/>
