<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/lpgl/gfollow/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
<input type="hidden" name="id" value="${settle.id }" />
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>机构：</label>
			<input name="organization.id" id="guw_orgId" type="hidden" value="${settle.organization.id }"/>
					<input class="validate[required] required" name="organization.name" id="guw_orgName" type="text" readonly="readonly" style="width: 140px;" value="${settle.organization.name }"/>
					<a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="organization" title="选择机构" width="400">查找带回</a>
		</p>
		<p>
			<label>保单号：</label>
			<input name="gpolicyNo" type="text" class="input-medium validate[maxSize[32]]" value="${settle.gpolicyNo }"/>
		</p>
		<p>
			<label>出险人：</label>
			<input type="text" name="insured" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${settle.insured }"/>
		</p>
		<p>
			<label>出险日期：</label>
			<input type="text" name="caseDate" id="caseDate" class="date" dateFmt="yyyy-MM-dd" value="<fmt:formatDate value="${settle.caseDate }" pattern="yyyy-MM-dd"/>"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		
		<p>
			<label>理赔类型：</label>
			<form:select path="settle.caseType" id="gcaseType" class="combox validate[required] required">
				<form:option value="意外身故">意外身故</form:option>
				<form:option value="疾病身故">疾病身故</form:option>
				<form:option value="重大疾病">重大疾病</form:option>
				<form:option value="全残">全残</form:option>
				<form:option value="医疗">医疗</form:option>
			</form:select>
		</p>
		<p>
			<label>备注：</label>
			<textarea name="remark" id="remark" cols="30" rows="3" class="input-medium">${settle.remark }</textarea>
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