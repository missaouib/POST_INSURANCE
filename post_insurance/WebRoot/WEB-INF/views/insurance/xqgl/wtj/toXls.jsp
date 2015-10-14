<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=XQ_Xls.xls");
%>
	<table>
			<tr>
				<th>所属机构</th>
				<th>保单号</th>
				<th>险种名称</th>
				<th>姓名</th>
				<th>联系电话</th>
				<th>保费</th>
				<th>交费对应日</th>
				<th>宽限期还有（天）</th>
				<th>状态</th>
				<th>交费失败原因</th>
				<th>账号</th>
				<th>网点</th>
				<th>总部催收情况</th>
				<th>省分催收情况</th>
				<th>市县催收情况</th>
			</tr>
			<c:forEach var="item" items="${reqs}">
			<tr>
				<td>${fn:replace(item.policy.organization.name,'中邮保险局','')}</td>
				<td>${item.policy.policyNo}</td>
				<td>${item.prdName}</td>
				<td>${item.holder}</td>
				<td>${item.mobile eq ""?item.phone:item.mobile}</td>
				<td>${item.policyFee}</td>
				<td><fmt:formatDate value="${item.feeDate }" pattern="yyyy-MM-dd"/></td>
				<td><span style="color:red; height:50%; margin-bottom:-contentheight;">${item.lastDateNum }</span></td>
				<td>${item.feeStatus }</td>
				<td>${item.feeFailReason}</td>
				<td>${item.account}</td>
				<td>
					<c:choose>  
					    <c:when test="${fn:length(item.netName) > 14}">  
					        <c:out value="${fn:substring(item.netName, 14, 30)}" />  
					    </c:when>  
					   <c:otherwise>  
					      <c:out value="${item.netName}" />  
					    </c:otherwise>  
					</c:choose>
				</td>
				<td>${item.hqIssueType}</td>
				<td>${item.provIssueType}</td>
				<td>${item.dealType}</td>
			</tr>
			</c:forEach>
	</table>
