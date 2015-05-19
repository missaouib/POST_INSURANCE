<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/uploaddatamanage/templatefield/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${field.id}"/>
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>模板名称：</label>
			<input type="text" name="templateName" class="input-medium validate[required,maxSize[32]] required" maxlength="32" readonly="readonly" value="${field.tblMemberDataTemplate.templateName }"/>
		</p>
		<p>
			<label>模板类型：</label>
			<select name="templateType" style="width:100px;" disabled="disabled">
				<option value="0" ${field.tblMemberDataTemplate.templateType == 0 ? 'selected="selected"' : ''}>txt</option>
				<option value="1" ${field.tblMemberDataTemplate.templateType == 1 ? 'selected="selected"' : ''}>csv</option>
				<option value="2" ${field.tblMemberDataTemplate.templateType == 2 ? 'selected="selected"' : ''}>dbf</option>
				<option value="3" ${field.tblMemberDataTemplate.templateType == 3 ? 'selected="selected"' : ''}>mdb</option>
				<option value="4" ${field.tblMemberDataTemplate.templateType == 4 ? 'selected="selected"' : ''}>xls</option>
				<option value="5" ${field.tblMemberDataTemplate.templateType == 5 ? 'selected="selected"' : ''}>xlsx</option>
			</select>
		</p>
		<p>
			<label>是否默认模板：</label>
			<select name="status" style="width:100px;" disabled="disabled">
				<option value="0" ${field.tblMemberDataTemplate.status == 0 ? 'selected="selected"' : ''}>非默认</option>			
				<option value="1" ${field.tblMemberDataTemplate.status == 1 ? 'selected="selected"' : ''}>默认</option>
			</select>
		</p>		
		<p>
			<label>标准列名称：</label>
			<input type="text" name="mapColumn" class="input-medium validate[required,maxSize[32]] required" readonly="readonly" maxlength="32" value="${field.mapColumn }"/>
		</p>
		<p>
			<label>是否固定值：</label>
			<select id="isStaticValue" name="isStaticValue" style="width:100px;">
				<option value="0" ${field.isStaticValue == 0 ? 'selected="selected"' : ''}>否</option>			
				<option value="1" ${field.isStaticValue == 1 ? 'selected="selected"' : ''}>是</option>
			</select>
		</p>
		<p>
			<label>固定值：</label>
			<input id="staticValue" type="text" name="staticValue" class="input-medium" maxlength="50" value="${field.staticValue }"/>
		</p>
		<p>
			<label>是否对应列：</label>
			<select id="isUsingMapcolumn" name="isUsingMapcolumn" style="width:100px;">
				<option value="0" ${field.isUsingMapcolumn == 0 ? 'selected="selected"' : ''}>否</option>			
				<option value="1" ${field.isUsingMapcolumn == 1 ? 'selected="selected"' : ''}>是</option>
			</select>
		</p>		
		<p>
			<label>是否列内容：</label>
			<select id="isUsingColumn" name="isUsingColumn" style="width:100px;">
				<option value="0" ${field.isUsingColumn == 0 ? 'selected="selected"' : ''}>否</option>			
				<option value="1" ${field.isUsingColumn == 1 ? 'selected="selected"' : ''}>是</option>
			</select>
		</p>
		<p>
			<label>数据列序号：</label>
			<input id="dataColumn" type="text" name="dataColumn" class="input-medium validate[custom[onlyNumberSp]]" maxlength="2" value="${field.dataColumn }"/>
		</p>
		<p>
			<label>列名：</label>
			<input id="columnName" type="text" name="columnName" class="input-medium" maxlength="50" value="${field.columnName }"/>
		</p>
		<p>
			<label>是否文件名：</label>
			<select id="isUsingFilename" name="isUsingFilename" style="width:100px;">
				<option value="0" ${field.isUsingFilename == 0 ? 'selected="selected"' : ''}>否</option>			
				<option value="1" ${field.isUsingFilename == 1 ? 'selected="selected"' : ''}>是</option>
			</select>
		</p>
		<p>
			<label>是否Sheet名：</label>
			<select id="isUsingSheetname" name="isUsingSheetname" style="width:100px;">
				<option value="0" ${field.isUsingSheetname == 0 ? 'selected="selected"' : ''}>否</option>			
				<option value="1" ${field.isUsingSheetname == 1 ? 'selected="selected"' : ''}>是</option>
			</select>
		</p>
		<p>
			<label>是否多列运算：</label>
			<select id="isUsingMulticolumn" name="isUsingMulticolumn" style="width:100px;">
				<option value="0" ${field.isUsingMulticolumn == 0 ? 'selected="selected"' : ''}>否</option>			
				<option value="1" ${field.isUsingMulticolumn == 1 ? 'selected="selected"' : ''}>是</option>
			</select>
		</p>
		<p>
			<label>多列运算公式：</label>
			<input id="multicolumn" type="text" name="multicolumn" class="input-medium" maxlength="32" value="${field.multicolumn }"/>
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button onclick="return onSubmitCheck();" type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>
<script type="text/javascript">
function onSubmitCheck() {
	// 同时只能选一个是
	// 如果选了对应列，则需要填对应列序号，数值
	// 如果选了多列计算，则需要填多列计算公式
	var isUsingMapcolumn = parseInt($("#isUsingMapcolumn").val(), 10);
	var isUsingColumn = parseInt($("#isUsingColumn").val(), 10);
	var dataColumn = $("#dataColumn").val();
	var isUsingFilename = parseInt($("#isUsingFilename").val(), 10);
	var isUsingSheetname = parseInt($("#isUsingSheetname").val(), 10);
	var isUsingMulticolumn = parseInt($("#isUsingMulticolumn").val(), 10);
	var isStaticValue = parseInt($("#isStaticValue").val(), 10);
	var multicolumn = $("#multicolumn").val();
	var staticValue = $("#staticValue").val();
	var columnName = $("#columnName").val();
	
	if((isUsingMapcolumn + isUsingColumn + isUsingFilename + isUsingSheetname + isUsingMulticolumn + isStaticValue) > 1) {
		alert("数据对应只能选择一种方式。");
		return false;
	}
	
	if(isStaticValue > 0) {
		if(staticValue == null || staticValue == 'undefined' || staticValue == '') {
			alert("请输入固定值。");
			return false;
		}
	}
	
	if((isUsingMapcolumn + isUsingColumn) > 0) {
		var i = parseInt(dataColumn, 10);
		if(isNaN(i) || columnName == null || columnName == '' || columnName == 'undefined') {
			alert("列对应方式，请输入对应的列序号、列名。");
			return false;
		}
	}
	
	if(isUsingMulticolumn == 1) {
		if(multicolumn == null || multicolumn == '' || multicolumn == 'undefined') {
			alert("多列运算方式，请输入多列运算计算公式。");
			return false;
		}
		
		if(/^[0-9\(\)\+\-\*\/]+$/.test(multicolumn)) {  
			try {   
				eval(multicolumn);   
				return true;  
			} catch(ex) {
				alert("请输入正确的四则运算计算公式。"); 
				return false;  
			}
		}
		 
	 	alert("请输入正确的四则运算计算公式。");
	 	return false;
	}
	
	return(true);
}
</script>