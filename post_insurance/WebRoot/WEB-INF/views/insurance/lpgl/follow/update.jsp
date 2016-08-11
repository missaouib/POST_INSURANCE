<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/lpgl/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
<input type="hidden" name="id" value="${settle.id }" />
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
			<input type="text" name="reporter" class="input-medium validate[maxSize[32]]" maxlength="32" value="${settle.reporter }"/>
		</p>
		<p>
			<label>报案人电话 ：</label>
			<input type="text" name="reporterPhone" class="input-medium validate[maxSize[32]]" maxlength="32" value="${settle.reporterPhone }"/>
		</p>
		<p>
			<label>出险日期：</label>
			<input type="text" name="caseDate" id="caseDate" class="date" dateFmt="yyyy-MM-dd" value="<fmt:formatDate value="${settle.caseDate }" pattern="yyyy-MM-dd"/>"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		
		<p>
			<label>理赔类型：</label>
			<form:select path="settle.caseType" id="caseType" class="combox validate[required] required">
				<form:option value="意外身故">意外身故</form:option>
				<form:option value="疾病身故">疾病身故</form:option>
				<form:option value="重大疾病">重大疾病</form:option>
				<form:option value="全残">全残</form:option>
				<form:option value="医疗">医疗</form:option>
			</form:select>
		</p>
		<p>
			<label>报案日期：</label>
			<input type="text" name="reporteDate" id="reporteDate" class="date validate[required] required" dateFmt="yyyy-MM-dd" value="<fmt:formatDate value="${settle.reporteDate }" pattern="yyyy-MM-dd"/>"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>立案日期：</label>
			<input type="text" name="recordDate" id="recordDate" class="date" dateFmt="yyyy-MM-dd" value="<fmt:formatDate value="${settle.recordDate }" pattern="yyyy-MM-dd"/>"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>结案日期：</label>
			<input type="text" name="closeDate" id="closeDate" class="date" dateFmt="yyyy-MM-dd" value="<fmt:formatDate value="${settle.closeDate }" pattern="yyyy-MM-dd"/>"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>赔付金额：</label>
			<input type="text" name="payFee" class="input-medium validate[maxSize[32]]" maxlength="32" value="${settle.payFee }"/>
		</p>
		<p>
			<label>案件状态：</label>
			<form:select path="settle.caseStatus" id="caseStatus" class="combox validate[required] required">
				<form:option value="待报案">待报案</form:option>
				<form:option value="待立案">待立案</form:option>
				<form:option value="待调查">待调查</form:option>
				<form:option value="待结案">待结案</form:option>
				<form:option value="拒付退费">拒付退费</form:option>
				<form:option value="结案关闭">结案关闭</form:option>
				<form:option value="不予立案">不予立案</form:option>
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