<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
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
				<th>综合合格率</th>
				<th>条线评比合格率</th>
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
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.checkRecordErrCounts}" pattern="#,###" /></td>
				<td style="vnd.ms-excel.numberformat: #0.00%">${tem.checkCounts==0?'/':((item.checkCounts-item.errCounts)/item.checkCounts*0.8+(item.checkRecordCounts==0?0.2:(item.checkRecordCounts-item.checkRecordErrCounts)/item.checkRecordCounts*0.2)) }</td>
				<td style="text-align: center;"><fmt:formatNumber type="percent" maxFractionDigits="2" value="${(empty item.checkCounts or item.checkCounts==0)?'1':(((item.checkCounts-item.errCounts)/item.checkCounts*0.9)+(empty item.checkRecordCounts or item.checkRecordCounts==0?0.1:(item.checkRecordCounts-item.checkRecordErrCounts)/item.checkRecordCounts*0.1))  }" /></td>
			</tr>
			</c:forEach>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>合计：</td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${totalPolicyCount}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${totalCheck}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${totalErr}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${totalRecordCheck}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${totalRecordErr}" pattern="#,###.#" /></td>
				<td style="vnd.ms-excel.numberformat: #0.00%">${(totalCheck-totalErr)/totalCheck*0.80+(totalRecordCheck-totalRecordErr)/totalRecordCheck*0.20}</td>
				<td style="vnd.ms-excel.numberformat: #0.00%">${((totalCheck-totalErr)/totalCheck*0.9)+((totalRecordCheck-totalRecordErr)/totalRecordCheck*0.1) }</td>
			</tr>
		</tbody>
	</table>