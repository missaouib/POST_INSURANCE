<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/uploaddatamanage/templatefieldrule/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${rule.id}"/>
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>模板名称：</label>
			<input type="text" name="templateName" class="input-medium validate[required,maxSize[32]] required" maxlength="32" readonly="readonly" value="${rule.tblMemberDataTemplateField.tblMemberDataTemplate.templateName}"/>
		</p>
		<p>
			<label>模板类型：</label>
			<select name="templateType" style="width:100px;" disabled="disabled">
				<option value="0" ${rule.tblMemberDataTemplateField.tblMemberDataTemplate.templateType == 0 ? 'selected="selected"' : ''}>txt</option>
				<option value="1" ${rule.tblMemberDataTemplateField.tblMemberDataTemplate.templateType == 1 ? 'selected="selected"' : ''}>csv</option>
				<option value="2" ${rule.tblMemberDataTemplateField.tblMemberDataTemplate.templateType == 2 ? 'selected="selected"' : ''}>dbf</option>
				<option value="3" ${rule.tblMemberDataTemplateField.tblMemberDataTemplate.templateType == 3 ? 'selected="selected"' : ''}>mdb</option>
				<option value="4" ${rule.tblMemberDataTemplateField.tblMemberDataTemplate.templateType == 4 ? 'selected="selected"' : ''}>xls</option>
				<option value="5" ${rule.tblMemberDataTemplateField.tblMemberDataTemplate.templateType == 5 ? 'selected="selected"' : ''}>xlsx</option>
			</select>
		</p>				
		<p>
			<label>是否默认模板：</label>
			<select name="status" style="width:100px;" disabled="disabled">
				<option value="0" ${rule.tblMemberDataTemplateField.tblMemberDataTemplate.status == 0 ? 'selected="selected"' : ''}>非默认</option>			
				<option value="1" ${rule.tblMemberDataTemplateField.tblMemberDataTemplate.status == 1 ? 'selected="selected"' : ''}>默认</option>
			</select>
		</p>
		<p>
			<label>标准列名：</label>
			<input type="text" name="fieldName" class="input-medium validate[required,maxSize[32]] required" maxlength="32" readonly="readonly" value="${rule.tblMemberDataTemplateField.mapColumn}"/>
		</p>
		<p>
			<label>规则名称：</label>
			<input type="text" name="ruleName" class="input-medium validate[required,maxSize[32]] required" maxlength="32" readonly="readonly" value="${rule.ruleName}"/>
		</p>
		<p>
			<label>分割符</label>
			<input type="text" name="splitChar" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${rule.splitChar}" />
		</p>
		<p>
			<label>取值序号：</label>
			<input type="text" name="valueIndex" class="input-medium validate[required,custom[onlyNumberSp]] required" maxlength="2" value="${rule.valueIndex}" />
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