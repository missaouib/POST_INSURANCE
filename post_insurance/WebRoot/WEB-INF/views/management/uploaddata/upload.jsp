<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<link rel="stylesheet" type="text/css" href="${contextPath}/styles/webuploader/webuploader.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/styles/upload/upload.css">
<script type="text/javascript" src="${contextPath}/styles/webuploader/webuploader.js"></script>
<style>
<!--

p {
display: block;
-webkit-margin-before: 1em;
-webkit-margin-after: 1em;
-webkit-margin-start: 0px;
-webkit-margin-end: 0px;
}

h4 {
font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
font-weight: 500;
line-height: 1.1;
color: inherit;
}

.btn-default {
width: 80px;
line-height: 20px;
text-align: center;
color: #fff;
border-radius: 5px;
margin:0 20px 20px 0;
position: relative;
overflow: hidden;
}
.btn {
border:0px solid #1e7db9;
box-shadow: 0 1px 2px #8fcaee inset,0 -1px 0 #497897 inset,0 -2px 3px #8fcaee inset;
background: -webkit-linear-gradient(top,#42a4e0,#2e88c0);
background: -moz-linear-gradient(top,#42a4e0,#2e88c0);
background: linear-gradient(top,#42a4e0,#2e88c0);
}
-->
</style>
<h2 class="contentTitle">数据上传</h2>

<div class="pageContent" style="margin: 0 10px" layoutH="50">
	<label>模板:</label>
	<form:select path="myTemplate.templateValue" name="template" id="template" style="width:150px;" items="${templateList}" itemLabel="desc" onchange="javascript:displayTips(this.value);"/>
	<div id="uploader" class="wu-example">
	    <!--用来存放文件信息-->
	    <div id="thelist" class="uploader-list"></div>
	    <div class="btns">
	        <div id="picker">选择文件</div>
	        <button id="ctlBtn" class="btn btn-default">开始上传</button>
	    </div>
	</div>
	<br />
	<br />
	<br />
	<pre id="tipsDesc"></pre>
	<br />
	<pre id="console"></pre>
	<br />
</div>

<script type="text/javascript"> 

function displayTips(val) {
	if(val=="Policy" || val=="PolicyDtl" || val=="Issue" || val=="CallFail" || val =="Renewed" 
			|| val=="CheckWrite" || val=="CheckRecord" || val=="PayToFailList" || val=="PayFromFailList" || val =="PaySuccessList" 
			|| val =="IssuePFR" || val =="CallFailPFR") {
		$("#tipsDesc").html("模板格式直接使用系统下载的原始表，请勿修改列名称。");
		return;
	}
	if(val=="CallFailStatus") {
		$("#tipsDesc").html("11185二访中心详细数据。须含列：保单号\t工单类别\t工单状态\t工单子类\t工单内容\t回访日期1\t回访人1\t回访类型1\t回访内容1\t回访日期2\t回访人2\t回访类型2\t回访内容2\t回访日期3\t回访人3\t回访类型3\t回访内容3\t回访日期4\t回访人4\t回访类型4\t回访内容4\t回访日期5\t回访人5\t回访类型5\t回访内容5\t回访日期6\t回访人6\t回访类型6\t回访内容6，关键列：回访人1");
		return;
	}
	if(val=="MiniCallFailStatus") {
		$("#tipsDesc").html("二访中心简易数据。须含列：保单号\t二访日期\t二访人员\t二访类型\t二访内容\t其他\t客户资料备注\t拨打电话，关键列：二访类型");
		return;
	}
	if(val=="CallFailMiniCityStatus") {
		$("#tipsDesc").html("市县回访详细数据。须含列：保单号\t工单状态\t上门回访日期\t上门回访人员\t上门回访类型\t上门回访内容，关键列：上门回访人员");
		return;
	}
	if(val=="CallFailCityStatus") {
		$("#tipsDesc").html("市县上门回访数据。须含列：保单号\t上交时间\t统计月份\t回访结果\t回访时间\t回访人，关键列：上交时间");
		return;
	}
	if(val=="CallFailPhoneStatus") {
		$("#tipsDesc").html("二访通话清单。须含列：保单号\t通话号码\t通话开始时间\t通话结束时间\t通话时长，关键列：保单号");
		return;
	}
	if(val=="CallFailMailStatus") {
		$("#tipsDesc").html("已发信函数据。须含列：保单号\t信函日期，关键列：信函日期");
		return;
	}
	if(val=="CallFailMailBackStatus") {
		$("#tipsDesc").html("回邮信函数据。须含列：保单号\t退信原因\t退信时间，关键列：保单号");
		return;
	}
	if(val=="CallFailMailSuccessStatus") {
		$("#tipsDesc").html("回邮信函数据。须含列：保单号\t信函成功\t回邮时间\t客户签名时间，关键列：保单号");
		return;
	}
	if(val=="CallFailNeedDoorStatus") {
		$("#tipsDesc").html("需上门回访数据。须含列：保单号\t工单状态，关键列：工单状态");
		return;
	}
	if(val=="CallFailCloseStatus") {
		$("#tipsDesc").html("回访结案数据。须含列：保单号\t结案时间，关键列：结案时间");
		return;
	}
	if(val=="RenewedStatus") {
		$("#tipsDesc").html("续期继续率清单。须含列：保单号\t险种名称\t保单年度\t保单当前状态\t交费失败原因，关键列：保单号");
		return;
	}
	if(val=="RenewedHQList") {
		$("#tipsDesc").html("总部催收清单。须含列：保单号\t险种名称\t保单年度\t备注\t工单子类\t回访日期\t工单内容，关键列：保单号");
		return;
	}
	if(val=="RenewedProvList") {
		$("#tipsDesc").html("省分（二访中心）催收清单须含列：保险单号码\t险种名称\t保单年度\t一访时间\t一访结果\t标记，关键列：一访结果");
		return;
	}
	if(val=="RenewedFeeMatchList") {
		$("#tipsDesc").html("省分（二访中心）催收清单须含列：保险单号码\t险种名称\t保单年度\t匹配结果，关键列：匹配结果");
		return;
	}
	if(val=="RenewedCityList") {
		$("#tipsDesc").html("市县催收清单。须含列：保险单号码\t险种名称\t保单年度\t催交时间\t催交结果，关键列：催交结果");
		return;
	}
}

var strFileGroup = new Date().Format("yyyyMMddhhmmss");

//文件上传
jQuery(function() {
    var $ = jQuery,
        $list = $('#thelist'),
        $btn = $('#ctlBtn'),
        state = 'pending',
        uploader;
    
    uploader = WebUploader.create({
        // 不压缩image
        resize: false,
        // swf文件路径
        swf: '${contextPath}/styles/webuploader/Uploader.swf',
        // 文件接收服务端。
        server: '/management/uploaddata/upload',
        // 选择文件的按钮。可选。
        accept: {
            title: '请选择数据文件',
            extensions: 'csv,xls,xlsx,txt'
        },
        formData:{
            fileGroup: strFileGroup
        },
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker',
       	disableGlobalDnd: true,
        fileNumLimit: 10,
        fileSizeLimit: 200 * 1024 * 1024,    // 200 M
        fileSingleSizeLimit: 50 * 1024 * 1024    // 50 M
    });

    // 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
        $list.append( '<div id="' + file.id + '" class="item">' +
            '<h4 class="info">' + file.name + '</h4>' +
            '<p class="state">等待上传...</p>' +
        '</div>' );
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress .progress-bar');
        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<div class="progress progress-striped active">' +
              '<div class="progress-bar" role="progressbar" style="width: 0%">' +
              '</div>' +
            '</div>').appendTo( $li ).find('.progress-bar');
        }

        $li.find('p.state').text('上传中');
        $percent.css( 'width', percentage * 100 + '%' );
    });

    uploader.on( 'uploadSuccess', function( file, response  ) {
        $( '#'+file.id ).find('p.state').text('已上传');
        
        if (response.result != null && response.result == 'success' && response.strFileName != null) {
            $("#console").html("开始导入数据");
            var tImport = setInterval(function () {
                $("#console").html("正在导入数据......");
            }, 1000);
            template = $("#template").val();
            //alert(template);
            $.ajax({
                type: 'post',
                url: "/uploaddatamanage/uploaddata/import",
                dataType: "text",
                data: { "strFileGroup": strFileGroup, "ny": "${ny}", "template": template, "memo": "" },
                success: function (data) {
                    clearInterval(tImport);
                    var response = $.parseJSON(data);
                    if(response.result == "confirm") {
                    	if(window.confirm(response.message + "，是否继续导入？")) {
                    		$("#console").html("导入数据成功。");
                    	} else {
		                        $.ajax({
	                            type: 'post',
	                            url: "/uploaddatamanage/uploaddata/cancelimport",
	                            dataType: "text",
	                            data: { strFileGroup: strFileGroup, "ny": 1 },
	                            success: function (data) {
	                                var response = $.parseJSON(data);
									if (response.result == "success") {
	                                    $("#console").html("取消导入数据。");
	                                } else {
	                                    $("#console").html("取消导入数据失败！");
	                                    if (response.message != null) {
	                                        alert(response.message);
	                                    }
	                                }
	                            }
	                        });
                    	}
                    } else if (response.result == "success") {
                        $("#console").html(response.message + " - 导入数据成功。");
                    } else {
                        if (response.message != null) {
                            alert(response.message);
                            $("#console").html(response.message);
                        }
                    }
                }
            });
        } else {
            alert(response.message);
        }
    });

    uploader.on( 'uploadError', function( file ) {
        $( '#'+file.id ).find('p.state').text('上传出错');
    });

    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').fadeOut();
    });

    uploader.on( 'all', function( type ) {
        if ( type === 'startUpload' ) {
            state = 'uploading';
        } else if ( type === 'stopUpload' ) {
            state = 'paused';
        } else if ( type === 'uploadFinished' ) {
            state = 'done';
        }

        if ( state === 'uploading' ) {
            $btn.text('暂停上传');
        } else {
            $btn.text('开始上传');
        }
    });

    $btn.on( 'click', function() {
        if ( state === 'uploading' ) {
            uploader.stop();
        } else {
            uploader.upload();
        }
    });
});
</script>