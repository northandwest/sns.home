ulewo.url = {
    load_exams : "/exam/learn.action", do_mark : "/exam/do_mark.action"
}
ulewo.letters = [ "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" ];
ulewo.chooseType = {
    type1 : 1, type2 : 2
}
$(function() {
    loadExams(1);
    initExamInfoPosition();
    $(document).on("click", ".answer", function() {
	calculationAnswer($(this));
    });

    $(document).on("click", "#post-exam", function() {
	postExam();
    });

});

// 初始化位置
function initExamInfoPosition() {
    var main_width = 1100;
    var document_width = $(document).width();
    var div_width = 260;
    var left = (document_width - main_width) / 2 - div_width;
    $(".exam-info").css("left", left + "px")
}

function loadExams() {
    ulewo.ajaxRequest({
	url : ulewo.url.load_exams, data : {
	    categoryId : ulewo.categoryId
	}, fun : function(res) {
	    var list = res.data;
	    var _len = list.length;
	    $("#total-num").text(_len);
	    $("#answer_left").text(_len);
	    if (_len > 0) {
		for (var i = 0, data; i < _len, data = list[i]; i++) {
		    new ExamItem(data, i).appendTo($("#exam-list"));
		}
//		var d = dialog({
//		    title : "开始考试", content : "考题已经加载完毕，点击'开始答题'进行考试", fixed : true, cancel : false, okValue : "开始答题", ok : function() {
//			
//		    }
//		});
//		d.showModal();
		
		startDoExam();
	    } else {
		$("<div class='no-data'>该分类下没有考题</div>").appendTo($("#exam-list"));
		$("#post-exam").remove();
	    }
	}
    });
}

// 开始答题
function startDoExam() {
    var sec = $("#time-s").text();
    sec = parseInt(sec);
    if (sec < 9) {
	$("#time-s").text("0" + (sec + 1));
    } else {
	$("#time-s").text(sec + 1);
    }
    if (sec == 59) {
	$("#time-s").text("00");
	var min = $("#time-m").text();
	min = parseInt(min);
	if (min < 9) {
	    $("#time-m").text("0" + (min + 1));
	} else {
	    $("#time-m").text(min + 1);
	}
    }
    ulewo.time = setTimeout("startDoExam()", 1000);
}

// 点击答案计算结果
function calculationAnswer(curObj) {
    var item_answer = curObj.parent().parent();
    var input_checks = item_answer.find("input:checked");
    var exam_item = item_answer.parent();
    var chooseType = exam_item.attr("choosetype");
    if (chooseType == ulewo.chooseType.type1 && !item_answer.attr("checkType")) {
	$("#answer_num").text(parseInt($("#answer_num").text()) + 1);
	$("#answer_left").text(parseInt($("#answer_left").text()) - 1);
	item_answer.attr("checkType", true);
    } else if (chooseType == ulewo.chooseType.type2) {
	if (input_checks.length == 1 && curObj.prop("checked")) {
	    $("#answer_num").text(parseInt($("#answer_num").text()) + 1);
	    $("#answer_left").text(parseInt($("#answer_left").text()) - 1);
	} else if (input_checks.length == 0) {
	    $("#answer_num").text(parseInt($("#answer_num").text()) - 1);
	    $("#answer_left").text(parseInt($("#answer_left").text()) + 1);
	}
    }
}

