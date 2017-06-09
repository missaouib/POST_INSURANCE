<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/bqgl/issuelist" page="${page }">
</dwz:paginationForm>

<form method="post" action="${contextPath }/bqgl/issuelist" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
	</div>
</form>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="Cservice:save">
				<li><a class="add" target="dialog" rel="lookup2organization_add" mask="true" width="530" height="330" href="${contextPath }/bqgl/issue/create"><span>添加保全复核问题</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Cservice:edit">
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="530" height="330" href="${contextPath }/bqgl/issue/update/{slt_uid}"><span>编辑保全复核问题</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Cservice:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/bqgl/issue/delete" title="确认要删除?"><span>删除保全复核问题</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Cservice:deal">
				<li class="line">line</li>
				<li><a class="delete" target="ajaxTodo" href="${contextPath }/bqgl/issue/DealStatus/{slt_uid}" title="确认更新状态?"><span>已处理</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Cservice:reset">
				<li class="line">line</li>
				<li><a class="delete" target="ajaxTodo" href="${contextPath }/bqgl/issue/CloseStatus/{slt_uid}" title="确认关闭?"><span>关闭</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">保单号</th>
				<th>保单所属机构</th>
				<th orderField=dealNum class="${page.orderField eq 'dealNum' ? page.orderDirection : ''}">保全受理号</th>
				<th>保全项目</th>
				<th orderField=info class="${page.orderField eq 'info' ? page.orderDirection : ''}">复核修改问题</th>
				<th orderField=csDate class="${page.orderField eq 'csDate' ? page.orderDirection : ''}">问题产生日期</th>
				<th orderField=status class="${page.orderField eq 'status' ? page.orderDirection : ''}">状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.policy.policyNo}</td>
				<td>${item.policy.organization.shortName}</td>
				<td>${item.dealNum}</td>
				<td>${item.conservationCode}</td>
				<td>${item.info}</td>
				<td>${item.csDate}</td>
				<td>
				<c:choose>
					<c:when test="${item.status eq 'NewStatus'}">
						<span style="color:red; height:50%; margin-bottom:-contentheight;">待处理</span>
					</c:when>
					<c:when test="${item.status eq 'DealStatus'}">
						已处理
					</c:when>
					<c:when test="${item.status eq 'CancelStatus'}">
						已撤销
					</c:when>
					<c:otherwise>
						已关闭
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>