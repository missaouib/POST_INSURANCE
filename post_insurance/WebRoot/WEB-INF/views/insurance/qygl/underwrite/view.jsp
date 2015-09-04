<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" id="underwriteForm" action="${contextPath}/qygl/underwrite/reopen" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${underwrite.id}"/>
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>人核件基本信息</legend>
		<p>
			<label>市县机构：</label>
			<span class="unit">${underwrite.organization.name }</span>
		</p>
		<p>
			<label>投保单号：</label>
			<span class="unit">${underwrite.formNo }</span>
		</p>
		<p>
			<label>保单号：</label>
			<span class="unit">${underwrite.policyNo }</span>
		</p>
		<p>
			<label>投保人：</label>
			<span class="unit">${underwrite.holder }</span>
		</p>
		<p>
			<label>被保险人：</label>
			<span class="unit">${underwrite.insured }</span>
		</p>
		<p>
			<label>投被保人关系：</label>
			<span class="unit">${underwrite.relation }</span>
		</p>
		<p>
			<label>转核原因：</label>
			<span class="unit">${underwrite.underwriteReason }</span>
		</p>
		<p>
			<label>邮保通录入时间：</label>
			<span class="unit"><fmt:formatDate value="${underwrite.ybtDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>核心录入时间：</label>
			<span class="unit"><fmt:formatDate value="${underwrite.sysDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>复核时间：</label>
			<span class="unit"><fmt:formatDate value="${underwrite.checkDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>是否下问题件：</label>
			<span class="unit">${underwrite.issueFlag==1?"是":"否" }</span>
		</p>
		<p>
			<label>问题描述：</label>
			<span class="unit">${underwrite.errorDesc }</span>
		</p>
		<p>
			<label>核保时间：</label>
			<span class="unit"><fmt:formatDate value="${underwrite.underwriteDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>是否下通知书：</label>
			<span class="unit">${underwrite.isLetter?"是":"否" }</span>
		</p>
		<p>
			<label>签单日期：</label>
			<span class="unit"><fmt:formatDate value="${underwrite.signDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>收到合同日：</label>
			<span class="unit"><fmt:formatDate value="${underwrite.provReceiveDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>客户签收日：</label>
			<span class="unit"><fmt:formatDate value="${underwrite.clientReceiveDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>回执回销日：</label>
			<span class="unit"><fmt:formatDate value="${underwrite.signInputDate }" pattern="yyyy-MM-dd"/></span>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>省分人核件流转记录</legend>
		<p>
			<label>分公司寄出时间：</label>
			<span class="unit"><fmt:formatDate value="${underwrite.provSendDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>分公司寄出EMS：</label>
			<span class="unit">${underwrite.provEmsNo }</span>
		</p>
	</fieldset>
	<fieldset>
		<legend>地市人核件流转记录</legend>
		<p>
			<label>地市收到日期：</label>
			<span class="unit"><fmt:formatDate value="${underwrite.cityReceiveDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>地市寄出日期：</label>
			<span class="unit"><fmt:formatDate value="${underwrite.citySendDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>地市寄出EMS：</label>
			<span class="unit">${underwrite.cityEmsNo }</span>
		</p>
	</fieldset>
	<fieldset>
		<legend>县区人核件流转记录</legend>
		<p>
			<label>县区收到日期：</label>
			<span class="unit"><fmt:formatDate value="${underwrite.areaReceiveDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>县区寄出日期：</label>
			<span class="unit"><fmt:formatDate value="${underwrite.areaSendDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>县区寄出EMS：</label>
			<span class="unit">${underwrite.areaEmsNo }</span>
		</p>
	</fieldset>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>