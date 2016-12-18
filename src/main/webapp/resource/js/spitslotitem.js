ulewo.max_comment_count = 5;
ulewo.spiturl = {
    saveSpitSlotComment : ulewo.absolutePath+"/addSpitSlotComment.action", loadSpitSlotComment :ulewo.absolutePath+"/loadSpitSlotComment", loadLike : ulewo.absolutePath+"/getLikeList", doLike : ulewo.absolutePath+"/doLike.action"
};
ulewo.likeType = "S";
ulewo.imageServer ="http://static.bucuoa.com"

$(document).on("click", ".load-more-comment", function() {
    loadMoreComment($(this));
});
$(document).on("click", ".like-info", function() {
    showLike($(this));
});
$(document).on("click", ".comment-info", function() {
    showComment($(this));
});
$(document).on("click", ".show-comment-post", function() {
    if (ulewo.user.userId == "") {
	    ulewo.goLogin();
	    return;
    }
    showCommentPost($(this));
});
$(document).on("click", ".comment-at", function() {
    showCommentPost4At($(this));
});
$(document).on("click", ".do-like", function() {
    if (ulewo.user.userId == "") {
	    ulewo.goLogin();
	    return;
    } 
    doLike($(this));
});

$(document).on("click", "#load-more", function() {
    loadSpitSlot(ulewo.pageNo + 1);
});

$(document).on("click", "#op-at", function() {
    ulewo.showAtUser($(this)[0], $("#spit-slot-area"));
});
$(document).on("click", "#op-emotion", function() {
    ulewo.showEmotion($(this)[0], $("#spit-slot-area"));
});

$(document).on("click", ".op-at", function() {
    if (ulewo.user.userId == "") {
	    ulewo.goLogin();
	    return;
    }
    ulewo.showAtUser($(this)[0],$(this).parent().parent().find("textarea"));
});
$(document).on("click", ".op-emotion", function() {
    ulewo.showEmotion($(this)[0],$(this).parent().parent().find("textarea"));
});
$(document).on("click", ".comment-post-btn", function() {
    if (ulewo.user.userId == "") {
	    ulewo.goLogin();
	    return;
    }
    postComment($(this));
});

function SpitSlotItem(data,detail) {
    var item = $("<div class='spit-item'></div>");
    // 头像
    $("<div class='spit-item-user-icon'><a href='" + ulewo.absolutePath + "/user/" + data.userId + "'><img src='" + ulewo.imageServer + "/upload/" + data.userIcon + "'/></a></div>").appendTo(item);
    // 吐槽主体
    var item_body = $("<div class='spit-item-body'></div>").appendTo(item);
    $("<div class='clear'></div>").appendTo(item);
    // 内容
    $("<div class='spit-item-body-content'>" + data.showContent + "</div>").appendTo(item_body);
    // 图片
    if (data.imageUrlSmall != null && data.imageUrlSmall != "") {
	var item_image = $("<div class='spit-item-body-img image-thum'></div>").appendTo(item_body);
	var imageSmallArray = data.imageUrlSmall.split("|");
	for (var i = 0, _len = imageSmallArray.length, img; i < _len, img = imageSmallArray[i]; i++) {
	    if (img !== "") {
		$("<a href='javascript:;' class='img'><img src='" + ulewo.absolutePath + "/upload/" + img + "'/></a>").appendTo(item_image);
	    }
	}
	$('<div class="clear"></div>').appendTo(item_image);
    }
    // 音乐
    if (data.musicUrl != null && data.musicUrl != '') {
	$('<audio src="' + data.musicUrl + '" controls="controls">你的浏览器不支持html5音乐播放器 </audio>').appendTo(item_body);
    }
    // 吐槽信息
    var spit_info = $("<div class='spit-item-body-info'></div>").appendTo(item_body);
    $("<a href='" + ulewo.absolutePath + "/user/" + data.userId + "' class='user-info'><i class='icon icon-user-name'></i><span>" + data.userName + "</span></a>").appendTo(spit_info);
    var comment_info = $("<a href='javascript:;' class='comment-info'><i class='icon icon-comment' ></i>评论：<span>" + data.commentCount + "</span></a>").appendTo(spit_info);
    $("<a href='javascript:;' class='like-info' spitSlotId =" + data.id + "><i class='icon icon-like' ></i>喜欢：<span>" + data.likeCount + "</span></a>").appendTo(spit_info);
    $("<span class='spit-create-time'>发布于：" + data.createTime + "</span>").appendTo(spit_info);
    // 吐槽评论
    if (data.commentCount > 0) {
	// 评论的箭头
	$("<i class='icon icon-comment-direction'></i>").appendTo(comment_info);
	var comment_body = $("<div class='spit-item-comment-body'></div>").appendTo(item_body);
	var comments = data.commentList;
	var border_style = "";
	for (var i = 0, _len = comments.length, comment; i < _len, comment = comments[i]; i++) {
	    if (i == _len - 1) {
		border_style = "style='border-bottom:none;'";
	    }
	    new CommentItem(comment, border_style).appendTo(comment_body);
	}
	if (data.commentCount > ulewo.max_comment_count) {
	    $("<div  class='load-more load-more-comment' spitSlotId =" + data.id + "><a href='javascript:;'>⇓加载更多</a></div>").appendTo(comment_body);
	}
    }
    // 点赞 评论
    var like_comment = $("<div class='like_comment'></div>").appendTo(item_body);
    $("<a href='javascript:;' class='do-like' spitSlotId =" + data.id + "><i class='icon icon-op-like'></i><span>赞</span></a>").appendTo(like_comment);
    $("<a href='javascript:;' class='show-comment-post'><i class='icon icon-op-comment'></i><span>评论</span></a>").appendTo(like_comment);
    if(!detail){
	 $("<a href='" + ulewo.absolutePath + "/user/" + data.userId + "/spitslot/"+data.id+"' class='show-detail'>查看详情</a>").appendTo(like_comment);
    }
   
    // 评论
    var comment_form = $("<div class='comment_form'></div>").appendTo(item_body);
    var comment_textarea = $("<textarea></textarea>").appendTo(comment_form);
    var comment_form_op = $("<div class='comment_form_op'></div>").appendTo(comment_form);
    $("<a href='javascript:;' title='@好友' class='op-at'><i class='icon icon-op-at'></i></a>").appendTo(comment_form_op);
    //.click(function() {
//	ulewo.showAtUser($(this)[0], comment_textarea);
   // });
    $("<a href='javascript:;' title='插入表情' class='op-emotion'><i class='icon icon-op-emotion'></i></a>").appendTo(comment_form_op);
    //.click(function() {
	//ulewo.showEmotion($(this)[0], comment_textarea);
   // });
    $("<a href='javascript:;' class='btn comment-post-btn' spitSlotId=" + data.id + ">回&nbsp;&nbsp;复</a>").appendTo(comment_form_op);
    //.click(function() {
//	postComment($(this));
   // });
    return item;
}

