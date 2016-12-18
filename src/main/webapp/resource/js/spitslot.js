ulewo.url = {
    loadSpitSlot : "loadSpitSlot", saveSpitSlot : "addSpitSlot.action"
}

var uploader = WebUploader.create({

    // 选完文件后，是否自动上传。
    auto : true,

    // swf文件路径
    swf : ulewo.absolutePath + '/resource/webuploader/Uploader.swf',

    // 文件接收服务端。
    server : ulewo.absolutePath + "/imageUpload.action",

    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick : {
	id : '#upload-image', multiple : false
    },
    // 只允许选择图片
    accept : {
	title : 'Images', extensions : 'gif,jpg,jpeg,bmp,png,GIF,JPG,JPEG,BMP,PNG', mimeTypes : 'image/*'
    }, duplicate : 1, // 不去重
    compress : false, // 压缩
    fileSingleSizeLimit : 2 * 1024 * 1024
});
uploader.on('fileQueued', function(file) {
    if ($("#image-con .imageItem").length > 2) {
	uploader.removeFile(file);
	ulewo.tipMsg({
	    type : "warn", content : "最多只能上传三张图片", timeout : 2000
	});
	return;
    }
    $("#image-con").show();
    $("#upload-image").hide();
    $(".send-spit-op .loading").show();
});

uploader.on('uploadSuccess', function(file, response) {
    if (response.responseCode == "200") {
	new ImageItem(response.savePath).appendTo($("#image-con"));
    }
    $("#upload-image").show();
    $(".send-spit-op .loading").hide();
});

uploader.on('error', function(handler) {
    if (handler == "F_EXCEED_SIZE") {
	alert("图片不能超过2M");
    }
});

function ImageItem(img) {
    var span = $('<span class="imageItem" data =' + img + ' ></span>');
    $('<img src="' + ulewo.absolutePath + '/upload/' + img + '" class="showimg">').appendTo(span);
    $('<a href="javascript:void(0)"><img src="' + ulewo.absolutePath + '/resource/images/icon-del.png"></a>').appendTo(span);
    return span;
}

$(function() {
    loadSpitSlot(1);
    /**
     * 删除图片
     */
    $(document).on("click", "#image-con span a", function() {
	$(this).parent().remove();
    });

    /**
     * 发布吐槽
     */
    $("#post-btn").click(function() {
	postSpitSlot();
    });

});

/**
 * 加载关注的人
 */
function showFocusDialog(obj) {
    var atDialog = ulewo.popDialog({
	width : 300, align : 'bottom left', obj : obj
    });
    ulewo.ajaxRequest({
	url : ulewo.url.loadFocus, showLoad : false, fun : function(res) {
	    var content = "";
	    var list = res.data;
	    var _len = list.length;
	    if (_len == 0) {
		content = "没有关注的用户";
	    } else {
		for (var i = 0, item; i < _len, item = list[i]; i++) {
		    content = content + "<a href='javascript:;' class='at_user'>" + item.friendUserName + "</a>";
		}
	    }
	    atDialog.content(content);
	}
    });
}

/**
 * 加载吐槽
 */
function loadSpitSlot(page) {
    ulewo.pageNo = page;
    $("#load-more").remove();
    $('<div id="loading"> <div class="loading-con"><img src="' + ulewo.absolutePath + '/resource/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo($("#spit-slot-list"));
    ulewo.ajaxRequest({
	url : ulewo.url.loadSpitSlot, data : {
	    pageNo : page
	}, showLoad : false, fun : function(res) {
	    $("#loading").remove();
	    var list = res.data.list;
	    var simplePage = res.data.page;
	    for (var i = 0, _len = list.length, data; i < _len, data = list[i]; i++) {
		new SpitSlotItem(data).appendTo($("#spit-slot-list"));
	    }
	    if (simplePage.pageTotal > simplePage.page) {
		$('<div id="load-more" class="load-more"><a href="javascript:;">⇓加载更多</a></div>').appendTo($("#spit-slot-list"));
	    }
	}
    });
}

/**
 * 提交吐槽
 */
function postSpitSlot() {
    if (ulewo.user.userId == "") {
	var url = ulewo.curUrl;
	url = encodeURI(url);
	document.location.href = "http://ucenter.bucuoa.com/login?redirect=" + url;
	return;
    }
    var content = $.trim($("#spit-slot-area").val());
    var imageArry = [];
    var imageItems = $("#image-con .imageItem");
    for (var i = 0, _len = imageItems.length; i < _len; i++) {
	imageArry.push(imageItems.eq(i).attr("data"));
    }
    if (content == "") {
	ulewo.tipMsg({
	    type : "warn", content : "吐槽内容不能为空", timeout : 1500
	});
	return;
    }
    if (content.length > 1000) {
	ulewo.tipMsg({
	    type : "warn", content : "吐槽内容不能超过1000", timeout : 1500
	});
	return;
    }
    ulewo.ajaxRequest({
	url : ulewo.url.saveSpitSlot, data : {
	    content : content, imageUrl : imageArry.join("|")
	}, fun : function(res) {
	    $(".spit-item").eq(0).before(new SpitSlotItem(res.data));
	    ulewo.tipMsg({
		type : "success", content : "2分已到碗里", timeout : 1500
	    });
	    clearForm();
	}
    });
}

function clearForm() {
    $("#spit-slot-area").val("");
    $("#image-con").empty();
}