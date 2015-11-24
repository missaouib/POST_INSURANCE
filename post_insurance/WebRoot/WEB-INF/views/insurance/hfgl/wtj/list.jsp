<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/hfgl/issue/list" page="${page }">
	<input type="hidden" name="search_LIKE_issueNo" value="${param.search_LIKE_issueNo }"/>
	<input type="hidden" name="policy.orgCode" value="${policy_orgCode }"/>
	<input type="hidden" name="policy.name" value="${policy_name }"/>
	<input type="hidden" name="search_LTE_shouldDate" value="${param.search_LTE_shouldDate }"/>
	<input type="hidden" name="search_GTE_shouldDate" value="${param.search_GTE_shouldDate }"/>
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="search_LIKE_hasLetter" value="${param.search_LIKE_hasLetter }"/>
	<input type="hidden" name="status" value="${param.status }"/>
</dwz:paginationForm>

<form method="post" id="hfForm" action="${contextPath }/hfgl/issue/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						工单号：<input type="text" id="hfIssueNo" style="width: 100px;" name="search_LIKE_issueNo" value="${param.search_LIKE_issueNo }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="issue.status" id="hfStatus" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:options items="${hfStatusList }" itemLabel="desc" itemValue="desc"/>
						</form:select>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="policy.orgCode" id="xq_orgCode" type="hidden" value="${policy_orgCode }"/>
						<input class="validate[required] required" name="policy.name" id="xq_orgName" type="text" readonly="readonly" style="width: 100px;" value="${policy_name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="policy" title="选择机构" width="400">查</a>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						保单号：<input type="text" id="hfPolicyNo" style="width: 100px;" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
					</td>
					<td>
						<label>承保开始日期：</label>
						<input type="text" name="search_GTE_shouldDate" id="hfDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_shouldDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>承保结束日期：</label>
						<input type="text" name="search_LTE_shouldDate" id="hfDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_shouldDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>已信函：</label>
						<form:select path="issue.search_LIKE_hasLetter" id="hfHsLetter" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:option value="信函已发"> 信函已发 </form:option>
						</form:select>
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
			<shiro:hasPermission name="Callfail:view">
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/hfgl/issue/view/{slt_uid}"><span>查看详情</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Callfail:edit">
				<li class="line">line</li>
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/hfgl/issue/update/{slt_uid}"><span>回复</span></a></li>
				<li class="line">line</li>
				<li><a iconClass="user_go" href="${contextPath}/hfgl/updateResetStatus/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>电话重置</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Callfail:provEdit">
			<li class="line">line</li>
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/hfgl/issue/provUpdate/{slt_uid}"><span>省分登记</span></a></li>
				<li class="line">line</li>
				<li><a target="myDialog" rel="ids" href="${contextPath }/hfgl/issue/toSetMailDate" class="edit"><span>批量已发信函</span></a></li>
				<li class="line">line</li>
				<!-- <li><a iconClass="user_go" target="ajaxTodo" href="${contextPath }/hfgl/issue/CloseStatus/{slt_uid}" title="确认办结案关闭?"><span>结案关闭</span></a></li> -->
				<li><a iconClass="user_go" target="selectedTodo" rel="ids" href="${contextPath }/hfgl/issue/CloseStatus" title="确认要结案关闭?"><span>批量结案关闭</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Callfail:11185Edit">
			<li class="line">line</li>
				<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/hfgl/issue/hqUpdate/{slt_uid}"><span>二访中心登记</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Callfail:view">
				<li class="line">line</li>
				<li><a class="icon" href="${contextPath }/hfgl/issue/maxlist?search_LIKE_issueNo=${param.search_LIKE_issueNo }&policy.orgCode=${policy_orgCode }&policy.name=${policy_name }&search_LTE_shouldDate=${param.search_LTE_shouldDate }&search_GTE_shouldDate=${param.search_GTE_shouldDate }&search_LIKE_policy.policyNo=${search_LIKE_policy_policyNo }&search_LIKE_hasLetter=${param.search_LIKE_hasLetter }&status=${param.status == null?'null':param.status }" target="dialog" rel="dlg_page1" max="true" title="回访不成功列表" width="800" height="480"><span>全屏查看</span></a></li>
				<li class="line">line</li>
				<li><a class="icon" target="_blank" href="${contextPath }/hfgl/toXls?search_LIKE_issueNo=${param.search_LIKE_issueNo }&policy.orgCode=${policy_orgCode }&search_LTE_shouldDate=${param.search_LTE_shouldDate }&search_GTE_shouldDate=${param.search_GTE_shouldDate }&search_LIKE_policy.policyNo=${search_LIKE_policy_policyNo }&search_LIKE_hasLetter=${param.search_LIKE_hasLetter }&status=${param.status == null?'null':param.status }"><span>导出Excel</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="icon" target="dialog" href="${contextPath }/hfgl/help" mask="true" width="530" height="430"><span>功能说明</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="160" width="140%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>保单机构</th>
				<th orderField=issueNo class="${page.orderField eq 'issueNo' ? page.orderDirection : ''}">工单编号</th>
				<th orderField=status class="${page.orderField eq 'status' ? page.orderDirection : ''}">工单状态</th>
				<th>工单内容</th>
				<th orderField=operateTime class="${page.orderField eq 'operateTime' ? page.orderDirection : ''}">系统导入</th>
				<th>离犹豫期(天)</th>
				<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">所属保单号</th>
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">所属机构</th>
				<th>投保人</th>
				<th>联系电话</th>
				<th>联系地址</th>
				<th>险种名称</th>
				<th>出单网点</th>
				<th>重置电话</th>
				<shiro:hasPermission name="Callfail:provEdit">
				<th orderField=hasLetter class="${page.orderField eq 'hasLetter' ? page.orderDirection : ''}">信函记录</th>
				</shiro:hasPermission>
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
				<td>${item.issueNo}</td>
				<td>${item.status}</td>
				<td>${item.issueContent}</td>
				<td><fmt:formatDate value="${item.operateTime }" pattern="yyyy-MM-dd"/></td>
				<td><span style="color:red; height:50%; margin-bottom:-contentheight;"><c:if test="${item.lastDateNum<0 }">0</c:if><c:if test="${item.lastDateNum>=0 }">${item.lastDateNum }</c:if></span></td>
				<td>${item.policy.policyNo}</td>
				<td>${fn:replace(item.organization.name,'邮政局中邮保险局','')}</td>
				<td>${item.policy.holder}</td>
				<td>${item.holderMobile eq ""?item.holderPhone:item.holderMobile}</td>
				<td>${item.addr}</td>
				<td>${item.policy.prodName}</td>
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
				<td>${item.resetPhone}</td>
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