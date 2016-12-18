/*
 * 用户列表
 */
define(function(require, exports, module) {
    require("common/umei.dev");
    umei.url = {
	loadList : "loadTaskList.do",// 列表
	taskSuspended : "task_suspended.do",// 暂停
	taskEnable : "task_enable.do",// 启用
	taskExecution : "immediate_execution.do",// 立即执行
	taskDelete : "task_del.do"// 删除
    }

    initPage(1);

    // 初始化页面列表
    function initPage(pageNo) {
	umei.pageNo = pageNo;
	var data = umei.searchParam + "&pageNo=" + pageNo;
	umei.postRequest({
	    async : true, url : umei.url.loadList, data : data, fun : function(response) {
		fillPage(response);
	    }
	});
    }

    function fillPage(response) {
	var data = response.data;
	var simplePage = data.page;
	var contentArr = [];
	var list = data.list;
	$("#pager_content tr:gt(0)").remove();
	if (list.length > 0) {
	    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
		var edit = "";
		if (umei.op.showEdit == "true") {
		    edit = edit + '<a href="task_2editTask.do?id=' + d.id + '" title="修改"><i class="icon i-edit"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
		}
		if (umei.op.showSuspended == "true") {
		    if (d.taskStatus.status == 0) {
			edit = edit + '<a href="javascript:void(0)" class="op-disabled" taskId=' + d.id + ' title="暂停"><i class="icon i-disabled"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
		    } else if (d.taskStatus.status == 1) {
			edit = edit + '<a href="javascript:void(0)" class="op-enabled" taskId=' + d.id + ' title="启用"><i class="icon i-effectiveness"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
		    }
		}
		if (umei.op.showExecution == "true") {
		    edit = edit + '<a href="javascript:void(0)" class="op-execution" taskId=' + d.id + ' title="立即执行"><i class="icon i-generate"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
		}
		if (umei.op.showDel == "true") {
		    edit = edit + '<a href="javascript:void(0)" class="op-del" taskId=' + d.id + ' title="删除"><i class="icon i-del"></i></a>';
		}
		$(
			'<tr><td>' + d.taskClassz + '</td><td>' + d.taskMethod + '</td><td>' + (d.description==null?"":d.description) + '</td><td>' + d.taskTime + '</td><td>' + d.taskStatus.description + '</td><td>' + d.lastupdateTime + '</td><td>'
				+ edit + '</td></tr>').appendTo($("#pager_content"));
	    }
	} else {
	    $('<tr><td  class="nodata" colspan="100">没有数据</td></tr>').appendTo($("#pager_content"));
	}

	umei.pagination({
	    pagePanelId : "pager", pageObj : simplePage, fun : initPage
	});
    }

    /**
     * 暂停
     */
    $(document).on("click", ".op-disabled", function() {
	var taskId = $(this).attr("taskId")
	umei.confirm("确认要暂停吗？", function() {
	    umei.postRequest({
		async : true, // 是否异步加载
		url : umei.url.taskSuspended, // 请求的url
		data : {
		    "id" : taskId
		}, // 请求参数
		fun : function(response) {// 回调方法
		    initPage(umei.pageNo);
		}
	    });
	})
    });

    /**
     * 启用
     */
    $(document).on("click", ".op-enabled", function() {
	var taskId = $(this).attr("taskId")
	umei.confirm("确认要暂停吗？", function() {
	    umei.postRequest({
		async : true, // 是否异步加载
		url : umei.url.taskEnable, // 请求的url
		data : {
		    "id" : taskId
		}, // 请求参数
		fun : function(response) {// 回调方法
		    initPage(umei.pageNo);
		}
	    });
	})
    });

    /**
     * 立即执行
     */
    $(document).on("click", ".op-execution", function() {
	var taskId = $(this).attr("taskId")
	umei.confirm("确认要暂停吗？", function() {
	    umei.postRequest({
		async : true, // 是否异步加载
		url : umei.url.taskExecution, // 请求的url
		data : {
		    "id" : taskId
		}, // 请求参数
		fun : function(response) {// 回调方法
		    initPage(umei.pageNo);
		}
	    });
	})
    });

    /**
     * 删除
     */
    $(document).on("click", ".op-del", function() {
	var taskId = $(this).attr("taskId")
	umei.confirm("确认要暂停吗？", function() {
	    umei.postRequest({
		async : true, // 是否异步加载
		url : umei.url.taskDelete, // 请求的url
		data : {
		    "ids" : taskId
		}, // 请求参数
		fun : function(response) {// 回调方法
		    initPage(umei.pageNo);
		}
	    });
	})
    });

});
