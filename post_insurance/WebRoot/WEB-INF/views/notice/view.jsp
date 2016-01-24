<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/notice/create" enctype="multipart/form-data" class="required-validate pageForm" onsubmit="return iframeCallback(this, customAjaxDone);">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>机构：</label>
			<span class="unit">${notice.organization.name }</span>
		</p>		
		<p>
			<label>接收人：</label>
			<span class="unit">${notice.user.realname }</span>
		</p>
		<p>
			<label>接收角色：</label>
			<span class="unit">${notice.role.name}</span>
		</p>
		<p>
			<label>失效日期：</label>
			<span class="unit"><fmt:formatDate value="${notice.invalidDate}" pattern="yyyy-MM-dd"/></span>
		</p>
		<div class="divider"></div>
		<p>
			<label>标题：</label>
			<span class="unit">${notice.noticeTitle}</span>
		</p>
		<p class="nowrap">
			<label>内容：</label>
			<span class="unit">${notice.noticeContent}</span>
		</p>
		<p>&nbsp;</p>
		<div class="divider"></div>
		<p>
			<label>附件</label>
			<span class="unit">
				<c:forEach var="subList" items="${notice.noticeAtts}">
		            <a href="${subList.attrLink}">${subList.attrLink}</a>
		        </c:forEach>
		    </span>
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