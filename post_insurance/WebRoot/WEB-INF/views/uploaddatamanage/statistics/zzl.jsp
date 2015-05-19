<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<link href="${contextPath}/js/bootstrap/css/plupload.css" rel="stylesheet" />
<script src="${contextPath}/js/highcharts/jquery-1.8.2.min.js"></script>
<script src="${contextPath}/js/highcharts/highcharts.js"></script>
<script src="${contextPath}/js/highcharts/highcharts-3d.js"></script>
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

<div id="divZZL" style="padding-left:100px;padding-top:20px;">
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

window.parent.onTabClosing("tabClosing", function(event){
	try {
		var tabid = $(event.target).attr("tabid");
		var currentTabid = $(window.frameElement).attr("tabid");
		if(tabid==currentTabid) {
			onDestroy();
		}
	} catch(e) {
	}
});

$(document).ready(function(){onStatistics();});
</script>