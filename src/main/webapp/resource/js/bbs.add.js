var editor = UE.getEditor('content');
ulewo.url = {
    addTopic : "add_topic.action", loadCategories : "loadCategories"
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
    $(".topicType").click(function() {
	topicTypeChoose($(this));
    });

    initCategory();

    /**
     * 添加选项
     */
    $("#addVote").click(function() {
	var index = $("#vote").find("input[name='voteTitle']").length;
	if (index >= 10) {
	    ulewo.tipMsg({
		type : "warn", content : "最多只能添加10个选项", timeout : 1500
	    });
	    return;
	}
	new voteItem().appendTo($("#vote-input-list"));
    });

    $(document).on("click", ".del-vote", function() {
	$(this).parent().remove();
    });

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
	$("<option value=''>请选择子板块</option>").appendTo($("#categoryId"));
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
 * 主题类型选择
 * 
 * @param curObj
 */
function topicTypeChoose(curObj) {
    var topicType = curObj.val();
    if (ulewo.topicType.topicType0 == topicType) {
	$("#vote").hide();
    } else if (ulewo.topicType.topicType1 == topicType) {
	$("#vote").show();
    }
}

/**
 * 发表主题
 */
function addTopic() {
    var form = $("#post-form");
    form.find(".error").remove();
    form.find("input").removeClass("warn");
    var title = $.trim(form.find("input[name='title']").val());
    var pCategoryId = $("#pCategoryId").val();
    var categoryId = $("#categoryId").val();
    var topicType = $.trim(form.find("input[name='topicType']:checked").val());
    var result = true;
    debugger;
    if (ulewo.topicType.topicType1 == topicType && !ulewo.checkForm($("#vote"))) {// 投票帖
	result = false;
    }
    if (title == "") {
	ulewo.setError(form.find("input[name='title']"), "帖子内容不能为空");
	result = false;
    }
    if (pCategoryId == "") {
	ulewo.setError($("#pCategoryId"), "请选择一级板块");
	result = false;
    }
    if (categoryId == "") {
	ulewo.setError($("#categoryId"), "请选择子板块");
	result = false;
    }
    var topicType = $.trim(form.find("input[name='topicType']:checked").val());

    if (ulewo.topicType.topicType1 == topicType && !ulewo.checkForm($("#vote"))) {// 投票帖
	result = false;
    }
    if (!editor.hasContents()) {
	ulewo.setError($("#content"), "帖子内容不能为空");
	result = false;
    }
    var mark = $("#mark").val();
    if (!ulewo.regs().number.reg.test(mark)) {
	ulewo.setError($("#mark"), "积分只能是数字");
	result = false;
    }
    if (!result) {
	return;
    }
    $("#richContent").val(editor.getContent());
    ulewo.ajaxRequest({
	url : ulewo.url.addTopic, data : $("#post-form").serialize(), fun : function(res) {
	    ulewo.tipMsg({
		type : "success", content : "发布成功，正在跳转到详情页......"
	    });
	    document.location.href = ulewo.absolutePath + "/bbs/" + res.data;
	}
    });
}

function voteItem() {
    var item = $("<div class='vote-input'><input checkData='{rq:true,msg:\"选项内容不能能为空\"}' placeholder='请输入选项内容' class='title-input' maxlength='200' type='text' name='voteTitle'>&nbsp;&nbsp;<a href='javascript:;'  class='icon icon-del del-vote'></a></div>")
    return item;
}