<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/bqgl/report/list" page="${page }">
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="name" value="${name }"/>
	<input type="hidden" name="search_LTE_shouldDate" value="${param.search_LTE_shouldDate }"/>
	<input type="hidden" name="search_GTE_shouldDate" value="${param.search_GTE_shouldDate }"/>
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
</dwz:paginationForm>

<form method="post" id="paySuccessForm" action="${contextPath }/bqgl/report/list" onsubmit="return navTabSearch(this)">
	<input type="hidden" name="flag" value="bq">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>保单号：</label>
						<input type="text" style="width: 100px;" id="cspolicyNo" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="orgCode" id="pay_orgCode" type="hidden" value="${orgCode }"/>
						<input class="validate[required] required" name="name" id="pay_orgName" type="text" readonly="readonly" style="width: 100px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
				</tr>
				<tr>
					<td>
						<label>开始日期：</label>
						<input type="text" id="shouldDate1" name="search_GTE_shouldDate" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_shouldDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>结束日期：</label>
						<input type="text" id="shouldDate2" name="search_LTE_shouldDate" class="date validate[required] required" style="width: 80px;"dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_shouldDate }"/><a class="inputDateButton" href="javascript:;">选</a>
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
			<li class="line">line</li>
			<li><a class="icon" target="_blank" href="${contextPath }/bqgl/report/list/toXls?orgCode=${orgCode}&search_LTE_shouldDate=${param.search_LTE_shouldDate}&search_GTE_shouldDate=${param.search_GTE_shouldDate}&search_LIKE_csNo=${param.search_LIKE_csNo}&search_LIKE_policy.policyNo=${search_LIKE_policy_policyNo}&search_LIKE_staffFlag=${csReport.search_LIKE_staffFlag}&search_LIKE_csCode=${param.search_LIKE_csCode}"><span>导出Excel</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>逾期标记</th>
				<th>管理机构</th>
				<th orderField=csNo class="${page.orderField eq 'csNo' ? page.orderDirection : ''}">保单号码</th>
				<th>投保人姓名</th>
				<th>投保人性别</th>
				<th>险种名称</th>
				<th>出单网点</th>
				<th>借款日期</th>
				<th>借款金额</th>
				<th>约定还款日期</th>
				<th>保单状态</th>
				<th>联系方式</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>
               <c:choose>  
				    <c:when test="${item.checkDate>1}">  
				        <div style="color: red;vertical-align:middle;font-weight:bold;">逾期状态</div>
				    </c:when>
				    <c:when test="${item.checkDate>-30}">  
				        <div style="color: yellow;vertical-align:middle;font-weight:bold;">预警状态</div>
				    </c:when>
				   <c:otherwise>  
				      <div style="color: green;vertical-align:middle;font-weight:bold;">正常状态</div>
				    </c:otherwise>  
				</c:choose>
				</td>
				<td>${item.organName}</td>
				<td>${item.policy.policyNo}</td>
				<td>${item.holder}</td>
				<td>${item.holderSexy}</td>
				<td>${item.prodName}</td>
				<td>${fn:replace(item.bankName, "中国邮政储蓄银行股份有限公司", "")}</td>
				<td><fmt:formatDate value="${item.loanDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.loanFee}</td>
				<td><fmt:formatDate value="${item.shouldDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.status}</td>
				<td>${item.phone}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>