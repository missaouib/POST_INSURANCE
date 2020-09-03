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
<form method="post" action="${contextPath }/lpgl/detail" class="required-validate pageForm" onsubmit="return iframeCallback(this, customAjaxDone);">
<input hidden="hidden" name="id" value="${settle.id }">
<input hidden="hidden" name="settleId" value="${settle.id }">
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>未结赔案信息</legend>
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
	</fieldset>
	<fieldset>
		<legend>保单基本信息</legend>
		<dl>
			<dt>被保险人：</dt>
			<dd>${settle.caseMan }</dd>
		</dl>
		<dl>
			<dt>保单号：</dt>
			<dd>
			<input type="hidden" name="policy.id" value="${settle.policy.id }"/>
			<input name="policy.policyNo" type="text" postField="policyNo" suggestFields="policyNo" 
					suggestUrl="/common/lookupPolicysuggest" lookupGroup="policy" value="${settle.policy.policyNo }"/>
					<a class="btnLook" href="${contextPath }/common/lookup4Policy" lookupGroup="policy" title="选择保单" width="650">查</a>
			<dd>
		</dl>
	</fieldset>
	<fieldset>
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
		<dl></dl>
	</fieldset>
	<fieldset>
		<legend>案件进度</legend>
		<p class="nowrap">
		<c:forEach var="item" items="${dealLogs}" varStatus="idx" end="2">
		<fmt:formatDate value="${item.dealDate}" pattern="yyyy-MM-dd"/>：${item.user.realname}:${empty item.followDate?"":"反馈日期"}${item.followDate }&nbsp;${item.info} <br/>
		</c:forEach>
		</dl>
		<dl>
			<dt>跟进反馈：</dt>
			<select name="toDealDay" id="lptdd" class="combox">
				<option value="1">1日内反馈 </option>
				<option value="3">3日内反馈 </option>
				<option value="5">5日内反馈 </option>
				<option value="15">15日内反馈 </option>
				<!-- <option value="30">择期反馈 </option> -->
			</select>
		</dl>
		<!-- 
		<dl>
			<dt>跟进日期：</dt>
			<input type="text" name="followDate" class="date validate[maxSize[12]]" dateFmt="yyyy-MM-dd" readonly="true" value="" pattern="yyyy-MM-dd"/>
			<a class="inputDateButton" href="javascript:;">选择</a>
		</dl>
		 -->
		<p class="nowrap">
			<dt>反馈内容：</dt>
			<textarea name="info" cols="50" rows="3"></textarea>
		</dl>
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