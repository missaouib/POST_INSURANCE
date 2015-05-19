<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/uploaddatamanage/template/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>模板名称：</label>
			<input type="text" name="templateName" class="input-medium validate[required,maxSize[32]] required" maxlength="32"/>
		</p>
		<p>
			<label>模板类型：</label>
			<select name="templateType" style="width:100px;">
				<option value="0">txt</option>
				<option value="1">csv</option>
				<option value="2">dbf</option>
				<option value="3">mdb</option>
				<option value="4">xls</option>
				<option value="5">xlsx</option>
			</select>
		</p>				
		<p>
			<label>是否默认模板：</label>
			<select name="status" style="width:100px;">
				<option value="0">非默认</option>			
				<option value="1">默认</option>
			</select>
		</p>
		<p>
			<label>访问用户名：</label>
			<input type="text" name="userName" class="input-medium" maxlength="30" />
		</p>
		<p>
			<label>访问密码：</label>
			<input type="text" name="password" class="input-medium" maxlength="30" />
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