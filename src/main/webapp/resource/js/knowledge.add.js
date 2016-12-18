var editor = UE.getEditor('content');
ulewo.url = {
    addTopic : "add_knowledge.action", loadCategories : "loadCategories"
}

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

deleteFile = function() {
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
    initCategory();
    
    $("#post-btn").click(function() {
	addTopic();
    });

    $(document).on("change", "#pCategoryId", function() {
	var pCategoryId = $(this).val();
	var list = ulewo.categories;
	var children = [];
	for (var i = 0, _len = list.length; i < _len; i++) {
	    if (list[i].categoryId == pCategoryId) {
		children = list[i].children;
		break;
	    }
	}
	$("#categoryId").empty();
	$("<option>请选择二级分类</option>").appendTo($("#categoryId"));
	for (var i = 0, _len = children.length; i < _len; i++) {
	    var item = children[i];
	    $("<option value=" + item.categoryId + ">" + item.name + "</option>").appendTo($("#categoryId"));
	}
    });
})

function initCategory() {
    ulewo.ajaxRequest({
	url : ulewo.url.loadCategories, showLoad : false, fun : function(res) {
	    var list = res.data;
	    ulewo.categories = list;
	    for (var i = 0, _len = list.length; i < _len; i++) {
		var item = list[i];
		$("<option value=" + item.categoryId + ">" + item.name + "</option>").appendTo($("#pCategoryId"));
	    }
	}
    });
}

/**
 * 发表主题
 */
function addTopic() {
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
	url : ulewo.url.addTopic, data : $("#post-form").serialize(), fun : function(res) {
	    ulewo.tipMsg({
		type : "success", content : "发布成功，知识已经提交审核，审核通过后其他人才能看到",timeout:4000,fun:function(){
		    document.location.href = ulewo.absolutePath + "/knowledge/" + res.data;
		}
	    });
	}
    });
}
