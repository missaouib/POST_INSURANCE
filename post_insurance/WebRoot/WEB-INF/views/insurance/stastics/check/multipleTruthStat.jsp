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
<form method="post" id="hfForm" action="${contextPath }/component/stastics/multipleTruth" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
					<label>所属机构：</label>
					<input name="orgCode" id="uw_orgCode" type="hidden" value="${orgCode }"/>
					<input class="validate[required] required" name="name" id="uw_orgName" type="text" readonly="readonly" style="width: 120px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org?flag=prov" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
					<td>承保日期起：
					<input type="text" name="policyDate1" id="uwDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${policyDate1 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>承保日期止：
					<input type="text" name="policyDate2" id="uwDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${policyDate2 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
					<label>银邮：</label>
					<form:select path="CheckModel.netFlag" id="tnetflag" class="combox">
						<form:option value="">  --  </form:option>
						<form:option value="1"> 邮政代理 </form:option>
						<form:option value="2"> 银行自营 </form:option>
					</form:select>
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
					<td>
					<label>长期险：</label>
						<form:select path="CheckModel.duration" id="truthdurationFlag" class="combox">
							<form:option value="0">全部</form:option>
							<form:option value="10">长期险</form:option>
						</form:select>
					</td>
					<td>
					<label>趸/期缴：</label>
					<form:select path="CheckModel.perm" id="pperm" class="combox">
						<form:option value="">  --  </form:option>
						<form:option value="1"> 年交 </form:option>
						<form:option value="0"> 趸交 </form:option>
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
<h2 class="contentTitle"><label>统计结果</label>
<a class="buttonActive" target="_blank" href="${contextPath }/component/stastics/truth/multipletoXls?netFlag=${netFlag }&duration=${duration }&orgCode=${orgCode }&policyDate1=${policyDate1 }&policyDate2=${policyDate2 }&levelFlag=${levelFlag}&duration=${duration}&perm=${perm}"><span>导出统计结果</span></a>
&nbsp;&nbsp;&nbsp;&nbsp;
</h2>
<br>
<div class="pageContent" layoutH="170" width="150%">
<div class="row" style="padding: 0 3px;">
	<div style="width:30%;border:1px solid #e66;margin:5px;float:left;min-height:100px">
	<h2 class="contentTitle">列表展示（单位：件）注： &nbsp;&nbsp;&nbsp;&nbsp;</h2>
		<table class="table" width="100%">
		<thead>
			<tr>
				<th>名称</th>
				<th>保单件数</th>
				<th>差错件数</th>
				<th>整改件数</th>
				<th>及时件数</th>
				<th title="合格率50%及时30%整改20%">综合合格率</th>
				<th title="合格率60%整改40%">条线合格率</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${cmRst}" varStatus="idx">
			<tr>
				<td>${item.organCode }</td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.policyCounts}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.checkCounts}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.errCounts}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.ontimeCounts}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.ratio}" pattern="#,###.##" />%</td>
				<td style="text-align: right;"><fmt:formatNumber value="${((1-(item.policyCounts==0?0:item.checkCounts/item.policyCounts))*0.6+(item.checkCounts==0?0.4:(item.errCounts/item.checkCounts*0.4)))*100}" pattern="#,###.##" />%</td>
			</tr>
			</c:forEach>
			<tr>
				<td>合计：</td>
				<td style="text-align: right;"><fmt:formatNumber value="${countSum}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${checkSum}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${errSum}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${ontimeSum}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${finalRatio}" pattern="#,###.##" />%</td>
				<td>&nbsp;</td>
			</tr>
		</tbody>
	</table>
	</div>
	<div style="width:65%;border:1px solid #e66;margin:5px;float:left">
	<div id="multipletruthtatMain" style="width: 900px;height:520px;"></div>
	    <script type="text/javascript">
	        // 基于准备好的dom，初始化echarts实例
	        var myChart = echarts.init(document.getElementById('multipletruthtatMain'));
			
	        option = {
				title: {
	                text: '图示'
	            },
	            tooltip: {},
	            toolbox: {
	                feature: {
	                    dataView: {show: true, readOnly: false},
	                    magicType: {show: true, type: ['line', 'bar']},
	                    restore: {show: true},
	                    saveAsImage: {show: true}
	                }
	            },
			    legend: {
			        data: ['出单量', '问题量', '整改量', '及时整改量','综合合格率']
			    },
			    xAxis: {
			        type: 'category',
			        boundaryGap: false,
			        data: [${cityCol}]
			    },
			    yAxis: [
			        {
			            type: 'value',
			            name: '总件数',
			            min: 100,
			            max: ${maxTB},
			            interval: 300,
			            axisLabel: {
			                formatter: '{value}'
			            }
			        },
			        {
			            type: 'value',
			            name: '综合合格率',
			            min: 0,
			            max: ${maxZB},
			            interval: 10,
			            axisLabel: {
			                formatter: '{value}'
			            }
			        }
			    ],
			    series: [
			        {
			            name: '出单量',
			            yAxisIndex: 0,
			            type: 'line',
			            data: [${countStr}]
			        },
			        {
			            name: '问题量',
			            yAxisIndex: 1,
			            type: 'line',
			            data: [${checkStr}]
			        },
			        {
			            name: '整改量',
			            yAxisIndex: 1,
			            type: 'line',
			            stack: '总量',
			            data: [${errSumStr}]
			        },
			        {
			            name: '及时整改量',
			            yAxisIndex: 1,
			            type: 'line',
			            data: [${ontimeStr}]
			        },
			        {
			            name: '综合合格率',
			            yAxisIndex: 1,
			            type: 'line',
			            data: [${ratioStr}]
			        }
			    ]
			};
	
	        // 使用刚指定的配置项和数据显示图表。
	        myChart.setOption(option);
	    </script>
	</div>
</div>
</div>