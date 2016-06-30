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
                    <a class="navbar-brand" >欢迎来到JDJR 数据平台</a></div>
                <div id="w0-collapse" class="collapse navbar-collapse">
                    <ul id="w1" class="navbar-nav navbar-right nav">
                        <li class="active"><a href="/sso/index">登录</a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
    <!-- END HEADER -->
    <div class="container">
        <div class="site-login">
            <h1></h1>

            <form id="register-form" action="/site/register" method="post" role="form">
                <input type="hidden" name="redirectUrl" value="${redirectUrl!''}"/>

                <div class="form-group field-registerform-name required">
                    <label class="control-label" for="registerform_name">用户名</label>
                    <input type="text" id="registerform_name" class="form-control" name="name"
                           check-type="required" minlength="1" maxlength="6" value="${name!''}">
                </div>

                <div class="form-group field-registerform-password required">
                    <label class="control-label" for="registerform_password">密码</label>
                    <input type="password" id="registerform_password" class="form-control" name="password"
                           check-type="required" minlength="6" maxlength="15">
                </div>

                <div class="form-group field-registerform-email mail">
                    <label class="control-label" for="registerform_email">邮箱</label>
                    <input type="text" id="registerform_email" class="form-control" name="email"
                           check-type="mail"  value="${email!''}">
                </div>
                <div>
            <#if msg??>
                        ${msg}
                    </#if>
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
                    <button id="submit" onclick="userRegister()" type="button" class="btn btn-primary login-btn">注册</button>
                </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $("#register-form").validation({icon: true});
    $("#submit").on('click', function (event) {
        if ($("#register-form").valid(this) == false) {
            return false;
        }
    });

    function userRegister() {
        var registerform_name = $("#registerform_name").val();
        var registerform_password = $("#registerform_password").val();
        var registerform_email = $("#registerform_email").val();
        $.ajax({
            type: "POST",
            url: "/site/register",
            data: {name: registerform_name,
                password: registerform_password, email: registerform_email},
            dataType: "json",
            success: function (data) {
                alert(data["msg"]);
                if(data.status==0){
                    window.location = "/site/addAccount";
                }
            },
            error: function (data) {
                alert(data["msg"]);
                if(data.status==1){
                    window.location = "/site/addAccount";
                }
            }
        });
    }
</script>
