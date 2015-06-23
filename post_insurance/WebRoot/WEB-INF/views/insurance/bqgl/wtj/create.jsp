<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/bqgl/issue/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>保单号：</label>
			<input type="hidden" name="xjglBjdmb.id" />
			<input name="xjglBjdmb.bjmc" type="text" postField="search_LIKE_bjmc" suggestFields="bjdm,bjmc" 
					suggestUrl="/xjgl/xsjbxx/lookupBjdmsuggest" lookupGroup="xjglBjdmb"/>
					<a class="btnLook" href="${contextPath }/xjgl/xsjbxx/lookup2bj" lookupGroup="xjglBjdmb" title="选择班级" width="600">查找带回</a>
		</p>
		<p>
			<label>姓名：</label>
			<input type="text" name="realname" class="input-medium validate[required,maxSize[32]] required" maxlength="32"/>
		</p>		
		<p>
			<label>登录密码：</label>
			<input type="password" name="plainPassword" class="input-medium validate[required,minSize[6],maxSize[32]] required" maxlength="32" value="123456" alt="字母、数字、下划线 6-32位"/>
		</p>
		<p>
			<label>电话：</label>
			<input type="text" name="phone" class="input-medium validate[custom[phone],maxSize[32]]" maxlength="32"/>
		</p>		
		<p>
			<label>用户邮箱：</label>
			<input type="text" name="email" class="input-medium validate[custom[email],maxSize[128]]" maxlength="128"/>
		</p>		
		<p>
			<label>用户状态：</label>
			<select name="status">
				<option value="enabled">可用</option>
				<option value="disabled">不可用</option>
			</select>
		</p>
		<p>
			<label>关联组织：</label>	
			<input name="organization.id" type="hidden"/>
			<input class="validate[required] required" name="organization.name" type="text" readonly="readonly" style="width: 140px;"/>
			<a class="btnLook" href="${contextPath }/bqgl/issue/lookup2org" lookupGroup="organization" title="关联组织" width="400">查找带回</a>	
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