<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<style type="text/css">
table.dataintable {
	margin-top:15px;
	border-collapse:collapse;
	border:1px solid #aaa;
	width:200%;
	}

table.dataintable th {
	vertical-align:baseline;
	padding:5px 15px 5px 6px;
	background-color:#3F3F3F;
	border:1px solid #3F3F3F;
	text-align:center;
	color:#fff;
	}

table.dataintable td {
	vertical-align:text-top;
	padding:6px 15px 6px 6px;
	border:1px solid #aaa;
	text-align:center;
	}

table.dataintable tr:nth-child(odd) {
	background-color:#F5F5F5;
}

table.dataintable tr:nth-child(even) {
	background-color:#fff;
}
</style>
<form method="post" id="hfForm" action="${contextPath }/component/stastics/areaStat" onsubmit="return navTabSearch(this)">
<input hidden="hidden" name="year" value=${year }>
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
					<label>所属机构：</label>
					<input name="orgCode" id="uw_orgCode" type="hidden" value="${orgCode }"/>
					<input class="validate[required] required" name="name" id="uw_orgName" type="text" readonly="readonly" style="width: 120px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org?flag=prov" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
					<td><label>考核年月：</label>
					<form:select path="StasticsCity.mthYear" id="asy" class="combox">
						<form:options items="${yl }"/>
					</form:select>
					<form:select path="StasticsCity.mthMonth" id="asm" class="combox">
						<form:options items="${ml }"/>
					</form:select>
					</td>
					<td><label>标记：</label>
					<label><input type="radio" name="flag" id="ary1" value="0" ${flag eq "0"?"checked":"" }/>当月</label>
					<label><input type="radio" name="flag" id="ary2" value="1" ${flag eq "1"?"checked":"" }/>累计</label>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="submit">統計</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>
