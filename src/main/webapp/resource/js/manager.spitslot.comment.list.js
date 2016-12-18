ulewo.searchParam = "";
ulewo.url = {
	loadSpitSlotComment : ulewo.absolutePath+"/manage/spitsplot/load_spit_slot_comment_list",
	delSpitSlotComment:ulewo.absolutePath+"/manage/spitsplot/delete_spit_slot_comment"
}
$(function(){
    initPage(1);
    $(document).on("click",".del-btn",function(){
    	var spitSlotId = $(this).attr("spitSlotId");
    	var commentId = $(this).attr("commentid");
    	ulewo.confirm({
    		msg : "确认要删除吗？", fun : function() {
    	    	ulewo.ajaxRequest({
    	    	    url : ulewo.url.delSpitSlotComment,
    	    	    data : {
    	    		spitSlotId:spitSlotId,
    	    		commentId:commentId
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
	    url : ulewo.url.loadSpitSlotComment,
	    data : data,
	    fun : function(response) {
		var data = response.data;
		var simplePage = data.page;
		var list = data.list;
		$("#data-list tr").remove();
		if (list.length > 0) {
		    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
			var op = '<a href="javascript:;" title="删除" class="del-btn" commentid="'+d.id+'"  spitSlotId="'+d.spitSlotId+'"><i class="icon i-del"></i></a>';
			$("<tr><td valign='center'>"+d.content+"</td><td valign='center'>"+d.userName+"</td><td>"+d.createTime+"</td><td>" + op + "</td></tr>").appendTo($("#data-list"));
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
