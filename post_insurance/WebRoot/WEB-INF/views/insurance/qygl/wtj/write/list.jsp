<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/qygl/issue/write/list" page="${page }">
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="name" value="${name }"/>
	<input type="hidden" name="search_LTE_policy.policyDate" value="${search_LTE_policy_policyDate }"/>
	<input type="hidden" name="search_GTE_policy.policyDate" value="${search_GTE_policy_policyDate }"/>
	<input type="hidden" name="search_LTE_replyTime" value="${param.search_LTE_replyTime }"/>
	<input type="hidden" name="search_GTE_replyTime" value="${param.search_GTE_replyTime }"/>
	<input type="hidden" name="fixStatus" value="${status }"/>
	<input type="hidden" name="checker" value="${checker }"/>
	<input type="hidden" name="keyInfo" value="${keyInfo }"/>
	<input type="hidden" name="search_GTE_checkBatch" value="${param.search_GTE_checkBatch }"/>
	<input type="hidden" name="search_LTE_checkBatch" value="${param.search_LTE_checkBatch }"/>
</dwz:paginationForm>

<form id="qyWriteForm" method="post" action="${contextPath }/qygl/issue/write/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
		<table class="searchContent">
				<tr>
					<td>
						<label>保单号：</label>
						<input type="text" id="qyWritePolicyNo" style="width: 100px;" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
					</td>
					<td>
						<label>批次号：</label>
						<input type="text" id="qyWriteGTEbatchNo" style="width: 100px;" name="search_GTE_checkBatch" value="${param.search_GTE_checkBatch }"/>
					</td>
					<td>
						<label>批次号：</label>
						<input type="text" id="qyWriteLTEbatchNo" style="width: 100px;" name="search_LTE_checkBatch" value="${param.search_LTE_checkBatch }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="issue.fixStatus" id="qy_w_status" class="combox">
						<form:option value=""> -- -- </form:option>
						<form:options items="${qyWriteStatusList }" itemLabel="desc"/>
					</form:select>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="orgCode" id="qy_w_orgCode" type="hidden" value="${orgCode }"/>
						<input class="validate[required] required" name="name" id="qy_w_orgName" type="text" readonly="readonly" style="width: 100px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
					<td>
					<label>排查方式:</label>
					<form:select path="issue.checker" id="qywchecker" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:option value="System">真实性排查</form:option>
							<form:option value="zhaoyong">档案整改</form:option>
						</form:select>
					</td>
				</tr>
				<tr>
					<td>
						<label>承保日期：</label>
						<input type="text" name="search_GTE_policy.policyDate" id="qy_w_date1" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${search_GTE_policy_policyDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>承保日期：</label>
						<input type="text" name="search_LTE_policy.policyDate" id="qy_w_date2" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${search_LTE_policy_policyDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>回复日期：</label>
						<input type="text" name="search_GTE_replyTime" id="qyw_d_date1" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_replyTime }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>回复日期：</label>
						<input type="text" name="search_LTE_replyTime" id="qyw_d_date2" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_replyTime }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
					<label>错误类型:</label>
					<form:select path="issue.keyInfo" id="cwki" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:option value="Email">Email错误</form:option>
							<form:option value="销售人员">使用销售人员电话</form:option>
							<form:option value="地址">地址不详</form:option>
							<form:option value="号码有误">电话号码有误</form:option>
							<form:option value="证件">证件号码有误</form:option>
							<form:option value="关系不符逻辑要求">关系不符逻辑要求</form:option>
							<form:option value="联系方式">联系方式为空</form:option>
							<form:option value="姓名有误">姓名有误</form:option>
						</form:select>
					</td>
					<td>&nbsp;</td>
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
			<shiro:hasPermission name="CheckWrite:view">
				<li><a iconClass="magnifier" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/qygl/issue/write/view/{slt_uid}"><span>处理详情</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="CheckWrite:edit">
				<li class="line">line</li>
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/qygl/issue/write/update/{slt_uid}"><span>回复</span></a></li>
				<li><a class="edit" target="selectedTodo" rel="ids" href="${contextPath }/qygl/issue/write/close" title="确认已完成整改?"><span>确认整改完毕</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="CheckWrite:provEdit">
				<li><a class="edit" target="ajaxTodo" href="${contextPath }/qygl/issue/write/appeal/{slt_uid}" title="确认并核实可申诉?"><span>申诉</span></a></li>
				<li><a class="edit" target="ajaxTodo" href="${contextPath }/qygl/issue/write/delete/{slt_uid}" title="确认要物理删除记录?"><span>删除</span></a></li>
			</shiro:hasPermission>
			<li><a class="icon" target="_blank" href="${contextPath }/qygl/issue/write/toXls?checker=${checker }&keyInfo=${keyInfo }&fixStatus=${status }&orgCode=${orgCode }&search_GTE_policy.policyDate=${search_GTE_policy_policyDate}&search_LTE_policy.policyDate=${search_LTE_policy_policyDate}&search_GTE_replyTime=${param.search_GTE_replyTime}&search_LTE_replyTime=${param.search_LTE_replyTime}&search_GTE_checkBatch=${param.search_GTE_checkBatch}&search_LTE_checkBatch=${param.search_LTE_checkBatch}"><span>导出Excel</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" target="dialog" href="${contextPath }/qygl/help" mask="true" width="530" height="430"><span>功能说明</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>批次号</th>		
				<th orderField=policy.organization.name class="${page.orderField eq 'policy.organization.name' ? page.orderDirection : ''}">所属机构</th>
				<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">所属保单号</th>
				<th orderField=policy.formNo class="${page.orderField eq 'policy.formNo' ? page.orderDirection : ''}">投保单号</th>
				<th orderField=policy.holder class="${page.orderField eq 'policy.holder' ? page.orderDirection : ''}">投保人</th>
				<th orderField=policy.policyDate class="${page.orderField eq 'policy.policyDate' ? page.orderDirection : ''}">承保日期</th>
				<th orderField=fixStatus class="${page.orderField eq 'fixStatus' ? page.orderDirection : ''}">问题件状态</th>
				<th>耗时</th>
				<th orderField=docMiss class="${page.orderField eq 'docMiss' ? page.orderDirection : ''}">资料缺失</th>
				<th orderField=keyInfo class="${page.orderField eq 'keyInfo' ? page.orderDirection : ''}">关键信息</th>
				<th orderField=importanceInfo class="${page.orderField eq 'importanceInfo' ? page.orderDirection : ''}">重要信息</th>
				<th orderField=elseInfo class="${page.orderField eq 'elseInfo' ? page.orderDirection : ''}">其他信息</th>
				<th orderField=netName class="${page.orderField eq 'netName' ? page.orderDirection : ''}">网点名称</th>
				<th orderField=checker class="${page.orderField eq 'checker' ? page.orderDirection : ''}">档案下发</th>
				<th orderField=isTruth class="${page.orderField eq 'isTruth' ? page.orderDirection : ''}">客真</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.checkBatch}</td>
				<td>${item.policy.organization.shortName}</td>
				<td><a target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/client/view/byPolicyNo/${item.policy.policyNo}"><div style="color: blue;vertical-align:middle;font-weight:normal;">${item.policy.policyNo}</div></a></td>
				<td>${item.policy.formNo}</td>
				<td>${item.policy.holder}</td>
				<td>${item.policy.policyDate}</td>
				<td>
				<c:choose>
					<c:when test="${item.fixStatus eq 'NewStatus'}">
						<div style="color: red;vertical-align:middle;font-weight:normal;">待处理</div>
					</c:when>
					<c:when test="${item.fixStatus eq 'FollowStatus'}">
						跟进中
					</c:when>
					<c:when test="${item.fixStatus eq 'IngStatus'}">
						待审核
					</c:when>
					<c:when test="${item.fixStatus eq 'CTStatus'}">
						已退保
					</c:when>
					<c:when test="${item.fixStatus eq 'CloseStatus'}">
						已整改
					</c:when>
					<c:otherwise>
						${item.fixStatus}
					</c:otherwise>
				</c:choose>
				</td>
				<td>${item.timeConsuming}</td>
				<td>${item.docMiss == "null"?"":item.docMiss}</td>
				<td>${item.keyInfo=="null"?"":item.keyInfo}</td>
				<td>${item.importanceInfo=="null"?"":item.importanceInfo}</td>
				<td>${item.elseInfo=="null"?"":item.elseInfo}</td>
				<td>
				<c:choose>  
					    <c:when test="${!empty item.netName && fn:length(item.netName) > 14}">  
					        <c:out value="${fn:substring(item.netName, 14, 30)}" />  
					    </c:when> 
					    <c:when test="${!empty item.netName && fn:length(item.netName) > 0}">  
					        <c:out value="${item.netName}" />  
					    </c:when> 
					   <c:otherwise>  
					      <c:out value="${item.policy.bankName}" />  
					    </c:otherwise>  
					</c:choose>
				</td>
				<td>
				<c:choose>  
					    <c:when test="${item.payMethod eq '期交' or item.payMethod eq '趸交'}">  
					        &nbsp;
					    </c:when>
					    <c:otherwise>  
					      <c:out value="${item.payMethod}" />  
					    </c:otherwise>  
					</c:choose>
				</td>
				<td>${item.isTruth}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>