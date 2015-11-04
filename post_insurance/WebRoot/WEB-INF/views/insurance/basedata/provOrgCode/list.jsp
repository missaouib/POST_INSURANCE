<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/basedata/provOrgCode/list" page="${page }">
	<input type="hidden" name="search_LIKE_orgCode" value="${param.search_LIKE_orgCode }"/>
	<input type="hidden" name="search_LIKE_orgName" value="${param.search_LIKE_orgName }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/basedata/provOrgCode/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>省分机构代码：</label>
					<input type="text" name="search_LIKE_orgCode" value="${param.search_LIKE_orgCode }"/>
				</li>
				<li>
					<label>省分机构名称：</label>
					<input type="text" name="search_LIKE_orgName" value="${param.search_LIKE_orgName }"/>
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
			<shiro:hasPermission name="ProvOrgCode:save">
				<li><a iconClass="user_add" target="dialog" rel="lookup2organization_add" mask="true" width="530" height="330" href="${contextPath }/basedata/provOrgCode/create"><span>添加省分机构代码对照</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ProvOrgCode:edit">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="530" height="330" href="${contextPath }/basedata/provOrgCode/update/{slt_uid}"><span>编辑省分机构代码对照</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ProvOrgCode:delete">
				<li><a iconClass="user_delete" target="selectedTodo" rel="ids" href="${contextPath }/basedata/provOrgCode/delete" title="确认要删除?"><span>删除省分机构代码对照</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th width="100">省分机构代码</th>
				<th width="100">省分机构名称</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${basedata}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.orgCode}</td>
				<td>${item.orgName}</td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>