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
			<label>客户姓名：</label>
			<span class="unit">${policy.holder }</span>
		</p>
		<p>
			<label>险种名称：</label>
			<span class="unit">${policy.prodName }</span>
		</p>
		<p>
			<label>交费方式：</label>
			<span class="unit">${policy.feeFequency }</span>
		</p>
		<p>
			<label>交费期间：</label>
			<span class="unit">${policy.perm }</span>
		</p>
		<p>
			<label>状态：</label>
			<span class="unit">${policy.stratus }</span>
		</p>
	</fieldset>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>