<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib prefix="dwz" uri="http://www./dwz"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<dwz:paginationForm action="${contextPath }/uploaddatamanage/document/list" page="${page }">
	<input type="hidden" name="search_LIKE_resource.name" value="${param.search_LIKE_resource.name}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/uploaddatamanage/document/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>文件名称：</label>
					<input type="text" name="search_LIKE_resource.name" value="${param.search_LIKE_resource.name}"/>
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
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th >文件名称</th>
				<th width="100">状态</th>
				<th width="100">浏览</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${resources}">
			<tr target="slt_uid" rel="${item.id}">
				<td>${item.getResource().name}</td>
				<td>${item.status==0?"未读":"已读"}</td>
				<td>
					<shiro:hasPermission name="Document:view">
						<a iconClass="page_white_put" target="_blank" href="${contextPath }/uploaddatamanage/document/view/${item.id}" title="浏览">浏览</a>
					</shiro:hasPermission>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>