<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>    
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中邮保险广东分公司-运营管理平台</title>
<link href="${contextPath}/styles/dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${contextPath}/styles/dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${contextPath}/styles/dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="${contextPath}/styles/validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${contextPath}/styles/ztree/css/zTreeStyle.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${contextPath}/styles/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${contextPath}/styles/treeTable/themes/default/treeTable.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/styles/post/css/postinsurance.css" rel="stylesheet" type="text/css" />
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
<%--
<script src="${contextPath}/styles/dwz/js/dwz.core.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.util.date.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.drag.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.tree.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.accordion.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.ui.js" type="text/javascript"></script>
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
--%>

<script src="${contextPath}/styles/dwz/js/dwz.min.js" type="text/javascript"></script>

<script src="${contextPath}/styles/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
<%-- 自定义JS --%>
<script src="${contextPath}/styles/dwz/js/customer.js" type="text/javascript"></script>
<script src="${contextPath}/styles/post/js/postinsurance.js" type="text/javascript"></script>
<%-- upload --%>
<script src="${contextPath}/styles/uploadify/scripts/jquery.uploadify.min.js" type="text/javascript"></script>
<%-- zTree --%>
<script src="${contextPath}/styles/ztree/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>

<script src="${contextPath}/js/DateFormat.js" type="text/javascript"></script>

<script type="text/javascript">
$(function(){	
	DWZ.init("${contextPath}/styles/dwz/dwz.frag.xml", {
		loginUrl:"${contextPath}/login/timeout?userType=admin", 
		loginTitle:"登录",	// 弹出登录对话框
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"${contextPath}/styles/dwz/themes"});
		}
	});
});

