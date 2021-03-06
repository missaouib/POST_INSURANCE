<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/bqgl/reissue/sendRecUpdate" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<input type="hidden" name="ids" value="${reissueIds }">
	<input type="hidden" name="mailFlag" value="${mailFlag }">
	<div class="pageFormContent" layouth="58">
		<p>
			<label>补发合同收到日期：</label>
			<input type="text" name="cityReceiveDate" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value="${reissue.cityReceiveDate }" pattern="yyyy-MM-dd"/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>接收人：</label>
			<input type="text" name="cityReceiver" class="input-medium validate[required,maxSize[14]] required" maxlength="32"value="${reissue.cityReceiver }"/>
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