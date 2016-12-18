$(function(){
	searchData(1);
});
ulewo.countTotal = 0;
function searchData(pageNo){
	var articleType = $("#searchType").val();
	var keyword = $.trim($("#search-keyword").val());
	if(articleType==""||keyword==""){
		return;
	}
	$(".seartch-tit").eq(0).text($("#searchType").find("option:selected").text());
	ulewo.ajaxRequest({
        url : ulewo.absolutePath+"/load_search", data : {
            articleType:articleType,
            keyword:keyword,
            pageNo:pageNo,
            countTotal:ulewo.countTotal
        }, fun : function(response) {
    		var data = response.data;
    		var simplePage = data.page;
    		ulewo.countTotal=simplePage.countTotal;
    		var list = data.list;
    		$("#data-list .search-item").remove();
    		if (list.length > 0) {
    			 for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
    				  new SearchItem(d).appendTo($("#data-list"));
    			 }
    		} else {
    		    $('<tr><td colspan="100"><div class="no-data" >没有搜索到数据</div></td></tr>').appendTo($("#data-list"));
    		}
    		ulewo.pagination({
    		    pagePanelId : "pager", pageObj : simplePage, fun : searchData
    		});
    	    }
        });
}

function SearchItem(data){
	var url = "";
	var searchType = $("#searchType").val();
	var id = data.id.substring(0,data.id.indexOf("_"));
	if(searchType=="T"){
		url = ulewo.absolutePath+"/bbs/"+id;
	}else if(searchType=="K"){
		url = ulewo.absolutePath+"/knowledge/"+id;
	}else if(searchType=="Z"){
		url = ulewo.absolutePath+"/ask/"+id;
	}else if(searchType=="B"){
		url = ulewo.absolutePath+"/user/"+data.userId+"/blog/"+id;
	}
	var item = $("<div class='search-item'></div>");
	$("<a href='"+url+"' target='_blank' class='search-item-title'>"+data.title+"</a><span class='time'>"+data.createTime+"</span><span class='search-by'>by</span><a class='search-user' target='_blank' href='"+ulewo.absolutePath+"/user/"+data.userId+"'>"+data.userName+"</a>").appendTo(item);
	$("<div class='search-summary'>"+data.summary+"</div>").appendTo(item);
	return item;
}
