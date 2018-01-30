<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" contect="no-cache"/>
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
        	<li class="daob"><a href="/web/todoList" target="_top">待办任务</a><span class="daobian"></span></li>
            <li><a href="/web/prdList" target="_top">上线产品</a><span class="daobian"></span></li>
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
	<div style="margin:0 auto; width: 85%">
    <div class="pageContent sortDrag" selector="h1" layoutH="72">
	<fieldset>
	<legend>待办任务</legend>
	<shiro:hasPermission name="Wtgd:view">
	<div class="panel <c:if test='${fn:length(issueList)<=0}'>close</c:if> collapse" defH="100">
		<h1><c:if test='${fn:length(issueList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>待处理问题工单</h1>
		<div>
			<table class="list" width="98%">
				<thead>
					<tr>
						<th>序号</th>
						<th orderField=issueNo class="${page.orderField eq 'issueNo' ? page.orderDirection : ''}">工单号</th>
						<th orderField=shouldDate class="${page.orderField eq 'shouldDate' ? page.orderDirection : ''}">待处理时间</th>
						<th>离结案还有（天）</th>
						<th>工单状态</th>
						<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">保单号</th>
						<th>保单所属机构</th>
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
						<td>${item.shouldDate }</td>
						<td><span style="color:red; height:50%; margin-bottom:-contentheight;">${item.lastDateNum }</span></td>
						<td>${item.status}</td>
						<td>${item.policy.policyNo}</td>
						<td>${item.policy.organization.shortName}</td>
						<td>${item.issueType}</td>
						<td>${item.issueContent}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="Cservice:view">
	<div class="panel <c:if test='${fn:length(bqIssueList)<=0}'>close</c:if> collapse" defH="100">
		<h1><c:if test='${fn:length(bqIssueList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>待处理保全复核问题</h1>
		<div>
			<table class="list" width="98%">
				<thead>
					<tr>
						<th>序号</th>
						<th>保单号</th>
						<th>保单所属机构</th>
						<th>保全复核问题</th>
						<th>问题产生日期</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${bqIssueList}" varStatus="var">
					<tr target="slt_uid" rel="${item.id}">
						<td>${var.index+1 }</td>
						<td>${item.policy.policyNo}</td>
						<td>${item.policy.organization.shortName}</td>
						<td>${item.csRst}</td>
						<td>${item.csDate}</td>
						<td>
						<c:if test="${fn:length(login_user.organization.orgCode) > 4}">
						<a target="ajaxTodo" href="${contextPath }/bqgl/issue/DealStatus/${item.id}" title="确认更新状态?"><span>已处理</span></a>
						</c:if>
						 <c:if test="${fn:length(login_user.organization.orgCode) <= 4}"> 
					     <a target="ajaxTodo" href="${contextPath }/bqgl/issue/CloseStatus/${item.id}" title="确认关闭?"><span>关闭</span></a>
					    </c:if> 
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="Callfail:view">
	<div class="panel <c:if test='${fn:length(hfIssueList)<=0}'>close</c:if> collapse" defH="100">
		<h1><c:if test='${fn:length(hfIssueList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>待二次回访工单</h1>
		<div>
			<table class="list" width="98%">
				<thead>
					<tr>
						<th>序号</th>
						<th>工单号</th>
						<th>待处理时间</th>
						<th>离犹豫期还有（天）</th>
						<th>工单状态</th>
						<th>保单号</th>
						<th>保单所属机构</th>
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
						<td><fmt:formatDate value="${item.shouldDate }" pattern="yyyy-MM-dd"/></td>
						<td><span style="color:red; height:50%; margin-bottom:-contentheight;"><c:if test="${item.lastDateNum<0 }">0</c:if><c:if test="${item.lastDateNum>=0 }">${item.lastDateNum }</c:if></span></td>
						<td>${item.status}</td>
						<td>${item.policy.policyNo}</td>
						<td>${item.policy.organization.shortName}</td>
						<td>${item.issueType}</td>
						<td>${item.issueContent}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="Renewed:view">
	<div class="panel <c:if test='${fn:length(xqIssueList)<=0}'>close</c:if> collapse" defH="100">
		<h1><c:if test='${fn:length(xqIssueList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>续期催缴工单</h1>
		<div>
			<table class="list" width="98%">
				<thead>
					<tr>
						<th>序号</th>
						<th>保单号</th>
						<th>保单所属机构</th>
						<th>交费对应日</th>
						<th>宽限期还有（天）</th>
						<th>交费状态</th>
						<th>交费失败原因</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${xqIssueList}" varStatus="var">
					<tr target="slt_uid" rel="${item.id}">
						<td>${var.index+1 }</td>
						<td>
						<c:if test="${fn:length(login_user.organization.orgCode) > 4}">
						<a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/xqgl/issue/update/${item.id}"><span>${item.policy.policyNo}</span></a>
						</c:if>
						 <c:if test="${fn:length(login_user.organization.orgCode) <= 4}"> 
					     <a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/xqfgl/issue/view/${item.id}"><span>${item.policy.policyNo}</span></a>
					    </c:if> 
						</td>
						<td>${item.policy.organization.shortName}</td>
						<td><fmt:formatDate value="${item.feeDate }" pattern="yyyy-MM-dd"/></td>
						<td><span style="color:red; height:50%; margin-bottom:-contentheight;">${item.lastDateNum }</span></td>
						<td>${item.feeStatus}</td>
						<td>${item.feeFailReason}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="CheckWrite:view">
	<div class="panel <c:if test='${fn:length(checkWriteIssueList)<=0}'>close</c:if> collapse" defH="100">
		<h1><c:if test='${fn:length(checkWriteIssueList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>新契约填写不合格件</h1>
		<div>
			<table class="list" width="98%">
				<thead>
					<tr>
						<th>序号</th>
						<th>保单号</th>
						<th>保单所属机构</th>
						<th>承保日期</th>
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
						<td>${item.policy.organization.shortName}</td>
						<td>${item.policy.policyDate}</td>
						<td>
						<c:choose>
							<c:when test="${item.fixStatus eq 'NewStatus'}">
								<span style="color:red; height:50%; margin-bottom:-contentheight;">待处理</span>
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
	</shiro:hasPermission>
	<shiro:hasPermission name="CheckRecord:view">
	<div class="panel <c:if test='${fn:length(checkRecordIssueList)<=0}'>close</c:if> collapse" defH="100">
		<h1><c:if test='${fn:length(checkRecordIssueList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>新契约录入不合格件</h1>
		<div>
			<table class="list" width="98%">
				<thead>
					<tr>
						<th>序号</th>
						<th>保单号</th>
						<th>保单所属机构</th>
						<th>承保日期</th>
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
						<td>${item.policy.organization.shortName}</td>
						<td>${item.policy.policyDate}</td>
						<td>
						<c:choose>
							<c:when test="${item.fixStatus eq 'NewStatus'}">
								<span style="color:red; height:50%; margin-bottom:-contentheight;">待处理</span>
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
	</shiro:hasPermission>
	<shiro:hasPermission name="UnderWrite:view">
	<div class="panel <c:if test='${fn:length(underwriteList)<=0}'>close</c:if> collapse" defH="100">
		<h1><c:if test='${fn:length(underwriteList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>人核件跟进任务</h1>
		<div>
			<table class="list" width="98%">
				<thead>
					<tr>
						<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">市县机构</th>
						<th orderField=formNo class="${page.orderField eq 'formNo' ? page.orderDirection : ''}">投保单号</th>
						<th orderField=policyNo class="${page.orderField eq 'policyNo' ? page.orderDirection : ''}">保单号</th>
						<th>投保人</th>
						<th orderField=prd.prdName class="${page.orderField eq 'prd.prdName' ? page.orderDirection : ''}">产品</th>
						<th orderField=ytbDate class="${page.orderField eq 'ytbDate' ? page.orderDirection : ''}">邮保通录入时间</th>
						<th>核心录入时间</th>
						<th>复核时间</th>
						<th>核保日期</th>
						<th>签单日期</th>
						<th>合同签收日期</th>
						<th>回执录入日期</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${underwriteList}">
					<tr target="slt_uid" rel="${item.id}">
						<td>${item.organization.shortName}</td>
						<td>
						<a target="dialog" rel="lookup2organization_edit" mask="true" width="550" height="220" href="${contextPath }/qygl/underwrite/signDateUpdate/${item.id}"><span>${item.formNo}</span></a>
						</td>
						<td>${item.policyNo}</td>
						<td>${item.holder}</td>
						<td>${item.prd.prdName}</td>
						<td><fmt:formatDate value="${item.ybtDate }" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatDate value="${item.sysDate }" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatDate value="${item.checkDate }" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatDate value="${item.underwriteDate }" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatDate value="${item.signDate }" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatDate value="${item.clientReceiveDate }" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatDate value="${item.billBackDate }" pattern="yyyy-MM-dd"/></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	</shiro:hasPermission>
</fieldset>
	</div>
	</div>
	<%@ include file="../../webroot.inc.jsp" %>
</div>
</div></div></div></div></div>

</body>
</html>
