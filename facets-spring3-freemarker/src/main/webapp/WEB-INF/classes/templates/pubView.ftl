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
				<dd><@displayPerson person=contributor/></dd>, 
				</#list>
			</dl>

			<dl>

				<#if resource[dc + 'date']??>
				<dt>Year of publication:</dt>
				<dd>
				${resource[dc + 'date']?first}
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
				<dt>Published by:</dt>
				<dd>{name of publisher}</dd>
				<#if resource[bibo + 'isbn']??>
				<dt>ISBN:</dt>
				<dd>${resource[bibo + 'isbn']?first}</dd>
				</#if>
			</dl>

				etc, etc.
				-->


		</div><!-- /pubdetails -->

		<#if resource[dc + 'abstract']??><p>${resource[dc + 'abstract']?first}</p></#if>

		<#if resource[rdfs + 'seeAlso']??>
		<button href="${resource[rdfs + 'seeAlso']?first}">Get full text &gt;</button>
		</#if>

		<#if resource[dc + 'bibliographicCitation']??>
		<div id="citation">
			<h2 class="veryweak">Full citation</h2>
			<cite class="veryweak">${resource[dc + 'bibliographicCitation']?first}</cite>
		</div><!-- /citation -->
		</#if>

		<p><a href="#">Where does this data come from?</a></p>

		<!--
		<table class="debug">
			<#list resource?keys as key>
			<#list resource[key] as value>
			<tr><td>${key}</td><td>${value}</td></tr>
			</#list>
			</#list>
		</table>
		-->

	</div><!-- /col1-2of3 -->
	<div class="col3of3">

		<div class="section">
			<h2>1 impact</h2>
			<ul class="objects">
				<li class="impact"><a href="#">Cited in article in New Scientist</a></li>
			</ul>
			<p class="button"><a class="add" href="#">New impact</a></p>
		</div><!-- /section -->

		<#if resource[dc + 'contributor']??>
		<div class="section">
			<h2>N contributors</h2>
			<ul class="objects">
				<#list resource[dc + 'contributor'] as contributor>
				<li class="person">
				<@displayPerson person=contributor/>
				</li>
				</#list>
			</ul>
		</div><!-- /section -->
		</#if>

		<div class="section">
			<h2>funded by 1 grant</h2>
			<ul class="objects">
				<li class="grant"><a href="#">Title of grant goes here</a></li>
			</ul>
		</div><!-- /section -->


	</div><!-- /col3of3 -->

</div><!-- /content -->

<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
