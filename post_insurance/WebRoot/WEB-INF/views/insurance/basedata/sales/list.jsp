<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/basedata/sales/list" page="${page }">
	<input type="hidden" name="search_LIKE_phone" value="${param.search_LIKE_phone }"/>
	<input type="hidden" name="search_LIKE_saleName" value="${param.search_LIKE_saleName }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/basedata/sales/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
			<tr>
				<td>姓名：
					<input type="text" name="search_LIKE_salesName" value="${param.search_LIKE_salesName }"/>
				</td>
				<td>电话：
					<input type="text" name="search_LIKE_phone" value="${param.search_LIKE_phone }"/>
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
			<shiro:hasPermission name="sales:save">
				<li><a class="add" target="dialog" rel="sales_add" mask="true" width="530" height="330" href="${contextPath }/basedata/sales/create"><span>添加销售人员</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="sales:edit">
				<li><a class="edit" target="dialog" rel="sales_edit" mask="true" width="530" height="330" href="${contextPath }/basedata/sales/update/{slt_uid}"><span>编辑销售人员</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="sales:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/basedata/sales/delete" title="确认要删除?"><span>删除销售人员</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th width="100">机构代码</th>
				<th width="100">机构名称</th>
				<th width="100">姓名</th>
				<th width="100">电话</th>
				<th width="100">状态</th>
				<th width="100">网点</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${basedata}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.organCode}</td>
				<td>${item.organName}</td>
				<td>${item.salesName}</td>
				<td>${item.phone}</td>
				<td>
					<c:choose>  
					    <c:when test="${item.status == 1}">  
					                    有效
					    </c:when>  
					   <c:otherwise>  
					      失效 
					    </c:otherwise>  
					</c:choose>
				</td>
				<td>${item.bankName}</td>
			</tr>	
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>