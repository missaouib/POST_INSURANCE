<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/hfgl/issue/list" page="${page }">
	<input type="hidden" name="search_LIKE_issueNo" value="${param.search_LIKE_issueNo }"/>
	<input type="hidden" name="search_LIKE_organization.orgCode" value="${param.search_LIKE_organization_orgCode }"/>
	<input type="hidden" name="search_LTE_shouldDate" value="${param.search_LTE_shouldDate }"/>
	<input type="hidden" name="search_GTE_shouldDate" value="${param.search_GTE_shouldDate }"/>
	<input type="hidden" name="status" value="${param.status }"/>
</dwz:paginationForm>

<form method="post" id="hfForm" action="${contextPath }/hfgl/issue/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>工单编号：</label>
					<input type="text" id="hfPolicyNo" name="search_LIKE_issueNo" value="${param.search_LIKE_issueNo }"/>
				</li>
				<li>
					<label>工单状态：</label>
					<form:select path="issue.status" id="hfStatus" class="combox">
						<form:option value=""> -- -- </form:option>
						<form:options items="${hfStatusList }" itemLabel="desc" itemValue="desc"/>
					</form:select>
				</li>
				<li>
					<label>所属机构：</label>
					<input name="search_LIKE_organization.orgCode" id="hf_orgCode" type="hidden" value="${search_LIKE_organization_orgCode }"/>
					<input class="validate[required] required" name="search_LIKE_organization.name" id="hf_orgName" type="text" readonly="readonly" style="width: 140px;" value="${search_LIKE_organization_name }"/>
					<a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="search_LIKE_organization" title="选择机构" width="400">查找带回</a>
				</li>				
			</ul>
			<ul class="searchContent">
				<li>
					<label>待处理开始日期：</label>
					<input type="text" name="search_GTE_shouldDate" id="hfDate1" class="date validate[required] required" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_shouldDate }"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
				</li>
				<li>
					<label>待处理结束日期：</label>
					<input type="text" name="search_LTE_shouldDate" id="hfDate2" class="date validate[required] required" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_shouldDate }"/>
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
			<shiro:hasPermission name="Callfail:view">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="680" href="${contextPath }/hfgl/issue/view/{slt_uid}"><span>查看回访不成功件</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Callfail:edit">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/hfgl/issue/update/{slt_uid}"><span>回复回访不成功件</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Callfail:provEdit">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/hfgl/issue/provUpdate/{slt_uid}"><span>省分回访登记</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Callfail:11185Edit">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/hfgl/issue/hqUpdate/{slt_uid}"><span>11185回访登记</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th width="100" orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">所属机构</th>
				<th width="100" orderField=issueNo class="${page.orderField eq 'issueNo' ? page.orderDirection : ''}">工单编号</th>
				<th width="200">工单内容</th>
				<th width="100" orderField=shouldDate class="${page.orderField eq 'shouldDate' ? page.orderDirection : ''}">待处理时间</th>
				<th width="120" orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">所属保单号</th>
				<th width="120" orderField=status class="${page.orderField eq 'status' ? page.orderDirection : ''}">工单状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.organization.name}</td>
				<td>${item.issueNo}</td>
				<td>${item.issueContent}</td>
				<td><fmt:formatDate value="${item.shouldDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.policy.policyNo}</td>
				<td>${item.status}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>