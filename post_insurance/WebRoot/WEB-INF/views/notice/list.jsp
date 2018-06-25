<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/notice/list" page="${page }">
	<input type="hidden" name="search_LIKE_noticeTitle" value="${param.search_LIKE_noticeTitle }"/>
	<input type="hidden" name="sendDate1" value="${sendDate1 }"/>
	<input type="hidden" name="sendDate2" value="${sendDate2 }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/notice/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
			<tr>
				<td>标题：
					<input type="text" name="search_LIKE_noticeTitle" value="${param.search_LIKE_noticeTitle }"/>
				</td>
				<td>
					<label>开始日期：</label>
					<input type="text" name="sendDate1" id="fqSendDate1" style="width: 80px;" class="date validate[required] required" dateFmt="yyyy-MM-dd" readonly="true" value="${sendDate1 }"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td>
					<label>结束日期：</label>
					<input type="text" name="sendDate2" id="fqSendDate2" style="width: 80px;" class="date validate[required] required" dateFmt="yyyy-MM-dd" readonly="true" value="${sendDate2 }"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
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
			<li><a iconClass="magnifier" target="dialog" rel="lookup2organization_add" mask="true" width="630" height="450" href="${contextPath }/notice/view/{slt_uid}"><span>查看</span></a></li>
			<shiro:hasPermission name="Notice:save">
				<li><a class="add" target="dialog" rel="lookup2organization_add" mask="true" width="630" height="430" href="${contextPath }/notice/create"><span>添加发布</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Notice:edit">
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="630" height="330" href="${contextPath }/notice/update/{slt_uid}"><span>修改</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Notice:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/notice/delete" title="确认要删除?"><span>删除</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>标题</th>		
				<th>接受者</th>
				<th>接收角色</th>
				<th>接收机构</th>
				<th>内容</th>
				<th>发布日期</th>
				<th>发布者</th>
				<th>失效日期</th>
				<th>附件</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${basedata}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.noticeTitle}</td>
				<td>${item.user.realname}</td>
				<td>${item.role.name}</td>
				<td>${item.organization.shortName}</td>
				<td>${item.noticeContent}</td>
				<td>${item.sendDate}</td>
				<td>${item.sender.realname}</td>
				<td><fmt:formatDate value="${item.invalidDate}" pattern="yyyy-MM-dd"/></td>
				<td>
				<c:forEach var="subList" items="${item.noticeAtts}">
		            <a href="${subList.attrLink}">${subList.attrLink}</a>
		        </c:forEach>
				</td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>