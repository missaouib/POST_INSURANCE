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
				<th>地市</th>
				<th>客户姓名</th>
				<th>保单号</th>
				<th>申请日期</th>
				<th>快递单号</th>
				<th>省分寄出日期</th>
				<th>地市接收日期</th>
				<th>状态</th>
			</tr>
			<c:forEach var="item" items="${reqs}" varStatus="status">
			<tr>
				<td><c:out value="${status.index+1}"/></td>
				<td>
				<c:choose>  
				    <c:when test="${fn:contains(item.csReport.policy.organization.name, '直属')}">  
				        <c:out value="${fn:replace(item.csReport.policy.organization.name,'邮政局直属中邮保险局','直属')}" />  
				    </c:when>  
				   <c:otherwise>  
				      <c:out value="${fn:replace(item.csReport.policy.organization.name,'邮政局中邮保险局','')}" />  
				    </c:otherwise>  
				</c:choose>
				</td>
				<td>${item.csReport.policy.holder}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.csReport.policy.policyNo}</td>
				<td><fmt:formatDate value='${item.csReport.csDate }' pattern='yyyy-MM-dd'/></td>
				<td>${item.provExpressNo}</td>
				<td>${item.provSentDate}</td>
				<td>${item.cityReceiveDate}</td>
				<td>
				<c:choose>
					<c:when test="${item.status eq 'CloseStatus'}">
						 已关闭
					</c:when>
					<c:when test="${item.status eq 'DealStatus'}">
						已寄出
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
