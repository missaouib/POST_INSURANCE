<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=RENEWED_FOLLOW.xls");
%>
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th>反馈状态</th>
				<th>反馈日期</th>
				<th>职业</th>
				<th>工作单位</th>
				<th>个人收入</th>
				<th>家庭收入</th>
				<th>购买目的</th>
				<th>网点资产情况</th>
				<th>机构</th>
				<th>投保人</th>
				<th>被保人</th>
				<th>险种名称</th>
				<th>保费</th>
				<th>交费期间</th>
				<th>保险期间</th>
				<th>承保日期</th>
				<th>保单状态</th>
				<th>退保日期</th>
				<th>网点</th>
				<th>员工单</th>
				<th>银行单</th>
				<th>证件号码</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${policies}">
			<tr>
				<td>
				<c:choose>
					<c:when test="${item.status eq 'NewStatus'}">
						未反馈
					</c:when>
					<c:when test="${item.status eq 'FeedStatus'}">
						已反馈
					</c:when>
					<c:otherwise>
						已终止
					</c:otherwise>
				</c:choose>
				</td>
				<td><fmt:formatDate value="${item.followDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.job}</td>
				<td>${item.company}</td>
				<td>${item.income}</td>
				<td>${item.homeIncome}</td>
				<td>${item.objectives}</td>
				<td>${item.bankInfo}</td>
				<td>${item.policy.policyNo}</td>
				<td>${item.policy.organization.shortName}</td>
				<td>${item.policy.holder}</td>
				<td>${item.policy.insured}</td>
				<td>${item.policy.prodName}</td>
				<td>${item.policy.policyFee}</td>
				<td>${item.policy.perm}</td>
				<td>${item.policy.policyDtl.duration}</td>
				<td><fmt:formatDate value="${item.policy.policyDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.policy.status}</td>
				<td>${item.policy.csDate != null?item.policy.csDate:""}</td>
				<td>${item.policy.bankName}</td>
				<td>${item.policy.staffFlag}</td>
				<td>${item.policy.bankCode!=null && item.policy.bankCode.netFlag==2?"是":"否" }</td>
				<td>******${fn:substring(item.policy.policyDtl.holderCardNum,6,17)}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
