<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=print_stat.xls");
%>
<table border="1" cellspacing="1" cellpadding="0">
		<thead>
			<tr>
				<th>序号</th>
				<th>名称</th>
				<th>承保件</th>
				<th>打印件数</th>
				<th>申请率</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${cmRst}" varStatus="idx">
			<tr>
				<td style="text-align: center;">${idx.index+1 }</td>
				<td>${item.orgName }</td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.policyCounts}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.checkCounts}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${(item.checkCounts/item.policyCounts)*100}" pattern="#,###.##" />%</td>
			</tr>
			</c:forEach>
			<tr>
				<td>&nbsp;</td>
				<td>合计：</td>
				<td style="text-align: right;"><fmt:formatNumber value="${countPt}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${sumPt}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${(sumPt/countPt)*100}" pattern="#,###.##" />%</td>
			</tr>
		</tbody>
	</table>