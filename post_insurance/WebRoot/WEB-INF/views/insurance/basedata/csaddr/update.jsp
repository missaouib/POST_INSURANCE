<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/basedata/csAddr/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${basedata.id}"/>
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>机构编码：</label>
			<input type="text" name="organCode" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.organCode }"/>
		</p>
		<p>
			<label>所属省：</label>
			<input type="text" name="prov" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.prov }"/>
		</p>
		<p>
			<label>城市名称：</label>
			<input type="text" name="city" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.city }"/>
		</p>
		<p>
			<label>联系人：</label>
			<input type="text" name="linker" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.linker }"/>
		</p>
		<p>
			<label>电话：</label>
			<input type="text" name="phone" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.phone }"/>
		</p>
		<p>
			<label>地址：</label>
			<input type="text" name="addr" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.addr }"/>
		</p>
		<p>
			<label>邮编：</label>
			<input type="text" name="postCode" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.postCode }"/>
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