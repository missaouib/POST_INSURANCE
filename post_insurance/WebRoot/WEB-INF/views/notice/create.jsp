<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/basedata/callDealType/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>标题：</label>
			<input type="text" name="typeName" class="input-medium validate[required,maxSize[32]] required" maxlength="32"/>
		</p>
		<p>
			<label>接收人：</label>
			<input type="text" name="receiver" class="input-medium validate[required,maxSize[32]] required" maxlength="32"/>
		</p>
		<p>
			<label>接收机构：</label>
			<input type="text" name="receiveOrg" class="input-medium validate[required,maxSize[32]] required" maxlength="32"/>
		</p>
		<p>
			<label>接收角色：</label>
			<input type="text" name="receiveRole" class="input-medium validate[required,maxSize[32]] required" maxlength="32"/>
		</p>
		<p>
			<label>有效期至：</label>
			<input type="text" name="ybtDate" id="invalidDate" class="date validate[required] required" dateFmt="yyyy-MM-dd HH:mm" readonly="true" value=""/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>内容：</label>
			<textarea class="editor" name="noticeContent" rows="8" cols="100" tools="Paste,Pastetext,|,Link,Unlink,|,Fullscreen"
								upLinkUrl="/notice/upload" upLinkExt="zip,rar,jpg,pdf,doc,docx,et,xls,wps">
			</textarea>
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