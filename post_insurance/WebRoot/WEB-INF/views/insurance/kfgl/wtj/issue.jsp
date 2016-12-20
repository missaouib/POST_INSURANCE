<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<div class="panelBar">
		<ul class="toolBar">
			<li><a class="icon" href="javascript:$.printBox('IssuePrintContent')"><span>工单打印</span></a></li>
		</ul>
	</div>
<div class="pageFormContent" layoutH="78">
<div id="IssuePrintContent">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>&nbsp;&nbsp;${fn:substring(issue.organization.name, 0, 2)}-${issue.issueType }-${issue.issueNo }</td>
    </tr>
  <tr>
    <td align="center"><p><span style="font-size:16px">中邮人寿呼入/回访问题工单转办单 </span></p></td>
    </tr>
  <tr>
    <td>&nbsp;&nbsp;工单流水号：${issue.issueNo } </td>
    </tr>
  <tr>
    <td><table width="100%" align="center" class="gridtable">
      <tr>
        <td>承办机构： </td>
        <td>${issue.policy.organization.name } </td>
        <td>保险单号码： </td>
        <td>${issue.policy.policyNo } </td>
      </tr>
      <tr>
        <td>销售网点： </td>
        <td>${issue.bankName } </td>
        <td>经办人： </td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>承保日期： </td>
        <td>${issue.policy.policyDate } </td>
        <td>承保金额： </td>
        <td>${issue.policy.policyFee } </td>
      </tr>
      <tr>
        <td>投保人姓名： </td>
        <td>${issue.policy.holder }</td>
        <td>联系电话： </td>
        <td>${issue.holderPhone }</td>
      </tr>
      <tr>
        <td>手机号码： </td>
        <td>${issue.holderMobile } </td>
        <td>工单类型： </td>
        <td>问题件 </td>
      </tr>
      <tr>
        <td>工单子类型： </td>
        <td>${issue.issueType } </td>
        <td>要求时效： </td>
        <td><fmt:formatDate value="${issue.finishDate}" pattern="yyyy-MM-dd"/></td>
      </tr>
    </table></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    </tr>
  <tr>
    <td>&nbsp;&nbsp;问题说明：（由省分客服人员填写）：</td>
    </tr>
  <tr>
    <td><table width="100%" align="center" class="gridtable">
      <tr>
        <td>工单内容：${issue.issueContent } </td>
      </tr>
      <tr>
        <td>工单转办/下发意见：
         <c:choose>  
		    <c:when test="${fn:contains(issue.issueType,'退保')}">  
		        转业务岗处理
		    </c:when>
		    <c:when test="${fn:contains(issue.issueType,'条款解释不清')}">  
		        转业务岗处理
		    </c:when>
		    <c:when test="${fn:contains(issue.issueType,'保单未送达')}">  
		        转契约岗处理
		    </c:when> 
		    <c:when test="${fn:contains(issue.issueType,'代签名')}">  
		        转契约岗处理
		    </c:when> 
		   <c:otherwise>  
		      &nbsp;
		    </c:otherwise>  
		</c:choose>
          <br />
          <br />
          <br />
          签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          日期： </td>
      </tr>
    </table></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    </tr>
  <tr>
    <td>&nbsp;&nbsp;处理结果：（由省分处理部门或机构填写）： </td>
    </tr>
  <tr>
    <td><table width="100%" align="center" class="gridtable">
      <tr>
        <td>处理结果： <br />
          <br />
          <br />
          经办人签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          日期： </td>
      </tr>
      <tr>
        <td>处理意见：<br />
          <br />
          <br />
          <br />
          负责人签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          日期： </td>
      </tr>
    </table></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    </tr>
  <tr>
    <td>&nbsp;&nbsp;处理结果：（由市县处理部门或机构填写）： </td>
    </tr>
  <tr>
    <td><table width="100%" align="center" class="gridtable">
      <tr>
        <td>处理结果： 
          ${issue.result } <br />
          <br />
          <br />
          <br />
          经办人签字：${issue.dealMan }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          日期：${issue.dealTime } </td>
      </tr>
      <tr>
        <td>处理意见： <br />
          <br />
          <br />
          <br />
          负责人签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          日期： </td>
      </tr>
    </table></td>
    </tr>
  <tr>
    <td><table width="100%" align="center" class="gridtable">
      <tr>
        <td>备注<br />
          <br /></td>
      </tr>
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