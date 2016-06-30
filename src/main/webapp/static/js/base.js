
//进入页面 判断sidebar是否收起状态
$(function(){
    if($.cookie('sidebar_isFold') == "true"){
        $("#pageContent").addClass("foldSidebar");
    }else if($.cookie('sidebar_isFold') == "false"){
        $("#pageContent").removeClass("foldSidebar");
    }
});


//左侧菜单 展开收取功能
$(document).on('click','#sideBarToggleBtn', function () {

    $("#pageContent").toggleClass("foldSidebar");

    if($("#pageContent").hasClass("foldSidebar")){  //收起状态
        $(this).find(".fa").removeClass("fa-angle-double-left").addClass("fa-angle-double-right");
        $.cookie('sidebar_isFold', true);
    }else{   //展开状态
        $(this).find(".fa").removeClass("fa-angle-double-right").addClass("fa-angle-double-left");
        $.cookie('sidebar_isFold', false);
    }
});

/*
 * dialog.open(title,message,buttons,styles)
 * title: 弹窗 标题
 * message: 正文部分
 * buttons: [{'title':'确定','class':'cancel||confirm', 'callback':function..},...]
 *
 * */
//大弹窗  带绿色头部
var dialog = {
    open : function(title,main,buttons,styles,closeBtn){
        $('#dialog').remove();
        var clientHeight = document.documentElement.scrollHeight || document.body.scrollHeight;
        var heightSty = "height:"+clientHeight+"px";

        var dialogDom = $("<div id='dialog'><div class='dialog-bg'></div><div class='dialog-body'>" +
            "<h3 class='dialog-head'>"+title+"</h3>" +
            "<div class='dialog-main'></div>" +
            "<div class='dialog-foot'> </div>" +
            "</div></div>");

        if(title == ""){
            dialogDom.find(".dialog-head").remove();
        }

        if(styles){
            dialogDom.find(".dialog-body").attr("style",styles);
        }
        if(closeBtn){
            dialogDom.find(".dialog-body").append("<a href='javascript:;' class='closePopBtn'><i class='fa fa-times'></i></a>")
            $(dialogDom).find(".closePopBtn").click( dialog.close);
        }
        var mainArea =dialogDom.find(".dialog-main"),
            btnArea = dialogDom.find(".dialog-foot");

        mainArea.append(main);

        if(buttons.length == 0) {
            dialogDom.find(".dialog-foot").remove();
        }else{
            for (var i = 0; i < buttons.length; i++) {
                var thisBtn = buttons[i];
                if (!thisBtn.callback) {
                    thisBtn.callback = dialog.close;
                }

                var thisBtnDom = $("<a href=\"javascript:;\" class=\"" + thisBtn.class + "\">" + thisBtn.title + "</a>");
                thisBtnDom.click(thisBtn.callback);
                btnArea.append(thisBtnDom);
            }
        }
        dialogDom.css('zIndex',setPopZindex());
        $("body").prepend(dialogDom);
    },
    close : function(){
        $('#dialog').remove();
    }
};
function setPopZindex(){
    var dialogs = $("#dialog");
    var popups = $("#popup");
    var zIndex = 10000;
    if(dialogs){
        zIndex += dialogs.length;
    }
    if(popups){
        zIndex += popups.length;
    }
    return zIndex;
}

function prompt(type,txt){
    var promptDom = $("<div class='prompt "+type+"'>"+txt+"</div>");
    var proNum = $(".prompt").length,
        thisTop = 60;

    if(proNum > 0){
        thisTop += (40 * proNum);
    }
    promptDom.css("top",thisTop+"px");
    $("body").append(promptDom);

    var timer = setTimeout(function(){
        promptDom.remove();
        clearTimeout(timer);
    },4000);

}

/*防止重复提交*/
function forbidRepeatSubmit(this_){
    var oldTxt = $(this_).html(),
        loadingTxt = $(this_).attr("data-loading-text") || '加载中...';
    $(this_).attr("disabled","disabled");
    $(this_).text(loadingTxt);
    $(this_).attr("data-txt",oldTxt);
    return false;
}
function clearForbidden(this_){
    var oldTxt = $(this_).attr("data-txt");
    $(this_).removeAttr("disabled");
    $(this_).html(oldTxt);
}