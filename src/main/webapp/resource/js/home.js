$(function(){
    $("#doSignIn").click(function() {
	var haveSignInType = $(this).attr("haveSignInType");
	if (haveSignInType == "true") {
	    return;
	}
	if (ulewo.user.userId == "") {
	    var url = ulewo.curUrl;
	    url = encodeURI(url);
	    document.location.href = ulewo.ucenterAbsolutePath+"/login?redirect=" + url;
	    return;
	}
	ulewo.ajaxRequest({
	    async : true, url : ulewo.absolutePath+"/doSignIn.action", fun : function(res) {
		$("#index_count").text(parseInt($("#index_count").text())+1);
		setSignInInfo(res);
		var msg = "2积分已到碗里";
		if (res.data.continueSigIn) {
		    msg = "连续7天签到10积分已到碗里";
		}
		ulewo.tipMsg({
		    type : "success", content : msg, timeout : 3000
		});
	    }
	});
    });
    
    loadWeatherInfo();
})


function loadWeatherInfo(){
	ulewo.ajaxRequest({
	    async : true,showLoad:false, url : ulewo.absolutePath+"/loadWeatherInfo.do", fun : function(res) {
		    $("#weather").empty();
		    //天气预报
		    var weatherData = res.data;
		    var weatherItem = $("<div></div>").appendTo($("#weather"));
		    $("<div class='city-info'><span class='city'>"+weatherData.city+"</span><span class='loc'>"+weatherData.loc+"&nbsp;&nbsp;天气实况</span></div>").appendTo(weatherItem);
		    var imgCon = $("<div class='weather-img'></div>").appendTo(weatherItem);
		    $("<div class='img'><img src='"+ulewo.absolutePath+"/resource/images/weather/"+weatherData.cond.code+".png'></div>").appendTo(imgCon);
		    var weather_info = $("<div class='weather-info'></div>").appendTo(imgCon);
		    $("<div class='cond'>"+weatherData.cond.txt+"</span><span class='tmp'>"+weatherData.tmp+"℃</div>").appendTo(weather_info);
		    $("<div class='sc'>"+weatherData.wind.dir+weatherData.wind.sc+"</span><span class='hum'>湿度："+weatherData.hum+"%</div>").appendTo(weather_info);
	    }
	});
}

function setSignInInfo(res) {
    var signInBtn = $("#doSignIn");
    signInBtn.find("#sign-info .sign-tit").text("已签到");
    signInBtn.attr("haveSignInType", true);
    $("#todaySigInCount").text(parseInt($("#todaySigInCount").text()) + 1);
    $("#userSignInCount").text(parseInt($("#userSignInCount").text()) + 1);
    $("#sign-days").text(parseInt($("#sign-days").text()) + 1);
}