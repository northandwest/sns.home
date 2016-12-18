ulewo.askUrl = {
	setBestAnswer : "set_best_answer.action"
}
$(function() {
    $(document).on("mouseenter", ".comment-item", function() {
	$(this).find("a.accept-btn").show();
    });
    $(document).on("mouseleave", ".comment-item", function() {
	$(this).find("a.accept-btn").hide();
    });
    
    $(document).on("click", "a.accept-btn", function() {
	var commentid = $(this).attr("commentid");
	acceptAnswer(commentid);
    });
})

function acceptAnswer(commentid){
	ulewo.ajaxRequest({
	    url : ulewo.askUrl.setBestAnswer,data : {
		bestAnswerId : commentid,askId:ulewo.topicId
	    }, fun : function(res) {
		 ulewo.tipMsg({
			type : "success", content : "采纳成功", timeout : 1500,fun:function(){
			    location.reload(true);
			}
		 });
	    }
	});
}
