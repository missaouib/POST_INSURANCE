<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/basedata/sales/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${basedata.id}"/>
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>机构代码：</label>
			<input type="text" name="organCode" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.organCode }"/>
		</p>
		<p>
			<label>机构名称：</label>
			<input type="text" name="organName" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.organName }"/>
		</p>
		<p>
			<label>销售人员姓名：</label>
			<input type="text" name="salesName" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.salesName }"/>
		</p>
		<p>
			<label>电话：</label>
			<input type="text" name="phone" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.phone }"/>
		</p>
		<p>
			<label>网点：</label>
			<input type="text" name="bankName" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.bankName }"/>
		</p>	
		<p>
			<label>网点状态：</label>
			<input type="text" name="status" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.status }"/> 0：失效；1：有效
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