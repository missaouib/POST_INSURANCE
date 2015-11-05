<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/bqgl/issue/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>保单号：</label>
			<input type="hidden" name="policy.id" class="input-medium validate[required,maxSize[32]] required" />
			<input name="policy.policyNo" type="text" postField="policyNo" suggestFields="policyNo" 
					suggestUrl="/common/lookupPolicysuggest" lookupGroup="policy"/>
		</p>
		<p>
			<label>保单所属机构</label>
			<input type="text" name="policy.organName" />
		</p>
		<p>
			<label>保全受理号：</label>
			<input type="text" name="dealNum" class="input-medium" maxlength="32"/>
		</p>		
		<p class="nowrap">
            <label>保全受理项目：</label>
            <input type="text" name="type" class="input-medium" maxlength="32" value="CT"/> 
            <span class="info">如果不是退保项目请删掉CT</span>
        </p>
		<p>
			<label>保全复核问题：</label>
			<input name="info" type="text" postField="search_LIKE_errorCode" suggestFields="info" 
					suggestUrl="/common/lookupBQIssusSuggest" lookupGroup="" class="input-medium validate[required,maxSize[32]] required"/>
			<a class="btnLook" target="dialog" width="500" height="500"  href="/common/lookup2BQIssuesDefine" lookupGroup="">查找带回</a>
		</p>
		<p class="nowrap">
			<label>保全复核问题描述：</label>
			<textarea name="remark" cols="50" rows="3"></textarea>
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