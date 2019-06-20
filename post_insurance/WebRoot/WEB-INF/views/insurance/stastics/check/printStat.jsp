<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script type="text/javascript">
<!--
function toTips(val) {
	if(val=="net") {
		alert("hi, 请选择具体地市县机构 ~~");
		return false;
	}
}
//-->
</script>
<script src="${contextPath}/js/echarts.min.js"></script>
<form method="post" id="hfForm" action="${contextPath }/component/stastics/policyPrint" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
					<label>所属机构：</label>
					<input name="orgCode" id="uw_orgCode" type="hidden" value="${orgCode }"/>
					<input class="validate[required] required" name="name" id="uw_orgName" type="text" readonly="readonly" style="width: 120px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
					<td>承保日期起：
					<input type="text" name="policyDate1" id="uwDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${policyDate1 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>承保日期止：
					<input type="text" name="policyDate2" id="uwDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${policyDate2 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
				</tr>
				<tr>
					<td><label>标记：</label>
					<form:select path="CheckModel.levelFlag" id="plevelflag" class="combox" onchange="javascript:toTips(this.value);">
						<form:option value="prov"> 省级 </form:option>
						<form:option value="city"> 市级 </form:option>
						<%-- <form:option value="net"> 网点 </form:option> --%>
					</form:select>
					</td>
					<td>&nbsp;
					</td>
					<td>
					&nbsp;
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
<h2 class="contentTitle"><label>统计结果</label>
<a class="buttonActive" target="_blank" href="${contextPath }/component/stastics/policyPrint/toXls?duration=${duration }&orgCode=${orgCode }&policyDate1=${policyDate1 }&policyDate2=${policyDate2 }&csDate1=${csDate1 }&csDate2=${csDate2 }&prdCode=${prdCode}&levelFlag=${levelFlag}&netFlag=${netFlag}&perm=${perm}&staffFlag=${staffFlag}&csFlag=${csFlag}&statType=${statType}&saleType=${saleType2}&status=${status}"><span>导出统计结果</span></a>
</h2>
<br>
<div class="pageContent" layoutH="130" width="150%">
<div class="row" style="padding: 0 3px;">
	<div class="sortDrag" style="width:30%;border:1px solid #e66;margin:5px;float:left;min-height:100px">
	<h2 class="contentTitle">列表展示（单位：件）注： &nbsp;&nbsp;&nbsp;&nbsp;</h2>
		<table class="table" width="100%">
		<thead>
			<tr>
				<th>序号</th>
				<th>名称</th>
				<th>承保件</th>
				<th>打印件数</th>
				<th>申请率</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${cmRst}" varStatus="idx">
			<tr>
				<td style="text-align: center;">${idx.index+1 }</td>
				<td>${item.orgName }</td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.policyCounts}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.errCounts}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${(item.errCounts/item.policyCounts)*100}" pattern="#,###.#" />%</td>
			</tr>
			</c:forEach>
			<tr>
				<td>&nbsp;</td>
				<td>合计：</td>
				<td style="text-align: right;"><fmt:formatNumber value="${countPt}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${sumPt}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${(sumPt/countPt)*100}" pattern="#,###.#" />%</td>
			</tr>
		</tbody>
	</table>
	</div>
	<div class="sortDrag" style="width:65%;border:1px solid #e66;margin:5px;float:left;min-height:10px">
	<div id="printtatMain" style="width: 800px;height:400px;"></div>
	    <script type="text/javascript">
	        // 基于准备好的dom，初始化echarts实例
	        var myChart = echarts.init(document.getElementById('printtatMain'));
			
	        option = {
	            tooltip: {
	                trigger: 'axis',
	                axisPointer: {
	                    type: 'cross',
	                    crossStyle: {
	                        color: '#999'
	                    }
	                }
	            },
	            toolbox: {
	                feature: {
	                    dataView: {show: true, readOnly: false},
	                    magicType: {show: true, type: ['line', 'bar']},
	                    restore: {show: true},
	                    saveAsImage: {show: true}
	                }
	            },
	            legend: {
	                data:['承保件','问题件','合格率']
	            },
	            xAxis: [
	                {
	                    type: 'category',
	                    data: [${col}],
	                    axisPointer: {
	                        type: 'shadow'
	                    }
	                }
	            ],
	            yAxis: [
	                {
	                    type: 'value',
	                    name: '数量',
	                    min: 0,
	                    max: ${maxTB},
	                    interval: 100,
	                    axisLabel: {
	                        formatter: '{value}'
	                    }
	                },
	                {
	                    type: 'value',
	                    name: '占比',
	                    min: 0,
	                    max: ${maxZB},
	                    interval: 10,
	                    axisLabel: {
	                        formatter: '{value} %'
	                    }
	                }
	            ],
	            series: [
	                {
	                    name:'承保件',
	                    type:'bar',
	                    data:[${countStr}]
	                },
	                {
	                    name:'差错件',
	                    type:'bar',
	                    data:[${sumStr}]
	                },
	                {
	                    name:'合格率',
	                    type:'line',
	                    yAxisIndex: 1,
	                    data:[${countPtStr}]
	                }
	            ]
	        };
	
	        // 使用刚指定的配置项和数据显示图表。
	        myChart.setOption(option);
	    </script>
	</div>
</div>
</div>