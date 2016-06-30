
/*传入毫秒数 转化为 yyyy-MM-DD hh:mm:ss 格式*/
function tempToDate(seconds){
    var time = new Date(seconds);
    var year = time.getFullYear(),
        month = (time.getMonth()+1),
        day = time.getDate(),
        hh = time.getHours(),
        mm = time.getMinutes(),
        ss = time.getSeconds();

    return year+"-"+month+"-"+day+" "+hh+":"+mm+":"+ss;
}

/*获取num个最新上传营业执照的公司列表 并讲节点插入到 oParent 的ul列表中*/
function freshNewList(oParent,num){
    $.ajax({
        type: 'GET',
        url: '/account/ajax/get-upload-licence-account',
        data: {
            'account_num': num,
        },
        success: function (result) {
            if(result.status == 0){
                $(oParent).empty();
                if(result.data.length > 0){

                    $.each(result.data,function(key, value){
                        var items = $("<li><a href='/account/view-operation-log?id="+result.data[key].accountId+"'><span>"+result.data[key].name+"</span> 于 <em>"+tempToDate(result.data[key].updateBusinessLicenceTimeTimestamp)+"</em> 上传了营业执照</a></li>");
                        $(oParent).append(items);
                    });
                }else{
                    var items = $("<li>暂无上传营业执照的公司 </li>")
                    $(oParent).append();
                }
            }
        }
    });
}

/*每5分钟 自动刷新一次最新上传执照的公司数据 */
var licenceBox = $("#upload-licence-account").find("ul");
freshNewList(licenceBox,6)
setInterval(function(){
    var licenceBox = $("#upload-licence-account").find("ul");
    freshNewList(licenceBox,6);
},50000);

/*手动点击刷新按钮*/
$("#refresh-licence-list").click(function(){
    var licenceBox = $("#upload-licence-account").find("ul");
    freshNewList(licenceBox,6);
    prompt("success","营业执照数据刷新成功!!")
});

/*点击查看更多按钮  弹窗显示 最新的20条数据*/
$("#viewMore-licence-list").click(function(){
    var licenceHtml = $("<ul class='licence-list'></ul>");
    var buttons = [{'title':'关闭','class':'cancel'}];

    freshNewList(licenceHtml,20);

    dialog.open("最新上传营业执照的客户",licenceHtml,buttons,"width:460px;margin-left:-230px;");
});

/*用户列表 点击表头排序功能*/
$(".dataList thead a").click(function() {
    var col = $(this).attr("data-sort");
    var clz = $(this).attr("class");
    var val = ((typeof(clz) == "undefined" || clz === "desc") ? '' : '-') + col ;

    $("#sort").val(val);
    $("#account-form").submit();
});




/*客户管理 公司信息tab ----------------------*/

/* 点击 查看审核历史  页面加载审核历史数据 */
$("#get-review-history").click(function(){
    var oParent = $("#review-history-box").find("tbody");
    $.ajax({
        type: 'GET',
        url: '/account/ajax/get-review-history',
        data: {
            'company_id': $(this).attr("data-companyId")
        },
        success: function (result) {
            if(!result.status){
                $(oParent).empty();
                if(result.data.length > 0){
                    $.each(result.data,function(key, value){
                        var items = $("<tr><td>"+tempToDate(result.data[key].reviewTime * 1000)+"</td><td>"+result.data[key].accountName+"</td><td>"+result.data[key].result+"</td><td>"+result.data[key].remark+"</td></tr>");
                        $(oParent).append(items);
                    });
                    $("#review-history-box").fadeIn(100);
                }else{
                    prompt("warning","还没有审核历史数据!!");
                }
            }
        }
    });
});
/*审核历史数据 关闭按钮*/
$(document).on('click','.closeBox', function () {
    $(this).parent().fadeOut(100);
});


/* 点击 开启自设logo权限  弹窗提示确认开启？然后开启权限 */
$("#start-logo-authority").click(function(){
    var close = confirm("您确认开启吗？");
    if(!close){
        return;
    }
    var accountID = $("#start-logo-authority").attr("data-accountId");
    $.ajax({
        type: 'POST',
        url: '/account/ajax/start-logo-authority',
        data: {
            'accountID': accountID
        },
       success: function (data) {
            if (data.status == 0) {
                window.location.href = "/account/view-company-info?id="+data.data;
            }
        },
        error: function (data) {
            alert(data["msg"]);
            if (data.status == 1) {
                window.location.href = "/account/view-company-info?id="+data.data;
            }
        }
    });
});

/* 点击 关闭自设logo权限  弹窗提示确认关闭 关闭权限 */
$("#close-logo-authority").click(function(){
    var close = confirm("您确认关闭吗？");
    if(!close){
        return;
    }
    var accountID = $("#close-logo-authority").attr("data-accountId");
    $.ajax({
        type: 'POST',
        url: '/account/ajax/close-logo-authority',
        data: {
            'accountID': accountID
        },
       success: function (data) {
            if (data.status == 0) {
                window.location.href = "/account/view-company-info?id="+data.data;
            }
        },
        error: function (data) {
            alert(data["msg"]);
            if (data.status == 1) {
                window.location.href = "/account/view-company-info?id="+data.data;
            }
        }
    });
});

