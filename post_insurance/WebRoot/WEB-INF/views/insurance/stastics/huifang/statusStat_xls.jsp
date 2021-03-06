<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=hf_stat.xls");
%>
<table border="1" cellspacing="1" cellpadding="0">
		<thead>
			<tr>
				<th>序号</th>
				<th>名称</th>
				<th>差错件数</th>
				<th>状态件数</th>
				<th>状态占比（整改进度）</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${cmRst}" varStatus="idx">
			<tr>
				<td style="text-align: center;">${idx.index+1 }</td>
				<td>${item.organCode }</td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.checkCounts-item.errCounts}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.policyCounts-item.errCounts}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${(1-(item.checkCounts-item.errCounts)/(item.policyCounts-item.errCounts))*100}" pattern="#,###.##" />%</td>
			</tr>
			</c:forEach>
			<tr>
				<td>&nbsp;</td>
				<td>合计：</td>
				<td style="text-align: right;"><fmt:formatNumber value="${sumPt}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${countPt}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${(1-sumPt/countPt)*100}" pattern="#,###.##" />%</td>
			</tr>
		</tbody>
	</table>
