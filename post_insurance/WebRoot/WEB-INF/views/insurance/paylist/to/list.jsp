<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/pay/to/list" page="${page }">
	<input type="hidden" name="search_LIKE_relNo" value="${param.search_LIKE_relNo }"/>
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="name" value="${name }"/>
	<input type="hidden" name="search_LTE_backDate" value="${param.search_LTE_backDate }"/>
	<input type="hidden" name="search_GTE_backDate" value="${param.search_GTE_backDate }"/>
	<input type="hidden" name="status" value="${param.status }"/>
</dwz:paginationForm>

<form method="post" id="payForm" action="${contextPath }/pay/to/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						业务单号：<input type="text" style="width: 100px;" id="relNo" name="search_LIKE_relNo" value="${param.search_LIKE_policy_policyNo }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="pay.status" class="combox" id="payStatus">
							<form:option value=""> -- -- </form:option>
							<form:options items="${ffStatusList }" itemLabel="desc"/>
						</form:select>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="orgCode" id="pay_orgCode" type="hidden" value="${orgCode }"/>
						<input class="validate[required] required" name="name" id="pay_orgName" type="text" readonly="readonly" style="width: 100px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
				</tr>
				<tr>
					<td>
						<label>回盘开始日期：</label>
						<input type="text" id="payBackDate1" name="search_GTE_backDate" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_backDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>回盘结束日期：</label>
						<input type="text" id="payBackDate2" name="search_LTE_backDate" class="date validate[required] required" style="width: 80px;"dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_backDate }"/><a class="inputDateButton" href="javascript:;">选</a>
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
			<shiro:hasPermission name="Cservice:reset">
				<li class="line">line</li>
				<li><a iconClass="user_go" target="ajaxTodo" href="${contextPath }/pay/close/{slt_uid}" title="确认关闭?"><span>关闭</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="icon" target="dialog" href="${contextPath }/bqgl/help" mask="true" width="530" height="430"><span>功能说明</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th orderField=orgName class="${page.orderField eq 'orgName' ? page.orderDirection : ''}">管理机构</th>
				<th>账户名</th>
				<th>账号</th>
				<th>金额</th>
				<th orderField=failDesc class="${page.orderField eq 'failDesc' ? page.orderDirection : ''}">状态描述</th>
				<th orderField=backDate class="${page.orderField eq 'backDate' ? page.orderDirection : ''}">回盘日期</th>
				<th orderField=relNo class="${page.orderField eq 'relNo' ? page.orderDirection : ''}">关联业务号码</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${paylists}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.orgName}</td>
				<td>${item.accountName}</td>
				<td>${item.account}</td>
				<td>${item.money}</td>
				<td>${item.failDesc}</td>
				<td>${item.backDate}</td>
				<td>${item.relNo}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>