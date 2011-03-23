<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<!-- main content -->
<div id="content" class="pub">
	<div class="col1-2of3">

		<#if view??><a class="back" href="javascript:history.go(-1)">Back to search results</a></#if>

		<h1>${resource[dc + 'title']?first}</h1>

		<div id="pubdetails">
			<dl>
				<dt>Output type:</dt>
				<dd>
				<#list resource[rdf + 'type'] as type>
				<#if type?contains(bibo?string)>${type?substring(bibo?length)}</#if>
				</#list>
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
			<dl>
				<dt>Published in:</dt>
				<dd>{name of parent publication}<#if resource[bibo + 'volume']??>, vol ${resource[bibo + 'volume']?first}</#if><#if resource[bibo + 'pageStart']?? && resource[bibo + 'pageEnd']??>, pp. ${resource[bibo + 'pageStart']?first} - ${resource[bibo + 'pageEnd']?first}</#if>

				</dd>
			</dl>

				<!-- else if it's a book:
			<dl>
				<dt>Published in:</dt>
				<dd>{name of publisher}</dd>
				<#if resource[bibo + 'isbn']??>
				<dt>ISBN:</dt>
				<dd>${resource[bibo + 'isbn']?first}</dd>
				</#if>
			</dl>

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

		<div class="help"><a href="#">Where does this data come from?</a></div>

	</div><!-- /col1-2of3 -->
	<div class="col3of3 sidebar">

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

	</div><!-- /col3of3 -->

</div><!-- /content -->

<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
