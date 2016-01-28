<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script type="text/javascript">
<!--
function doRel(val) {
	if(val == "") {
		alert("请选择关系！");
		return false;
	}
	var holder = $("#holder").val();
	if(val == "本人") {
		$("#insured").val(holder);
	} else {
		$("#insured").val("");
	}
}
//-->
</script>
<div class="pageContent">
<form method="post" action="${contextPath }/qygl/underwrite/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${underwrite.id }">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>所属机构：</label>
			<input name="organization.id" id="uw_orgId" type="hidden" value="${underwrite.organization.id }"/>
					<input class="validate[required] required" name="organization.name" id="uw_orgName" type="text" readonly="readonly" style="width: 140px;" value="${underwrite.organization.name }"/>
					<a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="organization" title="选择机构" width="400">查找带回</a>
		</p>
		<p>
			<label>投保单号</label>
			<input type="text" name="formNo" class="input-medium validate[required,maxSize[21] required" value="${underwrite.formNo }"/>
		</p>
		<p>
			<label>投保人：</label>
			<input type="text" name="holder" class="input-medium validate[required,maxSize[14]] required" maxlength="32" value="${underwrite.holder }"/>
		</p>
		<p>
			<label>投保年龄：</label>
			<input type="text" name="holderAge" id="holder" class="input-medium validate[required,maxSize[14]] required" maxlength="32" value="${underwrite.holderAge }"/>
		</p>
		<p>
			<label>投被保人关系：</label>
			<form:select path="underwrite.relation" id="relation" class="combox" onchange="javascript:doRel(this.value)">
				<form:option value="">请选择</form:option>
				<form:option value="本人">本人</form:option>
				<form:option value="夫妻">夫妻</form:option>
				<form:option value="父（母）子（女）">父（母）子（女）</form:option>
				<form:option value="祖孙">祖孙</form:option>
				<form:option value="其他">其他</form:option>
			</form:select>
		</p>
		<p>
			<label>被保人：</label>
			<input type="text" name="insured" class="input-medium validate[required,maxSize[14]] required" maxlength="32" value="${underwrite.insured }"/>
		</p>
		<p>
			<label>转核原因：</label>
			<form:select path="underwrite.underwriteReason" id="underwriteReason" class="combox">
				<form:option value="超龄">超龄</form:option>
				<form:option value="理赔">理赔</form:option>
				<form:option value="超额">超额</form:option>
				<form:option value="关系">关系</form:option>
				<form:option value="团险">团险</form:option>
			</form:select>
		</p>
		<p>
			<label>保险产品：</label>
			<input type="hidden" name="prd.id" class="input-medium validate[required,maxSize[32]] required" value="${underwrite.prd.id }"/>
			<input name="prd.prdName" type="text" postField="search_LIKE_prdName" suggestFields="prd" value="${underwrite.prd.prdName }"
					suggestUrl="/common/lookupPrdSuggest" lookupGroup="prd"/>
		</p>
		<p>
			<label>保费：</label>
			<input type="text" name="policyFee" class="input-medium validate[required,maxSize[14]] required" maxlength="32"value="${underwrite.policyFee }"/>
		</p>
		<p>
			<label>邮保通录入日期：</label>
			<input type="text" name="ybtDate" id="ybtDate" class="date validate[required] required" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value="${underwrite.ybtDate }" pattern="yyyy-MM-dd"/>"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>核心录入日期：</label>
			<input type="text" name="sysDate" id="sysDate" class="date validate[required] required" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value="${underwrite.sysDate }" pattern="yyyy-MM-dd"/>"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>复核日期：</label>
			<input type="text" name="checkDate" id="checkDate" class="date validate[required] required" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value="${underwrite.checkDate }" pattern="yyyy-MM-dd"/>"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<div class="divider"></div>
		<p>
			<label>保单号</label>
			<input type="text" name="policyNo" class="input-medium" value="${underwrite.policyNo }"/>
		</p>
		<p>
			<label>核保日期：</label>
			<input type="text" name="underwriteDate" id="underwriteDate" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value="${underwrite.underwriteDate }" pattern="yyyy-MM-dd"/>"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>是否下通知书：</label>
			<form:radiobutton path="underwrite.isLetter" value="1"/>是
			<form:radiobutton path="underwrite.isLetter" value="0"/>否
		</p>
		<p>
			<label>签单日期：</label>
			<input type="text" name="signDate" id="signDate" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value="${underwrite.signDate }" pattern="yyyy-MM-dd"/>"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>省分收到日期：</label>
			<input type="text" name="provReceiveDate" id="provReceiveDate" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value="${underwrite.provReceiveDate }" pattern="yyyy-MM-dd"/>"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>省分寄出EMS：</label>
			<input type="text" name="provEmsNo" class="input-medium" value="${underwrite.provEmsNo }"/>
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