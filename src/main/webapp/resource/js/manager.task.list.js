ulewo.searchParam = "";
ulewo.url = {
	loadList : ulewo.absolutePath+"/manage/task/load_task",// 列表
	taskSuspended : ulewo.absolutePath+"/manage/task/task_suspended",// 暂停
	taskEnable : ulewo.absolutePath+"/manage/task/task_enable",// 启用
	taskExecution : ulewo.absolutePath+"/manage/task/immediate_execution",// 立即执行
	taskDelete : ulewo.absolutePath+"/manage/task/task_del"// 删除
    }
$(function(){
    initPage(1);
   
    /**
     * 暂停
     */
    $(document).on("click", ".op-disabled", function() {
	var taskId = $(this).attr("taskId");
	ulewo.confirm({
	    msg : "确认要暂停吗？", 
	    fun :function() {
		ulewo.ajaxRequest({
			url : ulewo.url.taskSuspended, // 请求的url
			data : {
			    "id" : taskId
			}, // 请求参数
			fun : function(response) {// 回调方法
			    initPage(ulewo.pageNo);
			}
		    });
	    }
	})
    });

    /**
     * 启用
     */
    $(document).on("click", ".op-enabled", function() {
	var taskId = $(this).attr("taskId")
	ulewo.confirm({
	    msg : "确认要启用吗？", 
	    fun :function() {
		   ulewo.ajaxRequest({
			url : ulewo.url.taskEnable, // 请求的url
			data : {
			    "id" : taskId
			}, // 请求参数
			fun : function(response) {// 回调方法
			    initPage(ulewo.pageNo);
			}
		    });
	    }
	})
    });

    /**
     * 立即执行
     */
    $(document).on("click", ".op-execution", function() {
	var taskId = $(this).attr("taskId");
	ulewo.confirm({
	    msg : "确认要执行吗？", 
	    fun :function() {
		  ulewo.ajaxRequest({
			url : ulewo.url.taskExecution, // 请求的url
			data : {
			    "id" : taskId
			}, // 请求参数
			fun : function(response) {// 回调方法
			    initPage(ulewo.pageNo);
			}
		    });
	    }
	})
    });

    /**
     * 删除
     */
    $(document).on("click", ".op-del", function() {
	var taskId = $(this).attr("taskId")
	ulewo.confirm({
	    msg : "确认要删除吗？", 
	    fun :function() {
        	    ulewo.ajaxRequest({
        		url : ulewo.url.taskDelete, // 请求的url
        		data : {
        		    "ids" : taskId
        		}, // 请求参数
        		fun : function(response) {// 回调方法
        		    initPage(ulewo.pageNo);
        		}
        	    });
	    }
	})
    });
    
})

function initPage(pageNo) {
	ulewo.pageNo = pageNo;
	var data = ulewo.searchParam + "&pageNo=" + pageNo;
	ulewo.ajaxRequest({
	    async : true, url : ulewo.url.loadList, data : data, fun : function(response) {
		fillPage(response);
	    }
	});
    }

function fillPage(response) {
	var data = response.data;
	var simplePage = data.page;
	var contentArr = [];
	var list = data.list;
	$("#pager_content tr").remove();
	if (list.length > 0) {
	    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
		var edit = '<a href="task_2edit_task?id=' + d.id + '" title="修改"><i class="icon i-edit"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
		    if (d.taskStatus.status == 0) {
			edit = edit + '<a href="javascript:void(0)" class="op-disabled" taskId=' + d.id + ' title="暂停"><i class="icon i-disabled"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
		    } else if (d.taskStatus.status == 1) {
			edit = edit + '<a href="javascript:void(0)" class="op-enabled" taskId=' + d.id + ' title="启用"><i class="icon i-effectiveness"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
		    }
		    edit = edit + '<a href="javascript:void(0)" class="op-execution" taskId=' + d.id + ' title="立即执行"><i class="icon i-generate"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
		    edit = edit + '<a href="javascript:void(0)" class="op-del" taskId=' + d.id + ' title="删除"><i class="icon i-del"></i></a>';
		$(
			'<tr><td>' + d.taskClassz + '</td><td>' + d.taskMethod + '</td><td>' + (d.description==null?"":d.description) + '</td><td>' + d.taskTime + '</td><td>' + d.taskStatus.description + '</td><td>' + d.lastupdateTime + '</td><td>'
				+ edit + '</td></tr>').appendTo($("#pager_content"));
	    }
	} else {
	    $('<tr><td  colspan="100"><div  class="no-data">没有数据</div></td></tr>').appendTo($("#pager_content"));
	}

	ulewo.pagination({
	    pagePanelId : "pager", pageObj : simplePage, fun : initPage
	});
    }