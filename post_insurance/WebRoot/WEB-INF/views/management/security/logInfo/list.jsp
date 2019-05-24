<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/management/security/logInfo/list" page="${page }">
	<input type="hidden" name="search_EQ_username" value="${param.search_LIKE_username }"/>
	<input type="hidden" name="search_EQ_ipAddress" value="${param.search_LIKE_ipAddress }"/>
	<input type="hidden" name="search_EQ_logLevel" value="${param.search_EQ_logLevel }"/>
	<input type="hidden" name="search_GTE_createTime" value="${param.search_GTE_createTime}"/>
	<input type="hidden" name="search_LTE_createTime" value="${param.search_LTE_createTime}"/>
	<input type="hidden" name="search_LIKE_message" value="${param.search_LIKE_message}"/>
	<input type="hidden" name="module" value="${module}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath}/management/security/logInfo/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
				<td>
					<label style="width: 100px;">登录名称：</label>
					<input type="text" name="search_EQ_username" value="${param.search_LIKE_username }"/>
					</td>
				<td>
					<label style="width: 100px;">登录IP：</label>
					<input type="text" name="search_EQ_ipAddress" value="${param.search_LIKE_ipAddress }"/>
				</td>
				<td>
					<label style="width: 100px;">日志等级：</label>
					<select name="search_EQ_logLevel">
						<option value="">所有</option>
						<c:forEach var="logLevel" items="${logLevels }"> 
							<option value="${logLevel}" ${param.search_EQ_logLevel == logLevel ? 'selected="selected"':'' }>${logLevel}</option>
						</c:forEach>
					</select>
				</td>
				<td><label style="width: 100px;">模块：</label>
					<form:select path="logInfo.module" id="logModule" class="combox">
						<form:option value=""> -- -- </form:option>
						<form:options items="${modules }" itemLabel="desc" itemValue="desc"/>
					</form:select>
				</td>
			</tr>
			<tr>
				<td>
					<label style="width: 100px;">关键字：</label>
					<input type="text" name="search_LIKE_message" value="${param.search_LIKE_message }"/>
				</td>
				<td>
					<label style="width: 100px;">日志开始时间：</label>
					<input type="text" name="search_GTE_createTime" class="date" readonly="readonly" style="float:left;" value="${param.search_GTE_createTime}"/>
					<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
				</td>			
				<td>
					<label style="width: 100px;">日志结束时间：</label>
					<input type="text" name="search_LTE_createTime" class="date" readonly="readonly" style="float:left;" value="${param.search_LTE_createTime}"/>
					<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
				</td>
				<td>&nbsp;</td>							
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
			<shiro:hasPermission name="LogInfo:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath}/management/security/logInfo/delete" title="确认要删除?"><span>删除日志</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="162" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="100">登录名称</th>
				<th width="100">登录IP</th>
				<th width="100">相关模块</th>
				<th width="100" orderField="logLevel" class="${page.orderField eq 'logLevel' ? page.orderDirection : ''}">日志等级</th>
				<th >日志内容</th>
				<th width="130" orderField="createTime" class="${page.orderField eq 'createTime' ? page.orderDirection : ''}">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${logInfos}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.username}</td>
				<td>${item.ipAddress}</td>
				<td>${item.module}</td>
				<td>${item.logLevel}</td>
				<td>${item.message}</td>
				<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>