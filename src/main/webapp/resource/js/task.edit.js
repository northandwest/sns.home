/*
 * 编辑用户
 */
define(function(require, exports, module) {
	require("common/umei.dev");
	var taskId = $("#taskId").val();

	var url = "task_add.do";
	if (taskId != "") {
		url = "task_edit.do";
	}

	umei.initForm({
		formObj : $("#task_form"), // form对象
		type:"inline",
		autocommit : "true", // 是否自动提交，设置为true 按enter会提交 提交按钮用input
		// type设置为submit 点击提交按钮也会提交
		commiturl : url, // 提交的url地址
		fun : function(response) { // 提交后回调方法
			umei.tipMsg({
				type : "success",
				content : "保存成功",
				timeout : 1000,
				fun : function() {
					window.location.href = document.referrer;
				}
			})
		}
	});

	$("#settingTime").click(function() {
		var coinfig = {
			obj : $(this),
			title : "如何设置调度时间",
			content : getTaskInfo()
		// 提示信息
		};
		umei.tips(coinfig);
	});

	umei.taskTime = {
		"0/10 * * * * ?" : "每10秒触发",
		"0 0 12 * * ?" : "每天中午12点触发",
		"0 15 10 ? * *" : "每天上午10:15触发 ",
		"0 15 10 * * ?" : "每天上午10:15触发",
		"0 15 10 * * ? *" : "每天上午10:15触发 ",
		"0 15 10 * * ?" : "2005 2005年的每天上午10:15触发 ",
		"0 * 14 * * ?" : "在每天下午2点到下午2:59期间的每1分钟触发 ",
		"0 0/5 14 * * ?" : " 在每天下午2点到下午2:55期间的每5分钟触发 ",
		"0 0/5 14,18 * * ?" : "在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发",
		"0 0-5 14 * * ?" : "在每天下午2点到下午2:05期间的每1分钟触发 ",
		"0 10,44 14 ? 3 WED" : "每年三月的星期三的下午2:10和2:44触发 ",
		"0 15 10 ? * MON-FRI" : "周一至周五的上午10:15触发 ",
		"0 15 10 15 * ?" : "每月15日上午10:15触发 ",
		"0 15 10 L * ?" : "每月最后一日的上午10:15触发 ",
		"0 15 10 ? * 6L" : "每月的最后一个星期五上午10:15触发 ",
		"0 15 10 ? * 6L 2002-2005" : "2002年至2005年的每月的最后一个星期五上午10:15触发",
		"0 15 10 ? * 6#3" : "每月的第三个星期五上午10:15触发",
		"0 0 06,18 * * ?" : "在每天上午6点和下午6点触发",
		"0 30 5 * * ? *" : "在每天上午5:30触发",
		"0 0/3 * * * ?" : "每3分钟触发"
	}

	function getTaskInfo() {
		var infostr = "<table class='time-info-table'><tr><th>时间</th><th>描述</th></tr>";
		var count = 0;
		for ( var key in umei.taskTime) {
			if (count % 2 != 0) {
				infostr = infostr + "<tr><td>" + key + "</td><td>" + umei.taskTime[key] + "</td></tr>";
			} else {
				infostr = infostr + "<tr><td class='colorTd'>" + key + "</td><td class='colorTd'>"
						+ umei.taskTime[key] + "</td></tr>";
			}
			count++
		}
		infostr = infostr + "</table>";
		return infostr;
	}

});
