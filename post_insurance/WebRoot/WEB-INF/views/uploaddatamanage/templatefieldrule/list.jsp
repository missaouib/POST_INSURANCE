<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/uploaddatamanage/templatefieldrule/list" page="${page }">
	<input type="hidden" name="search_LIKE_ruleName" value="${param.search_LIKE_ruleName }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/uploaddatamanage/templatefieldrule/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>规则名称：</label>
					<input type="text" name="search_LIKE_ruleName" value="${param.search_LIKE_ruleName }"/>
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
			<shiro:hasPermission name="template:edit">
				<li><a iconClass="shield_add" target="dialog" rel="templatefieldrule_create_navTab" mask="true" width="550" height="400" href="${contextPath }/uploaddatamanage/templatefieldrule/create/${fieldID}"><span>添加规则</span></a></li>
				<li><a iconClass="shield_go" target="dialog" rel="templatefieldrule_update_navTab" mask="true" width="550" height="400" href="${contextPath }/uploaddatamanage/templatefieldrule/update/{slt_uid}"><span>编辑规则</span></a></li>
				<li><a iconClass="shield_delete" target="ajaxTodo" rel="templatefieldrule_navTab" href="${contextPath }/uploaddatamanage/templatefieldrule/delete/{slt_uid}" title="确认要删除?"><span>删除规则</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>		
				<th width="100">模板名称</th>
				<th width="100">模板类型</th>
				<th width="100">标准列名称</th>
				<th width="100">规则名称</th>
				<th width="100">分隔符</th>
				<th width="100">取值序号</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${rules}">
			<tr target="slt_uid" rel="${item.id}">
				<td>${item.tblMemberDataTemplateField.tblMemberDataTemplate.templateName}</td>
				<td>${item.tblMemberDataTemplateField.tblMemberDataTemplate.templateType == 0 ? "txt" : item.tblMemberDataTemplateField.tblMemberDataTemplate.templateType == 1 ? "csv" : item.tblMemberDataTemplateField.tblMemberDataTemplate.templateType == 2 ? "dbf" : item.tblMemberDataTemplateField.tblMemberDataTemplate.templateType == 3 ? "mdb" : item.tblMemberDataTemplateField.tblMemberDataTemplate.templateType == 4 ? "xls" : item.tblMemberDataTemplateField.tblMemberDataTemplate.templateType == 5 ? "xlsx" : ""}</td>
				<td>${item.tblMemberDataTemplateField.mapColumn}</td>
				<td>${item.ruleName}</td>
				<td>${item.splitChar}</td>
				<td>${item.valueIndex}</td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>