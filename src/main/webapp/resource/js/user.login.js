$(function() {
    var url = ulewo.curUrl;
    if (url.indexOf("redirect") != -1) {
	ulewo.redirect = url.substring(url.indexOf("redirect") + 9, url.length);
    }
    ulewo.initForm($("form.u-form").eq(0));
    ulewo.url = {
	login : "login.do"
    };
    $(".login-btn").click(function() {
	login();
    });

    $(document).on("click", "#refreshCheckCode", function() {
	$(this).find("img").attr("src", "checkCode?" + new Date());
    });

    $(document).keyup(function(event) {
	var code = event.keyCode;
	if (code == 13) {
	    login();
	}
    });
});

function login() {
    var formObj = $("form.u-form").eq(0);
    var result = ulewo.checkForm(formObj);
    if (result) {
	ulewo.ajaxRequest({
	    async : true, url : ulewo.url.login, data : formObj.eq(0).serialize(), fun : function(response) {
		if (response.responseCode.code == ulewo.resultCode.SUCCESS) {
		    ulewo.tipMsg({
			type : "success", content : "登陆成功，正在跳转....."
		    });
		    if (null == ulewo.redirect || ulewo.redirect == "") {
			ulewo.redirect = ulewo.absolutePath;
		    }
		    document.location.href = ulewo.redirect;
		} else if (response.responseCode.code == ulewo.resultCode.MOREMAXLOGINCOUNT) {
		    ulewo.tipMsg({
			type : "error", content : response.errorMsg, timeout : 3000
		    });
		    if ($("#checkCode-area").children().length == 0) {
			var formItem = $('<div class="form-item"></div>').appendTo($("#checkCode-area"));
			$('<div class="item-tit">验证码：</div>').appendTo(formItem);
			$('<div class="item-input"><input type="text" name="checkCode" class="check-code" placeholder="请输入验证码"  checkData="{rq:true}"></div>').appendTo(formItem);
			$('<div class="check_code"><a href="javascript:void(0)" id="refreshCheckCode"><img src="checkCode"></a></div>').appendTo(formItem);
			$('<div class="clear"></div>').appendTo(formItem);
		    }
		}
	    }
	})
    }
}