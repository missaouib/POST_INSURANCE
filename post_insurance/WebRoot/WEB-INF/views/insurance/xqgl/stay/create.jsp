<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/xqgl/stay/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>保单号：</label>
			<input name="policyNo" type="text" postField="policyNo" suggestFields="policyNo" class="input-medium validate[required] required"
					suggestUrl="/common/lookupPolicysuggest" lookupGroup="" value=""/>
		</p>
		<p>
			<label>投保人</label>
			<input type="text" name="holder" id="holder" class="input-medium" value=""/>
		</p>
		<p>
			<label>退保日期：</label>
			<input type="text" name="csDate" id="csDate" class="date" dateFmt="yyyy-MM-dd" value=""/>
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