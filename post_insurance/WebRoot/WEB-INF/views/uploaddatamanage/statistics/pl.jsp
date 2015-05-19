<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<script src="${contextPath}/js/highcharts/jquery-1.8.2.min.js"></script>
<script src="${contextPath}/js/highcharts/highcharts.js"></script>
<script src="${contextPath}/js/highcharts/highcharts-3d.js"></script>
<script src="${contextPath}/js/highcharts/jquery.capacityFixed.js"></script>
<style type="text/css">
.divChart {
	height: 460px; 
	min-width: 600px; 
	max-width: 600px;
	margin: 0 0;
}
.divChart_lx {
	height: 200px; 
	min-width: 400px; 
	max-width: 400px;
	margin: 0 0;
}
body,div,ul,li,dl,dd,dt { margin:0; padding:0;}
li { list-style-type:none;}
img { border:0;}
.certen { float:left; width:100%; margin-top:0px;}
.certen .left { width:330px; float:left;}
.certen dl { margin-top:25px;}
.certen .left dl dt { width:322px; height:33px; background:url(${contextPath}/styles/index/images/kuang1.jpg) no-repeat; text-align:center; font-size:15px; color:#404040; font-weight:bold; line-height:33px; text-align:center;}
.certen .left dl dd .shu {
	height: 100%;
	float: left;
	margin-top: 25px;
}
.certen .left dl dd .shu ul li { height:32px; margin-bottom:35px; line-height:32px; font-size:14px;}
.certen .left dl dd .shu ul li a { color:black; text-decoration:none;}
.certen .left dl dd .shu ul li span { display:block; width:62px; height:32px; background:url(${contextPath}/styles/index/images/kuang2.jpg) no-repeat; line-height:32px; text-align:center; font-size:13px; color:#0D0E08;}
.certen .left dl dd .shu .dleft { width:140px; float:left; margin-right:15px; text-align:right;}
.certen .left dl dd .shu .dleft ul li span { float:right;}
.certen .left dl dd .shu .dcertent { width:15px; height:100%; background:url(${contextPath}/styles/index/images/kuang3.jpg); float:left; height:335px; margin-left:-10px;}
.certen .left dl dd .shu .dright { width:140px; float:left;}
.certen .left dl dd .shu .dright ul li span{ float:left;}
.certen .right { width:580px; float:right;}
.certen .right dl dt { width:322px; height:33px; background:url(${contextPath}/styles/index/images/kuang1.jpg) no-repeat; text-align:center; font-size:15px; color:#404040; font-weight:bold; line-height:33px; text-align:center;}
.certen .right dl dd .ll { float:left;}
.certen .right dl dd { font-size:13px;}
</style>

<div style="padding-left:20px;padding-top:20px;">
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
	<a class="btn btn-success" id="btnOK" onclick="javascript:onStatistics();" style="display:none;"><i class="icon-ok icon-white"></i>统计</a>

	<div class="certen">
		<div style=" width:1000px; margin: 0 auto;">
			<div class="left" id="divPL">
				<dl>
					<dt id="lblTitlePL"></dt>
			        <dd>
			        	<div class="shu">
			            	<div class="dleft" style="padding-top:35px;">
			                	<ul>
			                    	<li><span>NO2.</span><a id="lblXSE1" href='#' onclick='onDetail(this);'></a>&nbsp;&nbsp;</li>
			                  		<li><span>NO4.</span><a id="lblXSE3" href='#' onclick='onDetail(this);'></a>&nbsp;&nbsp;</li>
			                        <li><span>NO6.</span><a id="lblXSE5" href='#' onclick='onDetail(this);'></a>&nbsp;&nbsp;</li>
			                        <li><span>NO8.</span><a id="lblXSE7" href='#' onclick='onDetail(this);'></a>&nbsp;&nbsp;</li>
			                        <li><span>NO10.</span><a id="lblXSE9" href='#' onclick='onDetail(this);'></a>&nbsp;&nbsp;</li>
			                    </ul>
			                </div>
			                <div class="dcertent"></div>
			                <div class="dright">
			                	<ul>
			                		<li><span>NO1.</span>&nbsp;&nbsp;<a id="lblXSE0" href='#' onclick='onDetail(this);'></a></li>
			                        <li><span>NO3.</span>&nbsp;&nbsp;<a id="lblXSE2" href='#' onclick='onDetail(this);'></a></li>
			                        <li><span>NO5.</span>&nbsp;&nbsp;<a id="lblXSE4" href='#' onclick='onDetail(this);'></a></li>
			                        <li><span>NO7.</span>&nbsp;&nbsp;<a id="lblXSE6" href='#' onclick='onDetail(this);'></a></li>
			                        <li><span>NO9.</span>&nbsp;&nbsp;<a id="lblXSE8" href='#' onclick='onDetail(this);'></a></li>
			                    </ul>
			                </div>
			            </div>
			        </dd>
				</dl>
			</div>
			<div id="divDetail" class="right" style="display:none;">
			    <dl>
					<dt id="lblTitleZZL"></dt>
			        <dd style="width:650;height:470;">
			        	<div id="divZZL" class="divChart"></div>
					</dd>
				</dl>
				<dl>
					<dt id="lblTitleXSE"></dt>
					<dd style="width:650;height:470;">
						<div id="divXSE" class="divChart"></div>
					</dd>
				</dl>
				<dl>
					<dt id="lblTitleXSL"></dt>
					<dd style="width:650;height:470;">
						<div id="divXSL" class="divChart"></div>
					</dd>
				</dl>
			    <dl>
					<dt id="lblTitlePM"></dt>
			        <dd style="margin-top:20px;">
				        <div class="ll" style="background:url(${contextPath}/styles/index/images/01.png) no-repeat; width:74px; height:56px; "></div>
				        <span style="display:block; height:18px;" id="lblXSE_PM0"></span>
				        <div class="ll" style="background:url(${contextPath}/styles/index/images/02.png) no-repeat; width:74px; height:56px; margin-left:-14px; "></div>
				        <span style="display:block;height:18px;" id="lblXSE_PM1"></span>
				        <div class="ll" style="background:url(${contextPath}/styles/index/images/03.png) no-repeat; width:87px; height:56px; margin-left:-17px; "></div>
				        <span style="display:block;height:18px;" id="lblXSE_PM2"></span>
			        </dd>
				</dl>
			</div>
		</div>
	</div>
</div>
<div id="divZZL_lx" class="divChart_lx" style="display:none;"></div>
<div id="divXSE_lx" class="divChart_lx" style="display:none;"></div>
<div id="divXSL_lx" class="divChart_lx" style="display:none;"></div>

<script type="text/javascript">
var objZZLChart = null;
var categoriesZZL = [201401, 201402, 201403];
var dataZZL = [2, 3, 4];
var objXSEChart = null;
var categoriesXSE = [201401, 201402, 201403];
var dataXSE = [2, 3, 4];
var objXSLChart = null;
var categoriesXSL = [201401, 201402, 201403];
var dataXSL = [2, 3, 4];

var objZZLChart_lx = null;
var categoriesZZL_lx = [201401, 201402, 201403];
var dataZZL_lx = [2, 3, 4];

var objXSEChart_lx = null;
var categoriesXSE_lx = [201401, 201402, 201403];
var dataXSE_lx = [2, 3, 4];

var objXSLChart_lx = null;
var categoriesXSL_lx = [201401, 201402, 201403];
var dataXSL_lx = [2, 3, 4];

function onStatistics() {
	var dm = $("#listDM").val();
	var ny = $("#listNY").val();
	$("#lblTitlePL").html(ny + "销售额前10位品类");
	
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
				dataXSE_PH = response.xse_ph;
				// 处理排序数据
				onHandlePX();
		    } else {
		    	alert("获取统计数据出错，请检查是否有品类数据。");
		    }
		}
	});
}

