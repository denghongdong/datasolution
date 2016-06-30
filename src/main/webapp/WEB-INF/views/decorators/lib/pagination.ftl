<#macro pagination form total pageIndex pageSize=10 align="right">
<script type="text/javascript">
    function pagination(pageIndex) {
        var hidden = '<input type="hidden" id="pageIndex" name="pageIndex" value="' + pageIndex + '" /><input type="hidden" id="pageSize" name="pageSize" value="${pageSize}" />';
        $("${form}").append(hidden).submit();
    }
</script>
    <#local pageCount=(total/pageSize)?ceiling/>
    <#local maxBegin=pageCount-9/>
    <#nested><!--嵌套内容-->
<nav style="text-align: ${align}">
    <ul class="pagination ${align}" style="margin: 0 0 20px 0;">
        <li><span>总计:${total?string.number}</span></li>
        <#if (pageCount>1)>
            <!--第一页、Prev-->
            <#if (pageIndex<=1)>
                <li class="disabled"><a href="javascript:void(0);">«</a></li>
                <li class="active"><a href="javascript:void(0);">1</a></li>
            <#else>
                <li><a href="javascript:pagination(${pageIndex-1});">«</a></li>
                <li><a href="javascript:pagination(1);">1</a></li>
            </#if>

            <!--计算开始计数begin-->
            <#local begin=pageIndex-4>

            <#if (begin>maxBegin)>
                <#local begin=maxBegin />
            </#if>
            <#if (begin<2)>
                <#local begin=2 />
            </#if>
            <!--前面是否出现更多-->
            <#if (begin>2)>
                <li class="disabled"><a href="javascript:void(0);">..</a></li>
            </#if>

            <!--计算结束计数end-->
            <#local end=begin+8 />
            <#if (end>=pageCount-1)>
                <#local end=pageCount-1 />
            </#if>
            <#if (begin<=end)>

                <#list begin..end as num>
                    <#if pageIndex==num>
                        <li class="active"><a href="javascript:void(0);">${num}</a></li>
                    <#else>
                        <li><a href="javascript:pagination(${num});">${num}</a></li>
                    </#if>
                </#list>
            </#if>
            <!--后面是否出现更多-->
            <#if (end<pageCount-1)>
                <li class="disabled"><a href="javascript:void(0);">..</a></li>
            </#if>

            <!--最后一页、Next-->
            <#if (pageIndex>=pageCount)>
                <li class="active"><a href="javascript:void(0);">${pageCount}</a></li>
                <li class="disabled"><a href="javascript:void(0);">»</a></li>
            <#else>
                <li><a href="javascript:pagination(${pageCount});">${pageCount}</a></li>
                <li><a href="javascript:pagination(${pageIndex+1});">»</a></li>
            </#if>
        </#if>
    </ul>
</nav>
</#macro>