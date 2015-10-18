<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=Issue_xls.xls");
%>
	<table>
			<tr>
				<th>保单机构</th>
				<th>工单编号</th>
				<th>工单内容</th>
				<th>系统导入</th>
				<th>离结案还有（天）</th>
				<th>所属保单号</th>
				<th>投保人</th>
				<th>联系电话</th>
				<th>险种名称</th>
				<th>出单网点</th>
				<th>工单状态</th>
				<th>所属机构</th>
				<th>市县处理结果</th>
				<th>市县经办人</th>
				<th>市县经办时间</th>
			</tr>
			<c:forEach var="item" items="${reqs}">
			<tr>
				<td>${fn:replace(item.policy.organization.name,'邮政局中邮保险局','')}</td>
				<td>${item.issueNo}</td>
				<td>${item.issueContent}</td>
				<td><fmt:formatDate value="${item.operateTime }" pattern="yyyy-MM-dd"/></td>
				<td><span style="color:red; height:50%; margin-bottom:-contentheight;"><c:if test="${item.lastDateNum<0 }">0</c:if><c:if test="${item.lastDateNum>=0 }">${item.lastDateNum }</c:if></span></td>
				<td>${item.policy.policyNo}</td>
				<td>${item.policy.holder}</td>
				<td>${item.holderMobile eq ""?item.holderPhone:item.holderMobile}</td>
				<td>${item.policy.prodName}</td>
				<td>
					<c:choose>  
					    <c:when test="${fn:length(item.bankName) > 14}">  
					        <c:out value="${fn:substring(item.bankName, 14, 30)}" />  
					    </c:when>  
					   <c:otherwise>  
					      <c:out value="${item.bankName}" />  
					    </c:otherwise>  
					</c:choose>
				</td>
				<td>${item.status}</td>
				<td>${fn:replace(item.organization.name,'邮政局中邮保险局','')}</td>
				<td>${item.result}</td>
				<td>${item.dealMan}</td>
				<td>${item.dealTime}</td>
			</tr>
			</c:forEach>
	</table>