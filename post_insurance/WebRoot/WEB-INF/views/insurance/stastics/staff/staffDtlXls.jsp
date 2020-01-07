<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8" />
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=staff_dtl.xls");
%>
<table border="1" cellspacing="1" cellpadding="0">
	<tr>
		<th>序号</th>
		<th>机构名称</th>
		<th>保单号</th>
		<th>投保人</th>
		<th>险种名称</th>
		<th>保费</th>
		<th>保单日期</th>
		<th>缴费方式</th>
		<th>缴费期限</th>
		<th>网点属性</th>
		<th>网点名称</th>
	</tr>
	<c:forEach var="item" items="${cmRst}" varStatus="idx">
	<tr>
		<td>${idx.index+1 }</td>
		<td>${item.organName}</td>
		<td style="vnd.ms-excel.numberformat:@">${item.policyNo}</td>
		<td>${item.holder}</td>
		<td>${item.prodName}</td>
		<td>${item.policyFee}</td>
		<td><fmt:formatDate value="${item.policyDate}" pattern="yyyy-MM-dd"/></td>
		<td>${item.feeFrequency}</td>
		<td>${item.perm}</td>
		<td>${item.bankName}</td>
		<td>${item.netFlag eq "1"?"邮政":"银行"}</td>
	</tr>
	</c:forEach>
</table>