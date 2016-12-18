ulewo.url = {
    findpwd : "sendCheckCode.do", resetpwd : "resetpwd.do"
};
$(function() {
    ulewo.initForm($("form.u-form").eq(0));
    $(".btn").click(function() {
	findpwd();
    });

    $(document).on("click", "#refreshCheckCode", function() {
	$(this).find("img").attr("src", "checkCode?" + new Date());
    });

    function findpwd() {
	var formObj = $("form.u-form").eq(0);
	var result = ulewo.checkForm(formObj);
	if (result) {
	    ulewo
		    .ajaxRequest({
			async : true,
			url : ulewo.url.findpwd,
			data : formObj.eq(0).serialize(),
			fun : function(response) {
			    if (response.responseCode.code == ulewo.resultCode.SUCCESS) {
				var content = '<form class="u-form" id="resetForm">'
					+ '<input type="hidden" name="email" value="'
					+ $("#findEmail").val()
					+ '">'
					+ '<div class="form-item">'
					+ '<div class="item-tit">新密码：</div>'
					+ '<div class="item-input"><input type="password" name="password" placeholder="密码长度6-16位字符，由数字、字母组成" checkData=\'{min:6,max:16,reg:"common",rq:true,msg:"请输入新密码"}\'></div>'
					+ '<div class="clear"></div></div>' + '<div class="form-item">' + '<div class="item-tit">确认密码：</div>'
					+ '<div class="item-input"><input type="password" name="repassword" placeholder="请再次输入您的新密码" checkData=\'{rq:true,msg:"请再次输入新密码"}\'></div>'
					+ '<div class="clear"></div>' + '</div>' + '<div class="form-item">' + '<div class="item-tit">验证码：</div>'
					+ '<div class="item-input"><input type="text" name="checkCode" placeholder="请输入您收到的验证码" checkData=\'{rq:true,msg:"请输入您收到的验证码"}\'></div>'
					+ '<div class="clear"></div>' + '</div>' + '</form>';
				ulewo.resetDialog = dialog({
				    title : "找回密码", content : content, okValue : '确定', ok : function() {
					resetPwd();
					return false;
				    }, cancelValue : '取消', cancel : function() {}
				});
				ulewo.resetDialog.showModal();
			    }
			}
		    })
	}
    }

    $(document).keyup(function(event) {
	var code = event.keyCode;
	if (code == 13) {
	    findpwd();
	}
    });
});

function resetPwd() {
    var formObj = $("#resetForm");
    ulewo.initForm(formObj);
    var result = ulewo.checkForm(formObj);
    var password = $.trim(formObj.find("input[name='password']").val());
    var repassword = $.trim(formObj.find("input[name='repassword']").val());
    if (password != repassword) {
	ulewo.setError(formObj.find("input[name='rePassword']"), "两次输入的密码不一致");
	return;
    }
    if (result) {
	ulewo.ajaxRequest({
	    async : true, url : ulewo.url.resetpwd, data : formObj.eq(0).serialize(), fun : function() {
		alert("修改成功啦");
	    }
	})
    }
}