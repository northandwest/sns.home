ulewo.searchParam = "";
ulewo.url = {
	loadComment : ulewo.absolutePath+"/manage/comment/load_comment",
	delComment:ulewo.absolutePath+"/manage/comment/delete_comment"
}
$(function(){
    initPage(1);
    
    $(document).on("click",".del-btn",function(){
	var commentId = $(this).attr("commentid");
	   ulewo.confirm({
		msg : "确认要删除吗？", fun : function() {
	    	ulewo.ajaxRequest({
	    	    url : ulewo.url.delComment,
	    	    data :{commentId:commentId,articleType:ulewo.articleType},
	    	    fun : function(response) {
	    	    	initPage(ulewo.pageNo);
	    	    }
	    	});
		}
	    });
    });
})

//搜索
function search(){
    ulewo.searchParam = $("#searchform").serialize();
    initPage(1);
}

//加载列表
function initPage(pageNo) {
	ulewo.pageNo = pageNo;
	var data = ulewo.searchParam + "&pageNo=" + pageNo+"&articleType="+ulewo.articleType;
	ulewo.ajaxRequest({
	    url : ulewo.url.loadComment,
	    data : data,
	    fun : function(response) {
		var data = response.data;
		var simplePage = data.page;
		var list = data.list;
		$("#data-list tr").remove();
		if (list.length > 0) {
		   
		    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
			var op ='<a href="javascript:;" title="删除" commentid='+d.id+' class="del-btn"><i class="icon i-del"></i></a>';
			$("<tr>" 
				+"<td valign='center'>"+d.content+"</td>" 
				+"<td>"+d.userName+"</td>" 
				+"<td>"+d.showCreateTime+"</td>" 
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





