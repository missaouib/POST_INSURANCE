<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=qyhb_stat.xls");
%>
<table border="1" cellspacing="1" cellpadding="0">
		<thead>
			<tr>
				<th>序号</th>
				<th>机构名称</th>
				<th>耗时时长</th>
				<th>人核件件数</th>
				<th>回执回销件数</th>
				<th>5日作业完成件数</th>
				<th>5日回销件数</th>
				<th>回执回销率</th>
				<th>5日作业完成率</th>
				<th>5日回执回销率</th>
				<th>全流程时效</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${cmRst}" varStatus="idx">
			<tr>
				<td>${idx.index+1 }</td>
				<td>${item.organName}</td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.sumdays}" pattern="#,###" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.policyCounts}" pattern="#,###" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.huixiaoCounts}" pattern="#,###" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.job5ds}" pattern="#,###" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.huixiao5ds}" pattern="#,###.#" /></td>
				<td style="text-align: center;"><fmt:formatNumber type="percent" maxFractionDigits="2" value="${item.huixiaoCounts/item.policyCounts  }" /></td>
				<td style="text-align: center;"><fmt:formatNumber type="percent" maxFractionDigits="2" value="${item.job5ds/item.policyCounts  }" /></td>
				<td style="text-align: center;"><fmt:formatNumber type="percent" maxFractionDigits="2" value="${item.huixiao5ds/item.huixiaoCounts  }" /></td>
				<td style="text-align: center;"><fmt:formatNumber pattern="#,###.##" value="${item.sumdays/item.policyCounts  }" /></td>
			</tr>
			</c:forEach>
			<tr>
				<td>&nbsp;</td>
				<td>合计：</td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${sumdays}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${policyCounts}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${huixiaoCounts}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${job5ds}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${huixiao5ds}" pattern="#,###.#" /></td>
				<td style="text-align: center;"><fmt:formatNumber type="percent" maxFractionDigits="2" value="${hxRatio }" /></td>
				<td style="text-align: center;"><fmt:formatNumber type="percent" maxFractionDigits="2" value="${job5dsRatio }" /></td>
				<td style="text-align: center;"><fmt:formatNumber type="percent" maxFractionDigits="2" value="${hx5dsRatio }" /></td>
				<td style="text-align: center;"><fmt:formatNumber pattern="#,###.##" value="${qlc }" /></td>
			</tr>
		</tbody>
	</table>