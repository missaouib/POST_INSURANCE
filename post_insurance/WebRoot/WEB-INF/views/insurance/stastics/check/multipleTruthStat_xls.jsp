<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=truth_stat.xls");
%>
<table border="1" cellspacing="1" cellpadding="0">
		<thead>
			<tr>
				<th>序号</th>
				<th>名称</th>
				<th>保单件数</th>
				<th>差错件数</th>
				<th>整改件数</th>
				<th>及时件数</th>
				<th>综合合格率</th>
				<th>条线评比合格率</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${cmRst}" varStatus="idx">
			<tr>
				<td style="text-align: center;">${idx.index+1 }</td>
				<td>${item.organCode }</td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.policyCounts}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.checkCounts}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.errCounts}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.ontimeCounts}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.statFlag}" pattern="#,###.##" />%</td>
				<td style="text-align: right;"><fmt:formatNumber value="${((1-item.checkCounts/item.policyCounts)*0.6+item.errCounts/item.checkCounts*0.4)*100}" pattern="#,###.##" />%</td>
			</tr>
			</c:forEach>
			<tr>
				<td>&nbsp;</td>
				<td>合计：</td>
				<td style="text-align: right;"><fmt:formatNumber value="${countSum}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${checkSum}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${errSum}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${ontimeSum}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${finalRatio}" pattern="#,###.##" />%</td>
				<td>&nbsp;</td>
			</tr>
		</tbody>
	</table>