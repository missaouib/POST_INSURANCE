<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/lpgl/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>机构：</label>
			<input name="organization.id" id="uw_orgId" type="hidden" value="${settle.organization.id }"/>
					<input class="validate[required] required" name="organization.name" id="uw_orgName" type="text" readonly="readonly" style="width: 140px;" value="${settle.organization.name }"/>
					<a class="btnLook" href="${contextPath }/management/security/settle/lookup2org" lookupGroup="organization" title="选择机构" width="400">查找带回</a>
		</p>
		<p>
			<label>出险人：</label>
			<input type="text" name="insured" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${settle.insured }"/>
		</p>
		<p>
			<label>报案人：</label>
			<input type="text" name="reporter" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${settle.reporter }"/>
		</p>
		<p>
			<label>报案人电话 ：</label>
			<input type="text" name="reporterPhone" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${settle.reporterPhone }"/>
		</p>
		<p>
			<label>出险日期：</label>
			<input type="text" name="caseDate" id="caseDate" class="date validate[required] required" dateFmt="yyyy-MM-dd" value="<fmt:formatDate value="${settle.caseDate }" pattern="yyyy-MM-dd"/>"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		
		<p>
			<label>理赔类型：</label>
			<input type="text" name="caseType" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${settle.caseType }"/>
		</p>
		<p>
			<label>报案日期：</label>
			<input type="text" name="reporteDate" id="reporteDate" class="date validate[required] required" dateFmt="yyyy-MM-dd" value="<fmt:formatDate value="${settle.reporteDate }" pattern="yyyy-MM-dd"/>"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>立案日期：</label>
			<input type="text" name="recordDate" id="recordDate" class="date validate[required] required" dateFmt="yyyy-MM-dd" value="<fmt:formatDate value="${settle.recordDate }" pattern="yyyy-MM-dd"/>"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>结案日期：</label>
			<input type="text" name="closeDate" id="closeDate" class="date validate[required] required" dateFmt="yyyy-MM-dd" value="<fmt:formatDate value="${settle.closeDate }" pattern="yyyy-MM-dd"/>"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>赔付金额：</label>
			<input type="text" name="payFee" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${settle.payFee }"/>
		</p>
		<p>
			<label>案件状态：</label>
			<input type="text" name="caseStatus" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value="${settle.caseStatus }"/>
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