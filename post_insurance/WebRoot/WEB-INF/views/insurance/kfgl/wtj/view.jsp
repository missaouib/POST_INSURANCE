<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" id="issueForm" action="${contextPath}/kfgl/issue/reopen" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${issue.id}"/>
	<input type="hidden" name="status" value="${issue.status}"/>
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>工单基本信息</legend>
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
		<legend>工单处理详情</legend>
		<p class="nowrap">
			<label>工单处理结果：</label>
			<textarea name="result" cols="50" rows="3">${issue.result }</textarea>
		</p>
		<p class="nowrap">
			<label>经办人：</label>
			<input type="text" name="dealMan" class="input-medium" maxlength="32" value="${issue.dealMan }"/>
		</p>
		<p class="nowrap">
			<label>经办日期：</label>
			<input type="text" name="dealTime" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="${issue.dealTime }"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>重新打开处理</legend>
		<p class="nowrap">
			<label>重新打开原因：</label>
			<textarea name="reopenReason" cols="50" rows="3">${issue.reopenReason }</textarea>
		</p>
		<p class="nowrap">
			<label>重新打开操作员：</label>
			<input type="text" name="reopenUser.realname" disabled="true" class="input-medium" maxlength="32" value="${issue.reopenUser.realname }"/>
		</p>
		<p class="nowrap">
			<label>重新打开日期：</label>
			<input type="text" name="reopenDate" disabled="true" class="input-medium" maxlength="32" value="${issue.reopenDate }"/>
		</p>
	</fieldset>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div <c:if test='${issue.status eq "已结案" }'>class="buttonDisabled"</c:if> <c:if test='${issue.status ne "已结案" }'>class="button"</c:if>><div class="buttonContent"><button type="submit" <c:if test='${issue.status eq "已结案" }'>disabled=true</c:if>>重打开</button></div></div></li>
			<li><div <c:if test='${issue.status eq "已结案" }'>class="buttonDisabled"</c:if> <c:if test='${issue.status ne "已结案" }'>class="button"</c:if>><div class="buttonContent"><button type="button" onclick="$('#status').val('CloseStatus');$('#issueForm').attr('action', '/kfgl/issue/close').submit();" <c:if test='${issue.status eq "已结案" }'>disabled=true</c:if>>结  案</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>