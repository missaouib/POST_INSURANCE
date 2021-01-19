<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/xqgl/follow/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${issue.id}"/>
	<input type="hidden" name="policyNo" value="${issue.policy.policyNo}"/>
	<div class="pageFormContent" layoutH="58">
		<fieldset>
		<legend>需反馈信息</legend>
		<p>
			<label>职业：</label>
			<input name="job" type="text" postField="job" suggestFields="job" class="input-medium validate[required,maxSize[32]] required"
					suggestUrl="/xqgl/lookupJobSuggest" lookupGroup="" value="${issue.job }"/>
		</p>		
		<p>
			<label>工作单位：</label>
			<input type="text" name="company" class="input-medium validate[required] required" maxlength="32" value="${issue.company }"/>
		</p>
		<p>
			<label>个人收入（元）：</label>
			<input type="text" name="income" class="input-medium validate[required] required" maxlength="32" value="${issue.income }"/>
		</p>
		<p>
			<label>家庭年收入（元）：</label>
			<input type="text" name="homeIncome" class="input-medium validate[required] required" maxlength="32" value="${issue.homeIncome }"/>
		</p>
		<p>
			<label>购买目的</label>
			<input name="objectives" type="text" postField="objectives" suggestFields="objectives" class="input-medium validate[required,maxSize[32]] required"
					suggestUrl="/xqgl/lookupWhyBuySuggest" lookupGroup="" value="${issue.objectives }"/>
		</p>
		<p>
			<label>客户网点资产情况</label>
			<textarea type="text" name="bankInfo" class="input-medium validate[required] required">${issue.bankInfo}</textarea>
		</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>
			<label>风险自评：</label>
			<input name="riskLevel" type="text" postField="riskLevel" suggestFields="riskLevel" class="input-medium validate[required,maxSize[32]] required"
					suggestUrl="/xqgl/lookupRiskSuggest" lookupGroup="" value="${issue.riskLevel }"/>
		</p>
		<p>
低风险：客户收入与资产足以覆盖后续交费；
中风险：客户收入与资产勉强覆盖后续交费；
高风险：客户收入与资产难以覆盖后续交费
		</p>
		</fieldset>
		<fieldset>
		<legend>保单基本信息</legend>
		<p>
			<label>投保单号：</label>
			<span class="unit">${issue.policy.formNo }</span>
		</p>
		<p>
			<label>保单号：</label>
			<span class="unit">${issue.policy.policyNo }</span>
		</p>
		<p>
			<label>机构：</label>
			<span class="unit">${issue.policy.organName }</span>
		</p>
		<p>
			&nbsp;
		</p>
		<p>
			<label>客户姓名：</label>
			<span class="unit">${issue.policy.holder }</span>
		</p>
		<p>
			<label>性别：</label>
			<span class="unit">${issue.policy.policyDtl.holderSexy }</span>
		</p>
		<p>
        <label>电话：</label>
        <span class="unit">${issue.policy.policyDtl.holderPhone }</span>
    </p>
    <p>
        <label>电话：</label>
        <span class="unit">${issue.policy.policyDtl.holderMobile }</span>
    </p>
    <p>
        <label>地址：</label>
        <span class="unit">${issue.policy.policyDtl.holderAddr }</span>
    </p>
    <p>
        <label>证件类型：</label>
        <span class="unit">${issue.policy.policyDtl.holderCardType }</span>
    </p>
    <p>
        <label>证件号码：</label>
        <span class="unit">${issue.policy.policyDtl.holderCardNum }</span>
    </p>
    <p>
        <label>证件有效期：</label>
        <span class="unit">${issue.policy.policyDtl.holderCardValid }</span>
    </p>
    <p>
        <label>Email：</label>
        <span class="unit">${issue.policy.policyDtl.holderEmail }</span>
    </p>
    <p>
        <label>投保年龄：</label>
        <span class="unit">${issue.policy.policyDtl.holderAge }</span>
    </p>
    <p>
			<label>被保人是投保人的：</label>
			<span class="unit">${issue.policy.policyDtl.relation }</span>
		</p>
		<p>
			<label>被保险人：</label>
			<span class="unit">${issue.policy.insured }</span>
		</p>
		<p>
			<label>被保险人年龄：</label>
			<span class="unit">${issue.policy.policyDtl.insuredAge }</span>
		</p>
		<p>
			<label>被保险人证件：</label>
			<span class="unit">${issue.policy.policyDtl.insuredCardType }</span>
		</p>
		<p>
			<label>被保险人号码：</label>
			<span class="unit">${issue.policy.policyDtl.insuredCardNum }</span>
		</p>
		<p>
			<label>证件有效期：</label>
			<span class="unit">${issue.policy.policyDtl.insuredCardValid }</span>
		</p>
		<p>
			<label>险种名称：</label>
			<span class="unit">${issue.policy.prodName }</span>
		</p>
		<p>
			<label>交费方式：</label>
			<span class="unit">${issue.policy.feeFrequency }</span>
		</p>
		<p>
			<label>交费期间：</label>
			<span class="unit">${issue.policy.perm }</span>
		</p>
		<p>
			<label>期间类型：</label>
			<span class="unit">${issue.policy.policyDtl.durationType }</span>
		</p>
		<p>
			<label>保险期间：</label>
			<span class="unit">${issue.policy.policyDtl.duration }</span>
		</p>
		<p>
			<label>保险费：</label>
			<span class="unit">${issue.policy.policyFee }</span>
		</p>
		<p>
			<label>承保日期：</label>
			<span class="unit">${issue.policy.policyDate }</span>
		</p>
		<p>
			<label>承保时间：</label>
			<span class="unit">${issue.policy.policyDtl.policyTime }</span>
		</p>
		<p>
			<label>终止期间：</label>
			<span class="unit">${issue.policy.policyDtl.policyInvalidDate }</span>
		</p>
		<p>
			<label>保单寄送标志：</label>
			<span class="unit">${issue.policy.policyDtl.policySendType }</span>
		</p>
		<p>
			<label>客户签收日期：</label>
			<span class="unit">${issue.policy.clientReceiveDate }</span>
		</p>
		<p>
			<label>回单日期：</label>
			<span class="unit">${issue.policy.billBackDate }</span>
		</p>
		<p>
			<label>状态：</label>
			<span class="unit">${issue.policy.status }</span>
		</p>
		<p>
			<label>是否员工单：</label>
			<span class="unit">${issue.policy.isStaff }</span>
		</p>
		<p>
			<label>是否银行单：</label>
			<span class="unit">${issue.policy.bankCode!=null && issue.policy.bankCode.netFlag==2?"是":"否" }</span>
		</p>
		<p>
			<label>网点名称：</label>
			<span class="unit">${issue.policy.policyDtl.bankName }</span>
		</p>
	</fieldset>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div <c:if test='${issue.status eq "已关闭" }'>class="buttonDisabled"</c:if> <c:if test='${issue.status ne "已关闭" }'>class="button"</c:if>><div class="buttonContent"><button type="submit" <c:if test='${issue.status eq "已关闭" }'>disabled=true</c:if>>确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>