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
                <a class="navbar-brand" href="/weekly/view">京东人账号信息</a></div>
            <div id="w0-collapse" class="collapse navbar-collapse">
                <ul id="w1" class="navbar-nav navbar-right nav">
                    <li class="active"><a href="/">回到首页</a></li>
                </ul>
            </div>
        </div>
    </nav>
</div>
<div class="account-search" align="left">
    <form id="account-form" action="/account/list" method="get" role="form">
        <ul class="serach-form clearfix">
            <li><label>用户登录名：</label>
                <div class="input-box">
                    <input type="text" id="" name="login" value="${param.login!''}" />
                </div>
            </li>
            <li><label>用户姓名：</label>
                <div class="input-box">
                    <input type="text" id="" name="name" value="${param.name!''}" />
                </div>
            </li>

            <li><label>用户邮箱：</label>
                <div class="input-box">
                    <input type="text" id="" name="email" value="${param.email!''}" />
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
        <div class="summary"><#if totalRecord &gt; 0></#if>共<b>${totalRecord}</b>个用户.</div>
        <table class="dataList" border="1" align="center" >
            <thead>
            <tr style="height:30px;line-hieght:30px;align:middle;width: 100px" >
                <th><a <#if param.sort == 'login'>class="asc"<#elseif param.sort == '-login'>class="desc"</#if>
                       href="javascript:;" data-sort="login">用户登录名<i class="sort"></i></th>

                <th><a <#if param.sort == 'name'>class="asc"<#elseif param.sort == '-name'>class="desc"</#if>
                       href="javascript:;" data-sort="name">用户姓名<i class="sort"></i></a></th>

                <th><a <#if param.sort == 'email'>class="asc"<#elseif param.sort == '-email'>class="desc"</#if>
                       href="javascript:;" data-sort="email">用户邮箱<i class="sort"></i></a></th>

                <th><a <#if param.sort == 'addTimeStamp'>class="asc"<#elseif param.sort == '-addTimeStamp'>class="desc"</#if>
                       href="javascript:;" data-sort="addTimeStamp">账户添加时间<i class="sort"></i></a></th>

                <th><a <#if param.sort == 'modTimeStamp'>class="asc"<#elseif param.sort == '-modTimeStamp'>class="desc"</#if>
                       href="javascript:;" data-sort="modTimeStamp">账户修改时间</a></th>
            </tr>
            </thead>

            <tbody>
                <#if totalRecord == 0>
                    <tr><td colspan="6"><div class="empty">没有数据</div></td></tr>
                </#if>
                <#list accountList as account>
                    <tr data-key="${account.id}" align="center">
                        <td>${account.login!''}</td>&nbsp;&nbsp;
                        <td><a href="/account/update-account-info?${account.id}">${account.name!''}</a></td>&nbsp;&nbsp;
                        <td>${account.email!''}</td>&nbsp;&nbsp;
                        <td>${addTimeStamp}</td>&nbsp;&nbsp;
                        <td>${modTimeStamp!''}</td>&nbsp;&nbsp;
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>
<script src="/static/js/pages/account.js"></script>