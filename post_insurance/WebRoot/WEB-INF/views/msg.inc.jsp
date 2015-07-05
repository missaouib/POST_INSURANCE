<%@ page pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<script type="text/javascript">
	function scroll_news() {
		var $firstNode = $('#scroll-container li');
		//得到 i的值 列数 
		var iRow = $('#scroll-container').find('li').length - 1;
		//debugger; 
		$(function() {
			$firstNode.eq(0).fadeOut('slow', function() {
				$(this).clone().appendTo($(this).parent()).fadeIn('slow');
				$(this).remove();
				$firstNode.eq(iRow).hide();
			});
		});
	}
	$(document).ready(function() {
		go();
	});
	function stop() {
		clearInterval(sn);
	}
	function go() {
		sn = setInterval('scroll_news()', 2000);
	}
</script>
<style type="text/css">
#scroll-container {
	border: 1px #000 solid;
	background: #FEC;
	height: 23px;
	line-height: 23px;
}

#scroll-container li {
	list-style: none;
	display: none;
	font-size: 12px;
}

li a {
	color: #F00;
	text-decoration: none;
	margin-left: 10px;
}

li a:hover {
	text-decoration: underline;
}
</style>
	<div id="scroll-container" onMouseOver="stop();" onMouseOut="go();">
		<li><a href="#">一生这么一次</a></li>
		<li><a href="#">不再因为任性而不肯低头</a></li>
		<li><a href="#">不再因为固执而轻言分手</a></li>
		<li><a href="#">坚信一次</a></li>
		<li><a href="#">一颗心需要坚定地去温暖另一颗心</a></li>
		<li><a href="#">一直走</a></li>
		<li><a href="#">就可以到白头</a></li>
		<li><a href="#">就那样相守</a></li>
		<li><a href="#">在来往的流年里</a></li>
		<li><a href="#">岁月安好</a></li>
		<li><a href="#">惟愿这一生</a></li>
		<li><a href="#">执子之手</a></li>
		<li><a href="#">与子偕老</a></li>
	</div>