<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/bqgl/reissue/sendRecUpdate" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<input type="hidden" name="ids" value="${reissueIds }">
	<input type="hidden" name="mailFlag" value="${mailFlag }">
	<div class="pageFormContent" layouth="58">
		<p>
			<label>省分收到日期：</label>
			<input type="text" name="provReceiveDate" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value="${reissue.provReceiveDate }" pattern="yyyy-MM-dd"/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>合同寄出日期：</label>
			<input type="text" name="provSentDate" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value="${reissue.provSentDate }" pattern="yyyy-MM-dd"/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>EMS单号：</label>
			<input type="text" name="provExpressNo" class="input-medium validate[required,maxSize[14]] required" maxlength="32"value="${reissue.provExpressNo }"/>
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