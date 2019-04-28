<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=groupsettles.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>	
				<th>机构名称</th>
				<th>出险人</th>
				<th>保单号</th>
				<th>险种名称</th>
				<th>保费</th>
				<th>承保日期</th>
				<th>报案人</th>
				<th>报案人电话</th>
				<th>出险日期</th>
				<th>理赔类型</th>
				<th>首次接触赔案时间</th>
				<th>接案人</th>
				<th>首次接收理赔材料日期</th>
				<th>签收人</th>
				<th>接收齐全理赔材料日期</th>
				<th>签收人</th>
				<th>发起调查日期</th>
				<th>发起人</th>
				<th>调查完成日期</th>
				<th>调查人</th>
				<th>报案日期</th>
				<th>立案日期</th>
				<th>赔案号</th>
				<th>结案日期</th>
				<th>赔付金额</th>
				<th>账户状态</th>
				<th>录入时间</th>
				<th>备注</th>
			</tr>
			<c:forEach var="item" items="${settles}" varStatus="idx">
			<tr>
				<td>${idx.index+1 }</td>
				<td>${item.organization.shortName}</td>
				<td>${item.insured}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.settlementDtls.policyNo}</td>
				<td>${item.settlementDtls.prodName}</td>
				<td>${item.settlementDtls.policyFee}</td>
				<td><fmt:formatDate value="${item.settlementDtls.policyDate}" pattern="yyyy-MM-dd"/></td>
				<td>${item.reporter}</td>
				<td>${item.reporterPhone}</td>
				<td><fmt:formatDate value="${item.caseDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.caseType}</td>
				<td><fmt:formatDate value="${item.settlementDtls.firstCaseTime}" pattern="yyyy-MM-dd"/></td>
				<td>${item.settlementDtls.caseMan}</td>
				<td><fmt:formatDate value="${item.settlementDtls.firstFileDate}" pattern="yyyy-MM-dd"/></td>
				<td>${item.settlementDtls.firstSignMan}</td>
				<td><fmt:formatDate value="${item.settlementDtls.allFileDate}" pattern="yyyy-MM-dd"/></td>
				<td>${item.settlementDtls.allSignMan}</td>
				<td><fmt:formatDate value="${item.settlementDtls.checkDate}" pattern="yyyy-MM-dd"/></td>
				<td>${item.settlementDtls.checker}</td>
				<td><fmt:formatDate value="${item.settlementDtls.checkDoneDate}" pattern="yyyy-MM-dd"/></td>
				<td>${item.settlementDtls.checkDoneMan}</td>
				<td><fmt:formatDate value="${item.reporteDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.recordDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.settlementDtls.claimsNo}</td>
				<td><fmt:formatDate value="${item.closeDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.payFee}</td>
				<td>${item.caseStatus}</td>
				<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${item.remark}</td>
			</tr>
			</c:forEach>
	</table>
