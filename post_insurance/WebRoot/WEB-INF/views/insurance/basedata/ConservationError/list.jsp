<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/basedata/conservationError/list" page="${page }">
	<input type="hidden" name="search_LIKE_errorCode" value="${param.search_LIKE_errorCode }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/basedata/conservationError/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>保全常见错误定义：</label>
					<input type="text" name="search_LIKE_errorCode" value="${param.search_LIKE_errorCode }"/>
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
			<shiro:hasPermission name="ConservationError:save">
				<li><a iconClass="user_add" target="dialog" rel="lookup2organization_add" mask="true" width="530" height="330" href="${contextPath }/basedata/conservationError/create"><span>添加保全常见错误定义</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ConservationError:edit">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="530" height="330" href="${contextPath }/basedata/conservationError/update/{slt_uid}"><span>编辑保全常见错误定义</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ConservationError:delete">
				<li><a iconClass="user_delete" target="selectedTodo" rel="ids" href="${contextPath }/basedata/conservationError/delete" title="确认要删除?"><span>删除保全常见错误定义</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th width="100">保全常见错误定义</th>
				<th width="100">错误描述</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${basedata}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.errorCode}</td>
				<td>${item.errorDesc}</td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>