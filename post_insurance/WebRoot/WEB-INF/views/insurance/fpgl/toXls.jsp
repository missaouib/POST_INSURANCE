<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=InvoiceReq.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>
                <th>投保人</th>
                <th>险种</th>
                <th>保单金额</th>
                <th>登记金额</th>
                <th>发票备注</th>
                <th>期间</th>
                <th>保单机构</th>
                <th>电子发票</th>
                <th>保单号</th>
                <th>保单状态</th>
                <td>出单日期</td>
                <td>续费日期</td>
                <td>申请日期</td>
                <th>接收邮箱</th>
                <th>EMS</th>
                <th>申请接收人</th>
                <th>接收地址</th>
                <th>联系电话</th>
                <th>状态</th>
        </tr>
        <c:forEach var="item" items="${reqs}" varStatus="status">
        <tr>
                <td><c:out value="${status.index+1}"/></td>
                <td>${item.policy.holder}</td>
                <td>${item.policy.prodName}</td>
                <td>${item.policy.policyFee}</td>
                <td>${item.fee}</td>
                <td>保单号：${item.policy.policyNo}  发票金额：<fmt:formatNumber value="${item.policy.totalFee}" pattern="#,###.##" />元，${item.reqFlag}，被保险人:${item.policy.insured}</td>
                <td>${item.policy.feeFrequency}</td>
                <td>${item.policy.organization.shortName}</td>
                <td>${item.isElectiveBill}</td>
                <td style="vnd.ms-excel.numberformat:@">${item.policy.policyNo}</td>
                <td>${item.policy.status}</td>
                <td><fmt:formatDate value="${item.policy.policyDate }" pattern="yyyy-MM-dd"/></td>
                <td><fmt:formatDate value="${item.feeDate }" pattern="yyyy-MM-dd"/></td>
                <td><fmt:formatDate value="${item.reqDate }" pattern="yyyy-MM-dd"/></td>
                <td>${item.billAddr}</td>
                <td style="vnd.ms-excel.numberformat:@">${item.billNo}</td>
                <td>${item.reqMan}</td>
                <td>${item.reqAddr}</td>
                <td>${item.phone}</td>
                <td>
				<c:choose>
					<c:when test="${item.status eq 'NewStatus'}">
						<span style="color:red; height:50%; margin-bottom:-contentheight;">待处理</span>
					</c:when>
					<c:when test="${item.status eq 'DealStatus'}">
						已寄出
					</c:when>
					<c:when test="${item.status eq 'ReceiveStatus'}">
						已接收
					</c:when>
					<c:otherwise>
						已关闭
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			</c:forEach>
	</table>
