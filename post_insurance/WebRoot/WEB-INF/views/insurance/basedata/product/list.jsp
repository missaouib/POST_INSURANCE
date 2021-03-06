<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/basedata/prd/list" page="${page }">
	<input type="hidden" name="search_LIKE_prdCode" value="${param.search_LIKE_prdCode }"/>
	<input type="hidden" name="search_LIKE_prdName" value="${param.search_LIKE_prdName }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/basedata/prd/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
			<tr>
				<td>产品代码：
					<input type="text" name="search_LIKE_prdCode" value="${param.search_LIKE_prdCode }"/>
				</td>
				<td>产品名称：
					<input type="text" name="search_LIKE_prdName" value="${param.search_LIKE_prdName }"/>
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
			<shiro:hasPermission name="Prd:save">
				<li><a class="add" target="dialog" rel="lookup2organization_add" mask="true" width="530" height="330" href="${contextPath }/basedata/prd/create"><span>添加产品</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Prd:edit">
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="530" height="330" href="${contextPath }/basedata/prd/update/{slt_uid}"><span>编辑产品</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Prd:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/basedata/prd/delete" title="确认要删除?"><span>删除产品</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>ID</th>	
				<th>产品代码</th>
				<th>产品名称</th>
				<th>产品全称</th>
				<th>产品状态</th>
				<th>每份金额</th>
				<th>缴费期间</th>
				<th>保险期间</th>
				<th>最高赔付倍数</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${prds}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.id}</td>
				<td>${item.prdCode}</td>
				<td>${item.prdName}</td>
				<td>${item.prdFullName}</td>
				<td>${item.prdStatus}</td>
				<td>${item.prdPerMoney}</td>
				<td>${item.prdPerm}</td>
				<td>${item.duration}</td>
				<td>${item.multiple}</td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>