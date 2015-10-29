<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=call_fail_dtl.xls");
%>
	<table>
			<tr>
				<th>保单机构</th>
				<th>工单编号</th>
				<th>工单内容</th>
				<th>系统导入</th>
				<th>离犹豫期(天)</th>
				<th>所属保单号</th>
				<th>所属机构</th>
				<th>投保人</th>
				<th>联系电话</th>
				<th>险种名称</th>
				<th>出单网点</th>
				<th>工单状态</th>
				<th>重置电话</th>
				<th>市县回访类型</th>
				<th>市县回访详情</th>
				<th>市县回访经办</th>
				<th>市县回访时间</th>
				<th>二访中心回访类型1</th>
				<th>二访中心回访详情1</th>
				<th>二访中心回访经办1</th>
				<th>二访中心回访时间1</th>
				<th>二访中心回访类型2</th>
				<th>二访中心回访详情2</th>
				<th>二访中心回访经办2</th>
				<th>二访中心回访时间2</th>
				<th>二访中心回访类型3</th>
				<th>二访中心回访详情3</th>
				<th>二访中心回访经办3</th>
				<th>二访中心回访时间3</th>
				<th>二访中心回访类型4</th>
				<th>二访中心回访详情4</th>
				<th>二访中心回访经办4</th>
				<th>二访中心回访时间4</th>
				<shiro:hasPermission name="Callfail:provEdit">
				<th>信函记录</th>
				</shiro:hasPermission>
			</tr>
			<c:forEach var="item" items="${reqs}">
			<tr>
				<td>${fn:replace(item.policy.organization.name,'邮政局中邮保险局','')}</td>
				<td>${item.issueNo}</td>
				<td>${item.issueContent}</td>
				<td><fmt:formatDate value="${item.operateTime }" pattern="yyyy-MM-dd"/></td>
				<td><span style="color:red; height:50%; margin-bottom:-contentheight;"><c:if test="${item.lastDateNum<0 }">0</c:if><c:if test="${item.lastDateNum>=0 }">${item.lastDateNum }</c:if></span></td>
				<td>${item.policy.policyNo}</td>
				<td>${fn:replace(item.organization.name,'邮政局中邮保险局','')}</td>
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
				<td>${item.resetPhone}</td>
				<td>${item.dealType}</td>
				<td>${item.dealDesc}</td>
				<td>${item.dealMan}</td>
				<td><fmt:formatDate value="${item.dealTime }" pattern="yyyy-MM-dd"/></td>
				<td>${item.hqDealType}</td>
				<td>${item.hqDealRst}</td>
				<td>${item.hqDealMan}</td>
				<td><fmt:formatDate value="${item.hqDealDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.hqDealType2}</td>
				<td>${item.hqDealRst2}</td>
				<td>${item.hqDealMan2}</td>
				<td><fmt:formatDate value="${item.hqDealDate2 }" pattern="yyyy-MM-dd"/></td>
				<td>${item.hqDealType3}</td>
				<td>${item.hqDealRst3}</td>
				<td>${item.hqDealMan3}</td>
				<td><fmt:formatDate value="${item.hqDealDate3 }" pattern="yyyy-MM-dd"/></td>
				<td>${item.hqDealType4}</td>
				<td>${item.hqDealRst4}</td>
				<td>${item.hqDealMan4}</td>
				<td><fmt:formatDate value="${item.hqDealDate4 }" pattern="yyyy-MM-dd"/></td>
				<shiro:hasPermission name="Callfail:provEdit">
				<td>${item.hasLetter}</td>
				</shiro:hasPermission>
			</tr>
			</c:forEach>
	</table>
