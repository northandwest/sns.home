ulewo.url = {
    loadExamUsers : "/exam/load_post_exam_users"
}

$(function() {
    loadExamUsers(1);

    $("#do-exam").click(function() {
	if (ulewo.user.userId == "") {
	    var d = dialog({
		title : "没带准考证", content : "你好像没带准考证哦", fixed : true, cancelValue : "弃考", cancel : function() {}, okValue : "去拿准考证", ok : function() {
		    document.location.href = ulewo.absolutePath + "/login";
		}
	    });
	    d.showModal();
	} else {
	    $("#do-exam-form").submit();
	}
    });
});

function loadExamUsers(page) {
    ulewo.pageNo = page;
    $('<div id="loading"> <div class="loading-con"><img src="' + ulewo.absolutePath + '/resource/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo($("#post-user-list"));
    ulewo.ajaxRequest({
	url : ulewo.url.loadExamUsers, data : {
	    pageNo : page
	}, showLoad : false, fun : function(res) {
	    $("#loading").remove();
	    var list = res.data.list;
	    var simplePage = res.data.page;
	    var _len = list.length;
	    if (_len > 0) {
		for (var i = 0, data; i < _len, data = list[i]; i++) {
		    new ExamUserItem(data).appendTo($("#post-user-list"));
		}
	    } else {
		$("<div class='no-data'>还没有人发布考题，赶紧抢沙发吧</div>").appendTo($("#post-user-list"));
	    }

	    ulewo.pagination({
		pagePanelId : "pager", pageObj : simplePage, fun : loadExamUsers
	    });
	}
    });
}

function ExamUserItem(data) {
    var item = $("<a href='"+ulewo.absolutePath+"/user/"+data.userId+"' class='exam-user'></a>");
    $("<img src='" + ulewo.absolutePath + "/upload/" + data.userIcon + "'>").appendTo(item);
    $("<span>" + data.userName + "</span>").appendTo(item);
    $("<span class='post-count'>发题：" + data.examCount + "</span>").appendTo(item);
    return item;
}