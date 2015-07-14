<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/qygl/issue/record/list" page="${page }">
	<input type="hidden" name="search_LIKE_issueNo" value="${param.search_LIKE_issueNo }"/>
	<input type="hidden" name="search_LIKE_organization.orgCode" value="${param.search_LIKE_organization_orgCode }"/>
	<input type="hidden" name="search_LTE_policy.policyDate" value="${search_LTE_policy_policyDate }"/>
	<input type="hidden" name="search_GTE_policy.policyDate" value="${search_GTE_policy_policyDate }"/>
	<input type="hidden" name="status" value="${param.status }"/>
</dwz:paginationForm>

<form method="post" id="qyRecordForm" action="${contextPath }/qygl/issue/record/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>工单编号：</label>
					<input type="text" id="qyRecordPolicyNo" name="search_LIKE_issueNo" value="${param.search_LIKE_issueNo }"/>
				</li>
				<li>
					<label>工单状态：</label>
					<form:select path="issue.fixStatus" id="qyRecordStatus" class="combox">
						<form:option value=""> -- -- </form:option>
						<form:options items="${qyRecordStatusList }" itemLabel="desc"/>
					</form:select>
				</li>
				<li>
					<label>所属机构：</label>
					<input name="search_LIKE_organization.orgCode" id="qy_r_orgCode" type="hidden" value="${search_LIKE_organization_orgCode }"/>
					<input class="validate[required] required" name="search_LIKE_organization.name" id="qy_r_orgName" type="text" readonly="readonly" style="width: 140px;" value="${search_LIKE_organization_name }"/>
					<a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="search_LIKE_organization" title="选择机构" width="400">查找带回</a>
				</li>				
			</ul>
			<ul class="searchContent">
				<li>
					<label>承保开始日期：</label>
					<input type="text" name="search_GTE_policy.policyDate" id="qy_r_date1" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="${search_GTE_policy_policyDate }"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
				</li>
				<li>
					<label>承保结束日期：</label>
					<input type="text" name="search_LTE_policy.policyDate" id="qy_r_date2" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="${search_LTE_policy_policyDate }"/>
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
			<shiro:hasPermission name="CheckRecord:view">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="680" href="${contextPath }/qygl/issue/record/view/{slt_uid}"><span>查看新契约不合格件</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="CheckRecord:edit">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/qygl/issue/record/update/{slt_uid}"><span>回复新契约不合格件</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th width="100" orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">所属机构</th>
				<th width="120" orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">所属保单号</th>
				<th width="120" orderField=policy.policyDate class="${page.orderField eq 'policy.policyDate' ? page.orderDirection : ''}">承保日期</th>
				<th width="120" orderField=fixStatus class="${page.orderField eq 'fixStatus' ? page.orderDirection : ''}">问题件状态</th>
				<th width="120" orderField=docMiss class="${page.orderField eq 'docMiss' ? page.orderDirection : ''}">资料缺失</th>
				<th width="120" orderField=keyInfo class="${page.orderField eq 'keyInfo' ? page.orderDirection : ''}">关键信息</th>
				<th width="120" orderField=importanceInfo class="${page.orderField eq 'importanceInfo' ? page.orderDirection : ''}">重要信息</th>
				<th width="120" orderField=netName class="${page.orderField eq 'netName' ? page.orderDirection : ''}">网点名称</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.policy.organization.name}</td>
				<td>${item.policy.policyNo}</td>
				<td>${item.policy.policyDate}</td>
				<td>
				<c:choose>
					<c:when test="${item.fixStatus eq 'NewStatus'}">
						待处理
					</c:when>
					<c:when test="${item.fixStatus eq 'DealStatus'}">
						已处理
					</c:when>
					<c:when test="${item.fixStatus eq 'ReopenStatus'}">
						重打开
					</c:when>
					<c:otherwise>
						已关闭
					</c:otherwise>
				</c:choose>
				</td>
				<td>${item.docMiss == "null"?"":item.docMiss}</td>
				<td>${item.keyInfo=="null"?"":item.keyInfo}</td>
				<td>${item.importanceInfo=="null"?"":item.importanceInfo}</td>
				<td>${item.netName=="null"?"":item.netName}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>