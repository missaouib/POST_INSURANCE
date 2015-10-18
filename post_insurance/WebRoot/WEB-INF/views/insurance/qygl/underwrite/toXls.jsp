<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=underwrite.xls");
%>
	<table>
			<tr>
				<th>市县机构</th>
				<th>投保单号</th>
				<th>保单号</th>
				<th>投保人</th>
				<th>被保人</th>
				<th>关系</th>
				<th>产品</th>
				<th>保费</th>
				<th>邮保通录入时间</th>
				<th>核心录入时间</th>
				<th>复核时间</th>
				<th>核保日期</th>
				<th>下通知书</th>
				<th>签单日期</th>
				<th>省分收到合同日</th>
				<th>省分寄出合同日</th>
				<th>合同签收日期</th>
				<th>回执录入日期</th>
			</tr>
			<c:forEach var="item" items="${reqs}">
			<tr>
				<td>${fn:replace(item.organization.name,'邮政局中邮保险局','')}</td>
				<td>${item.formNo}</td>
				<td>${item.policyNo}</td>
				<td>${item.holder}</td>
				<td>${item.insured}</td>
				<td>${item.relation}</td>
				<td>${item.prd.prdName}</td>
				<td>${item.policyFee}</td>
				<td><fmt:formatDate value="${item.ybtDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.sysDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.checkDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.underwriteDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.isLetter}</td>
				<td><fmt:formatDate value="${item.signDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.provReceiveDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.provSendDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.clientReceiveDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.signInputDate }" pattern="yyyy-MM-dd"/></td>
			</tr>
			</c:forEach>
	</table>
