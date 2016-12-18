ulewo.commentUrl = {
    loadComment : ulewo.absolutePath + "/loadComment", addComment : ulewo.absolutePath + "/addComment.action",
    doLike:ulewo.absolutePath+"/doLike.action",addCollection:ulewo.absolutePath+"/addCollection.action"
}
ulewo.contentMaxLength = 500;



$(function() {
    $('pre').each(function(i, block) {
	    hljs.highlightBlock(block);
    });
    //设置遮罩层宽度
    
    $(".no-login-shade").css({"width":(ulewo.editorWidth+30)+"px"});
    $(".login-panel").css({"left":(ulewo.editorWidth-$(".login-panel").width())/2+"px"});
    
    ulewo.commentPanel = $("#comment-list");
    
    //查看消息屏幕滚动到指定位置
    var url = window.location.href;
    //获取url后面#的信息 1_120  1是页码  120是评论ID
    var comment4messageIndex = url.lastIndexOf("#");
    var comment4PageNo = 1;
    if (comment4messageIndex != -1) {
	var comment4message = url.substring(url.lastIndexOf("#") + 1);
	var messageInfo = comment4message.split("_");
	if(messageInfo.length>0){
	    comment4PageNo = messageInfo[0];
	    ulewo.message4CommentId = messageInfo[1];
	}
    } 
    
    loadComments(comment4PageNo,ulewo.message4CommentId);
    
    $(document).on("click", ".op-at", function() {
	ulewo.showAtUser($(this)[0], $(this).parent().parent().find("textarea"));
    });

    $(document).on("click", ".op-emotion", function() {
	ulewo.showEmotion($(this)[0], $(this).parent().parent().find("textarea"));
    });

    // 点击回复
    $(document).on("click", ".comment-btn", function() {
	$(".comment-form").hide();
	var pid = $(this).attr("pid");
	var comment_id = $(this).attr("comment-id")
	var user_name = $("#user-name-" + comment_id).text();
	var comment_form = $("#comment-form-" + pid).show();
	var textarea = comment_form.find("textarea");
	if (pid != comment_id) {
	    textarea.val(textarea.val()).focus().val(textarea.val() + "@" + user_name + " ");
	} else {
	    textarea.val("").focus();
	}
    });
    // 发表回复
    $(document).on("click", ".post-comment-btn", function() {
	if(ulewo.user.userId==""){
	    ulewo.goLogin();
	    return;
	}
	postComment($(this));
    });

    // 发表一级回复
    $("#post-p-comment-btn").click(function() {
	if(ulewo.user.userId==""){
	    ulewo.goLogin();
	    return;
	}
	postPComment();
    });

    //初始化操作按钮
    initOpButtons();
    
    //操作按钮事件
    //发布评论
    $(document).on("click", ".op-post-comment", function() {
	if(ulewo.user.userId==""){
	    ulewo.goLogin();
	    return;
	}
	$('html,body').animate({scrollTop:$('#comment-content').offset().top}, 400);
	ulewo.ueditor.focus();
    });
    //查看评论
    $(document).on("click", ".op-go-comment", function() {
	$('html,body').animate({scrollTop:$('.comment-title').eq(0).offset().top}, 400);
    });
    //点赞
    $(document).on("click", ".op-do-like", function() {
	if(ulewo.user.userId==""){
	    ulewo.goLogin();
	    return;
	}
	ulewo.ajaxRequest({
		url : ulewo.commentUrl.doLike, data : {
		    articleId : ulewo.topicId,  articleType : ulewo.topicType
		}, fun : function(res) {
		   ulewo.tipMsg({
			type : "success", content : "点赞成功", timeout : 1500
		    });
		    var like_count = parseInt($("#like-count").text());
		    $("#like-count").text(like_count+1);
		    var like_users = $(".like-users")
		    var like = res.data;
		    if(like_users.children().length==0){
			$("<a href='"+ulewo.absolutePath+"/user/"+like.userId+"'>"+like.userName+"</a><span class='like-info'>很喜欢这篇文章</span>").appendTo(like_users);
		    }else{
			like_users.find("a").eq(0).before($("<a href='"+ulewo.absolutePath+"/user/"+like.userId+"'>"+like.userName+"</a>"));
		    }
		}
	});
    });
   
    //收藏
    $(document).on("click", ".op-do-collection", function() {
	if(ulewo.user.userId==""){
	    ulewo.goLogin();
	    return;
	}
	ulewo.ajaxRequest({
		url : ulewo.commentUrl.addCollection, data : {
		    articleId : ulewo.topicId, articleType : ulewo.topicType,title:ulewo.topicTitle
		}, fun : function(res) {
		   ulewo.tipMsg({
			type : "success", content : "收藏成功", timeout : 1500
		    });
		   var collection_count = parseInt($("#collection-count").text());
		    $("#collection-count").text(collection_count+1);
		}
	});
    });
    //滚动到顶部
    $(document).on("click", ".op-go-top", function() {
	$('html,body').animate({scrollTop:0}, 400);
    });
    
   
});

