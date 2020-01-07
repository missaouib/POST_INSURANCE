<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=check_dtl.xls");
%>
<table border="1" cellspacing="1" cellpadding="0">
		<thead>
			<tr>
				<th>序号</th>
				<th>机构编号</th>
				<th>机构名称</th>
				<th>抽检批次号</th>
				<th>保单号</th>
				<th>投保人</th>
				<th>电话</th>
				<th>网点</th>
				<th>是否需要整改</th>
				<th>关键差错</th>
				<th>状态</th>
				<th>处理详情</th>
				<th>抽检人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${cmRst}" varStatus="idx">
			<tr>
				<td>${idx.index+1 }</td>
				<td>${item.organCode}</td>
				<td>${item.organName}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.checkBatch}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.policyNo}</td>
				<td>${item.holder}</td>
				<td style="vnd.ms-excel.numberformat:@">${empty item.holderMobile?item.holderPhone:item.holderMobile}</td>
				<td>${item.bankName}</td>
				<td>${item.needFix}</td>
				<td>${item.keyInfo}</td>
				<td>${item.fixStatus}</td>
				<td>${item.fixDesc}</td>
				<td>${item.checker}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>