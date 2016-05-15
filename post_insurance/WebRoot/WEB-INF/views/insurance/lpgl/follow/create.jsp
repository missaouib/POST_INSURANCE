<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/lpgl/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>机构：</label>
			<input name="organization.id" id="uw_orgId" type="hidden" value=""/>
					<input class="validate[required] required" name="organization.name" id="uw_orgName" type="text" readonly="readonly" style="width: 140px;" value=""/>
					<a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="organization" title="选择机构" width="400">查找带回</a>
		</p>
		<p>
			<label>出险人：</label>
			<input type="text" name="insured" class="input-medium validate[required,maxSize[32]] required" maxlength="32" value=""/>
		</p>
		<p>
			<label>报案人：</label>
			<input type="text" name="reporter" class="input-medium validate[maxSize[32]]" maxlength="32" value=""/>
		</p>
		<p>
			<label>报案人电话 ：</label>
			<input type="text" name="reporterPhone" class="phone validate[maxSize[11]]" maxlength="11" value=""/>
		</p>
		<p>
			<label>出险日期：</label>
			<input type="text" name="caseDate" id="caseDate" class="date" dateFmt="yyyy-MM-dd" value=""/>
					<a class="inputDateButton" href="javascript:;">选择</a>
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
		<p>
			<label>报案日期：</label>
			<input type="text" name="reporteDate" id="reporteDate" class="date" dateFmt="yyyy-MM-dd" value=""/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>立案日期：</label>
			<input type="text" name="recordDate" id="recordDate" class="date" dateFmt="yyyy-MM-dd" value=""/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>结案日期：</label>
			<input type="text" name="closeDate" id="closeDate" class="date" dateFmt="yyyy-MM-dd" value=""/>
					<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>赔付金额：</label>
			<input type="text" name="payFee" class="input-medium validate[maxSize[32]]" maxlength="32" value=""/>
		</p>
		<p>
			<label>案件状态：</label>
			<select name="caseStatus" id="caseStatus" class="combox validate[required] required">
				<option value="待报案">待报案</option>
				<option value="待立案">待立案</option>
				<option value="待调查">待调查</option>
				<option value="待结案">待结案</option>
				<option value="拒付退费">拒付退费</option>
				<option value="结案关闭">结案关闭</option>
				<option value="不予立案">不予立案</option>
			</select>
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