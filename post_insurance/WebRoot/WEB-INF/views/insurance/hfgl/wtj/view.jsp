<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" id="issueForm" action="${contextPath}/hfgl/issue/reopen" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${issue.id}"/>
	<input type="hidden" name="status" value="${issue.status}"/>
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>回访不成功件基本信息</legend>
		<p>
			<label>工单编号：</label>
			<span class="unit">${issue.issueNo }</span>
		</p>
		<p>
			<label>工单状态：</label>
			<span class="unit">${issue.status }</span>
		</p>
		<p>
			<label>保单号：</label>
			<span class="unit">${issue.policy.policyNo }</span>
		</p>
		<p>
			<label>承保日期：</label>
			<span class="unit"><fmt:formatDate value="${issue.policy.policyDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>签收日期：</label>
			<span class="unit"><fmt:formatDate value="${issue.billBackDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>险种名称：</label>
			<span class="unit">${issue.policy.prodName }</span>
		</p>
		<p>
			<label>客户姓名：</label>
			<span class="unit">${issue.policy.holder }</span>
		</p>
		<p>
			<label>客户电话：</label>
			<span class="unit">${issue.holderPhone }</span>
		</p>
		<p>
			<label>客户手机：</label>
			<span class="unit">${issue.holderMobile }</span>
		</p>
		<p>
			<label>所属机构：</label>
			<span class="unit">${issue.issueOrg }</span>
		</p>
		<p class="nowrap">
			<label>出单网点：</label>
			<span class="unit">${issue.bankName }</span>
		</p>
		<p>
			<label>回访日期：</label>
			<span class="unit"><fmt:formatDate value="${issue.callDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>不成功日期：</label>
			<span class="unit"><fmt:formatDate value="${issue.issueDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>下发日期：</label>
			<span class="unit">${issue.operateTime }</span>
		</p>
		<p>
			<label>工单类型：</label>
			<span class="unit">${issue.issueType }</span>
		</p>
		<p class="nowrap">
			<label>一访内容：</label>
			<span class="unit">${issue.issueContent }</span>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>省分二访中心回访处理详情</legend>
		<p>
           <label>二访类别：</label>
           <span class="unit">${issue.hqDealTypeElse }</span>
        </p>
        <p>
           <label>客户资料备注：</label>
           <span class="unit">${issue.clientRemark }</span>
        </p>
        <dl>
           <dt>二访详情：</dt>
           <dd><textarea name="textarea1" cols="80" rows="3">${issue.hqDealRst }</textarea></dd>
        </dl>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
		<p>
			<label>二访结果：</label>
			<span class="unit">${issue.hqDealType }</span>
		</p>
		<p>
			<label>经办人：</label>
			<span class="unit">${issue.hqDealMan }</span>
		</p>
		<p>
			<label>回访日期：</label>
			<span class="unit"><fmt:formatDate value='${issue.hqDealDate }' pattern='yyyy-MM-dd'/></span>
		</p>
		<p>
			<label>拨打电话：</label>
			<span class="unit">${issue.phoneNum }</span>
		</p>
		<p>
			<label>通话开始：</label>
			<span class="unit">${issue.phoneStart }</span>
		</p>
		<p>
			<label>通话结束：</label>
			<span class="unit">${issue.phoneEnd }</span>
		</p>
		<p>
			<label>通话时长：</label>
			<span class="unit">${issue.phoneTime }</span>
		</p>
	</fieldset>
	<fieldset>
		<p>
			<label>可再访情况：</label>
			<span class="unit">${issue.canCallAgainRemark }</span>
		</p>
	</fieldset>
	<fieldset>
		<p>
			<label>信函标记：</label>
			<span class="unit">${issue.hasLetter }</span>
		</p>
		<p>
			<label>发信函时间：</label>
			<span class="unit">${issue.letterDate }</span>
		</p>
		<p>
			<label>二访结果：</label>
			<span class="unit">${issue.hqIssueType }</span>
		</p>
		<p>
			<label>地市上门结果：</label>
			<span class="unit">${issue.dealType }</span>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>市县回访情况</legend>
		<p class="nowrap">
			<label>回访处理记录：</label>
			<span class="unit">${issue.dealDesc }</span>
		</p>
		<p class="nowrap">
			<label>经办人：</label>
			<span class="unit">${issue.dealMan }</span>
		</p>
		<p class="nowrap">
			<label>经办日期：</label>
			<span class="unit">${issue.dealTime }</span>
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