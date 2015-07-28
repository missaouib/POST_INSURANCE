<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/hfgl/issue/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${issue.id}"/>
	<input type="hidden" name="status" value="${issue.status}"/>
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>回访不成功件基本信息</legend>
		<p>
			<label>工单编号：</label>
			<input type="text" name="issueNo" readonly="readonly" class="input-medium" maxlength="32" readonly="readonly" value="${issue.issueNo }"/>
		</p>
		<p>
			<label>工单状态：</label>
			<input type="text" name="status" readonly="readonly" class="input-medium" maxlength="32" readonly="readonly" value="${issue.status }"/>
		</p>
		<p>
			<label>保单号：</label>
			<input type="text" name="policy.policyNo" readonly="readonly" class="input-medium" maxlength="32" value="${issue.policy.policyNo }"/>
		</p>
		<p>
			<label>所属机构：</label>
			<input type="text" name="organization。name" readonly="readonly" class="input-medium" maxlength="128" value="${issue.organization.name }"/>
		</p>
		<p class="nowrap">
			<label>出单网点：</label>
			<textarea name="bankName" disabled="true" cols="20" rows="2">${issue.bankName }</textarea>
		</p>
		<p>
			<label>回访日期：</label>
			<input type="text" name="callDate" readonly="readonly" class="input-medium" maxlength="32" value="${issue.callDate }"/>
		</p>
		<p>
			<label>不成功日期：</label>
			<input type="text" name="issueDate" readonly="readonly" class="input-medium" maxlength="32" value="${issue.issueDate }"/>
		</p>
		<p>
			<label>下发日期：</label>
			<input type="text" name="issueTime" readonly="readonly" class="input-medium" maxlength="32" value="${issue.issueTime }"/>
		</p>
		<p>
			<label>工单类型：</label>
			<input type="text" name="issueType" readonly="readonly" class="input-medium" maxlength="32" value="${issue.issueType }"/>
		</p>
		<p class="nowrap">
			<label>工单内容：</label>
			<textarea name="issueContent" disabled="true" cols="20" rows="2">${issue.issueContent }</textarea>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>回访不成功处理详情(请在后面追加)</legend>
		<p class="nowrap">
			<label>回访不成功处理记录：</label>
			<textarea name="hqDealDesc" cols="50" rows="3">${issue.hqDealDesc }</textarea>
		</p>
		<p class="nowrap">
			<label>经办人：</label>
			<input type="text" name="hqDealMan" class="input-medium" maxlength="32" value="${issue.hqDealMan }"/>
		</p>
		<p class="nowrap">
			<label>经办日期：</label>
			<input type="text" name="hqDealDate" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="${issue.hqDealDate }"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>分公司回访情况</legend>
		<p class="nowrap">
			<label>分公司回访产生问题记录：</label>
			<textarea name="provIssueType" disabled="true" cols="50" rows="3">${issue.provIssueType }</textarea>
		</p>
		<p class="nowrap">
			<label>分公司回访结果：</label>
			<input type="text" name="provDealRst" disabled="true" class="input-medium" maxlength="32" value="${issue.provDealRst }"/>
		</p>
		<p class="nowrap">
			<label>分公司回访日期：</label>
			<input type="text" name="provDealDate" disabled="true" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="${issue.provDealDate }"/>
		</p>
		<p class="nowrap">
			<label>分公司回访备注：</label>
			<input type="text" name="provDealRemark" disabled="true" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="${issue.provDealRemark }"/>
		</p>
	</fieldset>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div <c:if test='${issue.status eq "收费成功" }'>class="buttonDisabled"</c:if> <c:if test='${issue.status ne "收费成功" }'>class="button"</c:if>><div class="buttonContent"><button type="button" onclick="$('#status').val('收费成功');$('#issueForm').attr('action', '/xqgl/issue/close').submit();" <c:if test='${issue.status eq "收费成功" }'>disabled=true</c:if>>提交回访结果</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>