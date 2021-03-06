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
			<span class="unit">${issue.policy.policyNo }</span>
		</p>
		<p>
			<label>投保人：</label>
			<span class="unit">${issue.holder }</span>
		</p>
		<p class="nowrap">
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
		<legend>续期催收登记</legend>
		<p>
			<label>催收工单类别：</label><!-- onchange="javascript:$('#myfixStatus').val($('#up_dealType').find('option:selected').text())" -->
			<form:select path="issue.dealType" id="up_dealType" class="combox validate[required] required">
				<form:option value=""> -- </form:option>
				<form:options items="${orgTypeList }" itemLabel="typeName" itemValue="typeName"/>
			</form:select>
		</p>
		<p class="nowrap">
			<label>催收详情：</label>
			<textarea name="fixStatus" id="myfixStatus" cols="25" rows="2" class="input-medium validate[required,maxSize[64]] required">${issue.fixStatus }</textarea>
		</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>
			<label>经办人：</label>
			<input type="text" name="dealMan" id="dealMan" value="${issue.dealMan }" class="input-medium validate[required,maxSize[12]] required"/>
		</p>
		<p>
			<label>经办日期：</label>
			<input type="text" name="dealTime" class="date validate[required,maxSize[12]] required" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value='${issue.dealTime }' pattern='yyyy-MM-dd'/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>备注：</label>
			<input type="text" name="fixDesc" id="fixDesc" value="${issue.fixDesc }"/>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>地市催收情况</legend>
		<p>
			<label>催收工单类别：</label>
			<span class="unit">${issue.hqIssueType }</span>
		</p>
		<p>
			<label>催收详情：</label>
			<span class="unit">${issue.hqDealRst }</span>
		</p>
		<p>
			<label>经办日期：</label>
			<span class="unit">${issue.hqDealDate }</span>
		</p>
		<p>
			<label>备注：</label>
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
			<label>经办日期：</label>
			<span class="unit">${issue.provDealDate }</span>
		</p>
		<p>
			<label>经办人：</label>
			<span class="unit">${issue.provDealMan }</span>
		</p>
		<p>
			<label>备注：</label>
			<span class="unit">${issue.provDealRemark }</span>
		</p>
	</fieldset>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div <c:if test='${issue.feeStatus eq "收费成功" }'>class="buttonDisabled"</c:if> <c:if test='${issue.feeStatus ne "收费成功" }'>class="button"</c:if>><div class="buttonContent"><button type="submit" <c:if test='${issue.feeStatus eq "收费成功" }'>disabled=true</c:if>>提交催收结果</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>