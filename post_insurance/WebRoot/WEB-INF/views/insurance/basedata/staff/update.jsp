<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/basedata/staff/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${basedata.id}"/>
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>姓名：</label>
			<input type="text" name="name" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.name }"/>
		</p>
		<p>
			<label>证件号码：</label>
			<input type="text" name="idCard" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.idCard }"/>
		</p>
		<p>
			<label>产品状态：</label>
			<form:select path="basedata.status" id="status" name="prdStatus" class="combox">
				<form:option value="1">正常</form:option>
				<form:option value="0">退休</form:option>
				<form:option value="2">劳务</form:option>
			</form:select>
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