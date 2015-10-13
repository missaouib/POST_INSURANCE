<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/qygl/issue/record/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${issue.id}"/>
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>新契约录入不合格件基本信息</legend>
		<p>
			<label>保单号：</label>
			<span class="unit">${issue.policy.policyNo }</span>
		</p>
		<p>
			<label>投保单号：</label>
			<span class="unit">${issue.formNo }</span>
		</p>
		<p>
			<label>网点类型：</label>
			<span class="unit">${issue.netType }</span>
		</p>
		<p>
			<label>出单网点：</label>
			<span class="unit">${fn:replace(issue.netName,'中国邮政储蓄银行股份有限公司','')}</span>
		</p>
		<p>
			<label>抽检人：</label>
			<span class="unit">${issue.checker }</span>
		</p>
		<p>
			<label>抽检批次：</label>
			<span class="unit">${issue.checkBatch }</span>
		</p>
		<p>
			<label>险种名称：</label>
			<span class="unit">${issue.prdName }</span>
		</p>
		<p class="nowrap">
			<label>关键信息错误情况：</label>
			<span class="unit">${issue.keyInfo=="null"?"":issue.keyInfo }</span>
		</p>
		<p class="nowrap">
			<label>重要信息错误情况：</label>
			<span class="unit">${issue.importanceInfo=="null"?"":issue.importanceInfo }</span>
		</p>
		<p class="nowrap">
			<label>其他信息错误情况：</label>
			<span class="unit">${issue.elseInfo=="null"?"":issue.elseInfo }</span>
		</p>
		<p>
			<label>扫描错误：</label>
			<span class="unit">${issue.docError=='null'?'':issue.docError }</span>
		</p>
		<p class="nowrap">
			<label>扫描缺失情况：</label>
			<span class="unit">${issue.docMiss=="null"?"":issue.docMiss }</span>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>不合格件处理详情</legend>
		<p class="nowrap">
			<label>处理结果类型：</label>
			<form:select path="issue.fixType" onchange="javascript:$('#fixDesc').val($('#fixType').find('option:selected').text())" class="combox validate[required] required">
				<form:option value=""> -- </form:option>
				<form:options items="${checkFixList }" itemLabel="typeDesc" itemValue="typeName"/>
			</form:select>
		</p>
		<p class="nowrap">
			<label>不合格件处理记录：</label>
			<textarea name="fixDesc" id="fixDesc" cols="50" rows="3" class="input-medium validate[required,maxSize[64]] required">${issue.fixDesc }</textarea>
		</p>
		<p class="nowrap">
			<label>经办人：</label>
			<input type="text" name="dealMan" class="input-medium validate[required,maxSize[12]] required" maxlength="32" value="${issue.dealMan }"/>
		</p>
		<p class="nowrap">
			<label>经办日期：</label>
			<input type="text" name="dealTime" class="date validate[required,maxSize[12]] required"" dateFmt="yyyy-MM-dd" readonly="true" value="${issue.dealTime }"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
	</fieldset>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div <c:if test='${issue.fixStatus eq "CloseStatus" }'>class="buttonDisabled"</c:if> <c:if test='${issue.fixStatus ne "CloseStatus" }'>class="button"</c:if>><div class="buttonContent"><button type="submit" <c:if test='${issue.fixStatus eq "CloseStatus" }'>disabled=true</c:if>>确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>