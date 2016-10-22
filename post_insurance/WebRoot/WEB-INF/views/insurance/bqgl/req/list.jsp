<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/bqgl/req/list" page="${page }">
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="search_LIKE_formNo" value="${param.search_LIKE_formNo }"/>
	<input type="hidden" name="search_LIKE_reqMan" value="${search_LIKE_reqMan }"/>
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="name" value="${name }"/>
	<input type="hidden" name="search_GTE_reqDate" value="${param.search_GTE_reqDate }"/>
	<input type="hidden" name="search_LTE_reqDate" value="${param.search_LTE_reqDate }"/>
	<input type="hidden" name="status" value="${status }"/>
</dwz:paginationForm>

<form method="post" id="bqForm" action="${contextPath }/bqgl/req/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						保单号：<input type="text" style="width: 100px;" id="reqPolicyNo" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
					</td>
					<td>
						投保单号：<input type="text" style="width: 100px;" id="reqformNo" name="search_LIKE_formNo" value="${param.search_LIKE_formNo }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="req.status" class="combox" id="bqStatus">
							<form:option value=""> -- -- </form:option>
							<form:options items="${crStatusList }" itemLabel="desc"/>
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
						客户姓名：<input type="text" style="width: 100px;" id="reqMan" name="search_LIKE_reqMan" value="${search_LIKE_reqMan }"/>
					</td>
					<td>
						<label>申请日期：</label>
						<input type="text" id="csDate1" name="search_GTE_reqDate" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_reqDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>申请日期：</label>
						<input type="text" id="csDate2" name="search_LTE_reqDate" class="date validate[required] required" style="width: 80px;"dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_reqDate }"/><a class="inputDateButton" href="javascript:;">选</a>
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
			<shiro:hasPermission name="CsReissue:provEdit">
				<li class="line">line</li>
				<li><a target="myDialog" rel="ids" href="${contextPath }/bqgl/req/batchSent" class="edit"><span>批量寄出（省分）</span></a></li>
				<li class="line">line</li>
				<li><a iconClass="user_go" target="selectedTodo" rel="ids" href="${contextPath }/bqgl/req/CloseStatus" title="确认批量关闭?"><span>批量关闭</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="CsReissue:cityEdit">
				<li class="line">line</li>
				<li><a target="myDialog" rel="ids" href="${contextPath }/bqgl/req/batchReceive" class="edit"><span>批量接收（地市）</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="icon" target="_blank" href="${contextPath }/bqgl/req/toXls?search_LIKE_conservationDtl.policy.policyNo=${search_LIKE_conservationDtl_policy_policyNo }&orgCode=${orgCode }&search_LTE_reqDate=${search_LTE_conservationDtl_csDate }&search_GTE_reqDate=${search_GTE_conservationDtl_csDate }&status=${param.status }"><span>导出Excel</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" target="dialog" href="${contextPath }/bqgl/help" mask="true" width="530" height="430"><span>功能说明</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=bankCode.organization.name class="${page.orderField eq 'bankCode.organization.name' ? page.orderDirection : ''}">申请地市</th>
				<th>投保单号</th>
				<th>保单号</th>
				<th>保单机构</th>
				<th>申请人</th>
				<th orderField=reqDate class="${page.orderField eq 'reqDate' ? page.orderDirection : ''}">申请日期</th>
				<th>联系电话</th>
				<th>保全项目</th>
				<th orderField=status class="${page.orderField eq 'status' ? page.orderDirection : ''}">状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${reqs}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>
				<c:choose>  
				    <c:when test="${fn:contains(item.bankCode.organization.name, '直属')}">  
				        <c:out value="${fn:replace(item.bankCode.organization.name,'邮政局直属中邮保险局','直属')}" />  
				    </c:when>  
				   <c:otherwise>  
				      <c:out value="${fn:replace(item.bankCode.organization.name,'邮政局中邮保险局','')}" />  
				    </c:otherwise>  
				</c:choose>
				</td>
				<td>${item.formNo}</td>
				<td>${item.policy.policyNo}</td>
				<td>${item.policy.organization.name}</td>
				<td>${item.reqMan}</td>
				<td><fmt:formatDate value='${item.reqDate }' pattern='yyyy-MM-dd'/></td>
				<td>${item.reqPhone}</td>
				<td>${item.reqTypeName}</td>
				<td>
				<c:choose>
					<c:when test="${item.status eq 'CloseStatus'}">
						 已关闭
					</c:when>
					<c:when test="${item.status eq 'DealStatus'}">
						已寄出
					</c:when>
					<c:when test="${item.status eq 'ReceiveStatus'}">
						已接收
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