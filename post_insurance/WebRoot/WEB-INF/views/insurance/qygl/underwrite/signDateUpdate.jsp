<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/qygl/underwrite/signDateUpdate" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<input type="hidden" name="id" value="${id }">
	<div class="pageFormContent" layouth="58">
		<p>
			<label>客户签收日期：</label>
			<input type="text" name="clientReceiveDate" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="${underwrite.clientReceiveDate }"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>核心回执回销日期：</label>
			<input type="text" name="signInputDate" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="${underwrite.signInputDate }"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
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