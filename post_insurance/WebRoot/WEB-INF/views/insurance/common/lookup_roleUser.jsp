<%@page import="com.gdpost.web.entity.main.Organization"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }/common/lookup4RoleUser" page="${page }" onsubmit="return dwzSearch(this, 'dialog');">
<input type="hidden" name="role" value="${role }"/>
<input type="hidden" name="roleId" value="${roleId }"/>
<input type="hidden" name="realname" value="${param.realname }"/>
</dwz:paginationForm>

<div class="pageHeader">
	<form method="post" id="cveForm" action="${contextPath }/common/lookup4RoleUser" onsubmit="return dwzSearch(this, 'dialog');">
	<input type="hidden" name="role" value="${role }"/>
	<input type="hidden" name="roleId" value="${roleId }"/>
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>用户名</label>
				<input type="text" name="realname" value="${param.realname }" class="textInput" >
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
	<table class="table" layoutH="138" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th orderfield="username">登录名</th>
				<th orderfield="realName">姓名名</th>
				<th>所属机构</th>
				<th>查找带回</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${userlist}" var="user">
			<tr>
				<td>${user.username}</td>
				<td>${user.realname}</td>
				<td>${user.organization.name}</td>
				<td>
					<a class="btnSelect" href="javascript:$.bringBack({id:'${user.id }', realname:'${user.realname }'})" title="查找带回">选择</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<!-- 分页 -->
	<dwz:pagination page="${page }" targetType="dialog"/>
	<div class="formBar">  
        <ul>  
            <li><div class="button"><div class="buttonContent"><button class="close" type="button" onclick="$.bringBack({id:'', realname:''})">清空</button></div></div></li>
            <li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>  
        </ul>  
    </div>
</div>