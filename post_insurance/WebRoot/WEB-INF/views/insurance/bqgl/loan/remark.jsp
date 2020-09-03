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
<form method="post" action="${contextPath }/bqgl/loan/remark" enctype="multipart/form-data" class="required-validate pageForm" onsubmit="return iframeCallback(this, customAjaxDone);">
<input type="hidden" name="id" value="${loan.id}"/>
	<div class="pageFormContent" layouth="58">
		<p>
			<label>相关备注：</label>
			<textarea name="remark" class="validate[maxSize[128]]" cols="30" rows="4">${loan.remark }</textarea>
		</p>
		<P>
		&nbsp;
		</P>
		<!-- 
		<p>
			<label>重置电话号码：</label>
			<textarea name="resetPhone" class="validate[maxSize[128]]" cols="30" rows="2">${cfl.resetPhone }</textarea>
		</p>
		 -->
		 <p>&nbsp;</p>
		<p>
			<label>附件</label>
			<input type="file" name="file">
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">确定提交</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close" >关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>