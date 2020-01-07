<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=uw_stats.xls");
%>
<table border="1" cellspacing="1" cellpadding="0">
	<thead>
			<tr>
				<th>序号</th>
				<th>机构</th>
				<th>超10天未回销</th>
				<th>超20天未回销</th>
				<th>超30天未回销</th>
				<th>超50天未回销</th>
				<th>合计</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${cmRst}" varStatus="idx">
			<tr>
				<td>${idx.index+1 }</td>
				<td>${item.orgName}</td>
				<td style="text-align: right;width: 100px;font-weight:800; "><fmt:formatNumber value="${item.l10 eq 0?'':item.l10}" pattern="#,###" /></td>
				<td style="text-align: right;width: 100px;font-weight:800;"><fmt:formatNumber value="${item.l20 eq 0?'':item.l20}" pattern="#,###" /></td>
				<td style="text-align: right;width: 100px;font-weight:800;"><fmt:formatNumber value="${item.l30 eq 0?'':item.l30}" pattern="#,###.#" /></td>
				<td style="text-align: right;width: 100px;font-weight:800;"><fmt:formatNumber value="${item.l50 eq 0?'':item.l50}" pattern="#,###.#" /></td>
				<td style="text-align: right;width: 100px;font-weight:800;"><fmt:formatNumber value="${item.sc}" pattern="#,###.#" /></td>
			</tr>
			</c:forEach>
			<tr>
				<td>&nbsp;</td>
				<td>合计：</td>
				<td style="text-align: right;width: 100px;font-weight:800;"><fmt:formatNumber value="${tl10}" pattern="#,###.#" /></td>
				<td style="text-align: right;width: 100px;font-weight:800;"><fmt:formatNumber value="${tl20}" pattern="#,###.#" /></td>
				<td style="text-align: right;width: 100px;font-weight:800;"><fmt:formatNumber value="${tl30}" pattern="#,###.#" /></td>
				<td style="text-align: right;width: 100px;font-weight:800;"><fmt:formatNumber value="${tl50}" pattern="#,###.#" /></td>
				<td style="text-align: right;width: 100px;font-weight:800;"><fmt:formatNumber value="${tsc}" pattern="#,###.#" /></td>
			</tr>
		</tbody>
</table>