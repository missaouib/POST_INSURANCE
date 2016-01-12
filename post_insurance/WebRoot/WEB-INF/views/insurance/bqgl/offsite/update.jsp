<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/bqgl/offsite/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${offsite.id}"/>
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>地市：</label>
			<input name="organization.id" id="uw_orgId" type="hidden" value="${offsite.organization.id }"/>
			<input name="organization.orgCode" id="uw_orgCode" type="hidden" value="${offsite.organization.orgCode }"/>
					<input class="validate[required] required" name="organization.name" id="uw_orgName" type="text" readonly="readonly" style="width: 140px;" value="${offsite.organization.name }"/>
					<a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="organization" title="选择机构" width="400">查找带回</a>
		</p>		
		<p>
			<label>经办人：</label>
			<input type="text" name="transactor" class="input-medium validate[required] required" maxlength="32" value="${offsite.transactor }"/>
		</p>
		<p>
			<label>转办日期：</label>
			<input type="text" name="dealDate" class="date validate[required,maxSize[12]] required" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value='${offsite.dealDate }' pattern='yyyy-MM-dd'/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>快递单号：</label>
			<input type="text" name="expressBillNo" class="input-medium" maxlength="32" value="${offsite.expressBillNo }"/>
		</p>
		<p>
			<label>保单号：</label>
			<input name="policyNo" type="text" postField="policyNo" suggestFields="policyNo" class="input-medium validate[required] required"
					suggestUrl="/common/lookupPolicyProvsuggest" lookupGroup="" value="${offsite.policyNo }"/>
		</p>
		<p>
			<label>出单省</label>
			<input type="text" class="input-medium validate[required] required" name="orginProv" id="orginProv" value="${offsite.orginProv }" />
		</p>
		<p>
			<label>客户姓名：</label>
			<input type="text" name="client" value="${offsite.client}" class="input-medium validate[required] required"/>
		</p>
		<p>
			<label>保全业务：</label>
			<input name="conservationType" id="conservationType" class="input-medium validate[required] required" type="text" postField="search_LIKE_csName" suggestFields="conservationType" 
					suggestUrl="/common/lookup2BQTypeSuggest" lookupGroup="" value="${offsite.conservationType }"/>
		</p>
		<p class="nowrap">
			<label>寄送地址：</label>
			<textarea type="text" name="mailAddr" class="input-medium">${offsite.mailAddr}</textarea>
		</p>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div <c:if test='${offsite.status eq "已关闭" }'>class="buttonDisabled"</c:if> <c:if test='${offsite.status ne "已关闭" }'>class="button"</c:if>><div class="buttonContent"><button type="submit" <c:if test='${offsite.status eq "已关闭" }'>disabled=true</c:if>>确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>