<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li class="line">line</li>
			<li><a class="icon" target="_blank" href="${contextPath }/component/stastics/areaStat/toXls?orgCode=${orgCode }&mth=${mthStr }&flag=${flag }&levelFlag=${levelFlag}&year=${year}"><span>导出统计结果</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="dataintable" layoutH="100" width="200%">
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
          <td rowspan="4">总分排名</td>
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
          <td>情况</td>
          <td>扣分</td>
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
          <td>0.7%</td>
          <td>30</td>
          <td>56</td>
          <td>6</td>
          <td>9</td>
          <td>60</td>
          <td>9</td>
          <td>100%</td>
          <td>6</td>
          <td>0</td>
          <td>5</td>
          <td>100%</td>
          <td>5</td>
          <td>23</td>
          <td>0.6%</td>
          <td>6</td>
          <td>10%</td>
          <td>2</td>
          <td>12</td>
          <td>0</td>
          <td>2</td>
          <td>100%</td>
          <td>7</td>
          <td>100%</td>
          <td>7</td>
          <td>9</td>
          <td>100</td>
        </tr>
    <c:forEach var="item" items="${cmRst}" varStatus="idx">
    <tr>
      <td>${item.organCode }</td>
      <td>${item.organName }</td>
      <td><c:choose><c:when test="${fn:contains(item.qyhglValue,'-')}">-</c:when><c:when test="${fn:contains(item.qyhglValue,'/')}">-</c:when><c:otherwise><fmt:formatNumber type="percent" minFractionDigits="2" value="${item.qyhglValue }" /></c:otherwise></c:choose></td>
      <td><fmt:formatNumber value="${item.qyhglScore}" pattern="#,###.##" /></td>
      <td><c:choose><c:when test="${fn:contains(item.kfhfcglValue,'-')}">-</c:when><c:when test="${fn:contains(item.kfhfcglValue,'/')}">-</c:when><c:otherwise><fmt:formatNumber type="percent" minFractionDigits="2" value="${item.kfhfcglValue }" /></c:otherwise></c:choose></td>
      <td><fmt:formatNumber value="${item.kfhfcglScore}" pattern="#,###.#" /></td>
      <td><c:choose><c:when test="${fn:contains(item.wtjValue,'-')}">-</c:when><c:when test="${fn:contains(item.wtjValue,'/')}">-</c:when><c:otherwise><fmt:formatNumber type="percent" minFractionDigits="2" value="${item.wtjValue }" /></c:otherwise></c:choose></td>
      <td><fmt:formatNumber value="${item.wtjScore}" pattern="#,###.#" /></td>
      <td><c:choose>
        <c:when test="${fn:contains(item.xq3ylostValue,'-')}">-</c:when>
        <c:when test="${fn:contains(item.xq3ylostValue,'/')}">-</c:when>
        <c:otherwise>
          <fmt:formatNumber type="percent" minFractionDigits="2" value="${item.xq3ylostValue }" />
        </c:otherwise>
      </c:choose></td>
      <td><fmt:formatNumber value="${item.xq3ylostScore}" pattern="#,###.##" /></td>
      <td><fmt:formatNumber value="${item.billvalueTotalScore}" pattern="#,###.#" /></td>
      <td><c:choose>
        <c:when test="${fn:contains(item.uwValue,'-')}">-</c:when>
        <c:when test="${fn:contains(item.uwValue,'/')}">-</c:when>
        <c:otherwise>
          <fmt:formatNumber pattern="#,###.##" value="${item.uwValue }" />
        </c:otherwise>
      </c:choose></td>
      <td><fmt:formatNumber value="${item.uwScore}" pattern="#,###.#" /></td>
      <td><c:choose>
        <c:when test="${fn:contains(item.lppayValue,'-')}">-</c:when>
        <c:when test="${fn:contains(item.lppayValue,'/')}">-</c:when>
        <c:otherwise>
          <fmt:formatNumber pattern="#,###.##" value="${item.lppayValue }" />
        </c:otherwise>
      </c:choose></td>
      <td><fmt:formatNumber value="${item.lppayScore}" pattern="#,###.#" /></td>
      <td><c:choose><c:when test="${fn:contains(item.xqissueValue,'-')}">-</c:when><c:when test="${fn:contains(item.xqissueValue,'/')}">-</c:when><c:otherwise><fmt:formatNumber type="percent" minFractionDigits="2" value="${item.xqissueValue }" /></c:otherwise></c:choose></td>
      <td><fmt:formatNumber value="${item.xqissueScore}" pattern="#,###.##" /></td>
      <td><c:choose>
        <c:when test="${fn:contains(item.issuetimeValue,'-')}">-</c:when>
        <c:when test="${fn:contains(item.issuetimeValue,'/')}">-</c:when>
        <c:otherwise>
          <fmt:formatNumber type="percent" minFractionDigits="2" value="${item.issuetimeValue }" />
        </c:otherwise>
      </c:choose></td>
      <td><fmt:formatNumber value="${item.issuetimeScore}" pattern="#,###.##" /></td>
      <td><c:choose><c:when test="${fn:contains(item.invalidValue,'-')}">-</c:when><c:when test="${fn:contains(item.invalidValue,'/')}">-</c:when><c:otherwise><fmt:formatNumber type="percent" minFractionDigits="2" value="${item.invalidValue }" /></c:otherwise></c:choose></td>
      <td><fmt:formatNumber value="${item.invalidScore}" pattern="#,###.#" /></td>
      <td><fmt:formatNumber value="${item.kfTotalScore}" pattern="#,###.#" /></td>
      <td><c:choose><c:when test="${fn:contains(item.inputValue,'-')}">-</c:when><c:when test="${fn:contains(item.inputValue,'/')}">-</c:when><c:otherwise><fmt:formatNumber type="percent" minFractionDigits="2" value="${item.inputValue }"/></c:otherwise></c:choose></td>
      <td><fmt:formatNumber value="${item.inputScore}" pattern="#,###.#" /></td>
      <td><c:choose><c:when test="${fn:contains(item.gwtjValue,'-')}">-</c:when><c:when test="${fn:contains(item.gwtjValue,'/')}">-</c:when><c:otherwise><fmt:formatNumber type="percent" minFractionDigits="2" value="${item.gwtjValue }"/></c:otherwise></c:choose></td>
      <td><fmt:formatNumber value="${item.gwtjScore}" pattern="#,###.#" /></td>
      <td><fmt:formatNumber value="${item.jobTotalScore}" pattern="#,###.#" /></td>
      <td><c:choose>
        <c:when test="${fn:contains(item.bqjyjkValue,'-')}">-</c:when>
        <c:when test="${fn:contains(item.bqjyjkValue,'/')}">-</c:when>
        <c:otherwise>
          <fmt:formatNumber type="percent" minFractionDigits="2" value="${item.bqjyjkValue }" />
        </c:otherwise>
      </c:choose></td>
      <td><fmt:formatNumber value="${item.bqjyjkScore}" pattern="#,###.#" /></td>
      <td><c:choose><c:when test="${fn:contains(item.truthValue,'-')}">-</c:when><c:when test="${fn:contains(item.truthValue,'/')}">-</c:when><c:otherwise><fmt:formatNumber type="percent" minFractionDigits="2" value="${item.truthValue }"/></c:otherwise></c:choose></td>
      <td><fmt:formatNumber value="${item.truthScore}" pattern="#,###.#" /></td>
      <td>${item.riskValue }</td>
      <td><fmt:formatNumber value="${item.riskScore}" pattern="#,###.#" /></td>
      <td><fmt:formatNumber value="${item.riskTotalScore}" pattern="#,###.#" /></td>
      <td><fmt:formatNumber value="${item.totalScore}" pattern="#,###.#" /></td>
      <td>${item.citySort }</td>
    </tr>
	</c:forEach>
	</table>
	</div>
</div>