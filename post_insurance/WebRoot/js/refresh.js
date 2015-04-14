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
		url: "/members/message/check?s=" + Math.random(),
		data: "", 
		success: function(data) {
			if (data!="") {
				var str = "";
				var flag = -1;
				if(data == "newMsg") {
					flag = 1;
					str = "有新的留言，请及时查看并处理！";
				} else if(data=="newReply") {
					flag = 1;
					str = "留言有新的回复，请及时查看处理！";
				} else if(data == "newQuestion") {
					flag = 1;
					str = "留言有新的疑问，请及时查看处理！";
				} else if(data == "fixed") {
					//flag = 1;
					//str = "有留言已经处理完成，请查看！";
				}
				if(flag == 1) {
					$.messager.anim('fade', 2000); 
					$.messager.show('<font color=red>留言提醒</font>',str,10000);
				}
			} 
		} 
	});
	$.ajaxSettings.global = true;
}
