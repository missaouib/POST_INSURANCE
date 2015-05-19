<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/uploaddatamanage/templatefield/list/${templateid}" page="${page }">
</dwz:paginationForm>

<form method="post" action="${contextPath }/uploaddatamanage/templatefield/list/${templateid}" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>

				</li>
			</ul>
			<div class="subBar">
				<ul>

				</ul>
			</div>
		</div>
	</div>
</form>
	
<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="template:edit">
				<li><a iconClass="shield_go" target="dialog" width="550" height="600" rel="templatefield_edit_navTab" href="${contextPath }/uploaddatamanage/templatefield/update/{slt_uid}"><span>修改标准列对应</span></a></li>
				<li><a iconClass="database_go" target="navTab" rel="templatefieldrule_navTab" href="${contextPath }/uploaddatamanage/templatefieldrule/list/{slt_uid}"><span>列规则</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>		
				<th width="100">模板名称</th>
				<th width="60">模板类型</th>
				<th width="60">默认模板</th>				
				<th width="100">标准列名称</th>
				<th width="100">是否固定值</th>
				<th width="100">固定值</th>
				<th width="100">是否对应列</th>
				<th width="100">是否列内容</th>				
				<th width="100">数据列序号</th>
				<th width="100">列名</th>
				<th width="100">是否文件名</th>
				<th width="100">是否Sheet名</th>
				<th width="100">是否多列运算</th>
				<th width="100">多列运算公式</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${fields}">
			<tr target="slt_uid" rel="${item.id}">
				<td>${item.tblMemberDataTemplate.templateName}</td>
				<td>${item.tblMemberDataTemplate.templateType == 0 ? "txt" : item.tblMemberDataTemplate.templateType == 1 ? "csv" : item.tblMemberDataTemplate.templateType == 2 ? "dbf" : item.tblMemberDataTemplate.templateType == 3 ? "mdb" : item.tblMemberDataTemplate.templateType == 4 ? "xls" : item.tblMemberDataTemplate.templateType == 5 ? "xlsx" : ""}</td>
				<td>${item.tblMemberDataTemplate.status == 0 ? "否" : "是"}</td>
				<td>${item.mapColumn}</td>
				<td>${item.isStaticValue == 0 ? "否" : "是"}</td>
				<td>${item.staticValue}</td>
				<td>${item.isUsingMapcolumn == 0 ? "否" : "是"}</td>	
				<td>${item.isUsingColumn == 0 ? "否" : "是"}</td>
				<td>${item.dataColumn}</td>
				<td>${item.columnName}</td>
				<td>${item.isUsingFilename == 0 ? "否" : "是"}</td>
				<td>${item.isUsingSheetname == 0 ? "否" : "是"}</td>
				<td>${item.isUsingMulticolumn == 0 ? "否" : "是"}</td>
				<td>${item.multicolumn}</td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>