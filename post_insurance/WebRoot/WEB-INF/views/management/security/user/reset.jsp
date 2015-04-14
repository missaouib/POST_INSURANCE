<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/management/security/user/reset/password/${userId}" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>修改密码为：</label>
			<input type="password" id="w_validation_userpwd" name="plainPassword" class="input-medium validate[required,minSize[6],maxSize[32]] required" maxlength="32" alt="字母、数字、下划线 6-32位"/>
		</p>
		<p>
			<label>确认密码：</label>
			<input type="password" name="replainPassword" class="input-medium validate[required,minSize[6],maxSize[32],equals[w_validation_userpwd]] required" maxlength="32" alt="字母、数字、下划线 6-32位"/>
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