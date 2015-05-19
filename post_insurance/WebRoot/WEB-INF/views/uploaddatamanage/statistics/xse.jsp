<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<link href="${contextPath}/js/bootstrap/css/plupload.css" rel="stylesheet" />
<script src="${contextPath}/js/highcharts/jquery-1.8.2.min.js"></script>
<script src="${contextPath}/js/highcharts/highcharts.js"></script>
<script src="${contextPath}/js/highcharts/highcharts-3d.js"></script>

<script src="${contextPath}/js/datav/build/deps.js"></script>
<script src="${contextPath}/js/datav/deps/seajs/sea.js"></script>

<style type="text/css">
#divZZL {
	height: 0px;
	min-width: 400px; 
	max-width: 600px;
	margin: 0 0;
}
#divTreemap {
    padding-left: 0px;
}
.divTable
{
	width: 100%;
	display:block;
	padding-top:30px;
	padding-bottom:0px;
	padding-right:0px;
	padding-left:30px; 
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
	width:300px;
	display:block;
}
</style>

<div id="divXSEParent" style="padding-left:100px;padding-top:20px;">
	<span style="display:none;">
	年月
	<select id="listNY" name="ny" style="width:100px;">
		<c:forEach var="item" items="${ny}">
		<option value="${item}">${item}</option>
		</c:forEach>
	</select>
	</span>
	<input type="radio" name="statisticstype" value="0" onclick="onChange(this.value);" checked="checked" />销售额
	<input type="radio" name="statisticstype" value="1" onclick="onChange(this.value);" />销售量
	<input type="radio" name="statisticstype" value="2" onclick="onChange(this.value);" />毛利率	
	<a class="btn btn-success" id="btnOK" onclick="javascript:onStatistics();" style="display:none;"><i class="icon-ok icon-white"></i>统计</a>
	<div class="divTable">
	   	<div class="divRow">
		    <div class="divColumn">
		        <div>
		            <label id="lblTitle"></label>
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
		            <label id="lblDM0" style="color:#ED561B;"></label>
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
		            <label id="lblDM1" style="color:#D6604D;"></label>
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
		            <label id="lblDM2" style="color:#D6604D;"></label>
		        </div>
		    </div>
		    <div class="divColumn">
		        <div>
		            <label id="lblZZL2" style="color:#D6604D;"></label>
		        </div>
		    </div>
		</div>
	</div>
	<div id="divTreemap"></div>
	<div id="divZZL"></div>
</div>

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
    
function onStatistics() {
	var ny = $("#listNY").val();
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

window.onbeforeunload = function() {
	//onDestroy();
}

function onDestroy() {
	if(objZZL) {
		objZZL.destroy();
		objZZL = null;
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

function onChange(value) {
	onStatistics();
};

$(document).ready(function(){onStatistics();});
</script>