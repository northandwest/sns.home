ulewo.searchParam = "";
ulewo.url = {
	loadBlog : ulewo.absolutePath+"/manage/blog/load_blog",
	delBlog:ulewo.absolutePath+"/manage/blog/delete_blog",
}
$(function(){
    initPage(1);

    $("#check-all").click(function(){
    	if($(this).is(':checked')){
    		$("#data-list input[name='blog']").prop("checked",true);
    	}else{
    		$("#data-list input[name='blog']").prop("checked",false);
    	}
    });

    $(".del-btn").click(function(){
    	var checkeds = $("#data-list input[name='blog']:checked");
    	if(checkeds.length==0){
    		ulewo.tipMsg({
				type : "warn", content : "请选择要删除的消息",timeout:1500
    		});
    		return;
    	}
    	var checkedIds = "";
    	for(var i=0,_len=checkeds.length;i<_len;i++){
    		var d = checkeds.eq(i);
    		if(i==_len-1){
    			checkedIds = checkedIds + "ids="+d.val()
    		}else{
    			checkedIds = checkedIds + "ids="+d.val()+"&";
    		}
    	}
    	del(checkedIds);
    });
})
//删除
function del(checkedIds){
    ulewo.confirm({
	msg : "确认要删除吗？", fun : function() {
    	ulewo.ajaxRequest({
    	    url : ulewo.url.delBlog,
    	    data :checkedIds,
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
	    url : ulewo.url.loadBlog,
	    data : data,
	    fun : function(response) {
		var data = response.data;
		var simplePage = data.page;
		var list = data.list;
		$("#data-list tr").remove();
		if (list.length > 0) {
		    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
			$("<tr>" 
				+"<td><input type='checkbox' value='"+d.blogId+"' class='not-input' name='blog'></td>" 
				+"<td valign='center'><a href='"+ulewo.absolutePath+"/user/"+d.userId+"/blog/"+d.blogId+"' target='_blank'>"+d.title+"</a></td>" 
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





