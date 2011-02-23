<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<!-- main content -->
<div id="content" class="departmentListing">

<#assign grouping = 3 />
<#assign currLet = 'A'/>
<#assign i = 0/>

    <h1>Departments & groups</h1>

    <!-- description -->
    <p>Explanation of what this contains - formal organisational units, plus specific research groupings (e.g. for REF) Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam interdum pulvinar nibh. Maecenas eget nunc in justo rhoncus aliquam. Phasellus nisl mi, convallis ut, lacinia vel, sodales tempus.</p>

    <!-- show the number of results -->
    <#assign count=0/>
    <#list departmentList as dept><#assign count=count+1/></#list>
    <p><span class="bold">${count}</span> results</p>

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
    

    <ul>
        <#list 0..25 as let>
            <#list departmentList as dept>
                <#if alphabet[let] == dept['label']?substring(0, 1)>
                    <li><a href="item?res=${dept['dept']?url('utf-8')}"><span class="bold">${dept['label']?substring(0,1)}</span>${dept['label']?substring(1)}</a> <span class="count">${dept['gcount']}</span></li>
                </#if>
            </#list>

            <#assign i=i+1/>
            <#if i % grouping == 0 >
                </ul>
                <!-- print the header for the next grouping of entries -->
                <@printAlphabetHeader start=i size=grouping-1/>
                <ul>
            </#if>
        </#list>
    </ul>

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
    <h2>
    <span><a name="${start}"><#list start..to as i>${alphabet[i]}</#list></a></span>
    </h2>
</#macro>