function onStatisticsDetail(pl) {
	var dm = $("#listDM").val();
	var ny = $("#listNY").val();
	$("#divDetail").css("display","");
	$("#lblTitleZZL").html(ny + "增长率前10位品规");
	$("#lblTitleXSE").html(ny + "销售额前10位品规");
	$("#lblTitleXSL").html(ny + "销售量前10位品规");
	$("#lblTitlePM").html(ny + "销售贡献最低的3位品规");
	$.ajax({
		type: 'post',
		url: "/uploaddatamanage/statistics/pm",
		dataType: "text",
		data: {"ny": ny, "dm": dm, "pl": pl},
		error: function () {
			alert("获取统计数据出错，请稍后重试。");
		},
		success: function (data) {
		    var response = $.parseJSON(data);
		    if(response.result == "success") {
				dataXSEPM_PH = response.xse_ph;
				categoriesZZL = response.zzl_ph;
				dataZZL = response.zzl_ph_data;
				
				categoriesXSE = response.xse_categories;
				dataXSE = response.xse_data;
				
				categoriesXSL = response.xsl_categories;
				dataXSL = response.xsl_data;
				
				categoriesZZL_lx = response.zzl.pm;
				dataZZL_lx = response.zzl.data;
				
				categoriesXSE_lx = response.xse_categories_pre;
				dataXSE_lx = response.xse_data_pre;
				
				categoriesXSL_lx = response.xsl_categories_pre;
				dataXSL_lx = response.xsl_data_pre;
				
				onZZLInit();
				// 处理排序数据
				onHandlePMPX();
				
				onXSEInit();
				onXSLInit();
		    } else {
		    	alert("获取统计数据出错，请检查是否有品名数据。");
		    }
		}
	});
}

