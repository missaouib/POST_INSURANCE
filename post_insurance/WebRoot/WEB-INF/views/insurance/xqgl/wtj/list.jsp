<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/xqgl/issue/list" page="${page }">
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="policy.orgCode" value="${policy_orgCode }"/>
	<input type="hidden" name="policy.name" value="${policy_name }"/>
	<input type="hidden" name="search_LTE_feeDate" value="${param.search_LTE_feeDate }"/>
	<input type="hidden" name="search_GTE_feeDate" value="${param.search_GTE_feeDate }"/>
	<input type="hidden" name="search_EQ_feeStatus" value="${param.search_EQ_feeStatus }"/>
	<input type="hidden" name="hqIssueType" value="${hqIssueType }"/>
	<input type="hidden" name="dealType" value="${dealType }"/>
	<input type="hidden" name="provActivity" value="${provActivity }"/>
	<input type="hidden" name="feeFailReason" value="${feeFailReason }"/>
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
						<form:select path="issue.search_EQ_feeStatus" id="xqStatus" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:options items="${xqStatusList }" itemLabel="desc" itemValue="desc"/>
						</form:select>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="policy.orgCode" id="xq_orgCode" type="hidden" value="${policy_orgCode }"/>
						<input class="validate[required] required" name="policy.name" id="xq_orgName" type="text" readonly="readonly" style="width: 100px;" value="${policy_name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="policy" title="选择机构" width="400">查</a>
					</td>
					<td>
						<label>失败原因：</label>
						<form:select path="issue.feeFailReason" id="xqFeeFailStatus" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:options items="${xqFailReasonList }" itemLabel="desc" itemValue="desc"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td>
						<label>总部催收：</label>
						<form:select path="issue.hqIssueType" id="xqDealStatus" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:options items="${xqDealStatusList }" itemLabel="desc" itemValue="desc"/>
						</form:select>
					</td>
					<td>
						<label>市/县催收：</label>
						<form:select path="issue.dealType" id="dealType" class="combox">
							<form:option value=""> -- </form:option>
							<form:options items="${orgTypeList }" itemLabel="typeName" itemValue="typeName"/>
						</form:select>
					</td>
					<td>
						<label>交费日起：</label>
						<input type="text" name="search_GTE_feeDate" class="date" style="width: 80px;" id="xq_date1" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_feeDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>交费日止：</label>
						<input type="text" name="search_LTE_feeDate" class="date" style="width: 80px;" id="xq_date2" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_feeDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
				</tr>
				<tr>
					<td>
						<label>省分标记：</label>
						<form:select path="issue.provActivity" id="xqAcitivties" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:options items="${provActivities }"/>
						</form:select>
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
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
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="580" href="${contextPath }/xqgl/issue/view/{slt_uid}"><span>查看详情</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Renewed:edit">
				<li class="line">line</li>
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/xqgl/issue/update/{slt_uid}"><span>市/县续期催收登记</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Renewed:provEdit">
				<li class="line">line</li>
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/xqgl/issue/provUpdate/{slt_uid}"><span>省分续期催登记</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Renewed:view">
				<li class="line">line</li>
				<li><a class="icon" href="${contextPath }/xqgl/issue/maxlist?provActivity=${provActivity}&search_LIKE_policy.policyNo=${search_LIKE_policy_policyNo }&policy.orgCode=${policy_orgCode }&search_LTE_feeDate=${param.search_LTE_feeDate }&search_GTE_feeDate=${param.search_GTE_feeDate }&encodeStatus=${encodeStatus }&encodeHqIssueType=${encodeHqIssueType }&encodeDealType=${encodeDealType }&encodeFeeFailReason=${encodeFeeFailReason }" target="dialog" rel="dlg_page1" max="true" title="续期催收列表" width="800" height="480"><span>全屏查看</span></a></li>
				<li class="line">line</li>
				<li><a class="icon" target="_blank" href="${contextPath }/xqgl/toXls?provActivity=${provActivity}&search_LIKE_policy.policyNo=${search_LIKE_policy_policyNo }&policy.orgCode=${policy_orgCode }&search_LTE_feeDate=${param.search_LTE_feeDate }&search_GTE_feeDate=${param.search_GTE_feeDate }&encodeStatus=${encodeStatus }&encodeHqIssueType=${encodeHqIssueType }&encodeDealType=${encodeDealType }&encodeFeeFailReason=${encodeFeeFailReason }"><span>导出Excel</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="icon" target="dialog" href="${contextPath }/xqgl/help" mask="true" width="530" height="430"><span>功能说明</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="185" width="150%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=policy.organization.name class="${page.orderField eq 'policy.organization.name' ? page.orderDirection : ''}">所属机构</th>
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
				<td>
				<c:choose>  
				    <c:when test="${fn:contains(item.policy.organization.name, '直属')}">  
				        <c:out value="${fn:replace(item.policy.organization.name,'邮政局直属中邮保险局','直属')}" />  
				    </c:when>  
				   <c:otherwise>  
				      <c:out value="${fn:replace(item.policy.organization.name,'邮政局中邮保险局','')}" />  
				    </c:otherwise>  
				</c:choose>
				</td>
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
				<td>${item.provDealRst}</td>
				<td>${item.fixStatus}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>