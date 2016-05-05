<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/client/list" page="${page }">
	<input type="hidden" name="search_GTE_policyFee" value="${param.search_GTE_policyFee }"/>
	<input type="hidden" name="search_LTE_policyFee" value="${param.search_LTE_policyFee }"/>
	<input type="hidden" name="orgCode" value="${policy_orgCode }"/>
	<input type="hidden" name="name" value="${policy_name }"/>
	<input type="hidden" name="search_LTE_policyDate" value="${param.search_LTE_policyDate }"/>
	<input type="hidden" name="search_GTE_policyDate" value="${param.search_GTE_policyDate }"/>
	<input type="hidden" name="search_LIKE_policyNo" value="${search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="search_LIKE_hasLetter" value="${param.search_LIKE_hasLetter }"/>
	<input type="hidden" name="status" value="${param.status }"/>
	<input type="hidden" name="search_LIKE_holder" value="${search_LIKE_policy_holder}"/>
</dwz:paginationForm>

<form method="post" id="hfForm" action="${contextPath }/client/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						保费：<input type="text" id="pf1" style="width: 50px;" name="search_GTE_policyFee" value="${param.search_GTE_policyFee }"/>&nbsp;-&nbsp;<input type="text" id="pf2" style="width: 50px;" name="search_LTE_policyFee" value="${param.search_LTE_policyFee }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="status" id="policyStatus" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:option value="有效">有效</form:option>
							<form:option value="终止">终止</form:option>
						</form:select>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="orgCode" id="c_orgCode" type="hidden" value="${policy_orgCode }"/>
						<input class="validate[required] required" name="name" id="c_orgName" type="text" readonly="readonly" style="width: 100px;" value="${policy_name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="policy" title="选择机构" width="400">查</a>
					</td>
					<td>
					投保人：<input type="text" id="policy_holder" style="width: 100px;" name="search_LIKE_holder" value="${search_LIKE_policy_holder }"/>
					</td>
				</tr>
				<tr>
					<td>
						保单号：<input type="text" id="hfPolicyNo" style="width: 100px;" name="search_LIKE_policyNo" value="${search_LIKE_policy_policyNo }"/>
					</td>
					<td>
						<label>承保日期：</label>
						<input type="text" name="search_GTE_policyDate" id="hfDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_policyDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>承保日期：</label>
						<input type="text" name="search_LTE_policyDate" id="hfDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_policyDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>产品：</label>
						
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						<label>缴费期间：</label>
						<input type="text" name="search_GTE_feeFrequency" id="ff1" class="validate[required] required" style="width: 80px;" value="${search_GTE_feeFrequency }"/>
					</td>
					<td>
						<label>缴费期间：</label>
						<input type="text" name="search_LTE_feeFrequency" id="ff2" class="validate[required] required" style="width: 80px;" value="${search_LTE_feeFrequency }"/>
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
			<shiro:hasPermission name="Client:view">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/client/view/{slt_uid}"><span>查看详情</span></a></li>
				<li class="line">line</li>
				<li><a class="icon" target="_blank" href="${contextPath }/client/toXls?search_GTE_policyFee=${param.search_GTE_policyFee }&search_LTE_policyFee=${param.search_LTE_policyFee }&orgCode=${policy_orgCode }&search_LTE_policyDate=${param.search_LTE_policyDate }&search_GTE_policyDate=${param.search_GTE_policyDate }&search_GTE_feeFrequency=${search_GTE_feeFrequency }&search_LTE_feeFrequency=${search_LTE_feeFrequency }&search_LIKE_policyNo=${search_LIKE_policy_policyNo }&encodeHasLetter=${encodeHasLetter }&encodeStatus=${encodeStatus == null?'null':encodeStatus }"><span>导出Excel</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="185" width="150%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>保单机构</th>
				<th orderField=canCallAgain class="${page.orderField eq 'canCallAgain' ? page.orderDirection : ''}">可再访</th>
				<th>可再访详情</th>
				<th orderField=issueNo class="${page.orderField eq 'issueNo' ? page.orderDirection : ''}">工单编号</th>
				<th orderField=status class="${page.orderField eq 'status' ? page.orderDirection : ''}">工单状态</th>
				<th>工单内容</th>
				<th orderField=readyDate class="${page.orderField eq 'readyDate' ? page.orderDirection : ''}">工单下发日期</th>
				<th>犹豫期</th>
				<th orderField=policyNo class="${page.orderField eq 'policyNo' ? page.orderDirection : ''}">所属保单号</th>
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">所属机构</th>
				<th>承保日期</th>
				<th>投保人</th>
				<th>联系电话</th>
				<th>联系地址</th>
				<th>险种名称</th>
				<th>出单网点</th>
				<shiro:hasPermission name="Callfail:provEdit">
				<th orderField=hasLetter class="${page.orderField eq 'hasLetter' ? page.orderDirection : ''}">信函记录</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td title="${item.organization.name}">
				<c:choose>  
				    <c:when test="${fn:contains(item.organization.name, '直属')}">  
				        <c:out value="${fn:replace(item.organization.name,'邮政局直属中邮保险局','直属')}" />  
				    </c:when>
				    <c:when test="${fn:contains(item.organization.name, '仲恺')}">  
				        ${fn:substring(item.organization.name, 0, 7)}
				    </c:when>
				   <c:otherwise>
				      <c:out value="${fn:replace(item.organization.name,'邮政局中邮保险局','')}" />  
				    </c:otherwise>  
				</c:choose>
				</td>
				<td>${item.canCallAgain}&nbsp;</td>
				<td>${item.canCallAgainRemark}&nbsp;&nbsp;${item.resetPhone}&nbsp;&nbsp;<fmt:formatDate value="${item.resetDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.issueNo}</td>
				<td>${item.status}</td>
				<td title="${item.issueContent}">${fn:substring(item.issueContent, 0, 15)}</td>
				<td><fmt:formatDate value="${item.readyDate }" pattern="yyyy-MM-dd"/></td>
				<td><span style="color:red; height:50%; margin-bottom:-contentheight;"><c:if test="${item.lastDateNum<0 }">0</c:if><c:if test="${item.lastDateNum>=0 }">${item.lastDateNum }</c:if></span></td>
				<td>${item.policyNo}</td>
				<td>${fn:replace(item.organization.name,'邮政局中邮保险局','')}</td>
				<td><fmt:formatDate value="${item.policyDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.holder}</td>
				<td>${item.holderMobile eq ""?item.holderPhone:item.holderMobile}</td>
				<td>${item.addr}</td>
				<td>${item.prodName}</td>
				<td>
					<c:choose>  
					    <c:when test="${fn:length(item.bankName) > 14}">  
					        <c:out value="${fn:substring(item.bankName, 14, 30)}" />  
					    </c:when>  
					   <c:otherwise>  
					      <c:out value="${item.bankName}" />  
					    </c:otherwise>  
					</c:choose>
				</td>
				<shiro:hasPermission name="Callfail:provEdit">
				<td>${item.hasLetter}</td>
				</shiro:hasPermission>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>