<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/basedata/staff/list" page="${page }">
	<input type="hidden" name="search_LIKE_name" value="${param.search_LIKE_name }"/>
	<input type="hidden" name="search_LIKE_idCard" value="${param.search_LIKE_idCard }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/basedata/staff/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>姓名：</label>
					<input type="text" name="search_LIKE_name" value="${param.search_LIKE_name }"/>
				</li>
				<li>
					<label>证件号码：</label>
					<input type="text" name="search_LIKE_idCard" value="${param.search_LIKE_idCard }"/>
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
			<shiro:hasPermission name="BankCode:save">
				<li><a iconClass="user_add" target="dialog" rel="lookup2organization_add" mask="true" width="530" height="330" href="${contextPath }/basedata/staff/create"><span>添加</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="BankCode:edit">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="530" height="330" href="${contextPath }/basedata/staff/update/{slt_uid}"><span>编辑</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="BankCode:delete">
				<li><a iconClass="user_delete" target="selectedTodo" rel="ids" href="${contextPath }/basedata/staff/delete" title="确认要删除?"><span>删除</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th width="100">姓名</th>
				<th width="100">证件号码</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${basedata}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.name}</td>
				<td>${item.idCard}</td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>