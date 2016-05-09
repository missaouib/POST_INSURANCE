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
				<li><a iconClass="user_add" target="dialog" rel="lookup2organization_add" mask="true" width="530" height="330" href="${contextPath }/lpgl/create"><span>添加</span></a></li>
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
				<th>序号</th>		
				<th>出险人</th>
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">所在组织</th>
				<th >角色</th>
				<th orderField="status" class="${page.orderField eq 'status' ? page.orderDirection : ''}">账户状态</th>
				<th orderField="createTime" class="${page.orderField eq 'createTime' ? page.orderDirection : ''}">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${users}" varStatus="idx">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${idx.index+1 }</td>
				<td>${item.insured}</td>
				<td>&nbsp;</td>
				<td>
					&nbsp;
				</td>
				<td>&nbsp;</td>
				<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>