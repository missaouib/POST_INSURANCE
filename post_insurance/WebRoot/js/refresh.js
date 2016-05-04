function doRefresh() {
    $.ajaxSettings.global = false;
    $.ajax({
        type: 'post',
        url: "/refresh/alert",
        dataType: "text",
        data: {},
        success: function (data) {
        	if(data) {
        		alert("您有超过10天的人核件待跟进！请及时跟进处理");
        	}
        }
    });
    $.ajaxSettings.global = true;
}

window.setInterval("doRefresh()", 10 * 60 * 1000);

function checkMessage() {
	$.ajaxSettings.global = false;
	$.ajax({
		type: "get", 
		dataType: "html",
		url: "/refresh/checkTodoList?s=" + Math.random(),
		data: "", 
		success: function(data) {
			if (data!="") {
				var str = "";
				var flag = -1;
				//alert(data);
				if(data.indexOf("KFGD")>0) {
					flag = 1;
					str = str + "问题工单、";
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
				var info = "您还有" + str + "等未完成处理，请及时跟进！"
				if(flag == 1) {
					$.messager.anim('fade', 2000); 
					$.messager.show('<font color=red>待办任务提醒</font>',info,3000);
				}
			} 
		} 
	});
	$.ajaxSettings.global = true;
}
