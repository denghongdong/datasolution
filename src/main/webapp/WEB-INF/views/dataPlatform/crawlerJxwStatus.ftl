
<div class="breadcrumb">
    <a href="/">导航页</a> &gt; <a href="">JXW 数据平台</a> &gt; 数据状态
</div>

<div class="grid-view">
    <table class="dataList">
        <thead>
        <tr>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>本地路径</th>
            <th>当前品牌</th>
            <th>数据状态</th>
        </tr>
        </thead>
        <tbody>

        <#if total == 0>
        <tr><td colspan="7"><div class="empty">没有数据</div></td></tr>
        </#if>

        <#if infoParam??>
            <#list infoParam as item>
            <tr>
                <td> ${item.startTime!""}</td>
                <td> ${item.endTime!""}</td>
                <td> ${item.city!""}</td>
                <td> ${item.filePath!""}</td>
            </tr>
            </#list>
        <#else>
        </#if>
        </tbody>
    </table>



<script>
    /*表格排序功能*/
    $(".dataList thead a").click(function() {
        var col = $(this).attr("data-sort");
        var clz = $(this).attr("class");
        var val = ((typeof(clz) == "undefined" || clz === "desc") ? '' : '-') + col ;

        $("#sort").val(val);
        $("#account-form").submit();
    });

</script>
<script type="text/javascript" src="/static/js/pages/system.js?v=${resourceVersion?c}"></script>