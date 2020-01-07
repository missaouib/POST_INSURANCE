<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<jsp:useBean id="now" class="java.util.Date" scope="page"/>

<dwz:paginationForm action="${contextPath }/qygl/ybt/list" page="${page }">
	<input type="hidden" name="search_LIKE_formNo" value="${search_LIKE_formNo }"/>
	<input type="hidden" name="search_LIKE_holder" value="${search_LIKE_holder }"/>
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="name" value="${name }"/>
	<input type="hidden" name="date2" value="${date2 }"/>
	<input type="hidden" name="date1" value="${date1 }"/>
</dwz:paginationForm>

<form method="post" id="hfForm" action="${contextPath }/qygl/ybt/list" onsubmit="return navTabSearch(this)">
<input type="hidden" name="status_flag" id="status_flag" value="${status_flag }"/>
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
			<tr>
				<td>投保单号：
					<input type="text" id="uwFormNo" style="width: 100px;" name="search_LIKE_formNo" value="${param.search_LIKE_formNo }"/>
				</td>
				<td>投保人：
					<input type="text" id="uwHolder" style="width: 100px;" name="search_LIKE_holder" value="${param.search_LIKE_holder }"/>
				</td>
				<td>
					<label>机构：</label>
					<input name="orgCode" id="uw_orgCode" type="hidden" value="${orgCode }"/>
					<input class="validate[required] required" name="name" id="uw_orgName" type="text" readonly="readonly" style="width: 120px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
				</td>
			</tr>
			<tr>
				<td>
					<label>录入日期起：</label>
					<input type="text" name="date1" id="ybtDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${date1 }"/><a class="inputDateButton" href="javascript:;">选</a>
				</td>
				<td>
					<label>录入日期止：</label>
					<input type="text" name="date2" id="ybtDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${date2 }"/><a class="inputDateButton" href="javascript:;">选</a>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
			</table>
			<div class="subBar">
				<ul>						
					<li><div class="button"><div class="buttonContent"><button type="submit" onclick="javascript:if(confirm('已上传签单未扫描清单？')) return true;">搜索</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="YBT:view">
			<li class="line">line</li>
			<li><a iconClass="magnifier" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/client/view/byPolicyNo/{slt_uid}"><span>查看保单</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" target="_blank" target="dwzExport" href="${contextPath }/qygl/ybt/list/toXls?orgCode=${orgCode }&date1=${date1 }&date2=${date2 }"><span>导出</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th>市县机构</th>
				<th>投保单号</th>
				<th>保单号</th>
				<th>签单日期</th>
				<th>投保人</th>
				<th>产品</th>
				<th>保费</th>
				<th>是否有差错</th>
				<th>是否扫描</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${ybtPolicys}">
			<tr target="slt_uid" rel="${item.policyNo}">
				<td><input name="ids" value="${item.policyNo}" type="checkbox"></td>
				<td>${item.orgName}</td>
				<td>${item.formNo}</td>
				<td title="${item.policyNo}">${item.policyNo}</td>
				<td><fmt:formatDate value="${item.policyDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.holder}</td>
				<td>${item.prodName}</td>
				<td>${item.policyFee}</td>
				<td>
				<c:choose>  
					    <c:when test="${item.hasErr}">  
					        <div style="color: red;vertical-align:middle;font-weight:normal;">${item.fixStatus eq 'NewStatus'?"差错待处理":item.fixStatus eq 'IngStatus'?"差错待审核":"" }</div>
					    </c:when>
					    <c:otherwise>  
					      &nbsp;
					    </c:otherwise>  
					</c:choose>
				</td>
				<td>
				<c:choose>  
					    <c:when test="${item.hasScan}">  
					      <div style="color: green;vertical-align:middle;font-weight:normal;">已扫描</div>
					    </c:when>
					    <c:otherwise>  
					     <div style="color: red;vertical-align:middle;font-weight:normal;">未扫描</div>
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