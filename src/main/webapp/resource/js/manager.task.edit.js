ulewo.taskTime = {
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
$(function(){
	$("#settingTime").click(function() {
		var coinfig ={
			title : "如何设置调度时间",width :500, align : 'bottom left', obj : $(this)[0],content : getTaskInfo()
		}
		ulewo.popDialog(coinfig);
	});
	
	$("#save-btn").click(function(){
	    saveTask();
	});
	var taskId = $("#taskId").val();
	ulewo.url = ulewo.absolutePath+"/manage/task/task_add";
	if (taskId != "") {
	    ulewo.url = ulewo.absolutePath+"/manage/task/task_edit";
	}
})

function saveTask(){
    	var result = ulewo.checkForm($("#task_form"));
	if(!result){
	    return;
	}
	ulewo.ajaxRequest({
	    url : ulewo.url,
	    data : $("#task_form").serialize(),
	    fun : function(res) {
	    	 ulewo.tipMsg({
			type : "success", content : "保存成功",timeout:1500
		});
	    }
	})
}

function getTaskInfo() {
		var infostr = "<table class='time-info-table'><tr><th>时间</th><th>描述</th></tr>";
		var count = 0;
		for ( var key in ulewo.taskTime) {
			if (count % 2 != 0) {
				infostr = infostr + "<tr><td>" + key + "</td><td>" + ulewo.taskTime[key] + "</td></tr>";
			} else {
				infostr = infostr + "<tr><td class='colorTd'>" + key + "</td><td class='colorTd'>"
						+ ulewo.taskTime[key] + "</td></tr>";
			}
			count++
		}
		infostr = infostr + "</table>";
		return infostr;
}

