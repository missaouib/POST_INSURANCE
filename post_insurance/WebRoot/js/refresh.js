function doRefresh() {
    $.ajaxSettings.global = false;
    $.ajax({
        type: 'post',
        url: "/refresh/alert",
        dataType: "text",
        data: {},
        success: function (data) {
        	if (data!="") {
				var str = "提醒：";
				var flag = 0;
				if(data.indexOf("QYGD")>0) {
					flag = 1;
					str = str + "您有超15天未回销登记的人核件；";
				}
	        	if(data.indexOf("KFGD")>0) {
					flag = 1;
					str = str + "您有超4天未回复的客服工单，";
				}
	        	if(data.indexOf("IQGD")>0) {
					flag = 1;
					str = str + "您有超4天未回复的客服咨询工单.";
				}
	        	if(flag = 1) {
	        		alert(str + "请及时处理");
	        	}
        	}
        }
    });
    $.ajaxSettings.global = true;
}

window.setInterval("doRefresh()", 60 * 8 * 60 * 1000);

function checkMessage() {
	$.ajaxSettings.global = false;
	$.ajax({
		type: "get", 
		dataType: "html",
		url: "/refresh/checkTodoList?s=" + Math.random(),
		data: "", 
		success: function(data) {
			if (data!="") {
				/*
				var str = "";
				var flag = -1;
				if(data.indexOf("KFGD")>0) {
					flag = 1;
					str = str + "问题工单、<br>";
				}
				if(data.indexOf("ZXGD")>0) {
					flag = 1;
					str = str + "咨询工单、<br>";
				}
				
				if(data.indexOf("HFGD")>0) {
					flag = 1;
					str = str + "回访不成功件、";
				}
				if(data.indexOf("BQGD")>0) {
					flag = 1;
					str = str + "保全复核问题、";
				}
				
				if(data.indexOf("TXGD")>0) {
					flag = 1;
					str = str + "新契约填写不合格件、";
				}
				if(data.indexOf("LRGD")>0) {
					flag = 1;
					str = str + "新契约录入不合格件、";
				}
				if(data.indexOf("XQGD")>0) {
					flag = 1;
					str = str + "续期催收件";
				}
				*/
				//alert(data);
				var info = "请及时跟进：<br>" + data;
				alertMsg.warn(info);
				//alert(flag);
				//alert(info);
				if(flag == 1) {
					//$.messager.anim('fade', 2000); 
					//$.messager.show('<font color=red>待办任务提醒</font>',info);
					alertMsg.warn(info);
				}
			} 
		} 
	});
	$.ajaxSettings.global = true;
}

function checkUrge() {
	$.ajaxSettings.global = false;
	$.ajax({
		type: "get", 
		dataType: "html",
		url: "/refresh/checkUrge?s=" + Math.random(),
		data: "", 
		success: function(data) {
			if (data!="") {
				var info = "提醒：" + data;
				alertMsg.warn(info);
			} 
		} 
	});
	$.ajaxSettings.global = true;
}

function DateDiff(sDate1, sDate2) {  //sDate1和sDate2是yyyy-MM-dd格式
	  
    var aDate, oDate1, oDate2, iDays;
    aDate = sDate1.split("-");
    oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);  //转换为yyyy-MM-dd格式
    if(sDate2 == "") {
    	var day2 = new Date();
    	day2.setTime(day2.getTime());
    	sDate2 = day2.getFullYear()+"-" + (day2.getMonth()+1) + "-" + day2.getDate();
    }
    aDate = sDate2.split("-");
    oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
    iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
    //alter(iDays);
    return iDays;  //返回相差天数
}

function urlCheckOverDate(pcount,fd, sDate1, sDate2, url) {
	if(pcount<=5000) {
		window.open(url,"_blank"); 
		return true;
	}
	if((DateDiff(sDate1, sDate2) > fd) || pcount>5000) {
		alert("服务器：请不要下载超过5000条记录或者日期超过" + fd + "天的数据啦！");
		return false;
	}
	window.open(url,"_blank"); 
	return true;
}