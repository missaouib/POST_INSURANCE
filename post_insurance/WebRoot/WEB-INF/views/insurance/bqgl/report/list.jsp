<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/bqgl/report/list" page="${page }">
	<input type="hidden" name="search_LIKE_csNo" value="${param.search_LIKE_csNo }"/>
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="name" value="${name }"/>
	<input type="hidden" name="search_LTE_csDate" value="${param.search_LTE_csDate }"/>
	<input type="hidden" name="search_GTE_csDate" value="${param.search_GTE_csDate }"/>
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="search_LIKE_csCode" value="search_LIKE_csCode">
</dwz:paginationForm>

<form method="post" id="paySuccessForm" action="${contextPath }/bqgl/report/list" onsubmit="return navTabSearch(this)">
	<input type="hidden" name="flag" value="bq">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						受理号：<input type="text" style="width: 100px;" id="csNo" name="search_LIKE_csNo" value="${param.search_LIKE_csNo }"/>
					</td>
					<td>
						<label>保单号：</label>
						<input type="text" style="width: 100px;" id="cspolicyNo" name="search_LIKE_policy.policyNo" value="${search_LIKE_csNo_policy_policyNo }"/>
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
						<input type="text" id="payCsDate1" name="search_GTE_csDate" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_csDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>回盘结束日期：</label>
						<input type="text" id="payCsDate2" name="search_LTE_csDate" class="date validate[required] required" style="width: 80px;"dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_csDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						保全编码：<input type="text" style="width: 100px;" id="csCode" name="search_LIKE_csCode" value="${param.search_LIKE_csCode }"/>
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
			<!-- 
			<li class="line">line</li>
			<li><a class="icon" target="_blank" href="${contextPath }/report/toXls?flag=bq&orgCode=${orgCode}&search_LTE_csDate=${param.search_LTE_csDate}&search_GTE_csDate=${param.search_GTE_csDate}&status=${status}"><span>导出Excel</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" target="dialog" href="${contextPath }/pay/help" mask="true" width="530" height="430"><span>功能说明</span></a></li>
			保全受理号 保单号 	保单所属机构 网点名称 	渠道 	操作机构代码 投保人姓名 	被保险人姓名 	保全复核通过日期 项目编码 金额 申请方式 
			 -->
		</ul>
	</div>
	<div id="w_list_print">
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th orderField=csNo class="${page.orderField eq 'csNo' ? page.orderDirection : ''}">保全受理号</th>
				<th>保单号</th>
				<th orderField=organName class="${page.orderField eq 'organName' ? page.orderDirection : ''}">所属机构</th>
				<th>网点名称</th>
				<th orderField=csChannel class="${page.orderField eq 'csChannel' ? page.orderDirection : ''}">渠道</th>
				<th>操作机构代码</th>
				<th>投保人姓名</th>
				<th>被保险人姓名</th>
				<th orderField=csDate class="${page.orderField eq 'csDate' ? page.orderDirection : ''}">通过日期</th>
				<th orderField=csCode class="${page.orderField eq 'csCode' ? page.orderDirection : ''}">项目编码</th>
				<th>金额</th>
				<th>申请方式</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.csNo}</td>
				<td>${item.policy.policyNo}</td>
				<td>${item.organName}</td>
				<td>${item.netName}</td>
				<td>${item.csChannel}</td>
				<td>${item.operateOrg}</td>
				<td>${item.holder}</td>
				<td>${item.insured}</td>
				<td><fmt:formatDate value="${item.csDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.csCode}</td>
				<td>${item.money}</td>
				<td>${item.csDeal}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>