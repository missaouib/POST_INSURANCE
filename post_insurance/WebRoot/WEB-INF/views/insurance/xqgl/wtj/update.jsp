<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/xqgl/issue/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${issue.id}"/>
	<input type="hidden" name="feeStatus" value="${issue.feeStatus}"/>
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>续期催收基本信息</legend>
		<p>
			<label>保单号：</label>
			<input type="text" name="policy.policyNo" readonly="readonly" class="input-medium" maxlength="32" value="${issue.policy.policyNo }"/>
		</p>
		<p class="nowrap">
			<label>出单网点：</label>
			<textarea name="netName" disabled="true" cols="20" rows="2">${issue.netName }</textarea>
		</p>
		<p>
			<label>保单年度：</label>
			<input type="text" name="policyYear" readonly="readonly" class="input-medium" maxlength="32" value="${issue.policyYear }"/>
		</p>
		<p>
			<label>险种名称：</label>
			<input type="text" name="prdName" readonly="readonly" class="input-medium" maxlength="32" value="${issue.prdName }"/>
		</p>
		<p>
			<label>交费对应日：</label>
			<textarea name="feeDate" disabled="true" cols="20" rows="2">${issue.feeDate }</textarea>
		</p>
		<p>
			<label>应收保费：</label>
			<input type="text" name="policyFee" readonly="readonly" class="input-medium" maxlength="32" value="${issue.policyFee }"/>
		</p>
		<p>
			<label>收费状态：</label>
			<input type="text" name="feeStatus" readonly="readonly" class="input-medium" maxlength="32" value="${issue.feeStatus }"/>
		</p>
		<p>
			<label>收费失败原因：</label>
			<input type="text" name="feeFailReason" readonly="readonly" class="input-medium" maxlength="32" value="${issue.feeFailReason }"/>
		</p>
		<p>
			<label>投保人：</label>
			<input type="text" name="holder" readonly="readonly" class="input-medium" maxlength="32" value="${issue.holder }"/>
		</p>
		<p>
			<label>联系电话：</label>
			<input type="text" name="phone" readonly="readonly" class="input-medium" maxlength="32" value="${issue.phone }"/>
		</p>
		<p>
			<label>联系手机：</label>
			<input type="text" name="mobile" readonly="readonly" class="input-medium" maxlength="32" value="${issue.mobile }"/>
		</p>
		
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>续期催收处理详情</legend>
		<p class="nowrap">
			<label>续期催收处理记录：</label>
			<textarea name="fixDesc" cols="50" rows="3">${issue.fixDesc }</textarea>
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
		<legend>总部催收情况</legend>
		<p class="nowrap">
			<label>总部催收产生问题记录：</label>
			<textarea name="hqIssueType" disabled="true" cols="50" rows="3">${issue.hqIssueType }</textarea>
		</p>
		<p class="nowrap">
			<label>总部催收结果：</label>
			<input type="text" name="hqDealRst" disabled="true" class="input-medium" maxlength="32" value="${issue.hqDealRst }"/>
		</p>
		<p class="nowrap">
			<label>总部催收日期：</label>
			<input type="text" name="hqDealDate" disabled="true" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="${issue.hqDealDate }"/>
		</p>
		<p class="nowrap">
			<label>总部催收备注：</label>
			<input type="text" name="hqDealRemark" disabled="true" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="${issue.hqDealRemark }"/>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>分公司催收情况</legend>
		<p class="nowrap">
			<label>分公司催收产生问题记录：</label>
			<textarea name="provIssueType" disabled="true" cols="50" rows="3">${issue.provIssueType }</textarea>
		</p>
		<p class="nowrap">
			<label>分公司催收结果：</label>
			<input type="text" name="provDealRst" disabled="true" class="input-medium" maxlength="32" value="${issue.provDealRst }"/>
		</p>
		<p class="nowrap">
			<label>分公司催收日期：</label>
			<input type="text" name="provDealDate" disabled="true" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="${issue.provDealDate }"/>
		</p>
		<p class="nowrap">
			<label>分公司催收备注：</label>
			<input type="text" name="provDealRemark" disabled="true" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="${issue.provDealRemark }"/>
		</p>
	</fieldset>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div <c:if test='${issue.feeStatus eq "收费成功" }'>class="buttonDisabled"</c:if> <c:if test='${issue.feeStatus ne "收费成功" }'>class="button"</c:if>><div class="buttonContent"><button type="button" onclick="$('#feeStatus').val('已催收');$('#issueForm').submit();" <c:if test='${issue.feeStatus eq "收费成功" }'>disabled=true</c:if>>提交催收结果</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>