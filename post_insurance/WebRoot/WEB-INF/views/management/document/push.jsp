<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib prefix="dwz" uri="http://www./dwz"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<dwz:paginationForm action="${contextPath }/management/document/list" page="${page }">
	<input type="hidden" name="search_LIKE_resource.name" value="${param.search_LIKE_resource.name }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/management/document/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>文件名称：</label>
					<input type="text" name="search_LIKE_resource.name" value="${param.search_LIKE_resource.name }"/>
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
			<shiro:hasPermission name="Push:add">
				<li><a iconClass="page_white_get" target="dialog" mask="true" width="430" height="300" close="close2upload" href="${contextPath }/management/document/push"><span>设置推送</span></a></li>
			</shiro:hasPermission>		
			<shiro:hasPermission name="Push:delete">
				<li><a iconClass="page_white_delete" target="push_add" rel="ids" href="${contextPath }/management/document/delete" title="确认要删除选定推送?"><span>删除推送</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>报告类型</th>
				<th >文件名称</th>
				<th >会员</th>
				<th >推送时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${resources}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.storeType.tostring() == "file" ? "药品零售市场研究报告" : (item.storeType.tostring() == "file2" ? "品类管理报告" : (item.storeType.tostring() == "file3" ? "消费者购药U&A研究" : "其他")) }</td>
				<td>${item.getResource().name}</td>
				<td>${item.getTblMember().memberName}</td>
				<td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>