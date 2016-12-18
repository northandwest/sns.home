ulewo.categoryUrl = {
    loadCategories : ulewo.absolutePath + "/admin/loadBlogCategories.action", saveCategory : ulewo.absolutePath + "/admin/saveBlogCategory.action",
    delCategory : ulewo.absolutePath + "/admin/delBlogCategory.action"
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

    $(document).on("click", ".del-btn", function() {
	delCategory($(this));
    });

    $("#add-category").click(function() {
	addCategory($(this));
    });
});


function delCategory(curObj) {
    var categoryid = curObj.attr("categoryid");
    ulewo.confirm({
	msg : "确认要删除分类吗？", fun : function() {
	    ulewo.ajaxRequest({
		url : ulewo.categoryUrl.delCategory, data : {
		    categoryId : categoryid
		}, fun : function(res) {
		    ulewo.tipMsg({
			type : "success", content : "删除成功", timeout : 1500
		    });
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
	    if(list.length==0){
		$("<div class='no-data'>没有分类，点击新增分类添加吧。</div>").appendTo(ulewo.categories);
	    }else{
		for (var i = 0, _len = list.length; i < _len; i++) {
			new CategoryItem(list[i]).appendTo(ulewo.categories);
		 }
	    }
	    
	}
    });
}

function CategoryItem(data) {
    var item = $("<div class='category'></div>");
    new editItem(data, true).appendTo(item);
    return item;
}

function editItem(data) {
    var item = $("<div class='edit-item'></div>");
    var form = $("<form></form>").appendTo(item);
    $("<input type='hidden' name='categoryId' value=" + data.categoryId + ">").appendTo(form);
    $("<span class='cate-rang'><input type='text' name='rang'  readonly='readonly' placeholder='请输入序号' class='none-input' value=" + data.rang + "></span>").appendTo(form);
    $("<span class='cate-name'><input type='text' name='name' readonly='readonly' placeholder='请输入分类名称' class='none-input' value=" + data.name + "></span>").appendTo(form);
    $("<a href='javascript:;' class='edit-btn'>修改</a>").appendTo(form);
    $("<a href='javascript:;' class='save-btn'>保存</a>").appendTo(form);
    $("<a href='javascript:;' class='del-btn' categoryid=" + data.categoryId + ">删除</a>").appendTo(form);
    return item;
}

// 编辑分类
function editCategory(curObj) {
    var edit_item = curObj.parent();
    var input = edit_item.find("span input");
    var save_btn = edit_item.find(".save-btn")
    input.removeClass("none-input");
    input.removeProp("readonly");
    $(curObj).hide();
    save_btn.show();
    input.focus();
}

function addCategory(curObj) {
    var rang = 0;
    var pid = 0;
    var children_panel;
    rang = ulewo.categories.find(".category").length;
    var data = {
	name : "", categoryId : '', rang : rang+1
    }
    if(ulewo.categories.find(".no-data").length>0){
	ulewo.categories.empty();
    }
    var category = new CategoryItem(data).appendTo(ulewo.categories);
    editCategory(category.find(".edit-btn"));
}

// 保存分类
function saveCategory(curObj) {
    var form = curObj.parent();
    var name = $.trim(form.find("input[name=name]").val());
    var pid = $.trim(form.find("input[name=pid]").val());
    var rang = $.trim(form.find("input[name=rang]").val());
    if (name == "") {
	ulewo.tipMsg({
	    type : "warn", content : "分类名称不能为空", timeout : 1500
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
