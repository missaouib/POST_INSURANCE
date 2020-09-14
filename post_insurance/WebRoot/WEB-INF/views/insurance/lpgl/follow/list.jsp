<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/lpgl/list" page="${page }">
	<input type="hidden" name="search_LIKE_caseMan" value="${param.search_LIKE_caseMan }"/>
	<input type="hidden" name="organization.orgCode" value="${org_code }"/>
	<input type="hidden" name="organization.name" value="${org_name }"/>
	<input type="hidden" name="search_LTE_caseDate" value="${param.search_LTE_caseDate }"/>
	<input type="hidden" name="search_GTE_caseDate" value="${param.search_GTE_caseDate }"/>
	<input type="hidden" name="caseStatus" value="${caseStatus }"/>
	<input type="hidden" name="claimsType" value="${"claimsType" }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/lpgl/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						出险人：<input type="text" id="caseMan" style="width: 100px;" name="search_LIKE_caseMan" value="${param.search_LIKE_caseMan }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="settle.caseStatus" id="list_caseStatus" class="combox">
							<form:option value=""> -- </form:option>
							<form:option value="待立案">待立案</form:option>
							<form:option value="待调查">待调查</form:option>
							<form:option value="已结案">已结案</form:option>
						</form:select>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="organization.orgCode" id="case_orgCode" type="hidden" value="${org_code }"/>
					<input class="validate[required] required" name="organization.name" id="case_orgName" type="text" readonly="readonly" style="width: 140px;" value="${org_name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="organization" title="选择机构" width="400">查找带回</a>
					</td>
				</tr>
				<tr>
					<td>
						<label>出险日期：</label>
						<input type="text" name="search_GTE_caseDate" id="caseDate1" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_caseDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>出险日期：</label>
						<input type="text" name="search_LTE_caseDate" id="caseDate2" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_caseDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>出险类型：</label>
						<form:select path="settle.claimsType" id="list_claimsType" class="combox">
							<form:option value=""> -- </form:option>
							<form:option value="0">邮银险</form:option>
							<form:option value="1">简易险</form:option>
						</form:select>
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
			<shiro:hasPermission name="Settlement:edit">
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="630" height="530" href="${contextPath }/lpgl/update/{slt_uid}"><span>编辑</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Settlement:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/lpgl/delete" title="确认要删除?"><span>删除</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Settlement:view">
				<li class="line">line</li>
				<li><a class="icon" target="_blank" href="${contextPath }/lpgl/toXls?claimsType=${claimsType }&search_LIKE_caseMan=${search_LIKE_caseMan }&search_LTE_caseDate=${param.search_LTE_caseDate}&search_GTE_caseDate=${param.search_GTE_caseDate}&caseStatus=${caseStatus}&organization.orgCode=${org_code}&organization.name=${org_name}"><span>导出Excel</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">机构名称</th>
				<th orderField=claimsType class="${page.orderField eq 'claimsType' ? page.orderDirection : ''}">渠道</th>
				<th orderField=claimsNo class="${page.orderField eq 'claimsNo' ? page.orderDirection : ''}">赔案号</th>
				<th>出险人</th>
				<th>报案人</th>
				<th>报案人电话</th>
				<th orderField=caseDate class="${page.orderField eq 'caseDate' ? page.orderDirection : ''}">出险日期</th>
				<th orderField=caseType class="${page.orderField eq 'caseType' ? page.orderDirection : ''}">理赔类型</th>
				<th orderField=reporteDate class="${page.orderField eq 'reporteDate' ? page.orderDirection : ''}">报案日期</th>
				<th orderField=caseStatus class="${page.orderField eq 'caseStatus' ? page.orderDirection : ''}">状态</th>
				<th>出险天数</th>
				<th>报案天数</th>
				<th>追踪要求</th>
				<th>登记详情</th>
				<th>剩余追踪天数</th>
				<th>操作日志</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${users}" varStatus="idx">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.organization.shortName}</td>
				<td>${item.claimsType eq "0"?"邮银险":"简易险"}</td>
				<td><div style="color: <c:choose><c:when test="${item.lessFeedBack<0}">red</c:when><c:when test="${item.lessFeedBack<2}">orange</c:when><c:otherwise>"black"</c:otherwise></c:choose>;vertical-align:middle;font-weight:normal;">${item.claimsNo}</div></td>
				<td>${item.caseMan}</td>
				<td>${item.reporter}</td>
				<td>${item.reporterPhone}</td>
				<td><fmt:formatDate value="${item.caseDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.caseType}</td>
				<td><fmt:formatDate value="${item.reporteDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.caseStatus}</td>
				<td>${item.caseLong }</td>
				<td>${item.reportLong }</td>
				<td>
				<c:choose>
					<c:when test="${not empty item.needFeedBack and item.needFeedBack eq '待反馈'}">
					<div style="color: red;vertical-align:middle;font-weight:normal;">待反馈</div>
					</c:when>
					<c:when test="${not empty item.needFeedBack and item.needFeedBack eq '已反馈'}">
					<div style="color: blue;vertical-align:middle;font-weight:normal;">已反馈</div>
					</c:when>
					<c:otherwise>
						&nbsp;
					</c:otherwise>
				</c:choose>
				</td>
				<td>
					<a target="dialog" rel="lookup2Policy" mask="true" width="850" height="650" href="${contextPath }/lpgl/detail/${item.id}"><div style="color: blue;vertical-align:middle;font-weight:normal;">录入</div></a> &nbsp;&nbsp;
				</td>
				<td>${item.lessFeedBack }</td>
				<td>
				<a target="dialog" mask="true" width="750" height="430" href="${contextPath }/lpgl/log/${item.id}"><div style="color: blue;vertical-align:middle;font-weight:normal;">查看</div></a>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>