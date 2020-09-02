<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/lpgl/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
<input type="hidden" name="id" value="${settle.id }" />
	<div class="pageFormContent" layoutH="58">
		<dl>
				<dt>机构：</dt>
				<dd>${settle.organization.name }</dd>
		</dl>
		<dl>
			<dt>赔案号：</dt>
			<dd>${settle.claimsNo }</dd>
		</dl>
		<dl>
			<dt>出险人：</dt>
			<dd>${settle.caseMan }</dd>
		</dl>
		<dl>
			<dt>报案人：</dt>
			<dd>${settle.reporter }</dd>
		</dl>
		<dl>
			<dt>报案人电话 ：</dt>
			<dd>${settle.reporterPhone }</dd>
		</dl>
		<dl>
			<dt>出险日期：</dt>
			<dd>${settle.caseDate }</dd>
		</dl>
		<dl>
			<dt>理赔类型：</dt>
			<dd>
			<form:select path="settle.caseType" id="caseType" class="combox validate[required] required">
				<form:option value="意外身故">意外身故</form:option>
				<form:option value="疾病身故">疾病身故</form:option>
				<form:option value="重大疾病">重大疾病</form:option>
				<form:option value="全残">全残</form:option>
				<form:option value="医疗">医疗</form:option>
			</form:select>
			</dd>
		</dl>
		<dl>
			<dt>报案日期：</dt>
			<dd>
			<input type="text" name="reporteDate" id="reporteDate" class="date validate[required] required" dateFmt="yyyy-MM-dd" value="<fmt:formatDate value="${settle.reporteDate }" pattern="yyyy-MM-dd"/>"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
			</dd>
		</dl>
		<dl>
			<dt>案件状态：</dt>
			<dd>
			<form:select path="settle.caseStatus" id="caseStatus" class="combox validate[required] required">
				<form:option value="待立案">待立案</form:option>
				<form:option value="待调查">待调查</form:option>
				<form:option value="已结案">已结案</form:option>
			</form:select>
			</dd>
		</dl>
		<dl>
			<dt>出险描述：</dt>
			<dd><textarea name="remark" id="remark" cols="30" rows="3" class="input-medium">${settle.remark }</textarea></dd>
		</dl>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>