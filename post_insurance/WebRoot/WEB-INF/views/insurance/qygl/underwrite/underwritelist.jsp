<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/qygl/underwritelist" page="${page }">
</dwz:paginationForm>

<form method="post" action="${contextPath }/qygl/underwritelist" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
	</div>
</form>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="UnderWrite:view">
				<li><a iconClass="user_edit" target="dialog" rel="underwrite_edit" mask="true" width="850" height="580" href="${contextPath }/qygl/underwrite/view/{slt_uid}"><span>查看人核件</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="UnderWrite:edit">
			<li class="line">line</li>
				<li><a iconClass="user_edit" target="dialog" rel="underwrite_edit" mask="true" width="850" height="580" href="${contextPath }/qygl/underwrite/update/{slt_uid}"><span>更新人核件</span></a></li>
				<li><a iconClass="user_go" href="${contextPath}/qygl/underwrite/signDateUpdate/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>回销登记</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="UnderWrite:provEdit">
			<li class="line">line</li>
				<li><a iconClass="user_edit" target="dialog" rel="underwrite_edit" mask="true" width="850" height="620" href="${contextPath }/qygl/underwrite/create"><span>新建人核件</span></a></li>
				<li><a iconClass="user_go" href="${contextPath}/qygl/underwrite/provSend/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>省分寄出</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="UnderWrite:cityEdit">
			<li class="line">line</li>
				<li><a iconClass="user_go" href="${contextPath}/qygl/underwrite/cityRec/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>地市接收</span></a></li>
				<li><a iconClass="user_go" href="${contextPath}/qygl/underwrite/citySend/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>地市寄出</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="UnderWrite:areaEdit">
			<li class="line">line</li>
				<li><a iconClass="user_go" href="${contextPath}/qygl/underwrite/areaRec/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>县区接收</span></a></li>
				<li><a iconClass="user_go" href="${contextPath}/qygl/underwrite/areaSend/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>县区寄出</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="icon" target="dialog" href="${contextPath }/qygl/underwrite/help" mask="true" width="530" height="430"><span>功能说明</span></a></li>
		</ul>
	</div>
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">市县机构</th>
				<th orderField=formNo class="${page.orderField eq 'formNo' ? page.orderDirection : ''}">投保单号</th>
				<th orderField=policyNo class="${page.orderField eq 'policyNo' ? page.orderDirection : ''}">保单号</th>
				<th>投保人</th>
				<th orderField=prd.prdName class="${page.orderField eq 'prd.prdName' ? page.orderDirection : ''}">产品</th>
				<th orderField=ytbDate class="${page.orderField eq 'ytbDate' ? page.orderDirection : ''}">邮保通录入时间</th>
				<th>核心录入时间</th>
				<th>复核时间</th>
				<th>核保日期</th>
				<th>签单日期</th>
				<th>合同签收日期</th>
				<th>回执录入日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${underwriteList}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.organization.name}</td>
				<td>${item.formNo}</td>
				<td>${item.policyNo}</td>
				<td>${item.holder}</td>
				<td>${item.prd.prdName}</td>
				<td><fmt:formatDate value="${item.ybtDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.sysDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.checkDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.underwriteDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.signDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.clientReceiveDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.signInputDate }" pattern="yyyy-MM-dd"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>