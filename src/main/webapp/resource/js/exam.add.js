ulewo.letter = [ "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" ];
ulewo.url = {
    saveExam : "add_exam.action"
}

$(function() {
    ulewo.initForm($("form.u-form").eq(0));

    $("#add-answer").click(function() {
	addAnswer();
    });

    $(document).on("click", ".del-answer", function() {
	delAnswer($(this));
    });

    $("#post-exam").click(function() {
	postExam();
    });
});

// 提交考题
function postExam() {
    var formObj = $("form.u-form").eq(0);
    var result = ulewo.checkForm(formObj);
    if (!result) {
	result = false;
    }
    var examChooseType = $("input[name='chooseType']:checked").val();
    var isRightAnswers = $("input[name='rightAnswer']:checked");
    var rightAnswers = $("input[name='rightAnswer']");
    var isRightAnswerLength = isRightAnswers.length;
    var answerLength = rightAnswers.length;
    var lastAnswer = rightAnswers.eq(answerLength - 1).parent();
    if (isRightAnswers.length == 0) {
	ulewo.setError(lastAnswer, "请选择正确答案");
	result = false;
    }
    if (examChooseType == 1 && isRightAnswerLength > 1) {
	ulewo.setError(lastAnswer, "单选题只能有一个答案");
	result = false;
    }
    if (examChooseType == 2 && isRightAnswerLength.length == answerLength) {
	ulewo.setError(lastAnswer, "不能所有答案都为正确答案");
	result = false;
    }
    if (!result) {
	return;
    }
    for (var i = 0, _len = rightAnswers.length; i < _len; i++) {
	rightAnswers.eq(i).val(i);
    }
    ulewo.ajaxRequest({
	url : ulewo.url.saveExam, data : $("#exam-form").serialize(), fun : function(res) {
	    var content = "<div class='dialog-tip-msg'><div class='dialog-tip-msg-icon success'></div><div class='dialog-tip-msg-content'>考题发布成功</div></div>";
	    ulewo.dialog = dialog({
		title : "发布成功", content : content, fixed : true, cancel : false, button : [ {
		    value : '继续发布考题', callback : function() {
			formObj[0].reset();
			$(".add-answer").remove();
			$(".add-right-answer").remove();
		    }, autofocus : true
		}, {
		    value : '返回考试大厅', callback : function() {
			document.location.href = ulewo.absolutePath + "/exam";
		    }
		} ]
	    });
	    ulewo.dialog.showModal();
	}
    });
}

// 添加答案
function addAnswer() {
    var index = $("#answer-list input[name='answer']").length;
    if (index == 10) {
	ulewo.tipMsg({
	    type : "warn", content : "最多只能添加10个答案", timeout : 1500
	});
	return;
    }
    var letter = ulewo.letter[index];
    // 插入答案
    $(
	    "<div class='answer-item add-answer'><span class='letter'>" + letter
		    + ":</span><input type='text' name='answer' class='check-code' placeholder='请输入答案'  checkData='{rq:true,msg:\"答案内容不能为空\"}'>&nbsp;&nbsp;<a href='javascript:;' index=" + index
		    + " class='icon icon-del del-answer'></a></div>").appendTo($("#answer-list"));
    // 插入答案选择
    $('<label class="add-right-answer">&nbsp;&nbsp;<input class="not-input" type="checkbox" name="rightAnswer"/><span>' + letter + '</span></label>').appendTo(
	    $("#right-answer-list"));
}

function delAnswer(_this) {
    _this.parent().remove();
    var index = _this.attr("index");
    
    var answer_items = $("#answer-list").find(".answer-item");
    var right_answers = $("#right-answer-list").find("label");
    right_answers.eq(index).remove();
    right_answers = $("#right-answer-list").find("label");
    for(var i=0,_len=answer_items.length;i<_len;i++){
	answer_items.eq(i).find("span.letter").html(ulewo.letter[i]+":");
	answer_items.eq(i).find("a.del-answer").attr("index",i);
    }
    for(var i=0,_len=right_answers.length;i<_len;i++){
	right_answers.eq(i).find("span").html(ulewo.letter[i]);
    }
}
