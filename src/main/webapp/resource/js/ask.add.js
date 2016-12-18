var editor = UE.getEditor('content');
ulewo.url = {
    addTopic : "add_ask.action", loadCategories : "loadCategories"
}

$(function() {
   // initCategory();

    $("#post-btn").click(function() {
	addTopic();
    });

    /**
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
	$("<option>请选择子板块</option>").appendTo($("#categoryId"));
	for (var i = 0, _len = children.length; i < _len; i++) {
	    var item = children[i];
	    $("<option value=" + item.categoryId + ">" + item.name + "</option>").appendTo($("#categoryId"));
	}
    });
    
    */
})

/***
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
**/

/**
 * 发表主题
 */
function addTopic() {
    var form = $("#post-form");
    var result = ulewo.checkForm(form);
    if (!editor.hasContents()) {
	ulewo.setError($("#content"), "帖子内容不能为空");
	result = false;
    }
    var inputMark = $.trim($("input[name='mark']").val());
    var mark = parseInt($("#my-mark").text());
    if (inputMark > mark) {
	ulewo.setError($("input[name='mark']"), "你的积分不够");
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
	    document.location.href = ulewo.absolutePath + "/ask/" + res.data;
	}
    });
}
