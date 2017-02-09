<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=tuibao_warnning.xls");
%>
<table border="1" cellspacing="1" cellpadding="0">
	<tr>
		<th>序号</th>
		<th>机构名称</th>
		<th>退保费</th>
		<th>总保费</th>
		<th>占比</th>
	</tr>
	<c:forEach var="item" items="${cmRst}" varStatus="idx">
	<tr target="slt_uid" rel="${item.organName}">
		<td>${idx.index+1 }</td>
		<td>${item.organName}</td>
		<td>${item.policyFee/10000}</td>
		<td>${item.sumPolicyFee/10000}</td>
		<td><fmt:formatNumber value="${item.policyFee/item.sumPolicyFee*100}" pattern="#,###.##" />%</td>
	</tr>
	</c:forEach>
</table>