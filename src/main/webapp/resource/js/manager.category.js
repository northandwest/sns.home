ulewo.categoryUrl = {
    loadCategories : ulewo.absolutePath+"/manage/category/loadCategories.action", saveCategory : ulewo.absolutePath+"/manage/category/saveCategory.action",
    refreshCategory : ulewo.absolutePath+"/manage/category/refreshCategory",
    delCategory : ulewo.absolutePath+"/manage/category/delCategory"
};
$(function() {
    ulewo.categories = $("#categories");

    loadCategories();

    $(document).on("click", ".edit-btn", function() {
	editCategory($(this));
    });

    $(document).on("click", ".save-btn", function() {
	saveCategory($(this));
    });

    $("#add-category").click(function() {
	addCategory($(this), true);
    });
    
    $("#refresh-category").click(function() {
	rereshCategory($(this), true);
    }); 
    
    $(document).on("click", ".add-sub-btn", function() {
	addCategory($(this), false);
    });
    
    $(document).on("click", ".del-btn", function() {
	delCategory($(this).attr("categoryid"));
    });
    
});

//删除
function delCategory(categoryId){
    ulewo.confirm({
	msg : "确认要删除吗？", fun : function() {
	    ulewo.ajaxRequest({
		url : ulewo.categoryUrl.delCategory,data:{
		    categoryId:categoryId
		}, fun : function(res) {
		    loadCategories();
		}
	    });
	}
    });
   
}

function loadCategories() {
    ulewo.categories.empty();
    ulewo.ajaxRequest({
	url : ulewo.categoryUrl.loadCategories, fun : function(res) {
	    var list = res.data;
	    for (var i = 0, _len = list.length; i < _len; i++) {
		new CategoryItem(list[i]).appendTo(ulewo.categories);
	    }
	}
    });
}

function CategoryItem(data) {
    var item = $("<div class='category'></div>");
    new editItem(data, true).appendTo(item);
    var children = data.children;
    var children_panel = $("<div class='children'></div>").appendTo(item);
    if (children.length > 0) {
	for (var i = 0, _len = children.length; i < _len; i++) {
	    var child = children[i];
	    new editItem(child, false).appendTo(children_panel);
	}
    }

    return item;
}

function editItem(data, pcategory) {
    var showInBBS = data.showInBBS == "Y" ? "checked=checked" : "";
    var showInQuestion = data.showInQuestion == "Y" ? "checked=checked" : "";
    var showInExam = data.showInExam == "Y" ? "checked=checked" : "";
    var showInKnowledge = data.showInKnowledge == "Y" ? "checked=checked" : "";
    var allowPost = data.allowPost == "Y" ? "checked=checked" : "";
    var item = $("<div class='edit-item'></div>");
    var form = $("<form></form>").appendTo(item);
    $("<input type='hidden' name='pid' value=" + data.pid + ">").appendTo(form);
    $("<input type='hidden' name='categoryId' value=" + data.categoryId + ">").appendTo(form);
    $("<span class='cate-rang'><input type='text' name='rang'  readonly='readonly' placeholder='请输入序号' class='none-input' value=" + data.rang + "></span>").appendTo(form);
    $("<span class='cate-name'><input type='text' name='name' readonly='readonly' placeholder='请输入分类名称' class='none-input' value=" + data.name + "></span>").appendTo(form);
    if (pcategory) {
	$("<span class='cate-desc'><input type='text' name='description' readonly='readonly' placeholder='请输入分类描述' class='none-input' value=" + data.description + "></span>").appendTo(form);
    }
    $("<label><input type='checkBox' disabled='disabled' " + showInBBS + " name='showInBBS' value='Y'>论坛</label>").appendTo(form);
    $("<label><input type='checkBox' disabled='disabled' " + showInQuestion + " name='showInQuestion' value='Y'>问答</label>").appendTo(form);
    $("<label><input type='checkBox' disabled='disabled' " + showInExam + " name='showInExam' value='Y'>在线考试</label>").appendTo(form);
    $("<label><input type='checkBox' disabled='disabled' " + showInKnowledge + " name='showInKnowledge' value='Y'>知识库</label>").appendTo(form);
    $("<label><input type='checkBox' disabled='disabled' " + allowPost + " name='allowPost' value='Y'>是否可以发帖</label>").appendTo(form);
    $("<a href='javascript:;' class='edit-btn'>修改</a>").appendTo(form);
    $("<a href='javascript:;' class='save-btn'>保存</a>").appendTo(form);
    if (pcategory) {
	$("<a href='javascript:;' class='add-sub-btn'>新增二级分类</a>").appendTo(form);
    }
    $("<a href='javascript:;' class='del-btn' categoryid ="+data.categoryId+" >删除</a>").appendTo(form);
    return item;
}

// 编辑分类
function editCategory(curObj) {
    var edit_item = curObj.parent();
    var input = edit_item.find("span input");
    var checkBox = edit_item.find("input[type=checkBox]");
    var save_btn = edit_item.find(".save-btn")
    input.removeClass("none-input");
    input.removeProp("readonly");
    checkBox.removeProp("disabled");
    $(curObj).hide();
    save_btn.show();
    input.focus();
}

function addCategory(curObj, pcategory) {
    var rang = 0;
    var pid = 0;
    var children_panel;
    if (pcategory) {
	rang = ulewo.categories.find(".category").length + 1;
    } else {
	children_panel = curObj.parent().parent().parent().find(".children").eq(0);
	rang = children_panel.find(".edit-item").length + 1;
	pid = curObj.parent().find("input[name='categoryId']").val();
    }
    var data = {
	name : "", categoryId : '', description : "", pid : pid, rang : rang, children : []
    }
    var category = null;
    if (pcategory) {
	category = new CategoryItem(data).appendTo(ulewo.categories);
    } else {
	category = new editItem(data, false).appendTo(children_panel);
    }
    editCategory(category.find(".edit-btn"));
}

function rereshCategory(){
    ulewo.ajaxRequest({
	url : ulewo.categoryUrl.refreshCategory,fun : function(res) {
	    ulewo.tipMsg({
		type : "success", content : "刷新成功", timeout : 1500
	    });
	}
    });
}

// 保存分类
function saveCategory(curObj) {
    var form = curObj.parent();
    var name = $.trim(form.find("input[name=name]").val());
    var pid = $.trim(form.find("input[name=pid]").val());
    var rang = $.trim(form.find("input[name=rang]").val());
    var description = $.trim(form.find("input[name=description]").val());
    if (name == "") {
	ulewo.tipMsg({
	    type : "warn", content : "分类名称不能为空", timeout : 1500
	});
	return;
    }
    if (description == "" && pid == 0) {
	ulewo.tipMsg({
	    type : "warn", content : "分类描述不能为空", timeout : 1500
	});
	return;
    }
    if (rang == "" || !ulewo.regs().number.reg.test(rang)) {
	ulewo.tipMsg({
	    type : "warn", content : "编号只能是数字，且不能为空", timeout : 1500
	});
	return;
    }
    ulewo.ajaxRequest({
	url : ulewo.categoryUrl.saveCategory, data : form.serialize(), fun : function(res) {
	    ulewo.tipMsg({
		type : "success", content : "保存成功", timeout : 1500
	    });
	    loadCategories();
	}
    });
}
