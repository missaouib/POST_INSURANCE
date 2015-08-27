<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/qygl/underwrite/list" page="${page }">
	<input type="hidden" name="search_LIKE_issueNo" value="${param.search_LIKE_issueNo }"/>
	<input type="hidden" name="policy.orgCode" value="${policy_orgCode }"/>
	<input type="hidden" name="policy.name" value="${policy_name }"/>
	<input type="hidden" name="search_LTE_ybtDate" value="${param.search_LTE_ybtDate }"/>
	<input type="hidden" name="search_GTE_ybtDate" value="${param.search_GTE_ybtDate }"/>
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="search_LIKE_hasLetter" value="${param.search_LIKE_hasLetter }"/>
	<input type="hidden" name="status" value="${param.status }"/>
</dwz:paginationForm>

<form method="post" id="hfForm" action="${contextPath }/qygl/underwrite/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>工单编号：</label>
					<input type="text" id="hfIssueNo" name="search_LIKE_issueNo" value="${param.search_LIKE_issueNo }"/>
				</li>
				<li>
					<label>保单号：</label>
					<input type="text" id="hfPolicyNo" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
				</li>
				<li>
					<label>工单状态：</label>
					<form:select path="issue.status" id="hfStatus" class="combox">
						<form:option value=""> -- -- </form:option>
						<form:options items="${hfStatusList }" itemLabel="desc" itemValue="desc"/>
					</form:select>
				</li>
				<li>
					<label>所属机构：</label>
					<input name="policy.orgCode" id="xq_orgCode" type="hidden" value="${policy_orgCode }"/>
					<input class="validate[required] required" name="policy.name" id="xq_orgName" type="text" readonly="readonly" style="width: 140px;" value="${policy_name }"/>
					<a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="policy" title="选择机构" width="400">查找带回</a>
				</li>				
			</ul>
			<ul class="searchContent">
				<li>
					<label>待处理开始日期：</label>
					<input type="text" name="search_GTE_ybtDate" id="hfDate1" class="date validate[required] required" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_ybtDate }"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
				</li>
				<li>
					<label>待处理结束日期：</label>
					<input type="text" name="search_LTE_ybtDate" id="hfDate2" class="date validate[required] required" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_ybtDate }"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
				</li>
				<li>
					<label>已信函：</label>
					<form:select path="issue.search_LIKE_hasLetter" id="hfHsLetter" class="combox">
						<form:option value=""> -- -- </form:option>
						<form:option value="信函已发"> 信函已发 </form:option>
					</form:select>
				</li>
			</ul>
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
			<shiro:hasPermission name="UnderWrite:view">
				<li><a iconClass="user_edit" target="dialog" rel="underwrite_edit" mask="true" width="850" height="580" href="${contextPath }/qygl/underwrite/view/{slt_uid}"><span>查看人核件</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="UnderWrite:edit">
				<li><a iconClass="user_edit" target="dialog" rel="underwrite_edit" mask="true" width="850" height="580" href="${contextPath }/qygl/underwrite/view/{slt_uid}"><span>更新人核件</span></a></li>
				<li><a iconClass="user_go" href="${contextPath}/hfgl/updateResetStatus/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>回销登记</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="UnderWrite:provEdit">
				<li><a iconClass="user_edit" target="dialog" rel="underwrite_edit" mask="true" width="850" height="620" href="${contextPath }/qygl/underwrite/update/{slt_uid}"><span>新建人核件</span></a></li>
				<li><a iconClass="user_go" href="${contextPath}/hfgl/updateResetStatus/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>省分寄出</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="UnderWrite:cityEdit">
				<li><a iconClass="user_go" href="${contextPath}/hfgl/updateResetStatus/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>地市接收</span></a></li>
				<li><a iconClass="user_go" href="${contextPath}/hfgl/updateResetStatus/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>地市寄出</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="UnderWrite:areaEdit">
				<li><a iconClass="user_go" href="${contextPath}/hfgl/updateResetStatus/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>县区接收</span></a></li>
				<li><a iconClass="user_go" href="${contextPath}/hfgl/updateResetStatus/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>县区寄出</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="icon" target="dialog" href="${contextPath }/hfgl/help" mask="true" width="530" height="430"><span>功能说明</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="160" width="140%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=issueNo class="${page.orderField eq 'issueNo' ? page.orderDirection : ''}">工单编号</th>
				<th>工单内容</th>
				<th orderField=ybtDate class="${page.orderField eq 'ybtDate' ? page.orderDirection : ''}">待处理时间</th>
				<th>离犹豫期(天)</th>
				<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">所属保单号</th>
				<th>投保人</th>
				<th>联系电话</th>
				<th>险种名称</th>
				<th>保单机构</th>
				<th>出单网点</th>
				<th orderField=status class="${page.orderField eq 'status' ? page.orderDirection : ''}">工单状态</th>
				<th>重置电话</th>
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">所属机构</th>
				<shiro:hasPermission name="UnderWrite:provEdit">
				<th orderField=hasLetter class="${page.orderField eq 'hasLetter' ? page.orderDirection : ''}">信函记录</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.issueNo}</td>
				<td>${item.issueContent}</td>
				<td><fmt:formatDate value="${item.ybtDate }" pattern="yyyy-MM-dd"/></td>
				<td><span style="color:red; height:50%; margin-bottom:-contentheight;"><c:if test="${item.lastDateNum<0 }">0</c:if><c:if test="${item.lastDateNum>=0 }">${item.lastDateNum }</c:if></span></td>
				<td>${item.policy.policyNo}</td>
				<td>${item.policy.holder}</td>
				<td>${item.holderMobile eq ""?item.holderPhone:item.holderMobile}</td>
				<td>${item.policy.prodName}</td>
				<td>${item.policy.organization.name}</td>
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
				<td>${item.status}</td>
				<td>${item.resetPhone}</td>
				<td>${item.organization.name}</td>
				<shiro:hasPermission name="UnderWrite:provEdit">
				<td>${item.hasLetter}</td>
				</shiro:hasPermission>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>