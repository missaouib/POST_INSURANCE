<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta charset="UTF-8" />
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=uw_warning_dtl.xls");
%>
<table border="1" cellspacing="1" cellpadding="0">
	<tr>
		<th>序号</th>
		<th>机构代码</th>
		<th>机构名称</th>
		<th>保单号</th>
		<th>投保单号</th>
		<th>投保人</th>
		<th>险种名称</th>
		<th>保费</th>
		<th>签单日期</th>
		<th>合同寄出</th>
		<th>快递单号</th>
		<th>网点名称</th>
		<th>跟进日期</th>
		<th>跟进详情</th>
		<th>L+逾期日）</th>
	</tr>
	<c:forEach var="item" items="${cmRst}" varStatus="idx">
	<tr>
		<td>${idx.index+1 }</td>
		<td>${item.orgCode}</td>
		<td>${item.orgName}</td>
		<td style="vnd.ms-excel.numberformat:@">${item.policyNo}</td>
		<td style="vnd.ms-excel.numberformat:@">${item.formNo}</td>
		<td>${item.holder}</td>
		<td>${item.prdName}</td>
		<td>${item.policyFee}</td>
		<td><fmt:formatDate value="${item.signDate}" pattern="yyyy-MM-dd"/></td>
		<td><fmt:formatDate value="${item.provSendDate}" pattern="yyyy-MM-dd"/></td>
		<td>${item.provEmsNo}</td>
		<td>${item.netName}</td>
		<td>${item.planDate}</td>
		<td>${item.remark}</td>
		<td>${item.longPerm}</td>
	</tr>
	</c:forEach>
</table>