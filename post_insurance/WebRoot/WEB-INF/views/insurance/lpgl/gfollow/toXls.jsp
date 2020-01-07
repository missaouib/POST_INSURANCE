<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=groupsettles.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>	
				<th>机构名称</th>
				<th>出险人</th>
				<th>保单号</th>
				<th>出险日期</th>
				<th>理赔类型</th>
				<th>赔案号</th>
				<th>结案日期</th>
				<th>赔付金额</th>
				<th>账户状态</th>
				<th>录入时间</th>
				<th>备注</th>
			</tr>
			<c:forEach var="item" items="${settles}" varStatus="idx">
			<tr>
				<td>${idx.index+1 }</td>
				<td>${item.organization.shortName}</td>
				<td>${item.insured}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.gsettleDtls.gpolicyNo}</td>
				<td><fmt:formatDate value="${item.caseDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.caseType}</td>
				<td>${item.gsettleDtls.claimsNo}</td>
				<td><fmt:formatDate value="${item.closeDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.payFee}</td>
				<td>${item.caseStatus}</td>
				<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${item.remark}</td>
			</tr>
			</c:forEach>
	</table>
