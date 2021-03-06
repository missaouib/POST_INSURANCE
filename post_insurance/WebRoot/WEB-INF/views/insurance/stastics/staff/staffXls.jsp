<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=staff_warnning.xls");
%>
<table border="1" cellspacing="1" cellpadding="0">
	<tr>
		<th>序号</th>
		<th>机构</th>
		<th>员工单</th>
		<th>总件数</th>
		<th>单量占比</th>
		<th>员工单保费</th>
		<th>总保费</th>
		<th>保费占比</th>
	</tr>
	<c:forEach var="item" items="${cmRst}" varStatus="idx">
	<tr target="slt_uid" rel="${item.organName}">
		<td>${idx.index+1 }</td>
		<td>${item.organName}</td>
		<td><fmt:formatNumber value="${item.staffCount}" pattern="#,###" /></td>
		<td><fmt:formatNumber value="${item.sumStaffCount}" pattern="#,###" /></td>
		<td><fmt:formatNumber value="${item.staffCount/item.sumStaffCount*100}" pattern="#,###.#" />%</td>
		<td><fmt:formatNumber value="${item.policyFee/10000}" pattern="#,###.#" /></td>
		<td><fmt:formatNumber value="${item.sumPolicyFee/10000}" pattern="#,###.#" /></td>
		<td><fmt:formatNumber value="${item.policyFee/item.sumPolicyFee*100}" pattern="#,###.#" />%</td>
	</tr>
	</c:forEach>
	<tr>
		<td>&nbsp;</td>
		<td>合计：</td>
		<td style="text-align: right;"><fmt:formatNumber value="${csumTb}" pattern="#,###.#" /></td>
		<td style="text-align: right;"><fmt:formatNumber value="${ctotalTb}" pattern="#,###.#" /></td>
		<td style="text-align: right;"><fmt:formatNumber value="${csumTb/ctotalTb*100}" pattern="#,###.#" />%</td>
		<td style="text-align: right;"><fmt:formatNumber value="${ssumTb}" pattern="#,###.#" /></td>
		<td style="text-align: right;"><fmt:formatNumber value="${stotalTb}" pattern="#,###.#" /></td>
		<td style="text-align: right;"><fmt:formatNumber value="${ssumTb/stotalTb*100}" pattern="#,###.#" />%</td>
	</tr>
</table>