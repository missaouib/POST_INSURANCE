<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/management/uploadstatus/list" page="${page }">
	<input type="hidden" name="search_EQ_ny" value="${param.search_EQ_ny }"/>
	<input type="hidden" name="search_EQ_tblMember.id" value="${search_EQ_tblMember_id }"/>
	<input type="hidden" name="search_EQ_tblMember.memberName" value="${search_EQ_tblMember_memberName }"/>	
</dwz:paginationForm>

<form method="post" action="${contextPath }/management/uploadstatus/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li style="width:200px;">
					<label style="width: 40px;">年月：</label>
					<select name="search_EQ_ny" id="search_EQ_ny" name="ny" style="width:120px;">
						<c:forEach var="item" items="${ny}">
						<option value="${item}" ${item == param.search_EQ_ny ? 'selected="selected"' : ''}>${item}</option>
						</c:forEach>
					</select>
				</li>
				<li style="width:180px;">
					<label style="width: 40px;">连锁：</label>
					<input name="search_EQ_tblMember.id" value="${search_EQ_tblMember_id }" type="text" style="display:none;"/>
					<input class="validate[required] required" name="search_EQ_tblMember.memberName" value="${search_EQ_tblMember_memberName}" type="text" readonly="readonly" style="width: 120px;"/>
				</li>
				<li style="width:40px;">
					<label style="width: 40px;"><a class="btnLook" href="${contextPath }/management/uploadstatus/lookup2member" lookupGroup="search_EQ_tblMember" title="关联连锁">查找带回</a></label>
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
			<shiro:hasPermission name="uploadstatus:delete">
				<li><a iconClass="shield_delete" target="selectedTodo" rel="ids" href="${contextPath}/management/uploadstatus/delete" title="删除数据操作不可恢复，确认要删除数据吗?"><span>删除数据</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="80px">年月</th>
				<th width="120px">连锁</th>
				<th width="120px">上传状态</th>
				<th width="120px">上传时间</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${items}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.ny}</td>
				<td>${item.tblMember.memberName}</td>
				<td>${item.status==0?"已上传":""}</td>
				<td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${item.memo}</td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>
