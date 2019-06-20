<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/basedata/bankCode/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${basedata.id}"/>
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>邮保通代码：</label>
			<input type="text" name="ybtCode" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.ybtCode }"/>
		</p>
		<p>
			<label>中邮网点代码：</label>
			<input type="text" name="cpiCode" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.cpiCode }"/>
		</p>
		<p>
			<label>银行网点名称：</label>
			<input type="text" name="bankCode" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.bankCode }"/>
		</p>
		<p>
			<label>网点名称：</label>
			<input type="text" name="name" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.name }"/>
		</p>
		<p>
			<label>管理机构：</label>
			<input name="organization.id" id="uw_orgId" type="hidden" value="${underwrite.organization.id }"/>
			<input class="validate[required] required" name="organization.orgCode" id="uw_orgCode" type="text" readonly="readonly" style="width: 140px;" value="${basedata.organization.orgCode }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="organization" title="选择机构" width="400">查找带回</a>
		</p>
		<p>
			<label>网点属性：</label>
			<input type="text" name="netFlag" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.netFlag }"/>(邮政:1,银行:2)
		</p>	
		<p>
			<label>网点状态：</label>
			<input type="text" name="status" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${basedata.status }"/>
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