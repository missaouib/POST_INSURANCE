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
<form method="post" action="${contextPath }/lpgl/task/type" class="required-validate pageForm" onsubmit="return iframeCallback(this, customAjaxDone);">
	<input type="hidden" name="id" value="${task.id }">
	<div class="pageFormContent" layouth="58">
		<p>
			<label>个团险标记：</label>
			<input type="radio" class="radio" name="policyType" value="0" ${task.policyType eq "0"?"checked=\"checked\"":"" }/>个险
			<input type="radio" class="radio" name="policyType" value="1" ${task.policyType eq "1"?"checked=\"checked\"":"" }/>团险
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">提交确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close" >关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>