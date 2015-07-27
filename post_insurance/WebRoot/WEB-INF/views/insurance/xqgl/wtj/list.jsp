<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/xqgl/issue/list" page="${page }">
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="search_LIKE_organization.orgCode" value="${param.search_LIKE_organization_orgCode }"/>
	<input type="hidden" name="search_LTE_feeDate" value="${param.search_LTE_feeDate }"/>
	<input type="hidden" name="search_GTE_feeDate" value="${param.search_GTE_feeDate }"/>
	<input type="hidden" name="feeStatus" value="${issue.feeStatus }"/>
</dwz:paginationForm>

<form id="xqForm" method="post" action="${contextPath }/xqgl/issue/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>保单号：</label>
					<input type="text" id="xq_policyno" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
				</li>
				<li>
					<label>问题件状态：</label>
					<form:select path="issue.feeStatus" id="xqStatus" class="combox">
						<form:option value=""> -- -- </form:option>
						<form:options items="${xqStatusList }" itemLabel="desc" itemValue="desc"/>
					</form:select>
				</li>
				<li>
					<label>所属机构：</label>
					<input name="search_LIKE_organization.orgCode" id="xq_orgCode" type="hidden" value="${search_LIKE_organization_orgCode }"/>
					<input class="validate[required] required" name="search_LIKE_organization.name" id="xq_orgName" type="text" readonly="readonly" style="width: 140px;" value="${search_LIKE_organization_name }"/>
					<a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="search_LIKE_organization" title="选择机构" width="400">查找带回</a>
				</li>				
			</ul>
			<ul class="searchContent">
				<li>
					<label>交费对应日开始日期：</label>
					<input type="text" name="search_GTE_feeDate" class="date" id="xq_date1" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_feeDate }"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
				</li>
				<li>
					<label>交费对应日结束日期：</label>
					<input type="text" name="search_LTE_feeDate" class="date" id="xq_date2" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_feeDate }"/>
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
			<shiro:hasPermission name="Renewed:view">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="680" href="${contextPath }/xqgl/issue/view/{slt_uid}"><span>查看续期催收件详情</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Renewed:edit">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/xqgl/issue/update/{slt_uid}"><span>市县续期催收登记</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Renewed:provEdit">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/xqgl/issue/provUpdate/{slt_uid}"><span>省分续期催登记</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th width="100" orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">所属机构</th>
				<th width="120" orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">保单号</th>
				<th width="120" orderField=feeDate class="${page.orderField eq 'feeDate' ? page.orderDirection : ''}">交费对应日</th>
				<th>宽限期还有（天）</th>
				<th width="120" orderField=feeStatus class="${page.orderField eq 'feeStatus' ? page.orderDirection : ''}">状态</th>
				<th width="120" orderField=feeFailReason class="${page.orderField eq 'feeFailReason' ? page.orderDirection : ''}">交费失败原因</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.policy.organization.name}</td>
				<td>${item.policy.policyNo}</td>
				<td><fmt:formatDate value="${item.feeDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.lastDateNum }</td>
				<td>${item.feeStatus }</td>
				<td>${item.feeFailReason}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>