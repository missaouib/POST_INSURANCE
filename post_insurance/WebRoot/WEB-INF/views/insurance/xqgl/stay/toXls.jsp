<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=RENEWED_STAY.xls");
%>
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th>上报人</th>
				<th>保单机构</th>
				<th>保单号</th>
				<th>投保人</th>
				<th>退保时间</th>
				<th>退保次数</th>
				<th>挽留详情</th>
				<th>联系电话</th>
				<th>险种名称</th>
				<th>缴费期间</th>
				<th>保费</th>
				<th>承保时间</th>
				<th>员工</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${stays}">
			<tr>
				<td>${item.user.realname}</td>
				<td>${item.policy.organization.shortName}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.policy.policyNo}</td>
				<td>${item.policy.holder}</td>
				<td><fmt:formatDate value='${item.csDate }' pattern='yyyy-MM-dd'/></td>
				<td>${item.stayNum}</td>
				<td>${item.remark}</td>
				<td>${item.policy.policyDtl.holderPhone}</td>
				<td>${item.policy.prodName}</td>
				<td>${item.policy.perm}</td>
				<td>${item.policy.policyFee}</td>
				<td>${item.policy.policyDate}</td>
				<td>${item.policy.isStaff}</td>
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
		</tbody>
	</table>
