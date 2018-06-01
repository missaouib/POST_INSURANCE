<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<form method="post" action="${contextPath }/dagl/scan/stat" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
			<tr>
				<td><label>月份</label>
					<form:select path="dsm.month" id="month" class="combox">
						<form:options items="${months }"/>
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
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th>序号</th>
				<th>机构名称</th>
				<th>承保件数</th>
				<th>未扫描件数</th>
				<th>扫描进度</th>
				<th>重点关注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${statData}" varStatus="idx">
			<tr>
				<td>${idx.index+1}</td>
				<td>${item.orgName}</td>
				<td>${item.allDoc}</td>
				<td>${item.sumDoc}</td>
				<td><fmt:formatNumber value="${item.percent}" pattern="#,###.##" />%</td>
				<td>${item.remark}</td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
</div>