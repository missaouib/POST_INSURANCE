<%@page import="com.gdpost.web.entity.main.Organization"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageHeader">
	<form method="post" id="cveForm" action="${contextPath }/common/lookup2BQIssuesDefine" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="searchBar">
	&nbsp;
	</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" layoutH="118" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th orderfield="loginName">登录名</th>
				<th orderfield="realName">姓名名</th>
				<th>所属机构</th>
				<th>查找带回</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${userlist}" var="user">
			<tr>
				<td>${user.loginName}</td>
				<td>${user.realName}</td>
				<td>${user.organization.name}</td>
				<td>
					<a class="btnSelect" href="javascript:$.bringBack({id:'${user.id }', realName:'${user.realName }'})" title="查找带回">选择</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
	<div class="formBar">  
        <ul>  
            <li><div class="button"><div class="buttonContent"><button class="close" type="button" onclick="$.bringBack({id:'', realName:''})">清空</button></div></div></li>
            <li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>  
        </ul>  
    </div>
</div>