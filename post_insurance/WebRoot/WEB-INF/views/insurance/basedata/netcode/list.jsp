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
			<table class="searchContent">
			<tr>
				<td>邮保通网点代码：
					<input type="text" name="search_LIKE_ybtCode" value="${param.search_LIKE_ybtCode }"/>
				</td>
				<td>银行网点编码：
					<input type="text" name="search_LIKE_bankCode" value="${param.search_LIKE_bankCode }"/>
				</td>
				<td>网点名称：
					<input type="text" name="search_LIKE_name" value="${param.search_LIKE_name }"/>
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
			<shiro:hasPermission name="BankCode:save">
				<li><a class="add" target="dialog" rel="lookup2organization_add" mask="true" width="530" height="330" href="${contextPath }/basedata/bankCode/create"><span>添加网点对应</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="BankCode:edit">
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="530" height="330" href="${contextPath }/basedata/bankCode/update/{slt_uid}"><span>编辑网点对应</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="BankCode:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/basedata/bankCode/delete" title="确认要删除?"><span>删除网点对应</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th>邮保通代码</th>
				<th>中邮网点代码</th>
				<th>银行网点代码</th>
				<th>网点名称</th>
				<th>管理机构</th>
				<th>网点属性</th>
				<th>行政级别</th>
				<th>网点全称</th>
				<th>城市</th>
				<th>县区</th>
				<th>机构属性</th>
				<th>地域属性</th>
				<th>入网情况</th>
				<th>旧名称</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${basedata}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.ybtCode}</td>
				<td>${item.cpiCode}</td>
				<td>${item.selBankCode}</td>
				<td>${item.name}</td>
				<td>${item.organization.shortName}</td>
				<td>
					<c:choose>  
					    <c:when test="${item.netFlag == 1}">  
					                    邮政网点
					    </c:when>  
					   <c:otherwise>  
					      银行网点  
					    </c:otherwise>  
					</c:choose>
				</td>
				<td>${item.ranks}</td>
				<td>${item.fullName}</td>
				<td>${item.city}</td>
				<td>${item.area}</td>
				<td>${item.attr}</td>
				<td>${item.addrAttr}</td>
				<td>${item.flag}</td>
				<td>${item.oldName}</td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>