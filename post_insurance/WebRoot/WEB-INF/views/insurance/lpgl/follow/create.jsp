<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/lpgl/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>机构：</label>
			<input type="text" name="username" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${user.username }"/>
		</p>
		<p>
			<label>出险人：</label>
			<input type="text" name="username" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${user.username }"/>
		</p>
		<p>
			<label>报案人：</label>
			<input type="text" name="username" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${user.username }"/>
		</p>
		<p>
			<label>报案人电话 ：</label>
			<input type="text" name="username" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${user.username }"/>
		</p>
		<p>
			<label>出险日期：</label>
			<input type="text" name="username" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${user.username }"/>
		</p>
		
		<p>
			<label>理赔类型：</label>
			<input type="text" name="username" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${user.username }"/>
		</p>
		<p>
			<label>报案日期：</label>
			<input type="text" name="username" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${user.username }"/>
		</p>
		<p>
			<label>立案日期：</label>
			<input type="text" name="username" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${user.username }"/>
		</p>
		<p>
			<label>结案日期：</label>
			<input type="text" name="username" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${user.username }"/>
		</p>
		<p>
			<label>赔付金额：</label>
			<input type="text" name="username" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${user.username }"/>
		</p>
		<p>
			<label>案件状态：</label>
			<input type="text" name="username" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${user.username }"/>
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