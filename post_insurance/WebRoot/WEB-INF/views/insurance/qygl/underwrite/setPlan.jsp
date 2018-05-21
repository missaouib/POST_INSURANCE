<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/qygl/underwrite/plan" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<input type="hidden" name="id" value="${UnderWrite.id }">
	<div class="pageFormContent" layouth="58">
		<p>
			<label>投保单号：</label>${UnderWrite.formNo }
		</p>
		<p>
			<label>保单号：</label>${UnderWrite.policyNo }
		</p>
		<p>
			<label>投保人：</label>${UnderWrite.holder }
		</p>
		<p>
			<label>计划跟进日期：</label>
			<input type="text" name="planDate" class="date" dateFmt="yyyy-MM-dd" value="<fmt:formatDate value="${UnderWrite.planDate }" pattern="yyyy-MM-dd"/>"/>空值为关闭
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>备注：</label>
			<textarea name="remark" class="input-medium" cols="35" rows="3">${UnderWrite.remark }</textarea>
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