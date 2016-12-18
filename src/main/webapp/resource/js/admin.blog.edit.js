var editor = UE.getEditor('content');
ulewo.url = {
    saveBlog : "save_blog.action",saveDraftsBlog:"save_drafts_blog.action"
}
ulewo.autoSveTime =180000;// 1000*60*3=180000 3分钟保存一次草稿
ulewo.topicType = {
    topicType0 : 0,// 普通帖
    topicType1 : 1
// 投票帖
}

var uploader = WebUploader.create({
    // 选完文件后，是否自动上传。
    auto : true,

    // swf文件路径
    swf : ulewo.absolutePath + '/resource/webuploader/Uploader.swf',

    // 文件接收服务端。
    server : ulewo.absolutePath + '/fileUpload.action',

    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick : {
	id : '#filePicker', multiple : false
    },
    // 只允许选择excel。
    duplicate : 1, // 不去重
    compress : false, // 压缩
    fileSingleSizeLimit : 2 * 1024 * 1024
});
uploader.on('fileQueued', function(file) {
    var name = file.name;
    var type = name.substring(name.lastIndexOf(".") + 1);
    if (type != "rar" && type != "zip") {
	uploader.removeFile(file);
	alert("文件只能是.rar或者.zip");
	return;
    }
    $("#filePicker").hide();
    $("#loading-upload").show();
});

uploader.on('uploadSuccess', function(file, response) {
    if (response.responseCode == "200") {
	$("<div>" + file.name + "&nbsp;&nbsp;<a href='javascript:deleteFile()'>删除</a></div>").appendTo($("#file-list"));
	$("#attached_file_name").val(file.name);
	$("#attached_file").val(response.savePath);
    }
    $("#file_upload").hide();
    $("#uploading").hide();
});

uploader.on('error', function(handler) {
    if (handler == "F_EXCEED_SIZE") {
	alert("文件不能超过2M");
    }
});

function deleteFile() {
    $.ajax({
	async : true, cache : false, type : 'POST', dataType : "json", data : {
	    fileName : $("#attached_file").val()
	}, url : ulewo.absolutePath + "/deleteFile.action",// 请求的action路径
	success : function(data) {
	    if (data.result = "200") {
		$("#filePicker").show();
		$("#loading-upload").hide();
		$("#file_upload").show();
		$("#attached_file_name").val("");
		$("#attached_file").val("");
		$("#file-list").empty();
	    } else {
		alert("删除失败");
	    }
	}
    });
};


$(function() {
    $("#post-btn").click(function() {
	postBlog();
    });
    
    var attached_file = $("#attached_file").val();
    var attached_file_name = $("#attached_file_name").val();
    if(attached_file!=""&&attached_file_name!=""){
	$("<div>" + attached_file_name + "&nbsp;&nbsp;<a href='javascript:deleteFile()'>删除</a></div>").appendTo($("#file-list"));
	$("#file_upload").hide();
	$("#uploading").hide();
    }
    var blogId = $("#blog-id").val();
	  setInterval(function(){
	      	    $("#richContent").val(editor.getContent());
		    ulewo.ajaxRequest({
			url : ulewo.url.saveDraftsBlog,showLoad:false, data : $("#post-form").serialize(), fun : function(res) {
			    $("#blog-id").val(res.data);
			    ulewo.tipMsg({
				type : "success", content : "自动保存草稿成功",timeout:1500
			    });
			}
		    });
	    },ulewo.autoSveTime)
})

/**
 * 发表主题
 */
function postBlog() {
    var form = $("#post-form");
    var result = ulewo.checkForm(form);
    if (!editor.hasContents()) {
	ulewo.setError($("#content"), "内容不能为空");
	result = false;
    }
    if (!result) {
	return;
    }
    $("#richContent").val(editor.getContent());
    ulewo.ajaxRequest({
	url : ulewo.url.saveBlog, data : $("#post-form").serialize(), fun : function(res) {
	    ulewo.tipMsg({
		type : "success", content : "发布成功，正在跳转到详情页......"
	    });
	    document.location.href = ulewo.absolutePath + "/user/"+ulewo.user.userId+"/blog/" + res.data;
	}
    });
}
