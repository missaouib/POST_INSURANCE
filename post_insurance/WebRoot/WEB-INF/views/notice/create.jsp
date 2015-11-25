<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script type="text/javascript">
<!--
function customAjaxDone(json){
	alert(json.statusCode);
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
<form method="post" action="${contextPath }/notice/create" enctype="multipart/form-data" class="required-validate pageForm" onsubmit="return iframeCallback(this, customAjaxDone);">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>机构：</label>
			<input name="organization.id" id="uw_orgId" type="hidden" value=""/>
			<input name="organization.orgCode" id="uw_orgCode" type="hidden" value="${offsite.organization.orgCode }"/>
					<input class="validate[required] required" name="organization.name" id="uw_orgName" type="text" readonly="readonly" style="width: 140px;" value=""/>
					<a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="organization" title="选择机构" width="400">查找带回</a>
		</p>		
		<p>
			<label>接收人：</label>
			<input type="text" name="user.id" class="input-medium validate[required] required" maxlength="32" value=""/>
		</p>
		<p>
			<label>接收角色：</label>
			<input type="text" name="role.id" class="input-medium" maxlength="32" value=""/>
		</p>
		<p>
			<label>失效日期：</label>
			<input type="text" name="invalidDate" class="date validate[required,maxSize[12]] required" dateFmt="yyyy-MM-dd" readonly="true" value=""/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>标题：</label>
			<input type="text" name="noticeTitle" class="input-medium" maxlength="32" value=""/>
		</p>
		<p>
			<label>内容</label>
			<input type="text" name="noticeContent" id="orginProv" class="input-medium validate[required] required"/>
		</p>
		<p>
			<label>附件</label>
			<input type="file" name="file">
		</p>
	</div>
	<iframe name="hidden_frame" id="hidden_frame" src="message.html" style="display:none"></iframe>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>