var dataXSE_PH = [];
function onHandlePX() {
	for(var i=0; i<10; i++) {
		$("#lblXSE" + i).html("");
	}

	for(var i=0; i<dataXSE_PH.length + 1; i++) {
		$("#lblXSE" + i).html(dataXSE_PH[i]);
	}
}

var dataXSEPM_PH = [];
function onHandlePMPX() {
	for(var i=0; i<3; i++) {
		$("#lblXSE_PM" + i).html("");
	}

	for(var i=0; i<dataXSEPM_PH.length + 1 && i<3; i++) {
		$("#lblXSE_PM" + i).html(dataXSEPM_PH[i]);
	}
}

function onDetail(obj) {
	var pl = $(obj).html();
	if(pl != null && pl !='' && pl != 'undefined') {
		onStatisticsDetail(pl);
	}
}

function onZZLInit() {
	if(objZZLChart) {
		objZZLChart.destroy();
		objZZLChart = null;
	}

	objZZLChart = new Highcharts.Chart({
        chart: {
         	renderTo: 'divZZL',
            type: 'column',
            marginBottom: 100,
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
					formatter: zzlFormatter,
					color: 'black'
				}
//			},
//			series: {
//				point: {
//					events : {
//						mouseOver: onColumnOver,
//						mouseOut: onColumnOut
//					}
//				}
            }
        },
        tooltip: {
        	useHtml: true,
            headerFormat: '<b>{point.key}</b><br/>',
            pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y}%<br/>',
            footerFormat: '<div id="divPointPosition"></div>'
        },
        xAxis: {
            categories: categoriesZZL,
            labels: {enabled:true,rotation: 315}
        },
        yAxis: {
        	gridLineWidth: 0,
        	title: {text: ''},
            opposite: false,
            labels: {
            	enabled: false
            } 
        },
        legend: {
        	enabled: false
        },
        series: [{
            name: '增长率',
            data: dataZZL,
            color: '#C2E646'
        }]
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
            marginBottom: 100,
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
					//formatter: zzlFormatter,
					color: 'black'
				}
			},
			series: {
				point: {
					events : {
						mouseOver: onXSEColumnOver,
						mouseOut: onXSEColumnOut
					}
				}
            }
        },
        tooltip: {
            headerFormat: '<b>{point.key}</b><br>',
            pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y}'
        },
        xAxis: {
            categories: categoriesXSE,
            labels: {enabled:true,rotation: 315}
        },
        yAxis: {
        	gridLineWidth: 0,
        	title: {text: ''},
            opposite: false,
            labels: {
            	enabled: false
            } 
        },
        legend: {
        	enabled: false
        },
        series: [{
            name: '销售额',
            data: dataXSE,
            color: '#C2E646'
        }]
    });
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
            marginBottom: 100,
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
					//formatter: zzlFormatter,
					color: 'black'
				}
			},
			series: {
				point: {
					events : {
						mouseOver: onXSLColumnOver,
						mouseOut: onXSLColumnOut
					}
				}
            }
        },
        tooltip: {
            headerFormat: '<b>{point.key}</b><br>',
            pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y}'
        },
        xAxis: {
            categories: categoriesXSL,
            labels: {enabled:true,rotation: 315}
        },
        yAxis: {
        	gridLineWidth: 0,
        	title: {text: ''},
            opposite: false,
            labels: {
            	enabled: false
            } 
        },
        legend: {
        	enabled: false
        },
        series: [{
            name: '销售量',
            data: dataXSL,
            color: '#C2E646'
        }]
    });
}

