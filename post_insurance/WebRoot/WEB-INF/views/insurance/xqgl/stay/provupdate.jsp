<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/bqgl/stay/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${stay.id}"/>
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>退保申请</legend>
		<p>
			<label>保单号：</label>
			<input name="policyNo" type="text" postField="policyNo" suggestFields="policyNo" class="input-medium validate[required] required"
					suggestUrl="/common/lookupCsAddrSuggest" lookupGroup="" value="${stay.policyNo }"/>
		</p>
		<p>
			<label>投保人</label>
			<input type="text" class="input-medium validate[required] required" name="orginProv" id="orginProv" value="${stay.hoder }" />
		</p>
		<p>
			<label>退保日期：</label>
			<input type="text" name="csDate" class="date validate[required,maxSize[12]] required" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value='${stay.csDate }' pattern='yyyy-MM-dd'/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p class="nowrap">
			<label>挽留详情：</label>
			<textarea type="text" name="remark" class="input-medium" cols="35" rows="3">${stay.remark}</textarea>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>保单详情</legend>
		<p class="nowrap">
			<label>挽留详情：</label>
			<textarea type="text" name="remark" class="input-medium" cols="35" rows="3">${stay.remark}</textarea>
		</p>
	</fieldset>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div <c:if test='${stay.status eq "已关闭" }'>class="buttonDisabled"</c:if> <c:if test='${stay.status ne "已关闭" }'>class="button"</c:if>><div class="buttonContent"><button type="submit" <c:if test='${stay.status eq "已关闭" }'>disabled=true</c:if>>确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>