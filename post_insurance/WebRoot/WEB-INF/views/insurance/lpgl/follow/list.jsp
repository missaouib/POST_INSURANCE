<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/lpgl/list" page="${page }">
	<input type="hidden" name="search_LIKE_insured" value="${param.search_LIKE_insured }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/lpgl/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>出险人：</label>
					<input type="text" name="search_LIKE_insured" value="${param.search_LIKE_insured }"/>
				</li>
			</ul>
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
			<shiro:hasPermission name="User:save">
				<li><a iconClass="user_add" target="dialog" rel="lookup2organization_add" mask="true" width="530" height="450" href="${contextPath }/lpgl/create"><span>添加</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="User:edit:User拥有的资源">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="530" height="330" href="${contextPath }/lpgl/update/{slt_uid}"><span>编辑</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="User:delete:User拥有的资源">
				<li><a iconClass="user_delete" target="selectedTodo" rel="ids" href="${contextPath }/lpgl/delete" title="确认要删除?"><span>删除</span></a></li>
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
				<td><a href="${contextPath }/lpgl/detail/${item.id}">登记详情</a></td>	
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