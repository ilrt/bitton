<li class="person">
    <a class="title" href="<@drillForResult result=result/>"><@label resource=result/></a>
    <#if result['<-' + foaf + 'member']??>
        <span class="otherdetails"><#list result['<-' + foaf + 'member'] as value>
            <#if value['label'] != invalidUrl>${value['label']}<#if value_has_next>, </#if></#if></#list>
        </span>
    </#if>
</li>
