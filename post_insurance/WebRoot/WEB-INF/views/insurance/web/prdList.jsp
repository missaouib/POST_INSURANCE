<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中邮保险广东分公司-运营管理平台</title>
<link href="${contextPath}/styles/dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${contextPath}/styles/dwz/themes/css/subcore.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${contextPath}/styles/validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" media="screen"/>
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

<script src="${contextPath}/styles/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
<%-- 自定义JS --%>
<script src="${contextPath}/styles/dwz/js/customer.js" type="text/javascript"></script>
<%-- upload --%>
<script src="${contextPath}/js/DateFormat.js" type="text/javascript"></script>

<script type="text/javascript">
$("#background,#progressBar").hide();
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
        	<li><a href="/web/index" target="_top">首页</a><span class="daobian"></span></li>
        	<li><a href="/web/todoList" target="_top">待办任务</a><span class="daobian"></span></li>
            <li class="daob"><a href="/web/prdList" target="_top">上线产品</a><span class="daobian"></span></li>
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
</div>

<div id="layout">
	<div id="container">
		<div id="navTab" class="tabsPage">
					<div class="navTab-panel tabsPageContent layoutBox">
				<div class="page unitBox">
    <!--  content  -->
    <div class="pageContent sortDrag" selector="h1" layoutH="68">
	<div class="tabs" currentIndex="0" eventType="click">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>产品列表</span></a></li>
					<li><a href="/web/prd/ffy1" class="j-ajax"><span>富富余1号</span></a></li>
					<li><a href="/web/prd/ffy3" class="j-ajax"><span>富富余3号</span></a></li>
					<li><a href="/web/prd/bbb" class="j-ajax"><span>百倍保</span></a></li>
					<li><a href="/web/prd/nb" class="j-ajax"><span>重疾保障计划</span></a></li>
					<li><a href="/web/prd/nbplus" class="j-ajax"><span>健康保障计划</span></a></li>
					<li><a href="/web/prd/ddb" class="j-ajax"><span>多多保</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" style="height:70;">
				<div class="tabs" currentIndex="0" eventType="click" style="width:800px">
					<div class="tabsHeader">
						<div class="tabsHeaderContent">
							<ul>
								<li><a href="javascript:;"><span>介绍</span></a></li>
								<li><a href="javascript:;"><span>填单</span></a></li>
								<li><a href="javascript:;"><span>核保</span></a></li>
								<li><a href="javascript:;"><span>限额</span></a></li>
								<li><a href="javascript:;"><span>样本</span></a></li>
								<li><a href="javascript:;"><span>销售</span></a></li>
							</ul>
						</div>
					</div>
					<div class="tabsContent" style="height:300px;">
						<div>产品的介绍</div>
						<div>填单的极少</div>
						<div>核保介绍</div>
						<div>限额介绍</div>
						<div>弹出样本查看</div>
						<div>技术部销售辅助工具</div>
					</div>
					<div class="tabsFooter">
						<div class="tabsFooterContent"></div>
					</div>
				</div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>
	<%@ include file="../../webroot.inc.jsp" %>
</div>
</div></div></div></div></div>

</body>
</html>
