<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>    
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理平台</title>
<link href="${contextPath}/css/common.css" rel="stylesheet"/>
</head>

<body style="background-color: white;">
<div class="top">
  <div class="top1">
    	<div class="logo"><a href="/web/index"><img src="${contextPath}/images/logo.png" width="202" height="80" style="margin-top:20px;border:none;" /></a>
            <div class="anniu">
            </div>
        </div>
  </div>
<div class="dao">
	<div class="daon">
		<ul>
			<li><a href="/web/index">首页</a><span class="daobian"></span></li>
        	<shiro:user>  
        	<li class="daob"><a href="/uploaddatamanage/uploaddata/index">数据服务</a><span class="daobian"></span></li>
            <li><a href="/uploaddatamanage/document/index">行业报告</a><span class="daobian"></span></li>
            </shiro:user>
        	<li><a href="#">新闻动态</a><span class="daobian"></span></li>
            <shiro:guest>
            <li><a href="/web/tologin">登录</a><span class="daobian"></span></li>
            </shiro:guest>
            <shiro:user>
            <li><a href="/members/user/index">用户管理</a><span class="daobian"></span></li>
            <li><a href="/web/logout">(<shiro:principal/>)退出</a><span class="daobian"></span></li>
            </shiro:user>
        </ul>
	</div>
</div>
</div>
<div class="certen">
<div class="main_visual">
</div>

<link href="${contextPath}/js/bootstrap/css/plupload.css" rel="stylesheet" />
<script src="${contextPath}/js/highcharts/jquery-1.8.2.min.js"></script>
<script src="${contextPath}/js/highcharts/highcharts.js"></script>
<script src="${contextPath}/js/highcharts/highcharts-3d.js"></script>

<script src="${contextPath}/js/datav/build/deps.js"></script>
<script src="${contextPath}/js/datav/deps/seajs/sea.js"></script>

<style type="text/css">
#divXSE {
	height: 400px; 
	min-width: 400px; 
	max-width: 500px;
	margin: 0 0;
}
#divXSL {
	height: 400px; 
	min-width: 400px; 
	max-width: 500px;
	margin: 0 0;
}
.divTable
{
	width: 100%;
	display:block;
	padding-top:10px;
	padding-bottom:1px;
	padding-right:10px;
	padding-left:50px; 
}
.divRow
{
	width: 99%; 
	height:30px;
	display:block;
	padding-bottom:5px;
}
.divColumn
{
	float: left;
	height:30px;
	width:150px;
	display:block;
}
</style>

<style type="text/css">
#divZZL {
	height: 0px;
	min-width: 400px; 
	max-width: 500px;
	margin: 0 0;
}
#divTreemap {
    padding-left: 0px;
}
.divTable1
{
	width: 96%;
	display:block;
	padding-top:30px;
	padding-bottom:0px;
	padding-right:0px;
	padding-left:30px; 
}
.divRow1
{
	width: 99%; 
	height:25px;
	display:block;
	padding-bottom:5px;
}
.divColumn1
{
	float: left;
	width:300px;
	display:block;
}
</style>

<div id="divZZL1" style="padding-left:200px;padding-top:20px;">
	<div style="display:none;">
	年月
	<select id="listNY" name="ny" style="width:100px;">
		<c:forEach var="item" items="${ny}">
		<option value="${item}">${item}</option>
		</c:forEach>
	</select>
	<a class="btn btn-success" id="btnOK" onclick="javascript:onStatistics();"><i class="icon-ok icon-white"></i>统计</a>
	</div>
	<div style="clear:both;">
		<div id="divXSE_ZZL" style="float:left;">
			<div id="content" class="divTable">
			   	<div class="divRow">
				    <div class="divColumn">
				        <div>
				            <label id="lblNY_XSE"></label>
				        </div>
				    </div>
				    <div class="divColumn">
				        <div>
				            <label id="lblData_XSE" style="color:#2166AC;"></label>
				        </div>
				    </div>
				</div>
				<div class="divRow">
				    <div class="divColumn">
				        <div>
				            <label id="lblNY_XSE1"></label>
				        </div>
				    </div>
				    <div class="divColumn">
				        <div>
				            <label id="lblXSE_ZZL1" style="color:#F4A582;"></label>
				        </div>
				    </div>
				</div>
				<div class="divRow">
				    <div class="divColumn">
				        <div>
				            <label id="lblNY_XSE2"></label>
				        </div>
				    </div>
				    <div class="divColumn">
				        <div>
				            <label id="lblXSE_ZZL2" style="color:#D6604D;"></label>
				        </div>
				    </div>
				</div>
			</div>
		</div>
		<div id="divXSL_ZZL" style="float:left;">
			<div id="content" class="divTable">
			   	<div class="divRow">
				    <div class="divColumn">
				        <div>
				            <label id="lblNY_XSL"></label>
				        </div>
				    </div>
				    <div class="divColumn">
				        <div>
				            <label id="lblData_XSL" style="color:#2166AC;"></label>
				        </div>
				    </div>
				</div>
				<div class="divRow">
				    <div class="divColumn">
				        <div>
				            <label id="lblNY_XSL1"></label>
				        </div>
				    </div>
				    <div class="divColumn">
				        <div>
				            <label id="lblXSL_ZZL1" style="color:#F4A582;"></label>
				        </div>
				    </div>
				</div>
				<div class="divRow">
				    <div class="divColumn">
				        <div>
				            <label id="lblNY_XSL2"></label>
				        </div>
				    </div>
				    <div class="divColumn">
				        <div>
				            <label id="lblXSL_ZZL2" style="color:#D6604D;"></label>
				        </div>
				    </div>
				</div>
			</div>
		</div>
	</div>
	<div style="clear:both;">
		<div id="divXSE" style="float:left;"></div>
		<div id="divXSL" style="float:left;"></div>
	</div>
