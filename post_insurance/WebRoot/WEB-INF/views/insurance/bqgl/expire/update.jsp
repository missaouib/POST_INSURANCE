<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/bqgl/expire/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<input type="hidden" name="csExpireId" value="${expire.id }">
	<div class="pageFormContent" layouth="58">
		<p>
		<label>既往跟进情况：</label>
		<c:forEach var="item" items="${expireDtls}">
		${item.dealDesc}；${item.dealMan}；${item.dealTime}<br>
		</c:forEach>
		</p>
		<p>
			<label>本次跟进情况：</label>
			<input type="text" name="dealDesc" class="input-medium validate[required,maxSize[14]] required" maxlength="32"value=""/>
		</p>
		<p>
			<label>跟进人：</label>
			<input type="text" name="dealMan" class="input-medium validate[required,maxSize[14]] required" maxlength="32"value=""/>
		</p>
		<p>
			<label>跟进日期：</label>
			<input type="text" name="dealTime" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="${today }" pattern="yyyy-MM-dd"/>"/>
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