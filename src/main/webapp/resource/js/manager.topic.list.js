ulewo.searchParam = "";
ulewo.url = {
	loadTopic : ulewo.absolutePath+"/manage/topic/load_topic",
	delTopic:ulewo.absolutePath+"/manage/topic/delete_topic",
	loadCategories :ulewo.absolutePath+"/bbs/loadCategories",
	changeTopicCategory:ulewo.absolutePath+"/manage/topic/change_category",
	setTop:ulewo.absolutePath+"/manage/topic/set_top",
	cancelTop:ulewo.absolutePath+"/manage/topic/cancel_top",
	setEssence:ulewo.absolutePath+"/manage/topic/set_essence",
	cancelEssence:ulewo.absolutePath+"/manage/topic/cancel_essence"
}
$(function(){
    initPage(1);
    initCategory();
    
    $("#check-all").click(function(){
    	if($(this).is(':checked')){
    		$("#data-list input[name='topic']").prop("checked",true);
    	}else{
    		$("#data-list input[name='topic']").prop("checked",false);
    	}
    });

    $(".del-btn").click(function(){
    	var checkeds = $("#data-list input[name='topic']:checked");
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
    
    $(document).on("click",".change-category-btn",function(){
    	showChangeCtegoryDialog($(this).attr("topicid"));
    });
    //置顶
    $(document).on("click",".set-top-btn",function(){
    	opTopic($(this).attr("topicid"),ulewo.url.setTop);
    });
    //取消置顶
    $(document).on("click",".cancel-top-btn",function(){
    	opTopic($(this).attr("topicid"),ulewo.url.cancelTop);
    });
    //设置精华
    $(document).on("click",".set-essence-btn",function(){
    	opTopic($(this).attr("topicid"),ulewo.url.setEssence);
    });
    //取消精华
    $(document).on("click",".cancel-essence-btn",function(){
    	opTopic($(this).attr("topicid"),ulewo.url.cancelEssence);
    });
    
    $(document).on("change", "#pCategoryId", function() {
    	var pCategoryId = $(this).val();
    	var list = ulewo.categories;
    	var children = [];
    	for (var i = 0, _len = list.length; i < _len; i++) {
    	    if (list[i].categoryId == pCategoryId) {
    	    	children = list[i].children;
    	    	break;
    	    }
    	}
    	$("#categoryId").empty();
    	$("<option value=''>请选择子板块</option>").appendTo($("#categoryId"));
    	for (var i = 0, _len = children.length; i < _len; i++) {
    	    var item = children[i];
    	    $("<option value=" + item.categoryId + ">" + item.name + "</option>").appendTo($("#categoryId"));
    	}
    });
})

function opTopic(topicId,url){
	ulewo.ajaxRequest({
	    url : url,
	    data :{
	    	topicId:topicId
	    },
	    fun : function(response) {
	    	initPage(ulewo.pageNo);
	    }
 });
}

//弹出选择框
function initCategory(){
	ulewo.ajaxRequest({
    	url : ulewo.url.loadCategories, showLoad : true, fun : function(res) {
    	    var list = res.data;
    	    ulewo.categories = list;
    	}
    });
}

function showChangeCtegoryDialog(topicId){
	var html = "<table>"
	   +"<tr>"
	     +"<td><span><select name='pCategoryId' id='pCategoryId'><option value=''>请选板块</option></select></span></td>"
	     +"<td><span><select name='categoryId' id='categoryId'><option value=''>请选择子板块</option></select></span></td>"
	   +"</tr>"
	   +"</table>"
	var d = ulewo.dialog({
		title:"修改分类",
		content:html,
		okfun:function(){
			saveTopicCategory(topicId,d);
		}
	});
	var list =  ulewo.categories;
	for (var i = 0, _len = list.length; i < _len; i++) {
		var item = list[i];
		$("<option value=" + item.categoryId + ">" + item.name + "</option>").appendTo($("#pCategoryId"));
	}
}
//保存帖子分类
function saveTopicCategory(topicId,d){
	 var pCategoryId = $("#pCategoryId").val();
	 var categoryId = $("#categoryId").val();
	 var result = true;
	 if (pCategoryId == "") {
		ulewo.setError($("#pCategoryId"), "请选择一级板块");
		result = false;
	}
	if (categoryId == "") {
		ulewo.setError($("#categoryId"), "请选择子板块");
		result = false;
	}
	if(!result){
		return;
	}
	d.remove();
	ulewo.ajaxRequest({
    	    url : ulewo.url.changeTopicCategory,
    	    data :{
    	    	topicId:topicId,
    	    	pCategoryId:pCategoryId,
    	    	categoryId:categoryId
    	    },
    	    fun : function(response) {
    	    	initPage(ulewo.pageNo);
    	    }
	 });
}

//删除
function del(checkedIds){
    ulewo.confirm({
	msg : "确认要删除吗？", fun : function() {
    	ulewo.ajaxRequest({
    	    url : ulewo.url.delTopic,
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
	    url : ulewo.url.loadTopic,
	    data : data,
	    fun : function(response) {
		var data = response.data;
		var simplePage = data.page;
		var list = data.list;
		$("#data-list tr").remove();
		if (list.length > 0) {
		    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
		    var op ='<a href="javascript:;" title="修改分类" topicid='+d.topicId+' class="change-category-btn"><i class="icon i-edit"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
		    	    var tag = "";
		    	    if(d.grade>0){
		    		tag = tag +'<span class="ding">置顶</span>&nbsp;&nbsp;';
		    		op = op+'<a href="javascript:;" title="取消置顶" topicid='+d.topicId+' class="cancel-top-btn"><i class="icon i-thsortdown"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'
		    	    }else{
		    		op = op+'<a href="javascript:;" title="置顶" topicid='+d.topicId+' class="set-top-btn"><i class="icon i-thsortup"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'
		    	    }
		    	    if(d.essence==1){
		    		tag = tag +'<span class="jing">精华</span>&nbsp;&nbsp;';
		    		op = op+'<a href="javascript:;" title="取消精华" topicid='+d.topicId+' class="cancel-essence-btn"><i class="icon i-sortdown"></i></a>'
		    	    }else{
		    		op = op+'<a href="javascript:;" title="精华" topicid='+d.topicId+' class="set-essence-btn"><i class="icon i-sortup"></i></a>'
		    	    }
		    	    if(d.topicType!=null&&d.topicType.type==1){
		    		tag = tag +'<span class="vote">投票</span>&nbsp;&nbsp;';
		    	    }
		    	    $("<tr>" 
				+"<td><input type='checkbox' value='"+d.topicId+"' class='not-input' name='topic'></td>" 
				+"<td valign='center'>"+tag+"<a href='"+ulewo.absolutePath+"/bbs/"+d.topicId+"' target='_blank'>"+d.title+"</a></td>" 
				+"<td>"+d.pCategoryName+"/"+d.categoryName+"</td>"
				+"<td>"+d.userName+"</td>" 
				+"<td>"+d.commentCount+"</td>" 
				+"<td>"+d.likeCount+"</td>" 
				+"<td>"+d.collectionCount+"</td>" 
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





