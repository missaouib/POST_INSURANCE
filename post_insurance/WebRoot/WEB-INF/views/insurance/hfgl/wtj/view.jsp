<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" id="issueForm" action="${contextPath}/hfgl/issue/reopen" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${issue.id}"/>
	<input type="hidden" name="status" value="${issue.status}"/>
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>回访不成功件基本信息</legend>
		<p>
			<label>工单编号：</label>
			<span class="unit">${issue.issueNo }</span>
		</p>
		<p>
			<label>工单状态：</label>
			<span class="unit">${issue.status }</span>
		</p>
		<p>
			<label>保单号：</label>
			<span class="unit">${issue.policy.policyNo }</span>
		</p>
		<p>
			<label>客户姓名：</label>
			<span class="unit">${issue.policy.holder }</span>
		</p>
		<p>
			<label>所属机构：</label>
			<span class="unit">${issue.organization.name }</span>
		</p>
		<p class="nowrap">
			<label>出单网点：</label>
			<span class="unit">${issue.bankName }</span>
		</p>
		<p>
			<label>回访日期：</label>
			<span class="unit">${issue.callDate }</span>
		</p>
		<p>
			<label>不成功日期：</label>
			<span class="unit">${issue.issueDate }</span>
		</p>
		<p>
			<label>下发日期：</label>
			<span class="unit">${issue.issueTime }</span>
		</p>
		<p>
			<label>工单类型：</label>
			<span class="unit">${issue.issueType }</span>
		</p>
		<p class="nowrap">
			<label>工单内容：</label>
			<span class="unit">${issue.issueContent }</span>
		</p>
	</fieldset>
	<fieldset>
		<p>
			<label>重置电话为：</label>
			<span class="unit">${issue.resetPhone }</span>
		</p>
	</fieldset>
	<fieldset>
		<p>
			<label>信函标记：</label>
			<span class="unit">${issue.hasLetter }</span>
		</p>
		<p>
			<label>发信函时间：</label>
			<span class="unit">${issue.letterDate }</span>
		</p>
		<p>
			<label>二访结果：</label>
			<span class="unit">${issue.hqIssueType }</span>
		</p>
		<p>
			<label>地市上门结果：</label>
			<span class="unit">${issue.dealType }</span>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>11185第一次回访不成功处理详情</legend>
		<p class="nowrap">
			<label>回访不成功处理记录：</label>
			<span class="unit">${issue.hqDealRst }</span>
		</p>
		<p class="nowrap">
			<label>回访日期：</label>
			<span class="unit">${issue.hqDealDate }"</span>
		</p>
	</fieldset>
	<fieldset>
		<legend>11185第二次回访不成功处理详情</legend>
		<p class="nowrap">
			<label>回访不成功处理记录：</label>
			<span class="unit">${issue.hqDealRst2 }</span>
		</p>
		<p class="nowrap">
			<label>回访日期：</label>
			<span class="unit">${issue.hqDealDate2 }</span>
		</p>
	</fieldset>
	<fieldset>
		<legend>11185第三次回访不成功处理详情</legend>
		<p class="nowrap">
			<label>回访不成功处理记录：</label>
			<span class="unit">${issue.hqDealRst3 }</span>
		</p>
		<p class="nowrap">
			<label>回访日期：</label>
			<span class="unit">${issue.hqDealDate3 }</span>
		</p>
	</fieldset>
	<fieldset>
		<legend>11185第四次回访不成功处理详情</legend>
		<p class="nowrap">
			<label>回访不成功处理记录：</label>
			<span class="unit">${issue.hqDealRst4 }</span>
		</p>
		<p class="nowrap">
			<label>回访日期：</label>
			<span class="unit">${issue.hqDealDate4 }</span>
		</p>
	</fieldset>
	<fieldset>
		<legend>11185第五次回访不成功处理详情</legend>
		<p class="nowrap">
			<label>回访不成功处理记录：</label>
			<span class="unit">${issue.hqDealRst5 }</span>
		</p>
		<p class="nowrap">
			<label>回访日期：</label>
			<span class="unit">${issue.hqDealDate5 }</span>
		</p>
	</fieldset>
	<fieldset>
		<legend>11185第六次回访不成功处理详情</legend>
		<p class="nowrap">
			<label>回访不成功处理记录：</label>
			<span class="unit">${issue.hqDealRst6 }</span>
		</p>
		<p class="nowrap">
			<label>回访日期：</label>
			<span class="unit">${issue.hqDealDate6 }</span>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>分公司回访情况</legend>
		<p class="nowrap">
			<label>分公司回访产生问题记录：</label>
			<span class="unit">${issue.provIssueType }</span>
		</p>
		<p class="nowrap">
			<label>分公司回访结果：</label>
			<span class="unit">${issue.provDealRst }</span>
		</p>
		<p class="nowrap">
			<label>分公司回访日期：</label>
			<span class="unit">${issue.provDealDate }</span>
		</p>
		<p class="nowrap">
			<label>分公司回访备注：</label>
			<span class="unit">${issue.provDealRemark }</span>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>市县催收情况</legend>
		<p class="nowrap">
			<label>续期催收处理记录：</label>
			<span class="unit">${issue.dealDesc }</span>
		</p>
		<p class="nowrap">
			<label>经办人：</label>
			<span class="unit">${issue.dealMan }</span>
		</p>
		<p class="nowrap">
			<label>经办日期：</label>
			<span class="unit">${issue.dealTime }</span>
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