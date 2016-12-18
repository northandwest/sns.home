ulewo.tags=["spitslot","knowledge","blog","fans","focus"]
ulewo.centerUrl={
    loadSpitSlot:ulewo.absolutePath+"/user/loadSpitSlot",
    loadTopic:ulewo.absolutePath+"/user/loadTopic",
    loadAsk:ulewo.absolutePath+"/user/loadAsk",
    loadKnowledge:ulewo.absolutePath+"/user/loadKnowledge",
    loadFans:ulewo.absolutePath+"/user/loadFans",
    loadFoucs:ulewo.absolutePath+"/user/loadFocus"
}
$(function(){
    ulewo.tag({
	id : "tag", contentClass : "tag-content", fun : function(index) {
	    var type = ulewo.tags[index];
	    var url = window.location.href;
	    url = url.substring(0, url.lastIndexOf("#"));
	    window.location.href = url + "#" + type;
	    dispatchLoad(type);
	}
    });
    initTagContent();
})
//初始化tag内容
function initTagContent(){
    var url = window.location.href;
    var indexType = url.lastIndexOf("#");
    if (indexType == -1) {
    	type = "spitslot";
    } else {
    	type = url.substring(url.lastIndexOf("#") + 1);
    }
    $("ul.tag li").removeClass("active");
    $("ul.tag li[type='" + type + "']").addClass("active");
    dispatchLoad(type);
}

function dispatchLoad(type) {
    switch (type) {
    case "spitslot":// 吐槽
	if($(".tag-content").eq(0).children().length==0){
	    loadSpitSlot(1);
	}
	break;
    case "topic":// 主题
	$(".tag-content").eq(1).show();
	if($("#topic-content").children().length==0){
	    loadTopic(1);
	}
	break;
    case "ask":// 问答
	$(".tag-content").eq(2).show();
	if($("#ask-content").children().length==0){
	    loadAsk(1);
	}
	break;
    case "knowledge":// 知识库
	$(".tag-content").eq(3).show();
	if($("#knowledge-content").children().length==0){
	    loadKnowledge(1);
	}
	break;
    case "fans":// 粉丝
	$(".tag-content").eq(5).show();
	if($("#fans-content").children().length==0){
	    loadFans();
	}
	break;
    case "focus":// 关注的人
	$(".tag-content").eq(6).show();
	if($("#focus-content").children().length==0){
	    loadFocus();
	}
	break;
    default:
	break;
    }
}

