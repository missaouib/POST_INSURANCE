<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=tuibao_dtl.xls");
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
		<th>保全号</th>
		<th>保全日期</th>
		<th>保全代码</th>
		<th>渠道</th>
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
		<td>${item.csNo}</td>
		<td><fmt:formatDate value="${item.csDate}" pattern="yyyy-MM-dd"/></td>
		<td>${item.csCode}</td>
		<td>${item.netFlag eq "1"?"邮政":"银行"}</td>
	</tr>
	</c:forEach>
</table>