/*修改 跟进状态*/
$("#changeFollowUpStatus").click(function(){
    $("#followUpStatus").fadeIn(500);
});
/*跟进状态 保存*/
$("#followUpStatusBtn").click(function(){
    $.ajax({
        type: 'GET',
        url: '/account/ajax/update-follow-up-status',
        data: {
            'account_id': $("#changeFollowUpStatus").attr("data-accountId"),
            'follow_up_status':$("#follow-up-status").val(),
        },
        success: function (result) {
            if(!result.status){
                prompt('success',' 跟进状态修改成功 !!');
                $("#followUpStatus").fadeOut(500);
                var timer = setTimeout(function(){
                    clearTimeout(timer);
                    location.reload();
                },1000)
            }else{
                prompt("warning","保存失败,请刷新重试!")
            }
        }
    });
})

/*通过审核*/
$("#review-accept-btn").click(function(){
    $.ajax({
        type: 'POST',
        url: '/account/ajax/review',
        data: {
            'account_id': $("#review-accept-btn").attr("data-accountId"),
            'status': 1,
            'remark': '',
        },
        success: function (data) {
            if(!data.status){
                prompt('success','操作成功 !!');
                location.reload();
            }else {
                prompt('error',data.msg);
            }
        }
    });
});
/*审核驳回*/
$(document).on('click','#review-reject-btn', function () {
    var feedBackHtml = $("<textarea id='remark' class='verify' data-verify='require'></textarea>");
    var buttons = [{'title':'确定','class':'confirm', 'callback':fnConfirm1},
        {'title':'关闭','class':'cancel'}];
    dialog.open("添加备注",feedBackHtml,buttons)
});

function fnConfirm1(){
    var remark = $("#remark").val().trim();
    if(remark == ""){
        prompt("warning","备注内容不能为空!")
        return false;
    }
    $.ajax({
        type: 'POST',
        url: '/account/ajax/review',
        data: {
            'account_id': $("#review-reject-btn").attr("data-accountId"),
            'status': 2,
            'remark': remark,
        },
        success: function (data) {
            if(!data.status){
                prompt('success','操作成功 !!');
                location.reload();
            }else {
                prompt('error',data.msg);
            }
        }
    });
}

/*客户管理 添加备注 */
$(document).on('click','#add-remark', function () {
    var feedBackHtml = $("<textarea id='remark' class='verify' data-verify='require'></textarea>");
    var buttons = [{'title':'确定','class':'confirm', 'callback':fnConfirm},
        {'title':'关闭','class':'cancel'}];
    dialog.open("添加备注",feedBackHtml,buttons)
});
function fnConfirm(){
    var remark = $("#remark").val().trim();
    if(remark == ""){
        prompt("warning","备注内容不能为空!")
        return false;
    }
    $.ajax({
        type: 'POST',
        url: '/account/ajax/add-account-remark',
        data: {
            'account_id': $("#account_id_hidden").val(),
            'remark': remark,
        },
        success: function (data) {
            if(!data.status){
                location.reload();
            }else{
                prompt("warning",data.msg)
            }
        }
    });
}

/*小美人鱼专属 --------------*/
/*美人鱼修改手机号*/
$("#mermaid-mobile-button").click(function(){
    var this_ = this;
    $("#mermaid-mobile-input").blur();
    if($("#mermaid-mobile-input").parents(".input-box").hasClass("has-error-input")){
        return false;
    }

    forbidRepeatSubmit(this_);
    $.ajax({
        type: 'POST',
        url: '/account/ajax/update-account-mobile',
        data: {
            'account_id': $("#account_id_hidden").val(),
            'mobile': $("#mermaid-mobile-input").val(),
        },
        success: function (data) {
            clearForbidden(this_);
            if(!data.status){
                prompt('success','修改手机号成功 !!');
            }else {
                prompt('error',data.msg);
            }
        }
    });
});
/*美人鱼 修改密码*/
$("#mermaid-password-button").click(function(){
    var this_ = this;

    $("#mermaid-password-input").blur();
    if($("#mermaid-password-input").parents(".input-box").hasClass("has-error-input")){
        return false;
    }

    forbidRepeatSubmit(this_);
    $.ajax({
        type: 'POST',
        url: '/account/ajax/reset-account-password',
        data: {
            'account_id': $("#account_id_hidden").val(),
            'password': $("#mermaid-password-input").val(),
        },
        success: function (data) {
            clearForbidden(this_);
            if(!data.status){
                prompt('success','重置密码成功 !!');
            }else {
                prompt('error',data.msg);
            }
        }
    });
});

/*营业执照  查看大图*/
$(document).on("click","#viewLicenceBig",function(){
    var href = $(this).attr("data-href");
    var bigImg = $("<img src='"+href+"' class='bigLicenceImg'/>");

    var imgBox = $("<div id='licenceImgBox'><a href='javascript:;' class='close'><i class='fa fa-times'></i></a> </div>");
    imgBox.append(bigImg);
    $("body").append(imgBox);
});
$(document).on("click","#licenceImgBox .close",function(){
    $(this).parent().remove();
})


/*公司logo  查看大图*/
$(document).on("click","#viewLogoBig",function(){
    var href = $(this).attr("data-href");
    var bigImg = $("<img src='"+href+"' class='bigLogoImg'/>");

    var imgBox = $("<div id='logoImgBox'><a href='javascript:;' class='close'><i class='fa fa-times'></i></a> </div>");
    imgBox.append(bigImg);
    $("body").append(imgBox);
});
$(document).on("click","#logoImgBox .close",function(){
    $(this).parent().remove();
})