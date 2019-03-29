<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=Inquire_xls.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>
				<th>保单机构</th>
				<th>工单编号</th>
				<th>工单类型</th>
				<th>工单内容</th>
				<th>系统导入</th>
				<th>所属保单号</th>
				<th>承保日期</th>
				<th>投保人</th>
				<th>联系电话</th>
				<th>险种名称</th>
				<th>出单网点</th>
				<th>工单状态</th>
				<th>所属机构</th>
				<th>市县处理结果</th>
				<th>市县经办人</th>
				<th>市县经办时间</th>
				<th>审核人</th>
				<th>审核日期</th>
				<th>是否银行单子</th>
			</tr>
			<c:forEach var="item" items="${reqs}" varStatus="status">
			<tr>
				<td><c:out value="${status.index+1}"/></td>
				<td>${item.policy.organization.shortName}</td>
				<td>${item.inquireNo}</td>
				<td>${item.inquireSubtype}</td>
				<td>${item.inquireDesc}</td>
				<td><fmt:formatDate value="${item.operateTime }" pattern="yyyy-MM-dd"/></td>
				<td style="vnd.ms-excel.numberformat:@">${item.policy.policyNo}</td>
				<td><fmt:formatDate value="${item.policy.policyDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.policy.holder}</td>
				<td>${item.clientPhone1 eq ""?item.clientPhone2:item.policy.policyDtl.holderMobile}</td>
				<td>${item.policy.prodName}</td>
				<td>
					<c:choose>  
					    <c:when test="${fn:length(item.netName) > 14}">  
					        <c:out value="${fn:substring(item.netName, 14, 30)}" />  
					    </c:when>  
					   <c:otherwise>  
					      <c:out value="${item.policy.bankName}" />  
					    </c:otherwise>  
					</c:choose>
				</td>
				<td>${item.inquireStatus}</td>
				<td>${item.organization.shortName}</td>
				<td>${item.inquireRst}</td>
				<td>${item.dealMan}</td>
				<td>${item.dealTime}</td>
				<td>${item.checker}</td>
				<td><fmt:formatDate value="${item.checkDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.policy.bankCode!=null && item.policy.bankCode.netFlag==2?"是":"否" }</td>
			</tr>
			</c:forEach>
	</table>
