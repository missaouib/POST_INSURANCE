<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=QY_Write_LIST.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
		<thead>
			<tr>
				<th>批次号</th>
				<th>所属机构</th>
				<th>保单号</th>
				<th>投保单号</th>
				<th>投保人</th>
				<th>电话</th>
				<th>手机</th>
				<th>Email电子邮箱</th>
				<th>地址</th>
				<th>被保险人</th>
				<th>关系</th>
				<th>承保日期</th>
				<th>问题件状态</th>
				<th>资料缺失</th>
				<th>关键信息</th>
				<th>重要信息</th>
				<th>其他信息</th>
				<th>网点名称</th>
				<th>结果类型</th>
				<th>处理详情</th>
				<th>经办人</th>
				<th>经办日期</th>
				<th>回复日期</th>
				<th>抽检人</th>
				<th>档案下发</th>
				<th>客真标记</th>
			</tr>
		</thead>
		<c:forEach var="item" items="${issues}">
			<tr>
				<td>${item.checkBatch}</td>
				<td>${item.policy.organization.shortName}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.policy.policyNo}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.policy.formNo}</td>
				<td>${item.policy.holder}</td>
				<td>${item.policy.policyDtl.holderPhone}</td>
				<td>${item.policy.policyDtl.holderMobile}</td>
				<td>${item.policy.policyDtl.holderEmail}</td>
				<td>${item.policy.policyDtl.holderAddr}</td>
				<td>${item.policy.insured}</td>
				<td>${item.policy.policyDtl.relation}</td>
				<td>${item.policy.policyDate}</td>
				<td>
				<c:choose>
					<c:when test="${item.fixStatus eq 'NewStatus'}">
						<span style="color:red; height:50%; margin-bottom:-contentheight;">待处理</span>
					</c:when>
					<c:when test="${item.fixStatus eq 'FollowStatus'}">
						跟进中
					</c:when>
					<c:when test="${item.fixStatus eq 'IngStatus'}">
						待审核
					</c:when>
					<c:when test="${item.fixStatus eq 'CTStatus'}">
						已退保
					</c:when>
					<c:when test="${item.fixStatus eq 'FailStatus'}">
						无法整改
					</c:when>
					<c:when test="${item.fixStatus eq 'CloseStatus'}">
						已整改
					</c:when>
					<c:otherwise>
						${item.fixStatus}
					</c:otherwise>
				</c:choose>
				</td>
				<td>${item.docMiss == "null"?"":item.docMiss}</td>
				<td>${item.keyInfo=="null"?"":item.keyInfo}</td>
				<td>${item.importanceInfo=="null"?"":item.importanceInfo}</td>
				<td>${item.elseInfo=="null"?"":item.elseInfo}</td>
				<td>
				<c:choose>  
					    <c:when test="${!empty item.netName && fn:length(item.netName) > 14}">  
					        <c:out value="${fn:substring(item.netName, 14, 30)}" />  
					    </c:when> 
					    <c:when test="${!empty item.netName && fn:length(item.netName) > 0}">  
					        <c:out value="${item.netName}" />  
					    </c:when> 
					   <c:otherwise>  
					      <c:out value="${item.policy.bankName}" />  
					    </c:otherwise>  
					</c:choose>
				</td>
				<td>${item.fixType=="null"?"":item.fixType}</td>
				<td>${item.fixDesc=="null"?"":item.fixDesc}</td>
				<td>${item.dealMan=="null"?"":item.dealMan}</td>
				<td><fmt:formatDate value="${item.dealTime }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.replyTime }" pattern="yyyy-MM-dd"/></td>
				<td>${item.checker}</td>
				<td>
				<c:choose>  
					    <c:when test="${item.payMethod eq '期交' or item.payMethod eq '趸交'}">  
					        &nbsp;
					    </c:when>
					    <c:otherwise>  
					      <c:out value="${item.payMethod}" />  
					    </c:otherwise>  
					</c:choose>
					</td>
				<td>${item.isTruth}</td>
			</tr>
			</c:forEach>
	</table>
