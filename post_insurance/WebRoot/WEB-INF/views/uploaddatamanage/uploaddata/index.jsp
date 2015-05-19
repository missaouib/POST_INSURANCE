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
        	<li class="daob"><a href="/uploaddatamanage/uploaddata/index">数据服务</a><span class="daobian"></span></li>
            <li><a href="/uploaddatamanage/document/index">行业报告</a><span class="daobian"></span></li>
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
    		<shiro:hasPermission name="uploaddata:upload">
        	<li>
            	<a href="/uploaddatamanage/uploaddata/toWebUpload"><img src="${contextPath}/images/up.gif" width="74" height="74" /></a>
                <a href="/uploaddatamanage/uploaddata/toWebUpload"><span class="name n1">数据上传</span></a>
                <span class="explain">数据服务内容根据上传的数据自定制，每个用户只能查看本企业内部的数据情况，行业情况请点击行业报告</span>
            </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="StatisticsZZL:show">
            <li>
            	<a href="/uploaddatamanage/statistics/webzzl"><img src="${contextPath}/images/sale.gif" width="74" height="74" /></a>
                <a href="/uploaddatamanage/statistics/webzzl"><span class="name n2">门店销售管理</span></a>
                <span class="explain">门店销售管理目前仅指各门店销售排行，便于连锁总部对各门店销售规模及变化的把控</span>
            </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="StatisticsPL:show">
            <li>
            	<a href="/uploaddatamanage/statistics/webpl"><img src="${contextPath}/images/category.gif" width="74" height="74" /></a>
                <a href="/uploaddatamanage/statistics/webpl"><span class="name n3">品类管理</span></a>
                <span class="explain">品类管理主要针对总部及各分店的品类结构及变化情况</span>
            </li>
            </shiro:hasPermission>
            <li>
            	<a href="##"><img src="${contextPath}/images/store.gif" width="74" height="74" /></a>
                <a href="##"><span class="name n4">库存管理</span></a>
                <span class="explain">库存管理即通过库存周转率指导各门店的备货</span> 
            </li>
            <li>
            	<a href="##"><img src="${contextPath}/images/vip.gif" width="74" height="74" /></a>
                <a href="##"><span class="name n5">会员管理</span></a>
                <span class="explain">会员管理包括会员信息的清洗、与销售的关联、客单价、回头率、重点会员分析等</span>
            </li>
        </ul>
    </div>
    <div style="height:30px;"></div>
</div>

<%@ include file="../../webroot.inc.jsp" %>
</body>
</html>
