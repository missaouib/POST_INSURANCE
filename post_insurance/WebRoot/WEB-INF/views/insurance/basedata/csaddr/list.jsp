<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/basedata/csAddr/list" page="${page }">
	<input type="hidden" name="search_LIKE_city" value="${param.search_LIKE_city }"/>
	<input type="hidden" name="search_LIKE_organCode" value="${param.search_LIKE_organCode }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/basedata/csAddr/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
			<tr>
				<td>城市：
					<input type="text" name="search_LIKE_city" value="${param.search_LIKE_city }"/>
				</td>
				<td>机构编码：
					<input type="text" name="search_LIKE_organCode" value="${param.search_LIKE_organCode }"/>
				</td>
			</tr>
			</table>
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
				<li><a class="add" target="dialog" rel="lookup2organization_add" mask="true" width="530" height="330" href="${contextPath }/basedata/csAddr/create"><span>添加</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="BankCode:edit">
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="530" height="330" href="${contextPath }/basedata/csAddr/update/{slt_uid}"><span>编辑</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="BankCode:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/basedata/csAddr/delete" title="确认要删除?"><span>删除</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th>机构编码</th>
				<th>所属省</th>
				<th>城市</th>
				<th>联系人</th>
				<th>电话</th>
				<th>地址</th>
				<th>邮编</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${basedata}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.organCode}</td>
				<td>${item.prov}</td>
				<td>${item.city}</td>
				<td>${item.linker}</td>
				<td>${item.phone}</td>
				<td>${item.addr}</td>
				<td>${item.postCode}</td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>