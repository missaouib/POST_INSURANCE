<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/basedata/bankCode/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${basedata.id}"/>
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>中邮系统网点代码：</label>
			<input type="text" name="cpiCode" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.cpiCode }"/>
		</p>
		<p>
			<label>中邮系统网点名称：</label>
			<input type="text" name="bankCode" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.bankCode }"/>
		</p>
		<p>
			<label>中邮系统网点名称：</label>
			<input type="text" name="name" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.name }"/>
		</p>
		<p>
			<label>银行网点名称：</label>
			<input type="text" name="bankName" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.bankName }"/>
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