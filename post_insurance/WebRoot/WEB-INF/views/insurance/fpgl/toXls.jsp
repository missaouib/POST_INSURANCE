<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=InvoiceReq.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>
				<td>保单号</td>
				<td>投保人</td>
				<td>保单机构</td>
				<td>发票标记</td>
				<td>发票金额</td>
				<td>发票缴费日期</td>
				<td>申请日期</td>
				<td>EMS</td>
				<td>申请接收人</td>
				<td>状态</td>
			</tr>
			<c:forEach var="item" items="${reqs}" varStatus="status">
			<tr>
				<td><c:out value="${status.index+1}"/></td>
				<td style="vnd.ms-excel.numberformat:@">${item.policy.policyNo}</td>
				<td>${item.policy.holder}</td>
				<td>${item.policy.organization.name}</td>
				<td>${item.flag}</td>
				<td>${item.fee}</td>
				<td><fmt:formatDate value="${item.feeDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.reqDate }" pattern="yyyy-MM-dd"/></td>
				<td style="vnd.ms-excel.numberformat:@">${item.billNo}</td>
				<td>${item.reqMan}</td>
				<td>
				<c:choose>
					<c:when test="${item.status eq 'NewStatus'}">
						<span style="color:red; height:50%; margin-bottom:-contentheight;">待处理</span>
					</c:when>
					<c:when test="${item.status eq 'DealStatus'}">
						已寄出
					</c:when>
					<c:when test="${item.status eq 'ReceiveStatus'}">
						已接收
					</c:when>
					<c:otherwise>
						已关闭
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			</c:forEach>
	</table>
