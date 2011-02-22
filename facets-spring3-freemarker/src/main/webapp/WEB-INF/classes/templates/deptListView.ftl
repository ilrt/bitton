<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<!-- main content -->
<div id="content" class="departmentListing">

<#assign grouping = 3 />
<#assign currLet = 'A'/>
<#assign i = 0/>

    <h1>Departments & groups</h1>
    <@printAlphabetHeader start=i size=grouping-1/>
    <ul>
        <#list departmentList as dept>
            <#assign nextLet = dept['label']?substring(0, 1)/>
            <#if nextLet != currLet>
                <#assign i=i+1/>
                <#assign currLet=nextLet/>
                <#if i % grouping == 0 >
                    </ul>
                    <!-- print the header for the next grouping of entries -->
                    <@printAlphabetHeader start=i size=grouping-1/>
                    <ul>
                </#if>
            </#if>
        <li><a href="item?res=${dept['dept']?url('utf-8')}"><span class="bold">${dept['label']?substring(0,1)}</span>${dept['label']?substring(1)}</a> <span class="count">${dept['gcount']}</span></li>
        </#list>
    </ul>

</div><!-- /content -->

<#include "includes/address-footer.ftl"/>
<#include "includes/footer.ftl"/>

<#macro printAlphabetHeader start size>
    <#assign to=start+size />
    <h2>
    <span><#list start..to as i>${alphabet[i]}</#list></span>
    </h2>
</#macro>
