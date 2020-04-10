<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=policy_list.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>
				<th>投保单号</th>
				<th orderField=policyNo class="${page.orderField eq 'policyNo' ? page.orderDirection : ''}">保单号</th>
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">机构</th>
				<th>投保人</th>
				<th>投保人性别</th>
				<th>投保人年龄</th>
				<th>被保人</th>
				<th>关系</th>
				<th>险种名称</th>
				<th>保费</th>
				<th>合计保费</th>
				<th>交费方式</th>
				<th>交费期间</th>
				<th>期间类型</th>
				<th>保险期间</th>
				<th>承保日期</th>
				<th>承保时间</th>
				<th>客户签收日期</th>
				<th>回销日期</th>
				<shiro:hasPermission name="Client:provEdit">
                <th>联系电话</th>
                <th>手机号码</th>
                <th>证件类型</th>
                <th>证件号码</th>
                </shiro:hasPermission>
				<th>状态</th>
				<th>退保日期</th>
				<th>是否犹撤</th>
				<th>邮保通编码</th>
				<th>核心编码</th>
				<th>网点</th>
				<th>员工单</th>
				<th>银行单</th>
			</tr>
			<c:forEach var="item" items="${policies}" varStatus="idx">
			<tr>
				<td>${idx.index+1 }</td>
				<td style="vnd.ms-excel.numberformat:@">${item.formNo}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.policyNo}</td>
				<td>${item.organization.shortName}</td>
				<td>${item.holder}</td>
				<td>${item.policyDtl.holderSexy}</td>
				<td>${item.policyDtl.holderAge}</td>
				<td>${item.insured}</td>
				<td>${item.policyDtl.relation}</td>
				<td>${item.prodName}</td>
				<td>${item.policyFee}</td>
				<td>${item.totalFee}</td>
				<td>${item.feeFrequency}</td>
				<td>${item.perm}</td>
				<td>${item.policyDtl.durationType}</td>
				<td>${item.policyDtl.duration}</td>
				<td><fmt:formatDate value="${item.policyDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.policyDtl.policyTime }" pattern="HH:mm:ss"/></td>
				<td><fmt:formatDate value="${item.clientReceiveDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.billBackDate }" pattern="yyyy-MM-dd"/></td>
				<shiro:hasPermission name="Client:provEdit">
                <td>${item.policyDtl==null?"":item.policyDtl.holderPhone}</td>
                <td>${item.policyDtl==null?"":item.policyDtl.holderMobile}</td>
                <td>${item.policyDtl==null?"":item.policyDtl.holderCardType}</td>
                <td>******${fn:substring(item.policyDtl.holderCardNum,6,17)}</td>
                </shiro:hasPermission>
				<td>${item.status}</td>
				<td>${item.csDate != null?item.csDate:""}</td>
				<td>${item.csFlag != null && item.csFlag==1?"Y":"N"}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.bankCode.ybtCode}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.bankCode.selBankCode}</td>
				<td>${item.bankCode.fullName}</td>
				<td>${item.isStaff}</td>
				<td>${item.bankCode!=null && item.bankCode.netFlag==2?"是":"否" }</td>
			</tr>
			</c:forEach>
	</table>
