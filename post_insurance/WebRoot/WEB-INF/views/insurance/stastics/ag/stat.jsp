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
<form method="post" id="hfForm" action="${contextPath }/component/stastics/csag" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
					<label>所属机构：</label>
					<input name="orgCode" id="uw_orgCode" type="hidden" value="${orgCode }"/>
					<input class="validate[required] required" name="name" id="uw_orgName" type="text" readonly="readonly" style="width: 120px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
					<td><label>承保日期起：</label>
					<input type="text" name="policyDate1" id="uwDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${policyDate1 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td><label>承保日期止：</label>
					<input type="text" name="policyDate2" id="uwDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${policyDate2 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td><label>产品：</label>
					<form:select path="TuiBaoModel.prdCode" id="prdCode" class="combox">
						<form:option value=""> -- </form:option>
						<form:options items="${prds }" itemLabel="prdName" itemValue="prdCode"/>
					</form:select>
					</td>
					<td><label>趸/期缴：</label>
					<form:select path="TuiBaoModel.perm" id="tbperm" class="combox">
						<form:option value="">  --  </form:option>
						<form:option value="1"> 年交 </form:option>
						<form:option value="0"> 趸交 </form:option>
					</form:select>
					</td>
				</tr>
				<tr>
					<td><label>标记：</label>
					<form:select path="TuiBaoModel.levelFlag" id="tbflag" class="combox" onchange="javascript:toTips(this.value);">
						<form:option value="prov"> 省级 </form:option>
						<form:option value="city"> 市级 </form:option>
						<form:option value="net"> 网点 </form:option>
					</form:select>
					</td>
					<td><label>领取日期起：</label>
					<input type="text" name="csDate1" id="uwDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${csDate1 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
					<label>领取日期止：</label>
					<input type="text" name="csDate2" id="uwDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${csDate2 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td><label>银邮：</label>
					<form:select path="TuiBaoModel.netFlag" id="netflag" class="combox">
						<form:option value="">  --  </form:option>
						<form:option value="1"> 邮政代理 </form:option>
						<form:option value="2"> 银行自营 </form:option>
					</form:select>
					</td>
					<td>
					<label>员工单标记：</label>
					<form:select path="TuiBaoModel.staffFlag" id="tbstaffflag" class="combox">
						<form:option value="">  --  </form:option>
						<form:option value="0"> 普通客户 </form:option>
						<form:option value="1"> 员工单 </form:option>
					</form:select>
					</td>
				</tr>
				<tr>
				<td><label>满期日期起：</label>
				<input type="text" name="policyEndDate1" id="peDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${policyEndDate1 }"/><a class="inputDateButton" href="javascript:;">选</a>
				</td>
				<td><label>满期日期止：</label>
				<input type="text" name="policyEndDate2" id="peDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${policyEndDate2 }"/><a class="inputDateButton" href="javascript:;">选</a>
				</td>
				<td>
					<label>长期险：</label>
						<form:select path="TuiBaoModel.duration" id="tbdurationFlag" class="combox">
							<form:option value="0">全部</form:option>
							<form:option value="10">长期险</form:option>
						</form:select>
				</td>
				<td>
				&nbsp;
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
<label><a class="buttonActive" target="_blank" href="${contextPath }/component/stastics/csag/toXls?duration=${duration }&orgCode=${orgCode }&policyDate1=${policyDate1 }&policyDate2=${policyDate2 }&policyEndDate1=${policyEndDate1 }&policyEndDate2=${policyEndDate2 }&csDate1=${csDate1 }&csDate2=${csDate2 }&prdCode=${prdCode}&levelFlag=${levelFlag}&netFlag=${netFlag}&perm=${perm}&staffFlag=${staffFlag}"><span>导出统计结果</span></a>
&nbsp;&nbsp;&nbsp;&nbsp;</label>
</h2>
<br>
<div class="pageContent" layoutH="170" width="150%">
<div class="row" style="padding: 0 3px;">
	<div style="width:30%;border:1px solid #e66;margin:5px;float:left;min-height:100px">
	<h2 class="contentTitle">列表展示 &nbsp;&nbsp;&nbsp;&nbsp;</h2>
		<table class="table" width="100%">
		<thead>
			<tr>
				<th>序号</th>
				<th>机构</th>
				<th>满期件数</th>
				<th>AG件数</th>
				<th>件数进度</th>
				<th>金额进度</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${cmRst}" varStatus="idx">
			<tr target="slt_uid" rel="${item.organName}">
				<td style="text-align: center;">${idx.index+1 }</td>
				<td>${item.organName}</td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.policyFee}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.sumCsFee}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.sumCsFee/item.policyFee*100}" pattern="#,###.##" />%</td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.sumFee/item.sumPolicyFee*100}" pattern="#,###.##" />%</td>
			</tr>
			</c:forEach>
			<tr>
				<td>&nbsp;</td>
				<td>合计：</td>
				<td style="text-align: right;"><fmt:formatNumber value="${countPt}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${sumPt}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${sumPt/countPt*100}" pattern="#,###.##" />%</td>
				<td style="text-align: right;"><fmt:formatNumber value="${csMoney/csProfit*100}" pattern="#,###.##" />%</td>
			</tr>
		</tbody>
	</table>
	</div>
	<div style="width:65%;border:1px solid #e66;margin:5px;float:left;min-height:10px">
	<div id="agmain" style="width: 800px;height:500px;"></div>
	    <script type="text/javascript">
	        // 基于准备好的dom，初始化echarts实例
	        var myChart = echarts.init(document.getElementById('agmain'));
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
			        data: ['满期件数', 'AG件数', '进度%']
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
			            name: '件数',
			            min: 0,
			            max: ${maxTB},
			            interval: 200,
			            axisLabel: {
			                formatter: '{value}'
			            }
			        },
			        {
			            type: 'value',
			            name: '进度',
			            min: 0,
			            max: ${maxZB},
			            interval: 5,
			            axisLabel: {
			                formatter: '{value}%'
			            }
			        }
			    ],
			    series: [
			        {
			            name: '满期件数',
			            type: 'bar',
			            data: [${countStr}]
			        },
			        {
			            name: 'AG件数',
			            type: 'bar',
			            data: [${sumStr}]
			        },
			        {
			            name: '进度%',
			            type: 'line',
			            yAxisIndex: 1,
			            data: [${countPtStr}]
			        }
			    ]
			};
	
	        // 使用刚指定的配置项和数据显示图表。
	        myChart.setOption(option);
	    </script>
	</div>
</div>
</div>