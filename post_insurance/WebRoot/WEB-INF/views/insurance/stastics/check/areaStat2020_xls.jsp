<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=area_stat_report.xls");
%>
<table border="1" cellspacing="1" cellpadding="0">
    <tr>
          <td>考核月份：</td>
          <td align="right">${mth }</td>
          <td colspan="9">保单品质（56分）</td>
          <td colspan="11">客户服务（23分）</td>
          <td colspan="5">作业质量（12分）</td>
          <td colspan="7">风险管控（9分）</td>
          <td colspan="2">总体情况</td>
        </tr>
        <tr>
          <td colspan="2">指标</td>
          <td colspan="2">新契约综合合格率</td>
          <td colspan="2">犹豫期内电话回访成功率</td>
          <td colspan="2">销售类问题件占比</td>
          <td colspan="2">3年期交保费综合丢失率</td>
          <td>保单品质维度得分</td>
          <td colspan="2">人核件全流程时效</td>
          <td colspan="2">出险支付时效</td>
          <td colspan="2">续期业务系统服务工单处理率</td>
          <td colspan="2">问题件咨询件处理时效</td>
          <td colspan="2">失效保单件数清理率</td>
          <td>客户服务维度得分</td>
          <td colspan="2">保全录入修改率</td>
          <td colspan="2">团险契约保全业务扫描件退回率</td>
          <td>作业质量维度得分</td>
          <td colspan="2">质押借款逾期件数</td>
          <td colspan="2">客户信息真实性综合合格率</td>
          <td colspan="2">风险事件</td>
          <td>风险管控维度得分</td>
          <td>总分</td>
          <td rowspan="3">总分排名</td>
        </tr>
        <tr>
          <td colspan="2">得分区间</td>
          <td colspan="2">[0,5]</td>
          <td colspan="2">[0,10]</td>
          <td colspan="2">[0,11]</td>
          <td colspan="2">[0,40]</td>
          <td>[0,76]</td>
          <td colspan="2">[0,11]</td>
          <td colspan="2">[0,14]</td>
          <td colspan="2">[0,6]</td>
          <td colspan="2">[0,8]</td>
          <td colspan="2">[0,5]</td>
          <td>[0,30]</td>
          <td colspan="2">[0,8]</td>
          <td colspan="2">[0,2]</td>
          <td>[0,16]</td>
          <td colspan="2">[0,2]</td>
          <td colspan="2">[0,7]</td>
          <td colspan="2">&nbsp;</td>
          <td>[0,9]</td>
          <td>[0,131]</td>
        </tr>
        <tr>
          <td>机构号</td>
          <td>机构名称</td>
          <td>基准值</td>
          <td>基准分</td>
          <td>基准值</td>
          <td>基准分</td>
          <td>基准值</td>
          <td>基准分</td>
          <td>基准值</td>
          <td>基准分</td>
          <td>合计</td>
          <td>基准值</td>
          <td>基准分</td>
          <td>基准值</td>
          <td>基准分</td>
          <td>基准值</td>
          <td>基准分</td>
          <td>基准值</td>
          <td>基准分</td>
          <td>基准值</td>
          <td>基准分</td>
          <td>合计</td>
          <td>基准值</td>
          <td>基准分</td>
          <td>基准值</td>
          <td>基准分</td>
          <td>合计</td>
          <td>基准值</td>
          <td>基准分</td>
          <td>基准值</td>
          <td>基准分</td>
          <td>基准值</td>
          <td>基准分</td>
          <td>合计</td>
          <td>合计</td>
        </tr>
        
    <c:forEach var="item" items="${cmRst}" varStatus="idx">
    <tr>
      <td>${item.organCode }</td>
      <td>${item.organName }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.qyhglValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.qyhglScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.kfhfcglValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.kfhfcglScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.wtjValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.wtjScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.xq3ylostValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.xq3ylostScore }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.billvalueTotalScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00">${item.uwValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.uwScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00">${item.lppayValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.lppayScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.xqissueValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.xqissueScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.issuetimeValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.issuetimeScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.invalidValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.invalidScore }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.kfTotalScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.inputValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.inputScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.gwtjValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.gwtjScore }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.jobTotalScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.bqjyjkValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.bqjyjkScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.truthValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.truthScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.riskValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.riskScore }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.riskTotalScore }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.totalScore }</td>
      <td>${item.citySort }</td>
    </tr>
	</c:forEach>
	</table>