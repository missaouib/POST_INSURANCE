<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=policy_list.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>
				<th>投保单号</th>
				<th orderField=policyNo class="${page.orderField eq 'policyNo' ? page.orderDirection : ''}">保单号</th>
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">机构</th>
				<th>投保人</th>
				<th>险种名称</th>
				<th>保费</th>
				<th>合计保费</th>
				<th>交费方式</th>
				<th>交费期间</th>
				<th>承保日期</th>
				<th>回销日期</th>
				<th>状态</th>
				<th>是否犹撤</th>
				<th>网点编码</th>
				<th>网点</th>
				<th>员工单</th>
				<th>银行单</th>
			</tr>
			<c:forEach var="item" items="${policies}" varStatus="idx">
			<tr>
				<td>${idx.index+1 }</td>
				<td style="vnd.ms-excel.numberformat:@">${item.formNo}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.policyNo}</td>
				<td>
				<c:choose>  
				    <c:when test="${fn:contains(item.organization.name, '直属')}">  
				        <c:out value="${fn:replace(item.organization.name,'邮政局直属中邮保险局','直属')}" />  
				    </c:when>
				    <c:when test="${fn:contains(item.organization.name, '仲恺')}">  
				        ${fn:substring(item.organization.name, 0, 7)}
				    </c:when>
				   <c:otherwise>
				      <c:out value="${fn:replace(item.organization.name,'邮政局中邮保险局','')}" />  
				    </c:otherwise>  
				</c:choose>
				</td>
				<td>${item.holder}</td>
				<td>${item.prodName}</td>
				<td>${item.policyFee}</td>
				<td>${item.totalFee}</td>
				<td>${item.feeFrequency}</td>
				<td>${item.perm}</td>
				<td><fmt:formatDate value="${item.policyDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.billBackDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.status}</td>
				<td>${item.csFlag != null && item.csFlag==1?"Y":"N"}</td>
				<td>${item.bankCode.name}</td>
				<td>${item.bankName}</td>
				<td>${item.isStaff}</td>
				<td>${item.bankCode!=null && item.bankCode.netFlag==2?"是":"否" }</td>
			</tr>
			</c:forEach>
	</table>
