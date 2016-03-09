<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/bqgl/offsite/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>地市：</label>
			<input name="organization.id" id="uw_orgId" type="hidden" value=""/>
			<input name="organization.orgCode" id="uw_orgCode" type="hidden" value="${offsite.organization.orgCode }"/>
					<input class="validate[required] required" name="organization.name" id="uw_orgName" type="text" readonly="readonly" style="width: 140px;" value=""/>
					<a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="organization" title="选择机构" width="400">查找带回</a>
		</p>		
		<p>
			<label>经办人：</label>
			<input type="text" name="transactor" class="input-medium" maxlength="32" value="${user.realName }"/>
		</p>
		<p>
			<label>转办日期：</label>
			<input type="text" name="dealDate" class="date" dateFmt="yyyy-MM-dd" readonly="true" value=""/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>快递单号：</label>
			<input type="text" name="expressBillNo" class="input-medium" maxlength="32" value="${offsite.expressBillNo }"/>
		</p>
		<p>
			<label>保单号：</label>
			<input name="policyNo" type="text" postField="policyNo" suggestFields="policyNo" class="input-medium validate[required] required"
					suggestUrl="/common/lookupPolicyProvsuggest" lookupGroup="" value=""/>
		</p>
		<p>
			<label>出单省</label>
			<input type="text" name="orginProv" id="orginProv" class="input-medium"/>
		</p>
		<p>
			<label>收件省</label>
			<input type="text" name="dealProv" id="dealProv" class="input-medium validate[required] required"/>
		</p>
		<p>
			<label>外省经办人：</label>
			<input type="text" class="input-medium" name="linker" value="${offsite.linker }" />
		</p>
		<p>
			<label>客户姓名：</label>
			<input type="text" name="client" value="" class="input-medium validate[required] required"/>
		</p>
		<p>
			<label>保全业务：</label>
			<input name="conservationType" id="conservationType" class="input-medium validate[required] required" type="text" postField="search_LIKE_csName" suggestFields="conservationType" 
					suggestUrl="/common/lookup2BQTypeSuggest" lookupGroup="" value=""/>
		</p>
		<p class="nowrap">
			<label>寄件地址：</label>
			<textarea type="text" name="mailAddr" class="input-medium">${offsite.mailAddr}</textarea>
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