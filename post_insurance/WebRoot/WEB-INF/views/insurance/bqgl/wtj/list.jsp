<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/bqgl/issue/list" page="${page }">
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${param.search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="search_LIKE_organization.orgCode" value="${param.search_LIKE_organization_orgCode }"/>
	<input type="hidden" name="status" value="${param.status }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/bqgl/issue/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>保单号：</label>
					<input type="text" name="search_LIKE_policy.policyNo" value="${param.search_LIKE_policy_policyNo }"/>
				</li>
				<li>
					<label>工单状态：</label>
					<form:select path="issue.status" class="combox">
						<form:option value=""> -- -- </form:option>
						<form:options items="${statusList }" itemLabel="desc" itemValue="desc"/>
					</form:select>
				</li>
				<li>
					<label>所属机构：</label>
					<input name="search_LIKE_organization.orgCode" id="search_LIKE_organization.orgCode" type="hidden" value="${search_LIKE_organization_orgCode }"/>
					<input class="validate[required] required" name="search_LIKE_organization.name" type="text" readonly="readonly" style="width: 140px;" value="${search_LIKE_organization_name }"/>
					<a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="search_LIKE_organization" title="选择机构" width="400">查找带回</a>
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
			<shiro:hasPermission name="Cservice:save">
				<li><a iconClass="user_add" target="dialog" rel="lookup2organization_add" mask="true" width="530" height="330" href="${contextPath }/bqgl/issue/create"><span>添加保全复核问题</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Cservice:edit">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="530" height="330" href="${contextPath }/bqgl/issue/update/{slt_uid}"><span>编辑保全复核问题</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Cservice:delete">
				<li><a iconClass="user_delete" target="selectedTodo" rel="ids" href="${contextPath }/bqgl/issue/delete" title="确认要删除?"><span>删除保全复核问题</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Cservice:reset">
				<li class="line">line</li>
				<li><a iconClass="user_go" target="ajaxTodo" href="${contextPath }/bqgl/issue/reset/status/{slt_uid}" title="确认更新状态?"><span>关闭</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th width="100" orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">保单号</th>
				<th width="100" orderField=dealNum class="${page.orderField eq 'dealNum' ? page.orderDirection : ''}">保全受理号</th>
				<th width="200">保全项目</th>
				<th width="120" orderField=info class="${page.orderField eq 'info' ? page.orderDirection : ''}">复核修改问题</th>
				<th width="120" orderField=status class="${page.orderField eq 'status' ? page.orderDirection : ''}">状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.policy.policyNo}</td>
				<td>${item.dealNum}</td>
				<td>${item.type}</td>
				<td>${item.info}</td>
				<td>${item.status}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>