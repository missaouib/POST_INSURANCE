<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
	<input type="hidden" name="id" value="${policy.id}"/>
	<input type="hidden" name="status" value="${policy.status}"/>
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>保单基本信息</legend>
		<p>
			<label>投保单号：</label>
			<span class="unit">${policy.formNo }</span>
		</p>
		<p>
			<label>保单号：</label>
			<span class="unit">${policy.policyNo }</span>
		</p>
		<p>
			<label>机构：</label>
			<span class="unit">${policy.organName }</span>
		</p>
		<p>
			&nbsp;
		</p>
		<p>
			<label>客户姓名：</label>
			<span class="unit">${policy.holder }</span>
		</p>
		<p>
			<label>性别：</label>
			<span class="unit">${policy.policyDtl.holderSexy }</span>
		</p>
		<p>
        <label>电话：</label>
        <span class="unit">${policy.policyDtl.holderPhone }</span>
    </p>
    <p>
        <label>电话：</label>
        <span class="unit">${policy.policyDtl.holderMobile }</span>
    </p>
    <p>
        <label>地址：</label>
        <span class="unit">${policy.policyDtl.holderAddr }</span>
    </p>
    <p>
        <label>证件类型：</label>
        <span class="unit">${policy.policyDtl.holderCardType }</span>
    </p>
    <p>
        <label>证件号码：</label>
        <span class="unit">${policy.policyDtl.holderCardNum }</span>
    </p>
    <p>
        <label>证件有效期：</label>
        <span class="unit">${policy.policyDtl.holderCardValid }</span>
    </p>
    <p>
        <label>Email：</label>
        <span class="unit">${policy.policyDtl.holderEmail }</span>
    </p>
    <p>
        <label>投保年龄：</label>
        <span class="unit">${policy.policyDtl.holderAge }</span>
    </p>
    <p>
			<label>被保人是投保人的：</label>
			<span class="unit">${policy.policyDtl.relation }</span>
		</p>
		<p>
			<label>被保险人：</label>
			<span class="unit">${policy.insured }</span>
		</p>
		<p>
			<label>被保险人年龄：</label>
			<span class="unit">${policy.policyDtl.insuredAge }</span>
		</p>
		<p>
			<label>被保险人证件：</label>
			<span class="unit">${policy.policyDtl.insuredCardType }</span>
		</p>
		<p>
			<label>被保险人号码：</label>
			<span class="unit">${policy.policyDtl.insuredCardNum }</span>
		</p>
		<p>
			<label>证件有效期：</label>
			<span class="unit">${policy.policyDtl.insuredCardValid }</span>
		</p>
		<p>
			<label>险种名称：</label>
			<span class="unit">${policy.prodName }</span>
		</p>
		<p>
			<label>交费方式：</label>
			<span class="unit">${policy.feeFrequency }</span>
		</p>
		<p>
			<label>交费期间：</label>
			<span class="unit">${policy.perm }</span>
		</p>
		<p>
			<label>期间类型：</label>
			<span class="unit">${policy.policyDtl.durationType }</span>
		</p>
		<p>
			<label>保险期间：</label>
			<span class="unit">${policy.policyDtl.duration }</span>
		</p>
		<p>
			<label>保险费：</label>
			<span class="unit">${policy.policyFee }</span>
		</p>
		<p>
			<label>承保日期：</label>
			<span class="unit">${policy.policyDate }</span>
		</p>
		<p>
			<label>承保时间：</label>
			<span class="unit">${policy.policyDtl.policyTime }</span>
		</p>
		<p>
			<label>终止期间：</label>
			<span class="unit">${policy.policyDtl.policyInvalidDate }</span>
		</p>
		<p>
			<label>保单寄送标志：</label>
			<span class="unit">${policy.policyDtl.policySendType }</span>
		</p>
		<p>
			<label>客户签收日期：</label>
			<span class="unit">${policy.clientReceiveDate }</span>
		</p>
		<p>
			<label>回单日期：</label>
			<span class="unit">${policy.billBackDate }</span>
		</p>
		<p>
			<label>状态：</label>
			<span class="unit">${policy.status }</span>
		</p>
		<p>
			<label>是否员工单：</label>
			<span class="unit">${policy.isStaff }</span>
		</p>
		<p>
			<label>是否银行单：</label>
			<span class="unit">${policy.bankCode!=null && policy.bankCode.netFlag==2?"是":"否" }</span>
		</p>
		<p>
			<label>网点名称：</label>
			<span class="unit">${policy.policyDtl.bankName }</span>
		</p>
	</fieldset>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>