function doRefresh() {
    $.ajaxSettings.global = false;
    $.ajax({
        type: 'post',
        url: "/refresh",
        dataType: "text",
        data: {},
        success: function (data) {
        }
    });
    $.ajaxSettings.global = true;
}

window.setInterval("doRefresh()", 6 * 60 * 1000);

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
				if(data == "issue") {
					flag = 1;
					str = "还有问题工单未处理，请及时查看并处理！";
				} else if(data=="hfissue") {
					flag = 1;
					str = "还有回访不成功件未处理，请及时查看处理！";
				} else if(data == "bqissue") {
					flag = 1;
					str = "还有保全复核问题未处理，请及时查看处理！";
				} else if(data == "writecheck") {
					flag = 1;
					str = "还有新契约填写不合格件，请及时查看处理！";
				} else if(data == "recordcheck") {
					flag = 1;
					str = "还有新契约录入不合格件，请及时查看处理！";
				} else if(data == "xqissue") {
					flag = 1;
					str = "还有续期催收件，请及时查看处理！";
				}
				if(flag == 1) {
					$.messager.anim('fade', 2000); 
					$.messager.show('<font color=red>待办任务提醒</font>',str,10000);
				}
			} 
		} 
	});
	$.ajaxSettings.global = true;
}
