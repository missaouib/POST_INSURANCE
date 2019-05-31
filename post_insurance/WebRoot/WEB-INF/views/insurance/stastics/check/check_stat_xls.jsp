<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=check_stat.xls");
%>
<table border="1" cellspacing="1" cellpadding="0">
		<thead>
			<tr>
				<th>序号</th>
				<th>机构编号</th>
				<th>机构名称</th>
				<th>承保件數</th>
				<th>抽检填写件数</th>
				<th>填写差错</th>
				<th>抽检录入件数</th>
				<th>录入差错</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${cmRst}" varStatus="idx">
			<tr>
				<td>${idx.index+1 }</td>
				<td>${item.organCode}</td>
				<td>${item.orgName}</td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.policyCounts}" pattern="#,###" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.checkCounts}" pattern="#,###" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.errCounts}" pattern="#,###" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.checkRecordCounts}" pattern="#,###" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.checkRecordErrCounts}" pattern="#,###.#" /></td>
			</tr>
			</c:forEach>
			<tr>
				<td>&nbsp;</td>
				<td>合计：</td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${tl10}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${tl20}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${tl30}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${tl50}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${tsc}" pattern="#,###.#" /></td>
			</tr>
		</tbody>
	</table>