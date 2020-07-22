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
	<input type="hidden" name="search_LIKE_fullCsCode" value="${param.search_LIKE_fullCsCode }">
	<input type="hidden" name="search_LIKE_staffFlag" value="${param.search_LIKE_staffFlag }">
	<input type="hidden" name="prd.prdFullName" value="${prd_name }"/>
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
						<input type="text" style="width: 100px;" id="cspolicyNo" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="orgCode" id="pay_orgCode" type="hidden" value="${orgCode }"/>
						<input class="validate[required] required" name="name" id="pay_orgName" type="text" readonly="readonly" style="width: 100px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
					<td>
					<label>员工单标记：</label>
					<form:select path="csReport.search_LIKE_staffFlag" id="tbstaffflags" class="combox">
						<form:option value="">  --  </form:option>
						<form:option value="0"> 普通客户 </form:option>
						<form:option value="1"> 员工单 </form:option>
					</form:select>
					</td>
					<td><label>产品：</label>
					<input name="prd.prdFullName" type="text" postField="search_LIKE_prdName" suggestFields="prdFullName" class="input-medium validate[required,maxSize[32]] required"
					suggestUrl="/common/lookupPrdSuggest" lookupGroup="prd" value="${prd_name }"/>
					</td>
				</tr>
				<tr>
					<td>
						<label>开始日期：</label>
						<input type="text" id="payCsDate1" name="search_GTE_csDate" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_csDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>结束日期：</label>
						<input type="text" id="payCsDate2" name="search_LTE_csDate" class="date validate[required] required" style="width: 80px;"dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_csDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						保全编码：<input type="text" style="width: 100px;" id="fullCsCode" name="search_LIKE_fullCsCode" value="${param.search_LIKE_fullCsCode }"/>
					</td>
					<td>
					<label>申请方式：</label>
					<select name="search_LIKE_csDeal" class="combox">
					  <option value=""> -- </option>
					  <option value="微信">微信</option>
					  <option value="柜面亲办">柜面亲办</option>
					  <option value="部门转办">部门转办</option>
					  <option value="邮保通">邮保通保全</option>
					</select>
					</td>
					<td>&nbsp;</td>
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
			<li><a class="icon" onclick="javascript:urlCheckOverDate(${page.getTotalCount() },183, $('#payCsDate1').val(),$('#payCsDate2').val(),'${contextPath }/bqgl/report/list/toXls?orgCode=${orgCode}&search_LTE_csDate=${param.search_LTE_csDate}&search_GTE_csDate=${param.search_GTE_csDate}&search_LIKE_csNo=${param.search_LIKE_csNo}&search_LIKE_policy.policyNo=${search_LIKE_policy_policyNo}&search_LIKE_staffFlag=${csReport.search_LIKE_staffFlag}&search_LIKE_fullCsCode=${param.search_LIKE_fullCsCode}&prd.prdFullName=${prd_name }');"><span>导出Excel</span></a></li>
			<!-- 
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
				<td>${item.fullCsCode}</td>
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