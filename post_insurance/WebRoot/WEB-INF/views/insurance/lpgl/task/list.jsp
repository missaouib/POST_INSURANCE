<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/lpgl/task/list" page="${page }">
	<input type="hidden" name="search_LIKE_insured" value="${param.search_LIKE_insured }"/>
	<input type="hidden" name="organization.orgCode" value="${org_code }"/>
	<input type="hidden" name="organization.name" value="${org_name }"/>
	<input type="hidden" name="search_LTE_checkStartDate" value="${param.search_LTE_checkStartDate }"/>
	<input type="hidden" name="search_GTE_checkStartDate" value="${param.search_GTE_checkStartDate }"/>
	<input type="hidden" name="checkStatus" value="${checkStatus }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/lpgl/task/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						出险人：<input type="text" id="insured" style="width: 100px;" name="search_LIKE_insured" value="${param.search_LIKE_insured }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="task.checkStatus" id="list_caseStatus" class="combox">
							<form:option value=""> -- </form:option>
							<form:option value="调查中">调查中</form:option>
							<form:option value="调查完成">调查完成</form:option>
						</form:select>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="organization.orgCode" id="uw_orgCode" type="hidden" value="${org_code }"/>
					<input class="validate[required] required" name="organization.name" id="uw_orgName" type="text" readonly="readonly" style="width: 140px;" value="${org_name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="organization" title="选择机构" width="400">查找带回</a>
					</td>
				</tr>
				<tr>
					<td>
						<label>调查日期：</label>
						<input type="text" name="search_GTE_checkStartDate" id="lpcDate1" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_checkStartDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>调查日期：</label>
						<input type="text" name="search_LTE_checkStartDate" id="lpcDate2" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_checkStartDate }"/><a class="inputDateButton" href="javascript:;">选</a>
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
			<shiro:hasPermission name="Settlement:save">
				<li><a iconClass="user_add" target="dialog" rel="lookup2organization_add" mask="true" width="530" height="450" href="${contextPath }/lpgl/task/create"><span>添加</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Settlement:edit">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="530" height="550" href="${contextPath }/lpgl/task/update/{slt_uid}"><span>编辑</span></a></li>
				<li><a iconClass="user_edit" target="ajaxTodo" title="确认已完成？" href="${contextPath }/lpgl/task/done/{slt_uid}"><span>完成</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Settlement:delete">
				<li><a iconClass="user_delete" target="selectedTodo" rel="ids" href="${contextPath }/lpgl/task/delete" title="确认要删除?"><span>删除</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Settlement:view">
				<li class="line">line</li>
				<li><a class="icon" target="_blank" href="${contextPath }/lpgl/task/toXls?search_LIKE_insured=${search_LIKE_insured }&search_LTE_checkStartDate=${param.search_LTE_checkStartDate}&search_GTE_checkStartDate=${param.search_GTE_checkStartDate}&checkStatus=${checkStatus}&organization.orgCode=${org_code}&organization.name=${org_name}"><span>导出Excel</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>操作</th>	
				<th>序号</th>	
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">机构名称</th>
				<th>出险人</th>
				<th>保单号</th>
				<th>调查起期</th>
				<th>调查时效</th>
				<th orderField=checker class="${page.orderField eq 'checker' ? page.orderDirection : ''}">调查人</th>
				<th>查勘费</th>
				<th>附件</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${users}" varStatus="idx">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>
					<a target="dialog" mask="true" width="530" height="530" href="${contextPath }/lpgl/task/update/${item.id}">详情</a> &nbsp;&nbsp;
					<a target="dialog" mask="true" width="750" height="430" href="${contextPath }/lpgl/task/log/${item.id}">操作日志</a>
				</td>	
				<td>${idx.index+1 }</td>
				<td title="${item.organization.name}">
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
				<td>${item.insured}</td>
				<td>${item.policy.policyNo}</td>
				<td><fmt:formatDate value="${item.checkStartDate}" pattern="yyyy-MM-dd"/></td>
				<td>${item.limitation }</td>
				<td>${item.checker}</td>
				<td>${item.checkFee}</td>
				<td> <a href="${item.attrLink}">${item.attrLink}</a></td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>