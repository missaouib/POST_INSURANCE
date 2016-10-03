<%@ page language="java" contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
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

<div style="color: red;font-size: 15px; font-weight:bold;">请按人核件处理规范要求，登记人核件各环节时效：必须登记人核件寄出</div><br>
<div id="scrollDiv_keleyi_com" class="scrollDiv">
	<div id="status_scrollDiv_keleyi_com">↑</div>
	<ul>
		<li><a href="http://keleyi.com/a/bjac/532bedbffca1affa.htm">单行文字间歇向上滚动，放光标就停，移开继续滚动</a></li>
		<li><a href="http://keleyi.com/a/bjac/5d7f4ac2efa72d30.htm">菜单滚动至顶部后固定</a></li>
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
			}, 1000, function() {
				$(this).css({
					marginTop : "0px"
				}).find("li:first").appendTo(this);
			});
		}
	}
	function KeleyiScroll(obj, statusobj) {
		setInterval('AutoScroll("' + obj + '","' + statusobj + '")', 5000);
		$(obj).hover(
			function() {
				$(statusobj).text('=')
			},
			function () {
			$(statusobj).text('↑')
			});
	}
</script>