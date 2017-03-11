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
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>
<h2 class="contentTitle"><label>退保结果影响分析结果</label></h2>
<br>
<div class="pageContent" layoutH="120" width="150%">
<fieldset>
		<legend>结果描述：</legend>
		<p>
		<div style="font-size: 16px; text-align: center;">
		员工单退保申请
		</div>
		<br>
		<div style="font-size: 16px">
		&nbsp;&nbsp;&nbsp;&nbsp;员工单信息如下。员工姓名：<span style="font-weight: bold; font-size: 16px;">${holder}</span>，
		所属机构：<span style="font-weight: bold;font-size: 16px;">${orgName}</span>，
		保单号码：<span style="font-weight: bold;font-size: 16px;">${policyNo}</span>，
		保险费：<span style="font-weight: bold;font-size: 16px;">${policyFee}</span>。
		申请退保原因：<span style="font-weight: bold;font-size: 16px;">${tbReason}</span>。<br><br>
		&nbsp;&nbsp;&nbsp;&nbsp;员工的所在县区局（${orgName }）的当前员工单退保率为：<span style="font-weight: bold;font-size: 16px;">${totalRate}</span>，
		如该员工退保后退保率变化为：<span style="font-weight: bold;font-size: 16px;">${newRate}</span>。<br><br>
		&nbsp;&nbsp;&nbsp;&nbsp;市局当前员工单退保率为：<span style="font-weight: bold;font-size: 16px;">${cityTotalRate}</span>，
		如该员工退保后退保率变化为：<span style="font-weight: bold;font-size: 16px;">${cityNewRate}</span>。
		</div>
		<br>
		</p>
</fieldset>
<a class="buttonActive" target="_blank" href="${contextPath }/doc/${policyNo }.doc"><span>点我下载申请书（.doc）</span></a>
</div>