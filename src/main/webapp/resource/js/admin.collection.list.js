ulewo.url = {
	loadCollection: ulewo.absolutePath+"/admin/load_collection.action",
	delCollection: ulewo.absolutePath+"/admin/del_collection.action"
}
$(function(){
    initPage(1);
    $(document).on("click",".del",function(){
	var articleId = $(this).attr("articleId");
	var articleType = $(this).attr("articleType");
	
	ulewo.confirm({
		msg : "确认要删除分类吗？", fun : function() {
			ulewo.ajaxRequest({
			    url : ulewo.url.delCollection,
			    data : {
				articleId:articleId,
				articleType:articleType
			    },
			    fun : function(response) {
				 ulewo.tipMsg({
					type : "success", content : "删除成功",timeout:1500
				 });
				 initPage(ulewo.pageNo);
			    }
			})
		}
	    });
    });
})

function search(){
    initPage(1);
}

function initPage(pageNo) {
	ulewo.pageNo = pageNo;
	var data = $("#searchform").serialize() + "&pageNo=" + pageNo;
	ulewo.ajaxRequest({
	    url : ulewo.url.loadCollection,
	    data : data,
	    fun : function(response) {
		var data = response.data;
		var simplePage = data.page;
		var list = data.list;
		$("#data-list tr").remove();
		if (list.length > 0) {
		    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
			var edit = '<a href="javascript:;" title="删除" class="del" articleId="'+d.articleId+'" articleType="'+d.articleType.type+'"><i class="icon i-del"></i></a>';
			var url = "";
			if(d.articleType.type=="T"){
			    url = ulewo.absolutePath+"/bbs/"+d.articleId;
			}else if(d.articleType.type=="K"){
			    url = ulewo.absolutePath+"/knowledge/"+d.articleId;
			}else if(d.articleType.type=="A"){
			    url = ulewo.absolutePath+"/ask/"+d.articleId;
			}else if(d.articleType.type=="B"){
			    url = ulewo.absolutePath+"/user/"+d.articleUserId+"/blog/"+d.articleId;
			}
			$("<tr><td valign='center'><a href='"+url+"' target='_blank'>"+d.title+"</a></td><td>"+d.showCreateTime+"</td><td>" + edit + "</td></tr>").appendTo($("#data-list"));
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
