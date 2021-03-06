<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" id="inquireForm" action="${contextPath}/kfgl/inquire/reopen" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${inquire.id}"/>
	<input type="hidden" name="inquireStatus" value="${inquire.inquireStatus}"/>
	<div class="pageFormContent" layoutH="60">
	<fieldset>
		<legend>工单基本信息</legend>
		<p>
			<label>工单编号：</label>
			<span class="unit">${inquire.inquireNo }</span>
		</p>
		<p class="nowrap">
			<label>工单状态：</label>
			<span class="unit"><c:choose>
                <c:when test="${inquire.inquireStatus eq 'NewStatus'}">待处理</c:when>
                <c:when test="${inquire.inquireStatus eq 'IngStatus'}">待审核</c:when>
                <c:when test="${inquire.inquireStatus eq 'DealStatus'}">已审核</c:when>
                <c:when test="${inquire.inquireStatus eq 'CTStatus'}">已退保</c:when>
               <c:otherwise>已结案</c:otherwise>
            </c:choose></span>
		</p>
		<p>
			<label>客户姓名：</label>
			<span class="unit">${inquire.client }</span>
		</p>
		<p>
			<label>保单号：</label>
			<span class="unit">${empty inquire.gpolicyNo?inquire.policyNos:inquire.gpolicyNo}</span>
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
	<div class="divider"></div>
	<fieldset>
		<legend>工单处理详情</legend>
		<p class="nowrap">
			<label>工单处理结果：</label>
			<textarea name="inquireRst" cols="50" rows="3">${inquire.inquireRst }</textarea>
		</p>
		<p class="nowrap">
			<label>经办人：</label>
			<input type="text" name="dealMan" class="input-medium" maxlength="32" value="${inquire.dealMan }"/>
		</p>
		<p class="nowrap">
			<label>经办日期：</label>
			<input type="text" name="dealTime" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="${inquire.dealTime }"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
	</fieldset>
	<shiro:hasPermission name="Inquire:provEdit">
	<div class="divider"></div>
		<h3 class="contentTitle">标签设置</h3>
		<div class="tabs">
			<div class="tabsContent" style="height: 150px;">
				<div>
					<table class="list nowrap itemDetail" addButton="新建标签" width="100%">
						<thead>
							<tr>
								<th type="text" name="items.itemString[]" size="12" fieldClass="required">标签名称</th>
								<th type="del" width="60">操作</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
			</div>
		</div>
	</shiro:hasPermission>
	<div class="divider"></div>
	<fieldset>
		<legend>重新打开处理</legend>
		<p class="nowrap">
			<label>重新打开原因：</label>
			<textarea name="reopenReason" cols="50" rows="3">${inquire.reopenReason }</textarea>
		</p>
		<p class="nowrap">
			<label>重新打开操作员：</label>
			<input type="text" name="reopenUser.realname" disabled="true" class="input-medium" maxlength="32" value="${inquire.reopenUser.realname }"/>
		</p>
		<p class="nowrap">
			<label>重新打开日期：</label>
			<input type="text" name="reopenDate" disabled="true" class="input-medium" maxlength="32" value="${inquire.reopenDate }"/>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>审核</legend>
		<p class="nowrap">
			<label>审核人：</label>
			<input type="text" name="checker" class="input-medium" maxlength="32" value="${checker }"/>
		</p>
		<p class="nowrap">
			<label>审核意见：</label>
			<input type="text" name="checkRst" class="input-medium" maxlength="52" value="审核通过。"/>
		</p>
	</fieldset>
	<fieldset>
		<legend>附件</legend>
		<p>
			<label>附件：</label>
			<a href="${inquire.attrLink}" target="_blank">${inquire.attrLink }</a>
		</p>
		</fieldset>
	</div>
	<div class="formBar">
		<ul>
			<shiro:hasPermission name="Inquire:provAdmin">
			<li><div <c:if test='${inquire.inquireStatus eq "已结案" }'>class="buttonDisabled"</c:if> <c:if test='${inquire.inquireStatus ne "已结案" }'>class="button"</c:if>><div class="buttonContent"><button type="submit" <c:if test='${inquire.inquireStatus eq "已结案" }'>disabled=true</c:if>>重打开</button></div></div></li>
			<li><div <c:if test='${inquire.inquireStatus eq "已结案" }'>class="buttonDisabled"</c:if> <c:if test='${inquire.inquireStatus ne "已结案" }'>class="button"</c:if>><div class="buttonContent"><button type="button" onclick="$('#inquireStatus').val('CloseStatus');$('#inquireForm').attr('action', '/kfgl/inquire/close').submit();" <c:if test='${inquire.inquireStatus eq "已结案" }'>disabled=true</c:if>>结案关闭</button></div></div></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Inquire:provEdit">
			<li><div <c:if test='${inquire.inquireStatus eq "已结案" }'>class="buttonDisabled"</c:if> <c:if test='${inquire.inquireStatus ne "已结案" }'>class="button"</c:if>><div class="buttonContent"><button type="button" onclick="$('#inquireForm').attr('action', '/kfgl/inquire/toCity').submit();" <c:if test='${inquire.inquireStatus eq "已结案" }'>disabled=true</c:if>>转办地市</button></div></div></li>
			<li><div <c:if test='${inquire.inquireStatus eq "已结案" }'>class="buttonDisabled"</c:if> <c:if test='${inquire.inquireStatus ne "已结案" }'>class="button"</c:if>><div class="buttonContent"><button type="submit" <c:if test='${inquire.inquireStatus eq "已结案" }'>disabled=true</c:if>>重打开</button></div></div></li>
			<li><div <c:if test='${inquire.inquireStatus eq "已结案" }'>class="buttonDisabled"</c:if> <c:if test='${inquire.inquireStatus ne "已结案" }'>class="button"</c:if>><div class="buttonContent"><button type="button" onclick="$('#inquireStatus').val('DealStatus');$('#inquireForm').attr('action', '/kfgl/inquire/deal').submit();" <c:if test='${inquire.inquireStatus eq "已结案" }'>disabled=true</c:if>>审核通过</button></div></div></li>
			</shiro:hasPermission>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>