<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/kfgl/inquires/print" page="${page }">
	<input type="hidden" name="search_LIKE_inquireNo" value="${param.search_LIKE_inquireNo }"/>
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="name" value="${name }"/>
	<input type="hidden" name="inquireStatus" value="${param.inquireStatus }"/>
	<input type="hidden" name="search_LTE_operateTime" value="${param.search_LTE_operateTime }"/>
	<input type="hidden" name="search_GTE_operateTime" value="${param.search_GTE_operateTime }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/kfgl/inquires/print" class="required-validate pageForm" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						工单号：<input type="text" id="kfPolicyNo" name="search_LIKE_inquireNo" style="width: 100px;" value="${param.search_LIKE_inquireNo }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="inquire.inquireStatus" id="kfiStatus" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:options items="${statusList }" itemLabel="desc"/>
						</form:select>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="orgCode" id="bq_orgCode" type="hidden" value="${orgCode }"/>
						<input class="validate[required] required" name="name" id="bq_orgName" type="text" readonly="readonly" style="width: 100px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
				</tr>
				<tr>
					<td>
						保单号：<input type="text" id="kfPolicyNo" style="width: 100px;" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
					</td>
					<td>
						<label>开始日期：</label>
						<input type="text" name="search_GTE_operateTime" id="kfDate1" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_operateTime }"/>
						<a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>结束日期：</label>
						<input type="text" name="search_LTE_operateTime" id="kfDate2" class="date" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_operateTime }"/>
					<a class="inputDateButton" href="javascript:;">选</a>
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
			<li><a class="icon" href="javascript:$.printBox('InquireListPrintContent')"><span>批打工单列表</span></a></li>
			<li class="line">line</li>
				<li><a class="icon" target="_blank" href="${contextPath }/kfgl/inquiresToXls?search_LIKE_inquireNo=${param.search_LIKE_inquireNo }&orgCode=${orgCode }&search_LTE_operateTime=${param.search_LTE_operateTime }&search_GTE_operateTime=${param.search_GTE_operateTime }&search_LIKE_policy.policyNo=${search_LIKE_policy_policyNo }&inquireStatus=${inquireStatus }"><span>导出Excel</span></a></li>
		</ul>
	</div>
<div id="InquireListPrintContent">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" layoutH="140">
  <tr>
    <td align="center"><p>___<u>${fn:substring(orgName, 0, 2)}</u>___中邮保险局问题件清单</p></td>
  </tr>
  <tr>
    <td>工单转办/下发意见：
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
签名：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期：</td>
  </tr>
  <tr>
    <td><table width="100%" class="gridtable">
      <tr>
        <td>工单转办/下发处理结果（由省分相关处理部门或机构填写）：<br />
        <br>
        经办人签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期：</td>
        </tr>
      <tr>
        <td>处理意见：
            <br />
        <br>
          负责人签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          日期</td>
      </tr>
      <tr>
        <td>处理结果（由地市、县在以下列表中逐件进行回复）</td>
      </tr>
      <tr>
        <td>处理意见（由地市、县负责人填写并签章确认）：<br />
          <br>
          负责人签字：
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          日期：</td>
      </tr>
    </table>
    </td>
  </tr>
  <tr>
    <td><table width="100%" class="gridtable">
    <thead>
      <tr>
        <th>机构名称</th>
        <th>工单流水号</th>
        <th>保单号</th>
        <th>客户姓名</th>
        <th>工单子类</th>
        <th>工单内容</th>
        <th>银行网点名称</th>
        <th>联系电话</th>
        <th>手机号码</th>
        <th>处理结果</th>
        <th>经办人员</th>
        <th>复核人员</th>
        <th>处理日期</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="item" items="${inquires}">
		<tr>
			<td>${item.organName}</td>
			<td>${item.inquireNo}</td>
			<td>${item.policyNos}</td>
			<td>${item.client}</td>
			<td>${item.inquireSubtype}</td>
			<td>${item.inquireDesc}</td>
			<td>
			<c:choose>  
			    <c:when test="${fn:length(item.netName) > 14}">  
			        <c:out value="${fn:substring(item.netName, 14, 30)}" />  
			    </c:when>  
			   <c:otherwise>  
			      <c:out value="${item.policy.bankName}" />  
			    </c:otherwise>  
			</c:choose>
			</td>
			<td>${item.clientPhone1}</td>
			<td>${item.clientPhone2}</td>
			<td>${item.inquireRst}</td>
			<td>${item.dealMan}</td>
			<td>${item.checker}</td>
			<td>${item.dealTime}</td>
		</tr>
		</c:forEach>
		</tbody>
    </table></td>
  </tr>
</table>
</div>
</div>
