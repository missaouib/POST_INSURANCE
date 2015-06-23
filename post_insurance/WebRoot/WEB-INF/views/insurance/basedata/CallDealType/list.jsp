<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/basedata/callDealType/list" page="${page }">
	<input type="hidden" name="search_LIKE_typeName" value="${param.search_LIKE_typeName }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/basedata/callDealType/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>回访处理类型：</label>
					<input type="text" name="search_LIKE_typeName" value="${param.search_LIKE_typeName }"/>
				</li>
			</ul>
			<div class="subBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="CallDealType:save">
				<li><a iconClass="user_add" target="dialog" rel="lookup2organization_add" mask="true" width="530" height="330" href="${contextPath }/basedata/callDealType/create"><span>添加回访类型</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="CallDealType:edit">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="530" height="330" href="${contextPath }/basedata/callDealType/update/{slt_uid}"><span>编辑回访类型</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="CallDealType:delete">
				<li><a iconClass="user_delete" target="selectedTodo" rel="ids" href="${contextPath }/basedata/callDealType/delete" title="确认要删除?"><span>删除回访类型</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th width="100">回访类型代码</th>
				<th width="100">回访类型名称</th>
				<th width="100">类型描述</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${basedata}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.typeCode}</td>
				<td>${item.typeName}</td>
				<td>${item.typeDesc}</td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>