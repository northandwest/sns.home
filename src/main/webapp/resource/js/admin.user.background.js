ulewo.backgroundName = ulewo.background.substring(ulewo.background.lastIndexOf("/")+1);
ulewo.userUrl={
		saveBackground:ulewo.absolutePath+"/admin/save_user_center_background.action"
}
$(function(){
	initSystemBackground();
	$(document).on("click", ".system-icon-s", function() {
		 checkSystemIcon($(this));
	});
	
	$("#save-btn").click(function(){
		saveUserBackground($(this));
	});
});
function initSystemBackground(){
	for(var i=1;i<=10;i++){
		var system_icon = $("<div class='system-icon-s' data='defbg/bg"+i+".jpg'></div>").appendTo("#system-icon");
		$("<img src="+ulewo.bgImagePath+"/images/defbg/bg"+i+".jpg>").appendTo(system_icon);
		if(ulewo.backgroundName==("bg"+i+".jpg")){
			$("<span class='icon-check'></span>").appendTo(system_icon);
			system_icon.css({"border":"2px solid #1094FA"});
		}
	}
	$('<div class="clear"></div>').appendTo("#system-icon");
};
function checkSystemIcon(curObj){
	$(".system-icon-s").css({"border":"2px solid #fff"});
	$("span.icon-check").remove();
	$("<span class='icon-check'></span>").appendTo(curObj);
	curObj.css({"border":"2px solid #1094FA"});
};

function saveUserBackground(curObj){
	if($(".system-icon-s span.icon-check").length==0){
		ulewo.setError($("#save-btn"), "请选择背景图片");
		return;
	}
	ulewo.ajaxRequest({
	    url : ulewo.userUrl.saveBackground,
	    data : {
	    	background:$(".system-icon-s span.icon-check").parent().attr("data")
	    },
	    fun : function(response) {
	    	 ulewo.tipMsg({
	    			type : "success", content : "保存成功", timeout : 1500
	    	  });
	    }
	});
};
