<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page import="java.util.Date"%>  
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
<form method="post" action="${contextPath }/lpgl/task/create" enctype="multipart/form-data" class="required-validate pageForm" onsubmit="return iframeCallback(this, customAjaxDone);">
	<div class="pageFormContent" layoutH="58">
		<fieldset>
		<legend>保单基本信息</legend>
		<p>
			<label>保单号：</label>
			<input name="policyNo" type="text" postField="search_LIKE_policyNo" suggestFields="policyNo" 
					suggestUrl="/lpgl/lookupSettlesuggest" lookupGroup="" class="input-medium validate[maxSize[32]]" value="${settleDtl.policyNo }"/>
		</p>
	  	<p >
			<label>出保险人：</label>
			<input type="text" name="insured" class="input-medium validate[maxSize[32]]" maxlength="32" value="${settleDtl.insured }"/>
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
			<input type="text" name="caseDate" class="input-medium validate[maxSize[32]]" maxlength="32" value="${settleDtl.caseDate }"/>
		</p>
		<p>
			<label>理赔类型：</label>
			<select name="caseType" id="caseType" class="combox validate[required] required">
				<option value="意外身故">意外身故</option>
				<option value="疾病身故">疾病身故</option>
				<option value="重大疾病">重大疾病</option>
				<option value="全残">全残</option>
				<option value="医疗">医疗</option>
			</select>
		</p>
	</fieldset>
	<fieldset>
		<legend>调查进程</legend>
		<p>
			<label>调查起期：</label>
			<input type="text" name="checkStartDate" class="input-medium validate[maxSize[32]]" maxlength="32" value="<fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM-dd"/>"/>
		</p>
		<p>
			<label>结案日期：</label>
			<input type="text" name="checkEndDate" id="checkEndDate" class="date" dateFmt="yyyy-MM-dd" readonly="readonly" value="${task.checkEndDate }"/>
		</p>
		<p>
			<label>调查要求：</label>
			<textarea name="checkReq" id="checkReq" cols="30" rows="3" class="input-medium"></textarea>
		</p>
		<p>&nbsp;</p><p>&nbsp;</p>
		<p>
			<label>调查人：</label>
			<input name="realname" type="text" postField="realname" suggestFields="realname" 
					suggestUrl="/common/lookupClaimUserSuggest?role=地市理赔" lookupGroup=""/>
					<a class="btnLook" href="${contextPath }/common/lookup4RoleUser?role=地市理赔" lookupGroup="" title="选择用户" width="650" hight="530">查</a>
		</p>
		<p>
			<label>调查地点：</label>
			<input type="text" name="checkerAddr" class="input-medium validate[maxSize[32]]" maxlength="32" value=""/>
		</p>
		<p>
			<label>查勘费：</label>
			<input type="text" name="checkFee" class="input-medium validate[maxSize[32]]" maxlength="32" value=""/>
		</p>
		<p>
			<label>备注：</label>
			<textarea name="remark" id="remark" cols="30" rows="3" class="input-medium"></textarea>
		</p>
		<p>&nbsp;</p><p>&nbsp;</p>
		<p>
			<label>附件</label>
			<input type="file" name="file">
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