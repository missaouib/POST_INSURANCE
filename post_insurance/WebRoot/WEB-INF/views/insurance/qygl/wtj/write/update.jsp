<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/kfgl/issue/updateCheckWrite" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${issue.id}"/>
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>不合格件基本信息</legend>
		<p>
			<label>保单号：</label>
			<input type="text" name="policy.policyNo" readonly="readonly" class="input-medium" maxlength="32" value="${issue.policy.policyNo }"/>
		</p>
		<p>
			<label>投保单号：</label>
			<input type="text" name="formNo" readonly="readonly" class="input-medium" maxlength="32" value="${issue.formNo }"/>
		</p>
		<p>
			<label>网点类型：</label>
			<textarea name="netType" disabled="true" cols="20" rows="2">${issue.netType }</textarea>
		</p>
		<p class="nowrap">
			<label>出单网点：</label>
			<textarea name="netName" disabled="true" cols="20" rows="2">${issue.netName }</textarea>
		</p>
		<p>
			<label>抽检人：</label>
			<input type="text" name="checker" readonly="readonly" class="input-medium" maxlength="32" value="${issue.checker }"/>
		</p>
		<p>
			<label>抽检批次：</label>
			<input type="text" name="checkBatch" readonly="readonly" class="input-medium" maxlength="32" value="${issue.checkBatch }"/>
		</p>
		<p>
			<label>险种名称：</label>
			<input type="text" name="prdName" readonly="readonly" class="input-medium" maxlength="32" value="${issue.prdName }"/>
		</p>
		<p class="nowrap">
			<label>关键信息错误情况：</label>
			<textarea name="keyInfo" disabled="true" cols="20" rows="2">${issue.keyInfo }</textarea>
		</p>
		<p class="nowrap">
			<label>重要信息错误情况：</label>
			<textarea name="importanceInfo" disabled="true" cols="20" rows="2">${issue.importanceInfo }</textarea>
		</p>
		<p class="nowrap">
			<label>其他信息错误情况：</label>
			<textarea name="elseInfo" disabled="true" cols="20" rows="2">${issue.elseInfo }</textarea>
		</p>
		<p>
			<label>影像件扫描错误：</label>
			<input type="text" name="dorError" readonly="readonly" class="input-medium" maxlength="32" value="${issue.dorError }"/>
		</p>
		<p class="nowrap">
			<label>投保资料扫描缺失情况：</label>
			<textarea name="docMiss" disabled="true" cols="20" rows="2">${issue.elseInfo }</textarea>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>不合格件处理详情</legend>
		<p class="nowrap">
			<label>不合格件处理记录：</label>
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
	</div>
			
	<div class="formBar">
		<ul>
			<li><div <c:if test='${issue.status eq "已结案" }'>class="buttonDisabled"</c:if> <c:if test='${issue.status ne "已结案" }'>class="button"</c:if>><div class="buttonContent"><button type="submit" <c:if test='${issue.status eq "已结案" }'>disabled=true</c:if>>确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>