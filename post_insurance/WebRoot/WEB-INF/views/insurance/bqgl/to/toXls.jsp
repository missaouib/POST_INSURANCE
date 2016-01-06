<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=TO_FEE_FAL_LIST.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>管理机构</th>
				<th>账户名</th>
				<th>账号</th>
				<th>金额</th>
				<th>状态描述</th>
				<th>回盘日期</th>
				<th>关联业务号码</th>
			</tr>
			<c:forEach var="item" items="${paylists}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.organization.name}</td>
				<td>${item.accountName}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.account}</td>
				<td>${item.money}</td>
				<td>${item.failDesc}</td>
				<td><fmt:formatDate value="${item.backDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.relNo}</td>
				<td>
					<c:choose>
					<c:when test="${item.status eq 'NewStatus'}">
						待关闭
					</c:when>
					<c:otherwise>
						已关闭
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			</c:forEach>
	</table>
