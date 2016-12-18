$(function() {
    ulewo.initForm($("form.u-form").eq(0));
    ulewo.url = {
	register : "register.do"
    };
    $(".register-btn").click(function() {
	register();
    });

    $("#refreshCheckCode").click(function() {
	$(this).find("img").attr("src", "checkCode?" + new Date());
    });

    function register() {
	var formObj = $("form.u-form").eq(0);
	var result = ulewo.checkForm(formObj);
	var password = $.trim(formObj.find("input[name='password']").val());
	var rePassword = $.trim(formObj.find("input[name='rePassword']").val());
	if (password != rePassword) {
	    ulewo.setError(formObj.find("input[name='rePassword']"), "两次输入的密码不一致");
	    return;
	}
	if (result) {
	    ulewo.ajaxRequest({
		async : true, url : ulewo.url.register, data : formObj.eq(0).serialize(), fun : function(res) {
		    ulewo.tipMsg({
			type : "success", content : "注册成功，正在转向个人主页",timeout:1500,fun:function(){
			    document.location.href = ulewo.absolutePath + "/user/" + res.data;
			}
		    });
		}
	    })
	}
    }

    $(document).keyup(function(event) {
	var code = event.keyCode;
	if (code == 13) {
	    register();
	}
    });
});