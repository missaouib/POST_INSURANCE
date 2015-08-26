<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/hfgl/issue/setMailDate" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<input type="hidden" name="ids" value="${hfIds }">
	<div class="pageFormContent" layouth="58">
		<p>
			<label>回访函寄出日期：</label>
			<input type="text" name="mailDate" class="date" dateFmt="yyyy-MM-dd" readonly="true" value=""/>
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