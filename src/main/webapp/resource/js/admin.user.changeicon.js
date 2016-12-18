ulewo.userIconUrl={
	saveCustomerUserIcon:ulewo.absolutePath+"/admin/save_user_icon.action",
	saveSysUserIcon:ulewo.absolutePath+"/admin/save_sys_user_icon.action"
	
}
ulewo.iconType = 0;
var uploader = WebUploader.create({
    // 选完文件后，是否自动上传。
    auto : true,

    // swf文件路径
    swf : ulewo.absolutePath + '/resource/webuploader/Uploader.swf',

    // 文件接收服务端。
    server : ulewo.absolutePath + '/imageUpload2Temp.action',

    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick : {
	id : '#filePicker', multiple : false
    },
    accept : {
	title : 'Images', extensions : 'gif,jpg,jpeg,bmp,png,GIF,JPG,JPEG,BMP,PNG', mimeTypes : 'image/*'
    },
    duplicate : 1, // 不去重
    compress : false, // 压缩
    fileSingleSizeLimit : 2 * 1024 * 1024
});
uploader.on('fileQueued', function(file) {
    $("#filePicker").hide();
    $("#loading-upload").show();
});

uploader.on('uploadSuccess', function(file, response) {
    if (response.responseCode == "200") {
	$("#imgarea").show();
	cutter.reload(ulewo.absolutePath+response.savePath);
    }
    $("#file_upload").hide();
    $("#uploading").hide();
});

uploader.on('error', function(handler) {
    if (handler == "F_EXCEED_SIZE") {
	alert("文件不能超过2M");
    }
});

var cutter = new jQuery.UtrialAvatarCutter({
		//主图片所在容器ID
		content : "picture_original",
		
		//缩略图配置,ID:所在容器ID;width,height:缩略图大小
		purviews : [{id:"picture_200",width:160,height:160}],
		
		//选择器默认大小
		selector : {width:160,height:160}
	}
);

$(function() {
    ulewo.tag({
		id : "tag", contentClass : "tag-content", fun : function(index) {
			ulewo.iconType = index;
		}
    });
    cutter.init();
    //初始化系统头像
    initSysUserIcon();
    $("#save-btn").click(function(){
	saveUserIcon();
    });
    
    $(document).on("click",".system-user-icon-s",function(){
	checkSysUserIcon($(this));
    })
});
//初始化系统头像
function initSysUserIcon(){
	for(var i=1;i<=20;i++){
		var system_icon = $("<div class='system-user-icon-s' data='usericon"+i+".png'></div>").appendTo("#sys-user-icon");
		$("<img src="+ulewo.absolutePath+"/resource/images/defusericon/usericon"+i+".png>").appendTo(system_icon);
	}
	$('<div class="clear"></div>').appendTo("#sys-user-icon");
}
//点击系统头像
function checkSysUserIcon(curObj){
	$(".system-user-icon-s").css({"border":"2px solid #fff"});
	$("span.icon-check").remove();
	$("<span class='icon-check'></span>").appendTo(curObj);
	curObj.css({"border":"2px solid #1094FA"});
}

//保存头像
function saveUserIcon(){
    $(".error").remove();
    //上传头像
    if(ulewo.iconType==0){
    	
		var data = cutter.submit();
		var imgPath = data.s;
		if(imgPath==""){
		    ulewo.setError($("#save-btn"), "请先上传头像");
		    return;
		}
		var start = imgPath.lastIndexOf("upload");
		var imgSavePath = imgPath.substring(start,data.s.lastIndexOf("?"));
		
		ulewo.ajaxRequest({
		    url : ulewo.userIconUrl.saveCustomerUserIcon,
		    data : {
			"img" : imgSavePath,
			"x1" : data.x,
			"y1" : data.y,
			"width" : data.w,
			"height" : data.h,
			"date" : new Date()
		    },
		    fun : function(response) {
		    	 ulewo.tipMsg({
		    		type : "success", content : "保存成功", timeout : 1500
		    	  });
		    }
		});
    }else if(ulewo.iconType==1)
    {
    	//系统头像
		if($(".system-user-icon-s span.icon-check").length==0){
		    ulewo.setError($("#save-btn"), "请选择头像");
		    return;
		}
		var userIcon = $(".system-user-icon-s span.icon-check").parent().attr("data");
		ulewo.ajaxRequest({
		    url : ulewo.userIconUrl.saveSysUserIcon,
		    data : {userIcon:userIcon},
		    fun : function(response) {
		    	 ulewo.tipMsg({
		    		type : "success", content : "保存成功", timeout : 1500
		    	  });
		    }
		});
    }
    
}