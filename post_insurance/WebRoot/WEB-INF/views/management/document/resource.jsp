<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib prefix="dwz" uri="http://www./dwz"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<dwz:paginationForm action="${contextPath }/management/document/resource" page="${page }" onsubmit="return divSearch(this, 'pageResourceList');">
	<input type="hidden" name="search_LIKE_name" value="${param.search_LIKE_name }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/management/document/resource" onsubmit="return divSearch(this, 'pageResourceList');">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>资源名称：</label>
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
			<shiro:hasPermission name="Push:save">
				<li><a iconClass="page_white_get" target="selectedTodo" rel="ids" onclick="var treeIds=GetCheckedAll();if(treeIds.length==0){alert('请选择推送会员。');event.cancelBubble=true;event.returnValue=false;return false;}this.href='${contextPath }/management/document/setting/' + treeIds;" href="${contextPath }/management/document/setting" title="确认要推送选定资源?"><span>推送</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%" rel="pageResourceList">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>报告类型</th>
				<th >名称</th>
				<th width="60">文件类型</th>
				<th width="80">长度</th>
				<th width="130">上传时间</th>
				<th width="40">下载</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${resources}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.storeType.tostring() == "file" ? "药品零售市场研究报告" : (item.storeType.tostring() == "file2" ? "品类管理报告" : (item.storeType.tostring() == "file3" ? "消费者购药U&A研究" : "其他")) }</td>
				<td>${item.name}</td>
				<td>${item.type}</td>
				<td>${item.size}</td>
				<td><fmt:formatDate value="${item.uploadTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<shiro:hasPermission name="Resource:download">
						<a iconClass="page_white_put" target="_blank" href="${contextPath }/management/component/resource/download/${item.storeType }/${item.uuid}" title="下载">下载</a>
					</shiro:hasPermission>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }" rel="pageResourceList" onchange="navTabPageBreak({numPerPage:this.value}, 'pageResourceList')"/>
</div>