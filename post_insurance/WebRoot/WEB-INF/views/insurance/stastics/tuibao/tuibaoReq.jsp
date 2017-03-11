<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script src="${contextPath}/js/echarts.common.min.js"></script>
<form method="post" id="hfForm" action="${contextPath }/surrender/req" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>身份证号码：
					<input type="text" id="tI" style="width: 100px;" name="idCardNum" value="${idCardNum}"/>
					</td>
					<td>保单号：
					<input type="text" id="txtP" style="width: 100px;" name="policyNo" value="${policyNo}"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>						
					<li><div class="button"><div class="buttonContent"><button type="submit">提交退保申请查询</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>
<h2 class="contentTitle"><label>退保结果影响分析</label></h2>
<br>
<div class="pageContent" layoutH="300" width="100%">
<table class="table" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>投保单号</th>
				<th>保单号</th>
				<th>机构</th>
				<th>投保人</th>
				<th>险种名称</th>
				<th>保费</th>
				<th>交费方式</th>
				<th>交费期间</th>
				<th>承保日期</th>
				<th>状态</th>
				<th>员工单</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${policies}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.formNo}</td>
				<td>${item.policyNo}</td>
				<td>
				<c:choose>  
				    <c:when test="${fn:contains(item.organization.name, '直属')}">  
				        <c:out value="${fn:replace(item.organization.name,'邮政局直属中邮保险局','直属')}" />  
				    </c:when>
				    <c:when test="${fn:contains(item.organization.name, '仲恺')}">  
				        ${fn:substring(item.organization.name, 0, 7)}
				    </c:when>
				   <c:otherwise>
				      <c:out value="${fn:replace(item.organization.name,'邮政局中邮保险局','')}" />  
				    </c:otherwise>  
				</c:choose>
				</td>
				<td>${item.holder}</td>
				<td>${item.prodName}</td>
				<td>${item.policyFee}</td>
				<td>${item.feeFrequency}</td>
				<td>${item.perm}</td>
				<td><fmt:formatDate value="${item.policyDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.status}</td>
				<td>${item.isStaff}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<c:if test="${isStaff }">
	<form method="post" id="subhfForm" action="${contextPath }/surrender/rst" onsubmit="return navTabSearch(this)">
	<input hidden="hidden" name="policyNo" value="${policyNo }">
	<fieldset>
		<legend>请输入退保原因：</legend>
		<p>
		<textarea name="tbReason" rows="3" cols="50"></textarea>
		</p>
	</fieldset>
	<div class="button"><div class="buttonContent"><button type="submit">提交申请</button></div></div>
	</form>
	</c:if>
</div>