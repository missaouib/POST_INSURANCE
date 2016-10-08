<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/client/list" page="${page }">
	<input type="hidden" name="search_GTE_policyFee" value="${param.search_GTE_policyFee }"/>
	<input type="hidden" name="search_LTE_policyFee" value="${param.search_LTE_policyFee }"/>
	<input type="hidden" name="orgCode" value="${policy_orgCode }"/>
	<input type="hidden" name="name" value="${policy_name }"/>
	<input type="hidden" name="search_LTE_policyDate" value="${param.search_LTE_policyDate }"/>
	<input type="hidden" name="search_GTE_policyDate" value="${param.search_GTE_policyDate }"/>
	<input type="hidden" name="search_LIKE_policyNo" value="${param.search_LIKE_policyNo }"/>
	<input type="hidden" name="search_LIKE_formNo" value="${param.search_LIKE_formNo }"/>
	<input type="hidden" name="search_GTE_perm" value="${param.search_GTE_perm }"/>
	<input type="hidden" name="search_LTE_perm" value="${param.search_LTE_perm }"/>
	<input type="hidden" name="status" value="${param.status }"/>
	<input type="hidden" name="prd.prdName" value="${prd_name }"/>
	<input type="hidden" name="search_LIKE_holder" value="${param.search_LIKE_holder}"/>
</dwz:paginationForm>

<form method="post" id="hfForm" action="${contextPath }/client/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						保费：<input type="text" id="pf1" style="width: 50px;" name="search_GTE_policyFee" value="${param.search_GTE_policyFee }"/>&nbsp;-&nbsp;<input type="text" id="pf2" style="width: 50px;" name="search_LTE_policyFee" value="${param.search_LTE_policyFee }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="policy.status" id="policyStatus" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:option value="有效">有效</form:option>
							<form:option value="终止">终止</form:option>
						</form:select>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="orgCode" id="c_orgCode" type="hidden" value="${policy_orgCode }"/>
						<input class="validate[required] required" name="name" id="c_orgName" type="text" readonly="readonly" style="width: 100px;" value="${policy_name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="policy" title="选择机构" width="400">查</a>
					</td>
					<td>
					投保人：<input type="text" id="policy_holder" style="width: 100px;" name="search_LIKE_holder" value="${param.search_LIKE_holder }"/>
					</td>
				</tr>
				<tr>
					<td>
						保单号：<input type="text" id="hfPolicyNo" style="width: 100px;" name="search_LIKE_policyNo" value="${param.search_LIKE_policyNo }"/>
					</td>
					<td>
						<label>承保日期：</label>
						<input type="text" name="search_GTE_policyDate" id="hfDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_policyDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>承保日期：</label>
						<input type="text" name="search_LTE_policyDate" id="hfDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_policyDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>产品：</label>
						<input name="prd.prdFullName" type="text" postField="search_LIKE_prdName" suggestFields="prdFullName" class="input-medium validate[required,maxSize[32]] required"
					suggestUrl="/common/lookupPrdSuggest" lookupGroup="prd" value="${prd_name }"/>
					</td>
				</tr>
				<tr>
					<td>
						投保单号：<input type="text" id="hfformno" style="width: 100px;" name="search_LIKE_formNo" value="${param.search_LIKE_formNo }"/>
					</td>
					<td>
						<label>缴费期间：</label>
						<input type="text" name="search_GTE_perm" id="ff1" class="validate[required] required" style="width: 80px;" value="${param.search_GTE_perm }"/>
					</td>
					<td>
						<label>缴费期间：</label>
						<input type="text" name="search_LTE_perm" id="ff2" class="validate[required] required" style="width: 80px;" value="${param.search_LTE_perm }"/>
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
			<shiro:hasPermission name="Client:view">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/client/view/{slt_uid}"><span>查看详情</span></a></li>
				<li class="line">line</li>
				<li><a class="icon" target="_blank" href="${contextPath }/client/toXls?search_GTE_policyFee=${param.search_GTE_policyFee }&search_LTE_policyFee=${param.search_LTE_policyFee }&orgCode=${policy_orgCode }&search_LTE_policyDate=${param.search_LTE_policyDate }&search_GTE_policyDate=${param.search_GTE_policyDate }&search_GTE_perm=${param.search_GTE_perm }&search_LTE_perm=${param.search_LTE_perm }&search_LIKE_policyNo=${param.search_LIKE_policyNo }&&search_LIKE_formNo=${param.search_LIKE_formNo }&encodeStatus=${encodeStatus == null?'null':encodeStatus }"><span>导出Excel</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="185" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>投保单号</th>
				<th orderField=policyNo class="${page.orderField eq 'policyNo' ? page.orderDirection : ''}">保单号</th>
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">机构</th>
				<th>投保人</th>
				<th>被保人</th>
				<th>险种名称</th>
				<th>保费</th>
				<th>交费方式</th>
				<th>交费期间</th>
				<th>承保日期</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${policies}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.formNo}</td>
				<td>${item.policyNo}</td>
				<td>
				<c:choose>  
				    <c:when test="${fn:contains(item.organization.name, '直属')}">  
				        <c:out value="${fn:replace(item.organization.name,'邮政局直属中邮保险局','直属')}" />  
				    </c:when>
				    <c:when test="${fn:contains(item.organization.name, '仲恺')}">  
				        ${fn:substring(item.organization.name, 0, 7)}
				    </c:when>
				   <c:otherwise>
				      <c:out value="${fn:replace(item.organization.name,'邮政局中邮保险局','')}" />  
				    </c:otherwise>  
				</c:choose>
				</td>
				<td>${item.holder}</td>
				<td>${item.insured}</td>
				<td>${item.prodName}</td>
				<td>${item.policyFee}</td>
				<td>${item.feeFrequency}</td>
				<td>${item.perm}</td>
				<td><fmt:formatDate value="${item.policyDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.status}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>