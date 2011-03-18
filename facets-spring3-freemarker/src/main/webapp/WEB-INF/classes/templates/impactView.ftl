<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<!-- main content -->
<div id="content" class="impact">
	<div class="col1-2of3">

		<a class="back" href="javascript:history.go(-1)">Back</a>

		<h1>${resource[dc + 'title']?first}</h1>

		<div id="impactdetails">
			<dl>
				<dt>Description:</dt>
				<dd>${resource[dc + 'description']?first}</dd>
			</dl>

			<dl>
				<dt>Publications:</dt>
				<dd>
					<ul>
						<#list resource[resrev + 'associatedPublicationTitle'] as item>
							<li>${item}</li>
						</#list>
					</ul>
				</dd>
			</dl>
			<dl>
				<dt>Grants:</dt>
				<dd>
					<ul>
						<#list resource[resrev + 'associatedGrantName'] as item>
							<li>${item}</li>
						</#list>
					</ul>
				</dd>
			</dl>
			<dl>
				<dt>Researchers involved:</dt>
				<dd>
					<ul>
						<#list resource[resrev + 'associatedResearcherName'] as item>
							<li>${item}</li>
						</#list>
					</ul>
				</dd>
			</dl>


		</div><!-- END #impactdetails -->

	</div><!-- END .col1-2of3 -->

</div><!-- /content -->

<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
