<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=policy_data_jg.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>
				<th>保险公司</th>
				<th>代理险种</th>
				<th>保险单号</th>
				<th>保险期间</th>
				<th>缴费方式</th>
				<th>缴费方式</th>
				<th>从业人员姓名</th>
				<th>执业登记编号</th>
				<th>所属网点</th>
				<th>投保人名称</th>
				<th>被保险人名称</th>
				<th>保险保额</th>
				<th>保险费</th>
				<th>佣金</th>
				<th>保单状态</th>
			</tr>
			<c:forEach var="item" items="${policies}" varStatus="idx">
			<tr>
				<td>${idx.index+1 }</td>
				<td>中邮保险</td>
				<td>${item.prodName}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.policyNo}</td>
				<td>${item.duration}</td>
				<td>${item.feeType}</td>
				<td>${item.feeFrequency}</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>${item.bankName}</td>
				<td>${item.holder}</td>
				<td>${item.insured}</td>
				<td>${item.insuredAmount}</td>
				<td>${item.policyFee}</td>
				<td>&nbsp;</td>
				<td>${item.status}</td>
			</tr>
			</c:forEach>
	</table>
