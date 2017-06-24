<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<form method="post" action="${contextPath }/management/security/user/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
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
				<th>机构名称</th>
				<th>承保件数</th>
				<th>未扫描件数</th>
				<th>扫描进度</th>
				<th>重点关注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${statData}">
			<tr>
				<td>${item.orgName}</td>
				<td>${item.allDoc}</td>
				<td>${item.sumDoc}</td>
				<td><fmt:formatNumber value="${item.percent}" pattern="#,###.##" />%</td>
				<td>${item.remark}</td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
</div>