ulewo.url = {
   loadVoteUrl : ulewo.absolutePath + "/bbs/loadVote", doVoteUrl : ulewo.absolutePath + "/bbs/doVote.action"
};
$(function() {
    loadTopicVote();
});

function loadTopicVote() {
    if (ulewo.articleType == "1") {
	$("#vote-con").html("正在加载调查......");
	ulewo.ajaxRequest({
	    url : ulewo.url.loadVoteUrl, showLoad : false, data : {
		topicId : ulewo.topicId
	    }, fun : function(res) {
		$("#vote-con").empty();
		loadvote(res.data);
	    }
	});
    }
}

function loadvote(vote) {
    ulewo.voteId = vote.voteId;
    var voteType = "radio";
    var voteType = vote.voteType.type == '2' ? "checkbox" : "radio";
    var votes = vote.dtlList;
    var curIndex = 0;
    var form = $("<form id='voteForm'></form>").appendTo($("#vote-con"));
    $("<input type='hidden' name='voteType' value='" + vote.voteType.type + "'>").appendTo(form);
    $("<input type='hidden' name='voteId' value='" + vote.voteId + "'>").appendTo(form);
    $("<input type='hidden' name='topicId' value='" + ulewo.topicId + "'>").appendTo(form);
    for (var i = 0, length = votes.length; i < length; i++) {
	if (i % 5 == 0) {
	    curIndex = 0;
	}
	var dtl = votes[i];
	var label = $("<label style='cursor:pointer' for='vote" + i + "'></label>").appendTo(form);
	var vote_detail_line = $('<div class="vote_detail_line"></div>').appendTo(label);
	if (vote.voteUser != null || vote.voteUser.length > 0) {
	    $('<div class="vote_detail_op"><input  class="not-input" type="' + voteType + '" id="vote' + i + '" name="voteDtlId" value="' + dtl.id + '"></div>').appendTo(vote_detail_line);
	}
	$('<div class="vote_detail_title">' + dtl.title + '</div>').appendTo(vote_detail_line);
	var vote_detail_color_p = $('<div class=vote_detail_color_p></div>').appendTo(vote_detail_line);
	var vote_detail_color = $('<div class="vote_detail_color" style="background-position: 0px ' + curIndex + 'px"></div>').appendTo(vote_detail_color_p);
	var percent = 0;
	if (dtl.count > 0) {
	    percent = (dtl.count / vote.sumCount).toFixed(3);
	}
	var shoPercent = accMul(percent, 100);
	var vote_detail_color_p_length = parseInt($(".vote_detail_color_p").eq(0).css("width"));
	var detailLength = accMul(percent, vote_detail_color_p_length);
	var vote_detail_percent = $('<div class=vote_detail_percent>' + dtl.count + '(' + shoPercent + '%)</div>').appendTo(vote_detail_line);
	curIndex = curIndex - 15;
	vote_detail_color.animate({
	    width : detailLength
	}, 1500);
    }
    var endDate = $("<div style='margin-top:10px;height:20px;'>投票结束日期:" + vote.endDate + "</div>").appendTo(form);
    if (vote.canVote) {
	$("<div class='btn'><a href='javascript:void(0)' onclick = 'vote(this)' class='btn  small-btn'>投票</a></div>").appendTo(form);
    } else {
	if (vote.voteUser != null && vote.voteUser.length > 0) {
	    var dtl = $('<div class="vote_detail_dtl"><span>你已经投了：</span></div>').appendTo(form);
	    var uservotes = vote.voteUser;
	    for (var i = 0, length = uservotes.length; i < length; i++) {
		if (i < length - 1) {
		    $("<span class='vote_tit'>" + uservotes[i].title + "、</span>").appendTo(dtl);
		} else {
		    $("<span class='vote_tit'>" + uservotes[i].title + "</span>").appendTo(dtl);
		}
	    }
	} else {
	    $('<div class="vote_detail_dtl"><span>投票已经结束</span></div>').appendTo(form);
	}
    }
};
/**
 * 两个数想成
 * 
 * @param arg1
 * @param arg2
 * @returns {Number}
 */
function accMul(arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {
	m += s1.split(".")[1].length
    } catch (e) {}
    try {
	m += s2.split(".")[1].length
    } catch (e) {}
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}

function vote(obj) {
    if (ulewo.user.userId == "") {
	var url = ulewo.curUrl;
	url = encodeURI(url);
	document.location.href = "../login?redirect=" + url;
	return;
    }
    if ($("input[name='voteDtlId']:checked").length == 0) {
	ulewo.tipMsg({
	    type : "warn", content : "请选择要投票的选项", timeout : 3000
	});
	return;
    }
    ulewo.ajaxRequest({
	url : ulewo.url.doVoteUrl, data : $("#voteForm").serialize(), fun : function(res) {
	    $("#vote-con").empty();
	    loadvote(res.data);
	}
    });
}
