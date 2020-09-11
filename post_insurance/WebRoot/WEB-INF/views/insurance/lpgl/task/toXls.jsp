<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=settles_task.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>	
				<th>赔案号</th>
				<th>报案日期</th>
				<th>出险日期</th>
				<th>理赔类型</th>
				<th>出险天数</th>
				<th>机构名称</th>
				<th>出险人</th>
				<th>保单号</th>
				<th>险种名称</th>
				<th>保费</th>
				<th>承保日期</th>
				<th>调查起始时间</th>
				<th>调查止期时间</th>
				<th>调查要求</th>
				<th>调查人</th>
				<th>调查地点</th>
				<th>查勘费</th>
				<th>备注</th>
				<th>附件</th>
				<th>日志</th>
			</tr>
			<c:forEach var="item" items="${tasks}" varStatus="idx">
			<tr>
				<td>${idx.index+1 }</td>
				<td>${item.settlement.claimsNo}</td>
				<td>${item.reporteDate}</td>
				<td>${item.caseDate}</td>
				<td>${item.caseType}</td>
				<td>${item.taskLong}</td>
				<td>${item.organization.shortName}</td>
				<td>${item.insured}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.policy.policyNo}</td>
				<td>${item.policy.prodName}</td>
				<td>${item.policy.policyFee}</td>
				<td><fmt:formatDate value="${item.policy.policyDate}" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.checkStartDate}" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.checkEndDate}" pattern="yyyy-MM-dd"/></td>
				<td>${item.checkReq}</td>
				<td>${item.checker}</td>
				<td>${item.checkerAddr}</td>
				<td>${item.checkFee}</td>
				<td>${item.remark}</td>
				<td>${item.attrLink}</td>
				<td><c:forEach var="dtl" items="${item.settleTaskLogs}">
				${dtl.info}（${dtl.user.realname}/<fmt:formatDate value='${dtl.dealDate}' pattern='yyyy-MM-dd'/>）Chr(10)
				</c:forEach>
				</td>
			</tr>
			</c:forEach>
	</table>
