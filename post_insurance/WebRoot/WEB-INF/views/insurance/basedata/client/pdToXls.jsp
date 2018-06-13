<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=policy_data.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>
				<th>保单号码</th>
				<th>投保单号码</th>
				<th>投保人姓名</th>
				<th>投保人电话号码</th>
				<th>投保人手机号码</th>
				<th>投保人证件类型</th>
				<th>投保人证件号码</th>
				<th>被保险人姓名</th>
				<th>被保人身份证</th>
				<th>被保人电话号码</th>
				<th>险种编码</th>
				<th>险种名称</th>
				<th>保费</th>
				<th>基本保额</th>
				<th>缴费频率</th>
				<th>缴费期数</th>
				<th>签单日期</th>
				<th>生效日期</th>
				<th>投保状态</th>
				<th>网点编码</th>
				<th>缴费方式</th>
				<th>卡折号</th>
				<th>是否犹撤</th>
				<th>是否非实时</th>
			</tr>
			<c:forEach var="item" items="${policies}" varStatus="idx">
			<tr target="slt_uid" rel="${item.id}">
				<td>${idx.index+1 }</td>
				<td style="vnd.ms-excel.numberformat:@">${item.policyNo}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.formNo}</td>
				<td>${item.holder}</td>
				<td>${item.holderPhone}</td>
				<td>${item.holderMobile}</td>
				<td>${item.holderCardType}</td>
				<shiro:hasPermission name="Callfail:provEdit">
				<td style="vnd.ms-excel.numberformat:@">${item.holderCardNum}</td>
				</shiro:hasPermission>
				<td>${item.insured}</td>
				<td>${item.insuredCardNum}</td>
				<td>${item.insuredPhone}</td>
				<td>${item.prodCode}</td>
				<td>${item.prodName}</td>
				<td>${item.policyFee}</td>
				<td>${item.insuredAmount}</td>
				<td>${item.feeFrequency}</td>
				<td>${item.perm}</td>
				<td><fmt:formatDate value="${item.policyDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.plicyValidDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.status}</td>
				<td>${item.bankCode}</td>
				<td>${item.feeType}</td>
				<td>${item.bankAccount}</td>
				<td>${item.csFlag eq 1?"Y":""}</td>
				<td>${item.uwFlag}</td>
			</tr>
			</c:forEach>
	</table>