function CommentItem(comment, border_style) {
    var comment_item = $("<div class='comment-item' " + border_style + "></div>")
    // 评论头像
    $("<div class='comment-user-icon'><a href='" + ulewo.absolutePath + "/user/" + comment.userId + "'><img src='" + ulewo.absolutePath + "/upload/" + comment.userIcon + "'/></a></div>").appendTo(
	    comment_item);
    // 评论主体
    var comment_main = $("<div class='comment-main'></div>").appendTo(comment_item);
    $("<div class='clear'></div>").appendTo(comment_item);
    // 评论内容
    $("<div class='comment-content'><a href='" + ulewo.absolutePath + "/user/" + comment.userId + "'>" + comment.userName + "</a>：" + comment.showContent + "</div>").appendTo(comment_main);
    // 评论回复
    var comment_op = $("<div class='comment-op'><span>" + comment.createTime + "</span></div>").appendTo(comment_main);
    $("<a href='javascript:;' username='" + comment.userName + "' class='comment-at'>回复</a>").appendTo(comment_op);
    return comment_item;
}

// 点赞
function doLike(curObj) {
    var spitSlotId = curObj.attr("spitSlotId");
    var spit_item_body = curObj.parent().parent();
    ulewo.ajaxRequest({
	url : ulewo.spiturl.doLike, data : {
	    articleId : spitSlotId, articleType : ulewo.likeType
	}, showLoad : false, fun : function(res) {
	    var like_span = curObj.parent().prevAll(".spit-item-body-info").find("a.like-info span");
	    like_span.text(parseInt(like_span.text()) + 1);
	    var like = res.data;
	    var spit_item_like_body = spit_item_body.find(".spit-item-like-body");
	    if (spit_item_like_body.length == 0) {
		spit_item_like_body = $("<div class='spit-item-like-body'></div>");
		curObj.parent().before(spit_item_like_body);
	    }
	    $("<a href='" + ulewo.absolutePath + "/user/" + like.userId + "'><img src='" + ulewo.absolutePath + "/upload/" + like.userIcon + "'/></a>").appendTo(spit_item_like_body)
	    ulewo.tipMsg({
		type : "success", content : "点赞成功", timeout : 1500
	    });
	}
    });
}
// 回复
function showCommentPost4At(curObj) {
    var spit_item_body = curObj.parent().parent().parent().parent().parent();
    var comment_form = spit_item_body.find(".comment_form");
    comment_form.show();
    var textarea = comment_form.find("textarea");
    textarea.val(textarea.val()).focus().val(textarea.val() + "@" + curObj.attr("username") + "  ");
}

