<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/kfgl/inquire/list" page="${page }">
	<input type="hidden" name="search_LIKE_inquireNo" value="${param.search_LIKE_inquireNo }"/>
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="name" value="${name }"/>
	<input type="hidden" name="inquireStatus" value="${inquireStatus }"/>
	<input type="hidden" name="inquireSubtype" value="${inquireSubtype }"/>
	<input type="hidden" name="kfstatus_flag" value="${kfstatus_flag }"/>
	<input type="hidden" name="search_LTE_operateTime" value="${param.search_LTE_operateTime }"/>
	<input type="hidden" name="search_GTE_operateTime" value="${param.search_GTE_operateTime }"/>
</dwz:paginationForm>

<form method="post" id="kfForm" action="${contextPath }/kfgl/inquire/list" onsubmit="return navTabSearch(this)">
<input type="hidden" name="kfstatus_flag" id="kfstatus_flag" value="${kfstatus_flag }"/>
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						工单号：<input type="text" id="kfPolicyNo" name="search_LIKE_inquireNo" style="width: 80px;" value="${param.search_LIKE_inquireNo }"/>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="orgCode" id="kf_orgCode" type="hidden" value="${orgCode }"/>
						<input class="validate[required] required" name="name" id="kf_orgName" type="text" readonly="readonly" style="width: 100px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
					<td>
						<label>开始起日期：</label>
						<input type="text" name="search_GTE_operateTime" id="kfDate1" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_operateTime }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>结束止日期：</label>
						<input type="text" name="search_LTE_operateTime" id="kfDate2" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_operateTime }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
				</tr>
				<tr>
					<td>
						保单号：<input type="text" id="kfPolicyNo" style="width: 80px;" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="inquire.inquireStatus" id="kfaskStatus" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:options items="${statusList }" itemLabel="desc"/>
						</form:select>
					</td>
					<td>
						<label>工单类型（经办部门）：</label>
						<form:select path="inquire.inquireSubtype" id="kfinquireSubtype" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:options items="${inquireSubtypes }"/>
						</form:select>
					</td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>						
					<li><div class="button"><div class="buttonContent"><button type="submit" onclick="$('#kfstatus_flag').val('');">搜索</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="Inquire:view">
				<li><a iconClass="magnifier" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/kfgl/inquire/view/{slt_uid}"><span>查看(审核)</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Inquire:edit">
				<li class="line">line</li>
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/kfgl/inquire/update/{slt_uid}"><span>回复</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Inquire:provAdmin">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/kfgl/inquire/urge" title="确认要催办?"><span>批量催办</span></a></li>
				<li><a class="icon" target="myDialog" rel="ids" href="${contextPath }/kfgl/inquire/toWord"><span>批量word</span></a></li>
				<li><a iconClass="shield_add" target="dialog" mask="true" width="400" height="500" href="${contextPath }/kfgl/inquire/userRole/{slt_uid}"><span>客服转办到</span></a></li>
				<li><a class="icon" target="selectedTodo" rel="ids" href="${contextPath }/kfgl/inquire/batchDeal" title="确认要批量审核通过?"><span>批量审核</span></a></li>
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/kfgl/inquire/CloseStatus" title="确认要结案关闭?"><span>批量结案</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Inquire:provEdit">
				<li class="line">line</li>
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/kfgl/inquire/batchToCity" title="确认转办地市?"><span>批量转办地市</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="edit" target="navTab" rel="printInquire" mask="true" width="820" height="520" href="${contextPath }/kfgl/inquire/print/{slt_uid}"><span>打印工单</span></a></li>
			<!-- 
			<li class="line">line</li>
			<li><a class="edit" target="navTab" rel="printInquireList" mask="true" width="820" height="520" href="${contextPath }/kfgl/inquires/print"><span>批打工单</span></a></li>
			 -->
			<shiro:hasPermission name="Inquire:view">
				<li class="line">line</li>
				<li><a class="icon" target="_blank" href="${contextPath }/kfgl/inquirelist/toXls?search_LIKE_inquireNo=${param.search_LIKE_inquireNo }&orgCode=${orgCode }&search_LTE_operateTime=${param.search_LTE_operateTime }&search_GTE_operateTime=${param.search_GTE_operateTime }&search_LTE_billBackDate=${param.search_LTE_billBackDate }&search_GTE_billBackDate=${param.search_GTE_billBackDate }&search_LIKE_policy.policyNo=${search_LIKE_policy_policyNo }&inquireStatus=${inquireStatus }&inquireSubtype=${inquireSubtype}&kfstatus_flag=${kfstatus_flag}"><span>导出Excel</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="icon" target="dialog" href="${contextPath }/kfgl/help" mask="true" width="530" height="430"><span>功能说明</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>	
				<th>催办</th>
				<th orderField=policy.organization.name class="${page.orderField eq 'policy.organization.name' ? page.orderDirection : ''}">所属机构</th>
				<th>标记</th>
				<th>经办部门</th>
				<th>转地市</th>
				<th orderField=inquireNo class="${page.orderField eq 'inquireNo' ? page.orderDirection : ''}">工单编号</th>
				<th>工单内容</th>
				<th orderField=operateTime class="${page.orderField eq 'operateTime' ? page.orderDirection : ''}">开始处理</th>
				<th>还剩(天)</th>
				<th>客户</th>
				<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">所属保单号</th>
				<th orderField=inquireStatus class="${page.orderField eq 'inquireStatus' ? page.orderDirection : ''}">状态</th>
				<th>处理情况</th>
				<th>经办人</th>
				<th>经办日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${inquires}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.urge?"是":"否"}</td>
				<td>${empty inquire.gpolicyNo?item.organ.shortName:inquire.gorganName}</td>
				<td>
					<c:choose>
                        <c:when test="${not empty item.gpolicyNo}">
                        	<div style="color: blue;vertical-align:middle;">团险</div>
                        </c:when>
                        <c:when test="${not empty item.policyNos}">
                        	<div style="color: blue;vertical-align:middle;">个险</div>
                        </c:when>
                       <c:otherwise>个险团险 </c:otherwise>
                    </c:choose>
				</td>
				<td>${item.assignTo}</td>
				<td>
					<c:choose>
                        <c:when test="${item.cityDealFlag}">
                        	<div style="color: blue;vertical-align:middle;">已转办</div>
                        </c:when>
                        <c:otherwise>否 </c:otherwise>
                    </c:choose>
				</td>
				<td>${item.inquireNo}</td>
				<td title="${item.inquireDesc}">${fn:substring(item.inquireDesc, 0, 25)}</td>
				<td>${item.operateTime }</td>
				<td><div style="color: red;vertical-align:middle;font-weight:bold;">${item.lastDateNum }</div></td>
				<td>${item.client}</td>
				<td>${empty item.gpolicyNo?item.policyNos:item.gpolicyNo}</td>
				<td><a target="dialog" rel="lookup2inquire_edit" mask="true" width="520" height="520" href="${contextPath }/kfgl/inquire/status/${item.id}">
					<c:choose>
                        <c:when test="${item.inquireStatus eq 'NewStatus'}">
                        	<div style="color: red;vertical-align:middle;font-weight:bold;">待处理</div>
                        </c:when>
                        <c:when test="${item.inquireStatus eq 'IngStatus'}">
                        	<div style="color: blue;vertical-align:middle;font-weight:bold;">待审核</div>
                        </c:when>
                        <c:when test="${item.inquireStatus eq 'DealStatus'}">
                        	<div style="color: blue;vertical-align:middle;font-weight:bold;">已审核</div>
                        </c:when>
                        <c:when test="${item.inquireStatus eq 'CTStatus'}">
                        	<div style="color: blue;vertical-align:middle;font-weight:bold;">已退保</div>
                        </c:when>
                       <c:otherwise>
                         	 已结案
                        </c:otherwise>
                    </c:choose>
                    </a>
				</td>
				<td title="${item.inquireRst}">${fn:substring(item.inquireRst, 0, 25)}</td>
				<td>${item.dealMan}</td>
				<td><fmt:formatDate value="${item.dealTime }" pattern="yyyy-MM-dd"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>