//初始化操作按钮
function initOpButtons(){
   var right_op_panel =  $("<div class='right-op-panel'></div>").appendTo($(".main").eq(0));
   $("<a href='javascript:;' class='op-post-comment'></a>").appendTo(right_op_panel);
   $("<a href='javascript:;' class='op-go-comment'></a>").appendTo(right_op_panel);
   $("<a href='javascript:;' class='op-do-like'></a>").appendTo(right_op_panel);
   $("<a href='javascript:;' class='op-do-collection'></a>").appendTo(right_op_panel);
   $("<a href='javascript:;' class='op-go-top'></a>").appendTo(right_op_panel);
   var margin = 20;
   var op_panel_width = right_op_panel.width();
   var op_panel_height = right_op_panel.height();
   var main_width = $(".main").width();
   var document_width = $(document).width();
   var document_height = $(window).height();
   var right = (document_width - main_width)/2-op_panel_width;
   var top = (document_height - op_panel_height)/2;
   right_op_panel.css({"right":(right-margin)+"px","top":top+"px"});
}

//发表一级回复
function postPComment() {
    if ($.trim($("#comment-content").val()) == "") {
	ulewo.setError($(".content_right textarea"), "回复内容不能为空");
	return;
    }
    $(".error").remove();
    var content = ulewo.ueditor == null ? $.trim($("#comment-content").val()) : ulewo.ueditor.getContent();
    ulewo.ajaxRequest({
	url : ulewo.commentUrl.addComment, data : {
	    richContent : content, articleId : ulewo.topicId, articleType : ulewo.topicType, pid : 0,pageNo:ulewo.commentPageNo
	}, fun : function(res) {
	    var data = res.data;
	    ulewo.ueditor == null ? $("#comment-content").val("") : ulewo.ueditor.setContent("");
	    ulewo.commentPanel.find(".no-data").remove();
	    var comment_items = ulewo.commentPanel.find(".comment-item");
	    if (comment_items.length == 0) {
		new CommentItem(data).appendTo(ulewo.commentPanel);
	    } else {
		comment_items.eq(0).before(new CommentItem(data));
	    }
	    
	    var comment_count = parseInt($("#comment-title-count").text());
	    $("#comment-title-count").text(comment_count+1);
	    $("#comment-count").text(comment_count+1);
	    
	}
    });
}

function postComment(curObj) {
    $(".error").remove();
    var pid = curObj.attr("pid");
    var comment_form = $("#comment-form-" + pid);
    var textarea = comment_form.find("textarea");
    var content = $.trim(textarea.val());
    if (content == "" || content.length > ulewo.contentMaxLength) {
	ulewo.setError(textarea, "回复内容不能为空，且不能超过1000字符");
	return;
    }
    ulewo.ajaxRequest({
	url : ulewo.commentUrl.addComment, data : {
	    content : content, articleId : ulewo.topicId, articleType : ulewo.topicType, pid : pid,pageNo:ulewo.commentPageNo
	}, fun : function(res) {
	    textarea.val("");
	    var data = res.data;
	    var child_panel = comment_form.parent().find(".child-panel").eq(0);
	    new CommentCon(data, pid,false).appendTo(child_panel);
	    
	    var comment_count = parseInt($("#comment-title-count").text());
	    $("#comment-title-count").text(comment_count+1);
	    $("#comment-count").text(comment_count+1);
	}
    });
}

