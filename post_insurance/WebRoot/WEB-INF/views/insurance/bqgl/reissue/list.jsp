<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/bqgl/reissue/list" page="${page }">
	<input type="hidden" name="search_LIKE_policyNo" value="${param.search_LIKE_policyNo }"/>
	<input type="hidden" name="search_LIKE_client" value="${param.search_LIKE_client }"/>
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="name" value="${name }"/>
	<input type="hidden" name="search_LTE_dealDate" value="${param.search_LTE_dealDate }"/>
	<input type="hidden" name="search_GTE_dealDate" value="${param.search_GTE_dealDate }"/>
	<input type="hidden" name="status" value="${param.status }"/>
</dwz:paginationForm>

<form method="post" id="bqForm" action="${contextPath }/bqgl/reissue/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						保单号：<input type="text" style="width: 100px;" id="bqPolicyNo" name="search_LIKE_policyNo" value="${param.search_LIKE_policyNo }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="reissue.status" class="combox" id="bqStatus">
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
						客户姓名：<input type="text" style="width: 100px;" id="bqPolicyNo" name="search_LIKE_client" value="${param.search_LIKE_client }"/>
					</td>
					<td>
						<label>申请日期：</label>
						<input type="text" id="dealDate1" name="search_GTE_csDate" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_csDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>申请日期：</label>
						<input type="text" id="dealDate2" name="search_LTE_csDate" class="date validate[required] required" style="width: 80px;"dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_csDate }"/><a class="inputDateButton" href="javascript:;">选</a>
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
			<shiro:hasPermission name="CsReissue:save">
				<li><a iconClass="user_add" target="dialog" rel="lookup2organization_add" mask="true" width="530" height="430" href="${contextPath }/bqgl/reissue/create"><span>登记合同补发</span></a></li>
			</shiro:hasPermission>
		 -->
			<shiro:hasPermission name="CsReissue:provEdit">
			<!-- 
				<shiro:hasPermission name="CsReissue:delete">
					<li class="line">line</li>
					<li><a iconClass="user_delete" target="selectedTodo" rel="ids" href="${contextPath }/bqgl/reissue/delete" title="确认要删除?"><span>删除</span></a></li>
				</shiro:hasPermission>
			-->
				<li class="line">line</li>
				<li><a iconClass="user_go" href="${contextPath}/bqgl/reissue/provSend/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>省分寄出</span></a></li>
				<li><a iconClass="user_go" target="selectedTodo" rel="ids" href="${contextPath }/bqgl/reissue/CloseStatus" title="确认批量关闭?"><span>批量关闭</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="CsReissue:cityEdit">
			<li class="line">line</li>
				<li><a iconClass="user_go" href="${contextPath}/bqgl/reissue/cityRec/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>地市接收</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="icon" target="_blank" href="${contextPath }/bqgl/reissue/toXls?search_LIKE_policyNo=${param.search_LIKE_policyNo }&orgCode=${orgCode }&search_LTE_csDate=${param.search_LTE_csDate }&search_GTE_csDate=${param.search_GTE_csDate }&status=${param.status }"><span>导出Excel</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" target="dialog" href="${contextPath }/bqgl/help" mask="true" width="530" height="430"><span>功能说明</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">地市</th>
				<th>客户姓名</th>
				<th>保单号</th>
				<th orderField=dealDate class="${page.orderField eq 'dealDate' ? page.orderDirection : ''}">申请日期</th>
				<th>快递单号</th>
				<th>省分收到日期</th>
				<th>省分寄出日期</th>
				<th>地市接收日期</th>
				<th orderField=status class="${page.orderField eq 'status' ? page.orderDirection : ''}">状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${reissues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>
				<c:choose>  
				    <c:when test="${fn:contains(item.organization.name, '直属')}">  
				        <c:out value="${fn:replace(item.organization.name,'邮政局直属中邮保险局','直属')}" />  
				    </c:when>  
				   <c:otherwise>  
				      <c:out value="${fn:replace(item.organization.name,'邮政局中邮保险局','')}" />  
				    </c:otherwise>  
				</c:choose>
				</td>
				<td>${item.conservationDtl.policy.holder}</td>
				<td>${item.conservationDtl.policy.policyNo}</td>
				<td><fmt:formatDate value='${item.csDate }' pattern='yyyy-MM-dd'/></td>
				<td>${item.provExpressNo}</td>
				<td>${item.provReceiveDate}</td>
				<td>${item.provSentDate}</td>
				<td>${item.cityReceiveDate}</td>
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