</div>

<div id="divXSEParent" style="clear:both;padding-left:200px;padding-top:60px;">
	<span style="display:none;">
	年月
	<select id="listNY2" name="ny" style="width:100px;">
		<c:forEach var="item" items="${ny}">
		<option value="${item}">${item}</option>
		</c:forEach>
	</select>
	</span>
	<input type="radio" name="statisticstype" value="0" onclick="onChange(this.value);" checked="checked" />销售额
	<input type="radio" name="statisticstype" value="1" onclick="onChange(this.value);" />销售量
	<input type="radio" name="statisticstype" value="2" onclick="onChange(this.value);" />毛利率	
	<a class="btn btn-success" id="btnOK" onclick="javascript:onStatistics();" style="display:none;"><i class="icon-ok icon-white"></i>统计</a>
	<div class="divTable1">
	   	<div class="divRow1">
		    <div class="divColumn1">
		        <div>
		            <label id="lblTitle"></label>
		        </div>
		    </div>
		    <div class="divColumn1">
		        <div>
		            <label id="lblZZL"></label>
		        </div>
		    </div>
		</div>
		<div class="divRow1">
		    <div class="divColumn1">
		        <div>
		            <label id="lblDM0" style="color:#ED561B;"></label>
		        </div>
		    </div>
		    <div class="divColumn1">
		        <div>
		            <label id="lblZZL0" style="color:#ED561B;"></label>
		        </div>
		    </div>
		</div>
		<div class="divRow1">
		    <div class="divColumn1">
		        <div>
		            <label id="lblDM1" style="color:#D6604D;"></label>
		        </div>
		    </div>
		    <div class="divColumn1">
		        <div>
		            <label id="lblZZL1" style="color:#D6604D;"></label>
		        </div>
		    </div>
		</div>
		<div class="divRow1">
		    <div class="divColumn1">
		        <div>
		            <label id="lblDM2" style="color:#D6604D;"></label>
		        </div>
		    </div>
		    <div class="divColumn1">
		        <div>
		            <label id="lblZZL2" style="color:#D6604D;"></label>
		        </div>
		    </div>
		</div>
	</div>
	<div id="divTreemap"></div>
	<div id="divZZL"></div>
</div>
<div style="height:60px;">
</div>

<script type="text/javascript">
var objXSEChart = null;
var objXSLChart = null;

function onStatistics() {
	var ny = $("#listNY").val();
	if(ny==null||ny=='undefined'||ny=='') {
		alert("没有数据。");
		return;
	}
	$.ajax({
		type: 'post',
		url: "/uploaddatamanage/statistics/zzl",
		dataType: "text",
		data: {"ny": ny},
		error: function () {
			alert("获取统计数据出错，请稍后重试。");
		},
		success: function (data) {
		    var response = $.parseJSON(data);
		    if(response.result == "success") {
				categoriesXSE = response.xse.ny;
				dataXSE = response.xse.data;
				categoriesXSL = response.xsl.ny;
				dataXSL = response.xsl.data;
				
				$("#lblNY_XSE").html("");
				$("#lblData_XSE").html("");
				$("#lblNY_XSE1").html("");
				$("#lblXSE_ZZL1").html("");
				$("#lblNY_XSE2").html("");
				$("#lblXSE_ZZL2").html("");
				
				$("#lblNY_XSL").html("");
				$("#lblData_XSL").html("");
				$("#lblNY_XSL1").html("");
				$("#lblXSL_ZZL1").html("");
				$("#lblNY_XSL2").html("");
				$("#lblXSL_ZZL2").html("");
				
				onXSEInit();
				onXSLInit();
		    }
		}
	});
}

