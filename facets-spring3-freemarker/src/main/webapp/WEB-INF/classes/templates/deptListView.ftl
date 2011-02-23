<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<!-- main content -->
<div id="content" class="departmentListing">

<#assign grouping = 3 />
<#assign currLet = 'A'/>
<#assign i = 0/>

    <h1>Departments & groups</h1>

    <@printQuickLinks/>

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
    <div class="quicklinks">
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
