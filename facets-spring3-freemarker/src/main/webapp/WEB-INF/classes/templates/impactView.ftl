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

	</div><!-- END .col1-2of3 -->

</div><!-- /content -->

<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
