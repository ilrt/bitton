<li class="people">
    <a class="title" href="<@drillForResult result=result/>"><@label resource=result/></a>
    <#if result['<-' + foaf + 'member']??>
        <span class="otherdetails"><#list result['<-' + foaf + 'member'] as value>
            ${value['label']}</#list>
        </span>
    </#if>
</li>