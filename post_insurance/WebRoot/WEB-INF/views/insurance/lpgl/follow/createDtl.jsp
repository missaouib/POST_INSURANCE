<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/lpgl/detail" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
<input hidden="hidden" name="settlement.id" value="${settle.id }">
<input hidden="hidden" name="id" value="${settleDtl.id }">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>赔案号：</label>
			<input type="text" name="claimsNo" class="input-medium validate[maxSize[32]]" maxlength="32" value="${settleDtl.claimsNo }"/>
		</p>
	<fieldset>
		<legend>保单基本信息</legend>
		<p>
			<label>被保险人：</label>
			<span class="unit">${settle.insured }</span>
		</p>
		<p>
			<label>保单号：</label>
			<input name="policyNo" type="text" postField="search_LIKE_policyNo" suggestFields="policyNo" 
					suggestUrl="/common/lookupPolicysuggest" lookupGroup="" class="input-medium validate[maxSize[32]]" value="${settleDtl.policyNo }"/>
		</p>
		<p>
			<label>险种：</label>
			<input type="text" name="prodName" class="input-medium validate[maxSize[32]]" maxlength="32" value="${settleDtl.prodName }"/>
		</p>
		<p>
			<label>保费：</label>
			<input type="text" name="policyFee" class="input-medium validate[maxSize[32]]" maxlength="32" value="${settleDtl.policyFee }"/>
		</p>
		<p>
			<label>生效日期：</label>
			<input type="text" name="policyDate" class="input-medium validate[maxSize[32]]" maxlength="32" value="${settleDtl.policyDate }"/>
		</p>
	</fieldset>
	<fieldset>
		<legend>出险信息</legend>
		<p>
			<label>出险日期：</label>
			<span class="unit"><fmt:formatDate value="${settle.caseDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>理赔类型：</label>
			<span class="unit">${settle.caseType }</span>
		</p>
	</fieldset>
	<fieldset>
		<legend>报案进程</legend>
		<p>
			<label>首次接触赔案时间：</label>
			<input type="text" name="firstCaseTime" class="date validate[maxSize[12]]" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value="${settleDtl.firstCaseTime }" pattern="yyyy-MM-dd"/>"/>
			<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>接案人：</label>
			<input type="text" name="caseMan" class="input-medium validate[maxSize[32]]" maxlength="32" value="${settleDtl.caseMan }"/>
		</p>
		<p>
			<label>首次接收理赔材料日期：</label>
			<input type="text" name="firstFileDate" class="date validate[maxSize[12]]" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value="${settleDtl.firstFileDate }" pattern="yyyy-MM-dd"/>"/>
			<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>签收人：</label>
			<input type="text" name="firstSignMan" class="input-medium validate[maxSize[32]]" maxlength="32" value="${settleDtl.firstSignMan }"/>
		</p>
		<p>
			<label>接收齐全理赔材料日期：</label>
			<input type="text" name="allFileDate" class="date validate[maxSize[12]]" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value="${settleDtl.allFileDate }" pattern="yyyy-MM-dd"/>"/>
			<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>签收人：</label>
			<input type="text" name="allSignMan" class="input-medium validate[maxSize[32]]" maxlength="32" value="${settleDtl.allSignMan }"/>
		</p>
	</fieldset>
	<fieldset>
		<legend>调查进程</legend>
		<p>
			<label>发起调查日期：</label>
			<input type="text" name="checkDate" class="date validate[maxSize[12]]" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value="${settleDtl.checkDate }" pattern="yyyy-MM-dd"/>"/>
			<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>发起人：</label>
			<input type="text" name="checker" class="input-medium validate[maxSize[32]]" maxlength="32" value="${settleDtl.checker }"/>
		</p>
		<p>
			<label>调查完成日期：</label>
			<input type="text" name="checkDoneDate" class="date validate[maxSize[12]]" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value="${settleDtl.checkDoneDate }" pattern="yyyy-MM-dd"/>"/>
			<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>调查人：</label>
			<input type="text" name="checkDoneMan" class="input-medium validate[maxSize[32]]" maxlength="32" value="${settleDtl.checkDoneMan }"/>
		</p>
	</fieldset>
	<fieldset>
		<legend>立案进程</legend>
		<p>
			<label>报案日期：</label>
			<span class="unit"><fmt:formatDate value="${settle.reporteDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>立案日期：</label>
			<span class="unit"><fmt:formatDate value="${settle.recordDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>结案日期：</label>
			<span class="unit"><fmt:formatDate value="${settle.closeDate }" pattern="yyyy-MM-dd"/></span>
		</p>
		<p>
			<label>赔付金额：</label>
			<span class="unit">${settle.payFee }</span>
		</p>
		<p>
			<label>案件状态：</label>
			<span class="unit">${settle.caseStatus }</span>
		</p>
	</fieldset>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>