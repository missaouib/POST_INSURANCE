<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/xqgl/issue/list" page="${page }">
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="policy.orgCode" value="${policy_orgCode }"/>
	<input type="hidden" name="policy.name" value="${policy_name }"/>
	<input type="hidden" name="search_LTE_feeDate" value="${param.search_LTE_feeDate }"/>
	<input type="hidden" name="search_GTE_feeDate" value="${param.search_GTE_feeDate }"/>
	<input type="hidden" name="search_LIKE_feeStatus" value="${param.search_LIKE_feeStatus }"/>
	<input type="hidden" name="search_LIKE_hqDealRemark" value="${param.search_LIKE_hqDealRemark }"/>
	<input type="hidden" name="search_LIKE_dealType" value="${param.search_LIKE_dealType }"/>
</dwz:paginationForm>

<form id="xqForm" method="post" action="${contextPath }/xqgl/issue/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						保单号：<input type="text" id="xq_policyno" name="search_LIKE_policy.policyNo" style="width: 100px;" value="${search_LIKE_policy_policyNo }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="issue.search_LIKE_feeStatus" id="xqStatus" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:options items="${xqStatusList }" itemLabel="desc" itemValue="desc"/>
						</form:select>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="policy.orgCode" id="xq_orgCode" type="hidden" value="${policy_orgCode }"/>
						<input class="validate[required] required" name="policy.name" id="xq_orgName" type="text" readonly="readonly" style="width: 100px;" value="${policy_name }"/>
						<a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="policy" title="选择机构" width="400">查</a>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<label>总部催收：</label>
						<form:select path="issue.search_LIKE_hqDealRemark" id="xqDealStatus" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:options items="${xqDealStatusList }" itemLabel="desc" itemValue="desc"/>
						</form:select>
					</td>
					<td>
						<label>市/县催收：</label>
						<form:select path="issue.search_LIKE_dealType" id="dealType" class="combox">
							<form:option value=""> -- </form:option>
							<form:options items="${orgTypeList }" itemLabel="typeDesc" itemValue="typeName"/>
						</form:select>
					</td>
					<td>
						<label>交费日起：</label>
						<input type="text" name="search_GTE_feeDate" class="date" style="width: 80px;" id="xq_date1" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_feeDate }"/>
						<a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>交费日止：</label>
						<input type="text" name="search_LTE_feeDate" class="date" style="width: 80px;" id="xq_date2" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_feeDate }"/>
						<a class="inputDateButton" href="javascript:;">选择</a>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="Renewed:view">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="580" href="${contextPath }/xqgl/issue/view/{slt_uid}"><span>查看续期催收件详情</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Renewed:edit">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/xqgl/issue/update/{slt_uid}"><span>市/县续期催收登记</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Renewed:provEdit">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/xqgl/issue/provUpdate/{slt_uid}"><span>省分续期催登记</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="icon" target="dialog" href="${contextPath }/xqgl/help" mask="true" width="530" height="430"><span>功能说明</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="160" width="150%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">所属机构</th>
				<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">保单号</th>
				<th orderField=prdName class="${page.orderField eq 'prdName' ? page.orderDirection : ''}">险种名称</th>
				<th>姓名</th>
				<th>联系电话</th>
				<th orderField=policyFee class="${page.orderField eq 'policyFee' ? page.orderDirection : ''}">保费</th>
				<th orderField=feeDate class="${page.orderField eq 'feeDate' ? page.orderDirection : ''}">交费对应日</th>
				<th>宽限期还有（天）</th>
				<th orderField=feeStatus class="${page.orderField eq 'feeStatus' ? page.orderDirection : ''}">状态</th>
				<th orderField=feeFailReason class="${page.orderField eq 'feeFailReason' ? page.orderDirection : ''}">交费失败原因</th>
				<th>账号</th>
				<th>网点</th>
				<th>总部催收情况</th>
				<th>省分催收情况</th>
				<th>市县催收情况</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${fn:replace(item.policy.organization.name,'中邮保险局','')}</td>
				<td>${item.policy.policyNo}</td>
				<td>${item.prdName}</td>
				<td>${item.holder}</td>
				<td>${item.mobile eq ""?item.phone:item.mobile}</td>
				<td>${item.policyFee}</td>
				<td><fmt:formatDate value="${item.feeDate }" pattern="yyyy-MM-dd"/></td>
				<td><span style="color:red; height:50%; margin-bottom:-contentheight;">${item.lastDateNum }</span></td>
				<td>${item.feeStatus }</td>
				<td>${item.feeFailReason}</td>
				<td>${item.account}</td>
				<td>
					<c:choose>  
					    <c:when test="${fn:length(item.netName) > 14}">  
					        <c:out value="${fn:substring(item.netName, 14, 30)}" />  
					    </c:when>  
					   <c:otherwise>  
					      <c:out value="${item.netName}" />  
					    </c:otherwise>  
					</c:choose>
				</td>
				<td>${item.hqIssueType}</td>
				<td>${item.provIssueType}</td>
				<td>${item.dealType}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>