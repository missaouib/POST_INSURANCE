<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/uploaddatamanage/template/list" page="${page }">
	<input type="hidden" name="search_LIKE_templateName" value="${param.search_LIKE_templateName }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/uploaddatamanage/template/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>模板名称：</label>
					<input type="text" name="search_LIKE_templateName" value="${param.search_LIKE_templateName }"/>
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
			<shiro:hasPermission name="template:save">
				<li><a iconClass="shield_add" target="dialog" rel="template_navTab" mask="true" width="530" height="330" href="${contextPath }/uploaddatamanage/template/create"><span>添加模板</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="template:save">
				<li><a iconClass="shield_add" target="dialog" rel="template_navTab" mask="true" width="530" height="230" href="${contextPath }/uploaddatamanage/template/copy/{slt_uid}"><span>复制模板</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="template:edit">
				<li><a iconClass="shield_go" target="dialog" rel="template_navTab" mask="true" width="530" height="330" href="${contextPath }/uploaddatamanage/template/update/{slt_uid}"><span>编辑模板</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="template:delete">
				<li><a iconClass="shield_delete" target="ajaxTodo" rel="template_navTab" href="${contextPath }/uploaddatamanage/template/delete/{slt_uid}" title="确认要删除?"><span>删除模板</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="template:edit">
				<li><a iconClass="database_go" target="navTab" rel="templatefield_navTab" href="${contextPath }/uploaddatamanage/templatefield/list/{slt_uid}"><span>模板标准列对应</span></a></li>
			</shiro:hasPermission>			
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>		
				<th width="100">模板名称</th>
				<th width="100">模板类型</th>
				<th width="60" orderField="status" class="${page.orderField eq 'status' ? page.orderDirection : ''}">状态</th>
				<th width="130" orderField="createDate" class="${page.orderField eq 'createDate' ? page.orderDirection : ''}">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${templates}">
			<tr target="slt_uid" rel="${item.id}">
				<td>${item.templateName}</td>
				<td>${item.templateType == 0 ? "txt" : item.templateType == 1 ? "csv" : item.templateType == 2 ? "dbf" : item.templateType == 3 ? "mdb" : item.templateType == 4 ? "xls" : item.templateType == 5 ? "xlsx" : ""}</td>
				<td>${item.status == 1 ? "默认":"非默认"}</td>
				<td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>