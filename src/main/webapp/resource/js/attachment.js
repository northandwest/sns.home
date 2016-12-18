ulewo.attachmentUrl = {
    downloadUser : ulewo.absolutePath + "/findAttachmentDonwload", checkdownload : ulewo.absolutePath + "/checkDownload.action", downloadAttachment : ulewo.absolutePath + "/downloadAttachment.action"
}

$(function(){
    $("#dcount").click(function() {
	var attachmentid = $(this).attr("attachmentid");
	loadDonwloadUser($(this)[0], attachmentid);
    });
    
    $("#downloadFile").click(function() {
	var topicid = $(this).attr("topicid");
	var attachmentid = $(this).attr("attachmentid");
	checkDownload(topicid, attachmentid);
    });
})

function checkDownload(topicid, attachmentid) {
    ulewo.ajaxRequest({
	url : ulewo.attachmentUrl.checkdownload, data : {
	    attachmentId : attachmentid, topicId : topicid
	}, fun : function(res) {
	    document.location.href = ulewo.attachmentUrl.downloadAttachment + "?attachmentId=" + attachmentid;
	    var dcount = $("#dcount").text();
	    $("#dcount").text(parseInt(dcount) + 1)
	}
    });
}

function loadDonwloadUser(curObj, attachmentid) {
    var d = ulewo.popDialog({
	width : 280, align : 'bottom', obj : curObj
    });
    var at_panel = $("<div></div>")
    ulewo.ajaxRequest({
	url : ulewo.attachmentUrl.downloadUser,showLoad:false, data : {
	    attachmentId : attachmentid
	}, fun : function(res) {
	    var content = "";
	    var list = res.data;
	    var _len = list.length;
	    if (_len == 0) {
		content = "没有用户下载";
	    } else {
		content = at_panel;
		for (var i = 0, item; i < _len, item = list[i]; i++) {
		    $("<a class='download-user' href='" + ulewo.absolutePath + "/user/" + item.userId + "' title='"+item.userName+"' target='_blank'><img src='" + ulewo.absolutePath + "/upload/" + item.userIcon + "'></a>").appendTo(content);
		}
	    }
	    d.content(content);
	}
    });
}