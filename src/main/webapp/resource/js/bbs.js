$(function(){
    $("#post-btn").click(function(){
	if(ulewo.user.userId==""){
	    ulewo.goLogin();
	    return;
	}
	document.location.href=ulewo.absolutePath+"/bbs/post_topic.action";
    });
})