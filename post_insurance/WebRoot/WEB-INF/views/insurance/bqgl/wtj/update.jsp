<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/bqgl/issue/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${issue.id}"/>
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>保单号：</label>
			<input type="hidden" name="policy.id" value="${issue.policy.id }" class="input-medium validate[required,maxSize[32]] required" />
			<input name="policy.policyNo" type="text" postField="search_LIKE_policyNo" suggestFields="policyNo" 
					suggestUrl="/common/lookupPolicysuggest" lookupGroup="policy" value="${issue.policy.policyNo }"/>
		</p>
		<p>
			<label>保单所属机构</label>
			<input type="text" name="policy.organName" value="${issue.policy.organName }"/>
		</p>
		<p>
			<label>保全受理号：</label>
			<input type="text" name="dealNum" class="input-medium" maxlength="32"/ value="${issue.dealNum }">
		</p>		
		<p>
			<label>保全受理项目：</label>
			<input type="text" name="info" class="input-medium" maxlength="32" value="${issue.info }"/>
		</p>
		<p>
			<label>保全复核问题：</label>
			<input name="csRst" type="text" postField="search_LIKE_errorCode" suggestFields="csRst" 
					suggestUrl="/common/lookupBQIssusSuggest" lookupGroup="" value="${issue.csRst }" class="input-medium validate[required,maxSize[32]] required"/>
			<a class="btnLook" target="dialog" width="500" height="500"  href="/common/lookup2BQIssuesDefine" lookupGroup="">查找带回</a>
		</p>
		<p class="nowrap">
			<label>保全复核问题详细内容：</label>
			<textarea name="remark" cols="50" rows="3">${issue.remark }</textarea>
		</p>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div <c:if test='${issue.status eq "已关闭" }'>class="buttonDisabled"</c:if> <c:if test='${issue.status ne "已关闭" }'>class="button"</c:if>><div class="buttonContent"><button type="submit" <c:if test='${issue.status eq "已关闭" }'>disabled=true</c:if>>确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>