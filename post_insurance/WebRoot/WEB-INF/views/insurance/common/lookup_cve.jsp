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
				<th orderfield="loginName">常见错误</th>
				<th width="80">查找带回</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${cvelistList}" var="cve">
			<tr>
				<td>${cve.errorCode}</td>
				<td>
					<a class="btnSelect" href="javascript:$.bringBack({errorCode:'${cve.errorCode}'})" title="查找带回">选择</a>
				</td>
				<td>
					<input type="checkbox" name="cve" value="{errorCode:'${cve.errorCode}'}" /> 
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
	<div class="formBar">  
        <ul>  
            <li><div class="buttonActive"><div class="buttonContent"><button type="button" multLookup="cve" >选择</button></div></div></li>  
            <li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>  
        </ul>  
    </div>
</div>