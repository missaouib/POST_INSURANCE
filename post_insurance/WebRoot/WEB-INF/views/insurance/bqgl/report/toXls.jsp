<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=CS_REPORT_LIST.xls");
%>
<table border="1" cellspacing="1" cellpadding="0">
<thead>
	<tr>
		<th>序号</th>
		<th>保全受理号</th>
		<th>保单号</th>
		<th>险种名称</th>
		<th>机构简称</th>
		<th>所属机构</th>
		<th>网点名称</th>
		<th>渠道</th>
		<th>操作机构代码</th>
		<th>投保人姓名</th>
		<th>被保险人姓名</th>
		<th>承保日期</th>
		<th>通过日期</th>
		<th>项目编码</th>
		<th>金额</th>
		<th>申请方式</th>
	</tr>
</thead>
<tbody>
	<c:forEach var="item" items="${issues}">
	<tr>
		<td>${idx.index+1 }</td>
		<td>${item.csNo}</td>
		<td style="vnd.ms-excel.numberformat:@">${item.policy.policyNo}</td>
		<td>${item.policy.prodName}</td>
		<td>${item.policy.organName}</td>
		<td>${item.organName}</td>
		<td>${item.netName}</td>
		<td>${item.csChannel}</td>
		<td>${item.operateOrg}</td>
		<td>${item.holder}</td>
		<td>${item.insured}</td>
		<td><fmt:formatDate value="${item.policy.policyDate }" pattern="yyyy-MM-dd"/></td>
		<td><fmt:formatDate value="${item.csDate }" pattern="yyyy-MM-dd"/></td>
		<td>${item.fullCsCode}</td>
		<td>${item.money}</td>
		<td>${item.csDeal}</td>
	</tr>
	</c:forEach>
	</tbody>
</table>
