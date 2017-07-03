<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/xqgl/stay/list" page="${page }">
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="search_LIKE_policy.holder" value="${search_LIKE_policy_holder }"/>
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="name" value="${name }"/>
	<input type="hidden" name="search_LTE_csDate" value="${param.search_LTE_csDate }"/>
	<input type="hidden" name="search_GTE_csDate" value="${param.search_GTE_csDate }"/>
	<input type="hidden" name="status" value="${param.status }"/>
</dwz:paginationForm>

<form method="post" id="bqForm" action="${contextPath }/xqgl/stay/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						保单号：<input type="text" style="width: 100px;" id="bqPolicyNo" name="search_LIKE_policyNo" value="${param.search_LIKE_policyNo }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="stay.status" class="combox" id="bqStatus">
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
						客户姓名：<input type="text" style="width: 100px;" id="bqPolicyNo" name="search_LIKE_policy.holder" value="${search_LIKE_policy_client }"/>
					</td>
					<td>
						<label>退保日期：</label>
						<input type="text" id="csDate1" name="search_GTE_csDate" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_csDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>退保日期：</label>
						<input type="text" id="csDate2" name="search_LTE_csDate" class="date validate[required] required" style="width: 80px;"dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_csDate }"/><a class="inputDateButton" href="javascript:;">选</a>
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
			<shiro:hasPermission name="RenewedStay:save">
				<li><a class="add" target="dialog" rel="lookup2organization_add" mask="true" width="530" height="450" href="${contextPath }/xqgl/stay/create"><span>登记退保挽留</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="RenewedStay:prov">
				<li class="line">line</li>
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="530" height="530" href="${contextPath }/xqgl/stay/provupdate/{slt_uid}"><span>省分更新</span></a></li>
				<li class="line">line</li>
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/xqgl/stay/delete" title="确认要删除?"><span>删除</span></a></li>
				<li class="line">line</li>
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/xqgl/stay/CloseStatus" title="确认批量关闭?"><span>批量关闭</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="icon" target="_blank" href="${contextPath }/xqgl/stay/toXls?search_LIKE_policy.policyNo=${search_LIKE_policy_policyNo }&search_LIKE_policy.holder=${search_LIKE_policy_holder }&orgCode=${orgCode }&search_LTE_csDate=${param.search_LTE_csDate }&search_GTE_csDate=${param.search_GTE_csDate }&status=${param.status }"><span>导出Excel</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=policy.organization.name class="${page.orderField eq 'policy.organization.name' ? page.orderDirection : ''}">保单机构</th>
				<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">保单号</th>
				<th>投保人</th>
				<th>退保时间</th>
				<shiro:hasPermission name="RenewedStay:prov">
				<th>退保次数</th>
				<th>挽留详情</th>
				<th>被保险人</th>
				<th>险种名称</th>
				<th>缴费期间</th>
				<th>保费</th>
				<th>承保时间</th>
				<th orderField=status class="${page.orderField eq 'status' ? page.orderDirection : ''}">状态</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${offsites}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.policy.organization.shortName}</td>
				<td>${item.policy.policyNo}</td>
				<td>${item.policy.holder}</td>
				<td><fmt:formatDate value='${item.csDate }' pattern='yyyy-MM-dd'/></td>
				<shiro:hasPermission name="RenewedStay:prov">
				<td>${item.stayNum}</td>
				<td>${item.remark}</td>
				<td>${item.policy.insured}</td>
				<td>${item.policy.prodName}</td>
				<td>${item.policy.perm}</td>
				<td>${item.policy.policyFee}</td>
				<td>${item.policy.policyDate}</td>
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
				</shiro:hasPermission>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>