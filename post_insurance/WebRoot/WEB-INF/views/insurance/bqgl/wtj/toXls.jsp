<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=BQ_RECORD.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>
				<th>保单号</th>
				<th>保单所属机构</th>
				<th>保全受理号</th>
				<th>保全项目</th>
				<th>复核修改问题</th>
				<th>复核修改问题描述</th>
				<th>问题产生日期</th>
				<th>状态</th>
			</tr>
			<c:forEach var="item" items="${reqs}" varStatus="status">
			<tr>
				<td><c:out value="${status.index+1}"/></td>
				<td style="vnd.ms-excel.numberformat:@">${item.policy.policyNo}	</td>
				<td>${item.policy.organization.shortName}</td>
				<td>${item.dealNum}</td>
				<td>${item.conservationCode}</td>
				<td>${item.info}</td>
				<td>${item.remark}</td>
				<td>${item.csDate}</td>
				<td>
				<c:choose>
					<c:when test="${item.status eq 'NewStatus'}">
						 <span style="color:red; height:50%; margin-bottom:-contentheight;">待处理</span>
					</c:when>
					<c:when test="${item.status eq 'DealStatus'}">
						已处理
					</c:when>
					<c:when test="${item.status eq 'CancelStatus'}">
						已撤销
					</c:when>
					<c:otherwise>
						已关闭
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			</c:forEach>
	</table>
