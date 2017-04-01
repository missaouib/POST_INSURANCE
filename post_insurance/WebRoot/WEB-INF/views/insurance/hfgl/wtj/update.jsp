<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/hfgl/issue/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
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
			<label>客户电话：</label>
			<span class="unit">${issue.holderPhone }</span>
		</p>
		<p>
			<label>客户手机：</label>
			<span class="unit">${issue.holderMobile }</span>
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
			<span class="unit">${issue.operateTime }</span>
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
		<p>
			<label>可再访情况：</label>
			<span class="unit">${issue.canCallAgainRemark }</span>
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
		<legend>回访不成功处理详情(仅记录最后结果)</legend>
		<p class="nowrap">
			<label>不成功类型：</label>
			<form:select path="issue.dealType" onchange="javascript:$('#dealDesc').val($('#dealType').find('option:selected').text())" class="combox validate[required] required">
				<form:option value=""> -- </form:option>
				<form:options items="${orgTypeList }" itemLabel="typeDesc" itemValue="typeName"/>
			</form:select>
		</p>
		<p>
			<label>不成功结果：</label>
			<textarea name="dealDesc" id="dealDesc" cols="30" rows="3" class="required">${issue.dealDesc }</textarea>
		</p>
		<p>&nbsp;</p><p>&nbsp;</p>
		<p class="nowrap">
			<label>签收回访函日期：</label>
			<input type="text" name="clientSignDate" class="date validate[required,maxSize[12]] required" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value="${issue.clientSignDate }" pattern="yyyy-MM-dd"/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p class="nowrap">
			<label>经办人：</label>
			<input type="text" name="dealMan" class="input-medium validate[required,maxSize[12]] required" maxlength="32" value="${issue.dealMan }"/>
		</p>
		<p>
			<label>经办日期：</label>
			<input type="text" name="dealTime" class="date validate[required,maxSize[12]] required" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value="${issue.dealTime }" pattern="yyyy-MM-dd"/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>省分二访中心回访处理详情</legend>
		<p>
           <label>二访类别：</label>
           <span class="unit">${issue.hqDealTypeElse }</span>
        </p>
        <p>
           <label>客户资料备注：</label>
           <span class="unit">${issue.clientRemark }</span>
        </p>
        <dl>
           <dt>二访详情：</dt>
           <dd><textarea name="textarea1" cols="80" rows="3">${issue.hqDealRst }</textarea></dd>
        </dl>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
		<p>
			<label>二访结果：</label>
			<span class="unit">${issue.hqDealType }</span>
		</p>
		<p>
			<label>经办人：</label>
			<span class="unit">${issue.hqDealMan }</span>
		</p>
		<p>
			<label>回访日期：</label>
			<span class="unit"><fmt:formatDate value='${issue.hqDealDate }' pattern='yyyy-MM-dd'/></span>
		</p>
		<p>
			<label>拨打电话：</label>
			<span class="unit">${issue.phoneNum }</span>
		</p>
		<p>
			<label>通话开始：</label>
			<span class="unit">${issue.phoneStart }</span>
		</p>
		<p>
			<label>通话结束：</label>
			<span class="unit">${issue.phoneEnd }</span>
		</p>
		<p>
			<label>通话时长：</label>
			<span class="unit">${issue.phoneTime }</span>
		</p>
	</fieldset>
	<!-- 
	<fieldset>
		<legend>省分二访中心第二次回访处理详情</legend>
		<p>
			<label>不成功类型：</label>
			<span class="unit">${issue.hqDealType2 }</span>
		</p>
		<p>
			<label>回访类型：</label>
			<span class="unit">${issue.hqDealRst2 }</span>
		</p>
		<p>
			<label>经办人：</label>
			<span class="unit">${issue.hqDealMan2 }</span>
		</p>
		<p>
			<label>回访日期：</label>
			<span class="unit"><fmt:formatDate value='${issue.hqDealDate2 }' pattern='yyyy-MM-dd'/></span>
		</p>
	</fieldset>
	<fieldset>
		<legend>省分二访中心第三次回访处理详情</legend>
		<p>
			<label>不成功类型：</label>
			<span class="unit">${issue.hqDealType3 }</span>
		</p>
		<p>
			<label>回访类型：</label>
			<span class="unit">${issue.hqDealRst3 }</span>
		</p>
		<p>
			<label>经办人：</label>
			<span class="unit">${issue.hqDealMan3 }</span>
		</p>
		<p>
			<label>回访日期：</label>
			<span class="unit"><fmt:formatDate value='${issue.hqDealDate3 }' pattern='yyyy-MM-dd'/></span>
		</p>
	</fieldset>
	<fieldset>
		<legend>省分二访中心第四次回访处理详情</legend>
		<p>
			<label>不成功类型：</label>
			<span class="unit">${issue.hqDealType4 }</span>
		</p>
		<p>
			<label>回访类型：</label>
			<span class="unit">${issue.hqDealRst4 }</span>
		</p>
		<p>
			<label>经办人：</label>
			<span class="unit">${issue.hqDealMan4 }</span>
		</p>
		<p>
			<label>回访日期：</label>
			<span class="unit"><fmt:formatDate value='${issue.hqDealDate4 }' pattern='yyyy-MM-dd'/></span>
		</p>
	</fieldset>
	<fieldset>
		<legend>省分二访中心第五次回访处理详情</legend>
		<p>
			<label>不成功类型：</label>
			<span class="unit">${issue.hqDealType5 }</span>
		</p>
		<p>
			<label>回访类型：</label>
			<span class="unit">${issue.hqDealRst5 }</span>
		</p>
		<p>
			<label>经办人：</label>
			<span class="unit">${issue.hqDealMan5 }</span>
		</p>
		<p>
			<label>回访日期：</label>
			<span class="unit"><fmt:formatDate value='${issue.hqDealDate5 }' pattern='yyyy-MM-dd'/></span>
		</p>
	</fieldset>
	<fieldset>
		<legend>省分二访中心第六次回访处理详情</legend>
		<p>
			<label>不成功类型：</label>
			<span class="unit">${issue.hqDealType6 }</span>
		</p>
		<p>
			<label>回访类型：</label>
			<span class="unit">${issue.hqDealRst6 }</span>
		</p>
		<p>
			<label>经办人：</label>
			<span class="unit">${issue.hqDealMan6 }</span>
		</p>
		<p>
			<label>回访日期：</label>
			<span class="unit"><fmt:formatDate value='${issue.hqDealDate6 }' pattern='yyyy-MM-dd'/></span>
		</p>
	</fieldset>
	 -->
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
	</div>
			
	<div class="formBar">
		<ul>
			<li><div <c:if test='${issue.status eq "已结案" }'>class="buttonDisabled"</c:if> <c:if test='${issue.status ne "已结案" }'>class="button"</c:if>><div class="buttonContent"><button type="submit" <c:if test='${issue.status eq "已结案" }'>disabled=true</c:if>>提交回访结果</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>