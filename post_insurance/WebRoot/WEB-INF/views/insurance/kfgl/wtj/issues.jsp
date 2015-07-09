<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/kfgl/issues/print" page="${page }">
	<input type="hidden" name="search_LIKE_issueNo" value="${param.search_LIKE_issueNo }"/>
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="status" value="${param.status }"/>
	<input type="hidden" name="search_LTE_shouldDate" value="${param.search_LTE_shouldDate }"/>
	<input type="hidden" name="search_GTE_shouldDate" value="${param.search_GTE_shouldDate }"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/kfgl/issues/print" class="required-validate pageForm" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>工单编号：</label>
					<input type="text" name="search_LIKE_issueNo" value="${param.search_LIKE_issueNo }"/>
				</li>
				<li>
					<label>工单状态：</label>
					<form:select path="issue.status" class="combox">
						<form:option value=""> -- -- </form:option>
						<form:options items="${statusList }" itemLabel="desc" itemValue="desc"/>
					</form:select>
				</li>
				<li>
					<label>所属机构：</label>
					<input name="orgCode" type="hidden" value="${orgCode }"/>
					<input class="validate[required] required" name="name" type="text" readonly="readonly" style="width: 140px;" value="${orgName }"/>
					<a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查找带回</a>
				</li>				
			</ul>
			<ul class="searchContent">
				<li>
					<label>工单开始日期：</label>
					<input type="text" name="search_GTE_shouldDate" class="date validate[required] required" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_shouldDate }"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
				</li>
				<li>
					<label>工单结束日期：</label>
					<input type="text" name="search_LTE_shouldDate" class="date validate[required] required" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_shouldDate }"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
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
<div class="pageFormContent" layoutH="58">
<div id="IssuePrintContent">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="2" align="center">___<u>${fn:substring(param.search_LIKE_organization_orgCode, 0, 2)}</u>___中邮保险局问题件清单</td>
  </tr>
  <tr>
    <td colspan="2"><p>工单转办/下发意见：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 签名：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期：</p></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="gridtable">
      <tr>
        <td><p>工单转办/下发处理结果（由省分相关处理部门或机构填写）：<br />
        </p>
          <p>经办人签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期：</p></td>
        </tr>
      <tr>
        <td><p>处理意见：
            <br />
        </p>
          <p>负责人签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          日期</p></td>
      </tr>
      <tr>
        <td>处理结果（由地市、县在以下列表中逐件进行回复）</td>
      </tr>
      <tr>
        <td><p>处理意见（由地市、县负责人填写并签章确认）：<br />
        </p>
          <p>&nbsp;</p>
          <p> 负责人签字：
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          日期：</p></td>
      </tr>
    </table></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="gridtable">
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
        <th>处理日期</th>
      </tr>
      <c:forEach var="item" items="${issues}">
		<tr target="slt_uid" rel="${item.id}">
			<td>${item.organName}</td>
			<td>${item.issueNo}</td>
			<td>${item.policy.policyNo}</td>
			<td>${item.holder}</td>
			<td>${item.issueType}</td>
			<td>${item.issueContent}</td>
			<td>${item.bankName}</td>
			<td>${item.holderPhone}</td>
			<td>${item.holderMobile}</td>
			<td>${item.result}</td>
			<td>${item.dealMan}</td>
			<td>${item.dealTime}</td>
		</tr>
		</c:forEach>
    </table></td>
  </tr>
</table>
</div>
</div>
<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close" onclick="javascript:$.printBox('IssuePrintContent')">打印工单</button></div></div></li>
		</ul>
	</div>
	</div>