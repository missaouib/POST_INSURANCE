<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/hfgl/ResetStatus/${cfl.id}" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layouth="58">
		<p>
			<label>重置电话号码：</label>
			<textarea name="phone" class="validate[required, maxSize[32]] required" cols="30" rows="2"></textarea>
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">修改</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close" >关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>