<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<!-- main content -->
<div id="content" class="pub">
		<#if view??><div id="topinfo"><a class="back" href="javascript:history.go(-1)">Back to search results</a></div></#if>
	<div class="col1-3of4">


		<div class="inner">
		<h1>${resource[dc + 'title']?first}</h1>

		<div id="pubdetails">
			<dl>
				<dt>Output type:</dt>
				<dd>
				<#list resource[rdf + 'type'] as type>
                    
                    <#if type?contains(bibo?string)>
                        <#assign pubType=type?substring(bibo?length)/>
                        ${pubType}
                    </#if>
				</#list>
<#if !pubType??>
				<#list resource[dc + 'type'] as type>
                    
                    <#if type?contains(resrev?string)>
                        <#assign pubType=type?substring(resrev?length)/>
                        ${pubType}
                    </#if>
				</#list>
</#if>
				</dd>
			</dl>

			<dl>
				<dt>Contributors:</dt>
				<#list resource[dc + 'contributor'] as contributor>
				<dd><@displayPerson person=contributor/></dd><#if contributor_has_next>,</#if>
				</#list>
			</dl>

			<dl>

				<#if resource[dc + 'date']??>
				<dt>Year of publication:</dt>
				<dd>
				${resource[dc + 'date']?first?date?string("yyyy")}
				</dd>
				</#if>
			</dl>

			<!-- if it's a journal article or simiar: -->
            <#if pubType="Article" || pubType="Conference_Contribution" || pubType="Chapter">
                <dl>
                    <dt>Published in:</dt>
                    <dd><#if resource[elements + 'publisher']??>${resource[elements + 'publisher']?first}<#elseif resource[dc + 'isPartOf']??>${journalName}</#if><#if resource[bibo + 'volume']??>, vol ${resource[bibo + 'volume']?first}</#if><#if resource[bibo + 'pageStart']?? && resource[bibo + 'pageEnd']??>, pp. ${resource[bibo + 'pageStart']?first} - ${resource[bibo + 'pageEnd']?first}</#if><#if resource[bibo + 'isbn']??>. ISBN: ${resource[bibo + 'isbn']?first}</#if>
                    </dd>
                </dl>
            <#elseif pubType="Book">
                <#if resource[elements + 'publisher']??>
                    <dl>
                        <dt>Published in:</dt>
                        <dd>${resource[elements + 'publisher']?first}<#if resource[bibo + 'isbn']??>, ISBN: ${resource[bibo + 'isbn']?first}</#if></dd>
                    </dl>
                </#if>
            <#elseif pubType="Report">
                <#if resource[bibo + 'volume']??>
                    <dl>
                        <dt>Volume:</dt>
                        <dd>${resource[bibo + 'volume']?first}<#if resource[bibo + 'isbn']??>, ISBN: ${resource[bibo + 'isbn']?first}</#if></dd>
                    </dl>
                </#if>
            <#else>
                <#-- print out any other relevant details we might have -->
                <#if resource[elements + 'publisher']??>
                    <dl>
                        <dt>Published in:</dt>
                        <dd>${resource[elements + 'publisher']?first}</dd>
                    </dl>
                </#if>
                <#if resource[bibo + 'isbn']??>
                    <dl>
                        <dt>ISBN:</dt>
                        <dd>${resource[bibo + 'isbn']?first}</dd>
                    </dl>
                </#if>
            </#if>
<#--
				etc, etc.


				The idea is that you'd get something like this for a journal article:

				Published in: Journal of Business vol 63, no.1: pp. 91-98

				For a book:

				Published by: Universe Books, ISBN: 2938179512

				-->


		</div><!-- /pubdetails -->

		<#if resource[dc + 'abstract']??><p>${resource[dc + 'abstract']?first}</p></#if>

		<#if resource[rdfs + 'seeAlso']??>
		<button class="bigarrow" href="${resource[rdfs + 'seeAlso']?first}">Get full text</button>
		</#if>

		<#if resource[dc + 'bibliographicCitation']??>
		<div id="citation">
			<h2 class="veryweak">Full citation</h2>
			<cite class="veryweak">${resource[dc + 'bibliographicCitation']?first}</cite>
		</div><!-- /citation -->
		</#if>


	</div><!-- /inner -->
	</div><!-- /col1-3of4 -->
	<div class="col4of4 sidebar">

		<#if resource['<-'+resrev+'associatedPublication']??>
			<div class="section">
				<h2>${resource['<-'+resrev+'associatedPublication']?size} impact<#if resource['<-'+resrev+'associatedPublication']?size != 1>s</#if></h2>
				<ul class="objects">
					<#list resource['<-'+resrev+'associatedPublication'] as impact>
						<li class="impact"><@displayImpact impact=impact/> <span class="otherdetails"></span></li>
					</#list>
				</ul>
				<p class="button"><a class="add" href="#">Add impact</a></p>
			</div><!-- /section -->
        <#else>
            <div class="section">
                <h2>No impacts</h2>
		<p class="small">No impacts currently logged, but you can add one:</p>
                <p class="button"><a class="add" href="#">Add impact</a></p>
            </div>
		</#if>
		<#if resource[dc + 'contributor']??>
		<div class="section">
			<h2>${resource[dc + 'contributor']?size} contributors</h2>
			<ul class="objects">
				<#list resource[dc + 'contributor'] as contributor>
					<li class="person">
						<@displayPerson person=contributor/>
					</li>
				</#list>
			</ul>
		</div><!-- /section -->
		</#if>

		<div class="help"><a href="page?name=datasources">Where does this data come from?</a></div>

	</div><!-- /col4of4 -->

</div><!-- /content -->

<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
