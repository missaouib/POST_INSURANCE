<%@page import="com.gdpost.web.entity.main.Organization"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }/common/lookup4Policy" page="${page }" onsubmit="return dwzSearch(this, 'dialog');">
<input type="hidden" name="insuredCardNum" value="${insuredCardNum }"/>
</dwz:paginationForm>

<div class="pageHeader">
	<form method="post" id="cveForm" action="${contextPath }/common/lookup4Policy" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>被保人证件：</label>
				<input type="text" name="insuredCardNum" value="${insuredCardNum }" class="textInput" >（完整号码）
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
				<th>保单号</th>
				<th>机构</th>
				<th>被保险人</th>
				<th>产品</th>
				<th>保单状态（参考）</th>
				<th>查找带回</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${policylist}" var="policy">
			<tr>
				<td>${policy.policyNo}</td>
				<td>${policy.organization.shortName}</td>
				<td>${policy.insured}</td>
				<td>${policy.prodName}</td>
				<td>${policy.status}</td>
				<td>
					<a class="btnSelect" href="javascript:$.bringBack({id:'${policy.id }', policyNo:'${policy.policyNo }'})" title="查找带回">选择</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<!-- 分页 -->
	<dwz:pagination page="${page }" targetType="dialog"/>
	<div class="formBar">  
        <ul>  
            <li><div class="button"><div class="buttonContent"><button class="close" type="button" onclick="$.bringBack({insured:'', policyNo:''})">清空</button></div></div></li>
            <li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>  
        </ul>  
    </div>
</div>