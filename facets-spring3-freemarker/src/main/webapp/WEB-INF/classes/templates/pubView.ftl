<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<!-- main content -->
    <div id="content">

        <#if view??><p><a href="javascript:history.go(-1)">&lt; Return to results</a></p></#if>

        <h1>${resource[dc + 'title']?first}</h1>
        
	<#if resource[dc + 'abstract']??><p>${resource[dc + 'abstract']?first}</p></#if>

        <#if resource[rdfs + 'seeAlso']??>
	    <button href="${resource[rdfs + 'seeAlso']?first}">Get full text &gt;</button>
        </#if>


        <p>Contributors:</p>
            <!-- order alphabetically -->
            <#if resource[dc + 'contributor']??>
                <ul>
                    <#list resource[dc + 'contributor'] as contributor>
                        <li>
                             <@displayPerson person=contributor/>
                        </li>
                    </#list>
                </ul>
            </#if>

        <#if resource[resrev + 'department']??>
	<!--
	Don't display depts unless we can associate them with a specific contributor:

            <p>Departments of above contributors:</p>
                <ul>
                    <#list resource[resrev + 'department'] as department>
                        <li><@displayOrg org=department/></li>
                    </#list>
                </ul>
	-->
        </#if>

        <table>
            <#if resource[dc + 'date']??>
                <tr>
                    <th>Date of Publication:</th>
                    <td>
                        ${resource[dc + 'date']?first}
                    </td>
                </tr>
            </#if>
            <tr>
                <th>Type of Publication:</th>
                <td>
                    <#list resource[rdf + 'type'] as type>
                        <#if type?contains(bibo?string)>${type?substring(bibo?length)}</#if>
                    </#list>
                </td>
            </tr>
            <#if resource[elements + 'publisher']??>
                <tr>
                    <th>Publisher:</th>
                    <td>
                        ${resource[elements + 'publisher']?first}
                    </td>
                </tr>
            </#if>
            <#if resource[bibo + 'isbn']??>
                <tr>
                    <th>ISBN:</th>
                    <td>
                        ${resource[bibo + 'isbn']?first}
                    </td>
                </tr>
            </#if>
            <#if resource[bibo + 'volume']??>
                <tr>
                    <th>Volume:</th>
                    <td>
                        ${resource[bibo + 'volume']?first}
                    </td>
                </tr>
            </#if>
            <#if resource[dc + 'isPartOf']??>
                <tr>
                    <th>Part Of:</th>
                    <td>
                        ${resource[dc + 'isPartOf']?first}
                    </td>
                </tr>
            </#if>
            <#if resource[bibo + 'pageStart']?? && resource[bibo + 'pageEnd']??>
                <tr>
                    <th>Pages:</th>
                    <td>
                        ${resource[bibo + 'pageStart']?first} - ${resource[bibo + 'pageEnd']?first}
                    </td>
                </tr>
            <#elseif resource[bibo + 'pageStart']??>
                <tr>
                    <th>Page Start:</th>
                    <td>
                        ${resource[bibo + 'pageStart']?first}
                    </td>
                </tr>
            <#elseif resource[bibo + 'pageEnd']??>
                <tr>
                    <th>Page End:</th>
                    <td>
                        ${resource[bibo + 'pageEnd']?first}
                    </td>
                </tr>
            </#if>
            <#if resource[dc + 'bibliographicCitation']??>
                <tr>
                    <th>Citation:</th>
                    <td>
                        <cite>${resource[dc + 'bibliographicCitation']?first}</cite>
                    </td>
                </tr>
            </#if>
        </table>

<!--
        <table class="debug">
            <#list resource?keys as key>
                <#list resource[key] as value>
                    <tr><td>${key}</td><td>${value}</td></tr>
                </#list>
            </#list>
        </table>
-->

     </div>

<#include "includes/address-footer.ftl"/>

<#include "includes/footer.ftl"/>
