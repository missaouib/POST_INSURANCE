<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/lpgl/create/detail" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
<input hidden="hidden" name="settlement.id" value=${settle.id }"">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>赔案号：</label>
			<input type="text" name="claimsNo" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value=""/>
		</p>
	<fieldset>
		<legend>保单基本信息</legend>
		<p>
			<label>被保险人：</label>
			<span class="unit">${issue.insured }</span>
		</p>
		<p>
			<label>保单号：</label>
			<input name="policy.policyNo" type="text" postField="search_LIKE_policyNo" suggestFields="policyNo" 
					suggestUrl="/common/lookupPolicysuggest" lookupGroup="policy" class="input-medium validate[required,maxSize[32]] required"/>
		</p>
		<p>
			<label>险种：</label>
			<input type="text" name="policy.prdName" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value=""/>
		</p>
		<p>
			<label>保费：</label>
			<input type="text" name="policy.policyFee" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value=""/>
		</p>
		<p>
			<label>生效日期：</label>
			<input type="text" name="policy.plicyValidDate" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value=""/>
		</p>
	</fieldset>
	<fieldset>
		<legend>出险信息</legend>
		<p>
			<label>出险日期：</label>
			<span class="unit">${issue.caseDate }</span>
		</p>
		<p>
			<label>理赔类型：</label>
			<span class="unit">${issue.caseType }</span>
		</p>
	</fieldset>
	<fieldset>
		<legend>报案进程</legend>
		<p>
			<label>首次接触赔案时间：</label>
			<input type="text" name="firstCaseTime" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value=""/>
		</p>
		<p>
			<label>接案人：</label>
			<input type="text" name="caseMan" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value=""/>
		</p>
		<p>
			<label>首次接收理赔材料日期：</label>
			<input type="text" name="firstFileDate" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value=""/>
		</p>
		<p>
			<label>签收人：</label>
			<input type="text" name="firstSignMan" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value=""/>
		</p>
		<p>
			<label>接收齐全理赔材料日期：</label>
			<input type="text" name="allFileDate" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value=""/>
		</p>
		<p>
			<label>签收人：</label>
			<input type="text" name="allSignMan" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value=""/>
		</p>
	</fieldset>
	<fieldset>
		<legend>调查进程</legend>
		<p>
			<label>发起调查日期：</label>
			<input type="text" name="checkDate" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value=""/>
		</p>
		<p>
			<label>发起人：</label>
			<input type="text" name="checker" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value=""/>
		</p>
		<p>
			<label>调查完成日期：</label>
			<input type="text" name="checkDoneDate" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value=""/>
		</p>
		<p>
			<label>调查人：</label>
			<input type="text" name="checkDoneMan" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value=""/>
		</p>
	</fieldset>
	<fieldset>
		<legend>立案进程</legend>
		<p>
			<label>报案日期：</label>
			<span class="unit">${issue.reporteDate }</span>
		</p>
		<p>
			<label>立案日期：</label>
			<span class="unit">${issue.recordDate }</span>
		</p>
		<p>
			<label>结案日期：</label>
			<span class="unit">${issue.closeDate }</span>
		</p>
		<p>
			<label>赔付金额：</label>
			<span class="unit">${issue.payFee }</span>
		</p>
		<p>
			<label>案件状态：</label>
			<span class="unit">${issue.caseStatus }</span>
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