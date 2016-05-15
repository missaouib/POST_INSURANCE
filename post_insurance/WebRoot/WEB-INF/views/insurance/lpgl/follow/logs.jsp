<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/lpgl/log/${settle_id}" page="${page }">
</dwz:paginationForm>

<form method="post" action="${contextPath }/lpgl/log/${settle_id}" onsubmit="return navTabSearch(this)">
</form>

<div class="pageContent">
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th>序号</th>	
				<th>操作人</th>
				<th>操作日期</th>
				<th>详情</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${settleLog}" varStatus="idx">
			<tr target="slt_uid" rel="${item.id}">
				<td>${idx.index+1 }</td>
				<td>${item.user.username}</td>
				<td><fmt:formatDate value="${item.dealDate}" pattern="yyyy-MM-dd"/></td>
				<td title="${item.info}">${fn:substring(item.info, 0, 37)}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>