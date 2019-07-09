<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=area_stat_report.xls");
%>
<table border="1" cellspacing="1" cellpadding="0">
    <tr>
          <td>考核月份：</td>
          <td align="right">${mth }</td>
          <td colspan="13">保单品质（56分）</td>
          <td colspan="7">客户服务（23分）</td>
          <td colspan="7">作业质量（12分）</td>
          <td colspan="5">风险管控（9分）</td>
          <td colspan="2">总体情况</td>
        </tr>
        <tr>
          <td colspan="2">指标</td>
          <td colspan="2">新契约综合合格率</td>
          <td colspan="2">犹豫期内电话回访成功率</td>
          <td colspan="2">问题件占比</td>
          <td colspan="2">13个月保费继续率</td>
          <td colspan="2">25个月保费继续率</td>
          <td colspan="2">期交新单当年退保率</td>
          <td>保单品质维度得分</td>
          <td colspan="2">人核件全流程时效</td>
          <td colspan="2">出险支付时效</td>
          <td colspan="2">失效保单件数清理率</td>
          <td>客户服务维度得分</td>
          <td colspan="2">保全录入修改率</td>
          <td colspan="2">团险契约保全业务扫描件退回率</td>
          <td colspan="2">简易险理赔资料准确率</td>
          <td>作业质量维度得分</td>
          <td colspan="2">质押借款逾期件数</td>
          <td colspan="2">客户信息真实性合格率</td>
          <td>风险管控维度得分</td>
          <td>总分</td>
          <td rowspan="4">总分排名</td>
        </tr>
        <tr>
          <td colspan="2">得分区间</td>
          <td colspan="2">[0,5]</td>
          <td colspan="2">[0,10]</td>
          <td colspan="2">[0,11]</td>
          <td colspan="2">[0,29]</td>
          <td colspan="2">[0,13]</td>
          <td colspan="2">[0,8]</td>
          <td>[0,76]</td>
          <td colspan="2">[0,11]</td>
          <td colspan="2">[0,14]</td>
          <td colspan="2">[0,5]</td>
          <td>[0,30]</td>
          <td colspan="2">[0,8]</td>
          <td colspan="2">[0,2]</td>
          <td colspan="2">[0,6]</td>
          <td>[0,16]</td>
          <td colspan="2">[0,2]</td>
          <td colspan="2">[0,7]</td>
          <td>[0,9]</td>
          <td>[0,131]</td>
        </tr>
        <tr>
          <td rowspan="2">机构号</td>
          <td rowspan="2">机构名称</td>
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
          <td>合计</td>
        </tr>
        <tr>
          <td>98%</td>
          <td>4</td>
          <td>98%</td>
          <td>8</td>
          <td>3%</td>
          <td>8</td>
          <td>95.5%</td>
          <td>20</td>
          <td>98%</td>
          <td>10</td>
          <td>0.2%</td>
          <td>6</td>
          <td>56</td>
          <td>6</td>
          <td>9</td>
          <td>60</td>
          <td>9</td>
          <td>100%</td>
          <td>5</td>
          <td>23</td>
          <td>0.6%</td>
          <td>6</td>
          <td>10%</td>
          <td>2</td>
          <td>90%</td>
          <td>4</td>
          <td>12</td>
          <td>0</td>
          <td>2</td>
          <td>100%</td>
          <td>7</td>
          <td>9</td>
          <td>100</td>
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
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.xq13jValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.xq13jScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.xq25jValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.xq25jScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.qjxdtblValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.qjxdtblScore }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.billvalueTotalScore }</td>
      <td>${item.uwValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.uwScore }</td>
      <td>${item.lppayValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.lppayScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.invalidValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.invalidScore }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.kfTotalScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.inputValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.inputScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.gwtjValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.gwtjScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.lpjyxValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.lpjyxScore }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.jobTotalScore }</td>
      <td>${item.bqjyjkValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.bqjyjkScore }</td>
      <td style="vnd.ms-excel.numberformat: #0.00%">${item.truthValue }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.truthScore }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.riskTotalScore }</td>
      <td style="vnd.ms-excel.numberformat:#,##0.00">${item.totalScore }</td>
      <td>${item.citySort }</td>
    </tr>
	</c:forEach>
	</table>