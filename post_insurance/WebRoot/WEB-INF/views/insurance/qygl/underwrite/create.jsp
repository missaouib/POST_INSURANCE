<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/qygl/underwrite/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>所属机构：</label>
			<input name="organization.id" id="uw_orgId" type="hidden" value=""/>
					<input class="validate[required] required" name="organization.name" id="uw_orgName" type="text" readonly="readonly" style="width: 140px;" value=""/>
					<a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="organization" title="选择机构" width="400">查找带回</a>
		</p>
		<p>
			<label>投保单号</label>
			<input type="text" name="formNo" class="input-medium validate[required,maxSize[21] required"/>
		</p>
		<p>
			<label>投保人：</label>
			<input type="text" name="holder" class="input-medium validate[required,maxSize[14]] required" maxlength="32"/>
		</p>
		<p>
			<label>被保人：</label>
			<input type="text" name="insured" class="input-medium validate[required,maxSize[14]] required" maxlength="32"/>
		</p>
		<p>
			<label>投被保人关系：</label>
			<input type="text" name="relation" class="input-medium validate[required,maxSize[14]] required" maxlength="32"/>
		</p>
		<p>
			<label>转核原因：</label>
			<input type="text" name="underwriteReason" class="input-medium validate[required,maxSize[64]] required" maxlength="64"/>
		</p>
		<p>
			<label>保险产品：</label>
			<input type="hidden" name="prd.id" class="input-medium validate[required,maxSize[32]] required" />
			<input name="prd.prdName" type="text" postField="search_LIKE_prdName" suggestFields="prd" class="input-medium validate[required,maxSize[32]] required"
					suggestUrl="/common/lookupPrdSuggest" lookupGroup="prd"/>
		</p>
		<p>
			<label>保费：</label>
			<input type="text" name="policyFee" class="input-medium validate[required,maxSize[14]] required" maxlength="32"/>
		</p>
		<p>
			<label>邮保通录入日期：</label>
			<input type="text" name="ybtDate" id="hfDate1" class="date validate[required] required" dateFmt="yyyy-MM-dd" readonly="true" value=""/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>核心录入日期：</label>
			<input type="text" name="sysDate" id="hfDate1" class="date validate[required] required" dateFmt="yyyy-MM-dd" readonly="true" value=""/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>复核日期：</label>
			<input type="text" name="checkDate" id="hfDate1" class="date validate[required] required" dateFmt="yyyy-MM-dd" readonly="true" value=""/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>