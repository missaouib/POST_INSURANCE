<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/bqgl/issue/list" page="${page }">
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${param.search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="name" value="${name }"/>
	<input type="hidden" name="search_LTE_csDate" value="${param.search_LTE_csDate }"/>
	<input type="hidden" name="search_GTE_csDate" value="${param.search_GTE_csDate }"/>
	<input type="hidden" name="status" value="${status }"/>
</dwz:paginationForm>

<form method="post" id="bqForm" action="${contextPath }/bqgl/issue/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						保单号：<input type="text" style="width: 100px;" id="bqPolicyNo" name="search_LIKE_policy.policyNo" value="${param.search_LIKE_policy_policyNo }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="issue.status" class="combox" id="bqStatus">
							<form:option value=""> -- -- </form:option>
							<form:options items="${baStatusList }" itemLabel="desc"/>
						</form:select>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="orgCode" id="bq_orgCode" type="hidden" value="${orgCode }"/>
						<input class="validate[required] required" name="name" id="bq_orgName" type="text" readonly="readonly" style="width: 100px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
				</tr>
				<tr>
					<td>
						<label>录入开始日期：</label>
						<input type="text" id="bqCsDate1" name="search_GTE_csDate" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_csDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>录入结束日期：</label>
						<input type="text" id="bqCsDate2" name="search_LTE_csDate" class="date validate[required] required" style="width: 80px;"dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_csDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						&nbsp;
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
			<shiro:hasPermission name="Cservice:save">
				<li><a class="add" target="dialog" rel="lookup2organization_add" mask="true" width="530" height="330" href="${contextPath }/bqgl/issue/create"><span>登记保全复核问题</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Cservice:edit">
				<li class="line">line</li>
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="530" height="330" href="${contextPath }/bqgl/issue/update/{slt_uid}"><span>编辑</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Cservice:delete">
				<li class="line">line</li>
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/bqgl/issue/delete" title="确认要删除?"><span>删除</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Cservice:deal">
				<li class="line">line</li>
				<li><a class="delete" target="ajaxTodo" href="${contextPath }/bqgl/issue/DealStatus/{slt_uid}" title="确认更新状态?"><span>已处理</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Cservice:reset">
				<li class="line">line</li>
				<li><a class="delete" target="ajaxTodo" href="${contextPath }/bqgl/issue/CloseStatus/{slt_uid}" title="确认关闭?"><span>关闭</span></a></li>
				<li class="line">line</li>
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/bqgl/issue/CloseStatus" title="确认批量关闭?"><span>批量关闭</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="icon" onclick="javascript:urlCheckOverDate(365,$('#bqCsDate1').val(),$('#bqCsDate2').val(),'${contextPath }/bqgl/toXls?search_LIKE_policy.policyNo=${param.search_LIKE_policy_policyNo }&orgCode=${orgCode }&search_LTE_csDate=${param.search_LTE_csDate }&search_GTE_csDate=${param.search_GTE_csDate }&status=${param.status }');"><span>导出Excel</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" target="dialog" href="${contextPath }/bqgl/help" mask="true" width="530" height="430"><span>功能说明</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">保单号</th>
				<th>保单所属机构</th>
				<th orderField=dealNum class="${page.orderField eq 'dealNum' ? page.orderDirection : ''}">保全受理号</th>
				<th>保全项目</th>
				<th orderField=info class="${page.orderField eq 'info' ? page.orderDirection : ''}">复核修改问题</th>
				<th>复核修改问题描述</th>
				<th orderField=csDate class="${page.orderField eq 'csDate' ? page.orderDirection : ''}">问题产生日期</th>
				<th orderField=status class="${page.orderField eq 'status' ? page.orderDirection : ''}">状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.policy.policyNo}</td>
				<td>${item.policy.organization.shortName}</td>
				<td>${item.dealNum}</td>
				<td>${item.conservationCode}</td>
				<td>${item.info}</td>
				<td>${item.remark}</td>
				<td>${item.csDate}</td>
				<td>
				<c:choose>
					<c:when test="${item.status eq 'NewStatus'}">
						 <span style="color:red; height:50%; margin-bottom:-contentheight;">待处理</span>
					</c:when>
					<c:when test="${item.status eq 'DealStatus'}">
						已处理
					</c:when>
					<c:when test="${item.status eq 'CancelStatus'}">
						已撤销
					</c:when>
					<c:otherwise>
						已关闭
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>