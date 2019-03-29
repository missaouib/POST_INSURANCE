<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/kfgl/inquire/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${inquire.id}"/>
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>工单基本信息</legend>
		<p>
			<label>工单编号：</label>
			<span class="unit">${inquire.inquireNo }</span>
		</p>
		<p>
			<label>工单状态：</label>
			<span class="unit">
			<c:choose>
                <c:when test="${item.inquireStatus eq 'NewStatus'}">
                	<div style="color: red;vertical-align:middle;font-weight:bold;">待处理</div>
                </c:when>
                <c:when test="${item.inquireStatus eq 'IngStatus'}">
                	<div style="color: blue;vertical-align:middle;font-weight:bold;">待审核</div>
                </c:when>
                <c:when test="${item.inquireStatus eq 'DealStatus'}">
                	<div style="color: blue;vertical-align:middle;font-weight:bold;">已审核</div>
                </c:when>
                <c:when test="${item.inquireStatus eq 'CTStatus'}">
                	<div style="color: blue;vertical-align:middle;font-weight:bold;">已退保</div>
                </c:when>
               <c:otherwise>
                 	 已结案
                </c:otherwise>
            </c:choose>
			</span>
		</p>
		<p>
			<label>客户姓名：</label>
			<span class="unit">${inquire.policy.holder }</span>
		</p>
		<p>
			<label>客户电话：</label>
			<span class="unit">${inquire.clientPhone1 }</span>
		</p>
		<p>
			<label>客户手机：</label>
			<span class="unit">${inquire.clientPhone2 }</span>
		</p>
		<p>
			<label>证件号码：</label>
			<span class="unit">${inquire.clientCardNum}</span>
		</p>
		<p>
			<label>保单号：</label>
			<span class="unit">${inquire.policy.policyNo }</span>
		</p>
		<p>
			<label>所属机构：</label>
			<span class="unit">${inquire.policy.organization.shortName }</span>
		</p>
		<p class="nowrap">
			<label>出单网点：</label>
			<span class="unit">${inquire.netName }</span>
		</p>
		<p>
			<label>下发日期：</label>
			<span class="unit">${inquire.operateTime }</span>
		</p>
		<p>
			<label>工单类型：</label>
			<span class="unit">${inquire.inquireSubtype }</span>
		</p>
		<p class="nowrap">
			<label>工单内容：</label>
			<span class="unit">${inquire.inquireDesc }</span>
		</p>
	</fieldset>
	<c:if test="${not empty inquire.reopenReason}">
	<div class="divider"></div>
	<fieldset>
		<legend>重新打开情况</legend>
		<p class="nowrap">
			<label>退回原因：</label>${inquire.reopenReason }
		</p>
		<p class="nowrap">
			处理人：${inquire.reopenUser.realname }；&nbsp;&nbsp;&nbsp;&nbsp;退回日期：${inquire.reopenDate }
		</p>
	</fieldset>
	</c:if>
	<div class="divider"></div>
	<fieldset>
		<legend>工单处理录入</legend>
		<p class="nowrap">
			<label>工单处理结果：</label>
			<textarea name="result" cols="50" rows="4" class="required">
${inquire.inquireRst }<c:if test="${empty inquire.inquireRst}">xx理财经理于 xx月xx日致电客户${inquire.policy.holder}，致电电话为______，谈话时长___分钟，处理如下。
沟通内容：
客户反馈：
</c:if>
			</textarea>
		</p>
		<p class="nowrap">
			<label>经办人：</label>
			<input type="text" name="dealMan" class="input-medium validate[required,maxSize[12]] required" maxlength="12" value="${inquire.dealMan }"/>
		</p>
		<p class="nowrap">
			<label>经办日期：</label>
			<input type="text" name="dealTime" class="date validate[required,maxSize[32]] required" dateFmt="yyyy-MM-dd" readonly="true" value="${inquire.dealTime }"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
	</fieldset>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div <c:if test='${inquire.inquireStatus eq "已结案" }'>class="buttonDisabled"</c:if> <c:if test='${inquire.inquireStatus ne "已结案" }'>class="button"</c:if>><div class="buttonContent"><button type="submit" <c:if test='${inquire.inquireStatus eq "已结案" }'>disabled=true</c:if> onclick="javascript:return del()">确定</button></div></div></li>
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