$(document).ready(function(){ 
	checkMessage();
	window.setInterval("checkMessage()",60000);//1分钟刷新1次 
}); 
</script>
</head>
<body scroll="no">
<div id="layout">
	<div id="header">
		<div class="headerNav">
			<a class="logo" href="${contextPath}/management/index">Logo</a>
			<ul class="nav">
				<li><a href="${contextPath}/web/index">网站</a></li>
				<li><font color="#ffffff">欢迎您，${login_user.username } 登录管理系统</font></li>
				<li><font color="#ffffff">所属机构：${login_user.organization.orgCode } ${login_user.organization.name }</font></li>
				<li><a href="${contextPath}/management/index">主页</a></li>
				<li><a href="${contextPath}/management/index/updateBase" target="dialog" mask="true" width="550" height="250">修改用户信息</a></li>
				<li><a href="${contextPath}/management/index/updatePwd" target="dialog" mask="true" width="500" height="200">修改密码</a></li>
				<li><a href="${contextPath}/logout?userType=admin">退出</a></li>
			</ul>
			 
			<ul class="themeList" id="themeList">
				<li theme="default"><div class="selected">blue</div></li>
				<li theme="green"><div>green</div></li>
				<li theme="purple"><div>purple</div></li>
				<li theme="silver"><div>silver</div></li>
				<li theme="azure"><div>天蓝</div></li>
			</ul>
			
		</div>
	</div>
	<div id="leftside">
		<div id="sidebar_s">
			<div class="collapse">
				<div class="toggleCollapse"><div></div></div>
			</div>
		</div>
		<div id="sidebar">
			<div class="toggleCollapse"><h2>菜单</h2><div>collapse</div></div>
			<div class="accordion" fillSpace="sideBar">
				<c:forEach var="level1" items="${menuModule.children }">
					<div class="accordionHeader">
						<h2><span>Folder</span>${level1.name }</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder expand">
						<c:forEach var="level2" items="${level1.children }">
							<li>
								<dwz:menuAccordion child="${level2 }" urlPrefix="${contextPath }"/>
							</li>
						</c:forEach>
						</ul>
					</div>												
				</c:forEach>				
			</div>
		</div>
	</div>
	<div id="container">
		<div id="navTab" class="tabsPage">
			<div class="tabsPageHeader">
				<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
					<ul class="navTab-tab">
						<li tabid="main" class="main"><a href="javascript:void(0)"><span><span class="home_icon">主页</span></span></a></li>
					</ul>
				</div>
				<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
				<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
				<div class="tabsMore">more</div>
			</div>
			<ul class="tabsMoreList">
				<li><a href="javascript:void(0)">主页</a></li>
			</ul>
			<div class="navTab-panel tabsPageContent layoutBox">
				<div class="page unitBox">
					<div class="accountInfo">
						<div class="right">
							<p><fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM-dd EEEE"/></p>
						</div>
						<p><span>欢迎, ${login_user.realname } . 请及时处理待办任务。</p>
					</div>
					<div align="center"><%@ include file="../../msg.inc.jsp" %></div>
					<div class="pageContent sortDrag" selector="h1" layoutH="12">
					<fieldset>
					<legend>待办任务</legend>
					<div class="panel <c:if test='${fn:length(issueList)<=0}'>close</c:if> collapse" defH="100">
						<h1><c:if test='${fn:length(issueList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>待处理问题工单</h1>
						<div>
							<table class="list" width="98%">
								<thead>
									<tr>
										<th>序号</th>
										<th orderField=issueNo class="${page.orderField eq 'issueNo' ? page.orderDirection : ''}">工单号</th>
										<th>工单状态</th>
										<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">保单号</th>
										<th orderField=issueType class="${page.orderField eq 'issueType' ? page.orderDirection : ''}">工单子类型</th>
										<th>工单内容</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${issueList}" varStatus="var">
									<tr target="slt_uid" rel="${item.id}">
										<td>${var.index+1 }</td>
										<td>
										<c:if test="${fn:length(login_user.organization.orgCode) > 4}">
										<a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/kfgl/issue/update/${item.id}"><span>${item.issueNo}</span></a>
										</c:if>
										 <c:if test="${fn:length(login_user.organization.orgCode) <= 4}"> 
									     <a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/kfgl/issue/view/${item.id}"><span>${item.issueNo}</span></a>
									    </c:if> 
										</td>
										<td>${item.status}</td>
										<td>${item.policy.policyNo}</td>
										<td>${item.issueType}</td>
										<td>${item.issueContent}</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
					<div class="panel <c:if test='${fn:length(bqIssueList)<=0}'>close</c:if> collapse" defH="100">
						<h1><c:if test='${fn:length(bqIssueList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>待处理保全复核问题</h1>
						<div>
							<table class="list" width="98%">
								<thead>
									<tr>
										<th>序号</th>
										<th>保单号</th>
										<th>保全复核问题</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${bqIssueList}" varStatus="var">
									<tr target="slt_uid" rel="${item.id}">
										<td>${var.index+1 }</td>
										<td>${item.policy.policyNo}</td>
										<td>${item.csRst}</td>
										<td>
										<c:if test="${fn:length(login_user.organization.orgCode) > 4}">
										<a target="ajaxTodo" href="${contextPath }/bqgl/issue/DealStatus/${item.id}" title="确认更新状态?"><span>已处理</span></a>
										<a target="ajaxTodo" href="${contextPath }/bqgl/issue/CancelStatus/${item.id}" title="确认撤销?"><span>撤销</span></a>
										</c:if>
										 <c:if test="${fn:length(login_user.organization.orgCode) <= 4}"> 
									     <a target="ajaxTodo" href="${contextPath }/bqgl/issue/CancelStatus/${item.id}" title="确认撤销?"><span>撤销</span></a>
									     <a target="ajaxTodo" href="${contextPath }/bqgl/issue/CloseStatus/${item.id}" title="确认关闭?"><span>关闭</span></a>
									    </c:if> 
										</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
					<div class="panel <c:if test='${fn:length(hfIssueList)<=0}'>close</c:if> collapse" defH="100">
						<h1><c:if test='${fn:length(hfIssueList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>待上门回访工单</h1>
						<div>
							<table class="list" width="98%">
								<thead>
									<tr>
										<th>序号</th>
										<th>工单号</th>
										<th>工单状态</th>
										<th>保单号</th>
										<th>工单子类型</th>
										<th>工单内容</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${hfIssueList}" varStatus="var">
									<tr target="slt_uid" rel="${item.id}">
										<td>${var.index+1 }</td>
										<td>
										<c:if test="${fn:length(login_user.organization.orgCode) > 4}">
										<a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/hfgl/issue/update/${item.id}"><span>${item.issueNo}</span></a>
										</c:if>
										 <c:if test="${fn:length(login_user.organization.orgCode) <= 4}"> 
									     <a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/hfgl/issue/view/${item.id}"><span>${item.issueNo}</span></a>
									    </c:if> 
										</td>
										<td>${item.status}</td>
										<td>${item.policy.policyNo}</td>
										<td>${item.issueType}</td>
										<td>${item.issueContent}</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
					<div class="panel <c:if test='${fn:length(xqIssueList)<=0}'>close</c:if> collapse" defH="100">
						<h1><c:if test='${fn:length(xqIssueList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>续期催缴工单</h1>
						<div>
							<table class="list" width="98%">
								<thead>
									<tr>
										<th>序号</th>
										<th>工单号</th>
										<th>工单状态</th>
										<th>保单号</th>
										<th>工单子类型</th>
										<th>工单内容</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${xqIssueList}" varStatus="var">
									<tr target="slt_uid" rel="${item.id}">
										<td>${var.index+1 }</td>
										<td>
										<c:if test="${fn:length(login_user.organization.orgCode) > 4}">
										<a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/xqgl/issue/update/${item.id}"><span>${item.issueNo}</span></a>
										</c:if>
										 <c:if test="${fn:length(login_user.organization.orgCode) <= 4}"> 
									     <a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/xqfgl/issue/view/${item.id}"><span>${item.issueNo}</span></a>
									    </c:if> 
										</td>
										<td>${item.status}</td>
										<td>${item.policy.policyNo}</td>
										<td>${item.issueType}</td>
										<td>${item.issueContent}</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
					<div class="panel <c:if test='${fn:length(checkWriteIssueList)<=0}'>close</c:if> collapse" defH="100">
						<h1><c:if test='${fn:length(checkWriteIssueList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>新契约填写不合格件</h1>
						<div>
							<table class="list" width="98%">
								<thead>
									<tr>
										<th>序号</th>
										<th>保单号</th>
										<th>状态</th>
										<th>关键信息</th>
										<th>重要信息</th>
										<th>扫描不完整</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${checkWriteIssueList}" varStatus="var">
									<tr target="slt_uid" rel="${item.id}">
										<td>${var.index+1 }</td>
										<td>
										<c:if test="${fn:length(login_user.organization.orgCode) > 4}">
										<a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/qygl/issue/write/update/${item.id}"><span>${item.policy.policyNo}</span></a>
										</c:if>
										 <c:if test="${fn:length(login_user.organization.orgCode) <= 4}"> 
									     <a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/qygl/issue/write/view/${item.id}"><span>${item.policy.policyNo}</span></a>
									    </c:if> 
										</td>
										<td>
										<c:choose>
											<c:when test="${item.fixStatus eq 'NewStatus'}">
												待处理
											</c:when>
											<c:when test="${item.fixStatus eq 'DealStatus'}">
												已处理
											</c:when>
											<c:when test="${item.fixStatus eq 'ReopenStatus'}">
												重打开
											</c:when>
											<c:otherwise>
												已关闭
											</c:otherwise>
										</c:choose>
										</td>
										<td>${item.keyInfo=="null"?"":item.keyInfo}</td>
										<td>${item.importanceInfo="null"?"":item.importanceInfo}</td>
										<td>${item.docMiss==null?"":item.docMiss}</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
					<div class="panel <c:if test='${fn:length(checkRecordIssueList)<=0}'>close</c:if> collapse" defH="100">
						<h1><c:if test='${fn:length(checkRecordIssueList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>新契约录入不合格件</h1>
						<div>
							<table class="list" width="98%">
								<thead>
									<tr>
										<th>序号</th>
										<th>保单号</th>
										<th>状态</th>
										<th>关键信息</th>
										<th>重要信息</th>
										<th>扫描不完整</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${checkRecordIssueList}" varStatus="var">
									<tr target="slt_uid" rel="${item.id}">
										<td>${var.index+1 }</td>
										<td>
										<c:if test="${fn:length(login_user.organization.orgCode) > 4}">
										<a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/qygl/issue/record/update/${item.id}"><span>${item.policy.policyNo}</span></a>
										</c:if>
										 <c:if test="${fn:length(login_user.organization.orgCode) <= 4}"> 
									     <a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/qygl/issue/record/view/${item.id}"><span>${item.policy.policyNo}</span></a>
									    </c:if> 
										</td>
										<td>
										<c:choose>
											<c:when test="${item.fixStatus eq 'NewStatus'}">
												待处理
											</c:when>
											<c:when test="${item.fixStatus eq 'DealStatus'}">
												已处理
											</c:when>
											<c:when test="${item.fixStatus eq 'ReopenStatus'}">
												重打开
											</c:when>
											<c:otherwise>
												已关闭
											</c:otherwise>
										</c:choose>
										</td>
										<td>${item.keyInfo=="null"?"":item.keyInfo}</td>
										<td>${item.importanceInfo="null"?"":item.importanceInfo}</td>
										<td>${item.docMiss==null?"":item.docMiss}</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</fieldset>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="footer">中邮保险广东分公司 运营管理部 Copyright &copy; 2013-2015, All Rights Reserve.</div>
<script src="${contextPath}/js/refresh.js" type="text/javascript"></script>
</body>
</html>