<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/basedata/bankCode/list" page="${page }">
	<input type="hidden" name="search_LIKE_ybtCode" value="${param.search_LIKE_ybtCode }"/>
	<input type="hidden" name="search_LIKE_bankCode" value="${param.search_LIKE_bankCode }"/>
	<input type="hidden" name="search_LIKE_name" value="${param.search_LIKE_name }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/basedata/bankCode/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>邮保通网点代码：</label>
					<input type="text" name="search_LIKE_ybtCode" value="${param.search_LIKE_ybtCode }"/>
				</li>
				<li>
					<label>银行网点编码：</label>
					<input type="text" name="search_LIKE_bankCode" value="${param.search_LIKE_bankCode }"/>
				</li>
				<li>
					<label>网点名称：</label>
					<input type="text" name="search_LIKE_name" value="${param.search_LIKE_name }"/>
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
			<shiro:hasPermission name="BankCode:save">
				<li><a iconClass="user_add" target="dialog" rel="lookup2organization_add" mask="true" width="530" height="330" href="${contextPath }/basedata/bankCode/create"><span>添加网点对应</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="BankCode:edit">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="530" height="330" href="${contextPath }/basedata/bankCode/update/{slt_uid}"><span>编辑网点对应</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="BankCode:delete">
				<li><a iconClass="user_delete" target="selectedTodo" rel="ids" href="${contextPath }/basedata/bankCode/delete" title="确认要删除?"><span>删除网点对应</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th width="100">邮保通代码</th>
				<th width="100">中邮网点代码</th>
				<th width="100">银行网点代码</th>
				<th width="100">网点名称</th>
				<th width="100">管理机构</th>
				<th width="100">网点属性</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${basedata}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.ybtCode}</td>
				<td>${item.cpiCode}</td>
				<td>${item.bankCode}</td>
				<td>${item.name}</td>
				<td>${item.organization.name}</td>
				<td>
					<c:choose>  
					    <c:when test="${item.flag == 1}">  
					                    邮政网点
					    </c:when>  
					   <c:otherwise>  
					      银行网点  
					    </c:otherwise>  
					</c:choose>
				</td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>