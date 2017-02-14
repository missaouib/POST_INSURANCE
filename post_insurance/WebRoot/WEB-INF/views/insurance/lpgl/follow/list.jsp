<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/lpgl/list" page="${page }">
	<input type="hidden" name="search_LIKE_insured" value="${param.search_LIKE_insured }"/>
	<input type="hidden" name="organization.orgCode" value="${org_code }"/>
	<input type="hidden" name="organization.name" value="${org_name }"/>
	<input type="hidden" name="search_LTE_caseDate" value="${param.search_LTE_caseDate }"/>
	<input type="hidden" name="search_GTE_caseDate" value="${param.search_GTE_caseDate }"/>
	<input type="hidden" name="caseStatus" value="${caseStatus }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/lpgl/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						出险人：<input type="text" id="insured" style="width: 100px;" name="search_LIKE_insured" value="${param.search_LIKE_insured }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="settle.caseStatus" id="list_caseStatus" class="combox">
							<form:option value=""> -- </form:option>
							<form:option value="待报案">待报案</form:option>
							<form:option value="待立案">待立案</form:option>
							<form:option value="待调查">待调查</form:option>
							<form:option value="待结案">待结案</form:option>
							<form:option value="拒付退费">拒付退费</form:option>
							<form:option value="结案关闭">结案关闭</form:option>
							<form:option value="不予立案">不予立案</form:option>
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
						<label>工单日期：</label>
						<input type="text" name="search_GTE_caseDate" id="lpcDate1" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_caseDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>工单日期：</label>
						<input type="text" name="search_LTE_caseDate" id="lpcDate2" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_caseDate }"/><a class="inputDateButton" href="javascript:;">选</a>
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
				<li><a class="add" target="dialog" rel="lookup2organization_add" mask="true" width="530" height="450" href="${contextPath }/lpgl/create"><span>添加</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Settlement:edit">
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="530" height="330" href="${contextPath }/lpgl/update/{slt_uid}"><span>编辑</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Settlement:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/lpgl/delete" title="确认要删除?"><span>删除</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Settlement:view">
				<li class="line">line</li>
				<li><a class="icon" target="_blank" href="${contextPath }/lpgl/toXls?search_LIKE_insured=${search_LIKE_insured }&search_LTE_caseDate=${param.search_LTE_caseDate}&search_GTE_caseDate=${param.search_GTE_caseDate}&caseStatus=${caseStatus}&organization.orgCode=${org_code}&organization.name=${org_name}"><span>导出Excel</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>操作</th>	
				<th>序号</th>	
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">机构名称</th>
				<th>出险人</th>
				<th>报案人</th>
				<th>报案人电话</th>
				<th orderField=caseDate class="${page.orderField eq 'caseDate' ? page.orderDirection : ''}">出险日期</th>
				<th orderField=caseType class="${page.orderField eq 'caseType' ? page.orderDirection : ''}">理赔类型</th>
				<th orderField=reporteDate class="${page.orderField eq 'reporteDate' ? page.orderDirection : ''}">报案日期</th>
				<th orderField=recordDate class="${page.orderField eq 'recordDate' ? page.orderDirection : ''}">立案日期</th>
				<th orderField=closeDate class="${page.orderField eq 'closeDate' ? page.orderDirection : ''}">结案日期</th>
				<th>赔付金额</th>
				<th orderField="caseStatus" class="${page.orderField eq 'caseStatus' ? page.orderDirection : ''}">账户状态</th>
				<th orderField="createTime" class="${page.orderField eq 'createTime' ? page.orderDirection : ''}">录入时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${users}" varStatus="idx">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>
					<a target="dialog" mask="true" width="530" height="530" href="${contextPath }/lpgl/detail/${item.id}">登记详情</a> &nbsp;&nbsp;
					<a target="dialog" mask="true" width="750" height="430" href="${contextPath }/lpgl/log/${item.id}">操作日志</a>
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
				<td>${item.reporter}</td>
				<td>${item.reporterPhone}</td>
				<td><fmt:formatDate value="${item.caseDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.caseType}</td>
				<td><fmt:formatDate value="${item.reporteDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.recordDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.closeDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.payFee}</td>
				<td>${item.caseStatus}</td>
				<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>