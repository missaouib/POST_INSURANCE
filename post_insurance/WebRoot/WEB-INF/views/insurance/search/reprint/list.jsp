<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/client/listPolicyReprintDtl" page="${page }">
	<input type="hidden" name="search_LIKE_policyNo" value="${param.search_LIKE_policyNo }"/>
	<input type="hidden" name="search_LIKE_formNo" value="${param.search_LIKE_formNo }"/>
	<input type="hidden" name="search_LIKE_emsNo" value="${param.search_LIKE_emsNo }"/>
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="name" value="${name }"/>
	<input type="hidden" name="plFlag" value="${plFlag }"/>
	<input type="hidden" name="search_LTE_printDate" value="${param.search_LTE_printDate }"/>
	<input type="hidden" name="search_GTE_printDate" value="${param.search_GTE_printDate }"/>
</dwz:paginationForm>

<form method="post" id="payqyForm" action="${contextPath }/client/listPolicyReprintDtl" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						保单号：<input type="text" style="width: 100px;" id="repno" name="search_LIKE_policyNo" value="${param.search_LIKE_policyNo }"/>
					</td>
					<td>
						快递单号：<input type="text" style="width: 100px;" id="reemsno" name="search_LIKE_emsNo" value="${param.search_LIKE_emsNo }"/>
					</td>
					<td>
						<label>标记：</label>
						<select name="plFlag" id="policyFlag" class="combox">
							<option value=""> -- -- </option>
							<option value="承保新单" <%if(request.getAttribute("plFlag") != null && request.getAttribute("plFlag").equals("承保新单")){%>selected<%} %>>承保新单</option>
							<option value="保全补发" <%if(request.getAttribute("plFlag") != null && request.getAttribute("plFlag").equals("保全补发")){%>selected<%} %>>保全补发</option>
						</select>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="orgCode" id="payqy_forgCode" type="hidden" value="${orgCode }"/>
						<input class="validate[required] required" name="name" id="payqy_forgName" type="text" readonly="readonly" style="width: 100px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
				</tr>
				<tr>
				<td>
					保险合同组号：<input type="text" style="width: 100px;" id="repno" name="search_LIKE_formNo" value="${param.search_LIKE_formNo }"/>
				</td>
				<td>
				<label>打印日期：</label>
				<input type="text" name="search_GTE_printDate" id="prDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_printDate }"/><a class="inputDateButton" href="javascript:;">选</a>
				</td>
				<td>
				<label>打印日期：</label>
				<input type="text" name="search_LTE_printDate" id="prDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_printDate }"/><a class="inputDateButton" href="javascript:;">选</a>
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
		<li><a class="icon" target="_blank" href="${contextPath }/client/listPolicyReprintDtl/toXls?search_LIKE_policyNo=${param.search_LIKE_policyNo }&orgCode=${orgCode }&search_LIKE_formNo=${param.search_LIKE_formNo}&search_LIKE_emsNo=${param.search_LIKE_emsNo}"><span>导出Excel</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th orderField=orgCode class="${page.orderField eq 'orgCode' ? page.orderDirection : ''}">机构号</th>
				<th orderField=formNo class="${page.orderField eq 'formNo' ? page.orderDirection : ''}">保险合同组号</th>
				<th orderField=policyNo class="${page.orderField eq 'policyNo' ? page.orderDirection : ''}">保单号</th>
				<th>状态</th>
				<th>快递单号</th>
				<th orderField=printDate class="${page.orderField eq 'printDate' ? page.orderDirection : ''}">寄出日期</th>
				<th>标记</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${policies}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.orgCode}</td>
				<td>${item.formNo}</td>
				<td>${item.policyNo}</td>
				<td>${item.status}</td>
				<td><a target="_blank" href="https://m.kuaidi100.com/index_all.html?type=ems%20&postid=${item.emsNo}"><div style="color: blue;vertical-align:middle;font-weight:normal;">${item.emsNo}</div></a></td>
				<td><fmt:formatDate value="${item.printDate }" pattern="yyyy-MM-dd"/></td>
				<td>
					<c:choose>
					<c:when test="${item.plFlag eq '保全补发'}">
						合同补发
					</c:when>
					<c:otherwise>
						新单打印
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>