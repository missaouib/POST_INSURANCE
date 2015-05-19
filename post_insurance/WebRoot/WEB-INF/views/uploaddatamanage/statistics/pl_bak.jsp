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
#divZZL_XSE {
	height: 400px; 
	min-width: 400px; 
	max-width: 500px;
	margin: 0 0;
	padding-left:50px;
}
.divTable
{
	width: 100%;
	display:block;
	padding-top:30px;
	padding-bottom:0px;
	padding-right:0px;
	padding-left:300px; 
}
.divRow
{
	width: 99%; 
	height:25px;
	display:block;
	padding-bottom:5px;
}
.divColumn
{
	float: left;
	width:150px;
	display:block;
}
</style>

<div id="divZZL" style="padding-left:100px;padding-top:20px;">
	<span style="display:none;">
	年月
	<select id="listNY" name="ny" style="width:100px;">
		<c:forEach var="item" items="${ny}">
		<option value="${item}">${item}</option>
		</c:forEach>
	</select>
	</span>
	店名
	<select id="listDM" name="dm" style="width:100px;">
		<c:forEach var="item" items="${dm}">
		<option value="${item}">${item}</option>
		</c:forEach>
	</select>	
	<a class="btn btn-success" id="btnOK" onclick="javascript:onStatistics();" style="display:none;><i class="icon-ok icon-white"></i>统计</a>
	<div>
		<div id="content" class="divTable">
		   	<div class="divRow">
			    <div class="divColumn">
			        <div>
			            <label id="lblXSE"></label>
			        </div>
			    </div>
			    <div class="divColumn">
			        <div>
			            <label id="lblZZL"></label>
			        </div>
			    </div>
			</div>
			<div class="divRow">
			    <div class="divColumn">
			        <div>
			            <label id="lblXSE0" style="color:#ED561B;"></label>
			        </div>
			    </div>
			    <div class="divColumn">
			        <div>
			            <label id="lblZZL0" style="color:#ED561B;"></label>
			        </div>
			    </div>
			</div>
			<div class="divRow">
			    <div class="divColumn">
			        <div>
			            <label id="lblXSE1" style="color:#D6604D;"></label>
			        </div>
			    </div>
			    <div class="divColumn">
			        <div>
			            <label id="lblZZL1" style="color:#D6604D;"></label>
			        </div>
			    </div>
			</div>
			<div class="divRow">
			    <div class="divColumn">
			        <div>
			            <label id="lblXSE2" style="color:#D6604D;"></label>
			        </div>
			    </div>
			    <div class="divColumn">
			        <div>
			            <label id="lblZZL2" style="color:#D6604D;"></label>
			        </div>
			    </div>
			</div>
		</div>
	</div>
	<div>
		<div id="divXSE" style="float:left;"></div>
		<div id="divZZL_XSE" style="float:left;"></div>
	</div>
</div>

<script type="text/javascript">
function onStatistics() {
	var dm = $("#listDM").val();
	var ny = $("#listNY").val();
	$.ajax({
		type: 'post',
		url: "/uploaddatamanage/statistics/pl",
		dataType: "text",
		data: {"ny": ny, "dm": dm},
		error: function () {
			alert("获取统计数据出错，请稍后重试。");
		},
		success: function (data) {
		    var response = $.parseJSON(data);
		    if(response.result == "success") {
				dataXSE = response.xse;
				categoriesZZL_XSE = response.zzl.pl;
				dataZZL_XSE = response.zzl.data;
				
				onXSEInit();
				onZZL_XSEInit();
				
				dataXSE_PH = response.xse_ph;
				dataZZL_PH = response.zzl_ph;
				// 处理排序数据
				onHandlePX();
		    } else {
		    	alert("获取统计数据出错，请检查是否有品类数据。");
		    }
		}
	});
}

var dataXSE_PH = [];
var dataZZL_PH = [];
function onHandlePX() {
	for(var i=0; i<3; i++) {
		$("#lblXSE").html("<b>最畅销品类</b>")
		$("#lblXSE" + i).html("");
		$("#lblZZL").html("<b>跃升品类</b>")
		$("#lblZZL" + i).html("");
	}

	for(var i=0; i<dataXSE_PH.length + 1; i++) {
		$("#lblXSE").html("<b>最畅销品类</b>")
		$("#lblXSE" + i).html(dataXSE_PH[i]);
	}
	
	for(var i=0; i<dataZZL_PH.length + 1; i++) {
		$("#lblZZL").html("<b>跃升品类</b>")
		$("#lblZZL" + i).html(dataZZL_PH[i]);
	}
}

var objXSEChart = null;
var dataXSE = [2, 3, 4];
function onXSEInit() {
	if(objXSEChart) {
		objXSEChart.destroy();
		objXSEChart = null;
	}

	objXSEChart = new Highcharts.Chart({
        chart: {
         	renderTo: 'divXSE',
            type: 'pie',
            options3d: {
				enabled: false,
                alpha: 45
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
            pie: {
                innerSize: 100,
                depth: 45,      
                minSize: 300,      
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.2f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },        
        series: dataXSE
    });
}

var objZZL_XSE = null;
var categoriesZZL_XSE = [201401, 201402, 201403];
var dataZZL_XSE = [2, 3, 4];
function onZZL_XSEInit() {
	if(objZZL_XSE) {
		objZZL_XSE.destroy();
		objZZL_XSE = null;
	}

	objZZL_XSE = new Highcharts.Chart({
        chart: {
         	renderTo: 'divZZL_XSE',
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
            categories: categoriesZZL_XSE,
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
        series: dataZZL_XSE
    });
}

window.onbeforeunload = function() {
	//onDestroy();
}

function onDestroy() {
	if(objXSEChart) {
		objXSEChart.destroy();
		objXSEChart = null;
	}
	
	if(objZZL_XSE) {
		objZZL_XSE.destroy();
		objZZL_XSE = null;
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

$(document).ready(
	function(){
		onStatistics();
		$('#listDM').change(function(){
			onStatistics();
		});
	}
);
</script>