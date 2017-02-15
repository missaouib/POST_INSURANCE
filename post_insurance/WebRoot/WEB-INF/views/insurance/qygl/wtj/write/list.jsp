<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/qygl/issue/write/list" page="${page }">
	<input type="hidden" name="search_LIKE_issueNo" value="${param.search_LIKE_issueNo }"/>
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="name" value="${name }"/>
	<input type="hidden" name="search_LTE_policy.policyDate" value="${search_LTE_policy_policyDate }"/>
	<input type="hidden" name="search_GTE_policy.policyDate" value="${search_GTE_policy_policyDate }"/>
	<input type="hidden" name="status" value="${status }"/>
</dwz:paginationForm>

<form id="qyWriteForm" method="post" action="${contextPath }/qygl/issue/write/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
		<table class="searchContent">
				<tr>
					<td>
						保单号：<input type="text" id="qyWritePolicyNo" style="width: 100px;" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="issue.fixStatus" id="qy_w_status" class="combox">
						<form:option value=""> -- -- </form:option>
						<form:options items="${qyWriteStatusList }" itemLabel="desc"/>
					</form:select>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="orgCode" id="qy_w_orgCode" type="hidden" value="${orgCode }"/>
						<input class="validate[required] required" name="name" id="qy_w_orgName" type="text" readonly="readonly" style="width: 100px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
				</tr>
				<tr>
					<td>
						<label>承保开始日期：</label>
						<input type="text" name="search_GTE_policy.policyDate" id="qy_w_date1" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${search_GTE_policy_policyDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>承保结束日期：</label>
						<input type="text" name="search_LTE_policy.policyDate" id="qy_w_date2" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${search_LTE_policy_policyDate }"/><a class="inputDateButton" href="javascript:;">选</a>
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
			<shiro:hasPermission name="CheckWrite:view">
				<li><a iconClass="magnifier" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/qygl/issue/write/view/{slt_uid}"><span>查看详情</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="CheckWrite:edit">
				<li class="line">line</li>
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/qygl/issue/write/update/{slt_uid}"><span>回复</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="icon" target="dialog" href="${contextPath }/qygl/help" mask="true" width="530" height="430"><span>功能说明</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=policy.organization.name class="${page.orderField eq 'policy.organization.name' ? page.orderDirection : ''}">所属机构</th>
				<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">所属保单号</th>
				<th orderField=policy.policyDate class="${page.orderField eq 'policy.policyDate' ? page.orderDirection : ''}">承保日期</th>
				<th orderField=fixStatus class="${page.orderField eq 'fixStatus' ? page.orderDirection : ''}">问题件状态</th>
				<th orderField=docMiss class="${page.orderField eq 'docMiss' ? page.orderDirection : ''}">资料缺失</th>
				<th orderField=keyInfo class="${page.orderField eq 'keyInfo' ? page.orderDirection : ''}">关键信息</th>
				<th orderField=importanceInfo class="${page.orderField eq 'importanceInfo' ? page.orderDirection : ''}">重要信息</th>
				<th orderField=netName class="${page.orderField eq 'netName' ? page.orderDirection : ''}">网点名称</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${fn:replace(item.policy.organization.name,'邮政局中邮保险局','')}</td>
				<td>${item.policy.policyNo}</td>
				<td>${item.policy.policyDate}</td>
				<td>
				<c:choose>
					<c:when test="${item.fixStatus eq 'NewStatus'}">
						<span style="color:red; height:50%; margin-bottom:-contentheight;">待处理</span>
					</c:when>
					<c:when test="${item.fixStatus eq 'DealStatus'}">
						已回复
					</c:when>
					<c:when test="${item.fixStatus eq 'ReopenStatus'}">
						重打开
					</c:when>
					<c:otherwise>
						已关闭
					</c:otherwise>
				</c:choose>
				</td>
				<td>${item.docMiss == "null"?"":item.docMiss}</td>
				<td>${item.keyInfo=="null"?"":item.keyInfo}</td>
				<td>${item.importanceInfo=="null"?"":item.importanceInfo}</td>
				<td>
				<c:choose>  
					    <c:when test="${fn:length(item.netName) > 14}">  
					        <c:out value="${fn:substring(item.netName, 14, 30)}" />  
					    </c:when>  
					   <c:otherwise>  
					      <c:out value="${item.netName}" />  
					    </c:otherwise>  
					</c:choose>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>