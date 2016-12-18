ulewo.url = {
    doSignIn : "doSignIn.action", loadCurDaySignIn : "loadCurDaySigin", loadMySignIn : "loadUserSigin.action"
}
$(function() {
    ulewo.tag({
	id : "tag", contentClass : "tag-content", fun : function(index) {
	    if (index == 1) {
		if (ulewo.user.userId == "") {
		    var url = ulewo.curUrl;
		    url = encodeURI(url);
		    document.location.href = "//ucenter.ucuoa.com?redirect=" + url;
//		      document.location.href = "../login?redirect=" + url;
		    return;
		}
		loadMySignInInfo();
	    }
	}
    });

    $("#doSignIn").click(function() {
	var haveSignInType = $(this).attr("haveSignInType");
	if (haveSignInType == "true") {
	    return;
	}
	if (ulewo.user.userId == "") {
	    ulewo.goLogin();
	    return;
	}
	ulewo.ajaxRequest({
	    async : true, url : ulewo.url.doSignIn, fun : function(res) {
		setSignInInfo(res);
		var msg = "2积分已到碗里";
		if (res.data.continueSigIn) {
		    msg = "连续7天签到10积分已到碗里";
		}
		ulewo.tipMsg({
		    type : "success", content : msg, timeout : 3000
		});
	    }
	});
    });

    loadCurDaySigin(1);
});

function loadCurDaySigin(page) {
    ulewo.curPage = page;
    $('<div id="loading"> <div class="loading-con"><img src="' + ulewo.absolutePath + '/resource/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo($("#curDaySigin"));
    ulewo.ajaxRequest({
	async : true, url : ulewo.url.loadCurDaySignIn, showLoad : false, data : {
	    pageNo : page
	}, fun : function(res) {
	    $("#loading").remove();
	    $("#curDaySigin").empty();
	    var list = res.data.list;
	    if (list.length == 0) {
		$("<div class='no-data'>今天还没有人签到</div>").appendTo($("#curDaySigin"));
	    }
	    var simplePage = res.data.page;
	    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
		new SignInItem(d).appendTo($("#curDaySigin"));
	    }
	    ulewo.pagination({
		pagePanelId : "pager", pageObj : simplePage, fun : loadCurDaySigin
	    });
	}
    });
}

function SignInItem(data) {
    var item = $("<div class='signIn-item'></div>");
    $("<div class='signIn-item-img'><a href='"+ulewo.absolutePath+"/user/"+data.userId+"'><img src='//static.bucuoa.com/upload/" + data.userIcon + "'></a></div>").appendTo(item);
    var info = $("<div class='signIn-item-info'></div>").appendTo(item);
    $("<div class='signIn-item-name'><a href='"+ulewo.absolutePath+"/user/"+data.userId+"'>" + data.userName + "</a></div>").appendTo(info);
    $("<div class='signIn-item-time'>" + data.signTime + "</div>").appendTo(info);
    $("<div class='clear'></div>").appendTo(item);
    return item;
}

// 设置签到信息
function setSignInInfo(res) {
    var signInBtn = $("#doSignIn");
    signInBtn.find("#sign-info .sign-tit").text("已签到");
    signInBtn.attr("haveSignInType", true);
    $("#todaySigInCount").text(parseInt($("#todaySigInCount").text()) + 1);
    $("#userSignInCount").text(parseInt($("#userSignInCount").text()) + 1);
    $("#sign-days").text(parseInt($("#sign-days").text()) + 1);
    if ($(".no-data").length == 0) {
	new SignInItem(res.data).appendTo($("#curDaySigin"));
    } else {
	$(".no-data").remove();
	new SignInItem(res.data).appendTo($("#curDaySigin"));
    }
}

// 加载我的签到信息
function loadMySignInInfo(year) {
    ulewo.ajaxRequest({
	async : true, url : ulewo.url.loadMySignIn, data : {
	    year : year
	}, fun : function(res) {
	    var data = res.data.list;
	    var curYear = res.data.curYear;
	    var year = res.data.year;
	    var userSignInPanel = $("#userSignIn");
	    userSignInPanel.empty();
	    // 加载年
	    var selectDiv = $("<div class='selectDiv'></div>").appendTo(userSignInPanel);
	    var selectCon = $("<div class='selectCon'></div>").appendTo(selectDiv);
	    var select = $("<select></select>").appendTo(selectCon).change(function() {
		loadMySignInInfo($(this).val());
	    });
	    for (var i = curYear; i >= 2013; i--) {
		if (i == year) {
		    $("<option value=" + i + " selected='selected'>" + i + "</option>").appendTo(select);
		} else {
		    $("<option value=" + i + ">" + i + "</option>").appendTo(select);
		}
	    }
	    $("<div class='noSignin'>未签到</div>").appendTo(selectDiv);
	    $("<div class='Signin'>已签到</div>").appendTo(selectDiv);
	    $("<div style='clear:left'></div>").appendTo(selectDiv);
	    var months = data;
	    for (var i = 0, length = months.length; i < length; i++) {
		var divCon = $("<div class='calendarCon'></div>").appendTo(userSignInPanel);
		var table = $("<table cellpadding='1' cellspacing='1'></table>").appendTo(userSignInPanel).appendTo(divCon);
		$("<tr><td colspan='8' class='month'>" + (i + 1) + "月</td></tr>").appendTo(table);
		$("<tr><td>星期日</td><td>星期一</td><td>星期二</td><td>星期三</td><td>星期四</td><td>星期五</td><td>星期六</td></tr>").appendTo(table);
		var monthData = months[i];
		var fristDay = monthData.fristDay;
		var monthDays = monthData.monthDays;
		var dayInfos = monthData.dayInfos;
		var tr = $("<tr></tr>").appendTo(table);
		for (var j = 1; j < fristDay; j++) {
		    $("<td>&nbsp;</td>").appendTo(tr);
		}
		for (var n = 0; n < monthDays; n++) {
		    var td = "";
		    if (dayInfos[n].signinType) {
			td = $("<td class='tdsignin'>" + dayInfos[n].day + "</td>").appendTo(tr);
		    } else {
			if (dayInfos[n].beforeNowDate) {
			    td = $("<td class='tdnosignin'>" + dayInfos[n].day + "</td>").appendTo(tr);
			} else {
			    td = $("<td class='afterday'>" + dayInfos[n].day + "</td>").appendTo(tr);
			}
		    }
		    if (dayInfos[n].curDay) {
			td.addClass("curDay");
		    }

		    if ((fristDay + n) % 7 == 0) {
			tr = $("<tr></tr>").appendTo(table);
		    }
		}
	    }
	    $("<div style='clear:left'></div>").appendTo(userSignInPanel);
	}
    });
}