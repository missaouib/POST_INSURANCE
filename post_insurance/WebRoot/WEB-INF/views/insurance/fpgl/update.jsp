<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/fpgl/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${req.id}"/>
	<input type="hidden" name="status" value="${req.status }">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>保单号：</label>
			<input name="policy.id" type="hidden" value="${req.policy.id }">
			<input name="policy.policyNo" type="text" postField="search_LIKE_policyNo" suggestFields="policyNo" 
					suggestUrl="/common/lookupPolicysuggest" lookupGroup="policy" class="input-medium validate[required,maxSize[32]] required" value="${req.policy.policyNo }"/>
		</p>
		<p>
			<label>保单所属机构</label>
			<input type="text" name="policy.organName" readonly="readonly" value="${req.policy.organization.name }"/>
		</p>
		<p>
			<label>发票标记：</label>
			<label>首期<form:radiobutton path="req.flag" value="首期"/>续期<form:radiobutton path="req.flag" value="续期"/></label>
		</p>
		<p>
			<label>发票金额：</label>
			<input type="text" name="fee" maxlength="32" class="input-medium validate[required,maxSize[32]] required" value="${req.fee }"/>
		</p>
		<p>
			<label>缴费日期：</label>
			<input type="text" name="feeDate" class="date validate[required,maxSize[32]] required" dateFmt="yyyy-MM-dd" readonly="true" value='<fmt:formatDate value="${req.feeDate}" pattern="yyyy-MM-dd"/>'/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>发票收取地址：</label>
			<input type="text" name="reqAddr" class="input-medium" value="${req.reqAddr }"/>
		</p>
		<p>
			<label>发票收件人：</label>
			<input type="text" name="reqMan" class="input-medium" value="${req.reqMan }"/>
		</p>
		<p>
			<label>发票收件人电话：</label>
			<input type="text" name="phone" class="input-medium" value="${req.phone }"/>
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