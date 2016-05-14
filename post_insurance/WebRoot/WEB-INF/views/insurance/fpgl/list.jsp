<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/fpgl/list" page="${page }">
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="search_LTE_reqDate" value="${param.search_LTE_reqDate }"/>
	<input type="hidden" name="search_GTE_reqDate" value="${param.search_GTE_reqDate }"/>
	<input type="hidden" name="status" value="${status }"/>
</dwz:paginationForm>

<form method="post" id="fpForm" action="${contextPath }/fpgl/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>保单号：</label>
					<input type="text" id="fqPolicyNo" name="search_LIKE_policy.policyNo" style="width: 100px;" value="${param.search_LIKE_policy_policyNo }"/>
				</li>
				<li>
					<label>状态：</label>
					<form:select path="req.status" id="fpStatus" class="combox">
						<form:option value=""> -- -- </form:option>
						<form:options items="${fpStatusList }" itemLabel="desc"/>
					</form:select>
				</li>
			</ul>
			<ul class="searchContent">
				<li>
					<label>申请开始日期：</label>
					<input type="text" name="search_GTE_reqDate" id="fqReqDate1" style="width: 80px;" class="date validate[required] required" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_reqDate }"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
				</li>
				<li>
					<label>申请结束日期：</label>
					<input type="text" name="search_LTE_reqDate" id="fqReqDate2" style="width: 80px;" class="date validate[required] required" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_reqDate }"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
				</li>
			</ul>
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
			<shiro:hasPermission name="InvoiceReq:save">
				<li><a iconClass="user_add" target="dialog" rel="lookup2organization_add" mask="true" width="530" height="430" href="${contextPath }/fpgl/create"><span>添加申请</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="InvoiceReq:edit">
				<li class="line">line</li>
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="530" height="430" href="${contextPath }/fpgl/update/{slt_uid}"><span>编辑</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="InvoiceReq:delete">
				<li class="line">line</li>
				<li><a iconClass="user_delete" target="selectedTodo" rel="ids" href="${contextPath }/fpgl/delete" title="确认要删除?"><span>删除申请</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="InvoiceReq:deal">
				<li class="line">line</li>
				<li><a iconClass="user_go" target="ajaxTodo" href="${contextPath }/fpgl/ReceiveStatus/{slt_uid}" title="确认更新状态?"><span>已接收</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="InvoiceReq:reset">
				<li class="line">line</li>
				<li><a iconClass="user_go" href="${contextPath}/fpgl/updateDealStatus/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>已寄出</span></a></li>
				<li><a iconClass="user_go" target="ajaxTodo" href="${contextPath }/fpgl/CloseStatus/{slt_uid}" title="确认关闭?"><span>关闭</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="InvoiceReq:view">
				<li class="line">line</li>
				<li><a class="icon" target="_blank" href="${contextPath }/fpgl/toXls?search_LIKE_policy.policyNo=${search_LIKE_policy_policyNo }&search_LTE_reqDate=${param.search_LTE_reqDate}&search_GTE_reqDate=${param.search_GTE_reqDate}&status=${status}"><span>导出Excel</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="icon" target="dialog" href="${contextPath }/fpgl/help" mask="true" width="530" height="430"><span>功能说明</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">保单号</th>
				<th>投保人</th>
				<th orderField=policy.organization.name class="${page.orderField eq 'policy.organization.name' ? page.orderDirection : ''}">保单机构</th>
				<th orderField=flag class="${page.orderField eq 'flag' ? page.orderDirection : ''}">发票标记</th>
				<th orderField=isElectiveBill class="${page.orderField eq 'isElectiveBill' ? page.orderDirection : ''}">电子发票</th>
				<th>发票金额</th>
				<th>发票缴费日期</th>
				<th orderField=reqDate class="${page.orderField eq 'reqDate' ? page.orderDirection : ''}">申请日期</th>
				<th orderField=status class="${page.orderField eq 'status' ? page.orderDirection : ''}">状态</th>
				<th>EMS</th>
				<th>申请接收人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${reqs}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.policy.policyNo}</td>
				<td>${item.policy.holder}</td>
				<td>${item.policy.organization.name}</td>
				<td>${item.flag}</td>
				<td>${item.isElectiveBill}</td>
				<td>${item.fee}</td>
				<td><fmt:formatDate value="${item.feeDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.reqDate }" pattern="yyyy-MM-dd"/></td>
				<td>
				<c:choose>
					<c:when test="${item.status eq 'NewStatus'}">
						<span style="color:red; height:50%; margin-bottom:-contentheight;">待处理</span>
					</c:when>
					<c:when test="${item.status eq 'DealStatus'}">
						已寄出
					</c:when>
					<c:when test="${item.status eq 'ReceiveStatus'}">
						已接收
					</c:when>
					<c:otherwise>
						已关闭
					</c:otherwise>
				</c:choose>
				</td>
				<td>${item.billNo}</td>
				<td>${item.reqMan}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>