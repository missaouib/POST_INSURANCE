<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/qygl/uwlist2weixin" page="${page }" onsubmit="return dwzSearch(this, 'dialog');">
</dwz:paginationForm>

<form method="post" id="hfForm" action="${contextPath }/qygl/uwlist2weixin" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="pageHeader">
	</div>
</form>

<div class="pageContent">
	<table class="table" layoutH="80" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">市县机构</th>
				<th orderField=formNo class="${page.orderField eq 'formNo' ? page.orderDirection : ''}">投保单号</th>
				<th>投保人</th>
				<th orderField=prd.prdName class="${page.orderField eq 'prd.prdName' ? page.orderDirection : ''}">产品</th>
				<th orderField=ybtDate class="${page.orderField eq 'ybtDate' ? page.orderDirection : ''}">邮保通录入</th>
				<th orderField=sysDate class="${page.orderField eq 'sysDate' ? page.orderDirection : ''}">核心录入</th>
				<th>合同寄出</th>
				<th>快递单号</th>
				<th>市局寄出</th>
				<th>县区寄出</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${underwriteList}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.organization.shortName}</td>
				<td>${item.formNo}</td>
				<td title="${item.holder}">${fn:substring(item.holder, 0, 4)}</td>
				<td>${item.prd.prdName}</td>
				<td><fmt:formatDate value="${item.ybtDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.sysDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.provSendDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.provEmsNo}</td>
				<td><fmt:formatDate value="${item.citySendDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.areaSendDate }" pattern="yyyy-MM-dd"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }" targetType="dialog"/>
</div>