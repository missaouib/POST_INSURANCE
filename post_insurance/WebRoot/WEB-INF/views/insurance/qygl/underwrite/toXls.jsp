<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=underwrite.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>
				<th>市县机构</th>
				<th>投保单号</th>
				<th>保单号</th>
				<th>投保人</th>
				<th>投保年龄</th>
				<th>被保人</th>
				<th>关系</th>
				<th>转核原因</th>
				<th>产品</th>
				<th>保费</th>
				<th>交付方式</th>
				<th>交付期数</th>
				<th>客户填单日期</th>
				<th>非实时录入日期</th>
				<th>核心录入日期</th>
				<th>复核时间</th>
				<th>体检下发日期</th>
				<th>体检回销日期</th>
				<th>契约调查下发日期</th>
				<th>契调回销日期</th>
				<th>核保完成日期</th>
				<th>签单日期</th>
				<th>保单寄出日期</th>
				<th>客户签收日期</th>
				<th>回执回销日期</th>
				<th>快递单号</th>
				<th>跟进日期</th>
				<th>备注</th>
				<th>市局收到合同日</th>
				<th>市局寄出合同日</th>
				<th>县局收到合同日</th>
				<th>县局寄出合同日</th>
				<th>状态</th>
				<th>网点</th>
				<shiro:hasPermission name="UnderWrite:provEdit">
				<th>客户电话</th>
				<th>客户手机</th>
				</shiro:hasPermission>
				<th>打印-签单-1天</th>
				<th>签收-邮寄</th>
				<th>签收-核心录入</th>
				<th>15日送达情况</th>
				<th>签收-签单</th>
				<th>剔除后签收-签单</th>
				<th>剔除后签收-录入</th>
				<th>15日送达</th>
				<th>5日作业完成</th>
				<th>5日回销完成</th>
			</tr>
			<c:forEach var="item" items="${reqs}" varStatus="status">
			<tr>
				<td><c:out value="${status.index+1}"/></td>
				<td>${item.organization.shortName}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.formNo}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.policyNo}</td>
				<td>${item.holder}</td>
				<td>${item.holderAge}</td>
				<td>${item.insured}</td>
				<td>${item.relation}</td>
				<td>${item.underwriteReason}</td>
				<td>${item.prd.prdName}</td>
				<td>${item.policyFee}</td>
				<td>${item.feeType}</td>
				<td>${item.perm}</td>
				<td><fmt:formatDate value="${item.formWriteDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.ybtDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.sysDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.checkDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.bodyCheckDate1 }</td>
				<td>${item.bodyCheckDate2 }</td>
				<td>${item.dealCheckDate1 }</td>
				<td>${item.dealCheckDate1 }</td>
				<td><fmt:formatDate value="${item.hbEndDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.signDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.provSendDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.clientReceiveDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.billBackDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.provEmsNo }</td>
				<td><fmt:formatDate value="${item.planDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.remark }</td>
				<td><fmt:formatDate value="${item.cityReceiveDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.citySendDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.areaReceiveDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.areaSendDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.status }</td>
				<td>${item.netName }</td>
				<shiro:hasPermission name="UnderWrite:provEdit">
				<td>${item.policyDtl.holderPhone }</td>
				<td>${item.policyDtl.holderMobile }</td>
				</shiro:hasPermission>
				<td>${item.sendsign }</td>
				<td>${item.clientprov }</td>
				<td>${item.clientsys }</td>
				<td>${item.clientybt }</td>
				<td>${item.clientsign }</td>
				<td>${item.clientsignprovsign }</td>
				<td>${item.clientsysprovsign }</td>
				<td>${item.allday15 }</td>
				<td>${item.dealday5 }</td>
				<td>${item.backday5 }</td>
			</tr>
			</c:forEach>
	</table>
