<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/bqgl/issue/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>保单号：</label>
			<input type="hidden" name="policy.id" />
			<input name="policy.policyNo" type="text" postField="search_LIKE_policyNo" suggestFields="policyNo" 
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
		<p>
			<label>保全受理项目：</label>
			<input type="text" name="info" class="input-medium" maxlength="32"/>
		</p>
		<p>
			<label>保全复核问题：</label>
			<input name="csRst" type="text" postField="search_LIKE_errorCode" suggestFields="csRst" 
					suggestUrl="/common/lookupBQIssusSuggest" lookupGroup=""/>
			<a class="btnLook" target="dialog" width="500" height="500"  href="/common/lookup2BQIssuesDefine" lookupGroup="">查找带回</a>
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