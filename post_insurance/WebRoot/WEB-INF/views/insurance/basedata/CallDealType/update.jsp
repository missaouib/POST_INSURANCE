<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/basedata/callDealType/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${basedata.id}"/>
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>类型标记：</label>
			<label>二次回访中心类型：<form:radiobutton path="hftype.flag" value="1"/>&nbsp;地市回访类型<form:radiobutton path="hftype.flag" value="2"/></label>
		</p>
		<p>
			<label>回访类型名称：</label>
			<input type="text" name="typeName" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.typeName }"/>
		</p>
		<p>
			<label>回访类型描述：</label>
			<input type="text" name="typeDesc" class="input-medium" value="${basedata.typeDesc }"/>
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