<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/bqgl/loan/list" page="${page }">
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="name" value="${name }"/>
	<input type="hidden" name="status" value="${status }"/>
	<input type="hidden" name="flag" value="${flag }"/>
	<input type="hidden" name="search_LTE_shouldDate" value="${param.search_LTE_shouldDate }"/>
	<input type="hidden" name="search_GTE_shouldDate" value="${param.search_GTE_shouldDate }"/>
	<input type="hidden" name="search_LIKE_holder" value="${param.search_LIKE_holder }"/>
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
</dwz:paginationForm>

<form method="post" id="paySuccessForm" action="${contextPath }/bqgl/loan/list" onsubmit="return navTabSearch(this)">
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
					<td>
					<label>状态：</label>
						<form:select path="loan.status" id="loanStatus" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:option value="借款"> 借款 </form:option>
							<form:option value="关闭"> 关闭 </form:option>
						</form:select>
					</td>
					<td>
						<label>投保人：</label>
						<input type="text" style="width: 100px;" id="csholder" name="search_LIKE_holder" value="${param.search_LIKE_holder }"/>
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
					<td>
					<label>标记：</label>
						<form:select path="loan.flag" id="loanFlag" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:option value="2"> 逾期 </form:option>
							<form:option value="1"> 预警 </form:option>
							<form:option value="0"> 正常 </form:option>
						</form:select>
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
		<shiro:hasPermission name="CsLoan:delete">
			<li class="line">line</li>
			<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/bqgl/loan/CloseStatus" title="确认要结案关闭?"><span>批量关闭</span></a></li>
		</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="edit" href="${contextPath}/bqgl/loan/remark/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>设置备注</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" target="_blank" href="${contextPath }/bqgl/loan/list/toXls?orgCode=${orgCode}&search_LTE_shouldDate=${param.search_LTE_shouldDate}&search_GTE_shouldDate=${param.search_GTE_shouldDate}&search_LIKE_policy.policyNo=${search_LIKE_policy_policyNo}&search_LIKE_holder=${param.search_LIKE_holder}&status=${status}&flag=${flag}"><span>导出Excel</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="table" layoutH="160" width="110%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>标记</th>
				<th>管理机构</th>
				<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">保单号码</th>
				<th>投保人</th>
				<th>联系方式</th>
				<th>性别</th>
				<th>险种名称</th>
				<th>出单网点</th>
				<th>借款日期</th>
				<th>借款金额</th>
				<th>约定还款</th>
				<th>状态</th>
				<th>备注</th>
				<th>附件</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>
                <c:choose>  
				    <c:when test="${item.checkDate>1}">  
				        <div style="color: red;vertical-align:middle;font-weight:bold;">逾期</div>
				    </c:when>
				    <c:when test="${item.checkDate>-30}">  
				        <div style="color: orange;vertical-align:middle;font-weight:bold;">预警</div>
				    </c:when>
				   <c:otherwise>  
				      <div style="color: green;vertical-align:middle;font-weight:bold;">正常</div>
				    </c:otherwise>  
				</c:choose>
				</td>
				<td>${item.policy.organization.shortName}</td>
				<td>${item.policy.policyNo}</td>
				<td>${item.holder}</td>
				<td>${item.phone}</td>
				<td>${item.holderSexy}</td>
				<td>${item.prodName}</td>
				<td>${fn:replace(item.bankName, "中国邮政储蓄银行股份有限公司", "")}</td>
				<td><fmt:formatDate value="${item.loanDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.loanFee}</td>
				<td><fmt:formatDate value="${item.shouldDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.status}</td>
				<td title="${item.remark}">${fn:substring(item.remark, 0, 14)}</td>
				<td>
		           <a href="${item.attachment}" target="_blank">${item.attachment}</a>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>