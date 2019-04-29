<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=CSExpire_LIST.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>			
				<th>地市</th>
				<th>保单号</th>
				<th>险种名称</th>
				<th>销售渠道</th>
				<th>保障期限</th>
				<th>保险止期</th>
				<th>投保人姓名</th>
				<th>投保人出生日期</th>
				<th>投保人身份证号</th>
				<th>投保人证件类型</th>
				<th>投保人年龄</th>
				<th>投保人期满年龄</th>
				<th>被保险人姓名</th>
				<th>被保人出生日期</th>
				<th>被保人身份证号</th>
				<th>被保人证件类型</th>
				<th>被保人年龄</th>
				<th>被保人期满年龄</th>
				<th>已交保费</th>
				<th>投保人手机</th>
				<th>投保人电话</th>
				<th>满期金额</th>
				<th>满期金及分红</th>
				<th>满期收益</th>
				<th>投被保人关系</th>
				<th>问题件</th>
				<th>公众号信息匹配</th>
				<th>满期客户账户匹配结果</th>
				<th>付费金额排查</th>
				<th>投保时年龄达60岁</th>
				<th>满期时被保人已满18周岁</th>
				<th>投被年龄差达40岁</th>
				<th>问题件分析</th>
				<th>员工单分析</th>
				<th>评级</th>
				<th>状态</th>
				<th>网点名称</th>
			</tr>
			<c:forEach var="item" items="${expires}" varStatus="status">
			<tr>
				<td><c:out value="${status.index+1}"/></td>
				<td>${item.policy.organization.shortName}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.policy.policyNo}</td>
				<td>${item.policy.prodName}</td>
				<td>${item.saleChannel}</td>
				<td>${item.duration}</td>
				<td><fmt:formatDate value='${item.policyEndDate}' pattern='yyyy-MM-dd'/></td>
				<td>${item.policy.holder}</td>
				<td><fmt:formatDate value='${item.holderBirthday}' pattern='yyyy-MM-dd'/></td>
				<td style="vnd.ms-excel.numberformat:@">${item.holderCardNum}</td>
				<td>${item.holderCardType}</td>
				<td>${item.holderYear}</td>
				<td>${item.holderExpireYear}</td>
				<td>${item.policy.insured}</td>
				<td><fmt:formatDate value='${item.insuredBirthday}' pattern='yyyy-MM-dd'/></td>
				<td style="vnd.ms-excel.numberformat:@">${item.insuredCardNum}</td>
				<td>${item.insuredCardType}</td>
				<td>${item.insuredYear}</td>
				<td>${item.insuredExpireYear}</td>
				<td>${item.policyMoney}</td>
				<td>${item.holderMobile}</td>
				<td>${item.holderPhone}</td>
				<td>${item.expireMoney}</td>
				<td>${item.expireProfit}</td>
				<td>${item.expireRate}</td>
				<td>${item.relation}</td>
				<td>${item.issueFlag}</td>
				<td>${item.subFlag}</td>
				<td>${item.balMatch}</td>
				<td>${item.payLevel}</td>
				<td>${item.holderAgeLevel}</td>
				<td>${item.adultLevel}</td>
				<td>${item.ageDiffLevel}</td>
				<td>${item.issueLevel}</td>
				<td>${item.staffLevel}</td>
				<td>${item.finalLevel}</td>
				<td>
				<c:choose>
					<c:when test="${item.status eq 'AGStatus'}">
						 已给付关闭
					</c:when>
					<c:when test="${item.status eq 'CTStatus'}">
						已退保
					</c:when>
					<c:when test="${item.status eq 'WarnStatus'}">
						异常件
					</c:when>
					<c:otherwise>
						<span style="color:red; height:50%; margin-bottom:-contentheight;">待处理</span>
					</c:otherwise>
				</c:choose>
				</td>
				<td>${item.policy.bankName}</td>
			</tr>
			</c:forEach>
	</table>
