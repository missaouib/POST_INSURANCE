<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/xqgl/stay/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${stay.id}"/>
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>退保申请</legend>
		<p>
			<label>保单号：</label>
			<input name="policyNo" type="text" class="input-medium validate[required] required" value="${stay.policy.policyNo }"/>
		</p>
		<p>
			<label>投保人：</label>
			<input type="text" class="input-medium validate[required] required" name="holder" value="${stay.policy.holder }" />
		</p>
		<p>
			<label>退保日期：</label>
			<input type="text" name="csDate" class="date validate[required,maxSize[12]] required" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value='${stay.csDate }' pattern='yyyy-MM-dd'/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>退保金额：</label>
			<input type="text" name="csMoney" id="csMoney" class="input-medium" value="${stay.csMoney }"/> 元
		</p>
		<p>
			<label>退保次数：</label>
			<input type="text" class="input-medium validate[required] required" name="stayNum" value="${stay.stayNum }" />元
		</p>
		<p class="nowrap">
			<label>挽留详情：</label>
			<textarea type="text" name="remark" class="input-medium" cols="35" rows="3">${stay.remark}</textarea>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>保单详情</legend>
		<p>
			<label>保单机构：</label>
			<input type="text" class="input-medium validate[required] required" name="orginProv" id="orginProv" value="${stay.policy.organization.name }" />
		</p>
		<p>
			<label>被保险人：</label>
			<input type="text" class="input-medium validate[required] required" name="orginProv" id="orginProv" value="${stay.policy.insured }" />
		</p>
		<p>
			<label>保险产品：</label>
			<input type="text" class="input-medium validate[required] required" name="orginProv" id="orginProv" value="${stay.policy.prodName }" />
		</p>
		<p>
			<label>缴费期间：</label>
			<input type="text" class="input-medium validate[required] required" name="orginProv" id="orginProv" value="${stay.policy.perm }" />
		</p>
		<p>
			<label>保费：</label>
			<input type="text" class="input-medium validate[required] required" name="orginProv" id="orginProv" value="${stay.policy.policyFee }" />
		</p>
		<p>
			<label>承保日期：</label>
			<input type="text" class="input-medium validate[required] required" name="orginProv" id="orginProv" value="${stay.policy.policyDate }" />
		</p>
		<p>
			<label>网点：</label>
			<input type="text" class="input-medium validate[required] required" name="orginProv" id="orginProv" value="${stay.policy.bankName }" />
		</p>
	</fieldset>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>