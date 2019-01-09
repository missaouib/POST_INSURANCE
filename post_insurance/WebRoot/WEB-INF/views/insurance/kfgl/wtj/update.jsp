<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/kfgl/issue/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${issue.id}"/>
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>工单基本信息</legend>
		<p>
			<label>工单编号：</label>
			<span class="unit">${issue.issueNo }</span>
		</p>
		<p>
			<label>工单状态：</label>
			<span class="unit">${issue.status }</span>
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
			<label>证件号码：</label>
			<span class="unit">${issue.idCard}</span>
		</p>
		<p>
			<label>保单号：</label>
			<span class="unit">${issue.policy.policyNo }</span>
		</p>
		<p>
			<label>所属机构：</label>
			<span class="unit">${issue.organization.shortName }</span>
		</p>
		<p class="nowrap">
			<label>出单网点：</label>
			<span class="unit">${issue.bankName }</span>
		</p>
		<p>
			<label>回访日期：</label>
			<span class="unit">${issue.callDate }</span>
		</p>
		<p>
			<label>不成功日期：</label>
			<span class="unit">${issue.issueDate }</span>
		</p>
		<p>
			<label>下发日期：</label>
			<span class="unit">${issue.issueTime }</span>
		</p>
		<p>
			<label>工单类型：</label>
			<span class="unit">${issue.issueType }</span>
		</p>
		<p class="nowrap">
			<label>工单内容：</label>
			<span class="unit">${issue.issueContent }</span>
		</p>
	</fieldset>
	<c:if test="${not empty issue.reopenReason}">
	<div class="divider"></div>
	<fieldset>
		<legend>重新打开情况</legend>
		<p class="nowrap">
			<label>退回原因：</label>${issue.reopenReason }
		</p>
		<p class="nowrap">
			处理人：${issue.reopenUser.realname }；&nbsp;&nbsp;&nbsp;&nbsp;退回日期：${issue.reopenDate }
		</p>
	</fieldset>
	</c:if>
	<div class="divider"></div>
	<fieldset>
		<legend>工单处理录入</legend>
		<p class="nowrap">
			<label>工单处理结果：</label>
			<textarea name="result" cols="50" rows="4" class="required">${issue.result }<c:if test="${empty issue.result}">xxx 理财经理于 xx月xx日致电客户${issue.policy.holder}，致电电话为___________，谈话时长____分钟，处理如下。
沟通内容：
客户反馈：
</c:if>
			</textarea>
		</p>
		<p class="nowrap">
			<label>经办人：</label>
			<input type="text" name="dealMan" class="input-medium validate[required,maxSize[12]] required" maxlength="12" value="${issue.dealMan }"/>
		</p>
		<p class="nowrap">
			<label>经办日期：</label>
			<input type="text" name="dealTime" class="date validate[required,maxSize[32]] required" dateFmt="yyyy-MM-dd" readonly="true" value="${issue.dealTime }"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p class="nowrap">
			<label>复核人：</label>
			<input type="text" name="cityReviewer" class="input-medium validate[required,maxSize[12]] required" maxlength="12" value="${issue.cityReviewer }"/>
		</p>
		<p class="nowrap">
			<label>复核意见：</label>
			<input type="text" name="cityReviewRst" class="input-medium validate[required,maxSize[12]] required" maxlength="12" value="${issue.cityReviewRst eq null?'同意，复核无误。': issue.cityReviewRst}"/>
		</p>
	</fieldset>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div <c:if test='${issue.status eq "已结案" }'>class="buttonDisabled"</c:if> <c:if test='${issue.status ne "已结案" }'>class="button"</c:if>><div class="buttonContent"><button type="submit" <c:if test='${issue.status eq "已结案" }'>disabled=true</c:if> onclick="javascript:return del()">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>
<script type="text/javascript">
	function del() { 
	 var msg = "您所反馈的信息将会复访客户核对，请确认真实无误。"; 
	 if (confirm(msg)==true){ 
	  return true; 
	 }else{ 
	  return false; 
	 } 
	} 
</script>