<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=policyScanDtl.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>
				<th>市县机构</th>
				<th>网点</th>
				<th>投保单号</th>
				<th>保单号</th>
				<th>签单日期</th>
				<th>投保人</th>
				<th>产品</th>
				<th>保费</th>
				<th>是否有差错</th>
				<th>是否扫描</th>
			</tr>
			<c:forEach var="item" items="${ybtPolicys}" varStatus="idx">
			<tr>
				<td>${idx.index+1}</td>
				<td>${item.orgName}</td>
				<td>${item.bankName}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.formNo}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.policyNo}</td>
				<td><fmt:formatDate value="${item.policyDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.holder}</td>
				<td>${item.prodName}</td>
				<td>${item.policyFee}</td>
				<td>
				<c:choose>  
					    <c:when test="${item.hasErr}">  
					        <div style="color: red;vertical-align:middle;font-weight:normal;">${item.fixStatus eq 'NewStatus'?"差错待处理":item.fixStatus eq 'IngStatus'?"差错待审核":"" }</div>
					    </c:when>
					    <c:otherwise>  
					      &nbsp;
					    </c:otherwise>  
					</c:choose>
				</td>
				<td>
				<c:choose>  
					    <c:when test="${item.hasScan}">  
					      <div style="color: green;vertical-align:middle;font-weight:normal;">已扫描</div>
					    </c:when>
					    <c:otherwise>  
					     <div style="color: red;vertical-align:middle;font-weight:normal;">未扫描</div>
					    </c:otherwise>  
					</c:choose>
				</td>
			</tr>
			</c:forEach>
	</table>
