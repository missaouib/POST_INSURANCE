<%@ page language="java" contentType="text/html;charset=UTF-8"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<style type="text/css">
.scrollDiv {
	height: 18px;
	width: 500px; /* 必要元素 */
	line-height: 18px;
	border: #ccc 1px solid;
	overflow: hidden; /* 必要元素 */
}

.scrollDiv li {
	height: 18px;
	padding-left: 10px;
}

ul, li {
	list-style-type: none;
	margin: 0px;
	padding: 0px;
}

#status_scrollDiv_keleyi_com {
	float: left;
	width: 18px;
	font-weight: bold;
	color: red;
}
</style>
<div style="color: #00F;font-size: 15px; font-weight:bold;">请按人核件处理规范要求，登记人核件各环节时效：必须登记人核件寄出日期</div><br>
<div id="scrollDiv_keleyi_com" class="scrollDiv">
	<div id="status_scrollDiv_keleyi_com">↑</div>
	<ul>
		<%
		String ods = (String)request.getAttribute("noticeMsg");
		String[] s = ods.split("\\|");
		if(s[0] != null && new Integer(s[0]) > 0) {
		%>
		<li><a target="dialog" mask="true" width="800" height="440" style="color:#F00;font-weight: bold;" href="${contextPath}/qygl/uwlist2pop">共<%=s[0] %>件人核件寄出时效逾期（弹窗提醒），点击查看</a></li>
		<%
		};
		if(s[1] != null && new Integer(s[1]) >0) {
		%>
		<li><a target="dialog" mask="true" width="800" height="440" style="color:#F00;font-weight: bold;" href="${contextPath}/qygl/uwlist2call">共<%=s[1] %>件人核件全程时效逾期（电话提醒），点击查看</a></li>
		<%
		};
		if(s[2] != null && new Integer(s[2]) >0) {
		%>
		<li><a target="dialog" mask="true" width="800" height="440" style="color:#F00;font-weight: bold;" href="${contextPath}/qygl/uwlist2weixin">共<%=s[2] %>件人核件逾期时间较长，需上微信催办（微信催办），点击查看</a></li>
		<%
		};
		%>
	</ul>
</div>
<br>
<script type="text/javascript">
	$(document).ready(function() {
		KeleyiScroll("#scrollDiv_keleyi_com", "#status_scrollDiv_keleyi_com");
	});
	function AutoScroll(obj, statusobj) {
		if ($(statusobj).text() == '↑') {
			$(obj).find("ul:first").animate({
				marginTop : "-18px"
			}, 8000, function() {
				$(this).css({
					marginTop : "2px"
				}).find("li:first").appendTo(this);
			});
		}
	}
	function KeleyiScroll(obj, statusobj) {
		setInterval('AutoScroll("' + obj + '","' + statusobj + '")', 8000);
		$(obj).hover(
			function() {
				$(statusobj).text('=')
			},
			function () {
			$(statusobj).text('↑')
			});
	}
</script>