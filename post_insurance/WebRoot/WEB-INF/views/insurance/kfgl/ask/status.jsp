<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<div class="pageContent">
	<table class="table" layoutH="60" width="100%">
		<thead>
			<tr>
				<th>操作</th>
				<th>操作人</th>
				<th>操作日期</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>新建</td>
				<td>${inquire.operateId}</td>
				<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr>
				<td>回复</td>
				<td>${inquire.dealMan}</td>
				<td><fmt:formatDate value="${item.dealTime}" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr>
				<td>重新打开</td>
				<td>${inquire.reopenUser.realname}</td>
				<td><fmt:formatDate value="${item.reopenDate}" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr>
				<td>审核</td>
				<td>${inquire.checker}</td>
				<td><fmt:formatDate value="${item.checkDate}" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr>
				<td>结案</td>
				<td>${inquire.closeUser}</td>
				<td><fmt:formatDate value="${item.finishDate}" pattern="yyyy-MM-dd"/></td>
			</tr>
		</tbody>
	</table>
	<!-- 分页 -->
</div>