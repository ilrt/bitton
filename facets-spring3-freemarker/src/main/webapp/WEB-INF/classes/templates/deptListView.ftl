<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<!-- main content -->
<div id="content" class="departmentListing">

	<div class="col1-2of3">

		<#assign grouping = 4 />
		<#assign currLet = 'A'/>
		<#assign i = 0/>

		<h1>Departments and groups</h1>

		<!-- show the search box -->
		<form action="item" method="get">
			<select class="autocomplete" name="res" id="res">
				<option value="">Start typing a name to filter these results</option>
				<#list departmentList as dept><#if dept['dept']??><option value="${dept['dept']}">${dept['label']}</option></#if></#list>
			</select>
			<input type="submit" value="Go"/>
		</form>

		<!-- show the jump to links -->
		<@printQuickLinks/>

		<!-- print the first ABC section header -->
		<@printAlphabetHeader start=i size=grouping-1/>


		<ul class="results">
			<#list 0..25 as let>
			<#list departmentList as dept>
				<#if dept['dept']?? && alphabet[let] == dept['label']?substring(0, 1)>
					<li class="group">
						<a class="title" href="item?res=${dept['dept']?url('utf-8')}">${dept['label']}</a>
						<span class="otherdetails">
							<#if dept['peoplecount']??>${dept['peoplecount']} <#if dept['peoplecount'] = 1>person<#else>people</#if><#else>0 people</#if>, 
							<#if dept['pubcount']??>${dept['pubcount']} publication <#if dept['pubcount'] != 1>s</#if><#else>0 publications</#if>,
							<#if dept['grantcount']??>${dept['grantcount']} grant<#if dept['grantcount'] != 1>s</#if><#else>0 grants</#if>
						</span>
					</li>
				</#if>
			</#list>

			<#assign i=i+1/>
			<#if i % grouping == 0 >
		</ul>
		<!-- print the header for the next grouping of entries -->
		<@printAlphabetHeader start=i size=grouping-1/>
		<ul class="results">
			</#if>
			</#list>
		</ul>

	</div><!-- /col -->

	<div class="col3of3 sidebar">
		<div class="section">
			<!-- show the number of results -->
			<#assign count=0/>
			<#list departmentList as dept><#assign count=count+1/></#list>
			<p class="top">There are <strong>${count} departments and groups</strong> recorded in the system. These include formal organisational units, such as faculties or schools as well as specific research groupings (e.g. for REF).</p>

		</div><!-- /section -->
		<div class="section">
			<h2>Popular</h2>
			<ul class="objects">
                                <li class="group"><a href="item?res=http%3A%2F%2Fresrev.ilrt.bris.ac.uk%2Fresearch-revealed-hub%2Forganisation_units%2FCHEM%23org">School of Chemistry</a></li>
				<li class="group"><a href="item?res=http%3A%2F%2Fresrev.ilrt.bris.ac.uk%2Fresearch-revealed-hub%2Forganisation_units%2FGEOG%23org">School of Clinical Sciences</a></li>
				<li class="group"><a href="item?res=http%3A%2F%2Fresrev.ilrt.bris.ac.uk%2Fresearch-revealed-hub%2Forganisation_units%2FSSCM%23org">School of Social and Community Medicine</a></li>
				<li class="group"><a href="item?res=http%3A%2F%2Fresrev.ilrt.bris.ac.uk%2Fresearch-revealed-hub%2Forganisation_units%2FGEOG%23org">School of Geographical Sciences</a></li>
                                <li class="group"><a href="item?res=http%3A%2F%2Fresrev.ilrt.bris.ac.uk%2Fresearch-revealed-hub%2Forganisation_units%2FFSCI%23org">Faculty of Science</a></li>
			</ul>
		</div><!-- /section -->
	</div><!-- /col -->

</div><!-- /content -->

<#include "includes/address-footer.ftl"/>
<#include "includes/footer.ftl"/>

<#macro printQuickLinks>
<div class="quicklinks">Jump to:
	<#assign j = 0/>
	<#list 0..25 as let>
	<#if j % grouping == 0 >
	<#assign to=let+(grouping-1) />
	<#if 25 < to>
	<#assign to=25 />
	</#if>
	<a href="#${let}"><#list let..to as k>${alphabet[k]}</#list></a>
	</#if>
	<#assign j = j + 1/>
	</#list>
</div>
</#macro>

<#macro printAlphabetHeader start size>
<#assign to=start+size />
<#if 25 < to>
<#assign to=25 />
</#if>
<h2><a name="${start}"><#list start..to as i>${alphabet[i]}</#list></a></h2>
</#macro>
