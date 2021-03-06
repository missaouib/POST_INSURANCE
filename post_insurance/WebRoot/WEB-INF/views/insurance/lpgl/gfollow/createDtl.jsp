<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/lpgl/gfollow/detail" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
<input hidden="hidden" name="gsettle.id" value="${settle.id }">
<input hidden="hidden" name="gsettleDtlId" value="${settleDtl.id }">
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
			<input name="gpolicyNo" type="text" class="input-medium validate[maxSize[32]]" value="${settle.gpolicyNo }"/>
		</p>
		<p>
			<label>险种：</label>
			<input type="text" name="prodName" class="input-medium validate[maxSize[32]]" maxlength="32" value="${settleDtl.prodName }"/>
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
		<p>
			<label>备注：</label>
			<span class="unit">${settle.remark }</span>
		</p>
	</fieldset>
	<fieldset>
		<legend>案件进度</legend>
		<p class="nowrap">
		<c:forEach var="item" items="${dealLogs}" varStatus="idx" end="2">
		<fmt:formatDate value="${item.dealDate}" pattern="yyyy-MM-dd"/>：${item.user.username}:${empty item.followDate?"":"反馈日期"}${item.followDate }&nbsp;${item.info} <br/>
		</c:forEach>
		</p>
		<p>
			<label>跟进反馈：</label>
			<select name="toDealDay" id="lpgtdd" class="combox">
				<option value="1">1日内反馈 </option>
				<option value="3">3日内反馈 </option>
				<option value="5">5日内反馈 </option>
				<option value="15">15日内反馈 </option>
				<option value="30">择期反馈 </option>
			</select>
		</p>
		<p>
			<label>跟进日期：</label>
			<input type="text" name="followDate" class="date validate[maxSize[12]]" dateFmt="yyyy-MM-dd" readonly="true" value="" pattern="yyyy-MM-dd"/>
			<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p class="nowrap">
			<label>反馈内容：</label>
			<textarea name="info" cols="50" rows="3"></textarea>
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