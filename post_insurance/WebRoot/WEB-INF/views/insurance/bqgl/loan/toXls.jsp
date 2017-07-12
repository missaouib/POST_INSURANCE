<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=CS_REPORT_LIST.xls");
%>
<table border="1" cellspacing="1" cellpadding="0">
	<thead>
		<tr>
			<th>序号</th>
			<th>逾期标记</th>
			<th>管理机构</th>
			<th orderField=csNo class="${page.orderField eq 'csNo' ? page.orderDirection : ''}">保单号码</th>
			<th>投保人姓名</th>
			<th>投保人性别</th>
			<th>险种名称</th>
			<th>出单网点</th>
			<th>借款日期</th>
			<th>借款金额</th>
			<th>约定还款日期</th>
			<th>保单状态</th>
			<th>联系方式</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="item" items="${issues}" varStatus="index">
		<tr>
			<td>${index+1 }</td>
			<td>
              <c:choose>  
			    <c:when test="${item.checkDate>1}">  
			        <div style="color: red;vertical-align:middle;font-weight:bold;">逾期状态</div>
			    </c:when>
			    <c:when test="${item.checkDate>-30}">  
			        <div style="color: yellow;vertical-align:middle;font-weight:bold;">预警状态</div>
			    </c:when>
			   <c:otherwise>  
			      <div style="color: green;vertical-align:middle;font-weight:bold;">正常状态</div>
			    </c:otherwise>  
			</c:choose>
			</td>
			<td>${item.organName}</td>
			<td>${item.policy.policyNo}</td>
			<td>${item.holder}</td>
			<td>${item.holderSexy}</td>
			<td>${item.prodName}</td>
			<td>${fn:replace(item.bankName, "中国邮政储蓄银行股份有限公司", "")}</td>
			<td><fmt:formatDate value="${item.loanDate }" pattern="yyyy-MM-dd"/></td>
			<td>${item.loanFee}</td>
			<td><fmt:formatDate value="${item.shouldDate }" pattern="yyyy-MM-dd"/></td>
			<td>${item.status}</td>
			<td>${item.phone}</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
