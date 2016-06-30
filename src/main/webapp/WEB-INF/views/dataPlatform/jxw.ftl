<link rel="stylesheet" href="/static/css/pages/account.css" />
<div class="page-header">
    <nav id="w0" class="navbar-inverse navbar-fixed-top navbar" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#w0-collapse"><span
                        class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span></button>
                <a class="navbar-brand" href="/dp/infoshow">酒仙网 数据平台</a></div>
            <div id="w0-collapse" class="collapse navbar-collapse">
                <ul id="w1" class="navbar-nav navbar-right nav">
                    <li class="active"><a href="/">回到首页</a></li>
                </ul>
            </div>
        </div>
    </nav>
</div>
<div class="account-search" align="left">
    <form id="account-form" action="/dp/list" method="get" role="form">
        <ul class="serach-form clearfix">
            <li><label>品牌名称：</label>
                <div class="input-box">
                    <input type="text" id="" name="brand_name" value="${param.brand_name!''}" />
                </div>
            </li>

            <li><label>价格：</label>
                <div class="input-box">
                    <input type="text" id="" name="price" value="${param.price!''}" />
                </div>
            </li>
        </ul>
        <input type="hidden" id="sort" name="sort" value="${param.sort!''}" />
        <div class="search-btns">
            <button id="submitBtn" type="submit" class="btn">搜索</button>
        </div>
    </form>
</div>

<div class="account-grid">
    <div id="w0" class="grid-view">
        <div class="summary"><#if totalRecord &gt; 0></#if>共<b>${totalRecord}</b>条符合条件的数据.</div>
        <table class="dataList" border="1" align="center" >
            <thead>
            <tr style="height:30px;line-hieght:30px;align:middle;width: 100px" >

                <th><a <#if param.sort == 'goods_name'>class="asc"<#elseif param.sort == '-goods_name'>class="desc"</#if>
                       href="javascript:;" data-sort="goods_name">商品名称<i class="sort"></i></a></th>

                <th><a <#if param.sort == 'price'>class="asc"<#elseif param.sort == '-price'>class="desc"</#if>
                       href="javascript:;" data-sort="price">价钱<i class="sort"></i></a></th>

                <th><a <#if param.sort == 'operate_time'>class="asc"<#elseif param.sort == '-operate_time'>class="desc"</#if>
                       href="javascript:;" data-sort="operate_time">修改时间<i class="sort"></i></a></th>

                <th><a <#if param.sort == 'degree'>class="asc"<#elseif param.sort == '-degree'>class="desc"</#if>
                       href="javascript:;" data-sort="degree">酒精度</a></th>
            </tr>
            </thead>

            <tbody>
            <#if totalRecord == 0>
            <tr><td colspan="6"><div class="empty">没有数据</div></td></tr>
            </#if>
            <#list jxwList as list>
            <tr data-key="${list.id}" align="center">
                <td>${list.goods_name!''}</td>&nbsp;&nbsp;
                <td>${list.price!''}</td>&nbsp;&nbsp;
                <td>${list.operate_time!''}</td>&nbsp;&nbsp;
                <td>${list.degree!''}</td>&nbsp;&nbsp;
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>
<script src="/static/js/pages/account.js"></script>