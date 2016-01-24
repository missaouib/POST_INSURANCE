<%@page import="com.gdpost.web.entity.main.Organization"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }/management/security/user/lookupUser" page="${page }" onsubmit="return dwzSearch(this, 'dialog');">
</dwz:paginationForm>

<div class="pageHeader">
	<form method="post" action="${contextPath }/management/security/user/lookupUser" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>用户名</label>
				<input type="text" name="search_LIKE_realname" value="${param.search_LIKE_realName }" class="textInput" >
				<!-- 
				<label>组织:</label>
				<input type="hidden" name="userId"/>
				<input name="name" type="text" value="${param.name }" postField="search_LIKE_name" suggestFields="name" suggestUrl="/management/security/organization/lookup2org" lookupGroup=""/>(输入查找)
				 -->
			</li>
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" layoutH="118" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th orderfield="loginName">用户登录名</th>
				<th orderfield="userName">所属组织</th>
				<th width="80">查找带回</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${userList}" var="user">
			<tr>
				<td>${user.username}</td>
				<td>${user.organization.name}</td>
				<td>
					<a class="btnSelect" href="javascript:$.bringBack({userId:'${user.id}', username:'${user.username}'})" title="查找带回">选择</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<!-- 分页 -->
	<dwz:pagination page="${page }" targetType="dialog"/>
</div>