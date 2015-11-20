<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" id="issueForm" action="${contextPath}/xqgl/issue/reopen" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${issue.id}"/>
	<input type="hidden" name="feeStatus" value="${issue.feeStatus}"/>
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>续期催收基本信息</legend>
		<p>
			<label>保单号：</label>
			<span class="unit">${issue.policy.policyNo }</span>
		</p>
		<p>
			<label>投保人：</label>
			<span class="unit">${issue.holder }</span>
		</p>
		<p>
			<label>出单网点：</label>
			<span class="unit">${issue.netName }</span>
		</p>
		<p>
			<label>保单年度：</label>
			<span class="unit">${issue.policyYear }</span>
		</p>
		<p>
			<label>险种名称：</label>
			<span class="unit">${issue.prdName }</span>
		</p>
		<p>
			<label>交费对应日：</label>
			<span class="unit">${issue.feeDate }</span>
		</p>
		<p>
			<label>应收保费：</label>
			<span class="unit">${issue.policyFee }</span>
		</p>
		<p>
			<label>账号：</label>
			<span class="unit">${issue.account }</span>
		</p>
		<p>
			<label>收费状态：</label>
			<span class="unit">${issue.feeStatus }</span>
		</p>
		<p>
			<label>收费失败原因：</label>
			<span class="unit">${issue.feeFailReason }</span>
		</p>
		<p>
			<label>联系电话：</label>
			<span class="unit">${issue.phone }</span>
		</p>
		<p>
			<label>联系手机：</label>
			<span class="unit">${issue.mobile }</span>
		</p>
		<p>
			<label>地址：</label>
			<span class="unit">${issue.addr }</span>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>总部催收情况</legend>
		<p>
			<label>催收工单类别：</label>
			<span class="unit">${issue.hqIssueType }</span>
		</p>
		<p>
			<label>催收详情：</label>
			<span class="unit">${issue.hqDealRst }</span>
		</p>
		<p>
			<label>催收日期：</label>
			<span class="unit">${issue.hqDealDate }</span>
		</p>
		<p>
			<label>催收备注：</label>
			<span class="unit">${issue.hqDealRemark }</span>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>分公司催收情况</legend>
		<p>
			<label>催收工单类别：</label>
			<span class="unit">${issue.provIssueType }</span>
		</p>
		<p>
			<label>催收详情：</label>
			<span class="unit">${issue.provDealRst }</span>
		</p>
		<p>
			<label>催收日期：</label>
			<span class="unit">${issue.provDealDate }</span>
		</p>
		<p>
			<label>催收备注：</label>
			<span class="unit">${issue.provDealRemark }</span>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>市县催收情况</legend>
		<p>
			<label>催收工单类别：</label>
			<span class="unit">${issue.fixDesc }</span>
		</p>
		<p>
			<label>催收详情：</label>
			<span class="unit">${issue.fixStatus }</span>
		</p>
		<p>
			<label>催收日期：</label>
			<span class="unit">${issue.dealTime }</span>
		</p>
		<p>
			<label>催收备注：</label>
			<span class="unit">${issue.fixDesc }</span>
		</p>
		<p>
			<label>催收人：</label>
			<span class="unit">${issue.dealMan }</span>
		</p>
	</fieldset>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>