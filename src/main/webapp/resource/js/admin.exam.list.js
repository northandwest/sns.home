ulewo.searchParam = "";
ulewo.url = {
	loadBlog : ulewo.absolutePath+"/admin/load_my_question.action",
	delBlog:"del_blog.action"
}
$(function(){
    initPage(1);
    $(document).on("click",".del-btn",function(){
    	var blogId = $(this).attr("blogId");
    	ulewo.confirm({
    		msg : "确认要删除吗？", fun : function() {
    	    	ulewo.ajaxRequest({
    	    	    url : ulewo.url.delBlog,
    	    	    data : {
    	    	    	blogId:blogId
    	    	    },
    	    	    fun : function(response) {
    	    	    	initPage(ulewo.pageNo);
    	    	    }
    	    	});
    		}
    	    });
    });
})

function search(){
    ulewo.searchParam = $("#searchform").serialize();
    initPage(1);
}

function initPage(pageNo) {
	ulewo.pageNo = pageNo;
	var data = ulewo.searchParam + "&pageNo=" + pageNo;
	ulewo.ajaxRequest({
	    url : ulewo.url.loadBlog,
	    data : data,
	    fun : function(response) {
		var data = response.data;
		var simplePage = data.page;
		var list = data.list;
		$("#data-list tr").remove();
		if (list.length > 0) {
		    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
			var edit = '<a href="'+ulewo.absolutePath+'/admin/edit_blog.action?blogId='+d.id+'" title="修改" class="edit-btn" blodId="'+d.id+'"><i class="icon i-edit"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" title="删除" class="del-btn"  blogId="'+d.id+'"><i class="icon i-del"></i></a>';
			$("<tr><td valign='center'><a href='"+ulewo.absolutePath+"/user/"+ulewo.user.userId+"/blog/"+d.id+"' target='_blank'>"+d.examTitle+"</a></td><td>"+d.commentCount+"</td><td>"+d.readCount+"</td><td>"+d.showCreateTime+"</td><td>" + edit + "</td></tr>").appendTo($("#data-list"));
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
