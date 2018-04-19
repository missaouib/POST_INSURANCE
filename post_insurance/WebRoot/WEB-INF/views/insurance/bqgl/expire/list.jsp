<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/bqgl/expire/list" page="${page }">
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="search_LIKE_policy.holder" value="${search_LIKE_policy_holder }"/>
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="name" value="${name }"/>
	<input type="hidden" name="search_LTE_policyEndDate" value="${param.search_LTE_policyEndDate }"/>
	<input type="hidden" name="search_GTE_policyEndDate" value="${param.search_GTE_policyEndDate }"/>
	<input type="hidden" name="search_LIKE_finalLevel" value="${param.search_LIKE_finalLevel }"/>
	<input type="hidden" name="status" value="${status }"/>
</dwz:paginationForm>

<form method="post" id="bqForm" action="${contextPath }/bqgl/expire/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						保单号：<input type="text" style="width: 100px;" id="expirePolicyNo" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="expire.status" class="combox" id="csStatus">
							<form:option value=""> -- -- </form:option>
							<form:options items="${csStatusList }" itemLabel="desc"/>
						</form:select>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="orgCode" id="expire_orgCode" type="hidden" value="${orgCode }"/>
						<input class="validate[required] required" name="name" id="bq_orgName" type="text" readonly="readonly" style="width: 100px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
					<td>
					<label>评级：</label>
					<form:select path="expire.search_LIKE_finalLevel" id="exfl" class="combox">
						<form:option value=""> -- </form:option>
						<form:option value="风险">风险</form:option>
						<form:option value="关注">关注</form:option>
						<form:option value="预警">预警</form:option>
						<form:option value="正常">正常</form:option>
					</form:select>
					</td>
				</tr>
				<tr>
					<td>
						客户姓名：<input type="text" style="width: 100px;" id="bqPolicyholder" name="search_LIKE_policy.holder" value="${search_LIKE_policy_holder }"/>
					</td>
					<td>
						<label>满期日期：</label>
						<input type="text" id="csExpireDate1" name="search_GTE_policyEndDate" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_policyEndDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>满期日期：</label>
						<input type="text" id="csExpireDate2" name="search_LTE_policyEndDate" class="date validate[required] required" style="width: 80px;"dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_policyEndDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>&nbsp;
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
			<shiro:hasPermission name="CsReissue:provEdit">
				<li class="line">line</li>
				<li><a class="add" target="dialog" rel="csexpire_edit" mask="true" width="530" height="330" href="${contextPath }/bqgl/expire/update/{slt_uid}"><span>登记跟进情况</span></a></li>
				<li class="line">line</li>
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/bqgl/expire/WARNStatus" title="确认批量将保单设为异常件?"><span>批量异常</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="icon" target="_blank" href="${contextPath }/bqgl/expire/toXls?search_LIKE_policy.policyNo=${search_LIKE_policy_policyNo }&orgCode=${orgCode }&search_LTE_policyEndDate=${search_LTE_policyEndDate }&search_GTE_policyEndDate=${search_GTE_policyEndDate }&status=${param.status }&search_LIKE_finalLevel=${param.search_LIKE_finalLevel}"><span>导出Excel</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=policy.organization.name class="${page.orderField eq 'policy.organization.name' ? page.orderDirection : ''}">地市</th>
				<th>保单号</th>
				<th>险种</th>
				<th orderField=policyEndDate class="${page.orderField eq 'csReport.csDate' ? page.orderDirection : ''}">满期日期</th>
				<th>渠道</th>
				<th>投保人年龄</th>
				<th>期满年龄</th>
				<th>被保人年龄</th>
				<th>期满年龄</th>
				<th>投保人手机</th>
				<th>员工单</th>
				<th>评级</th>
				<th orderField=status class="${page.orderField eq 'status' ? page.orderDirection : ''}">状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${expires}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.policy.organization.shortName}</td>
				<td>${item.policy.policyNo}</td>
				<td>${item.policy.prodName}</td>
				<td><fmt:formatDate value='${item.policyEndDate}' pattern='yyyy-MM-dd'/></td>
				<td>${item.saleChannel}</td>
				<td>${item.holderYear}</td>
				<td>${item.holderExpireYear}</td>
				<td>${item.insuredYear}</td>
				<td>${item.insuredExpireYear}</td>
				<td>${item.holderMobile}</td>
				<td>${item.staffLevel}</td>
				<td>${item.finalLevel}</td>
				<td>
				<c:choose>
					<c:when test="${item.status eq 'AGStatus'}">
						已领取
					</c:when>
					<c:when test="${item.status eq 'CTStatus'}">
						已退保
					</c:when>
					<c:when test="${item.status eq 'WARNStatus'}">
						异常件
					</c:when>
					<c:otherwise>
						<span style="color:red; height:50%; margin-bottom:-contentheight;">待处理</span>
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