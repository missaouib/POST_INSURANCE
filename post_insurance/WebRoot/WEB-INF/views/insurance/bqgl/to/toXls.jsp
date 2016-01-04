<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=BQ_RECORD.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>地市</th>
				<th>经办人</th>
				<th>转办日期</th>
				<th>快递单号</th>
				<th>保单号</th>
				<th>出单省</th>
				<th>客户姓名</th>
				<th>保全业务</th>
				<th>联系人</th>
				<th>寄件地址</th>
				<th>省分转办日期</th>
				<th>状态</th>
			</tr>
			<c:forEach var="item" items="${reqs}">
			<tr>
				<td>
				<c:choose>  
				    <c:when test="${fn:contains(item.organization.name, '直属')}">  
				        <c:out value="${fn:replace(item.organization.name,'邮政局直属中邮保险局','直属')}" />  
				    </c:when>  
				   <c:otherwise>  
				      <c:out value="${fn:replace(item.organization.name,'邮政局中邮保险局','')}" />  
				    </c:otherwise>  
				</c:choose>
				</td>
				<td>${item.transactor}</td>
				<td><fmt:formatDate value='${item.dealDate }' pattern='yyyy-MM-dd'/></td>
				<td>${item.expressBillNo}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.policyNo}</td>
				<td>${item.orginProv}</td>
				<td>${item.client}</td>
				<td>${item.conservationType}</td>
				<td>${item.linker}</td>
				<td>${item.mailAddr}</td>
				<td><fmt:formatDate value='${item.provDealDate }' pattern='yyyy-MM-dd'/></td>
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
