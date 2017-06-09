<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=RI_LIST.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>
				<th>申请地市</th>
				<th>申请网点</th>
				<th>投保单号</th>
				<th>保单号</th>
				<th>保单机构</th>
				<th>申请人</th>
				<th>申请日期</th>
				<th>联系电话</th>
				<th>保全项目</th>
				<th>状态</th>
			</tr>
			<c:forEach var="item" items="${reqs}" varStatus="status">
			<tr>
				<td><c:out value="${status.index+1}"/></td>
				<td>${item.bankCode.organization.shortName}</td>
				<td>${item.bankCode.name}</td>
				<td>${item.formNo}</td>
				<td>${item.policy.policyNo}</td>
				<td>${item.policy.organization.name}</td>
				<td>${item.reqMan}</td>
				<td><fmt:formatDate value='${item.reqDate }' pattern='yyyy-MM-dd'/></td>
				<td>${item.reqPhone}</td>
				<td>${item.reqTypeName}</td>
				<td>
				<c:choose>
					<c:when test="${item.status eq 'CloseStatus'}">
						 已关闭
					</c:when>
					<c:when test="${item.status eq 'DealStatus'}">
						已处理
					</c:when>
					<c:when test="${item.status eq 'ReceiveStatus'}">
						已接收
					</c:when>
					<c:otherwise>
						<span style="color:red; height:50%; margin-bottom:-contentheight;">待处理</span>
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			</c:forEach>
	</table>
