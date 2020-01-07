<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=InvoiceReq.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>单据ID</th>
				<th>单据编号</th>
				<th>客户编码</th>
                <th>客户名称</th>
                <th>购方税号</th>
                <th>购方地址电话</th>
                <th>购方银行帐号</th>
                <th>联系方式</th>
                <th>开票类型</th>
                <th>发票备注</th>
                <th>单据行号</th>
                <th>单据行性质</th>
                <th>商品代码</th>
                <th>商品名称</th>
                <th>商品规格</th>
                <th>计量单位</th>
                <th>商品数量</th>
                <th>含税单价</th>
                <th>含税金额</th>
                <th>不含税单价</th>
                <th>不含税金额</th>
                <th>税率</th>
                <th>税额</th>
                <th>税收编码</th>
                <th>续期/首期</th>
                <th>保单号</th>
                <th>保单状态</th>
                <th>登记金额</th>
                <th>保单机构</th>
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
        <c:forEach var="item" items="${reqs}">
        <tr>
                <td>${item.billId}</td>
                <td>${item.billId}</td>
                <td></td>
                <td>${item.policy.holder}</td>
                <td></td>
                <td></td>
                <td></td>
                <td>${item.policy.policyDtl.holderPhone}</td>
                <td style="vnd.ms-excel.numberformat:@">${item.isElectiveBill?"026":"007"}</td>
                <td>保单号：${item.policy.policyNo}  发票金额：<fmt:formatNumber value="${item.policy.totalFee}" pattern="#,###.##" />元,承保日期:<fmt:formatDate value="${item.policy.policyDate }" pattern="yyyy-MM-dd"/>,${item.reqFlag},被保险人:${item.policy.insured},缴(续)费日期:<fmt:formatDate value="${item.feeDate }" pattern="yyyy-MM-dd"/></td>
                <td>1</td>
                <td>0</td>
                <td></td>
                <td>${item.policy.prodName}</td>
                <td></td>
                <td></td>
                <td>1</td>
                <td><fmt:formatNumber value="${item.policy.totalFee}" pattern="####.##" /></td>
                <td><fmt:formatNumber value="${item.policy.totalFee}" pattern="####.##" /></td>
                <td><fmt:formatNumber value="${item.policy.totalFee}" pattern="####.##" /></td>
                <td><fmt:formatNumber value="${item.policy.totalFee}" pattern="####.##" /></td>
                <td>0.00</td>
                <td>0.00</td>
                <td style="vnd.ms-excel.numberformat:@">3060301010000000000</td>
                <td>${item.reqFlag}</td>
                <td style="vnd.ms-excel.numberformat:@">${item.policy.policyNo}</td>
                <td>${item.policy.status}</td>
                <td>${item.fee}</td>
                <td>${item.policy.organization.shortName}</td>
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
