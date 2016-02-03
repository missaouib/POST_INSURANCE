<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=call_fail_dtl.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>
				<th>保单机构</th>
				<th>工单编号</th>
				<th>工单内容</th>
				<th>工单状态</th>
				<th>待处理时间</th>
				<th>离犹豫期(天)</th>
				<th>所属保单号</th>
				<th>所属机构</th>
				<th>出单日期</th>
				<th>投保人</th>
				<shiro:hasPermission name="Callfail:provEdit">
				<th>证件号码</th>
				</shiro:hasPermission>
				<th>联系电话</th>
				<th>联系地址</th>
				<th>险种名称</th>
				<th>出单网点</th>
				<th>重置电话</th>
				<th>不成功件生成时间</th>
				<th>市县回访类型</th>
				<th>市县回访详情</th>
				<th>市县回访经办</th>
				<th>市县回访时间</th>
				<th>二访日期</th>
				<th>二访类型</th>
				<th>二访详情</th>
				<th>二访人</th>
				<th>可再访</th>
				<th>可再访备注</th>
				<th>重置时间</th>
				<shiro:hasPermission name="Callfail:provEdit">
				<th>信函记录</th>
				</shiro:hasPermission>
			</tr>
			<c:forEach var="item" items="${reqs}" varStatus="status">
			<tr>
				<td><c:out value="${status.index+1}"/></td>
				<td>${fn:replace(item.policy.organization.name,'邮政局中邮保险局','')}</td>
				<td>${item.issueNo}</td>
				<td>${item.issueContent}</td>
				<td>${item.status}</td>
				<td><fmt:formatDate value="${item.readyDate }" pattern="yyyy-MM-dd"/></td>
				<td><span style="color:red; height:50%; margin-bottom:-contentheight;"><c:if test="${item.lastDateNum<0 }">0</c:if><c:if test="${item.lastDateNum>=0 }">${item.lastDateNum }</c:if></span></td>
				<td style="vnd.ms-excel.numberformat:@">${item.policy.policyNo}</td>
				<td>${fn:replace(item.organization.name,'邮政局中邮保险局','')}</td>
				<td>${item.policy.policyDate}</td>
				<td>${item.policy.holder}</td>
				<shiro:hasPermission name="Callfail:provEdit">
				<td style="vnd.ms-excel.numberformat:@">${item.idCard}</td>
				</shiro:hasPermission>
				<td>${item.holderMobile eq ""?item.holderPhone:item.holderMobile}</td>
				<td>${item.addr}</td>
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
				
				<td>${item.resetPhone}</td>
				<td><fmt:formatDate value="${item.issueDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.dealType}</td>
				<td>${item.dealDesc}</td>
				<td>${item.dealMan}</td>
				<td><fmt:formatDate value="${item.dealTime }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.hqDealDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.hqDealType}</td>
				<td>${item.hqDealRst}</td>
				<td>${item.hqDealMan}</td>
				<td>${item.canCallAgain}</td>
				<td>${item.canCallAgainRemark}</td>
				<td><fmt:formatDate value="${item.resetDate }" pattern="yyyy-MM-dd"/></td>
				<shiro:hasPermission name="Callfail:provEdit">
				<td>${item.hasLetter}</td>
				</shiro:hasPermission>
			</tr>
			</c:forEach>
	</table>
