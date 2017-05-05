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
		  员工单退保签批函
		</div>
		<br>
		<div style="font-size: 16px">
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  员工单信息如下。员工姓名：${holder}，所属机构：${orgName}，保单号码：${policyNo}，保险费：${policyFee}元，出单日期：${policyDate}。 <br /><br />
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  所属县区${year}年期交总保费${totalAreaFee}万元，员工单期交保费${staffAreaFee}万元，所属县区局（${orgName}）的当前员工单期交退保率为：${orgCurStaffRate}，如该员工退保后期交退保率变化为：${orgNewStaffRate}，期交总退保率变为${orgNewPermRate}，${orgTips }。 <br /><br />
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  所属市局${year}年期交总保费${totalCityFee}万元，员工单期交保费${staffCityFee}万元，市局当前员工单期交退保率为：${curStaffRate}，如该员工退保后期交退保率变化为：${newStaffRate}，期交总退保率变为${newPermRate}，${cityTips }。 <br /><br />
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  该员工申请退保原因：${tbReason}。 <br /><br />
		<p >&nbsp;</p>
		                            中邮保险局局长签字：
		</div>
		<br>
</fieldset>
<a class="buttonActive" target="_blank" href="${contextPath }/doc/${policyNo }.doc"><span>点我下载申请书（.doc）</span></a>
</div>