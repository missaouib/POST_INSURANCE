<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>    
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理系统</title>

<script type="text/javascript" src="${contextPath}/js/jquery.event.drag-1.5.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.touchSlider.js"></script>
<link rel="stylesheet" href="${contextPath}/styles/validationEngine/css/validationEngine.jquery.css" type="text/css"/>
<script src="${contextPath}/styles/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${contextPath}/styles/validationEngine/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${contextPath}/styles/validationEngine/js/jquery.validationEngine-2.6.4.js" type="text/javascript" charset="utf-8"></script>
<link href="${contextPath}/css/common.css" rel="stylesheet"/>
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
			<li><a href="/web/index">首页</a><span class="daobian"></span></li>
        	<li><a href="/uploaddatamanage/uploaddata/index">数据服务</a><span class="daobian"></span></li>
            <li class="daob"><a href="/uploaddatamanage/document/index">行业报告</a><span class="daobian"></span></li>
            <li><a href="#">新闻动态</a><span class="daobian"></span></li>
            <shiro:guest>
            <li class="daob"><a href="/web/tologin">登录</a><span class="daobian"></span></li>
            </shiro:guest>
            <shiro:user>
            <li><a href="/members/user/index">用户管理</a><span class="daobian"></span></li>
            <li><a href="/web/logout">(<shiro:principal/>)退出</a><span class="daobian"></span></li>
            </shiro:user>
        </ul>
	</div>
</div>
</div>

<div class="certen">
	<div class="main">
    	<ul class="dataservices">
    		<shiro:hasPermission name="document:show">
        	<li>
            	<a href="${contextPath}/uploaddatamanage/document/toWebList/FILE"><img src="${contextPath}/images/icon3.jpg" width="74" height="74" /></a>
                <a href="${contextPath}/uploaddatamanage/document/toWebList/FILE"><span class="name n1">中国药品零售市场研究报告</span></a>
                <span class="explain">《中国药品零售市场研究报告》重点关注行业动态变化</span>
            </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="document:show">
            <li>
            	<a href="${contextPath}/uploaddatamanage/document/toWebList/FILE2"><img src="${contextPath}/images/icon4.jpg" width="74" height="74" /></a>
                <a href="${contextPath}/uploaddatamanage/document/toWebList/FILE2"><span class="name n2">品类管理报告</span></a>
                <span class="explain">品类管理报告是按月度呈现重点城市重点品类的变化情况</span>
            </li>
            </shiro:hasPermission>
            <li>
            	<a href="${contextPath}/uploaddatamanage/document/toWebList/FILE3"><img src="${contextPath}/images/icon5.jpg" width="74" height="74" /></a>
                <a href="${contextPath}/uploaddatamanage/document/toWebList/FILE3"><span class="name n3">消费者购药U&A研究</span></a>
                <span class="explain">消费者购药U&A研究主要针对药品零售市场的重点类别和重点会员用户进行分析</span>
            </li>
            <li>
            	<a href="${contextPath}/uploaddatamanage/document/toWebList/OTHER"><img src="${contextPath}/images/icon6.jpg" width="74" height="74" /></a>
                <a href="${contextPath}/uploaddatamanage/document/toWebList/OTHER"><span class="name n3">其他</span></a>
                <span class="explain"></span>
            </li>
        </ul>
    </div>
    <div style="height:30px;"></div>
</div>

<%@ include file="../../webroot.inc.jsp" %>
</body>
</html>