function onData_lx_Init(divID_lx, objChart_lx, categories, data) {
	if(objChart_lx) {
		objChart_lx.destroy();
		objChart_lx = null;
	}

	objChart_lx = new Highcharts.Chart({
        chart: {
         	renderTo: divID_lx,
            type: 'line',
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
        xAxis: {
            categories: categories
        },
        yAxis: {
            title: {
                text: ''
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: ''
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },      
        series: data
    });
}

function onColumnOver(event) {
	if(categoriesZZL_lx == null || dataZZL_lx == null || dataZZL_lx.length < 2) {
		return;
	}	

	var dCategoriesZZL_lx = null;
	var dDataZZL_lx = null;
	var iIndex = -1;
	for(var i=0; i<categoriesZZL_lx.length; i++) {
		if(categoriesZZL_lx[i] === this.category) {
			iIndex = i;
			break;
		}
	}
	
	if(iIndex == -1) {
		return;
	}
	
	// [ny1, ny2]
	// [{name:pm,data:[zzl1, zzl2]}]
	var ny1 = dataZZL_lx[0].name;
	var ny2 = dataZZL_lx[1].name;
	var zzl1 = dataZZL_lx[0].data[iIndex];
	var zzl2 = dataZZL_lx[1].data[iIndex];

	dCategoriesZZL_lx = [ny1, ny2];
	dDataZZL_lx = [{"name":this.category,"data":[zzl1, zzl2]}];
	
	onData_lx_Init('divZZL_lx', objZZLChart_lx, dCategoriesZZL_lx, dDataZZL_lx);
	
   	var left = $("#lblTitleZZL").offset().left + 200;
   	var top = $("#lblTitleZZL").offset().top;
	$("#divZZL_lx").css({"top":top,"left":left}).show().capacityFixed();
}

function onXSEColumnOver(event) {
	if(categoriesXSE_lx == null || dataXSE_lx == null || categoriesXSE_lx.length < 2) {
		return;
	}	

	var dData = null;
	var iIndex = -1;
	for(var i=0; i<categoriesXSE.length; i++) {
		if(categoriesXSE[i] === this.category) {
			iIndex = i;
			break;
		}
	}

	if(iIndex == -1) {
		return;
	}

	// [{name:pm,data:[xse1, xse2]}]
	var xse1 = dataXSE_lx[iIndex];
	var xse2 = dataXSE[iIndex];
	
	dData = [{"name":this.category,"data":[xse1, xse2]}];

	onData_lx_Init('divXSE_lx', objXSEChart_lx, categoriesXSE_lx, dData);

   	var left = $("#lblTitleXSE").offset().left + 200;
   	var top = $("#lblTitleXSE").offset().top;
	$("#divXSE_lx").css({"top":top,"left":left}).show().capacityFixed();
}

function onXSLColumnOver(event) {
	if(categoriesXSL_lx == null || dataXSL_lx == null || categoriesXSL_lx.length < 2) {
		return;
	}	

	var dData = null;
	var iIndex = -1;
	for(var i=0; i<categoriesXSL.length; i++) {
		if(categoriesXSL[i] === this.category) {
			iIndex = i;
			break;
		}
	}
	
	if(iIndex == -1) {
		return;
	}
	
	// [{name:pm,data:[xsl1, xsl2]}]
	var xsl1 = dataXSL_lx[iIndex];
	var xsl2 = dataXSL[iIndex];

	dData = [{"name":this.category,"data":[xsl1, xsl2]}];
	
	onData_lx_Init('divXSL_lx', objXSLChart_lx, categoriesXSL_lx, dData);
	
   	var left = $("#lblTitleXSL").offset().left + 200;
   	var top = $("#lblTitleXSL").offset().top;
	$("#divXSL_lx").css({"top":top,"left":left}).show().capacityFixed();
}

function onColumnOut(event) {
	$("#divZZL_lx").hide();
	if(objZZLChart_lx != null) {
		objZZLChart_lx.destroy();
		objZZLChart_lx = null;
	}
}

function onXSEColumnOut(event) {
	$("#divXSE_lx").hide();
	if(objXSEChart_lx != null) {
		objXSEChart_lx.destroy();
		objXSEChart_lx = null;
	}
}

function onXSLColumnOut(event) {
	$("#divXSL_lx").hide();
	if(objXSLChart_lx != null) {
		objXSLChart_lx.destroy();
		objXSLChart_lx = null;
	}
}

function zzlFormatter() {
	return(this.y + '%');
}

window.onbeforeunload = function() {
	//onDestroy();
}

function onDestroy() {
	if(objZZLChart) {
		objZZLChart.destroy();
		objZZLChart = null;
	}
	
	if(objXSEChart) {
		objXSEChart.destroy();
		objXSEChart = null;
	}
	
	if(objXSLChart) {
		objXSLChart.destroy();
		objXSLChart = null;
	}
}

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
		onStatisticsDetail('');
		$('#listDM').change(function(){
			onStatistics();
			onStatisticsDetail('');
		});
	}
);

</script>