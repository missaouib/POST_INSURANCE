<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/kfgl/issue/list" page="${page }">
</dwz:paginationForm>

<form method="post" action="${contextPath }/kfgl/issue/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
	</div>
</form>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="Renewed:view">
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="680" href="${contextPath }/xqgl/issue/view/{slt_uid}"><span>查看续期催收件详情</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Renewed:edit">
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/xqgl/issue/update/{slt_uid}"><span>市县续期催收登记</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Renewed:provEdit">
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/xqgl/issue/provUpdate/{slt_uid}"><span>省分续期催登记</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">所属机构</th>
				<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">保单号</th>
				<th orderField=feeDate class="${page.orderField eq 'feeDate' ? page.orderDirection : ''}">交费对应日</th>
				<th>宽限期还有（天）</th>
				<th orderField=feeStatus class="${page.orderField eq 'feeStatus' ? page.orderDirection : ''}">状态</th>
				<th orderField=feeFailReason class="${page.orderField eq 'feeFailReason' ? page.orderDirection : ''}">交费失败原因</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${fn:replace(item.policy.organization.name,'中邮保险局','')}</td>
				<td>${item.policy.policyNo}</td>
				<td><fmt:formatDate value="${item.feeDate }" pattern="yyyy-MM-dd"/></td>
				<td><span style="color:red; height:50%; margin-bottom:-contentheight;">${item.lastDateNum }</span></td>
				<td>${item.feeStatus }</td>
				<td>${item.feeFailReason}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>