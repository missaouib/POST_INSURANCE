<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<link href="${contextPath}/js/bootstrap/css/plupload.css" rel="stylesheet" />
<script src="${contextPath}/js/highcharts/jquery-1.8.2.min.js"></script>
<script src="${contextPath}/js/highcharts/highcharts.js"></script>
<script src="${contextPath}/js/highcharts/highcharts-3d.js"></script>
<style type="text/css">
#divChart {
	height: 400px;
	min-width: 500px;
	max-width: 800px;
	margin: 50 0;
}
</style>

<div id="divZZL" style="padding-left:100px;padding-top:20px;">
	年月
	<select id="listNY" name="ny" style="width:100px;">
		<c:forEach var="item" items="${ny}">
		<option value="${item}">${item}</option>
		</c:forEach>
	</select>
	店名
	<select id="listDM" name="dm" style="width:100px;">
		<c:forEach var="item" items="${dm}">
		<option value="${item}">${item}</option>
		</c:forEach>
	</select>
	品类
	<select id="listPL" name="pl" style="width:100px;">
		<c:forEach var="item" items="${pl}">
		<option value="${item}">${item}</option>
		</c:forEach>
	</select>
	<input type="radio" name="statisticstype" value="0" checked="checked" />销售额
	<input type="radio" name="statisticstype" value="1" />销售量
	<input type="radio" name="statisticstype" value="2" />毛利率
	<input type="radio" name="statisticstype" value="3" />增长率	
	<a class="btn btn-success" id="btnOK" onclick="javascript:onStatistics();"><i class="icon-ok icon-white"></i>统计</a>
	<div id="divChart"></div>
</div>

<script type="text/javascript">
var objChart = null;
var categories = [201409, 201410, 201411];
var data = [{name: 'pl1',data: [7.0, 6.9, 9.5]}, {name: 'pl2',data: [3.9, 4.2, 5.7]}];

function onStatistics() {
	var ny = $("#listNY").val();
	var dm = $("#listDM").val();
	var pl = $("#listPL").val();
	var statisticstype = $("input[name='statisticstype']:checked").val();
	$.ajax({
		type: 'post',
		url: "/uploaddatamanage/statistics/ppdesc",
		dataType: "text",
		data: {"ny": ny, "dm": dm, "pl": pl, "statisticstype": statisticstype},
		error: function () {
			alert("获取统计数据出错，请稍后重试。");
		},
		success: function (data) {
		    var response = $.parseJSON(data);
		    if(response.result == "success") {
				if(statisticstype == 3) {
					categories = response.zzl.pl;
					data = response.zzl.data;				
					onZZL_XSEInit(categories, data);
				} else {
					categories = response.data.categories;
					data = response.data.data;
					onChartInit(categories, data);
				}
		    }
		}
	});
}

function onChartInit(categories, data) {
	if(objChart) {
		objChart.destroy();
		objChart = null;
	}

	objChart = new Highcharts.Chart({
        chart: {
         	renderTo: 'divChart',
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

function onZZL_XSEInit(categoriesZZL_XSE, dataZZL_XSE) {
	if(objChart) {
		objChart.destroy();
		objChart = null;
	}

	objChart = new Highcharts.Chart({
        chart: {
         	renderTo: 'divChart',
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
	if(objChart) {
		objChart.destroy();
		objChart = null;
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
</script>