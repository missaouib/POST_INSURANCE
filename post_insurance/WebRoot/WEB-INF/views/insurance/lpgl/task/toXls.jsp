<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=settles_task.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>	
				<th>机构名称</th>
				<th>出险人</th>
				<th>保单号</th>
				<th>险种名称</th>
				<th>保费</th>
				<th>承保日期</th>
				<th>出险日期</th>
				<th>理赔类型</th>
				<th>调查起始时间</th>
				<th>调查止期时间</th>
				<th>调查要求</th>
				<th>调查人</th>
				<th>调查地点</th>
				<th>查勘费</th>
			</tr>
			<c:forEach var="item" items="${tasks}" varStatus="idx">
			<tr>
				<td>${idx.index+1 }</td>
				<td title="${item.organization.name}">
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
				<td>${item.insured}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.settlementDtl.policyNo}</td>
				<td>${item.settlementDtl.prodName}</td>
				<td>${item.settlementDtl.policyFee}</td>
				<td><fmt:formatDate value="${item.settlementDtl.policyDate}" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.settlementDtl.caseDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.settlementDtl.caseType}</td>
				<td><fmt:formatDate value="${item.checkStartDate}" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.checkEndDate}" pattern="yyyy-MM-dd"/></td>
				<td>${item.checkReq}</td>
				<td>${item.checker}</td>
				<td>${item.checkerAddr}</td>
				<td>${item.checkFee}</td>
			</tr>
			</c:forEach>
	</table>
