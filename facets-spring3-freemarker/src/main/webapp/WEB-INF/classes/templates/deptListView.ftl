<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<!-- main content -->
<div id="content" class="departmentListing">

	<div class="col1-2of3">

		<#assign grouping = 3 />
		<#assign currLet = 'A'/>
		<#assign i = 0/>

		<h1>Departments and groups</h1>

		<!-- show the search box -->
		<form action="item" method="get">
			<select class="autocomplete" name="res" id="res">
				<option value="">Start typing a name to filter these results</option>
				<#list departmentList as dept><option value="${dept['dept']}">${dept['label']}</option></#list>
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
			<#if alphabet[let] == dept['label']?substring(0, 1)>
			<li class="group"><a class="title" href="item?res=${dept['dept']?url('utf-8')}">${dept['label']}</a> <span class="otherdetails">${dept['gcount']} people, N publications, N grants <a href="#" class="show_impacts">show N impacts</a></span></li>
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
				<li class="group"><a href="#">Department of Chemical Engineering</a></li>
				<li class="group"><a href="#">Department of Chemical Engineering</a></li>
				<li class="group"><a href="#">Department of Chemical Engineering</a></li>
				<li class="group"><a href="#">Department of Chemical Engineering</a></li>
				<li class="group"><a href="#">Department of Chemical Engineering</a></li>
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
