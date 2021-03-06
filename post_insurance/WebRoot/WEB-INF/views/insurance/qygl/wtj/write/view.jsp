<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" id="issueForm" action="${contextPath}/qygl/issue/write/reopen" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${issue.id}"/>
	<input type="hidden" name="fixStatus" value="${issue.fixStatus}"/>
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>新契约填写不合格件基本信息</legend>
		<p>
			<label>保单号：</label>
			<span class="unit">${issue.policy.policyNo }</span>
		</p>
		<p>
			<label>投保单号：</label>
			<span class="unit">${issue.formNo }</span>
		</p>
		<p>
			<label>投保人：</label>
			<span class="unit">${issue.policy.holder }</span>
		</p>
		<p>
			<label>电话：</label>
			<span class="unit">${issue.policy.policyDtl.holderPhone }</span>
		</p>
		<p>
			<label>手机：</label>
			<span class="unit">${issue.policy.policyDtl.holderMobile }</span>
		</p>
		<p>
			<label>EMAIL：</label>
			<span class="unit">${issue.policy.policyDtl.holderEmail }</span>
		</p>
		<p>
			<label>地址：</label>
			<span class="unit">${issue.policy.policyDtl.holderAddr }</span>
		</p>
		<p>
			<label>网点类型：</label>
			<span class="unit">${issue.netType }</span>
		</p>
		<p>
			<label>出单网点：</label>
			<span class="unit">
			<c:choose>  
					    <c:when test="${!empty item.netName && fn:length(issue.netName) > 14}">  
					        <c:out value="${fn:substring(issue.netName, 14, 30)}" />  
					    </c:when> 
					    <c:when test="${!empty item.netName && fn:length(issue.netName) > 0}">  
					        <c:out value="${issue.netName}" />  
					    </c:when> 
					   <c:otherwise>  
					      <c:out value="${issue.policy.bankName}" />  
					    </c:otherwise>  
					</c:choose>
			</span>
		</p>
		<p>
			<label>抽检人：</label>
			<span class="unit">${issue.checker }</span>
		</p>
		<p>
			<label>抽检批次：</label>
			<span class="unit">${issue.checkBatch }</span>
		</p>
		<p>
			<label>险种名称：</label>
			<span class="unit">${issue.prdName }</span>
		</p>
		<p class="nowrap">
			<label>关键信息错误情况：</label>
			<span class="unit">${empty issue.keyInfo?"":issue.keyInfo }</span>
		</p>
		<p class="nowrap">
			<label>重要信息错误情况：</label>
			<span class="unit">${empty issue.importanceInfo?"":issue.importanceInfo }</span>
		</p>
		<p class="nowrap">
			<label>其他信息错误情况：</label>
			<span class="unit">${empty issue.elseInfo?"":issue.elseInfo }</span>
		</p>
		<p>
			<label>扫描错误：</label>
			<span class="unit">${empty issue.docError?'':issue.docError }</span>
		</p>
		<p class="nowrap">
			<label>扫描缺失情况：</label>
			<span class="unit">${empty issue.docMiss?"":issue.docMiss }</span>
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
		<legend>不合格件处理详情</legend>
		<p class="nowrap">
			<label>不合格件处理类型：</label>
			<span class="unit">${issue.fixType }</span>
		</p>
		<p class="nowrap">
			<label>不合格件处理记录：</label>
			<span class="unit">${issue.fixDesc }</span>
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
	<c:if test="${empty issue.reopenReason}">
	<div class="divider"></div>
	<fieldset>
		<legend>重新打开处理</legend>
		<p class="nowrap">
			<label>重新打开原因：</label>
			<textarea name="reopenReason" cols="50" rows="3">${issue.reopenReason }</textarea>
		</p>
		<p class="nowrap">
			<label>重新打开操作员：</label>
			<input type="text" name="reopenUser.realname" disabled="true" class="input-medium" maxlength="32" value="${issue.reopenUser.realname }"/>
		</p>
		<p class="nowrap">
			<label>重新打开日期：</label>
			<input type="text" name="reopenDate" disabled="true" class="input-medium" maxlength="32" value="${issue.reopenDate }"/>
		</p>
	</fieldset>
	</c:if>
	</div>
			
	<div class="formBar">
		<ul>
			<shiro:hasPermission name="CheckWrite:provEdit">
			<li><div <c:if test='${issue.fixStatus eq "CloseStatus" }'>class="buttonDisabled"</c:if> <c:if test='${issue.fixStatus ne "CloseStatus" }'>class="button"</c:if>><div class="buttonContent"><button type="submit" <c:if test='${issue.fixStatus eq "CloseStatus" }'>disabled=true</c:if>>重打开</button></div></div></li>
			<li><div <c:if test='${issue.fixStatus ne "CloseStatus" }'>class="button"</c:if>><div class="buttonContent"><button type="submit">强制重打开</button></div></div></li>
			<li><div <c:if test='${issue.fixStatus eq "CloseStatus" }'>class="buttonDisabled"</c:if> <c:if test='${issue.fixStatus ne "CloseStatus" }'>class="button"</c:if>><div class="buttonContent"><button type="button" onclick="$('#fixStatus').val('CloseStatus');$('#issueForm').attr('action', '/qygl/issue/write/close').submit();" <c:if test='${issue.fixStatus eq "CloseStatus" }'>disabled=true</c:if>>整改完毕</button></div></div></li>
			</shiro:hasPermission>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>