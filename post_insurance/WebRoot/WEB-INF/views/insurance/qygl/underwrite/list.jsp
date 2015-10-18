<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/qygl/underwrite/list" page="${page }">
	<input type="hidden" name="search_LIKE_formNo" value="${search_LIKE_formNo }"/>
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="name" value="${name }"/>
	<input type="hidden" name="search_LTE_ybtDate" value="${param.search_LTE_ybtDate }"/>
	<input type="hidden" name="search_GTE_ybtDate" value="${param.search_GTE_ybtDate }"/>
</dwz:paginationForm>

<form method="post" id="hfForm" action="${contextPath }/qygl/underwrite/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>保单号：</label>
					<input type="text" id="uwFormNo" style="width: 100px;" name="search_LIKE_formNo" value="${param.search_LIKE_formNo }"/>
				</li>
				<li>
					<label>所属机构：</label>
					<input name="orgCode" id="uw_orgCode" type="hidden" value="${orgCode }"/>
					<input class="validate[required] required" name="name" id="uw_orgName" type="text" readonly="readonly" style="width: 120px;" value="${name }"/>
					<a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
				</li>				
			</ul>
			<ul class="searchContent">
				<li>
					<label>邮保通日期起：</label>
					<input type="text" name="search_GTE_ybtDate" id="uwDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_ybtDate }"/><a class="inputDateButton" href="javascript:;">选</a>
				</li>
				<li>
					<label>邮保通日期止：</label>
					<input type="text" name="search_LTE_ybtDate" id="uwDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_ybtDate }"/><a class="inputDateButton" href="javascript:;">选</a>
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
			<shiro:hasPermission name="UnderWrite:view">
				<li><a iconClass="user_edit" target="dialog" rel="underwrite_edit" mask="true" width="800" height="440" href="${contextPath }/qygl/underwrite/view/{slt_uid}"><span>查看</span></a></li>
				<li><a class="icon" target="_blank" href="${contextPath }/qygl/underwrite/toXls?search_LIKE_formNo=${param.search_LIKE_formNo }&orgCode=${orgCode }&search_LTE_ybtDate=${param.search_LTE_ybtDate }&search_GTE_ybtDate=${param.search_LTE_ybtDate }"><span>导出Excel</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="UnderWrite:edit">
			<li class="line">line</li>
				<li><a iconClass="user_edit" target="dialog" rel="underwrite_edit" mask="true" width="800" height="440" href="${contextPath }/qygl/underwrite/update/{slt_uid}"><span>更新</span></a></li>
				<li><a iconClass="user_go" href="${contextPath}/qygl/underwrite/signDateUpdate/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>回销登记</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="UnderWrite:provEdit">
			<shiro:hasPermission name="UnderWrite:delete">
				<li><a iconClass="user_delete" target="selectedTodo" rel="ids" href="${contextPath }/qygl/underwrite/delete" title="确认要删除?"><span>删除</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
				<li><a iconClass="user_edit" target="dialog" rel="underwrite_edit" mask="true" width="800" height="440" href="${contextPath }/qygl/underwrite/create"><span>新建</span></a></li>
				<li><a iconClass="user_go" href="${contextPath}/qygl/underwrite/provSend/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>省分寄出</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="UnderWrite:cityEdit">
			<li class="line">line</li>
				<li><a iconClass="user_go" href="${contextPath}/qygl/underwrite/cityRec/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>地市接收</span></a></li>
				<li><a iconClass="user_go" href="${contextPath}/qygl/underwrite/citySend/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>地市寄出</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="UnderWrite:areaEdit">
			<li class="line">line</li>
				<li><a iconClass="user_go" href="${contextPath}/qygl/underwrite/areaRec/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>县区接收</span></a></li>
				<li><a iconClass="user_go" href="${contextPath}/qygl/underwrite/areaSend/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>县区寄出</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="icon" target="dialog" href="${contextPath }/qygl/underwrite/help" mask="true" width="530" height="430"><span>功能说明</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="160" width="130%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">市县机构</th>
				<th orderField=formNo class="${page.orderField eq 'formNo' ? page.orderDirection : ''}">投保单号</th>
				<th orderField=policyNo class="${page.orderField eq 'policyNo' ? page.orderDirection : ''}">保单号</th>
				<th>投保人</th>
				<th>被保人</th>
				<th>关系</th>
				<th orderField=prd.prdName class="${page.orderField eq 'prd.prdName' ? page.orderDirection : ''}">产品</th>
				<th orderField=policyFee class="${page.orderField eq 'policyFee' ? page.orderDirection : ''}">保费</th>
				<th orderField=ybtDate class="${page.orderField eq 'ybtDate' ? page.orderDirection : ''}">邮保通录入时间</th>
				<th>核心录入时间</th>
				<th>复核时间</th>
				<th>核保日期</th>
				<th>签单日期</th>
				<th>省分收到合同日</th>
				<th>合同签收日期</th>
				<th>回执录入日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${underwrites}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${fn:replace(item.organization.name,'邮政局中邮保险局','')}</td>
				<td>${item.formNo}</td>
				<td>${item.policyNo}</td>
				<td>${item.holder}</td>
				<td>${item.insured}</td>
				<td>${item.relation}</td>
				<td>${item.prd.prdName}</td>
				<td>${item.policyFee}</td>
				<td><fmt:formatDate value="${item.ybtDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.sysDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.checkDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.underwriteDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.signDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.provReceiveDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.clientReceiveDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.signInputDate }" pattern="yyyy-MM-dd"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>