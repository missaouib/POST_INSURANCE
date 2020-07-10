<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=AG_STAT.xls");
%>
<table border="1" cellspacing="1" cellpadding="0">
	<tr>
		<th>序号</th>
		<th>机构名称</th>
		<th>满期件数</th>
		<th>AG件数</th>
		<th>件数进度</th>
		<th>金额进度</th>
	</tr>
	<c:forEach var="item" items="${cmRst}" varStatus="idx">
	<tr>
		<td>${idx.index+1 }</td>
		<td>${item.organName}</td>
		<td>${item.policyFee}</td>
		<td>${item.sumCsFee}</td>
		<td><fmt:formatNumber value="${item.sumCsFee/item.policyFee*100}" pattern="#,###.##" />%</td>
		<td><fmt:formatNumber value="${item.sumFee/item.sumPolicyFee*100}" pattern="#,###.##" />%</td>
	</tr>
	</c:forEach>
	<tr>
		<td>&nbsp;</td>
		<td>合计：</td>
		<td><fmt:formatNumber value="${sumTb}" pattern="#,###.#" /></td>
		<td><fmt:formatNumber value="${totalCS}" pattern="#,###.#" /></td>
		<td><fmt:formatNumber value="${totalCS/sumTb*100}" pattern="#,###.##" />%</td>
		<td><fmt:formatNumber value="${csMoney/csProfit*100}" pattern="#,###.##" />%</td>
	</tr>
</table>