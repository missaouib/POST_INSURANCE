<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=reprint_LIST.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
		<thead>
			<tr>
				<th>机构号</th>
				<th>投保单号</th>
				<th>保单号</th>
				<th>状态</th>
				<th>快递单号</th>
				<th>寄出日期</th>
			</tr>
		</thead>
		<c:forEach var="item" items="${policies}">
		<tr target="slt_uid" rel="${item.id}">
			<td>${item.orgCode}</td>
			<td>${item.formNo}</td>
			<td>${item.policyNo}</td>
			<td>${item.status}</td>
			<td>${item.emsNo}</td>
			<td><fmt:formatDate value="${item.printDate }" pattern="yyyy-MM-dd"/></td>
		</tr>
		</c:forEach>
	</table>
