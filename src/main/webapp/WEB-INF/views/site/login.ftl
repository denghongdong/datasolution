<div class="wrap">
    <!-- BEGIN HEADER -->
    <div class="page-header">
        <nav id="w0" class="navbar-inverse navbar-fixed-top navbar" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#w0-collapse"><span
                            class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span></button>
                    <a class="navbar-brand" href="/">欢迎来到JDJR 数据平台</a></div>
                <div id="w0-collapse" class="collapse navbar-collapse">
                    <ul id="w1" class="navbar-nav navbar-right nav">
                        <li class="active"><a href="/site/addAccount">注册</a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
    <!-- END HEADER -->
    <div class="container">
        <div class="site-login">
            <h1></h1>

            <form id="login-form" action="/site/login-check" method="post" role="form">
                <input type="hidden" name="redirectUrl" value="${redirectUrl!''}"/>
                <div class="form-group field-loginform-email">
                    <label class="control-label" for="loginform-email">账号</label>
                    <input type="text" id="loginform-email" class="form-control" name="loginname"
                           check-type="mail required" value="${loginname!''}">
                </div>
                <div class="form-group field-loginform-password required">
                    <label class="control-label" for="loginform-password">密码</label>
                    <input type="password" id="loginform-password" class="form-control" name="password"
                           check-type="required" minlength="6">
                </div>
                <div>
                    <#if msg??>
                        ${msg}
                    </#if>
                </div>
                <div class="form-group field-loginform-rememberme">
                    <div class="checkbox">
                        <label for="loginform-rememberme">
                            <input type="checkbox" id="loginform-rememberme" name="rememberMe" value="1">
                            记住我
                        </label>
                    </div>
                </div>
                <div class="form-group">
                <#if errmsg??>
                    <span id="validerrmsg" class="help-block" style="color: #FF0000;">
                        <div class="alert alert-danger" role="alert" style="padding:10px;">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                        ${errmsg}
                        </div>
                    </span>
                </#if>
                    <button id="submit" type="submit" class="btn btn-primary login-btn">登录</button>
                </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $("#login-form").validation({icon: true});
    $("#submit").on('click', function (event) {
        if ($("#login-form").valid(this) == false) {
            return false;
        }
    });
</script>
