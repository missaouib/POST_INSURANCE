<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/basedata/conservationError/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${basedata.id}"/>
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>保全常见错误定义：</label>
			<input type="text" name="errorCode" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.errorCode }"/>
		</p>
		<p>
			<label>常见错误描述：</label>
			<input type="text" name="errorDesc" class="input-medium" value="${basedata.errorDesc }"/>
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