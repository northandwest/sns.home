ulewo.searchParam = "";
ulewo.url = {
	loadKnowledge : ulewo.absolutePath+"/manage/knowledge/load_knowledge",
	delKnowledge:ulewo.absolutePath+"/manage/knowledge/delete_knowledge",
	auditKnowledge:ulewo.absolutePath+"/manage/knowledge/audit_knowledge"
}
$(function(){
    initPage(1);

$("#check-all").click(function(){
	
    	if($(this).is(':checked')){
    		$("#data-list input[name='knowledge']").prop("checked",true);
    	}else{
    		$("#data-list input[name='knowledge']").prop("checked",false);
    	}
    });
    
    $(".audit-btn").click(function(){
    	var checkedExams = $("#data-list input[name='knowledge']:checked");
    	if(checkedExams.length==0){
    		ulewo.tipMsg({
				type : "warn", content : "请选择要审核的消息",timeout:1500
    		});
    		return;
    	}
    	var checkedExamIds = "";
    	for(var i=0,_len=checkedExams.length;i<_len;i++){
    		var d = checkedExams.eq(i);
    		if(i==_len-1){
    		    checkedExamIds = checkedExamIds + "ids="+d.val()
    		}else{
    		    checkedExamIds = checkedExamIds + "ids="+d.val()+"&";
    		}
    	}
    	audit(checkedExamIds);
    });
    $(".del-btn").click(function(){
    	var checkedExams = $("#data-list input[name='knowledge']:checked");
    	if(checkedExams.length==0){
    		ulewo.tipMsg({
				type : "warn", content : "请选择要删除的消息",timeout:1500
    		});
    		return;
    	}
    	var checkedExamIds = "";
    	for(var i=0,_len=checkedExams.length;i<_len;i++){
    		var d = checkedExams.eq(i);
    		if(i==_len-1){
    		    checkedExamIds = checkedExamIds + "ids="+d.val()
    		}else{
    		    checkedExamIds = checkedExamIds + "ids="+d.val()+"&";
    		}
    	}
    	del(checkedExamIds);
    });
})

//审核
function audit(checkedExamIds){
    ulewo.confirm({
	msg : "确认要审核吗？", fun : function() {
    	ulewo.ajaxRequest({
    	    url : ulewo.url.auditKnowledge,
    	    data :checkedExamIds,
    	    fun : function(response) {
    	    	initPage(ulewo.pageNo);
    	    }
    	});
	}
    });
}
//删除
function del(checkedExamIds){
    ulewo.confirm({
	msg : "确认要删除吗？", fun : function() {
    	ulewo.ajaxRequest({
    	    url : ulewo.url.delKnowledge,
    	    data :checkedExamIds,
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
	    url : ulewo.url.loadKnowledge,
	    data : data,
	    fun : function(response) {
		var data = response.data;
		var simplePage = data.page;
		var list = data.list;
		$("#data-list tr").remove();
		if (list.length > 0) {
		    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
			$("<tr>" 
				+"<td><input type='checkbox' value='"+d.topicId+"' class='not-input' name='knowledge'></td>" 
				+"<td>"+(d.status.type==0?"未审核":"已审核")+"</td>" 
				+"<td valign='center'><a href='"+ulewo.absolutePath+"/knowledge/"+d.topicId+"' target='_blank'>"+d.title+"</a></td>" 
				+"<td>"+d.userName+"</td>" 
				+"<td>"+d.commentCount+"</td>" 
				+"<td>"+d.likeCount+"</td>" 
				+"<td>"+d.collectionCount+"</td>" 
				+"<td>"+d.showCreateTime+"</td>" 
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