// 加载更多评论
function loadMoreComment(curObj) {
    var comment_body = curObj.parent();
    $('<div id="loading"> <div class="loading-con"><img src="' + ulewo.absolutePath + '/resource/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo(comment_body);
    var spitSlotId = curObj.attr("spitSlotId");
    ulewo.ajaxRequest({
	url : ulewo.spiturl.loadSpitSlotComment, data : {
	    spitSlotId : spitSlotId
	}, showLoad : false, fun : function(res) {
	    comment_body.empty();
	    var list = res.data;
	    var border_style = "";
	    for (var i = 0, _len = list.length, data; i < _len, data = list[i]; i++) {
		if (i == _len - 1) {
		    border_style = "style='border-bottom:none;'";
		}
		new CommentItem(data, border_style).appendTo(comment_body);
	    }
	}
    });
}

// 查看喜欢的人
function showLike(curObj) {
    var likeCount = parseInt(curObj.find("span").text());
    if (likeCount > 0) {
	var spit_item_body = curObj.parent().parent();
	spit_item_body.find(".spit-item-comment-body").hide();
	curObj.parent().find(".icon-comment-direction").remove();
	$("<i class='icon icon-comment-direction'></i>").appendTo(curObj);
	var spit_item_like_body = spit_item_body.find(".spit-item-like-body");
	if (spit_item_like_body.length == 0) {
	    spit_item_like_body = $("<div class='spit-item-like-body'></div>");
	    curObj.parent().after(spit_item_like_body);
	    $('<div id="loading"> <div class="loading-con"><img src="' + ulewo.absolutePath + '/resource/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo(spit_item_like_body);
	    var spitSlotId = curObj.attr("spitSlotId");
	    ulewo.ajaxRequest({
		url : ulewo.spiturl.loadLike, data : {
		    articleId : spitSlotId, articleType : ulewo.likeType
		}, showLoad : false, fun : function(res) {
		    spit_item_like_body.empty();
		    var list = res.data;
		    for (var i = 0, _len = list.length, like; i < _len, like = list[i]; i++) {
			$("<a href='" + ulewo.absolutePath + "/user/" + like.userId + "'><img src='" + ulewo.absolutePath + "/upload/" + like.userIcon + "'/></a>").appendTo(spit_item_like_body)
		    }
		}
	    });
	}
	spit_item_like_body.show();
    }
}

// 查看评论
function showComment(curObj) {
    var commentCount = parseInt(curObj.find("span").text());
    if (commentCount > 0) {
	var spit_item_body = curObj.parent().parent();
	spit_item_body.find(".spit-item-like-body").hide();
	spit_item_body.find(".spit-item-comment-body").show();
	curObj.parent().find(".icon-comment-direction").remove();
	$("<i class='icon icon-comment-direction'></i>").appendTo(curObj);
    }
}

function showCommentPost(curObj) {
    $(".spit-item .spit-item-body .comment_form").hide();
    var comment_form = curObj.parent().parent().find(".comment_form")
    comment_form.show();
    comment_form.find("textarea").focus();
}

// 发表评论
function postComment(curObj) {
    var spit_item_body = curObj.parent().parent().parent();
    var content = $.trim(spit_item_body.find("textarea").val());
    var spitSlotId = curObj.attr("spitSlotId");
    if (content == "") {
	ulewo.tipMsg({
	    type : "warn", content : "评论内容不能为空", timeout : 1500
	});
	return;
    }
    if (content.length > 1000) {
	ulewo.tipMsg({
	    type : "warn", content : "评论内容不能超过1000", timeout : 1500
	});
	return;
    }
    ulewo.ajaxRequest({
	url : ulewo.spiturl.saveSpitSlotComment, data : {
	    content : content, spitSlotId : spitSlotId
	}, fun : function(res) {
	    var spit_item_comment_body = spit_item_body.find(".spit-item-comment-body");
	    // 没有评论
	    if (spit_item_comment_body.length == 0) {
		// 插入评论body节点
		spit_item_comment_body = $("<div class='spit-item-comment-body'></div>");
		// 将节点插入到吐槽信息栏后面
		spit_item_body.find(".spit-item-body-info").after(spit_item_comment_body);
		new CommentItem(res.data, "style='border-bottom:none;'").appendTo(spit_item_comment_body);
	    } else {
		// 有评论插入到最前面
		spit_item_comment_body.find(".comment-item").eq(0).before(new CommentItem(res.data));
	    }
	   
	    spit_item_comment_body.show();
	    // 插入评论节点
	    // 插入箭头信息
	    spit_item_body.find(".spit-item-body-info a .icon-comment-direction").remove();
	    spit_item_body.find("spit-item-like-body").hide();
	    $('<i class="icon icon-comment-direction"></i>').appendTo(spit_item_body.find("a.comment-info"));
	    spit_item_comment_body.parent().find(".spit-item-like-body").hide();
	    // 更新评论数量
	    var countSpan = spit_item_body.find("a.comment-info span");
	    countSpan.text(parseInt(countSpan.text()) + 1);
	    ulewo.tipMsg({
		type : "success", content : "1分已到碗里", timeout : 1500
	    });
	    spit_item_body.find("textarea").val("");
	}
    });
}