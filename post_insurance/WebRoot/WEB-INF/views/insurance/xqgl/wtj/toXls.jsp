<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=XQ_Xls.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>
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
				<th>总部催收详情</th>
				<th>省分催收情况</th>
				<th>省分催收详情</th>
				<th>省分催收时间</th>
				<th>市县催收情况</th>
				<th>市县催收详情</th>
				<th>市县催收时间</th>
				<th>活动标记</th>
			</tr>
			<c:forEach var="item" items="${reqs}" varStatus="status">
			<tr>
				<td><c:out value="${status.index+1}"/></td>
				<td>${fn:replace(item.policy.organization.name,'中邮保险局','')}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.policy.policyNo}</td>
				<td>${item.prdName}</td>
				<td>${item.holder}</td>
				<td>${item.mobile eq ""?item.phone:item.mobile}</td>
				<td>${item.policyFee}</td>
				<td><fmt:formatDate value="${item.feeDate }" pattern="yyyy-MM-dd"/></td>
				<td><span style="color:red; height:50%; margin-bottom:-contentheight;">${item.lastDateNum }</span></td>
				<td>${item.feeStatus }</td>
				<td>${item.feeFailReason}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.account}</td>
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
				<td>${item.hqDealRst}</td>
				<td>${item.provIssueType}</td>
				<td>${item.provDealRst}</td>
				<td><fmt:formatDate value="${item.provDealDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.dealType}</td>
				<td>${item.fixStatus}</td>
				<td><fmt:formatDate value="${item.dealTime }" pattern="yyyy-MM-dd"/></td>
				<td>${item.provActivity}</td>
			</tr>
			</c:forEach>
	</table>
