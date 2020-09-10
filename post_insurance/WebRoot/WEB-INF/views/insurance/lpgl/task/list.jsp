<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/lpgl/task/list" page="${page }">
	<input type="hidden" name="search_LIKE_insured" value="${param.search_LIKE_insured }"/>
	<input type="hidden" name="organization.orgCode" value="${org_code }"/>
	<input type="hidden" name="organization.name" value="${org_name }"/>
	<input type="hidden" name="checkDate2" value="${checkDate2 }"/>
	<input type="hidden" name="checkDate1" value="${checkDate1 }"/>
	<input type="hidden" name="checkStatus" value="${checkStatus }"/>
	<input type="hidden" name="taskLong" value="${taskLong }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/lpgl/task/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						出险人：<input type="text" id="insured" style="width: 100px;" name="search_LIKE_insured" value="${param.search_LIKE_insured }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="task.checkStatus" id="list_taskStatus" class="combox">
							<form:option value=""> -- </form:option>
							<form:option value="调查中">调查中</form:option>
							<form:option value="调查完成">调查完成</form:option>
						</form:select>
					</td>
					<td>
						<label>调查时效：</label>
						<form:select path="task.taskLong" id="list_taskLong" class="combox">
							<form:option value=""> -- </form:option>
							<form:option value="3">3天内</form:option>
							<form:option value="5">5天内</form:option>
							<form:option value="7">7天内</form:option>
							<form:option value="9">7天以上</form:option>
						</form:select>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="organization.orgCode" id="task_orgCode" type="hidden" value="${org_code }"/>
					<input class="validate[required] required" name="organization.name" id="task_orgName" type="text" readonly="readonly" style="width: 140px;" value="${org_name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="organization" title="选择机构" width="400">查找带回</a>
					</td>
				</tr>
				<tr>
					<td>
						<label>调查日期：</label>
						<input type="text" name="checkDate1" id="taskDate1" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${checkDate1 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>调查日期：</label>
						<input type="text" name="checkDate2" id="taskDate2" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${checkDate2 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>日期标记：</label>
						<label><input type="radio" class="radio" name="checkDateFlag" value="0" ${checkDateFlag eq "0"?"checked=\"checked\"":"" }/>调查发起
						<input type="radio" class="radio" name="checkDateFlag" value="1" ${checkDateFlag eq "1"?"checked=\"checked\"":"" }/>调查止期</label>
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
			<shiro:hasPermission name="Settlement:save">
				<li><a class="add" target="dialog" rel="lookup2organization_add" mask="true" width="850" height="650" href="${contextPath }/lpgl/task/create"><span>添加</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Settlement:edit">
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="650" href="${contextPath }/lpgl/task/update/{slt_uid}"><span>编辑</span></a></li>
				<li><a class="edit" target="ajaxTodo" title="确认已完成？" href="${contextPath }/lpgl/task/done/{slt_uid}"><span>完成</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Settlement:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/lpgl/task/delete" title="确认要删除?"><span>删除</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Settlement:view">
				<li class="line">line</li>
				<li><a class="icon" target="_blank" href="${contextPath }/lpgl/task/toXls?taskLong=${taskLong }&search_LIKE_insured=${search_LIKE_insured }&checkDate2=${checkDate2}&checkDate1=${checkDate1}&checkStatus=${checkStatus}&organization.orgCode=${org_code}&organization.name=${org_name}&checkDateFlag=${checkDateFlag}"><span>导出Excel</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>序号</th>	
				<th>险种类别</th>	
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">机构名称</th>
				<th>保单号</th>
				<th>出险人</th>
				<!-- <th>调查起期</th>
				<th>调查完成</th> -->
				<th>调查时效</th>
				<th orderField=checker class="${page.orderField eq 'checker' ? page.orderDirection : ''}">调查人</th>
				<th>待办状态</th>
				<th>操作</th>	
				<th>核心发起日期</th>
				<th>追踪要求</th>
				<th>剩余追踪天数</th>
				<th>查看日志</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${users}" varStatus="idx">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${idx.index+1 }</td>
				<td>
					 ${item.policyType eq 0?"个险":"团险"}&nbsp;&nbsp;（<a target="dialog" rel="lookup2taskpolicyType" mask="true" width="350" height="200" href="${contextPath }/lpgl/task/type/${item.id}">设</a>） &nbsp;&nbsp;
				</td>
				<td>${item.organization.shortName}</td>
				<td>${item.policy.policyNo}</td>
				<td>${item.insured}</td>
				<%-- <td><fmt:formatDate value="${item.checkStartDate}" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.checkEndDate}" pattern="yyyy-MM-dd"/></td> --%>
				<td>${item.limitation }</td>
				<td>${item.checker}</td>
				<td>${item.checkStatus}</td>
				<td>
					 <a target="dialog" rel="lookup2taskpolicyType" mask="true" max="true" width="800" height="600" href="${contextPath }/lpgl/task/update/${item.id}"><div style="color: blue;vertical-align:middle;font-weight:normal;">录入</div></a>&nbsp;&nbsp;
				</td>
				<td>
					 <fmt:formatDate value="${item.hxDate}" pattern="yyyy-MM-dd"/>&nbsp;&nbsp;（<a target="dialog" rel="lookup2taskpolicyType" mask="true" width="350" height="200" href="${contextPath }/lpgl/task/HXDay/${item.id}">设</a>） &nbsp;&nbsp;
				</td>
				<td>
				<c:choose>
					<c:when test="${not empty item.needFeedBack and item.needFeedBack eq '待反馈'}">
					<div style="color: red;vertical-align:middle;font-weight:normal;">待反馈</div>
					</c:when>
					<c:when test="${not empty item.needFeedBack and item.needFeedBack eq '已反馈'}">
					<div style="color: blue;vertical-align:middle;font-weight:normal;">已反馈</div>
					</c:when>
					<c:otherwise>
						&nbsp;
					</c:otherwise>
				</c:choose>
				</td>
				<td>${item.lessFeedBack }</td>
				<td>
					<a target="dialog" mask="true" width="750" height="430" href="${contextPath }/lpgl/task/log/${item.id}">查</a>
				</td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>