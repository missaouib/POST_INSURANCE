<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/fpgl/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>保单号：</label>
			<input name="policy.id" type="hidden">
			<input name="policy.policyNo" type="text" postField="search_LIKE_policyNo" suggestFields="policyNo" 
					suggestUrl="/common/lookupPolicysuggest" lookupGroup="policy" class="input-medium validate[required,maxSize[32]] required"/>
		</p>
		<p>
			<label>投保人姓名：</label>
			<input type="text" name="policy.holder" readonly="readonly" class="input-medium validate[required,maxSize[32]] required"/>
		</p>
		<p>
			<label>保单所属机构：</label>
			<input type="text" name="policy.organName" readonly="readonly" class="input-medium validate[required,maxSize[32]] required"/>
		</p>
		<p>
			<label>发票标记：</label>
			<input type="radio" name="reqFlag" value="首期"/>首期
			<input type="radio" name="reqFlag" value="续期"/>续期
		</p>
		<p>
			<label>电子发票标记：</label>
			<input type="radio" name="isElectiveBill" value="TRUE" checked="checked"/>是
			<input type="radio" name="isElectiveBill" value="FALSE"/>否
		</p>
		<p>
			<label>电子发票接收邮箱：</label>
			<input type="text" name="billAddr" maxlength="18" class="input-medium validate[maxSize[18]]"/>
		</p>
		<p>
			<label>发票金额：</label>
			<input type="text" name="fee" maxlength="32" class="input-medium validate[required,maxSize[7]] required"/>
		</p>
		<p>
			<label>缴费日期：</label>
			<input type="text" name="feeDate" class="date validate[required,maxSize[32]] required" dateFmt="yyyy-MM-dd" readonly="true"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>发票收件人：</label>
			<input name="reqMan" type="text" postField="reqMan" suggestFields="reqMan" 
					suggestUrl="/fpgl/lookupReqSuggest?flag=man" lookupGroup=""/>
		</p>
		<p>
			<label>发票收取地址：</label>
			<input type="text" name="reqAddr" class="input-medium"/>
		</p>
		<p>
			<label>发票收件人电话：</label>
			<input type="text" name="phone" class="input-medium validate[maxSize[11]]"/>
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