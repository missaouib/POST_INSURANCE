<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/qygl/underwrite/sendRecUpdate" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<input type="hidden" name="id" value="${id }">
	<input type="hidden" name="flag" value="${flag }">
	<div class="pageFormContent" layouth="58">
		<p>
			<label>人核件寄出日期：</label>
			<input type="text" name="sendDate" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value="${underwrite.sendDate }" pattern="yyyy-MM-dd"/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>EMS单号：</label>
			<input type="text" name="emsNo" class="input-medium validate[required,maxSize[14]] required" maxlength="32"value="${underwrite.emsNo }"/>
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">提交确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close" >关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>