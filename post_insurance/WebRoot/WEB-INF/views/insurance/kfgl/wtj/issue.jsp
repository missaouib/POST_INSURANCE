<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<style type="text/css">
#MsgContent {
	position: absolute;
	left: 0;
	top: 3px;
	color: #000;
	font-family: "微软雅黑";
	white-space: nowrap;
	font-size: 14px;
	table-layout: fixed;
}
table.gridtable {
font-family:  "微软雅黑";
 font-size:14px;
 color:#333333;
 border-width: 1px;
 border-color: #666666;
 border-collapse: collapse;
}
table.gridtable th {
	border-width: 1px;
	padding: 2px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}
table.gridtable td {
	border-width: 1px;
	padding: 2px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}
</style>
<div id="MsgContent" style="width:756px;height:1086px;border:0px solid #000000;">
<div>
  <p>&nbsp;&nbsp;${fn:substring(issue.orangization.name, 0, 2)}-${issue.issueType }-${issue.issueNo }</p>
  <div align="center" style="font-size:16px">中邮人寿呼入/回访问题工单转办单 </div>
  <div>&nbsp;&nbsp;工单流水号：${issue.issueNo } </div>
  </div>
  <table width="90%" align="center" class="gridtable">
    <tr >
      <td >承办机构： </td>
      <td >${issue.policy.orangization.name } </td>
      <td >保险单号码： </td>
      <td >${issue.policy.policyNo } </td>
    </tr>
    <tr >
      <td >销售网点： </td>
      <td >${issue.bankName } </td>
      <td >经办人： </td>
      <td >&nbsp;</td>
    </tr>
    <tr >
      <td >承保日期： </td>
      <td >${issue.policy.policyDate } </td>
      <td >承保金额： </td>
      <td >${issue.policy.policyFee } </td>
    </tr>
    <tr >
      <td >投保人姓名： </td>
      <td >${issue.policy.holder }</td>
      <td >联系电话： </td>
      <td >${issue.holderPhone }</td>
    </tr>
    <tr >
      <td >手机号码： </td>
      <td >${issue.holderMobile } </td>
      <td >工单类型： </td>
      <td >问题件 </td>
    </tr>
    <tr >
      <td >工单子类型： </td>
      <td >${issue.issueType } </td>
      <td >要求时效： </td>
      <td >${issue.readyDate+5 } </td>
    </tr>
  </table><br><br>
  <div>&nbsp;&nbsp;问题说明：（由省分客服人员填写）：</div> 
  <table width="90%" align="center" class="gridtable">
    <tr >
      <td >工单内容：${issue.issueContent } </td>
    </tr>
    <tr >
      <td >工单转办/下发意见：转营运部契约岗处理 <br><br><br>
        签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期： </td>
    </tr>
  </table><br><br>
  <div>&nbsp;&nbsp;处理结果：（由省分处理部门或机构填写）： </div>
  <table width="90%" align="center" class="gridtable">
    <tr >
      <td >处理结果： <br><br><br>
        经办人签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期： </td>
    </tr>
    <tr >
      <td >处理意见：<br><br><br> 
        负责人签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期： </td>
    </tr>
  </table><br><br>
  <div>&nbsp;&nbsp;处理结果：（由市县处理部门或机构填写）： </div>
  <table width="90%" align="center" class="gridtable">
    <tr >
      <td >处理结果： 
        ${issue.result } 
        <br><br><br>经办人签字：&nbsp;         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 日期： </td>
    </tr>
    <tr >
      <td >处理意见： 
        <br><br><br>负责人签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期： </td>
    </tr>
  </table>
  <table width="90%" align="center" class="gridtable">
    <tr >
      <td valign="top" >备注<br><br> </td>
    </tr>
  </table>
</div>