//加载吐槽
function loadSpitSlot(page){
    var tag_content = $(".tag-content").eq(0);
    ulewo.pageNo = page;
    $("#load-more").remove();
    $('<div id="loading"> <div class="loading-con"><img src="' + ulewo.absolutePath + '/resource/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo(tag_content);
    ulewo.ajaxRequest({
	url : ulewo.centerUrl.loadSpitSlot, data : {
	    pageNo : page,
	    userId:ulewo.usercenter.userId
	}, showLoad : false, fun : function(res) {
	    $("#loading").remove();
	    var list = res.data.list;
	    var simplePage = res.data.page;
	    if(simplePage.countTotal==0){
		$("<span class='no-data'>没有吐槽</span>").appendTo(tag_content);
	    }else{
		for (var i = 0, _len = list.length, data; i < _len, data = list[i]; i++) {
			new SpitSlotItem(data).appendTo(tag_content);
		    }
		    if (simplePage.pageTotal > simplePage.page) {
			$('<div id="load-more" class="load-more"><a href="javascript:;">⇓加载更多</a></div>').appendTo(tag_content);
		    }
	    }
	    
	}
    });
}
/************主题******************/
function topicItem(data){
    var topic_item = $("<div class='topic-item'></div>");
    var topic_item_info = $("<div class='topic-item-info'></div>").appendTo(topic_item);
    var topic_item_title = $("<div class='topic-item-title'></div>").appendTo(topic_item_info);
    if(data.grade>0){
	 $('<span class="ding">置顶</span>').appendTo(topic_item_title);
    }
    if(data.essence=="1"){
	 $('<span class="jing">精华</span>').appendTo(topic_item_title);
    }
    if(data.topicType.type==1){
	 $('<span class="vote">投票</span>').appendTo(topic_item_title);
    }
    $("<a href='"+ulewo.absolutePath+"/bbs/"+data.topicId+"' target='_blank' class='title'>"+data.title+"</a>").appendTo(topic_item_title);
    $('<span class="time">'+data.showCreateTime+'</span>').appendTo(topic_item_title);
    $('<a class="topic-cate-tag"  target="_blank"  href="'+ulewo.absolutePath+'/bbs/sub_board/'+data.categoryId+'">'+data.categoryName+'</a>').appendTo(topic_item_title);
    $('<div class="topic-item-summary">'+data.summary+'</div>').appendTo(topic_item_info);
    if(data.topicImageThumArray!=null){
      var topic_item_images =  $('<div class="image-thum topic-item-images"></div>').appendTo(topic_item_info);
       for(var i=0,_len=data.topicImageThumArray.length;i<_len;i++){
	   $('<a href="javascript:;" class="img"><img src="'+ulewo.absolutePath+'/upload/'+data.topicImageThumArray[i]+'"></a>').appendTo(topic_item_images);
	   if(i==2){
	       break;
	   }
       }
       $('<div class="clear"></div>').appendTo(topic_item_images);
    }
    var topic_count_info = $('<div class="topic-count-info"></div>').appendTo(topic_item_info);
    $('<span class="count"><i class="icon icon-read"></i><span>阅读：'+data.readCount+'</span></span>').appendTo(topic_count_info);
    $('<span class="count"><i class="icon icon-comment"></i><span>评论：'+data.commentCount+'</span></span>').appendTo(topic_count_info);
    $('<span class="count"><i class="icon icon-like"></i><span>喜欢：'+data.likeCount+'</span></span>').appendTo(topic_count_info);
    $('<span class="count"><i class="icon icon-collection"></i><span>收藏：'+data.collectionCount+'</span></span>').appendTo(topic_count_info);
    return topic_item;
}

function loadTopic(page){
    var topic_content = $("#topic-content");
    $('<div id="loading"> <div class="loading-con"><img src="' + ulewo.absolutePath + '/resource/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo(topic_content);
    ulewo.ajaxRequest({
	async : true, url : ulewo.centerUrl.loadTopic, showLoad : false, data : {
	    pageNo : page,
	    userId:ulewo.usercenter.userId
	}, fun : function(res) {
	  
	    $("#loading").remove();
	    $("#topic-content").empty();
	    var list = res.data.list;
	    if (list.length == 0) {
		$("<span class='no-data'>没有帖子</span>").appendTo(topic_content);
	    }
	    var simplePage = res.data.page;
	    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
		new topicItem(d).appendTo(topic_content);
	    }
	   
	    if(simplePage.pageTotal>1){
		 ulewo.pagination({
			pagePanelId : "topic-pager-top", pageObj : simplePage, fun : loadTopic
		    });
		    ulewo.pagination({
			pagePanelId : "topic-pager-footer", pageObj : simplePage, fun : loadTopic
		 });
	    }
	   
	}
    });
}


/************问答******************/
function askItem(data){
    var topic_item = $("<div class='topic-item'></div>");
    var topic_item_info = $("<div class='topic-item-info'></div>").appendTo(topic_item);
    var topic_item_title = $("<div class='topic-item-title'></div>").appendTo(topic_item_info);
    $("<a href='"+ulewo.absolutePath+"/ask/"+data.askId+"' target='_blank' class='title'>"+data.title+"</a>").appendTo(topic_item_title);
    $('<span class="time">'+data.showCreateTime+'</span>').appendTo(topic_item_title);
    $('<div class="topic-item-summary">'+data.summary+'</div>').appendTo(topic_item_info);
    if(data.topicImageThumArray!=null){
      var topic_item_images =  $('<div class="image-thum topic-item-images"></div>').appendTo(topic_item_info);
       for(var i=0,_len=data.topicImageThumArray.length;i<_len;i++){
	   $('<a href="javascript:;" class="img"><img src="'+ulewo.absolutePath+'/upload/'+data.topicImageThumArray[i]+'"></a>').appendTo(topic_item_images);
	   if(i==2){
	       break;
	   }
       }
       $('<div class="clear"></div>').appendTo(topic_item_images);
    }
    var topic_count_info = $('<div class="topic-count-info"></div>').appendTo(topic_item_info);
    $('<span class="count"><i class="icon icon-mark"></i><span>赏金：'+data.mark+'</span></span>').appendTo(topic_count_info);
    $('<span class="count"><i class="icon icon-read"></i><span>阅读：'+data.readCount+'</span></span>').appendTo(topic_count_info);
    $('<span class="count"><i class="icon icon-comment"></i><span>评论：'+data.commentCount+'</span></span>').appendTo(topic_count_info);
    $('<span class="count"><i class="icon icon-like"></i><span>喜欢：'+data.likeCount+'</span></span>').appendTo(topic_count_info);
    $('<span class="count"><i class="icon icon-collection"></i><span>收藏：'+data.collectionCount+'</span></span>').appendTo(topic_count_info);
    return topic_item;
}

function loadAsk(page){
    var topic_content = $("#ask-content");
    $('<div id="loading"> <div class="loading-con"><img src="' + ulewo.absolutePath + '/resource/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo(topic_content);
    ulewo.ajaxRequest({
	async : true, url : ulewo.centerUrl.loadAsk, showLoad : false, data : {
	    pageNo : page,
	    userId:ulewo.usercenter.userId
	}, fun : function(res) {
	  
	    $("#loading").remove();
	    $("#ask-content").empty();
	    var list = res.data.list;
	    if (list.length == 0) {
		$("<span class='no-data'>没有问答</span>").appendTo(topic_content);
	    }
	    var simplePage = res.data.page;
	    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
		new askItem(d).appendTo(topic_content);
	    }
	   
	    if(simplePage.pageTotal>1){
        	    ulewo.pagination({
        		pagePanelId : "ask-pager-top", pageObj : simplePage, fun : loadTopic
        	    });
        	    ulewo.pagination({
        		pagePanelId : "ask-pager-footer", pageObj : simplePage, fun : loadTopic
        	    });
	    }
	}
    });
}


/************知识库******************/
function knowledgeItem(data){
    var topic_item = $("<div class='topic-item'></div>");
    var topic_item_info = $("<div class='topic-item-info'></div>").appendTo(topic_item);
    var topic_item_title = $("<div class='topic-item-title'></div>").appendTo(topic_item_info);
    $("<a href='"+ulewo.absolutePath+"/knowledge/"+data.topicId+"' target='_blank' class='title'>"+data.title+"</a>").appendTo(topic_item_title);
    $('<span class="time">'+data.showCreateTime+'</span>').appendTo(topic_item_title);
    $('<a class="topic-cate-tag" target="_blank"  href="'+ulewo.absolutePath+'/knowledge/categoryId/'+data.categoryId+'">'+data.categoryName+'</a>').appendTo(topic_item_title);
    $('<div class="topic-item-summary">'+data.summary+'</div>').appendTo(topic_item_info);
    if(data.topicImageThumArray!=null){
      var topic_item_images =  $('<div class="image-thum topic-item-images"></div>').appendTo(topic_item_info);
       for(var i=0,_len=data.topicImageThumArray.length;i<_len;i++){
	   $('<a href="javascript:;" class="img"><img src="//static.bucuoa.com/upload/'+data.topicImageThumArray[i]+'"></a>').appendTo(topic_item_images);
	   if(i==2){
	       break;
	   }
       }
       $('<div class="clear"></div>').appendTo(topic_item_images);
    }
    var topic_count_info = $('<div class="topic-count-info"></div>').appendTo(topic_item_info);
    $('<span class="count"><i class="icon icon-read"></i><span>阅读：'+data.readCount+'</span></span>').appendTo(topic_count_info);
    $('<span class="count"><i class="icon icon-comment"></i><span>评论：'+data.commentCount+'</span></span>').appendTo(topic_count_info);
    $('<span class="count"><i class="icon icon-like"></i><span>喜欢：'+data.likeCount+'</span></span>').appendTo(topic_count_info);
    $('<span class="count"><i class="icon icon-collection"></i><span>收藏：'+data.collectionCount+'</span></span>').appendTo(topic_count_info);
    return topic_item;
}

function loadKnowledge(page){
    var topic_content = $("#knowledge-content");
    $('<div id="loading"> <div class="loading-con"><img src="' + ulewo.absolutePath + '/resource/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo(topic_content);
    ulewo.ajaxRequest({
	async : true, url : ulewo.centerUrl.loadKnowledge, showLoad : false, data : {
	    pageNo : page,
	    userId:ulewo.usercenter.userId
	}, fun : function(res) {
	  
	    $("#loading").remove();
	    $("#knowledge-content").empty();
	    var list = res.data.list;
	    if (list.length == 0) {
		$("<span class='no-data'>没有帖子</span>").appendTo(topic_content);
	    }
	    var simplePage = res.data.page;
	    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
		new knowledgeItem(d).appendTo(topic_content);
	    }
	   
	    if(simplePage.pageTotal>1){
		 ulewo.pagination({
			pagePanelId : "knowledge-pager-top", pageObj : simplePage, fun : loadTopic
		    });
		    ulewo.pagination({
			pagePanelId : "knowledge-pager-footer", pageObj : simplePage, fun : loadTopic
		 });
	    }
	   
	}
    });
}

/********粉丝，关注*********/

function loadFans(){
    var topic_content = $("#fans-content");
    $('<div id="loading"> <div class="loading-con"><img src="' + ulewo.absolutePath + '/resource/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo(topic_content);
    ulewo.ajaxRequest({
	async : true, url : ulewo.centerUrl.loadFans, showLoad : false, data : {
	    userId:ulewo.usercenter.userId
	}, fun : function(res) {
	    $("#loading").remove();
	    topic_content.empty();
	    var list = res.data;
	    if (list.length == 0) {
		$("<span class='no-data'>没有粉丝</span>").appendTo(topic_content);
	    }
	    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
		$("<li><a href='"+ulewo.absolutePath+"/user/"+d.userId+"'><img src='//static.bucuoa.com/upload/"+d.userIcon+"'><span>"+d.userName+"</span></a></li>").appendTo(topic_content);
	    }
	}
    });
}

function loadFocus(){
    var topic_content = $("#focus-content");
    $('<div id="loading"><div class="loading-con"><img src="' + ulewo.absolutePath + '/resource/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo(topic_content);
    ulewo.ajaxRequest({
	async : true, url : ulewo.centerUrl.loadFoucs, showLoad : false, data : {
	    userId:ulewo.usercenter.userId
	}, fun : function(res) {
	    $("#loading").remove();
	    topic_content.empty();
	    var list = res.data;
	    if (list.length == 0) {
		$("<span class='no-data'>没有关注的人</span>").appendTo(topic_content);
	    }
	    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
		$("<li><a href='"+ulewo.absolutePath+"/user/"+d.friendUserId+"'><img src='//static.bucuoa.com/upload/"+d.friendUserIcon+"'><span>"+d.friendUserName+"</span></a></li>").appendTo(topic_content);
	    }
	}
    });
}