// 交卷
function postExam() {
    var answer_left = parseInt($("#answer_left").text());
    var exam_items = $(".exam_item");
    var examIdstr = "";
    var rightAnswerstr = "";
    if (answer_left > 0) {
	for (var i = 0, _len = exam_items.length; i < _len; i++) {
	    var exam_item = exam_items.eq(i);
	    var inputs = exam_item.find("input:checked");
	    if (inputs.length == 0) {
		ulewo.tipMsg({
		    type : "warn", content : "第" + (i + 1) + "没选择答案", timeout : 1500
		});
		return false;
	    }
	}
    }

    for (var i = 0, _len = exam_items.length; i < _len; i++) {
	if (i < _len - 1) {
	    examIdstr = examIdstr + exam_items.eq(i).attr("examid") + ",";
	} else {
	    examIdstr = examIdstr + exam_items.eq(i).attr("examid");
	}
    }
    var answers = $("input:checked");
    for (var i = 0, _len = answers.length; i < _len; i++) {
	if (i < _len - 1) {
	    rightAnswerstr = rightAnswerstr + answers.eq(i).val() + ",";
	} else {
	    rightAnswerstr = rightAnswerstr + answers.eq(i).val();
	}
    }

    clearTimeout(ulewo.time);
    ulewo.ajaxRequest({
	url : ulewo.url.do_mark, data : {
	    examIdstr : examIdstr, rightAnswerstr : rightAnswerstr
	}, fun : function(res) {
	    var list = res.data;
	    var rightCount = 0;
	    for (var i = 0, _len = list.length; i < _len; i++) {
		var answer = list[i];
		setResultInfo(answer);
		if (answer.correct) {
		    rightCount++;
		}
	    }
	    $(".answer").prop("disabled", true);
	    $("#mark").text(rightCount * 2);
	    $("#post-exam").remove();
	}
    });
}

function setResultInfo(answer) {
    var examId = answer.id;
    var exam_item = $("#exam" + examId);
    // 答案正确
    if (answer.correct) {
	$("<i class='icon exam-right'>").appendTo(exam_item);
    } else {
	$("<i class='icon exam-error'>").appendTo(exam_item);
	var correctAnswerIds = answer.correctAnswerIds;
	var answerLetter = "";
	for (var i = 0, _len = correctAnswerIds.length; i < _len; i++) {
	    answerLetter = answerLetter + " " + $("#answer" + correctAnswerIds[i]).attr("letter");
	}
	$('<div class="right-answer"><span>正确答案:</span><span>' + answerLetter + '</span></div>').appendTo(exam_item);
	if (answer.analyse != null && answer.analyse != "") {
	    $('<div class="analyse"><div class="analyse-tit">答案解析</div><div class="analyse-con">' + answer.analyse + '</div>').appendTo(exam_item);
	}
    }
}

function ExamItem(exam, num) {
    var exam_item = $('<div class="exam_item" chooseType="' + exam.chooseType.type + '" checkType=false id="exam' + exam.id + '" examId="' + exam.id + '"></div>');
    var typetit = "单选";
    var userName = exam.userName;
    if (exam.chooseType.type == ulewo.chooseType.type1) {
    	typetit = "单选";
    } else if (exam.chooseType.type == ulewo.chooseType.type2) {
    	typetit = "多选";
    }
    $('<div class="item-num">第<span class="item-num-no">' + (num + 1) + '</span>题<span class="item-type">(' + typetit + ')</span> <span class="item-user">发布者：' + userName + '</span></div>').appendTo(
	    exam_item);
    $('<div class="item-tit">' + exam.examTitle + '</div>').appendTo(exam_item);
    var item_answer = $('<div class="item-answer"></div>').appendTo(exam_item);
    var subs = exam.examDetails;
    for (var i = 0, _len = subs.length; i < _len; i++) {
	var letter = ulewo.letters[i];
	if (exam.chooseType.type == ulewo.chooseType.type1) {
	    $(
		    '<label style="width:98%" id="answer' + subs[i].id + '" letter="' + letter + '"><input type="radio" class="not-input answer"  name="answer' + exam.id + '" class="answer" value="' + subs[i].id
			    + '" ><span>' + letter + '.' + subs[i].answer + '</span></label>').appendTo(item_answer);
	} else if (exam.chooseType.type == ulewo.chooseType.type2) {
	    $(
		    '<label  style="width:98%" id="answer' + subs[i].id + '" letter="' + letter + '"><input type="checkbox" class="not-input answer" name="answer' + exam.id + '"  class="answer" value="' + subs[i].id
			    + '"><span>' + letter + '.' + subs[i].answer + '</span></label>').appendTo(item_answer);
	}
    }
    return exam_item;
}
