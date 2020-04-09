<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=policy_stat.xls");
%>
<table border="1" cellspacing="1" cellpadding="0">
	<tr>
		<th>序号</th>
		<th>名称</th>
		<th>件数</th>
		<th>件数占比</th>
		<th>保费</th>
		<th>保费占比</th>
		<th>已交保费</th>
	</tr>
	<c:forEach var="item" items="${cmRst}" varStatus="idx">
	<tr>
		<td>${idx.index+1 }</td>
		<td>${item.statName}</td>
		<td>${item.policyCount}</td>
		<td><fmt:formatNumber value="${item.policyCount/countPt*100}" pattern="#,###.##" />%</td>
		<td>${item.policyFee}</td>
		<td><fmt:formatNumber value="${item.policyFee/sumPt*100}" pattern="#,###.##" />%</td>
		<td><fmt:formatNumber value="${item.hadPolicyFee}" pattern="#,###.##" /></td>
	</tr>
	</c:forEach>
	<tr>
		<td>&nbsp;</td>
		<td>合计：</td>
		<td style="text-align: right;"><fmt:formatNumber value="${countPt}" pattern="#,###.##" /></td>
		<td>&nbsp;</td>
		<td style="text-align: right;"><fmt:formatNumber value="${sumPt}" pattern="#,###.##" /></td>
		<td>&nbsp;</td>
		<td style="text-align: right;"><fmt:formatNumber value="${sumHadPolicyFee}" pattern="#,###.##" /></td>
	</tr>
</table>