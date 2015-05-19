<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/uploaddatamanage/template/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${template.id}"/>
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>模板名称：</label>
			<input type="text" name="templateName" class="input-medium validate[required,maxSize[32]] required" maxlength="32" readonly="readonly" value="${template.templateName }"/>
		</p>
		<p>
			<label>模板类型：</label>
			<select name="templateType" style="width:100px;">
				<option value="0" ${template.templateType == 0 ? 'selected="selected"' : ''}>txt</option>
				<option value="1" ${template.templateType == 1 ? 'selected="selected"' : ''}>csv</option>
				<option value="2" ${template.templateType == 2 ? 'selected="selected"' : ''}>dbf</option>
				<option value="3" ${template.templateType == 3 ? 'selected="selected"' : ''}>mdb</option>
				<option value="4" ${template.templateType == 4 ? 'selected="selected"' : ''}>xls</option>
				<option value="5" ${template.templateType == 5 ? 'selected="selected"' : ''}>xlsx</option>
			</select>
		</p>				
		<p>
			<label>是否默认模板：</label>
			<select name="status" style="width:100px;">
				<option value="0" ${template.status == 0 ? 'selected="selected"' : ''}>非默认</option>			
				<option value="1" ${template.status == 1 ? 'selected="selected"' : ''}>默认</option>
			</select>
		</p>
		<p>
			<label>访问用户名：</label>
			<input type="text" name="userName" class="input-medium" maxlength="30" value="${template.userName }"/>
		</p>
		<p>
			<label>访问密码：</label>
			<input type="text" name="password" class="input-medium" maxlength="30" value="${template.password }"/>
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