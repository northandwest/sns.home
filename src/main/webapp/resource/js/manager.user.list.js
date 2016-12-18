ulewo.searchParam = "";
ulewo.url = {
	loadUser : ulewo.absolutePath+"/manage/user/load_user",
	delUser:ulewo.absolutePath+"/manage/user/del_user",
	rewardMark:ulewo.absolutePath+"/manage/user/reward_mark",
	warnUser:ulewo.absolutePath+"/manage/user/warn_user"
}
$(function(){
    initPage(1);
    
    //删除
    $(document).on("click",".del-btn",function(){
    	del($(this).attr("userid"),$(this).attr("username"));
    });
    //奖励积分
    $(document).on("click",".reward-mark-btn",function(){
    	showReward($(this).attr("userid"),$(this).attr("username"));
    });
    //警告
    $(document).on("click",".warn-user-btn",function(){
    	showWarn($(this).attr("userid"),$(this).attr("username"));
    });
})

function showReward(userId,userName){
	var html = "<table>"
	   +"<tr>"
	     	+"<td>积分：</td><td><input type='text' class='' name='mark' id='mark-input'></td>"
	   +"</tr>"
	   +"<tr>"
	   		+"<td>消息内容：</td><td><textarea name='message' id='message-input' style='width:200px;height:80px'></textarea></td>"
	   +"</tr>"
	   +"</table>"
	var d = ulewo.dialog({
		title:"奖励【"+userName+"】积分",
		content:html,
		okfun:function(){
			sendReward(userId,d);
		}
	});
}

//发送奖励
function sendReward(userId,d){
	var mark = $("#mark-input").val();
	var message = $("#message-input").val();
	var result = true;
	 if (mark== "") {
		ulewo.setError($("#mark-input"), "请输入积分");
		result = false;
	}
	if (message == "") {
		ulewo.setError($("#message-input"), "请输入消息内容");
		result = false;
	}
	if(!result){
		return;
	}
	d.remove();
	ulewo.ajaxRequest({
	    url : ulewo.url.rewardMark,
	    data :{
	    	userId:userId,
	    	mark:mark,
	    	message:message
	    },
	    fun : function(response) {
	    	initPage(ulewo.pageNo);
	    }
 });
}

function showWarn(userId,userName){
	var html = "<table>"
		   +"<tr>"
		     +"<td>消息内容：</td><td><textarea name='message' id='message-input' style='width:200px;height:80px'></textarea></td>"
		   +"</tr>"
		   +"</table>"
	var d = ulewo.dialog({
		title:"警告【"+userName+"】用户",
		content:html,
		okfun:function(){
			sendWarn(userId,d);
		}
	});
}

function sendWarn(userId,d){
	var message = $("#message-input").val();
	if (message == "") {
		ulewo.setError($("#message-input"), "请输入警告内容");
		return;
	}
	d.remove();
	ulewo.ajaxRequest({
	    url : ulewo.url.warnUser,
	    data :{
	    	userId:userId,
	    	message:message
	    },
	    fun : function(response) {
	    	initPage(ulewo.pageNo);
	    }
 });
}
//删除
function del(userId,userName){
    ulewo.confirm({
	msg : "确认要删除【"+userName+"】吗？", fun : function() {
    	ulewo.ajaxRequest({
    	    url : ulewo.url.delUser,
    	    data :{userId:userId},
    	    fun : function(response) {
    	    	initPage(ulewo.pageNo);
    	    }
    	});
	}
    });
}

//搜索
function search(){
    ulewo.searchParam = $("#searchform").serialize();
    initPage(1);
}

//加载列表
function initPage(pageNo) {
	ulewo.pageNo = pageNo;
	var data = ulewo.searchParam + "&pageNo=" + pageNo;
	ulewo.ajaxRequest({
	    url : ulewo.url.loadUser,
	    data : data,
	    fun : function(response) {
		var data = response.data;
		var simplePage = data.page;
		var list = data.list;
		$("#data-list tr").remove();
		if (list.length > 0) {
		    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
		    var op ='<a href="javascript:;" title="奖励积分" userid='+d.userId+' username='+d.userName+' class="reward-mark-btn"><i class="icon i-agree"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'
		    	    +'<a href="javascript:;" title="警告用户" userid='+d.userId+' username='+d.userName+' class="warn-user-btn"><i class="icon i-publish"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'
		    		+'<a href="javascript:;" title="删除" userid='+d.userId+' username='+d.userName+' class="del-btn"><i class="icon i-del"></i></a>';
		   var sex = "";
		   if(d.sex=="M"){
			   sex = "男";
		   }else if(d.sex=="F"){
			   sex = "女";
		   }
		    $("<tr>" 
				+"<td valign='center'><img src='"+ulewo.absolutePath+"/upload/"+d.userIcon+"' style='width:30px;'></td>" 
				+"<td>"+d.userName+"</td>"
				+"<td>"+sex+"</td>"
				+"<td>"+(d.birthday==null?"":d.birthday)+"</td>"
				+"<td>"+(d.work==null?"":d.work)+"</td>"
				+"<td>"+(d.address==null?"":d.address)+"</td>"
				+"<td>"+d.showRegisterTime+"</td>" 
				+"<td>"+d.showlastLoginTime+"</td>" 
				+"<td>"+d.mark+"</td>" 
				+"<td>"+(d.characters==null?"":d.characters)+"</td>"
				+"<td>"+op+"</td>" 
			+"</tr>").appendTo($("#data-list"));
		    }
		} else {
		    $('<tr><td colspan="100"><div class="no-data" >没有数据</div></td></tr>').appendTo($("#data-list"));
		}
		ulewo.pagination({
		    pagePanelId : "pager", pageObj : simplePage, fun : initPage
		});
	    }
	});
}





