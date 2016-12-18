ulewo.searchParam = "";
ulewo.url = {
	loadSpitSlot : ulewo.absolutePath+"/manage/spitsplot/load_spit_slot",
	delSpitSlot:ulewo.absolutePath+"/manage/spitsplot/delete_spit_slot"
}
$(function(){
    initPage(1);
    //删除吐槽
    $(document).on("click",".del-btn",function(){
	delSpitSlot($(this));
    });
})
//搜索
function search(){
    ulewo.searchParam = $("#searchform").serialize();
    initPage(1);
}

//删除评论
function delSpitSlot(curObj){
    var spitslotId = curObj.attr("spitslotid");
	ulewo.confirm({
		msg : "确认要删除吗？", fun : function() {
	    	ulewo.ajaxRequest({
	    	    url : ulewo.url.delSpitSlot,
	    	    data : {
	    		spitSlotId:spitslotId
	    	    },
	    	    fun : function(response) {
	    	    	initPage(ulewo.pageNo);
	    	    }
	    	});
		}
	    });
}
//加载列表
function initPage(pageNo) {
	ulewo.pageNo = pageNo;
	var data = ulewo.searchParam + "&pageNo=" + pageNo;
	ulewo.ajaxRequest({
	    url : ulewo.url.loadSpitSlot,
	    data : data,
	    fun : function(response) {
		var data = response.data;
		var simplePage = data.page;
		var list = data.list;
		$("#data-list tr").remove();
		if (list.length > 0) {
		    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
			var op =
			    	'<a href="'+ulewo.absolutePath+'/user/'+d.userId+'/spitslot/'+d.id+'" target="_blank" title="查看" class="edit-btn"><i class="icon i-preview"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'
			    	+'<a href="javascript:;" title="删除" class="del-btn"  spitslotid="'+d.id+'"><i class="icon i-del"></i></a>';
			$("<tr>" 
				+"<td valign='center'>"+d.content+"</td>" 
				+"<td>"+d.userName+"</td>" 
				+"<td>"+d.commentCount+"</td>" 
				+"<td>"+d.createTime+"</td>" 
				+"<td>" + op + "</td>" 
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





