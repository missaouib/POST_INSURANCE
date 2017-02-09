<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script src="${contextPath}/js/echarts.common.min.js"></script>
<form method="post" id="hfForm" action="${contextPath }/component/stastics/tuibao" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
					<label>所属机构：</label>
					<input name="orgCode" id="uw_orgCode" type="hidden" value="${orgCode }"/>
					<input class="validate[required] required" name="name" id="uw_orgName" type="text" readonly="readonly" style="width: 120px;" value="${name }"/>
					<a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
					<td>承保日期起：
					<input type="text" name="policyDate1" id="uwDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${policyDate1 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>承保日期止：
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
						<form:option value="年交"> 年交 </form:option>
						<form:option value="趸交"> 趸交 </form:option>
					</form:select>
					</td>
				</tr>
				<tr>
					<td><label>标记：</label>
					<form:select path="TuiBaoModel.levelFlag" id="tbflag" class="combox">
						<form:option value="prov"> 省级 </form:option>
						<form:option value="city"> 市级 </form:option>
					</form:select>
					</td>
					<td>退保日期起：
					<input type="text" name="csDate1" id="uwDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${csDate1 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>退保日期止：
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
<a class="buttonActive" target="_blank" href="${contextPath }/component/stastics/tuibao/toXls?orgCode=${orgCode }&policyDate1=${policyDate1 }&policyDate2=${policyDate2 }&csDate1=${csDate1 }&csDate2=${csDate2 }&prdCode=${prdCode}&levelFlag=${levelFlag}&netFlag=${netFlag}&perm=${perm}&staffFlag=${staffFlag}"><span>导出统计结果</span></a>
&nbsp;&nbsp;&nbsp;&nbsp;
<a class="buttonActive" target="_blank" href="${contextPath }/component/stastics/tuibao/dtlXls?orgCode=${orgCode }&policyDate1=${policyDate1 }&policyDate2=${policyDate2 }&csDate1=${csDate1 }&csDate2=${csDate2 }&prdCode=${prdCode}&levelFlag=${levelFlag}&netFlag=${netFlag}&perm=${perm}&staffFlag=${staffFlag}"><span>导出明细数据</span></a>
</h2>
<br>
<div class="pageContent" layoutH="70" width="150%">
<div class="row" style="padding: 0 3px;">
	<div class="sortDrag" style="width:30%;border:1px solid #e66;margin:5px;float:left;min-height:100px">
	<h2 class="contentTitle">列表展示（单位：万元）&nbsp;&nbsp;&nbsp;&nbsp;</h2>
		<table class="table" layoutH="220" width="100%">
		<thead>
			<tr>
				<th>序号</th>
				<th>机构</th>
				<th>退保费</th>
				<th>总保费</th>
				<th>占比</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${cmRst}" varStatus="idx">
			<tr target="slt_uid" rel="${item.organName}">
				<td style="text-align: center;">${idx.index+1 }</td>
				<td>${item.organName}</td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.policyFee/10000}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.sumPolicyFee/10000}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.policyFee/item.sumPolicyFee*100}" pattern="#,###.#" />%</td>
			</tr>
			</c:forEach>
			<tr>
				<td>&nbsp;</td>
				<td>合计：</td>
				<td style="text-align: right;"><fmt:formatNumber value="${sumTb}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${totalTb}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${sumTb/totalTb*100}" pattern="#,###.#" />%</td>
			</tr>
		</tbody>
	</table>
	</div>
	<div class="sortDrag" style="width:65%;border:1px solid #e66;margin:5px;float:left;min-height:10px">
	<div id="main" style="width: 800px;height:400px;"></div>
	    <script type="text/javascript">
	        // 基于准备好的dom，初始化echarts实例
	        var myChart = echarts.init(document.getElementById('main'));
	
	        option = {
        		title: {
	                text: '退保预警图表'
	            },
	            tooltip: {
	                trigger: 'axis'
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
	                data:['退保保费','占比']
	            },
	            xAxis: [
	                {
	                    type: 'category',
	                    data: [${col}]
	                }
	            ],
	            yAxis: [
	                {
	                    type: 'value',
	                    name: '退保金额（万元）',
	                    min: 0,
	                    max: ${maxTB},
	                    interval: 500,
	                    axisLabel: {
	                        formatter: '{value} '
	                    }
	                },
	                {
	                    type: 'value',
	                    name: '占比',
	                    min: 0,
	                    max: ${maxZB},
	                    interval: ${maxZB/10},
	                    axisLabel: {
	                        formatter: '{value} %'
	                    }
	                }
	            ],
	            series: [
	                {
	                    name:'退保金额',
	                    type:'bar',
	                    label: {
	                        normal: {
	                            show: true
	                        }
	                    },
	                    data:[${tuibao}]
	                },
	                {
	                    name:'占比',
	                    type:'line',
	                    yAxisIndex: 1,
	                    label: {
	                        normal: {
	                            show: true
	                        }
	                    },
	                    data:[${zhanbi}]
	                }
	            ]
	        };
	
	        // 使用刚指定的配置项和数据显示图表。
	        myChart.setOption(option);
	    </script>
	</div>
</div>
</div>