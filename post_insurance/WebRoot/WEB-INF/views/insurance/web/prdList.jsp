<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中邮保险广东分公司-运营管理平台</title>

<script src="${contextPath}/styles/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.event.drag-1.5.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.touchSlider.js"></script>
<link href="${contextPath}/css/common.css" rel="stylesheet"/>
<script type="text/javascript">
$(document).ready(function () {
	$(".main_visual").hover(function(){
		$("#btn_prev,#btn_next").fadeIn()
		},function(){
		$("#btn_prev,#btn_next").fadeOut()
		})
	$dragBln = false;

	$(".main_image").touchSlider({
		flexible : true,
		speed : 1000,
		btn_prev : $("#btn_prev"),
		btn_next : $("#btn_next"),
		paging : $(".flicking_con a"),
		counter : function (e) {
			$(".flicking_con a").removeClass("on").eq(e.current-1).addClass("on");
		}
	});
	$(".main_image").bind("mousedown", function() {
		$dragBln = false;
	})
	$(".main_image").bind("dragstart", function() {
		$dragBln = true;
	})
	$(".main_image a").click(function() {
		if($dragBln) {
			return false;
		}
	})
	timer = setInterval(function() { $("#btn_next").click();}, 3000);
	$(".main_visual").hover(function() {
		clearInterval(timer);
	}, function() {
		timer = setInterval(function() { $("#btn_next").click();}, 3000);
	})
	$(".main_image").bind("touchstart", function() {
		clearInterval(timer);
	}).bind("touchend", function() {
		timer = setInterval(function() { $("#btn_next").click();}, 3000);
	});
	$(".main_image").resize();
});
</script>
</head>

<body>
<div class="top">
  <div class="top1">
    	<div class="logo"><a href="/web/index"><img src="${contextPath}/images/logo.png" width="202" height="80" style="margin-top:20px;" /></a>
            <div class="anniu">
            </div>
        </div>
  </div>
<div class="dao">
	<div class="daon">
		<ul>
        	<li class="daob"><a href="#">首页</a><span class="daobian"></span></li>
        	<li><a href="/web/todoList">待办任务</a><span class="daobian"></span></li>
            <li><a href="/web/prdList">上线产品</a><span class="daobian"></span></li>
        	<li><a href="#">常见问题</a><span class="daobian"></span></li>
            <shiro:guest>
            <li><a href="/web/tologin">登录</a><span class="daobian"></span></li>
            </shiro:guest>
            <shiro:user>
            <li><a href="/web/logout">(<shiro:principal/>)退出</a><span class="daobian"></span></li>
            </shiro:user>
        </ul>
	</div>
</div>
</div>
<div class="certen">
  <div class="main_visual">
                <div class="flicking_con">
                	<div class="flicking_inner">
                    <a href="#">1</a>
                    <a href="#">2</a>
                    <a href="#">3</a>
               	 </div>
            </div>
			<div class="main_image">
				<ul>					
					<li><img src="${contextPath}/images/img_main_1.jpg" width="1920" height="450" /></li>
					<li><img src="${contextPath}/images/img_main_2.jpg" width="1920" height="450" /></li>
					<li><img src="${contextPath}/images/img_main_3.jpg" width="1920" height="450" /></li>
				</ul>
				<a href="javascript:;" id="btn_prev"></a>
				<a href="javascript:;" id="btn_next"></a>
			</div>
             <div style=" width:100%; margin-top:1px; height:14px; background:url(${contextPath}/images/t2.jpg); float:left;"></div>
			</div>
<div class="c2" style="display:none;">
	<dl>
    	<dt><img src="${contextPath}/images/tu1.jpg" width="228" height="180" /></dt>
        <dd><a href="/uploaddatamanage/uploaddata/toWebUpload"><span class="adian"></span>数据服务</a></dd>
    </dl>
    <dl>
   	  <dt><img src="${contextPath}/images/tu2.jpg" width="228" height="180" /></dt>
        <dd><a href="/uploaddatamanage/document/toWebList"><span class="adian"></span>行业报告</a></dd>
    </dl>
    <dl>
    	<dt><img src="${contextPath}/images/tu3.jpg" width="228" height="180" /></dt>
        <dd><a href="#"><span class="adian"></span>新闻动态</a></dd>
    </dl>
</div>
</div>
<%@ include file="../../webroot.inc.jsp" %>
</body>
</html>
