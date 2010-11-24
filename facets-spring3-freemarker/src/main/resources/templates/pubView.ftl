<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>
<#include "includes/header-title.ftl"/>
<#include "includes/header-navigation.ftl"/>

<!-- main content -->
<div id="main-content">

    <div id="content">

        <h2>${resource[dc + 'title']?first}</h2>

        <p>Contributors:
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
        </p>

        <#if resource[dc + 'contributor']?size == 1>
            <p>Department: <@displayOrg org=resource[resrev + 'department']?first/></p>
        <#else>
            <p>Departments of above contributors:
                <ul>
                    <#list resource[resrev + 'department'] as department>
                        <li><@displayOrg org=department/></li>
                    </#list>
                </ul>
            </p>
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
                        ${resource[elem</th>
                <td>
                   ents + 'publisher']?first}
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
        </table>

        <#if resource[rdfs + 'seeAlso']??>
            <p>
                See also: <a href="${resource[rdfs + 'seeAlso']?first}">${resource[rdfs + 'seeAlso']?first}</a>
            </p>
        </#if>

<!--
        <table class="debug">
            <#list resource?keys as key>
                <#list resource[key] as value>
                    <tr><td>${key}</td><td>${value}</td></tr>
                </#list>
            </#list>
        </table>
-->

        <#if view??><p><em><a href="javascript:history.go(-1)">Return to results</a></em></p></#if>

     </div>

<#include "includes/address-footer.ftl"/>

</div>

<#include "includes/footer.ftl"/>
