<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=settles.xls");
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
				<th>报案日期</th>
				<th>赔案号</th>
				<th>结案日期</th>
				<th>赔付金额</th>
				<th>账户状态</th>
				<th>录入时间</th>
				<th>出险天数</th>
				<th>追踪要求</th>
				<th>剩余追踪天数</th>
				<th>备注</th>
				<th>日志</th>
			</tr>
			<c:forEach var="item" items="${settles}" varStatus="idx">
			<tr>
				<td>${idx.index+1 }</td>
				<td>${item.organization.shortName}</td>
				<td>${item.caseMan}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.policy.policyNo}</td>
				<td>${item.policy.prodName}</td>
				<td>${item.policy.policyFee}</td>
				<td><fmt:formatDate value="${item.policy.policyDate}" pattern="yyyy-MM-dd"/></td>
				<td>${item.reporter}</td>
				<td>${item.reporterPhone}</td>
				<td><fmt:formatDate value="${item.caseDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.caseType}</td>
				<td><fmt:formatDate value="${item.reporteDate }" pattern="yyyy-MM-dd"/></td>
				<td><div style="color: <c:choose><c:when test="${item.lessFeedBack<0}">red</c:when><c:when test="${item.lessFeedBack<2}">orange</c:when><c:otherwise>"black"</c:otherwise></c:choose>;vertical-align:middle;font-weight:normal;">${item.claimsNo}</div></td>
				<td><fmt:formatDate value="${item.caseEndDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.claimsCloseReport.giveFee}</td>
				<td>${item.caseStatus}</td>
				<td><fmt:formatDate value="${item.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${item.caseLong }</td>
				<td>
				<c:choose>
					<c:when test="${not empty item.needFeedBack and item.needFeedBack eq '待反馈'}">
					<div style="color: red;vertical-align:middle;font-weight:normal;">待反馈</div>
					</c:when>
					<c:when test="${not empty item.needFeedBack and item.needFeedBack eq '已反馈'}">
					<div style="color: blue;vertical-align:middle;font-weight:normal;">已反馈</div>
					</c:when>
					<c:otherwise>
						&nbsp;
					</c:otherwise>
				</c:choose>
				</td>
				<td>${item.lessFeedBack }</td>
				<td>${item.remark}</td>
				<td><c:forEach var="dtl" items="${item.settlementLogs}">
				${dtl.info}（${dtl.user.realname}/<fmt:formatDate value='${dtl.dealDate}' pattern='yyyy-MM-dd'/>）
				</c:forEach>
				</td>
			</tr>
			</c:forEach>
	</table>
