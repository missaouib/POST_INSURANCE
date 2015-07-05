<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/hfgl/issue/list" page="${page }">
</dwz:paginationForm>

<form method="post" action="${contextPath }/hfgl/issue/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
	</div>
</form>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="Callfail:view">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="680" href="${contextPath }/hfgl/issue/view/{slt_uid}"><span>查看回访不成功件</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Callfail:edit">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/hfgl/issue/update/{slt_uid}"><span>回复回访不成功件</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Callfail:provEdit">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/hfgl/issue/provUpdate/{slt_uid}"><span>省分回访登记</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th width="100" orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">所属机构</th>
				<th width="100" orderField=issueNo class="${page.orderField eq 'issueNo' ? page.orderDirection : ''}">工单编号</th>
				<th width="200">工单内容</th>
				<th width="120" orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">所属保单号</th>
				<th width="120" orderField=status class="${page.orderField eq 'status' ? page.orderDirection : ''}">工单状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issueList}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.organization.name}</td>
				<td>${item.issueNo}</td>
				<td>${item.issueContent}</td>
				<td>${item.policy.policyNo}</td>
				<td>${item.status}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>