function loadComments(page,message4CommentId) {
    ulewo.commentPageNo = page;
    $('<div id="loading"> <div class="loading-con"><img src="' + ulewo.absolutePath + '/resource/images/loading.gif"/><span>正在加载评论.......</span></div></div>').appendTo($("#comment-list"));
    ulewo.ajaxRequest({
	url : ulewo.commentUrl.loadComment, showLoad : false, data : {
	    articleId : ulewo.topicId, articleType : ulewo.topicType, pageNo : page
	}, fun : function(res) {
	    $("#comment-list").empty();
	    var list = res.data.list;
	    if (list.length == 0) {
		$("<div class='no-data'>没有评论，赶紧抢沙发</div>").appendTo($("#comment-list"));
	    }
	    var simplePage = res.data.page;
	    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
		if(d.id==ulewo.bestAnswerId){
		    continue;
		}
		new CommentItem(d).appendTo($("#comment-list"));
	    }
	    ulewo.pagination({
		pagePanelId : "pager", pageObj : simplePage, fun : loadComments
	    });
	    
	    $('pre').each(function(i, block) {
		    hljs.highlightBlock(block);
	    });
	    //屏幕滚动到制定位置
	    if(message4CommentId!=null&&message4CommentId>0){
		$('html,body').animate({scrollTop:$('#comment'+message4CommentId).offset().top}, 400);
	    }
	}
    });
}

function CommentItem(data) {
    var item = $("<div class='comment-item'></div>").appendTo(ulewo.commentPanel);
    new CommentCon(data, data.id,true).appendTo(item);
    var children = data.children;
    // 二级回复
    var item_child_panel = $("<div class='child-panel'></div>").appendTo(item);
    for (var i = 0, _len = children.length; i < _len; i++) {
	new CommentCon(children[i], data.id,false).appendTo(item_child_panel);
    }
    // 回复表单
    var comment_form = $("<div class='comment-form' id='comment-form-" + data.id + "'></div>").appendTo(item);
    $("<div class='subcomment-form-textarea'><textarea></textarea></div>").appendTo(comment_form);
    var op = $("<div class='comment-form-op'></div>").appendTo(comment_form);
    $("<a href='javascript:;' title='@好友' class='op-at'><i class='icon icon-op-at'></i></a>").appendTo(op);
    $("<a href='javascript:;' title='插入表情' class='op-emotion'><i class='icon icon-op-emotion'></i></a>").appendTo(op);
    $("<a href='javascript:;' class='btn post-comment-btn' pid='" + data.id + "'>回复</a>").appendTo(op);
    return item;
}

function CommentCon(data, pid,pComment) {
    var comment_panel = $("<div class='comment-panel' id='comment"+data.id+"'></div>");
    // 头像
    $("<div class='user-icon'><a href='" + ulewo.absolutePath + "/user/" + data.userId + "'><img src='" + ulewo.absolutePath + "/upload/" + data.userIcon + "'></a></div>").appendTo(comment_panel);
    var comment_con = $("<div class='comment-con'></div>").appendTo(comment_panel);
    $("<div class='clear'></div>").appendTo(comment_panel);
    // 内容
    $("<div class='content_detail'><a id='user-name-" + data.id + "' href='" + ulewo.absolutePath + "/user/" + data.userId + "'>" + data.userName + "</a>：<span>" + data.showContent + "</span></div>")
	    .appendTo(comment_con);
    // 时间，回复
   var op = $("<div class='time-op'><span class='time'>" + data.showCreateTime + "</span><a href='javascript:;'  pid='" + pid + "' comment-id = '" + data.id + "' class='comment-btn'><i class='icon icon-re'></i>回复</a></div>").appendTo(
	    comment_con);
   if(pComment&&ulewo.bestAnswerId==""&&ulewo.user.userId==ulewo.topicUserId){
       $("<a href='javascript:;' class='btn accept-btn' commentid="+pid+">采纳答案</a>").appendTo(op);
   }
    return comment_panel;
}
