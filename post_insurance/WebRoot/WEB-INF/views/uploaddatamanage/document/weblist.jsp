<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>    
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理平台</title>
<link href="${contextPath}/styles/dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${contextPath}/styles/dwz/themes/css/subcore.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${contextPath}/styles/validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${contextPath}/styles/postinsurance/css/postinsurance.css" rel="stylesheet" type="text/css" />
<!--[if IE]>
<link href="${contextPath}/styles/dwz/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->
<!--[if lte IE 9]>
<script src="${contextPath}/styles/dwz/js/speedup.js" type="text/javascript"></script>
<![endif]-->
<script src="${contextPath}/styles/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/jquery.validate.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="${contextPath}/styles/jquery/jquery.messager.js"></script>
<script src="${contextPath}/styles/treeTable/jquery.treeTable.min.js" type="text/javascript"></script>
<%-- form验证 --%>
<script src="${contextPath}/styles/validationEngine/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${contextPath}/styles/validationEngine/js/jquery.validationEngine-2.6.4.js" type="text/javascript" charset="utf-8"></script>

<script src="${contextPath}/styles/dwz/js/dwz.core.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.util.date.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.drag.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.tree.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.accordion.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.ui.pt.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.theme.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.navTab.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.tab.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.resize.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.dialog.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.cssTable.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.stable.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.ajax.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.pagination.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.database.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.datepicker.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.effects.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.panel.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.checkbox.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.history.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.combox.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.print.js" type="text/javascript"></script>

<script src="${contextPath}/styles/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
<%-- 自定义JS --%>
<script src="${contextPath}/styles/dwz/js/customer.js" type="text/javascript"></script>
<script src="${contextPath}/styles/postinsurance/js/postinsurance.js" type="text/javascript"></script>
<%-- upload --%>
<script src="${contextPath}/styles/uploadify/scripts/jquery.uploadify.min.js" type="text/javascript"></script>

<script src="${contextPath}/js/DateFormat.js" type="text/javascript"></script>

<script type="text/javascript">
$(function(){	
	DWZ.init("${contextPath}/styles/dwz/dwz.frag.xml", {
		loginUrl:"${contextPath}/web/index", 
		loginTitle:"登录",	// 弹出登录对话框
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"${contextPath}/styles/dwz/themes"});
		}
	});
}); 
</script>

<link href="${contextPath}/css/common.css" rel="stylesheet"/>
</head>

<body style="background-color: white;">
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
        	<shiro:user>  
        	<li><a href="/uploaddatamanage/uploaddata/index">数据服务</a><span class="daobian"></span></li>
            <li class="daob"><a href="/uploaddatamanage/document/index">行业报告</a><span class="daobian"></span></li>
            </shiro:user>
        	<li><a href="#">新闻动态</a><span class="daobian"></span></li>
            <shiro:guest>
            <li><a href="/web/tologin">登录</a><span class="daobian"></span></li>
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
<div class="main_visual">
</div>

<div id="layout">
	<div id="container">
		<div id="navTab" class="tabsPage">
					<div class="navTab-panel tabsPageContent layoutBox">
				<div class="page unitBox">
		
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<dwz:paginationForm action="${contextPath }/uploaddatamanage/document/toWebList/${storetype}" page="${page }">
	<input type="hidden" name="search_LIKE_resource.name" value="${param.search_LIKE_resource.name}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/uploaddatamanage/document/toWebList/${storetype}" onsubmit="return navTabAjaxDone(this);">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li style="width:30%;">&nbsp;</li>
				<li>
					<label>文件名称：</label>
					<input type="text" name="search_LIKE_resource.name" value="${param.search_LIKE_resource.name}"/>
				</li>
				<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
			</ul>
		</div>
	</div>
</form>

<div class="pageContent">
	
	<table class="table" layoutH="160" width="100%">

		<tbody>
			<c:forEach var="item" items="${resources}">
			<tr target="slt_uid" rel="${item.id}">
				<td width="30%" align="right"><img src='${contextPath }/images/dot.png' style='height:8px;width:8px;vertical-align: middle;' />&nbsp;&nbsp;<b>${storetype.toLowerCase() == "file" ? "[药品零售市场研究报告]" : (storeType.toLowerCase() == "file2" ? "[品类管理报告]" : (storeType.toLowerCase() == "file3" ? "[消费者购药U&A研究]" : "[其他]")) }</b></td>
				<td align="left" width="50%">&nbsp;&nbsp;<a target="_blank" href="${contextPath }/uploaddatamanage/document/webview/${item.id}" title="浏览">${item.getResource().name}&nbsp;&nbsp;<img src='${contextPath }/images/pdf.png' style='vertical-align: middle;' /></a></td>
				<td align="center" width="65px">${item.getResource().uploadTime}</td>
				<td align="left" ><img src='${contextPath }/images/new.png' style='vertical-align:bottom;margin-top:-5px;display:${item.status==1?"none":""}' />&nbsp;</td>
				<td></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
	
	<%@ include file="../../webroot.inc.jsp" %>
</div>
</div>
</div>
</div>

</div>
</div>
</div>

</body>
</html>
