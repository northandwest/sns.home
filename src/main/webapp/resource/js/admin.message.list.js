ulewo.searchParam = "";
ulewo.url = {
	loadMessage:ulewo.absolutePath+"/admin/load_user_message_list.action",
	delMessage:ulewo.absolutePath+"/admin/del_message.action",
    markMessage:ulewo.absolutePath+"/admin/mark_message_read.action"
}
$(function(){
    initPage(1);
    $(document).on("click",".del-btn",function(){
    	del("ids="+$(this).attr("messageId"));
    });
    $(document).on("click",".mark-btn",function(){
    	mark("ids="+$(this).attr("messageId"));
    });
    
    $("#check-all").click(function(){
    	if($(this).is(':checked')){
    		$("#data-list input[name='message']").prop("checked",true);
    	}else{
    		$("#data-list input[name='message']").prop("checked",false);
    	}
    });
    
    $("#mark-batch-btn").click(function(){
    	var checkedMessages = $("#data-list input[name='message']:checked");
    	if(checkedMessages.length==0){
    		ulewo.tipMsg({
				type : "warn", content : "请选择要标记的消息",timeout:1500
    		});
    		return;
    	}
    	var messsageIds = "";
    	for(var i=0,_len=checkedMessages.length;i<_len;i++){
    		var d = d=checkedMessages.eq(i);
    		if(i==_len-1){
    			messsageIds = messsageIds + "ids="+d.val()
    		}else{
    			messsageIds = messsageIds + "ids="+d.val()+"&";
    		}
    	}
    	mark(messsageIds);
    });
    $("#del-batch-btn").click(function(){
    	var checkedMessages = $("#data-list input[name='message']:checked");
    	if(checkedMessages.length==0){
    		ulewo.tipMsg({
				type : "warn", content : "请选择要删除的消息",timeout:1500
    		});
    		return;
    	}
    	var messsageIds = "";
    	for(var i=0,_len=checkedMessages.length;i<_len;i++){
    		var d = d=checkedMessages.eq(i);
    		if(i==_len-1){
    			messsageIds = messsageIds + "ids="+d.val()
    		}else{
    			messsageIds = messsageIds + "ids="+d.val()+"&";
    		}
    	}
    	del(messsageIds);
    });
})

function del(messageId){
	ulewo.confirm({
		msg : "确认要删除吗？", fun : function() {
	    	ulewo.ajaxRequest({
	    	    url : ulewo.url.delMessage,
	    	    data :messageId,
	    	    fun : function(response) {
	    	    	initPage(ulewo.pageNo);
	    	    }
	    	});
		}
	});
}


function mark(messageId){
	ulewo.confirm({
			msg : "确认要标记为已读吗？", fun : function() {
			ulewo.ajaxRequest({
			    url : ulewo.url.markMessage,
			    data : messageId,
			    fun : function(response) {
			    	initPage(ulewo.pageNo);
			    }
			});
		}
	})
}

function search(){
    ulewo.searchParam = $("#searchform").serialize();
    initPage(1);
}

function initPage(pageNo) {
	ulewo.pageNo = pageNo;
	var data = ulewo.searchParam + "&pageNo=" + pageNo;
	ulewo.ajaxRequest({
	    url : ulewo.url.loadMessage,
	    data : data,
	    fun : function(response) {
		var data = response.data;
		var simplePage = data.page;
		var list = data.list;
		$("#data-list tr").remove();
		if (list.length > 0) {
		    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
			var edit ="";
			if(d.url!=""){
			    edit = edit+'<a href="'+ulewo.absolutePath+'/admin/readMessage.action?id='+d.id+'" title="查看" target="_blank" class="read-btn" url="'+d.url+'" messageId="'+d.id+'"><i class="icon i-preview"></i>&nbsp;&nbsp;&nbsp;&nbsp;' 
			}
			edit = edit+'<a href="javascript:;" title="标记为已读" class="mark-btn" messageId="'+d.id+'"><i class="icon i-start"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'
			edit = edit+'<a href="javascript:;" title="删除" class="del-btn"  messageId="'+d.id+'"><i class="icon i-del"></i></a>';
			$("<tr><td><input type='checkbox' class='not-input' name='message' value='"+d.id+"'></td><td valign='center'>"+(d.status=="0"?"未读":"已读")+"</td><td valign='center'>"+d.description+"</td><td>"+d.createTime+"</td><td>" + edit + "</td></tr>").appendTo($("#data-list"));
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
