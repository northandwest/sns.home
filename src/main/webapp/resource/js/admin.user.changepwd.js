ulewo.userUrl = {
		saveUser:ulewo.absolutePath+"/admin/save_pwd.action"
}
$(function(){
	$("#post-btn").click(function(){
		savePwd();
	});
})

function savePwd(){
	var result = ulewo.checkForm($("#post-form"));
	if($("#post-form input[name='newPwd']").val()!=$("#post-form input[name='reNewPwd']").val()){
		ulewo.setError($("#post-form input[name='reNewPwd']"),"两次输入的密码不一致");
		result = false;
	}
	if(!result){
		return;
	}
	ulewo.ajaxRequest({
		url : ulewo.userUrl.saveUser,
	    data : $("#post-form").serialize(),
	    fun : function(res) {
	    	 ulewo.tipMsg({
					type : "success", content : "修改密码成功",timeout:1500
			  });
	    }
	})
}