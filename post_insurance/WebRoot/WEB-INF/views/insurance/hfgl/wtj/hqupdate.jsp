<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath}/hfgl/issue/hqUpdate" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${issue.id}"/>
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>回访不成功件基本信息</legend>
		<p>
			<label>工单编号：</label>
			<span class="unit">${issue.issueNo }</span>
		</p>
		<p>
			<label>工单状态：</label>
			<span class="unit">${issue.status }</span>
		</p>
		<p>
			<label>保单号：</label>
			<span class="unit">${issue.policy.policyNo }</span>
		</p>
		<p>
			<label>险种名称：</label>
			<span class="unit">${issue.policy.prodName }</span>
		</p>
		<p>
			<label>客户姓名：</label>
			<span class="unit">${issue.policy.holder }</span>
		</p>
		<p>
			<label>客户电话：</label>
			<span class="unit">${issue.holderPhone }</span>
		</p>
		<p>
			<label>客户手机：</label>
			<span class="unit">${issue.holderMobile }</span>
		</p>
		<p>
			<label>所属机构：</label>
			<span class="unit">${issue.organization.name }</span>
		</p>
		<p class="nowrap">
			<label>出单网点：</label>
			<span class="unit">${issue.bankName }</span>
		</p>
		<p>
			<label>回访日期：</label>
			<span class="unit">${issue.callDate }</span>
		</p>
		<p>
			<label>不成功日期：</label>
			<span class="unit">${issue.issueDate }</span>
		</p>
		<p>
			<label>下发日期：</label>
			<span class="unit">${issue.issueTime }</span>
		</p>
		<p>
			<label>工单类型：</label>
			<span class="unit">${issue.issueType }</span>
		</p>
		<p class="nowrap">
			<label>工单内容：</label>
			<span class="unit">${issue.issueContent }</span>
		</p>
	</fieldset>
	<fieldset>
		<p>
			<label>重置电话为：</label>
			<span class="unit">${issue.resetPhone }</span>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
	<legend>回访结果</legend>
		<p class="nowrap">
			<label>回访结果：</label>
			<form:radiobutton path="issue.status" value="二访成功"/>成功&nbsp;&nbsp;
			<form:radiobutton path="issue.status" value="二访失败"/>失败&nbsp;&nbsp;
		</p>
	</fieldset>
	<fieldset>
		<legend>第一次回访处理详情</legend>
		<p>
			<label>不成功类型：</label>
			<form:select path="issue.hqDealType" onchange="javascript:$('#hqDealRst').val($('#hqDealType').find('option:selected').text())">
				<form:option value=""> -- </form:option>
				<form:options items="${hqTypeList }" itemLabel="typeDesc" itemValue="typeName"/>
			</form:select>
		</p>
		<p>
			<label>不成结果：</label>
			<textarea name="hqDealRst" id="hqDealRst" cols="25" rows="2">${issue.hqDealRst }</textarea>
		</p>
		<p>&nbsp;</p><p>&nbsp;</p>
		<p>
			<label>经办人：</label>
			<input type="text" name="hqDealMan" id="hqDealMan" value="${issue.hqDealMan }"/>
		</p>
		<p>
			<label>回访日期：</label>
			<input type="text" name="hqDealDate" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value='${issue.hqDealDate }' pattern='yyyy-MM-dd'/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
	</fieldset>
	<fieldset>
		<legend>第二次回访处理详情</legend>
		<p>
			<label>不成功类型：</label>
			<form:select path="issue.hqDealType2" onchange="javascript:$('#hqDealRst2').val($('#hqDealType2').find('option:selected').text())">
				<form:option value=""> -- </form:option>
				<form:options items="${hqTypeList }" itemLabel="typeDesc" itemValue="typeName"/>
			</form:select>
		</p>
		<p>
			<label>不成结果：</label>
			<textarea name="hqDealRst2" id="hqDealRst2" cols="25" rows="2">${issue.hqDealRst2 }</textarea>
		</p>
		<p>&nbsp;</p><p>&nbsp;</p>
		<p>
			<label>经办人：</label>
			<input type="text" name="hqDealMan2" id="hqDealMan2" value="${issue.hqDealMan2 }"/>
		</p>
		<p>
			<label>回访日期：</label>
			<input type="text" name="hqDealDate2" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value='${issue.hqDealDate2 }' pattern='yyyy-MM-dd'/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
	</fieldset>
	<fieldset>
		<legend>第三次回访处理详情</legend>
		<p>
			<label>不成功类型：</label>
			<form:select path="issue.hqDealType3" onchange="javascript:$('#hqDealRst3').val($('#hqDealType3').find('option:selected').text())">
				<form:option value=""> -- </form:option>
				<form:options items="${hqTypeList }" itemLabel="typeDesc" itemValue="typeName"/>
			</form:select>
		</p>
		<p>
			<label>不成结果：</label>
			<textarea name="hqDealRst3" id="hqDealRst3" cols="25" rows="2">${issue.hqDealRst3 }</textarea>
		</p>
		<p>&nbsp;</p><p>&nbsp;</p>
		<p>
			<label>经办人：</label>
			<input type="text" name="hqDealMan3" id="hqDealMan3" value="${issue.hqDealMan3 }"/>
		</p>
		<p>
			<label>回访日期：</label>
			<input type="text" name="hqDealDate3" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value='${issue.hqDealDate3 }' pattern='yyyy-MM-dd'/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
	</fieldset>
	<fieldset>
		<legend>第四次回访处理详情</legend>
		<p>
			<label>不成功类型：</label>
			<form:select path="issue.hqDealType4" onchange="javascript:$('#hqDealRst4').val($('#hqDealType4').find('option:selected').text())">
				<form:option value=""> -- </form:option>
				<form:options items="${hqTypeList }" itemLabel="typeDesc" itemValue="typeName"/>
			</form:select>
		</p>
		<p>
			<label>不成结果：</label>
			<textarea name="hqDealRst4" id="hqDealRst4" cols="25" rows="2">${issue.hqDealRst4 }</textarea>
		</p>
		<p>&nbsp;</p><p>&nbsp;</p>
		<p>
			<label>经办人：</label>
			<input type="text" name="hqDealMan4" id="hqDealMan4" value="${issue.hqDealMan4 }"/>
		</p>
		<p>
			<label>回访日期：</label>
			<input type="text" name="hqDealDate4" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value='${issue.hqDealDate4 }' pattern='yyyy-MM-dd'/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
	</fieldset>
	<fieldset>
		<legend>第五次回访处理详情</legend>
		<p>
			<label>不成功类型：</label>
			<form:select path="issue.hqDealType5" onchange="javascript:$('#hqDealRst5').val($('#hqDealType5').find('option:selected').text())">
				<form:option value=""> -- </form:option>
				<form:options items="${hqTypeList }" itemLabel="typeDesc" itemValue="typeName"/>
			</form:select>
		</p>
		<p>
			<label>不成结果：</label>
			<textarea name="hqDealRst5" id="hqDealRst5" cols="25" rows="2">${issue.hqDealRst5 }</textarea>
		</p>
		<p>&nbsp;</p><p>&nbsp;</p>
		<p>
			<label>经办人：</label>
			<input type="text" name="hqDealMan5" id="hqDealMan5" value="${issue.hqDealMan5 }"/>
		</p>
		<p>
			<label>回访日期：</label>
			<input type="text" name="hqDealDate5" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value='${issue.hqDealDate5 }' pattern='yyyy-MM-dd'/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
	</fieldset>
	<fieldset>
		<legend>第六次回访处理详情</legend>
		<p>
			<label>不成功类型：</label>
			<form:select path="issue.hqDealType6" onchange="javascript:$('#hqDealRst6').val($('#hqDealType6').find('option:selected').text())">
				<form:option value=""> -- </form:option>
				<form:options items="${hqTypeList }" itemLabel="typeDesc" itemValue="typeName"/>
			</form:select>
		</p>
		<p>
			<label>不成结果：</label>
			<textarea name="hqDealRst6" id="hqDealRst6" cols="25" rows="2">${issue.hqDealRst6 }</textarea>
		</p>
		<p>&nbsp;</p><p>&nbsp;</p>
		<p>
			<label>经办人：</label>
			<input type="text" name="hqDealMan6" id="hqDealMan6" value="${issue.hqDealMan6 }"/>
		</p>
		<p>
			<label>回访日期：</label>
			<input type="text" name="hqDealDate6" class="date" dateFmt="yyyy-MM-dd" readonly="true" value="<fmt:formatDate value='${issue.hqDealDate6 }' pattern='yyyy-MM-dd'/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
	</fieldset>
	<div class="divider"></div>
	<fieldset>
		<legend>分公司回访情况</legend>
		<p class="nowrap">
			<label>分公司回访产生问题记录：</label>
			<span class="unit">${issue.provIssueType }</span>
		</p>
		<p class="nowrap">
			<label>分公司回访结果：</label>
			<span class="unit">${issue.provDealRst }</span>
		</p>
		<p class="nowrap">
			<label>分公司回访日期：</label>
			<span class="unit">${issue.provDealDate }</span>
		</p>
		<p class="nowrap">
			<label>分公司回访备注：</label>
			<span class="unit">${issue.provDealRemark }</span>
		</p>
	</fieldset>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div <c:if test='${issue.status eq "已结案" }'>class="buttonDisabled"</c:if> <c:if test='${issue.status ne "已结案" }'>class="button"</c:if>><div class="buttonContent"><button type="submit" <c:if test='${issue.status eq "已结案" }'>disabled=true</c:if>>提交回访结果</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>