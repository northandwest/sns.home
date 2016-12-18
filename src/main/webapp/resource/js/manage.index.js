$(function(){
    ulewo.ajaxRequest({
	url : ulewo.absolutePath + "/manage/index/load_data", showLoad : true, fun : function(res) {
	    var list = res.data;
	    for (var i = 0, _len = list.length; i < _len; i++) {
		  var chart = echarts.init($(".chart").eq(i)[0]);
		  chart.setOption(list[i]); 
	    }
	}
    });
});