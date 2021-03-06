<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script type="text/javascript">
<!--
function customAjaxDone(json){
	//alert(json.statusCode);
    if (json.statusCode == DWZ.statusCode.ok){
    	DWZ.ajaxDone(json);
    	dialogReloadNavTab(json);
    	$.pdialog.closeCurrent(); 
    }
    else{
        DWZ.ajaxDone(json);
    }
}
//-->
</script>
<div class="pageContent">
<form method="post" action="${contextPath}/kfgl/inquire/update" id="inquireForm" enctype="multipart/form-data" class="required-validate pageForm" onsubmit="return iframeCallback(this, customAjaxDone);">
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
			<span class="unit"><c:choose>
                <c:when test="${inquire.inquireStatus eq 'NewStatus'}">待处理
                </c:when>
                <c:when test="${inquire.inquireStatus eq 'IngStatus'}">待审核
                </c:when>
                <c:when test="${inquire.inquireStatus eq 'DealStatus'}">已审核
                </c:when>
                <c:when test="${inquire.inquireStatus eq 'CTStatus'}">已退保
                </c:when>
               <c:otherwise>
                 	 已结案
                </c:otherwise>
            </c:choose>
			</span>
		</p>
		<p>
			<label>客户姓名：</label>
			<span class="unit">${inquire.client }</span>
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
			<span class="unit">${empty inquire.gpolicyNo?inquire.policyNos:inquire.gpolicyNo }</span>
		</p>
		<p>
			<label>所属机构：</label>
			<span class="unit">${empty inquire.gpolicyNo?inquire.organ.shortName:inquire.gorganName }</span>
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
			<textarea name="inquireRst" cols="50" rows="5" class="required" style="font-size: 12">${inquire.inquireRst }<c:if test="${empty inquire.inquireRst}">
(1)问题处理情况：
(2)是否已联系客户解释：是/否
(3)联系客户解释方式：电话/面谈
(4)联系客户时间：
</c:if>
			</textarea>
		</p>
		<p class="nowrap">
			<label>经办人：</label>
			<input type="text" id="dealMan" name="dealMan" class="input-medium validate[required,maxSize[12]] required" maxlength="12" value="${inquire.dealMan }"/>
		</p>
		<p class="nowrap">
			<label>经办日期：</label>
			<input type="text" id="dealTime" name="dealTime" class="date validate[required,maxSize[32]] required" dateFmt="yyyy-MM-dd" readonly="true" value="${inquire.dealTime }"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
	</fieldset>
	<fieldset>
		<legend>附件</legend>
		<p>
			<label>附件：</label>
			<a href="${inquire.attrLink}" target="_blank">${inquire.attrLink }</a>
		</p>
	<p>&nbsp;</p>
		<p>
			<label>附件</label>
			<input type="file" name="file">
		</p>
	</fieldset>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div <c:if test='${inquire.inquireStatus eq "已结案" }'>class="buttonDisabled"</c:if> <c:if test='${inquire.inquireStatus ne "已结案" }'>class="button"</c:if>><div class="buttonContent"><button type="button" onclick="$('#dealMan').attr('class','');$('#dealTime').attr('class','');$('#inquireForm').attr('action', '/kfgl/inquire/toCity').submit();" <c:if test='${inquire.inquireStatus eq "已结案" }'>disabled=true</c:if>>转办地市</button></div></div></li>
			<li><div <c:if test='${inquire.inquireStatus eq "已结案" }'>class="buttonDisabled"</c:if> <c:if test='${inquire.inquireStatus ne "已结案" }'>class="button"</c:if>><div class="buttonContent"><button type="submit" <c:if test='${inquire.inquireStatus eq "已结案" }'>disabled=true</c:if> onclick="javascript:return del()">确定回复</button></div></div></li>
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