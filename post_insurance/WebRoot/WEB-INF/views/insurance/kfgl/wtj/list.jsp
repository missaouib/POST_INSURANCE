<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/kfgl/issue/list" page="${page }">
	<input type="hidden" name="search_LIKE_issueNo" value="${param.search_LIKE_issueNo }"/>
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="name" value="${name }"/>
	<input type="hidden" name="status" value="${status }"/>
	<input type="hidden" name="search_LTE_shouldDate" value="${param.search_LTE_shouldDate }"/>
	<input type="hidden" name="search_GTE_shouldDate" value="${param.search_GTE_shouldDate }"/>
</dwz:paginationForm>

<form method="post" id="kfForm" action="${contextPath }/kfgl/issue/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						工单号：<input type="text" id="kfPolicyNo" name="search_LIKE_issueNo" style="width: 80px;" value="${param.search_LIKE_issueNo }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="issue.status" id="kfStatus" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:options items="${statusList }" itemLabel="desc" itemValue="desc"/>
						</form:select>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="orgCode" id="kf_orgCode" type="hidden" value="${orgCode }"/>
						<input class="validate[required] required" name="name" id="kf_orgName" type="text" readonly="readonly" style="width: 100px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
				</tr>
				<tr>
					<td>
						保单号：<input type="text" id="kfPolicyNo" style="width: 80px;" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
					</td>
					<td>
						<label>开始日期：</label>
						<input type="text" name="search_GTE_shouldDate" id="kfDate1" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_shouldDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>结束日期：</label>
						<input type="text" name="search_LTE_shouldDate" id="kfDate2" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_shouldDate }"/><a class="inputDateButton" href="javascript:;">选</a>
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
			<shiro:hasPermission name="Wtgd:view">
				<li><a iconClass="magnifier" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/kfgl/issue/view/{slt_uid}"><span>查看详情</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Wtgd:edit">
				<li class="line">line</li>
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/kfgl/issue/update/{slt_uid}"><span>回复</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Wtgd:provEdit">
				<li class="line">line</li>
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/kfgl/issue/CloseStatus" title="确认要结案关闭?"><span>批量结案关闭</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="edit" target="navTab" rel="printIssue" mask="true" width="820" height="520" href="${contextPath }/kfgl/issue/print/{slt_uid}"><span>打印工单</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" target="navTab" rel="printIssueList" mask="true" width="820" height="520" href="${contextPath }/kfgl/issues/print"><span>批打工单</span></a></li>
			<shiro:hasPermission name="Wtgd:view">
				<li class="line">line</li>
				<li><a iconClass="magnifier" href="${contextPath }/kfgl/issue/maxlist?search_LIKE_issueNo=${param.search_LIKE_issueNo }&orgCode=${orgCode }&name=${name }&search_LTE_shouldDate=${param.search_LTE_shouldDate }&search_GTE_shouldDate=${param.search_GTE_shouldDate }&search_LIKE_policy.policyNo=${search_LIKE_policy_policyNo }&encodeStatus=${encodeStatus==null?'null':encodeStatus }" target="dialog" rel="dlg_page1" max="true" title="客服工单列表" width="800" height="480"><span>全屏查看</span></a></li>
				<li class="line">line</li>
				<li><a class="icon" target="_blank" href="${contextPath }/kfgl/toXls?search_LIKE_issueNo=${param.search_LIKE_issueNo }&orgCode=${orgCode }&search_LTE_shouldDate=${param.search_LTE_shouldDate }&search_GTE_shouldDate=${param.search_GTE_shouldDate }&search_LIKE_policy.policyNo=${search_LIKE_policy_policyNo }&status=${encodeStatus==null?'null':encodeStatus }"><span>导出Excel</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="icon" target="dialog" href="${contextPath }/kfgl/help" mask="true" width="530" height="430"><span>功能说明</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=policy.organization.name class="${page.orderField eq 'policy.organization.name' ? page.orderDirection : ''}">所属机构</th>
				<th orderField=issueNo class="${page.orderField eq 'issueNo' ? page.orderDirection : ''}">工单编号</th>
				<th>工单内容</th>
				<th orderField=operateTime class="${page.orderField eq 'operateTime' ? page.orderDirection : ''}">开始处理</th>
				<th>离结案（天）</th>
				<th>客户姓名</th>
				<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">所属保单号</th>
				<th orderField=status class="${page.orderField eq 'status' ? page.orderDirection : ''}">状态</th>
				<th>市县处理</th>
				<th>经办人</th>
				<th>经办日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${fn:replace(item.policy.organization.name,'邮政局中邮保险局','')}</td>
				<td>${item.issueNo}</td>
				<td>${item.issueContent}</td>
				<td>${item.operateTime }</td>
				<td><span style="color:red; height:50%; margin-bottom:-contentheight;">${item.lastDateNum }</span></td>
				<td>${item.policy.holder}</td>
				<td>${item.policy.policyNo}</td>
				<td>${item.status}</td>
				<td>${item.result}</td>
				<td>${item.dealMan}</td>
				<td><fmt:formatDate value="${item.dealTime }" pattern="yyyy-MM-dd"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>