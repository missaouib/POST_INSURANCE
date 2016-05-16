<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/lpgl/task/create" class="required-validate pageForm" enctype="multipart/form-data" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
		<fieldset>
		<legend>保单基本信息</legend>
		<p>
			<label>保单号：</label>
			<input name="policyNo" type="text" postField="search_LIKE_policyNo" suggestFields="policyNo" 
					suggestUrl="/lpgl/lookupSettlesuggest" lookupGroup="" class="input-medium validate[required,maxSize[32]] required" value="${settleDtl.policyNo }"/>
		</p>
		<p>
			<label>出保险人：</label>
			<input type="text" name="insured" class="input-medium validate[required,maxSize[32]] required" readonly="readonly" maxlength="32" value="${settleDtl.insured }"/>
		</p>
		<p>
			<label>险种：</label>
			<input type="text" name="prodName" class="input-medium validate[required,maxSize[32]] required" readonly="readonly" maxlength="32" value="${settleDtl.prodName }"/>
		</p>
		<p>
			<label>保费：</label>
			<input type="text" name="policyFee" class="input-medium validate[required,maxSize[32]] required" readonly="readonly" maxlength="32" value="${settleDtl.policyFee }"/>
		</p>
		<p>
			<label>生效日期：</label>
			<input type="text" name="policyDate" class="input-medium validate[required,maxSize[32]] required" readonly="readonly" maxlength="32" value="${settleDtl.policyDate }"/>
		</p>
	</fieldset>
	<fieldset>
		<legend>出险信息</legend>
		<p>
			<label>出险日期：</label>
			<input type="text" name="caseDate" class="input-medium validate[required,maxSize[32]] required" readonly="readonly" maxlength="32" value="${settleDtl.caseDate }"/>
		</p>
		<p>
			<label>理赔类型：</label>
			<input type="text" name="caseType" class="input-medium validate[required,maxSize[32]] required" readonly="readonly" maxlength="32" value="${settleDtl.caseType }"/>
		</p>
	</fieldset>
	<fieldset>
		<legend>调查进程</legend>
		<p>
			<label>调查起期：</label>
			<input type="text" name="createTime" class="input-medium validate[required,maxSize[32]] required" readonly="readonly" maxlength="32" value="${task.createTime }"/>
		</p>
		<p>
			<label>结案日期：</label>
			<input type="text" name="closeDate" id="closeDate" class="date" dateFmt="yyyy-MM-dd" readonly="readonly" value="${task.checkEndDate }"/>
		</p>
		<p>
			<label>调查要求：</label>
			<input type="text" name="checkReq" class="input-medium validate[maxSize[32]]" maxlength="32" value=""/>
		</p>
		<p>
			<label>调查人：</label>
			<p>
			<label>接收人：</label>
			<input name="checker" type="text" postField="realname" suggestFields="realname" 
					suggestUrl="/common/lookupClaimUserSuggest" lookupGroup=""/>
					<a class="btnLook" href="${contextPath }/common/lookup4User?role=地市理赔" lookupGroup="user" title="选择用户" width="650">查</a>
		</p>
		</p>
		<p>
			<label>调查地点：</label>
			<input type="text" name="checkerAddr" class="input-medium validate[maxSize[32]]" maxlength="32" value=""/>
		</p>
		<p>
			<label>查勘费：</label>
			<input type="text" name="checkFee" class="input-medium validate[maxSize[32]]" maxlength="32" value=""/>
		</p>
		<p>
			<label>附件</label>
			<input type="file" name="file">
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