function onXSEInit() {
	if(objXSEChart) {
		objXSEChart.destroy();
		objXSEChart = null;
	}

	objXSEChart = new Highcharts.Chart({
        chart: {
         	renderTo: 'divXSE',
            type: 'column',
            margin: 75,
            options3d: {
				enabled: false,
                alpha: 10,
                beta: 15,
                depth: 70
            }
        },
        credits: {
            enabled: false
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        plotOptions: {
            column: {
                depth: 50,
				dataLabels: {  
					enabled: true, 
					formatter: xseFormatter,
					color: 'black'
				}
            }
        },
        xAxis: {
            categories: categoriesXSE
        },
        yAxis: {
        	title: {text: '销售额(元)'},
            opposite: false
        },
        series: [{
            name: '销售额',
            data: dataXSE,
            color: '#2166AC'
        }]
    });
}

var categoriesXSE = [201401, 201402, 201403];
var dataXSE = [2, 3, 4];
function xseFormatter() {
	var val = 0;
	var iIndex = 0;
	
	for(var i=0; i<categoriesXSE.length; i++) {
		if(this.x == categoriesXSE[i]) {
			iIndex = i;
			break;
		}
	}
	
	if(iIndex == categoriesXSE.length - 1) {
		$("#lblNY_XSE").html(this.key);
		$("#lblData_XSE").html(this.y);
	}
	
	if(iIndex > 0) {
		var current = this.series.data[iIndex].y;
		var pre = this.series.data[iIndex - 1].y;
		if(!isNaN(current) && !isNaN(pre)) {
			val = Math.round((current - pre)*10000/pre)/100;
			$("#lblNY_XSL" + iIndex).html(this.key + "增长率");
			$("#lblXSL_ZZL" + iIndex).html(val + "%");
			return(this.y);
		}
	}

	return(this.y);
}

function onXSLInit() {
	if(objXSLChart) {
		objXSLChart.destroy();
		objXSLChart = null;
	}
	
	objXSLChart = new Highcharts.Chart({
        chart: {
        	renderTo: 'divXSL',
            type: 'column',
            margin: 75,
            options3d: {
				enabled: false,
                alpha: 10,
                beta: 15,
                depth: 70
            }
        },
        credits: {
            enabled: false
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        plotOptions: {
            column: {
                depth: 50,
                dataLabels: {  
					enabled: true, 
					formatter: xslFormatter,
					color: 'black'
				}
            }
        },
        xAxis: {
        	//title: {text: '年月'},
            categories: categoriesXSL
        },
        yAxis: {
        	title: {text: '销售量(U)'},
            opposite: false
        },
        series: [{
            name: '销售量',
            data: dataXSL,
            color: '#2166AC'
        }]
    });
};

var categoriesXSL = [201401, 201402, 201403];
var dataXSL = [2, 3, 4];
function xslFormatter() {
	var val = 0;
	var iIndex = 0;
	for(var i=0; i<categoriesXSL.length; i++) {
		if(this.x == categoriesXSL[i]) {
			iIndex = i;
			break;
		}
	}
	
	if(iIndex == categoriesXSL.length - 1) {
		$("#lblNY_XSL").html(this.key);
		$("#lblData_XSL").html(this.y);
	}
	
	if(iIndex > 0) {
		var current = this.series.data[iIndex].y;
		var pre = this.series.data[iIndex - 1].y;
		if(!isNaN(current) && !isNaN(pre)) {
			val = Math.round((current - pre)*10000/pre)/100;
			$("#lblNY_XSE" + iIndex).html(this.key + "增长率");
			$("#lblXSE_ZZL" + iIndex).html(val + "%");
			return(this.y);
		}
	}

	return(this.y);
}

window.onbeforeunload = function() {
	//onDestroy();
}

function onDestroy() {	
	if(objXSEChart) {
		objXSEChart.destroy();
		objXSEChart = null;
	}
	
	if(objXSLChart) {
		objXSLChart.destroy();
		objXSLChart = null;
	}
};

$(document).ready(function(){onStatistics();});
</script>

<script type="text/javascript">
seajs.config({
    alias: {
        'DataV': '${contextPath}/js/datav/lib/datav.js',
        'Treemap': '${contextPath}/js/datav/lib/charts/treemap.js'
    }
});
</script>
    
<script type="text/javascript">
var objTreemap = null;

function onTreemap(jsonData) {
    seajs.use(["Treemap", "DataV"], function (Treemap, DataV) {
        DataV.changeTheme("theme0");
        if(objTreemap == null) {
        	objTreemap = new Treemap("divTreemap", {"width": 700, "height": 400});
        }
        
        objTreemap.on("mousemove", function () {
            var jqNode = this.jqNode;
            treemapNode = jqNode.treemapNode,
            floatTag = jqNode.treemap.floatTag;

            floatTag.html('<div style="text-align: center; color:#fff;">' +
    treemapNode.name + '</div>' +
    '<div style="text-align: center; color: #fff;">' +
    '' + treemapNode.value + ',增长率:' + getTreemapZZL(treemapNode.name) + '%</div>');
        });
        
        objTreemap.setSource(jsonData);
        objTreemap.render();
    });
}

function getTreemapZZL(dm) {
	var zzl = 0;
	if(dataZZLTreemap) {
		for(var i=0; i<dataZZLTreemap.data.length; i++) {
			if(dataZZLTreemap.data[i].dm == dm) {
				zzl = dataZZLTreemap.data[i].zzl;
			}
		}
	}
	
	return(zzl);
}

var objZZL = null;
var categoriesZZL = [201401, 201402, 201403];
var dataZZL = [2, 3, 4];
var dataZZLTreemap = [];

$(function () {
    Highcharts.setOptions({
        colors: ['#92C5DE', '#D6604D', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4']
    });
});
    
function onStatistics2() {
	var ny = $("#listNY2").val();
	var statisticstype = $("input[name='statisticstype']:checked").val();
	$.ajax({
		type: 'post',
		url: "/uploaddatamanage/statistics/xse",
		dataType: "text",
		data: {"ny": ny},
		error: function () {
			alert("获取统计数据出错，请稍后重试。");
		},
		success: function (data) {
		    var response = $.parseJSON(data);
		    if(response.result == "success") {
		    	switch(parseInt(statisticstype)) {
		    		case 0:
						categoriesZZL = response.xse.dm;
						dataZZL = response.xse.data;
						dataZZLTreemap = response.zzl_xse;
						onTreemap(response.xse.treemap);
						//onZZLInit();
						onZZLList();
		    			break;
		    		case 1:
						categoriesZZL = response.xsl.dm;
						dataZZL = response.xsl.data;
						dataZZLTreemap = response.zzl_xsl;
						onTreemap(response.xsl.treemap);
						//onZZLInit();
						onZZLList();
		    			break;
		    		case 2:
						categoriesZZL = response.mll.dm;
						dataZZL = response.mll.data;
						dataZZLTreemap = response.zzl_mll;
						onTreemap(response.mll.treemap);
						//onZZLInit();
						onZZLList();
		    			break;
		    	}
		    }
		}
	});
}

function onZZLList() {
	var zzl = dataZZLTreemap.data;
	var tempdm;
	var tempzzl;
	for(var i=0; i<zzl.length-1; i++) {
		for(var j=i+1; j<zzl.length; j++)
		if(zzl[j].zzl>zzl[i].zzl) {
			tempdm = zzl[j].dm;
			tempzzl = zzl[j].zzl;
			zzl[j].dm = zzl[i].dm;
			zzl[j].zzl = zzl[i].zzl;
			zzl[i].dm = tempdm;
			zzl[i].zzl = tempzzl;
		}
	}
	
	$("#lblTitle").html("<b>增长率前三</b>");
	for(var i=0; i<zzl.length&&i<3; i++) {
		$("#lblDM" + i).html(zzl[i].dm);
		$("#lblZZL" + i).html(zzl[i].zzl + "%");
	}
}

function onZZLInit() {
	if(objZZL) {
		objZZL.destroy();
		objZZL = null;
	}

	objZZL = new Highcharts.Chart({
        chart: {
         	renderTo: 'divZZL',
            type: 'bar',
            options3d: {
                enabled: false,
                alpha: 15,
                beta: 15,
                viewDistance: 25,
                depth: 40
            },
            marginTop: 80,
            marginRight: 40
        },
        credits: {
            enabled: false
        },
        title: {
            text: ''
        },
        xAxis: {
            categories: categoriesZZL,
            title: {
            	text: ''
            }
        },
        yAxis: {
            allowDecimals: false,
            title: {
                text: '增长率（%）'
            }
        },
        tooltip: {
            headerFormat: '<b>{point.key}</b><br>',
            pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y}%'
        },
        plotOptions: {
            bar: {
                //stacking: 'normal',
                //depth: 40,
                dataLabels: {  
					enabled: true
					//format: "{y}%",
				}
            }
        },
        series: dataZZL
    });
}

function onDestroy() {
	if(objZZL) {
		objZZL.destroy();
		objZZL = null;
	}
};

function onChange(value) {
	onStatistics2();
};

$(document).ready(function(){onStatistics2();});
</script>

	<%@ include file="../../webroot.inc.jsp" %>
</div>
</div>
</div>
</div>


</body>
</html>