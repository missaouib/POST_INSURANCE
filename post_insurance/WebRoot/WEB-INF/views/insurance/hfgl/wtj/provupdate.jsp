<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/hfgl/issue/provUpdate" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${issue.id}"/>
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
			<label>险种名称：</label>
			<span class="unit">${issue.policy.prodName }</span>
		</p>
		<p>
			<label>客户姓名：</label>
			<span class="unit">${issue.policy.holder }</span>
		</p>
		<p>
			<label>客户电话：</label>
			<span class="unit">${issue.holderPhone }</span>
		</p>
		<p>
			<label>客户手机：</label>
			<span class="unit">${issue.holderMobile }</span>
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
	<div class="divider"></div>
	<fieldset>
		<legend>省分回访处理记录</legend>
		<p class="nowrap">
			<label>不成功类型：</label>
			<form:select path="issue.provIssueType" onchange="javascript:$('#provDealRst').val($('#provIssueType').find('option:selected').text())" class="combox ivalidate[required] required">
				<form:option value=""> -- </form:option>
				<form:options items="${hqTypeList }" itemLabel="typeDesc" itemValue="typeName"/>
			</form:select>
		</p>
		<p class="nowrap">
			<label>省分回访详情：</label>
			<textarea name="provDealRst" id="provDealRst" cols="50" rows="3" class="input-medium validate[required,maxSize[64]] required">${issue.provDealRst }</textarea>
		</p>
		<!-- 
		<p class="nowrap">
			<label>回访结果：</label>
			<form:radiobutton path="issue.status" value="二访失败"/>二访失败&nbsp;&nbsp;
			<form:radiobutton path="issue.status" value="已结案"/>已结案
		</p>
		 -->
		<p class="nowrap">
			<label>经办人：</label>
			<input type="text" name="provDealMan" class="input-medium validate[required,maxSize[12]] required" maxlength="32" value="${issue.provDealMan }"/>
		</p>
		<p class="nowrap">
			<label>经办日期：</label>
			<input type="text" name="provDealDate" class="date validate[required,maxSize[12]] required" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value='${issue.provDealDate }' pattern='yyyy-MM-dd'/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>省分二访中心第一次回访处理详情</legend>
		<p>
			<label>不成功类型：</label>
			<span class="unit">${issue.hqDealType }</span>
		</p>
		<p>
			<label>回访类型：</label>
			<span class="unit">${issue.hqDealRst }</span>
		</p>
		<p>
			<label>经办人：</label>
			<span class="unit">${issue.hqDealMan }</span>
		</p>
		<p>
			<label>回访日期：</label>
			<span class="unit"><fmt:formatDate value='${issue.hqDealDate }' pattern='yyyy-MM-dd'/></span>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>地市回访情况</legend>
		<p class="nowrap">
			<label>地市回访产生问题记录：</label>
			<span class="unit">${issue.dealType }</span>
		</p>
		<p class="nowrap">
			<label>地市回访结果：</label>
			<span class="unit">${issue.dealDesc }</span>
		</p>
		<p class="nowrap">
			<label>地市回访日期：</label>
			<span class="unit">${issue.dealTime }</span>
		</p>
		<p class="nowrap">
			<label>地市回访人员：</label>
			<span class="unit">${issue.dealMan }</span>
		</p>
	</fieldset>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div <c:if test='${issue.status eq "已结案" }'>class="buttonDisabled"</c:if> <c:if test='${issue.status ne "已结案" }'>class="button"</c:if>><div class="buttonContent"><button type="submit" <c:if test='${issue.status eq "已结案" }'>disabled=true</c:if>>提交回访结果</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>