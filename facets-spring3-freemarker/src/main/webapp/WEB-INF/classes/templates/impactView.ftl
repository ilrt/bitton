<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<div id="content" class="impact">
	<#if view??><div id="topinfo"><a class="back" href="javascript:history.go(-1)">Back to search results</a></div></#if>

	<div class="col1-3of4">

	<div class="inner">

		<h1>${resource[dc + 'title']?first}</h1>

		<div id="impactdetails">
			<dl>
				<dt>Description:</dt>
				<dd>${resource[dc + 'description']?first}</dd>
			</dl>

            <#if resource[resrev + 'associatedPublicationTitle']?? && 0 < resource[resrev + 'associatedPublicationTitle']?size>
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
            </#if>
            <#if resource[resrev + 'associatedGrantName']?? && 0 < resource[resrev + 'associatedGrantName']?size>
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
            </#if>
            <#if resource[resrev + 'associatedResearcherName']?? && 0 < resource[resrev + 'associatedResearcherName']?size>
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
            </#if>

		</div><!-- END #impactdetails -->


</div><!-- /inner -->
</div><!-- /col -->

	<div class="col4of4 sidebar">
		<div class="section">
                <p class="button"><a class="edit" href="#">Edit impact</a></p>

		<p class="flag"><a href="#">Flag as high priority</a></p>

	</div><!-- /section -->
		<div class="help"><a href="page?name=datasources">Where does this data come from?</a></div>

	</div><!-- /col4of4 -->

</div><!-- /content -->

<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
