<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/qygl/underwrite/planList" page="${page }">
	<input type="hidden" name="search_LIKE_formNo" value="${search_LIKE_formNo }"/>
	<input type="hidden" name="search_LIKE_holder" value="${search_LIKE_holder }"/>
	<input type="hidden" name="search_LIKE_policyNo" value="${search_LIKE_policyNo }"/>
</dwz:paginationForm>

<form method="post" id="hfForm" action="${contextPath }/qygl/underwrite/planList" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
			<tr>
				<td>投保单号：
					<input type="text" id="uwFormNo" style="width: 100px;" name="search_LIKE_formNo" value="${param.search_LIKE_formNo }"/>
				</td>
				<td>投保人：
					<input type="text" id="uwHolder" style="width: 100px;" name="search_LIKE_holder" value="${param.search_LIKE_holder }"/>
				</td>
				<td>保单号：
					<input type="text" id="uwPlanNo" style="width: 100px;" name="search_LIKE_policyNo" value="${param.search_LIKE_policyNo }"/>
				</td>
			</tr>
			</table>
			<div class="subBar">
				<ul>						
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="UnderWrite:view">
				<li><a iconClass="magnifier" target="dialog" rel="underwrite_edit" mask="true" width="850" height="440" href="${contextPath }/qygl/underwrite/view/{slt_uid}"><span>查看</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="UnderWrite:edit">
			<li class="line">line</li>
				<li><a class="delete" href="${contextPath}/qygl/underwrite/signDateUpdate/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>回销登记</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="edit" target="dialog" rel="underwrite_edit" mask="true" width="450" height="340" href="${contextPath }/qygl/underwrite/plan/{slt_uid}"><span>跟进设置</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="135" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">市县机构</th>
				<th>投保人</th>
				<th orderField=formNo class="${page.orderField eq 'formNo' ? page.orderDirection : ''}">投保单号</th>
				<th orderField=policyNo class="${page.orderField eq 'policyNo' ? page.orderDirection : ''}">保单号</th>
				<th>合同寄出</th>
				<th>快递单号</th>
				<th orderField=planDate class="${page.orderField eq 'planDate' ? page.orderDirection : ''}">跟进日期</th>
				<th>备注</th>
				<th>地市接收</th>
				<th>县区接收</th>
				<th orderField=prd.prdName class="${page.orderField eq 'prd.prdName' ? page.orderDirection : ''}">产品</th>
				<th orderField=ybtDate class="${page.orderField eq 'ybtDate' ? page.orderDirection : ''}">邮保通录入</th>
				<th orderField=sysDate class="${page.orderField eq 'sysDate' ? page.orderDirection : ''}">核心录入</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${underwrites}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>
				<c:choose>  
				    <c:when test="${fn:contains(item.organization.name, '直属')}">  
				        <c:out value="${fn:replace(item.organization.name,'邮政局直属中邮保险局','直属')}" />  
				    </c:when>
				    <c:when test="${fn:contains(item.organization.name, '营业本部')}">  
				        <c:out value="营业本部" />  
				    </c:when>
				   <c:otherwise>
				      <c:out value="${fn:replace(item.organization.name,'邮政局中邮保险局','')}" />  
				    </c:otherwise>  
				</c:choose>
				</td>
				<td title="${item.holder}">${fn:substring(item.holder, 0, 4)}</td>
				<td>${item.formNo}</td>
				<td title="${item.policyNo}">${item.policyNo}</td>
				<td><fmt:formatDate value="${item.provSendDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.provEmsNo}</td>
				<td><div style="color: red;vertical-align:middle;font-weight:bold;"><fmt:formatDate value="${item.planDate }" pattern="yyyy-MM-dd"/></div></td>
				<td>${item.remark}</td>
				<td><fmt:formatDate value="${item.cityReceiveDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.areaReceiveDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.prd.prdName}</td>
				<td><fmt:formatDate value="${item.ybtDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.sysDate }" pattern="yyyy-MM-dd"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>