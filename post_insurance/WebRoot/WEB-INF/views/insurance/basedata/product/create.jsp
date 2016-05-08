<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/basedata/prd/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>产品代码：</label>
			<input type="text" name="prdCode" class="input-medium validate[required,maxSize[32]] required" maxlength="32"/>
		</p>
		<p>
			<label>产品名称：</label>
			<input type="text" name="prdName" class="input-medium validate[required,maxSize[32]] required" maxlength="32"/>
		</p>
		<p>
			<label>产品全称：</label>
			<input type="text" name="prdFullName" class="input-medium validate[required,maxSize[32]] required" maxlength="32"/>
		</p>
		<p>
			<label>产品状态：</label>
			<select id="prdStatus" name="prdStatus" class="combox">
				<option value="0">停售</option>
				<option value="1">正常销售</option>
			</select>
		</p>
		<p>
			<label>每份金额：</label>
			<input type="text" name="prdPerMoney" class="input-medium" maxlength="32"/>
		</p>
		<p>
			<label>交费期间：</label>
			<input type="text" name="prdPerm" class="input-medium" maxlength="32"/>
		</p>
		<p>
			<label>保险期间：</label>
			<input type="text" name="duration" class="input-medium" maxlength="32"/>
		</p>
		<p>
			<label>最高理赔倍数：</label>
			<input type="text" name="multiple" class="input-medium" maxlength="32"/>
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