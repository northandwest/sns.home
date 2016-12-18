ulewo.userUrl = {
		saveUser:ulewo.absolutePath+"/admin/save_user.action"
}

$(function(){
	$("#post-btn").click(function(){
		saveUserInfo();
	});
})

function saveUserInfo(){
	$("#post-form").attr("action",ulewo.userUrl.saveUser);
	$("#post-form").submit();
/*	var data = $("#post-form").serialize();
	$.ajax({
		  type: 'POST',
		  url: ulewo.userUrl.saveUser,
		  data: data,
		  success: function(response) {
		    	 ulewo.tipMsg({
		    			type : "success", content : "保存成功", timeout : 1500
		    	  });
		    }
		});
	*/

}